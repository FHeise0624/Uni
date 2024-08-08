package org.group44.parliamentbrowser;

import static java.util.stream.Collectors.toList;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.group44.parliamentbrowser.builder.AbgeordneterBuilder;
import org.group44.parliamentbrowser.bundestag.AbgeordneterLoader;
import org.group44.parliamentbrowser.database.MongoDBConnectionHandler;
import org.group44.parliamentbrowser.pdf.PdfBuilder;
import org.group44.parliamentbrowser.types.Abgeordneter;
import org.group44.parliamentbrowser.types.AbgeordneterImpl;
import org.group44.parliamentbrowser.types.Benutzer;
import org.group44.parliamentbrowser.types.BenutzerGruppe;
import org.group44.parliamentbrowser.types.Rede;
import org.group44.parliamentbrowser.types.RedeImpl;
import org.group44.parliamentbrowser.types.Sitzung;
import org.group44.parliamentbrowser.types.SitzungImpl;
import org.group44.parliamentbrowser.types.Tagesordnung;
import org.group44.parliamentbrowser.types.TagesordnungImpl;
import org.group44.parliamentbrowser.util.FreeMarkerConfig;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * @author Cora
 *
 * Die Klasse Api stellt die RESTful-Schnittstelle basierend auf Java Spark dar
 *
 * Bearbeitet von Felix
 */

public class Api {

	private static final Logger LOGGER = LoggerFactory.getLogger(Api.class);
	
	private static MongoDBConnectionHandler mongoDb = MongoDBConnectionHandler.getInstance();

    private static PlenarProtokolleThread plenarProtokolleThread = null;


    private static boolean isAuthenticated = false;
    private static boolean isAuthenticatedUser = false;
    private static boolean isAuthenticatedManager = false;
    private static boolean isAuthenticatedAdmin = false;


	private static String toJson(String key, List<String> elements) {
		String elementsAsJson = elements.stream().collect(Collectors.joining(","));
		return "{\"" + key + "\":[" + elementsAsJson + "]}";
	}

    /**
     * Die Klasse startet den Import der Plenarprotokolle.
     * Die run Methode des PlenarProtokolleThreads läuft asynchron, das heißt, das submit
     * startet den Thread, wartet aber nicht auf dessen Beendigung
     * 
     * @author Cora
     */
    private static Route postPlenarProtokolle = (Request request, Response response) -> {
        plenarProtokolleThread = new PlenarProtokolleThread();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(plenarProtokolleThread);
		response.status(201);
		return plenarProtokolleThread.getProgress();
	};

	/**
	 * Liest den Status des Ladens der Plenarprotokolle, für die Fortschrittsanzeige
	 * 
	 * @author Cora
	 */
    public static Route getStatusPlenarProtokolle = (Request request, Response response) -> {
    	if (plenarProtokolleThread == null) {
    		Spark.halt(404, "Import von Plenarprotokollen nicht gestartet!");
    	}
        return plenarProtokolleThread.getProgress();
    };

    /**
     * Legt einen Abgeordneten in der Datenbank an
     * 
     * @author Cora
     */
    public static Route postAbgeordneter = (Request request, Response response) -> {
        String abgeordneterAsJson = request.body();
        AbgeordneterImpl abgeordneter = (AbgeordneterImpl) AbgeordneterImpl.fromJson(abgeordneterAsJson);
        mongoDb.createAbgeordneter(abgeordneter);
        response.status(201);
        return abgeordneter.toJson();
    };

    /**
     * Aktualisiert alle Abgeordneten mit den Stammdaten von der Web-Seite des Bundestages
     * 
     * @author Cora
     */
    public static Route postAbgeordnete = (Request request, Response response) -> {
        List<String> abgeordneteIdsInMongoDb = mongoDb.readAbgeordnete().stream().map(a -> a.getID().toString()).collect(Collectors.toList());
        AbgeordneterLoader abgeordneterLoader = new AbgeordneterLoader();
        Document xmlAbgeordnete = abgeordneterLoader.getXmlAbgeordnete();
        AbgeordneterBuilder abgeordneterBuilder = new AbgeordneterBuilder();
        List<Abgeordneter> abgeordnete = abgeordneterBuilder.from(xmlAbgeordnete);
        List<Abgeordneter> newAbgeordnete = new ArrayList<>();
        for (Abgeordneter abgeordneter : abgeordnete) {
            if (!abgeordneteIdsInMongoDb.contains(abgeordneter.getID().toString())) {
                newAbgeordnete.add(abgeordneter);
                LOGGER.info("Neuen Abgeordneten {} gefunden!", abgeordneter.toString());
            }
        }
        if (newAbgeordnete.isEmpty()) {
            Spark.halt(404, "Keine neuen Abgeordneten in den Stammdaten des Bundestages gefunden!");
        }
        mongoDb.createAbgeordnete(newAbgeordnete);
		List<String> newAbgeordneteAsJson = newAbgeordnete.stream().map(abgeordneter -> abgeordneter.toJson()).collect(toList());
		String responseBody = toJson("abgeordnete", newAbgeordneteAsJson);
        response.status(201);
        return responseBody;
    };

    /**
     * Aktualisiert einen Abgeordneten
     * 
     * @author Cora
     */
    public static Route putAbgeordneter = (Request request, Response response) -> {
        String abgeordneterAsJson = request.body();
        Abgeordneter abgeordneter = AbgeordneterImpl.fromJson(abgeordneterAsJson);
        long updateCount = mongoDb.updateAbgeordneter(abgeordneter);
        if (updateCount == 0) {
			Spark.halt(404, "Abgeordneter mit Id " + abgeordneter.getID().toString()+ " nicht in MongoDB gefunden");
        }
        response.status(200);
        return abgeordneter.toJson();
    };

    /**
     * Lädt alle Abgeordneten aus der Datenbank und liefert diese als JSON-Objekte zurück
     * 
     * @author Cora
     */
    public static Route getAbgeordnete = (Request request, Response response) -> {
		List<Abgeordneter> abgeordnete = mongoDb.readAbgeordnete();
		List<String> abgeordneteAsJson = abgeordnete.stream().map(abgeordneter -> abgeordneter.toJson()).collect(toList());
		String responseBody = toJson("abgeordnete", abgeordneteAsJson);
		response.status(200);
		return responseBody;
	};
	
    /**
     * Lädt einen Abgeordneten aus der Datenbank und liefert diesen als JSON-Objekt zurück
     * 
     * @author Cora
     */
	public static Route getAbgeordneter = (Request request, Response response) -> {
		String abgeordneterId = request.params(":abgeordneterId");
		Abgeordneter abgeordneter = mongoDb.readAbgeordneter(abgeordneterId);
		if (abgeordneter == null) {
			Spark.halt(404, "Abgeordneter mit Id " + abgeordneterId + " nicht in MongoDB gefunden");
		}
		String responseBody = abgeordneter.toJson();
		response.status(200);
		return responseBody;
	};

	/** Löscht einen Abgeordneten aus der Datenbank anhand des Request Parameters :abgeordneterId 
	 * 
	 * @author Cora
	 */
    public static Route deleteAbgeordneter = (Request request, Response response) -> {
        String abgeordneterId = request.params(":abgeordneterId");
        long delecteCount = mongoDb.deleteAbgeordneter(abgeordneterId);;
        if (delecteCount == 0) {
			Spark.halt(404, "Abgeordneter mit Id " + abgeordneterId + " nicht in MongoDB gefunden und konnte nicht gelöscht werden!");
        }
        response.status(200);
        return "Abgeordneter mit Id " + abgeordneterId + " gelöscht!";
    };

    /**
     * Legt eine neue Sitzung in der Datenbank an, die Sitzung wird als JSON-String übergeben
     * 
     * @author Cora
     */
    public static Route postSitzung = (Request request, Response response) -> {
        String sitzungAsJson = request.body();
        Sitzung sitzung = SitzungImpl.fromJson(sitzungAsJson);
        mongoDb.createSitzung(sitzung);
        response.status(201);
        return sitzung.toJson();
    };

    /**
     * Ändert eine Sitzung in der Datenbank
     * 
     * @author Cora
     */
    public static Route putSitzung = (Request request, Response response) -> {
        String sitzungAsJson = request.body();
        Sitzung sitzung = SitzungImpl.fromJson(sitzungAsJson);
        long updateCount = mongoDb.updateSitzung(sitzung);
        if (updateCount == 0) {
			Spark.halt(404, "Sitzung mit Id " + sitzung.getID().toString() + " nicht in MongoDB gefunden");
        }
        response.status(200);
        return sitzung.toJson();
    };

    /**
     * Liest alle Sitzungen einer Wahlperiode und liefert diese als JSON-Objekte zurück
     * 
     * @author Cora
     */
	public static Route getSitzungen = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			List<Sitzung> sitzungen = mongoDb.readSitzungen(wahlperiode);
			List<String> sitzungenAsJson = sitzungen.stream().map(sitzung -> sitzung.toJson()).collect(toList());
			String responseBody = toJson("sitzungen", sitzungenAsJson);
			response.status(200);
			return responseBody;
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und nummer müssen numerisch sein!";
		}
	};

	/**
	 * Liest eine Sitzung anhand der Request Parameter :wahlperiode und :nummer aus der Datenbank und liefert diese als JSON Objekt zurück
	 * 
	 * @author Cora
	 */
	public static Route getSitzung = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			Integer nummer = Integer.valueOf(request.params(":nummer"));
			Sitzung sitzung = mongoDb.readSitzung(wahlperiode, nummer);
			if (sitzung == null) {
				Spark.halt(404, "Keine Sitzung mit Nummer " + nummer +  " in Wahlperiode " + wahlperiode + " in MongoDB gefunden");
			}
			String responseBody = sitzung.toJson();
			response.status(200);
			return responseBody;
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und nummer müssen numerisch sein!";
		}
	};

	/**
	 * Löscht eine Sitzung anhand der Request Parameter :wahlperiode und :nummer aus der Datenbank
	 * 
	 * @author Cora
	 */
    public static Route deleteSitzung = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			Integer nummer = Integer.valueOf(request.params(":nummer"));
	        long deleteCount = mongoDb.deleteSitzung(wahlperiode, nummer);
	        if (deleteCount == 0) {
				Spark.halt(404, "Sitzung mit Nummer " + nummer + " in Wahlperiode " + wahlperiode + " nicht in MongoDB gefunden");
	        }
	        response.status(200);
	        return "Sitzung mit Nummer " + nummer + " in Wahlperiode " + wahlperiode + " gelöscht!";
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und nummer müssen numerisch sein!";
		}
    };

    /**
     * Legt eine neue Sitzung mit den Daten des übergeben JSON-Objekts in der Datenbank an
     * 
     * @author Cora
     */
    public static Route postTagesordnung = (Request request, Response response) -> {
        String tagesordnungAsJson = request.body();
        Tagesordnung tagesordnung = TagesordnungImpl.fromJson(tagesordnungAsJson);
        mongoDb.createTagesordnung(tagesordnung);
        response.status(201);
        return tagesordnung.toJson();
    };

    /**
     * Aktualisiert eine Sitzung mit den Daten des übergebenen JSON-Objekts
     * 
     * @author Cora
     */
    public static Route putTagesordnung = (Request request, Response response) -> {
        String tagesordnungAsJson = request.body();
        Tagesordnung tagesordnung = TagesordnungImpl.fromJson(tagesordnungAsJson);
        long updateCount = mongoDb.updateTagesordnung(tagesordnung);
        if (updateCount == 0) {
			Spark.halt(404, "Tagesordnung mit Id " + tagesordnung.getID().toString() + " nicht in MongoDB gefunden");
        }
        response.status(200);
        return tagesordnung.toJson();
    };

    /**
     * Liest alle Tagesordnungen einer Sitzung in einer Wahlperiode und liefert diese als JSON-Objekt zurück
     * 
     *  @author Cora
     */
	public static Route getTagesordnungen = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			Integer sitzungNummer = Integer.valueOf(request.params(":sitzungNummer"));
			List<Tagesordnung> tagesordnungen = mongoDb.readTagesordnungen(wahlperiode, sitzungNummer);
			if (tagesordnungen.isEmpty()) {
				Spark.halt(404, "Keine Tagesordnungen mit Nummer " + sitzungNummer +  " in Wahlperiode " + wahlperiode + " in MongoDB gefunden");
			}
			List<String> tagesordnungenAsJson = tagesordnungen.stream().map(tagesordnung -> tagesordnung.toJson()).collect(toList());
			String responseBody = toJson("tagesordnungen", tagesordnungenAsJson);
			response.status(200);
			return responseBody;
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und sitzungNummer müssen numerisch sein!";
		}
	};
	
    /**
     * Liest eine Tagesordnung mit einer Id einer Sitzung in einer Wahlperiode und liefert dieses als JSON-Objekt zurück
     * 
     *  @author Cora
     */
	public static Route getTagesordnung = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			Integer sitzungNummer = Integer.valueOf(request.params(":sitzungNummer"));
			String topId = request.params(":topId");
			Tagesordnung tagesordnung = mongoDb.readTagesordnung(wahlperiode, sitzungNummer, topId);
			if (tagesordnung == null) {
				Spark.halt(404, "Keine Tagesordnung mit Top-Id " + topId +  " in Wahlperiode " + wahlperiode + " in Sitzung " + sitzungNummer + " in MongoDB gefunden");
			}
			String responseBody = tagesordnung.toJson();
			response.status(200);
			return responseBody;
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und sitzungNummer müssen numerisch sein!";
		}
	};

    /**
     * Löscht alle Tagesordnunges einer Sitzung in einer Wahlperiode aus der Datenbank
     * 
     *  @author Cora
     */
    public static Route deleteTagesordnungen = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			Integer sitzungNummer = Integer.valueOf(request.params(":sitzungNummer"));
			long deleteCount = mongoDb.deleteTagesordnung(wahlperiode, sitzungNummer);
			if (deleteCount == 0) {
				Spark.halt(404, "Keine Tagesordnung in Wahlperiode " + wahlperiode + " in Sitzung " + sitzungNummer + " in MongoDB gefunden");
			}
			response.status(200);
			return deleteCount + " Tagesordnungen  in Wahlperiode " + wahlperiode + " in Sitzung " + sitzungNummer + " gelöscht!";
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und sitzungNummer müssen numerisch sein!";
		}
    };

    /**
     * Löscht eine Tagesordnung mit einer Id einer Sitzung in einer Wahlperiode aus der Datenbank
     * 
     *  @author Cora
     */
    public static Route deleteTagesordnung = (Request request, Response response) -> {
		try {
			Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
			Integer sitzungNummer = Integer.valueOf(request.params(":sitzungNummer"));
			String topId = request.params(":topId");
			long deleteCount = mongoDb.deleteTagesordnung(wahlperiode, sitzungNummer, topId);
			if (deleteCount == 0) {
				Spark.halt(404, "Keine Tagesordnung mit Top-Id " + topId +  " in Wahlperiode " + wahlperiode + " in Sitzung " + sitzungNummer + " in MongoDB gefunden");
			}
			response.status(200);
			return "Tagesordnung mit Top-Id "+ topId + " in Wahlperiode " + wahlperiode + " in Sitzung " + sitzungNummer + " gelöscht!";
		} catch (NumberFormatException e) {
			response.status(422);
			return "Parameter wahlperiode und sitzungNummer müssen numerisch sein!";
		}
    };

    /**
     * Legt eine neue Rede mit den Daten des übergeben JSON-Objekts in der Datenbank an
     * 
     *  @author Cora
     */
    public static Route postRede = (Request request, Response response) -> {
        String redeAsJson = request.body();
        Rede rede = RedeImpl.fromJson(redeAsJson);
        mongoDb.createRede(rede);
        response.status(201);
        return rede.toJson();
    };

    /**
     * Aktualisiert eine Rede mit den Daten des übergeben JSON-Objekts in der Datenbank an
     * 
     *  @author Cora
     */
    public static Route putRede = (Request request, Response response) -> {
        String redeAsJson = request.body();
        Rede rede = RedeImpl.fromJson(redeAsJson);
        long updateCount = mongoDb.updateRede(rede);
        if (updateCount == 0) {
			Spark.halt(404, "Keine Rede mit Id " + rede.getRedeId() + " in MongoDB gefunden");
        }
        response.status(200);
        return rede.toJson();
    };

    /**
     * Liest alle Reden eines Abgeordneten aus der Datenbank und liefert diese als JSON-Objekt zurück
     * 
     *  @author Cora
     */
    public static Route getRedenOfAbgeordneter = (Request request, Response response) -> {
		String abgeordneterId = request.params(":abgeordneterId");
		List<Rede> reden = mongoDb.readRedenOfAbgeordneter(abgeordneterId);
		if (reden == null || reden.isEmpty()) {
			Spark.halt(404, "Keine Reden mit Redner-Id " + abgeordneterId + " in MongoDB gefunden");
		}
		List<String> redenAsJson = reden.stream().map(rede -> rede.toJson()).collect(toList());
		String responseBody = toJson("reden", redenAsJson);
		response.status(200);
		return responseBody;
	};

    /**
     * Liest eine Rede mit einer Id aus der Datenbank und liefert diese als JSON-Objekt zurück
     * 
     *  @author Cora
     */
	public static Route getRede = (Request request, Response response) -> {
		String redeId = request.params(":redeId");
		Rede rede = mongoDb.readRede(redeId);
		if (rede == null) {
			Spark.halt(404, "Keine Rede mit Rede-Id " + redeId + " in MongoDB gefunden");
		}
		String responseBody = rede.toJson();
		response.status(200);
		return responseBody;
	};

    /**
     * Löscht eine Rede mit einer Id aus der Datenbank 
     * 
     *  @author Cora
     */
    public static Route deleteRede = (Request request, Response response) -> {
        String redeId = request.params(":redeId");
        long deleteCount = mongoDb.deleteRede(redeId);
        if (deleteCount == 0) {
			Spark.halt(404, "Keine Rede mit Rede-Id " + redeId + " in MongoDB gefunden");
        }
        response.status(200);
        return "Rede mit Id " + redeId + " gelöscht!";
    };
    
    /**
     * Liest alle Reden einer Sitzung einer Wahlperiode aus der Datenbank und liefert diese als JSON-Objekt zurück
     * 
     *  @author Cora
     */
    public static Route getRedenOfSitzung = (Request request, Response response) -> {
        Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
        Integer sitzungsNummer = Integer.valueOf(request.params(":sitzungsNummer"));
        List<Rede> reden = mongoDb.readReden(wahlperiode, sitzungsNummer);
        if (reden.isEmpty()) {
			Spark.halt(404, "Keine Rede für die Sitzung " + sitzungsNummer + " in der Wahlperiode " + wahlperiode + " in MongoDB gefunden");
        }
		String responseBody = toJson("reden", reden.stream().map(rede -> rede.toJson()).collect(Collectors.toList()));
		response.status(200);
		return responseBody;
    };
    
    /**
     * Löscht alle Reden einer Sitzung einer Wahlperiode aus der Datenbank 
     * 
     *  @author Cora
     */
    public static Route deleteRedenOfSitzung = (Request request, Response response) -> {
        Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
        Integer sitzungsNummer = Integer.valueOf(request.params(":sitzungsNummer"));
        long deleteCount = mongoDb.deleteReden(wahlperiode, sitzungsNummer);
        if (deleteCount == 0) {
			Spark.halt(404, "Keine Rede für die Sitzung " + sitzungsNummer + " in der Wahlperiode " + wahlperiode + " in MongoDB gefunden");
        }
        response.status(200);
        return deleteCount + " Reden gelöscht!";
    };

    /**
     * Liest eine Sitzung mit allen Tagesordnungen und Reden aus der Datenbank und erzeugt eine PDF datei auf Basis einer Latex-Vorlage
     * 
     * @author Dominik, Cora
     */
    public static Route getSitzungAsPdf = (Request request, Response response) -> {
        Integer wahlperiode = Integer.valueOf(request.params(":wahlperiode"));
        Integer sitzungsNummer = Integer.valueOf(request.params(":sitzungsNummer"));
        byte[] content = new PdfBuilder().sitzungToPdf(wahlperiode, sitzungsNummer);
        if (content == null) {
			Spark.halt(404, "Keine Tgesordnungen für die Sitzung " + sitzungsNummer + " in der Wahlperiode " + wahlperiode + " in MongoDB gefunden");
        }
        response.status(200);
        response.type("application/pdf");
        return content;
    };
    
    /**
     * Aktualisiert die Latex-Vorlage zur Erzeugung einer PDF Datei aus einer Sitzung mit allen Tagesordnungen und Reden
     * 
     * @author Dominik, Cora
     */
    public static Route postSitzungVorlage = (Request request, Response response) -> {
    	new PdfBuilder().updateSitzungVorlage(request.body());
        response.status(201);
        return request.body();
    };
    
    /**
     * Legt einen Benutzer mit den Daten aus dem JSON-Objekt in der Datenbank an
     * 
     * @author Cora
     */
    public static Route postBenutzer = (Request request, Response response) -> {
        String benutzerAsJson = request.body();
        Benutzer benutzer = Benutzer.fromJson(benutzerAsJson);
        mongoDb.createBenutzer(benutzer);
        response.status(201);
        return benutzer.toJson();
    };

    /**
     * Aktualsiert einen Benutzer mit den Daten aus dem JSON-Objekt in der Datenbank
     * 
     * @author Cora
     */
    public static Route putBenutzer = (Request request, Response response) -> {
        String benutzerAsJson = request.body();
        Benutzer benutzer = Benutzer.fromJson(benutzerAsJson);
        long updateCount = mongoDb.updateBenutzer(benutzer);
        if (updateCount == 0) {
			Spark.halt(404, "Keinen Benutzer mit Id " + benutzer.getBenutzerId() + " in MongoDB gefunden");
        }
        response.status(200);
        return benutzer.toJson();
    };

    /**
     * Liest alle Benutzer aus der Datenbank und liefert diese als JSON-Objekte zurück
     * 
     * @author Cora
     */
    public static Route getAlleBenutzer = (Request request, Response response) -> {
        List<Benutzer> benutzer = mongoDb.readBenutzer();
        List<String> benutzerAsJson = benutzer.stream().map(b -> b.toJson()).collect(toList());
        String responseBody = toJson("benutzer", benutzerAsJson);
        response.status(200);
        return responseBody;
    };

    /**
     * Liest einen Benutzer anhand seiner Id aus der Datenbank und liefert diesen als JSON-Objekt zurück
     * 
     * @author Cora
     */
    public static Route getBenutzer = (Request request, Response response) -> {
        String benutzerId = request.params(":benutzerId");
        Benutzer benutzer = mongoDb.readBenutzer(benutzerId);
        if (benutzer == null) {
			Spark.halt(404, "Keine Benutzer mit Id " + benutzerId + " in MongoDB gefunden");
        }
        String responseBody = benutzer.toJson();
        response.status(200);
        return responseBody;
    };

    /**
     * Löscht einen Benutzer anhand seiner Id aus der Datenbank
     * 
     * @author Cora
     */
    public static Route deleteBenutzer = (Request request, Response response) -> {
        String benutzerId = request.params(":benutzerId");
        long deleteCount = mongoDb.deleteBenutzer(benutzerId);
        if (deleteCount == 0) {
			Spark.halt(404, "Keine Benutzer mit Id " + benutzerId + " in MongoDB gefunden");
        }
        response.status(200);
        return "Benutzer mit Id " + benutzerId + " gelöscht!";
    };

    /**
     * Legt eine Benutzergruppe mit den Daten des übergebenen JSON-Objekts in der Datenbank an
     * 
     * @author Cora
     */
    public static Route postBenutzerGruppe = (Request request, Response response) -> {
        String gruppeAsJson = request.body();
        BenutzerGruppe gruppe = BenutzerGruppe.fromJson(gruppeAsJson);
        mongoDb.createBenutzerGruppe(gruppe);
        response.status(201);
        return gruppe.toJson();
    };

    /**
     * Aktualisiert eine Benutzergruppe mit den Daten des übergebenen JSON-Objekts in der Datenbank
     * 
     * @author Cora
     */
    public static Route putBenutzerGruppe = (Request request, Response response) -> {
        String gruppeAsJson = request.body();
        BenutzerGruppe gruppe = BenutzerGruppe.fromJson(gruppeAsJson);
        long updateCount = mongoDb.updateBenutzerGruppe(gruppe);
        if (updateCount == 0) {
			Spark.halt(404, "Keine BenutzerGruppe mit Id " + gruppe.getGruppeId() + " in MongoDB gefunden");
        }
        response.status(200);
        return gruppe.toJson();
    };

    /**
     * Liest alle Benutzergruppen aus der Datenbank und gibt diese als JSON-Objekt zurück
     * 
     * @author Cora
     */
    public static Route getBenutzerGruppen = (Request request, Response response) -> {
        List<BenutzerGruppe> gruppen = mongoDb.readBenutzerGruppen();
        List<String> gruppenAsJson = gruppen.stream().map(b -> b.toJson()).collect(toList());
        String responseBody = toJson("benutzergruppen", gruppenAsJson);
        response.status(200);
        return responseBody;
    };

    /**
     * Liest eine Benutzergruppe anhand ihrer Id aus der Datenbank und gibt diese als JSON-Objekt zurück
     * 
     * @author Cora
     */
    public static Route getBenutzerGruppe = (Request request, Response response) -> {
        String gruppeId = request.params(":gruppeId");
        BenutzerGruppe gruppe = mongoDb.readBenutzerGruppe(gruppeId);
        if (gruppe == null) {
            Spark.halt(404, "Keine Benutzergruppe mit Id " + gruppeId + " in MongoDB gefunden");
        }
        String responseBody = gruppe.toJson();
        response.status(200);
        return responseBody;
    };

    /**
     * Löscht einen Benutzergruppe anhand ihrer Id aus der Datenbank
     * 
     * @author Cora
     */
    public static Route deleteBenutzerGruppe = (Request request, Response response) -> {
        String gruppeId = request.params(":gruppeId");
        long deleteCount = mongoDb.deleteBenutzerGruppe(gruppeId);
        if (deleteCount == 0) {
			Spark.halt(404, "Keine BenutzerGruppe mit Id " + gruppeId + " in MongoDB gefunden");
        }
        response.status(200);
        return "BenutzerGruppe mit Id " + gruppeId + " gelöscht!";
    };


    public static void main(String[] args) {

        // Definition der nötigen Daten zur Initialisierung des Webdienstes
        port(1616);
        staticFileLocation("/style");
        Configuration configuration = FreeMarkerConfig.configure();
        FreeMarkerEngine fME = new FreeMarkerEngine(configuration);

        post("/abgeordnete", Api.postAbgeordnete);
        post("/plenarprotokolle", Api.postPlenarProtokolle);
        get("/plenarprotokolle/status", Api.getStatusPlenarProtokolle);
        
        get("/abgeordnete", Api.getAbgeordnete);

        post("/abgeordneter", Api.postAbgeordneter);
        put("/abgeordneter", Api.putAbgeordneter);
        get("/abgeordneter/:abgeordneterId", Api.getAbgeordneter);
        delete("/abgeordneter/:abgeordneterId", Api.deleteAbgeordneter);

        post("/sitzung", Api.postSitzung);
        put("/sitzung", Api.putSitzung);
        get("/sitzung/:wahlperiode/:nummer", Api.getSitzung);
        delete("/sitzung/:wahlperiode/:nummer", Api.deleteSitzung);

        get("/sitzungen/:wahlperiode", Api.getSitzungen);

        post("/tagesordnung", Api.postTagesordnung);
        put("/tagesordnung", Api.putTagesordnung);
        get("/tagesordnung/:wahlperiode/:sitzungNummer/:topId", Api.getTagesordnung);
        delete("/tagesordnung/:wahlperiode/:sitzungNummer/:topId", Api.deleteTagesordnung);

        get("/tagesordnungen/:wahlperiode/:sitzungNummer", Api.getTagesordnungen);
        delete("/tagesordnungen/:wahlperiode/:sitzungNummer", Api.deleteTagesordnungen);

        post("/rede", Api.postRede);
        put("/rede", Api.putRede);
        get("/rede/:redeId", Api.getRede);
        delete("/rede/:redeId", Api.deleteRede);

        get("/reden/:abgeordneterId", Api.getRedenOfAbgeordneter);
        get("/reden/:wahlperiode/:sitzungsNummer", Api.getRedenOfSitzung);
        delete("/reden/:wahlperiode/:sitzungsNummer", Api.deleteRedenOfSitzung);

        get("/latex/sitzung/:wahlperiode/:sitzungsNummer", Api.getSitzungAsPdf);
        post("/latex/sitzung/vorlage", Api.postSitzungVorlage);
        
        post("/benutzer", Api.postBenutzer);
        put("/benutzer", Api.putBenutzer);
        get("/benutzer/:benutzerId", Api.getBenutzer);
        delete("/benutzer/:benutzerId", Api.deleteBenutzer);

        get("/benutzers", Api.getAlleBenutzer);

        post("/benutzergruppe", Api.postBenutzerGruppe);
        put("/benutzergruppe", Api.putBenutzerGruppe);
        get("/benutzergruppe/:gruppeId", Api.getBenutzerGruppe);
        delete("/benutzergruppe/:gruppeId", Api.deleteBenutzerGruppe);

        get("/benutzergruppen", Api.getBenutzerGruppen);

        // Redirection auf die Homepage bei leerem Link
        get("/", (req, res) -> {
            res.redirect("/home");
            return null;
        });

        // Definition der Route zur Homepage und übergabe der Nutzerrechte.
        get("/home", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
            return fME.render(new ModelAndView(model, "homepage.ftl"));
        });

        // Definition der Route zum Login
        get("/login", (req, res) -> {
            return fME.render(new ModelAndView(null,"login.ftl"));
        });

        // Definition der Login Logik
        post("/login", (req, res) -> {
            // Abrufen aller Benutzer aus der Datenbank
            String responseBody = (String) Api.getAlleBenutzer.handle(req, res);
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray usersArray = jsonObject.getAsJsonArray("benutzer");

            // Speichern der Eingabe auf der Loginseite
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            // Schleife zum Extrahieren der Benutzerdaten aus dem JSON-File
            for (JsonElement userElement : usersArray) {
                JsonObject userObject = userElement.getAsJsonObject();
                String storedUsername = String.valueOf(userObject.get("benutzername").getAsString());
                String storedPassword = String.valueOf(userObject.get("password").getAsString());

                // Abgleich der Eingabe mit gespeicherten Daten.
                if (username.equals(storedUsername)) {
                    if (password.equals(storedPassword)) {

                        // Bei korrekten Logindaten, übergabe der Nutzerdaten an die Session.
                        req.session(true).attribute("username", username);
                        req.session(true).attribute("email", String.valueOf(userObject.get("email").getAsString()));
                        req.session(true).attribute("nachname", String.valueOf(userObject.get("nachname").getAsString()));
                        req.session(true).attribute("vorname", String.valueOf(userObject.get("vorname").getAsString()));
                        req.session(true).attribute("nutzerId",
                                String.valueOf(userObject.get("benutzerId").getAsString()));

                        // Extrahieren der Nutzergruppen und übergabe dieser als Liste von Strings
                        JsonArray rollenIds = userObject.getAsJsonArray("gruppenIds");
                        List<String> rollen = new ArrayList<>();
                        for (JsonElement e : rollenIds) {
                            rollen.add(e.getAsString());
                        }
                        String rolle = rollen.toString();
                        req.session(true).attribute("rolle", rolle);

                        // Authentifizierungsstatus auf true setzten.
                        isAuthenticated = true;
                        if (rollen.contains("Admin")) {
                            isAuthenticatedAdmin = true;
                            isAuthenticatedManager = true;
                            isAuthenticatedUser = true;
                        } else if (rollen.contains("Manager")) {
                            isAuthenticatedManager = true;
                            isAuthenticatedUser = true;
                        } else if (rollen.contains("User")) {
                            isAuthenticatedUser = true;
                        }

                        // Authentifizierungsstatus als Attribute der Session speichern
                        req.session().attribute("isAuthenticated", isAuthenticated);
                        req.session().attribute("isAuthenticatedUser", isAuthenticatedUser);
                        req.session().attribute("isAuthenticatedManager", isAuthenticatedManager);
                        req.session().attribute("isAuthenticatedAdmin", isAuthenticatedAdmin);

                        // Weiterleitung auf Homepage nach erfolgtem Login.
                        res.redirect("/home");
                        return null;
                    } else {

                        // Ausgabe Fehlermeldung, wenn kein Nutzer mit dem eingegebenen Passwort existiert.
                        res.status(401);
                        return "Login failed: Invalid username or password";
                    }
                }
            }

            //Ausgabe Fehlermeldung, wenn der Login nicht durchgeführt werden kann.
            res.status(401);
            return "Login failed: Invalid username or password";
        });

        // Definition der Route der Signup Page
        get("/signup", (req, res) -> {

            // Übergabe des Authenticated Status an die Signup Page.
            Map<String, Object> model = new HashMap<>();
            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
            return fME.render(new ModelAndView(model, "signup.ftl"));
        });

        // Post-Request neuer Nutzer mittels API-Methode
        post("/postBenutzer", (req, res) -> {
            Api.postBenutzer.handle(req, res);
            res.status(201);
            res.redirect("/home");
            return null;
        });

        // Definition der Route für die Profilseite
        get("/profile", (req, res) -> {

            // Abfragen der Nutzerdaten aus der Session
            String username = req.session().attribute("username");
            String vorname = req.session().attribute("vorname");
            String nachname = req.session().attribute("nachname");
            String email = req.session().attribute("email");
            String rolle = req.session().attribute("rolle");
            String id = req.session().attribute("nutzerId");
            Map<String, Object> model = new HashMap<>();

            // Übergabe der Nutzerdaten
            model.put("username", username);
            model.put("vorname", vorname);
            model.put("nachname", nachname);
            model.put("email", email);
            model.put("rolle", rolle);
            model.put("id", id);

            // Übergabe des Authentifizierungsstatus
            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
            return fME.render(new ModelAndView(model, "profile.ftl"));
        });

        // Definition von Path für das Benutzer Management
        get("/userManagement", (req, res) -> {

            // Abfrage aller Benutzerdaten aus der MongoDB
            String responseBody = (String) Api.getAlleBenutzer.handle(req, res);
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray usersArray = jsonObject.getAsJsonArray("benutzer");
            List<Map<String, Object>> nutzer = new Gson().fromJson(usersArray, new TypeToken<List<Map<String, Object>>>(){}.getType());

            // Übergabe der Benutzerdaten
            Map<String, Object> model = new HashMap<>();
            model.put("nutzer", nutzer);

            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
            return fME.render(new ModelAndView(model, "userManagement.ftl"));
        });

        // Definition der Route des Post-Request für den Logout
        post("/logout", (req, res) -> {

            // Prüfen ob nutzer eingeloggt ist
            if (isAuthenticatedUser || isAuthenticatedManager || isAuthenticatedAdmin) {
                req.session().invalidate();
                isAuthenticated = false;
                isAuthenticatedUser = false;
                isAuthenticatedManager = false;
                isAuthenticatedAdmin = false;
                req.session().attribute("isAuthenticated", isAuthenticated);
                req.session().attribute("isAuthenticatedUser", isAuthenticatedUser);
                req.session().attribute("isAuthenticatedManager", isAuthenticatedManager);
                req.session().attribute("isAuthenticatedAdmin", isAuthenticatedAdmin);
                res.redirect("/home");
                return null;
            } else {
                res.redirect("/home");
                return "Benutzer ist nicht angemeldet";
            }
        });

        // Definition des Path für das Abgeordneten Management
        get("/abgeordnetenManagement", (req, res) -> {

            // Abrufen aller Abgeordneten aud der MongoDB
            String responseBody = (String) Api.getAbgeordnete.handle(req, res);
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray usersArray = jsonObject.getAsJsonArray("abgeordnete");
            List<Map<String, Object>> abgeordnete = new Gson().fromJson(usersArray,
                    new TypeToken<List<Map<String, Object>>>(){}.getType());

            // Übergabe der Abgeordneten & des Authentifizierungsstatus
            Map<String, Object> model = new HashMap<>();
            model.put("abgeordnete", abgeordnete);
            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
           return fME.render(new ModelAndView(model, "abgeordnetenManagement.ftl"));
        });

        // Definition der Route der Reden
        get("/speeches", (req, res) ->{

            // Übergabe des Authentifizierungsstatus
            Map<String, Object> model = new HashMap<>();
            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
           return fME.render(new ModelAndView(model, "speeches.ftl"));
        });

        // Definition der Route zur Protokolle-Seite
        get("/protokolle", (req, res) -> {

             // Übergabe des Authentifizierungsstatus
            Map<String, Object> model = new HashMap<>();
            model.put("isAuthenticatedAdmin", isAuthenticatedAdmin);
            model.put("isAuthenticated", isAuthenticated);
            model.put("isAuthenticatedUser", isAuthenticatedUser);
            model.put("isAuthenticatedManager", isAuthenticatedManager);
           return fME.render(new ModelAndView(model, "protokolle.ftl"));
        });
    }
}

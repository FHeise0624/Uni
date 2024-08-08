package org.group44.parliamentbrowser.database;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.group44.parliamentbrowser.Configuration;
import org.group44.parliamentbrowser.exception.BundestagException;
import org.group44.parliamentbrowser.exception.BundestagFileException;
import org.group44.parliamentbrowser.types.*;
import org.group44.parliamentbrowser.util.ObjectCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

/**
 * Die Klasse stellt die Verbindung zur MongoDB sicher, hier werden die Daten in die Datenbank geladen
 *
 * @author Cora
 */

public class MongoDBConnectionHandler implements ConnectionHandler {
    
	private static Logger LOGGER = LoggerFactory.getLogger(MongoDBConnectionHandler.class);
	private static MongoDBConnectionHandler INSTANCE = null;
	
	public static final String COLLECTION_ABGEORDNETER = "abgeordneter";
	public static final String COLLECTION_SITZUNG = "sitzung";
	public static final String COLLECTION_REDE = "rede";
	public static final String COLLECTION_TAGESORDNUNG = "tagesordnung";
    public static final String COLLECTION_BENUTZER = "benutzer";
    public static final String COLLECTION_BENUTZERGRUPPE = "benutzergruppe";
	
    private Configuration configuration;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    
	private MongoCollection<AbgeordneterImpl> abgeordneterCollection = null;
	private MongoCollection<SitzungImpl> sitzungCollection = null;
	private MongoCollection<TagesordnungImpl> tagesordnungCollection = null;
	private MongoCollection<RedeImpl> redeCollection = null;
    private MongoCollection<Benutzer> benutzerCollection = null;
    private MongoCollection<BenutzerGruppe> benutzerGruppeCollection = null;

	private boolean opened = false;
	
    private MongoDBConnectionHandler() {
    	this.configuration = Configuration.getInstance();
    	open();
    }
    
    /**
     * Liefert eine Instanz des MongoDBConnectionHandlers, als Singleton implementiert
     * 
     * @return die aktive Datenbank-Verbindung
     */
    public static MongoDBConnectionHandler getInstance() {
    	if (INSTANCE == null) {
    		INSTANCE = new MongoDBConnectionHandler();
    	}
    	return INSTANCE;
    }
    
    /**
     * Öffnet die Datenbank-Verbindung und initialisiert den ConnectionHandler
     */
    public void open() {
    	if (!opened) {
	    	LOGGER.info("Anmeldung an der Datenbank {} auf dem Server {}:{} mit dem Benutzer {}.", configuration.getRemoteDatabase(), configuration.getRemoteHost(), configuration.getRemotePort(), configuration.getRemoteUser());
	        mongoClient = new MongoClient(mongoServer(), mongoCredentials(), mongoClientOptions());
	        mongoDatabase = mongoClient.getDatabase(configuration.getRemoteDatabase());
	        abgeordneterCollection = mongoDatabase.getCollection(COLLECTION_ABGEORDNETER, AbgeordneterImpl.class);
	        sitzungCollection = mongoDatabase.getCollection(COLLECTION_SITZUNG, SitzungImpl.class);
	        tagesordnungCollection = mongoDatabase.getCollection(COLLECTION_TAGESORDNUNG, TagesordnungImpl.class);
	        redeCollection = mongoDatabase.getCollection(COLLECTION_REDE, RedeImpl.class);
            benutzerCollection = mongoDatabase.getCollection(COLLECTION_BENUTZER, Benutzer.class);
            benutzerGruppeCollection = mongoDatabase.getCollection(COLLECTION_BENUTZERGRUPPE, BenutzerGruppe.class);
	        opened = true;
    	}
    }

    /**
     * Legt Indizes in der Datenbank an
     */
    public void createIndexes() {
        sitzungCollection.createIndex(Indexes.ascending("rednerId"));
        redeCollection.createIndex(Indexes.ascending("rednerId"));
    }
    
    /**
     * Liest alle Abgeordneten aus der Datenbank
     * 
     * @return Liefert eine Liste mit allen Abgeordneten aus der Datenbank
     */
	@Override
	public List<Abgeordneter> readAbgeordnete() {
		List<Abgeordneter> abgeordnete = abgeordneterCollection.find().into(new ArrayList<>());
		LOGGER.info("{} Abgeordnete aus der Collection {} geladen.", abgeordnete.size(), COLLECTION_ABGEORDNETER);
		return abgeordnete;
	}
	
	/**
	 * Liest einen Abgeordneten mit einer Id aus der Datenbank
	 * 
	 * @param abgeordneterId - Id des gesuchten Abgeordneten
	 * 
	 * @return Abgeordneter mit der Id
	 */
	public Abgeordneter readAbgeordneter(String abgeordneterId) {
		Document query = new Document();
		query.append("abgeordneterId", abgeordneterId);
		List<Abgeordneter> abgeordnete = abgeordneterCollection.find(query).into(new ArrayList<>());
		LOGGER.info("{} Abgeordneter mit Id {} aus der Collection {} geladen.", abgeordnete.size(), abgeordneterId, COLLECTION_ABGEORDNETER);
		if (abgeordnete.size() > 0) {
			return abgeordnete.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Speichert neue Abgeordnete in der Datenbank
	 * 
	 * @param abgeordnete - Liste mit den zu speichernden Abgeordneten
	 */
	@Override
	public void createAbgeordnete(List<Abgeordneter> abgeordnete) throws BundestagException {
		abgeordneterCollection.insertMany(abgeordnete.stream().map(abgeordneter -> (AbgeordneterImpl)abgeordneter).collect(Collectors.toList()));
		LOGGER.info("{} Abgeordnete in Collection {} eingefügt.", abgeordnete.size(), COLLECTION_ABGEORDNETER);
	}

	/**
	 * Speichert einen neuen Abgeordneten in der Datenbank
	 * 
	 * @param abgeordneter - zu speichernder Abgeordneter
	 */
    public void createAbgeordneter(AbgeordneterImpl abgeordneter) throws BundestagException {
        abgeordneterCollection.insertOne((AbgeordneterImpl)abgeordneter);
        LOGGER.info("Abgeordneter {} in Collection {} eingefügt.", abgeordneter, COLLECTION_ABGEORDNETER);
    }

    /**
     * Aktualisiert einen Abgeordneten in der Datenbank, falls dieser nicht vorhanden ist wird er NICHT angelegt.
     * 
     * @param abgeordneter - zu aktualisierender Abgeordneter
     * 
     * @return - Anzahl der aktualisierten Abgeordneten, 0 falls der Abgeordnete noch nicht in der Datenban existiert
     */
	public long updateAbgeordneter(Abgeordneter abgeordneter) {
		if (abgeordneter != null) {
			Bson query = Filters.eq("abgeordneterId", abgeordneter.getID().toString());
			AbgeordneterImpl updatedAbgeordneter = (AbgeordneterImpl)abgeordneter;
			ReplaceOptions replaceOptions = new ReplaceOptions().upsert(false);
			UpdateResult result = abgeordneterCollection.replaceOne(query, updatedAbgeordneter, replaceOptions);
			LOGGER.info("{} Abgeordneter mit Id {} aktualisiert.", result.getModifiedCount(), abgeordneter.getID().toString());
			return result.getModifiedCount();
		}
		return 0;
	}

    /**
     * Löscht einen Abgeordneten in der Datenbank
     * 
     * @param abgeordneter - zu löschender Abgeordneter
     * 
     * @return - Anzahl der gelöschten Abgeordneten, 0 falls der Abgeordnete nicht in der Datenban existiert
     */
    public long deleteAbgeordneter(String abgeordneterId) {
        Bson query = Filters.eq("abgeordneterId", abgeordneterId);
        long count = abgeordneterCollection.deleteOne(query).getDeletedCount();
        LOGGER.info("{} Abgeordneter mit Id {} aus Collection {} gelöscht", count, abgeordneterId, COLLECTION_ABGEORDNETER);
        return count;
    }

	/**
	 * Liest alle Reden aus der Datenbank und liefert sie als Liste zurück
	 * 
	 * @return - Liste aller gespeicherter Reden
	 */
	@Override
	public List<Rede> readReden() {
		List<Rede> reden = redeCollection.find().into(new ArrayList<>());
		LOGGER.info("{} Reden aus der Collection {} geladen.", reden.size(), COLLECTION_REDE);
		return reden;
	}
	
	/**
	 * Liest alle Reden eines Abgeordneten aus der Datenbank und liefert sie als Liste zurück
	 * 
	 * @param rednerId - Redner dessen Reden gelesen werden sollen
	 * 
	 * @return - Liste aller Reden des Redner
	 */
	public List<Rede> readRedenOfAbgeordneter(String rednerId) {
		Document query = new Document();
		query.append("rednerId", rednerId);
		List<Rede> reden = redeCollection.find(query).into(new ArrayList<>());
		LOGGER.info("{} Reden aus der Collection {} für den Redner mit Id {} geladen.", reden.size(), COLLECTION_REDE, rednerId);
		return reden;
	}
	
	/**
	 * Liest die Reden anhand einer Id aus der Datenbank und liefert sie zurück
	 * 
	 * @param redeId - Id der Rede die gelesen werden sollen
	 * 
	 * @return - Rede mit der gesuchten Id
	 */
	public Rede readRede(String redeId) {
		Document query = new Document();
		query.append("redeId", redeId);
		List<Rede> reden = redeCollection.find(query).into(new ArrayList<Rede>());
		LOGGER.info("{} Rede mit Rede-Id {} aus der Collection {} geladen.", reden.size(), redeId, COLLECTION_REDE);
		if (reden.size() > 0) {
			return reden.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Liest alle Reden einer Sitzung einer Wahlperiode aus der Datenbank und liefert sie als Liste zurück
	 * 
	 * @param wahlperiode - gesuchte Wahlperiode
	 * @param sitzungsNummer - gesuchte Sitzung
	 * 
	 * @return - Liste aller Reden in der gesuchten Sitzung der gesuchten Wahlperiode
	 */
    public List<Rede> readReden(Integer wahlperiode, Integer sitzungsNummer) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("sitzungNummer", sitzungsNummer));
		List<Rede> reden = redeCollection.find(query).into(new ArrayList<Rede>());
        LOGGER.info("{} Reden für die Sitzung {} in der Wahlperiode {} in Collection {} gefunden", reden.size(), sitzungsNummer, wahlperiode, COLLECTION_REDE);
        return reden;
    }

    /**
     * Speichert neue Reden in der Datenbank
     * 
     * @param reden - Liste der Reden die in der Datenbank gespeichert werden sollen
     */
	@Override
	public void createReden(List<Rede> reden) throws BundestagException {
		redeCollection.insertMany(reden.stream().map(rede -> (RedeImpl)rede).collect(Collectors.toList()));
		LOGGER.info("{} Reden in Collection {} eingefügt.", reden.size(), COLLECTION_REDE);
	}

    /**
     * Speichert eine Rede in der Datenbank
     * 
     * @param rede - Rede die in der Datenbank gespeichert werden soll
     */
    public void createRede(Rede rede) throws BundestagException {
        redeCollection.insertOne((RedeImpl)rede);
        LOGGER.info("Rede {} in Collection {} eingefügt.", rede, COLLECTION_REDE);
    }

    /**
     * Aktualisiert eine Rede in der Datenbank, falls diese nicht vorhanden ist wird sie NICHT angelegt.
     * 
     * @param rede - Rede die in der Datenbank aktualisiert werden soll
     */
	public void updateRedeNlpResults(Rede rede) {
		Document filter = new Document("redeId", rede.getID().toString());
		// Update sentences, tokens, sentiments, pos, dependencies, namedEntities
		Bson update = Updates.combine(Updates.set("sentences", rede.getSentences()),
				Updates.set("tokens", rede.getTokens()), Updates.set("sentiments", rede.getSentiments()),
				Updates.set("pos", rede.getPos()), Updates.set("dependencies", rede.getDependencies()),
				Updates.set("namedEntities", rede.getNamedEntities()));
		// Wenn es die Rede nicht gibt wird auch nichts eingefügt
		UpdateOptions options = new UpdateOptions().upsert(false);
		// Dokument in der MongoDB aktualisieren
		redeCollection.updateOne(filter, update, options);
		LOGGER.info("NLP-Daten für die Rede mit Id {} in Collection {} aktualisiert.", rede.getID(), COLLECTION_REDE);
	}

    /**
     * Aktualisiert eine Rede in der Datenbank, falls diese nicht vorhanden ist wird sie NICHT angelegt.
     * 
     * @param rede - Rede die in der Datenbank aktualisiert werden soll
     * 
     * @return Gibt die Anzahl der aktualisierten Reden zurück
     */
	public long updateRede(Rede rede) {
		if (rede != null) {
			Bson query = Filters.eq("redeId", rede.getID().toString());
			RedeImpl updatedRede = (RedeImpl)rede;
			ReplaceOptions replaceOptions = new ReplaceOptions().upsert(false);
			UpdateResult result = redeCollection.replaceOne(query, updatedRede, replaceOptions);
			LOGGER.info("{} Rede mit Id {} in Collection {} aktualisiert.", result.getModifiedCount(), rede.getID().toString(), COLLECTION_REDE);
			return result.getModifiedCount();
		}
		return 0;
	}

    /**
     * Löscht eine Rede aus der Datenbank anhand ihrer Id
     * 
     * @param rede - Rede die in der Datenbank gelöscht werden soll
     * 
     * @return Gibt die Anzahl der gelöschten Reden zurück
     */
    public long deleteRede(String redeId) {
        Bson query = Filters.eq("redeId", redeId);
        long count = redeCollection.deleteOne(query).getDeletedCount();
        LOGGER.info("{} Rede mit Id {} aus Collection {} gelöscht", count, redeId, COLLECTION_REDE);
        return count;
    }

    /**
     * Löscht alle Reden aus der Datenbank mit der angegebenen Wahlperiode und Sitzungsnummer
     * 
     * @param wahlperiode - wahlperiode
     * @param sitzungsNummer - sitzungsnummer
     * 
     * @return Gibt die Anzahl der gelöschten Reden zurück
     */
    public long deleteReden(Integer wahlperiode, Integer sitzungsNummer) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("sitzungNummer", sitzungsNummer));
        long count = redeCollection.deleteMany(query).getDeletedCount();
        LOGGER.info("{} Reden für die Sitzung {} in der Wahlperiode {} aus Collection {} gelöscht", count, sitzungsNummer, wahlperiode, COLLECTION_REDE);
        return count;
    }

    /**
     * Legt eine neue Sitzung in der Datenbank an
     * 
     * @param sitzung - neue Sitzung die in der Datenbank gespeichert werden soll
     * 
     * @throws BundestagException
     */
	public void createSitzung(Sitzung sitzung) throws BundestagException {
		sitzungCollection.insertOne((SitzungImpl)sitzung);
		LOGGER.info("Sitzung {} in Collection {} eingefügt.", sitzung, COLLECTION_SITZUNG);
	}

    /**
     * Legt neue Sitzungen in der Datenbank an
     * 
     * @param sitzungen - Liste mit Sitzungen die in der Datenbank gespeichert werden sollen
     * 
     * @throws BundestagException
     */
	@Override
	public void createSitzungen(List<Sitzung> sitzungen) throws BundestagException {
		sitzungCollection.insertMany(sitzungen.stream().map(sitzung -> (SitzungImpl)sitzung).collect(Collectors.toList()));
		LOGGER.info("{} Sitzungen in Collection {} eingefügt.", sitzungen.size(), COLLECTION_SITZUNG);
	}

    /**
     * Liest alle Sitzungen aus der Datenbank 
     * 
     * @return Liste mit allen Sitzungen aus der Datenbank 
     */
	@Override
	public List<Sitzung> readSitzungen() {
		List<Sitzung> sitzungen = sitzungCollection.find().into(new ArrayList<>());
		LOGGER.info("{} Sitzungen aus Collection {} geladen.", sitzungen.size(), COLLECTION_SITZUNG);
		return sitzungen;
	}

    /**
     * Liest alle Sitzungen einer Wahlperiode aus der Datenbank 
     * 
     * @param wahlperiode - gesuchte Wahlperiode
     * 
     * @return Liste mit allen Sitzungen der Wahlperiode aus der Datenbank 
     */
	public List<Sitzung> readSitzungen(Integer wahlperiode) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode));
		List<Sitzung> sitzungen = sitzungCollection.find(query).into(new ArrayList<>());
		LOGGER.info("{} Sitzungen(en) in Wahlperiode {} aus Collection {} geladen.", sitzungen.size(), wahlperiode, COLLECTION_SITZUNG);
		return sitzungen;
	}

    /**
     * Liest alle Sitzungen einer Wahlperiode und Sitzungsnummer aus der Datenbank 
     * 
     * @param wahlperiode - gesuchte Wahlperiode
     * @param nummer - gesuchte Sitzungsnummer
     * 
     * @return Liste mit allen Sitzungen der Wahlperiode und Sitzungsnummer aus der Datenbank 
     */
	public Sitzung readSitzung(Integer wahlperiode, Integer nummer) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("nummer", nummer));
		List<Sitzung> sitzungen = sitzungCollection.find(query).into(new ArrayList<>());
		LOGGER.info("{} Sitzung(en) in Wahlperiode {} und Nummer {} aus Collection {} geladen.", sitzungen.size(), wahlperiode, nummer, COLLECTION_SITZUNG);
		if (sitzungen.size() > 0) {
			return sitzungen.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Aktualisiert eine Sitzung mit den übergebenen Daten, fallls die Sitzung noch nicht existiert, wird sie NICHT angelegt.
	 * 
	 * @param sitzung - zu aktualisierende Sitzung
	 * 
	 * @return Anzahl der aktualisierten Sitzungen
	 */
	public long updateSitzung(Sitzung sitzung) {
		if (sitzung != null) {
			Bson query = Filters.and(Filters.eq("wahlperiode", sitzung.getWahlperiode()), Filters.eq("nummer", sitzung.getNummer()));
			SitzungImpl updatedSitzung = (SitzungImpl)sitzung;
			ReplaceOptions replaceOptions = new ReplaceOptions().upsert(false);
			UpdateResult result = sitzungCollection.replaceOne(query, updatedSitzung, replaceOptions);
			LOGGER.info("{} Sitzung(en) mit Wahlperiode {} und Nummer {} in Collection {} aktualisiert.", result.getModifiedCount(), sitzung.getWahlperiode(), sitzung.getNummer(), COLLECTION_SITZUNG);
			return result.getModifiedCount();
		}
		return 0;
	}

	/**
	 * Löscht alle Sitzungen der angebenene Wahlperiode und Sitzungsnummer
	 * 
	 * @param wahlperiode - gesuchte Wahlperiode
	 * @param nummer - gesuchte Sitzungsnummer
	 * 
	 * @return Anzhal der gelöschten Sitzungen
	 */
    public long deleteSitzung(Integer wahlperiode, Integer nummer) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("nummer", nummer));
        long count = sitzungCollection.deleteOne(query).getDeletedCount();
        LOGGER.info("{} Sitzung(en) in Wahlperiode {} und Nummer {} aus Collection {} gelöscht.", count, wahlperiode, nummer, COLLECTION_SITZUNG);
        return count;
    }

    /**
     * Legt eine neue Tagesordnung in der Datenbank an
     * 
     * @param tagesordnung - zu speichernde Tagesordnung
     * 
     * @throws BundestagException
     */
    public void createTagesordnung(Tagesordnung tagesordnung) throws BundestagException {
        tagesordnungCollection.insertOne((TagesordnungImpl) tagesordnung);
        LOGGER.info("Tagesordnung {} in Collection {} eingefügt.", tagesordnung, COLLECTION_TAGESORDNUNG);
    }

    /**
     * Legt mehrere neue Tagesordnungen in der Datenbank an
     * 
     * @param tagesordnungen - Liste der zu speichernden Tagesordnungen
     * 
     * @throws BundestagException
     */
	@Override
	public void createTagesordnungen(List<Tagesordnung> tagesordnungen) throws BundestagException {
		tagesordnungCollection.insertMany(tagesordnungen.stream().map(tagesordnung -> (TagesordnungImpl)tagesordnung).collect(Collectors.toList()));
		LOGGER.info("{} Tagesordnungen in Collection {} eingefügt.", tagesordnungen.size(), COLLECTION_TAGESORDNUNG);
	}

    /**
     * Liest alle Tagesordnungen in der Datenbank an
     * 
     * @return Liste aller Tagesordnungen
     * 
     * @throws BundestagException
     */
	@Override
	public List<Tagesordnung> readTagesordnungen() throws BundestagFileException {
		List<Tagesordnung> tagesordnungen = tagesordnungCollection.find().into(new ArrayList<>());
		LOGGER.info("{} Tagesordnungen aus Collection {} geladen.", tagesordnungen.size(), COLLECTION_TAGESORDNUNG);
		return tagesordnungen;
	}

    /**
     * Liest alle Tagesordnungen der angegebenen Wahlperiode und Sitzungsnummer aus der Datenbank
     * 
     * @param wahlperiode - gesuchte Wahlperiode
     * @param sitzungNummer - gesuchte Sitzungsnummer
     * 
     * @return Liste aller Tagesordnungen der angegebenen Wahlperiode und Sitzungsnummer
     */
	public List<Tagesordnung> readTagesordnungen(Integer wahlperiode, Integer sitzungNummer) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("sitzungNummer", sitzungNummer));
		List<Tagesordnung> tagesordnungen = tagesordnungCollection.find(query).into(new ArrayList<>());
		LOGGER.info("{} Tagesordnungen aus Collection {} für die Wahlperiode {} und Sitzungnummer {} geladen.", tagesordnungen.size(), COLLECTION_TAGESORDNUNG, wahlperiode, sitzungNummer);
		return tagesordnungen;
	}

    /**
     * Liest eine Tagesordnung der angegebenen Wahlperiode und Sitzungsnummer mit Id aus der Datenbank
     * 
     * @param wahlperiode - gesuchte Wahlperiode
     * @param sitzungNummer - gesuchte Sitzungsnummer
     * @param topId - gesuchte Tagesordnungs Id
     * 
     * @return Tagesordnung der angegebenen Wahlperiode und Sitzungsnummer mit der angegebenen Id
     */
	public Tagesordnung readTagesordnung(Integer wahlperiode, Integer sitzungNummer, String topId) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("sitzungNummer", sitzungNummer), Filters.eq("topId", topId));
		List<Tagesordnung> tagesordnungen = tagesordnungCollection.find(query).into(new ArrayList<>());
		LOGGER.info("{} Tagesordnung aus Collection {} für die Wahlperiode {}, Sitzungnummer {} und Top-Id {} geladen.", tagesordnungen.size(), COLLECTION_TAGESORDNUNG, wahlperiode, sitzungNummer, topId);
		if (tagesordnungen.size() > 0) {
			return tagesordnungen.get(0);
		} else {
			return null;
		}
	}

    /**
     * Aktualisiert eine Tagesordnung mit der angegebenen Tagesordnung in der Datenbank, existiert sie noch nicht wird sie NICHT angelegt
     * 
     * @param tagesordnung - Tagesordnung die aktualisiert werden soll
     * 
     * @return Anzahl der aktualisierten Tagesordnungen
     */
    public long updateTagesordnung(Tagesordnung tagesordnung) {
        if (tagesordnung != null) {
            Bson query = Filters.and(Filters.eq("wahlperiode", tagesordnung.getWahlperiode()), Filters.eq("sitzungNummer", tagesordnung.getSitzungNummer()), Filters.eq("topId", tagesordnung.getTopId()));
            TagesordnungImpl updatedTagesordnung = (TagesordnungImpl)tagesordnung;
            ReplaceOptions replaceOptions = new ReplaceOptions().upsert(false);
            UpdateResult result = tagesordnungCollection.replaceOne(query, updatedTagesordnung, replaceOptions);
            LOGGER.info("{} Tagesordnung(en) für Wahlperiode {}, Sitzungnummer {} und TopId {} in Collection {} aktualisiert.", result.getModifiedCount(), tagesordnung.getWahlperiode(), tagesordnung.getSitzungNummer(), tagesordnung.getTopId(), COLLECTION_TAGESORDNUNG);
            return result.getModifiedCount();
        }
        return 0;
    }

    /**
     * Löscht alle Tagesordnungen der angegebenen Wahlperiode und Sitzungsnummer aus der Datenbank
     * 
     * @param wahlperiode - gesuchte Wahlperiode
     * @param sitzungNummer - gesuchte Sitzungsnummer
     * 
     * @return Anzahl der Tagesordnungen die gelöscht wurden
     */
    public long deleteTagesordnung(Integer wahlperiode, Integer sitzungNummer) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("sitzungNummer", sitzungNummer));
        long count = tagesordnungCollection.deleteMany(query).getDeletedCount();
        LOGGER.info("{} Tagesordnung(en) mit Wahlperiode {} und Sitzungnummer {} aus Collection {} gelöscht.", count, wahlperiode, sitzungNummer, COLLECTION_TAGESORDNUNG);
        return count;
    }

    /**
     * Löscht eine Tagesordnung der angegebenen Wahlperiode und Sitzungsnummer mit Id aus der Datenbank
     * 
     * @param wahlperiode - gesuchte Wahlperiode
     * @param sitzungNummer - gesuchte Sitzungsnummer
     * @param topId - gesuchte Tagesordnungs Id
     * 
     * @return Anzahl der Tagesordnungen die gelöscht wurden
     */
    public long deleteTagesordnung(Integer wahlperiode, Integer sitzungNummer, String topId) {
		Bson query = Filters.and(Filters.eq("wahlperiode", wahlperiode), Filters.eq("sitzungNummer", sitzungNummer), Filters.eq("topId", topId));
        long count = tagesordnungCollection.deleteOne(query).getDeletedCount();
        LOGGER.info("{} Tagesordnung(en) mit Wahlperiode {}, Sitzungnummer {} und Top-Id {} aus Collection {} gelöscht.", count, wahlperiode, sitzungNummer, topId, COLLECTION_TAGESORDNUNG);
        return count;
    }

    /**
     * Aktualisiert einen Benutzer mit den angebenen Daten, existiert der Benutzer nicht wird er NICHT abgelegt
     * 
     * @param benutzer - Daten des Benutzers
     *  
     * @return Anzahl der aktualisierten Benutzer
     */
    public long updateBenutzer(Benutzer benutzer) {
        Bson query = Filters.eq("benutzerId", benutzer.getBenutzerId());
        ReplaceOptions replaceOptions = new ReplaceOptions().upsert(false);
        UpdateResult result = benutzerCollection.replaceOne(query, benutzer, replaceOptions);
        LOGGER.info("{} Benutzer mit Id {} in Collection {} aktualisiert.", result.getModifiedCount(), benutzer.getBenutzerId(), COLLECTION_BENUTZER);
        return result.getModifiedCount();
    }

    /**
     * Legt einen neuen Benutzer in der Datenbank an
     * 
     * @param benutzer - neuer Benutzer
     */
    public void createBenutzer(Benutzer benutzer) {
        benutzer.setBenutzerId(UUID.randomUUID().toString());
        benutzerCollection.insertOne(benutzer);
        LOGGER.info("Benutzer {} in Collection {} eingefügt.", benutzer, COLLECTION_BENUTZER);
    }

    /**
     * Liest den Benutzer mit der angegebenen Id aus der Datenbank
     * 
     * @param benutzerId - Id des gesuchten Benutzers
     * 
     * @return Benutzer mit der gesuchten Id 
     */
    public Benutzer readBenutzer(String benutzerId) {
        Bson query = Filters.eq("benutzerId", benutzerId);
        List<Benutzer> benutzer = benutzerCollection.find(query).into(new ArrayList<>());
        LOGGER.info("{} Benutzer mit Id {} aus Collection {} geladen.", benutzer.size(), benutzerId, COLLECTION_BENUTZER);
        if (!benutzer.isEmpty()) {
            return benutzer.get(0);
        } else {
            return null;
        }
    }

    /**
     * Liest alle Benutzer aus der Datenbank
     * 
     * @return Liste aller Benutzer in der Datenbank
     */
    public List<Benutzer> readBenutzer() {
        List<Benutzer> benutzer = benutzerCollection.find().into(new ArrayList<>());
        LOGGER.info("{} Benutzer aus Collection {} geladen.", benutzer.size(), COLLECTION_BENUTZER);
        return benutzer;
    }

    /**
     * Löscht einen Benutzer anhand der Id aus der Datenbank
     * 
     * @param benutzerId - Id des Benutzers der gelöscht werden soll
     * 
     * @return Anzahl der gelöschten Benutzer
     */
    public long deleteBenutzer(String benutzerId) {
        Bson query = Filters.eq("benutzerId", benutzerId);
        long count = benutzerCollection.deleteOne(query).getDeletedCount();
        LOGGER.info("{} Benutzer aus Collection {} gelöscht", count, COLLECTION_BENUTZER);
        return count;
    }

    /**
     * Legt eine neue Benutzergruppe in der Datenbank an
     * 
     * @param gruppe - Benutzergruppe die neu angegelgt werden soll
     */
    public void createBenutzerGruppe(BenutzerGruppe gruppe) {
        gruppe.setGruppeId(UUID.randomUUID().toString());
        benutzerGruppeCollection.insertOne(gruppe);
        LOGGER.info("BenutzerGruppe {} in Collection {} eingefügt.", gruppe, COLLECTION_BENUTZERGRUPPE);
    }

    /**
     * Aktualisiert eine Benutzergruppe mit den angegebenen Werten, ist die Benutzergruppe nicht vorhanden wird sie NICHT angelegt.
     * 
     * @param gruppe - Benutzergruppe die aktualisiert werden soll
     * 
     * @return Anzahl der aktualisierten Benutzergruppen
     */
    public long updateBenutzerGruppe(BenutzerGruppe gruppe) {
        Bson query = Filters.eq("gruppeId", gruppe.getGruppeId());
        ReplaceOptions replaceOptions = new ReplaceOptions().upsert(false);
        UpdateResult result = benutzerGruppeCollection.replaceOne(query, gruppe, replaceOptions);
        LOGGER.info("{} BenutzerGruppe(n) mit Id {} in Collection {} aktualisiert.", result.getModifiedCount(), gruppe.getGruppeId(), COLLECTION_BENUTZER);
        return result.getModifiedCount();
    }

    /**
     * Liest eine Benutzergruppe mit der angebenen Id aus der Datenbank
     * 
     * @param gruppeId - Id der Benutzergruppe die gelesen werden soll
     * 
     * @return Benutzergruppe mit der gesuchten Id
     */
    public BenutzerGruppe readBenutzerGruppe(String gruppeId) {
        Bson query = Filters.eq("gruppeId", gruppeId);
        List<BenutzerGruppe> gruppen = benutzerGruppeCollection.find(query).into(new ArrayList<>());
        LOGGER.info("{} BenutzerGruppe(n) mit Id {} aus Collection {} geladen.", gruppen.size(), gruppeId, COLLECTION_BENUTZERGRUPPE);
        if (!gruppen.isEmpty()) {
            return gruppen.get(0);
        } else {
            return null;
        }
    }

    /**
     * Liest alle Benutzergruppen aus der Datenbank
     * 
     * @return Liste aller Benutzergruppen
     */
    public List<BenutzerGruppe> readBenutzerGruppen() {
        List<BenutzerGruppe> gruppen = benutzerGruppeCollection.find().into(new ArrayList<>());
        LOGGER.info("{} BenutzerGruppe(n) aus Collection {} geladen.", gruppen.size(), COLLECTION_BENUTZERGRUPPE);
        return gruppen;
    }

    /**
     * Löscht eine Benutzergruppe anhand ihrer Id aus der Datenbank
     * 
     * @param gruppeId - Id der Benutzergruppe die gelöscht werden soll
     * 
     * @return Anzahl der gelöschten Benutzergruppen
     */
    public long deleteBenutzerGruppe(String gruppeId) {
        Bson query = Filters.eq("gruppeId", gruppeId);
        long count = benutzerGruppeCollection.deleteOne(query).getDeletedCount();
        LOGGER.info("{} BenutzerGruppe(n) mit Id {} aus Collection {} gelöscht", count, gruppeId, COLLECTION_BENUTZERGRUPPE);
        return count;
    }

    /**
     * VORSICHT: Setzt die Datenbank vollständig zurück, löscht den Inhalt aller Tabellen mit Bundestags-Daten
     */
    public void reset() {
    	abgeordneterCollection.drop();
    	sitzungCollection.drop();
    	redeCollection.drop();
    	tagesordnungCollection.drop();
    }
    
    private ServerAddress mongoServer() {
        return new ServerAddress(configuration.getRemoteHost(), configuration.getRemotePort());
    }
    
    private MongoCredential mongoCredentials() {
        return MongoCredential.createScramSha1Credential(configuration.getRemoteUser(), configuration.getRemoteDatabase(), configuration.getRemotePassword());
    }
    
    private MongoClientOptions mongoClientOptions() {
        ClassModel<Gruppe> gruppeModel = ClassModel.builder(Gruppe.class).enableDiscriminator(true).build();
        ClassModel<GruppeImpl> gruppeImplModel = ClassModel.builder(GruppeImpl.class).enableDiscriminator(true).build();
        ClassModel<Mandat> mandatModel = ClassModel.builder(Mandat.class).enableDiscriminator(true).build();
        ClassModel<MandatImpl> mandatImplModel = ClassModel.builder(MandatImpl.class).enableDiscriminator(true).build();
        ClassModel<Mitgliedschaft> mitgliedschaftModel = ClassModel.builder(Mitgliedschaft.class).enableDiscriminator(true).build();
        ClassModel<MitgliedschaftImpl> mitgliedschaftImplModel = ClassModel.builder(MitgliedschaftImpl.class).enableDiscriminator(true).build();
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().register(gruppeModel, gruppeImplModel, mandatModel, mandatImplModel, mitgliedschaftModel, mitgliedschaftImplModel).automatic(true).build());
        CodecRegistry mongoCodecsRegistry = CodecRegistries.fromCodecs(new ObjectCodec());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry, mongoCodecsRegistry);
        return MongoClientOptions.builder().codecRegistry(codecRegistry).build();
    }
    
    /**
     * Liefert die Namen aller Collections (Tabellen) der Datenbank
     * 
     * @return Liste mit den Namen der Collections der Datenbank
     */
    public List<String> getCollections() {
    	List<String> collections = new ArrayList<>();
    	for (String collection : mongoDatabase.listCollectionNames()) {
			collections.add(collection);
		}
    	return collections;
    }
    
    /**
     * Liefert alle Dokumente einer Collection aus der Datenbank
     * 
     * @param collectionName - Name der Collection aus der gelesen werden soll
     * 
     * @return Alle Dokumente in der angegebenen Collection
     */
    public MongoCollection<Document> getCollection(String collectionName) {
    	return mongoDatabase.getCollection(collectionName);
    }
    
    /**
     * Schließt die Datenbankverbindung
     */
    public void close() {
        mongoClient.close();
    }

    /**
     * Zählt die Dokumente in einer Collection
     * 
     * @param collection - Name der Collection deren Elemente gezählt werden sollen
     * 
     * @return Anzahl der Dokumente in der gesuchten Collection
     */
    public long count(String collection) {
        return mongoDatabase.getCollection(collection).countDocuments();
    }
}

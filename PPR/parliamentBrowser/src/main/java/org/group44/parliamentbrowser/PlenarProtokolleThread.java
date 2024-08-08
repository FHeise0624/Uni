package org.group44.parliamentbrowser;

import org.group44.parliamentbrowser.bundestag.PlenarProtokollHandler;
import org.group44.parliamentbrowser.bundestag.PlenarProtokollLoader;
import org.group44.parliamentbrowser.database.MongoDBConnectionHandler;
import org.group44.parliamentbrowser.nlp_analyse.NLP;
import org.group44.parliamentbrowser.types.Rede;
import org.group44.parliamentbrowser.types.Sitzung;
import org.group44.parliamentbrowser.types.Tagesordnung;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Die Klasse dient dazu, Plenarprotokolle zu importieren und ermöglicht die Fortschrittsanzeige
 * 
 * @author Cora
 */
public class PlenarProtokolleThread implements Runnable {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlenarProtokolleThread.class);
    protected static final int PROGRESS_MAX = 500;
    private static MongoDBConnectionHandler mongoDb = MongoDBConnectionHandler.getInstance();
    private static PlenarProtokollHandler plenarProtokollHandler = new PlenarProtokollHandler();

    protected int progress = 0;

    /**
     * Führt den Import der Plenarprotokolle aus. Dabei werden die Protokolle von der Seite des Bundestags 
     * gelesen, die Objekte Sitzung, Tagesordnung und Rede erzeugt. Anschließend erfolgt die NLP Analyse 
     * (sofern dies konfiguriert ist, siehe isNlpActive in der Configuration) und speichert di eerzeugten
     * Objekte in der Datenbank.
     */
    @Override
    public void run() {
    	int protokolleImporiert = 0;
        int countExistingSitzungen = 0;
        PlenarProtokollLoader plenarProtokollLoader = new PlenarProtokollLoader();
        while (plenarProtokollLoader.hasNext() && countExistingSitzungen < 5) {
            Document xmlPlenarProtokoll = plenarProtokollLoader.next();
            if (plenarProtokollHandler.isNew(xmlPlenarProtokoll)) {
                Sitzung sitzung = plenarProtokollHandler.getSitzung();
                mongoDb.createSitzung(sitzung);

                List<Tagesordnung> tagesordnungen = plenarProtokollHandler.getTagesordnungen();
                mongoDb.createTagesordnungen(tagesordnungen);

                List<Rede> reden = plenarProtokollHandler.getReden();
                if (Configuration.getInstance().isNlpActive()) {
                	try {
						new NLP().doNlp(reden);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } else {
                	LOGGER.warn("Überspringe NLP Analyse");
                }
                mongoDb.createReden(reden);
                protokolleImporiert += 1;
                LOGGER.info("Protokoll der Sitzung {} ist neu und wurde in die Datenbank eingefügt", sitzung.getNummer());
            } else {
                countExistingSitzungen += 1;
                LOGGER.info("Protokoll existiert schon");
            }
            progress += 1;
        }
        LOGGER.info("{} Protokolle geladen und {} neue Protokolle in die MongoDB importiert", progress, protokolleImporiert);
        progress = PROGRESS_MAX;
    }

    /**
     * Gibt den Fortschritt zurück
     *
     * @return Fortschritt als String
     */
    public String getProgress() {
        StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder.append("\"progress\":\"").append(progress).append("\", ");
        stringBuilder.append("\"progressMax\":\"").append(PROGRESS_MAX).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

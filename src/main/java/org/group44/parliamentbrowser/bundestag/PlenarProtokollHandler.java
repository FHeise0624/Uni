package org.group44.parliamentbrowser.bundestag;

import java.util.ArrayList;
import java.util.List;

import org.group44.parliamentbrowser.builder.RedeBuilder;
import org.group44.parliamentbrowser.builder.SitzungBuilder;
import org.group44.parliamentbrowser.builder.TagesordnungBuilder;
import org.group44.parliamentbrowser.database.MongoDBConnectionHandler;
import org.group44.parliamentbrowser.types.Abgeordneter;
import org.group44.parliamentbrowser.types.Rede;
import org.group44.parliamentbrowser.types.Sitzung;
import org.group44.parliamentbrowser.types.Tagesordnung;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Die Klasse behandelt ein XML Plenarprotokoll (Prüfung ob das Protokoll bereits in der Datenbank vorhanden ist und Einzelteile extrahieren)
 * 
 *  @author Cora
 */
public class PlenarProtokollHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlenarProtokollHandler.class);

	private MongoDBConnectionHandler mongoDB = null;
	private List<Abgeordneter> abgeordnete = new ArrayList<>();
	private Sitzung sitzung = null;
	private List<Tagesordnung> tagesordnungen = new ArrayList<>();
	private List<Rede> reden = new ArrayList<>();
	
	/**
	 * Erzeugt den Handler
	 */
	public PlenarProtokollHandler() {
		mongoDB = MongoDBConnectionHandler.getInstance();
		abgeordnete = mongoDB.readAbgeordnete();
	}
	
	/**
	 * Prüft ob das Plenarprotokoll mit seinen Daten bereits in der Datenbank vorhanden ist. Falls nicht werden 
	 * die Bestandteile des Plenarprotokolls extrahiert und im Objekt gespeichert
	 * 
	 * @param xmlPlenarProtokoll - Plenarprotokoll das geladen wurde und geprüft werden soll
	 * 
	 * @return true, falls das Plenarprotokoll noch nicht in der Datenbank vorhanden ist, sonst false
	 */
	public boolean isNew(Document xmlPlenarProtokoll) {
		this.sitzung = new SitzungBuilder().from(xmlPlenarProtokoll);
		this.tagesordnungen = new ArrayList<>();
		this.reden = new ArrayList<>();
		if (mongoDB.readSitzung(sitzung.getWahlperiode(), sitzung.getNummer()) != null) {
			LOGGER.info("Sitzung mit Nummer {} in Wahlperiode {} existiert bereits!", sitzung.getNummer(), sitzung.getWahlperiode());
			return false;
		}
		LOGGER.info("Neue Sitzung mit Nummer {} in Wahlperiode {} gefunden!", sitzung.getNummer(), sitzung.getWahlperiode());
		this.tagesordnungen = new TagesordnungBuilder().from(xmlPlenarProtokoll);
		LOGGER.info("{} Tagesordnungen erzeugt.", tagesordnungen.size());
		this.reden = new RedeBuilder(abgeordnete).from(xmlPlenarProtokoll);
		LOGGER.info("{} Reden erzeugt, {} von diesen sind fehlerhaft (Tagesordnung oder Abgeordneter existiert nicht).", reden.size(), RedeBuilder.FAULTY_REDEN_IDS.size());
		return true;
	}

	/**
	 * Liefert die Sitzung des behandelten Plenarprotokolls
	 * 
	 * @return Sitzung
	 */
	public Sitzung getSitzung() {
		return sitzung;
	}


	/**
	 * Liefert die Tagesordnungen des behandelten Plenarprotokolls
	 * 
	 * @return Liste von Tagesordnungen
	 */
	public List<Tagesordnung> getTagesordnungen() {
		return tagesordnungen;
	}


	/**
	 * Liefert die Reden des behandelten Plenarprotokolls
	 * 
	 * @return Liste von Reden
	 */
	public List<Rede> getReden() {
		return reden;
	}
	
}

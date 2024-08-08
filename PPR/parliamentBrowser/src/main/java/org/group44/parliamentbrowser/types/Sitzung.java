package org.group44.parliamentbrowser.types;

import java.sql.Date;

import org.group44.parliamentbrowser.exception.BundestagJsonException;

/**
 * Enthält die Informationen der Sitzungen
 *
 * @author Cora, Dominik
 */
public interface Sitzung extends BundestagObject {

    /**
     * Gibt die Nummer der Sitzung zurück
     *
     * @return nummer
     */
	public Integer getNummer();

    /**
     * Gibt den Ort zurück
     *
     * @return ort als String
     */
	public String getOrt();

    /**
     * Gibt den Zeitstempel des Starts der Sitzung zurück
     *
     * @return Zeitstempel
     */
	public Date getBeginn();

    /**
     * Gibt den Zeitstempel des Endes der Sitzung zurück
     *
     * @return Zeitstempel
     */
	public Date getEnde();

    /**
     * Gibt die Dauer der Sitzung in Minuten zurück
     *
     * @return dauer
     */
	public Integer getDauerInMinuten();

    /**
     * Gibt die Wahlperiode zurück
     *
     * @return wahlperiode
     */
	public Integer getWahlperiode();

    /**
     * Schreibt das Objekt Sitzung als Json weg
     *
     * @return Sitzung as Json string
     *
     * @throws BundestagJsonException -
     */
	public String toJson() throws BundestagJsonException;

    /**
     * Für Menschen schöner zu lesen, wird nicht gebraucht
     *
     * @return Sitzung as Json
     *
     * @throws BundestagJsonException -
     */
	public String toJsonPretty() throws BundestagJsonException;

    /**
     * Erstellt String in Latex Format
     * @return string
     * @author Dominik
     */
    public String toTex();
}

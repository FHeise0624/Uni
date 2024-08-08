package org.group44.parliamentbrowser.types;

import java.util.List;

import org.group44.parliamentbrowser.exception.BundestagJsonException;

/**
 * Enthält die Informationen der Tagesordnungen
 *
 * @author Cora, Dominik
 */
public interface Tagesordnung extends BundestagObject {

    /**
     * Gibt die Wahlperiode zurück
     *
     * @return wahlperiode
     */
	public Integer getWahlperiode();

    /**
     * Gibt die Sitzungsnummer zurück
     *
     * @return sitzungsnummer
     */
	public Integer getSitzungNummer();

    /**
     * Gibt die TopId zurück
     *
     * @return TopId
     */
	public String getTopId();

    /**
     * Gibt den Text zurück
     *
     * @return text
     */
    public String getText();

    /**
     * Gibt die RedenIds zurück
     *
     * @return redenIds als Liste
     */
    public List<String> getRedenIds();

    /**
     * Schreibt das Objekt Tagesordnung als Json weg
     *
     * @return Tagesordnung as Json string
     *
     * @throws BundestagJsonException -
     */

    public String toJson() throws BundestagJsonException;

    /**
     * Für Menschen schöner zu lesen, wird nicht gebraucht
     *
     * @return Tagesordnung as Json
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

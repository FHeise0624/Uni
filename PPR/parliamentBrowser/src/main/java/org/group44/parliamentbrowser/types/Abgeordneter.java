package org.group44.parliamentbrowser.types;

import java.sql.Date;
import java.util.List;

import org.group44.parliamentbrowser.exception.BundestagJsonException;
/**
 * Enthält die Informationen über die Abgeordneten des Bundestags
 *
 * @author Cora
 */
public interface Abgeordneter extends BundestagObject {

    /**
     * Gibt einen Namen des Abgeordneten zurück
     *
     * @return Namen des Abgeordneten
     */
	public String getName();

    /**
     * Gibt einen Vornamen des Abgeordneten zurück
     *
     * @return Vornamen des Abgeordneten
     */
	public String getVorname();

    /**
     * Gibt den Ortszusatz des Abgeordneten zurück
     *
     * @return Ortszusatz des Abgeordneten
     */
	public String getOrtszusatz();

    /**
     * Gibt den Adelssuffix des Abgeordneten zurück
     *
     * @return Adelssuffix des Abgeordneten
     */
	public String getAdelssuffix();

    /**
     * Gibt die Anrede des Abgeordneten zurück
     *
     * @return Anrede des Abgeordneten
     */
	public String getAnrede();

    /**
     * Gibt den Akadischen Titel des Abgeordneten zurück
     *
     * @return Akadischen Titel des Abgeordneten
     */
	public String getAkadTitel();

    /**
     * Gibt das Geburtsdatum des Abgeordneten zurück
     *
     * @return Geburtsdatum des Abgeordneten
     */
	public Date getGeburtsDatum();

    /**
     * Gibt den Geburtsort des Abgeordneten zurück
     *
     * @return Geburtsort des Abgeordneten
     */
	public String getGeburtsOrt();

    /**
     * Gibt das Sterbedatum des Abgeordneten zurück
     *
     * @return Sterbedatum des Abgeordneten
     */
	public Date getSterbeDatum() throws NullPointerException;

    /**
     * Gibt das Geschlecht des Abgeordneten zurück
     *
     * @return Geschlecht des Abgeordneten
     */
	public Types.GESCHLECHT getGeschlecht();

    /**
     * Gibt die Religion des Abgeordneten zurück
     *
     * @return Religion des Abgeordneten
     */
	public String getReligion();

    /**
     * Gibt den Beruf des Abgeordneten zurück
     *
     * @return Beruf des Abgeordneten
     */
	public String getBeruf();

    /**
     * Gibt den Lebenslauf des Abgeordneten zurück
     *
     * @return Lebenslauf des Abgeordneten
     */
	public String getVita() throws NullPointerException;

    /**
     * Gibt die Partei zurück
     *
     * @return Id der Partei, der der Abgeordnete angehört
     */
	public String getParteiId();

	/**
	 * Setzt die Partei, deren Mitglied der Abgeordnete ist
	 * 
	 * @param parteiId der Partei deren Mitglied der Abgeordnete ist
	 */
	public void setParteiId(String parteiId);
	
	public Gruppe getFraktion();
	
	/**
	 * Gibt die Mandate zurück, die der Abgeordnete hält
	 * 
	 * @return Mandate, die der Abgeordnete hält
	 */
	public List<Mandat> getMandate();

	/**
	 * Gibt das Mandat zurück, das der Abgeordnete in der Wahlperiode hält
	 * 
	 * @param wahlperiodeId Id der Wahlperiode deren Mandate für den Abgeordneten ermittelt
	 *                    werden sollen
	 * 
	 * @return Mandat, das der Abgeordnete in der Wahlperiode hält
	 */
	public Mandat getMandat(String wahlperiodeId);

	/**
	 * Fügt dem Abgeordneten ein Mandat hinzu
	 * 
	 * @param mandat Neues Mandat das der Abgeordnete hält
	 */
	public void addMandat(Mandat mandat);

	/**
	 * Gibt die Mitgliedschaften in Gruppen zurück, die der Abgeordnete besitzt
	 * 
	 * @return Mitgliedschaften in Gruppen, die der Abgeordnete besitzt
	 */
	public List<Mitgliedschaft> getMitgliedschaften();

	/**
	 * Gibt die Mitgliedschaften in Gruppen zurück, die der Abgeordnete in der
	 * Wahlperiode besitzt
	 * 
	 * @param wahlperiodeId Wahlperiode deren Mitgliedschaften für den Abgeordneten
	 *                    ermittelt werden sollen
	 * 
	 * @return Mitgliedschaften in Gruppen, die der Abgeordnete in der Wahlperiode
	 *         besitzt
	 */
	public List<Mitgliedschaft> getMitgliedschaften(String wahlperiodeId);

	/**
	 * Fügt dem Abgeordneten eine Mitgliedschaft hinzu
	 * 
	 * @param mitgliedschaft Neues Mitgliedschaft die der Abgeordnete besitzt
	 */
	public void addMitgliedschaft(Mitgliedschaft mitgliedschaft);

	/**
	 * Gibt die Ids der Reden, die ein Bundestagsabgeordneter gehalten hat, zurück
	 * 
	 * @return Liste der Ids der Reden des Bundestagsabgeordneten
	 */
	public List<String> getRedenIds();

	/**
	 * Fügt der Liste der Ids des Reden des Bundestagsabgeordneten die übergeben Rede-Id hinzu
	 *
	 * @param redeId Id der Rede die der Abgeordnete gehalten hat
	 */
	public void addRedeId(String redeId);

    /**
     * Schreibt das Objekt Abgeordneter als Json weg
     *
     * @return Abgeordneter as Json string
     *
     * @throws BundestagJsonException -
     */

	public String toJson() throws BundestagJsonException;

    /**
     * Für Menschen schöner zu lesen, wird nicht gebraucht
     *
     * @return Abgeordneter as Json
     *
     * @throws BundestagJsonException -
     */
	public String toJsonPretty() throws BundestagJsonException;

    /**
     * Fügt die Url eines Bildes zur Liste bildUrls hinzu
     *
     * @param bildURL
     *
     * @author dominik
     */

	public void addBildURL(String bildURL);

    /**
     * Gibt alle Bilder des Abgeordneten wieder
     *
     * @return Bilder vom Abgeordneten
     *
     * @author dominik
     */
	public List<String> getBilder();

    /**
     * Setzt die Bilder des Abgeordneten
     *
     * @param bilder - eine Liste von Urls zu den Bildern
     */
	public void setBilder(List<String> bilder);
}

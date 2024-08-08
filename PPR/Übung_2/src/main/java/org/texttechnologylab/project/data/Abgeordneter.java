package org.texttechnologylab.project.data;

import org.texttechnologylab.project.exception.BundestagException;

import java.sql.Date;
import java.util.Set;

/**
 * Interface für einen Abgeordneten
 *
 * @author Giuseppe Abrami
 */
public interface Abgeordneter extends BundestagObject {

    /**
     * Gib den Namen des Abgeordneten zurück
     * @return
     */
    String getName();

    /**
     * Gibt den Vornamen des Abgeordneten zurück
     * @return
     */
    String getVorname();

    /**
     * Gibt den Ortszusatz des Abgeordneten zurück
     * @return
     */
    String getOrtszusatz();

    /**
     * Gibt den Adelszusatz des Abgeordneten zurück
     * @return
     */
    String getAdelssuffix();

    /**
     * Gibt die Anrede des Abgeordeten zurück
     * @return
     */
    String getAnrede();

    /**
     * Gibt den akademischen Titel des Abgeordneten zurück
     * @return
     */
    String getAkadTitel();

    /**
     * Gibt das Geburtsdatum des Abgeordneten zurück
     * @return
     */
    Date getGeburtsDatum();

    /**
     * Gibt den Geburtsort des Abgeordneten zurück
     * @return
     */
    String getGeburtsOrt();

    /**
     * Gibt das Sterbedatum des Abgeordneten zurück
     * @return
     * @throws NullPointerException
     */
    Date getSterbeDatum() throws NullPointerException;

    /**
     * Gibt das Geschlecht des Abgeordneten zurück
     * @return
     */
    Types.GESCHLECHT getGeschlecht();

    /**
     * Gibt die Religionszugehörigkeit des Abgeordneten zurück
     * @return
     */
    String getReligion();

    /**
     * Gibt den Beruf des Abgeordneten zurück
     * @return
     */
    String getBeruf();

    /**
     * Gibt eine kurze Vita des Abgeordneten zurück
     * @return
     * @throws NullPointerException
     */
    String getVita() throws NullPointerException;

    /**
     * Listet alle Mandate des Abgeordneten auf
     * @return
     */
    Set<Mandat> listMandate();

    /**
     * Listet alle Mandata des Abgeordneten nach Wahlperiode auf.
     * @param pValue
     * @return
     */
    Set<Mandat> listMandate(Wahlperiode pValue);

    /**
     * Abfrage ob ein Abgeordneter in einer gegebenen Wahlperiode ein Mandat hatte.
     * @param pValue
     * @return
     */
    boolean hasMandat(Wahlperiode pValue);

    /**
     * Liste aller Mitgliedschaften des Abgeordneten
     * @return
     */
    Set<Mitgliedschaft> listMitgliedschaften();

    /**
     * Liste aller Mitgliedschaften des Abgeordneten, für eine Wahlperiode
     * @param pValue
     * @return
     */
    Set<Mitgliedschaft> listMitgliedschaften(Wahlperiode pValue);

    /**
     * Liste aller Abstimmungen des Abgeordneten
     * @return
     */
    Set<Abstimmung> listAbstimmungen();

    /**
     * Liste aller Abstimmungen des Abgeordneten für eine Wahlperiode
     * @param pValue
     * @return
     */
    Set<Abstimmung> listAbstimmungen(Wahlperiode pValue);

    /**
     * Liste aller Abstimmungen des Abgeordneten für eine Wahlperiode und zu einer spezifischen Abstimmungsart
     * @param pValue
     * @param pType
     * @return
     */
    Set<Abstimmung> listAbstimmungen(Wahlperiode pValue, Types.ABSTIMMUNG pType);

    /**
     * Rückgabe der Partei des Abgeordneten
     *
     * @return
     */
    Partei getPartei() throws BundestagException;
}

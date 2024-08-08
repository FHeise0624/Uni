package org.texttechnologylab.project.data;

import org.texttechnologylab.project.exception.BundestagException;

import java.sql.Date;
import java.util.Set;

/**
 * Interface für ein Mandat
 * @author Giuseppe Abrami
 */
public interface Mandat extends BundestagObject{

    /**
     * Gib den Abgeordneten des Mandates zurück
     *
     * @return
     */
    Abgeordneter getAbgeordneter();

    /**
     * Beginn des Mandates
     * @return
     */
    Date fromDate();

    /**
     * Ende des Mandates
     * @return
     */
    Date toDate();

    /**
     * Liste aller Fraktionen während des Mandates
     * @return
     */
    Set<Fraktion> getFraktionen();

    /**
     * Liste aller Ausschüsse während des Mandates
     * @return
     */
    Set<Ausschuss> listAusschuesse();

    /**
     * Liste aller Mitgliedschaften während eines Mandates
     * @return
     */
    Set<Mitgliedschaft> listMitgliedschaft();

    /**
     * Rückgabe des Mandattypes
     * @return
     */
    Types.MANDAT getTyp();

    /**
     * Wahlperiode des Mandates
     * @return
     */
    Wahlperiode getWahlperiode();

    /**
     * Wahlkreis des Mandates
     *
     * @return
     */
    Wahlkreis getWahlkreis() throws BundestagException;


}

package org.texttechnologylab.project.data;

import org.texttechnologylab.project.exception.BundestagException;

import java.sql.Date;

/**
 * Interface für eine Mitgliedschaft
 *
 * @author Giuseppe Abrami
 */
public interface Mitgliedschaft {

    /**
     * Rückgabe des Abgeordneten
     * @return
     */
    Abgeordneter getAbgeordneter();

    /**
     * Funktion in der Mitgliedschaft
     *
     * @return
     */
    String getFunktion() throws BundestagException;

    /**
     * Beginn der Mitgliedschaft
     *
     * @return
     */
    Date getFromDate();

    /**
     * Ende der Mitgliedschaft
     * @return
     */
    Date getToDate();

    /**
     * Mitgliedschaft noch Aktiv?
     * @return
     */
    boolean isActive();

    /**
     * Gruppe der Mitgliedschaft
     * @return
     */
    Gruppe getGruppe();

    /**
     * Wahlperiode der Mitgliedschaft
     *
     * @return
     */
    Wahlperiode getWahlperiode();

}

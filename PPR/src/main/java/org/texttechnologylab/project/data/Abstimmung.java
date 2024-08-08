package org.texttechnologylab.project.data;

import org.texttechnologylab.project.exception.BundestagException;

/**
 * Interface für eine Abstimmung
 *
 * @author Giuseppe Abrami
 */
public interface Abstimmung extends BundestagObject {

    /**
     * Rückgabe des Abgeordneten der Abgestimmt hat.
     * @return
     */
    Abgeordneter getAbgeordneter();

    /**
     * Rückgabe über das Abstimmungsergebnis
     * @return
     */
    Types.ABSTIMMUNG getErgebnis();

    /**
     * Beschreibung der Abstimmung
     *
     * @return
     */
    String getBeschreibung() throws BundestagException;

}

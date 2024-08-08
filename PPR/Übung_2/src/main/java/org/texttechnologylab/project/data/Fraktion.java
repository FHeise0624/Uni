package org.texttechnologylab.project.data;

import java.util.Map;

/**
 * Interface für eine Fraktion
 *
 * @author Giuseppe Abrami
 */
public interface Fraktion extends Gruppe {

    /**
     * Rückgabe aller Funktionsträger
     *
     * @return
     */
    Map<String, Abgeordneter> getFunktionaere();

}

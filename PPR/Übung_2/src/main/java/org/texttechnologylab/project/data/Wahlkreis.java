package org.texttechnologylab.project.data;

import java.util.Set;

/**
 * Interface für einen Wahlkreis
 * @author Giuseppe Abrami
 */
public interface Wahlkreis extends BundestagObject {

    /**
     * Nummer des Wahlkreises
     *
     * @return
     */
    int getNumber();

    /**
     * Liste aller Mandate
     * @return
     */
    Set<Mandat> getMandate();

    /**
     * Liste aller Mandate für eine Wahlperiode
     *
     * @param pValue
     * @return
     */
    Set<Mandat> getMandate(Wahlperiode pValue);

}

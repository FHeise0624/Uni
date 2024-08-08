package org.texttechnologylab.project.data;

import java.util.Set;

/**
 * Interface für eine Partei
 *
 * @author Giuseppe Abrami
 */
public interface Partei extends Gruppe {

    /**
     * Liste aller Mitgliedern der Partei im Bundestag
     * @return
     */
    Set<Abgeordneter> listMitglieder();

    /**
     * Liste aller Mitgliedern der Partei gemäß Wahlperiode
     *
     * @param pPeriode
     * @return
     */
    Set<Abgeordneter> listMitglieder(Wahlperiode pPeriode);
}


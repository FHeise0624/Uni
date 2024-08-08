package org.texttechnologylab.project.data;

import java.util.Set;

/**
 * Interface einer Gruppe
 * @author Giuseppe Abrami
 */
public interface Gruppe extends BundestagObject{

    /**
     * Liste aller Mitglieder
     *
     * @return
     */
    Set<Abgeordneter> getMembers();

    /**
     * Liste aller Mitglieder gemäß Wahlperiode
     *
     * @param pPeriode
     * @return
     */
    Set<Abgeordneter> getMembers(Wahlperiode pPeriode);


}

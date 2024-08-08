package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Gruppe;
import org.texttechnologylab.project.data.Wahlperiode;

import java.util.Set;

/**
 * Implementierung Gruppe
 * @author Felix Tams
 */
public class GruppeImpl implements Gruppe {
    //Attribute
    private Set<Abgeordneter> pMembers = null;
    // TODO Members abhängig
    private Object pID = null;
    private String pLabel = "";

    //init Object
    public GruppeImpl(Set<Abgeordneter> pMembers, Object pID, String pLabel){
        this.pMembers = pMembers;
        this.pID = pID;
        this.pLabel = pLabel;
    }
    //Methoden
    @Override
    public Set<Abgeordneter> getMembers() {
        return this.pMembers;
    }

    @Override
    public Set<Abgeordneter> getMembers(Wahlperiode pPeriode) {
        return null;//TODO implementieren
    }

    @Override
    public Object getID() {
        return this.pID;
    }

    @Override
    public String getLabel() {
        return this.pLabel;
    }

    @Override
    public int compareTo(BundestagObject o) {
        return 0;
    }
}

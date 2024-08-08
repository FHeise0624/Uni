package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Fraktion;
import org.texttechnologylab.project.data.Wahlperiode;

import java.util.Map;
import java.util.Set;

/**
 * Implementierung der Fraktion
 * @author Felix Tams
 */
public class FraktionImpl implements Fraktion {
    // Attribute
    private Map<String, Abgeordneter> pFunktionaere = null;
    private Set<Abgeordneter> pMembers = null;
    private Object pID = null;
    private String pLabel = "";

    // Init Object
    public FraktionImpl(Map<String, Abgeordneter> pFunktionaere,
                        Set<Abgeordneter> pMembers, Object pID, String pLabel){
        this.pFunktionaere = pFunktionaere;
        this.pMembers = pMembers;
        this.pID = pID;
        this.pLabel = pLabel;
    }

    // Methoden
    @Override
    public Map<String, Abgeordneter> getFunktionaere() {
        return this.pFunktionaere;
    }

    @Override
    public Set<Abgeordneter> getMembers() {
        return this.pMembers;
    }

    @Override
    public Set<Abgeordneter> getMembers(Wahlperiode pPeriode) {
        return null;
        // TODO implement
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

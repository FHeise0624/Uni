package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.Ausschuss;
import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Wahlperiode;

import java.util.Set;

/**
 * Implementierung Ausschuss
 * @author Felix Tams
 */
public class AusschussImpl implements Ausschuss {
    // Deklaration der Klassenattribute
    private String pType = "";
    private Set<Abgeordneter> pMembers = null;
    private Object pID = null;
    private String pLabel = "";

    // Inst Ausschuss Objekt
    public AusschussImpl(String pType, Set<Abgeordneter> pMembers, Object pID,
                         String pLabel){
        this.pType = pType;
        this.pMembers = pMembers;
        this.pLabel = pLabel;
        this.pID = pID;
    }
    // implementierug der Klassenmethoden
    @Override
    public String getType() {
        return this.pType;
    }

    @Override
    public Set<Abgeordneter> getMembers() {
        return this.pMembers;
    }

    @Override
    public Set<Abgeordneter> getMembers(Wahlperiode pPeriode) {
        return null;//TODO member in abhängigkeit implementieren
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

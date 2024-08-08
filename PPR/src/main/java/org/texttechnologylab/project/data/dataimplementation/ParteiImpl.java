package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Partei;
import org.texttechnologylab.project.data.Wahlperiode;

import java.util.Set;

public class ParteiImpl implements Partei {
    // Attribute
    private Set<Abgeordneter> pMitglieder;
    private Object pID;
    private Set<Abgeordneter> pMembers;
    private String pLabel;


    // Initialisierung
    public ParteiImpl(Set<Abgeordneter> pMitglieder, Object pId,
                      Set<Abgeordneter> pMembers, String pLabel){
        this.pMitglieder = pMitglieder;
        this.pID = pId;
        this.pMembers = pMembers;
        this.pLabel = pLabel;
    }
    // Methoden

    @Override
    public Set<Abgeordneter> listMitglieder() {
        return this.pMitglieder;
    }

    @Override
    public Set<Abgeordneter> listMitglieder(Wahlperiode pPeriode) {
        return null; //TODO implementieren
    }

    @Override
    public Set<Abgeordneter> getMembers() {
        return this.pMembers;
    }

    @Override
    public Set<Abgeordneter> getMembers(Wahlperiode pPeriode) {
        return null; //TODO implementieren
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

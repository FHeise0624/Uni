package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Mandat;
import org.texttechnologylab.project.data.Wahlkreis;
import org.texttechnologylab.project.data.Wahlperiode;

import java.util.Set;

public class WahlkreisImpl implements Wahlkreis {
    // Attribute
    private int pNumber;
    private Set<Mandat> pMandate;
    private Object pID;
    private String pLabel;


    // Instanziierung
    public WahlkreisImpl(int pNumber, Set<Mandat> pMandate, Object pID,
                         String pLabel){
        this.pNumber = pNumber;
        this.pMandate = pMandate;
        this.pID = pID;
        this.pLabel = pLabel;
    }
    // Methoden

    @Override
    public int getNumber() {
        return this.pNumber;
    }

    @Override
    public Set<Mandat> getMandate() {
        return this.pMandate;
    }

    @Override
    public Set<Mandat> getMandate(Wahlperiode pValue) {
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

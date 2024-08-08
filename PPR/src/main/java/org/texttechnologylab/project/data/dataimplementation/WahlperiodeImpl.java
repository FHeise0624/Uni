package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Mandat;
import org.texttechnologylab.project.data.Types;
import org.texttechnologylab.project.data.Wahlperiode;

import java.sql.Date;
import java.util.Set;

public class WahlperiodeImpl implements Wahlperiode {
    // Attribute
    private int pNumber;
    private Date pStartDate;
    private Date pEndDate;
    private Set<Mandat> pMandate;
    private Object pID;
    private String pLabel;

    // Instanziierung
    public WahlperiodeImpl(int pNumber, Date pStartDate, Date pEndDate,
                           Set<Mandat> pMandate, Object pID,String pLabel){
        this.pNumber = pNumber;
        this.pStartDate = pStartDate;
        this.pEndDate = pEndDate;
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
    public Date getStartDate() {
        return this.pStartDate;
    }

    @Override
    public Date getEndDate() {
        return this.pEndDate;
    }

    @Override
    public Set<Mandat> listMandate() {
        return this.pMandate;
    }

    @Override
    public Set<Mandat> listMandate(Types.MANDAT pType) {
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

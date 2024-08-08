package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.Gruppe;
import org.texttechnologylab.project.data.Mitgliedschaft;
import org.texttechnologylab.project.data.Wahlperiode;
import org.texttechnologylab.project.exception.BundestagException;

import java.sql.Date;

public class MitgliedschaftImpl implements Mitgliedschaft {
    // Attribute
    private Abgeordneter pAbgeordneter;
    private String pFunction;
    private Date pFromDate;
    private Date  pToDate;
    private Boolean pIsActive;
    private Gruppe pGruppe;
    private Wahlperiode pWahlperiode;

    // Instanziierung
    public MitgliedschaftImpl(Abgeordneter pAbgeordneter, String pFunction,
                              Date pFromDate, Date pToDate, Boolean pIsActive,
                              Gruppe pGruppe, Wahlperiode pWahlperiode){
        this.pAbgeordneter = pAbgeordneter;
        this.pFunction = pFunction;
        this.pFromDate = pFromDate;
        this.pToDate = pToDate;
        this.pIsActive = pIsActive;
        this.pGruppe = pGruppe;
        this.pWahlperiode = pWahlperiode;
    }
    // Methoden
    @Override
    public Abgeordneter getAbgeordneter() {
        return pAbgeordneter;
    }

    @Override
    public String getFunktion() throws BundestagException {
        return pFunction;
    }

    @Override
    public Date getFromDate() {
        return pFromDate;
    }

    @Override
    public Date getToDate() {
        return pToDate;
    }

    @Override
    public boolean isActive() {
        return pIsActive;
    }

    @Override
    public Gruppe getGruppe() {
        return pGruppe;
    }

    @Override
    public Wahlperiode getWahlperiode() {
        return pWahlperiode;
    }
}

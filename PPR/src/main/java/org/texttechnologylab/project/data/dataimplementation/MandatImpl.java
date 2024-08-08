package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.*;
import org.texttechnologylab.project.exception.BundestagException;

import java.sql.Date;
import java.util.Set;

/**
 * Implementierung Mandat
 * @author Felix Tams
 */
public class MandatImpl implements Mandat {
    //Attribute
    private Abgeordneter pAbgeordneter = null;
    private Date pFromDate = null;
    private Date pToDate = null;
    private Set<Fraktion> pFraktionen = null;
    private Set<Ausschuss> pAusschuesse = null;
    private Set<Mitgliedschaft> pMitgliedschaft = null;
    private Types.MANDAT pType = null;
    private Wahlperiode pWahlperiode = null;
    private Wahlkreis pWahlkreis = null;
    private Object pID = null;
    private String pLabel = "";

    //Init
    public MandatImpl(Abgeordneter pAbgeordneter, Date pFromDate, Date pToDate,
                      Set<Fraktion> pFraktionen, Set<Ausschuss> pAusschuesse,
                      Set<Mitgliedschaft> pMitgliedschaft, Types.MANDAT pType,
                      Wahlperiode pWahlperiode, Wahlkreis pWahlkreis, Object pID,
                      String pLabel){
        this.pAbgeordneter = pAbgeordneter;
        this.pFromDate = pFromDate;
        this.pToDate = pToDate;
        this.pFraktionen = pFraktionen;
        this.pAusschuesse = pAusschuesse;
        this.pMitgliedschaft = pMitgliedschaft;
        this.pType = pType;
        this.pWahlperiode = pWahlperiode;
        this.pWahlkreis = pWahlkreis;
        this.pID = pID;
        this.pLabel = pLabel;
    }
    //Methoden

    @Override
    public Abgeordneter getAbgeordneter() {
        return this.pAbgeordneter;
    }

    @Override
    public Date fromDate() {
        return this.pFromDate;
    }

    @Override
    public Date toDate() {
        return this.pToDate;
    }

    @Override
    public Set<Fraktion> getFraktionen() {
        return this.pFraktionen;
    }

    @Override
    public Set<Ausschuss> listAusschuesse() {
        return this.pAusschuesse;
    }

    @Override
    public Set<Mitgliedschaft> listMitgliedschaft() {
        return this.pMitgliedschaft;
    }

    @Override
    public Types.MANDAT getTyp() {
        return this.pType;
    }

    @Override
    public Wahlperiode getWahlperiode() {
        return this.pWahlperiode;
    }

    @Override
    public Wahlkreis getWahlkreis() throws BundestagException {
        return this.pWahlkreis;
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

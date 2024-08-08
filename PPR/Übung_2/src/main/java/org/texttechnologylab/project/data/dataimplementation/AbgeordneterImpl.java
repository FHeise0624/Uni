package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.*;
import org.texttechnologylab.project.exception.BundestagException;

import java.sql.Date;
import java.util.Set;

/**
 * Implementierung der Abgeordnetenklasse
 * @author Felix Tams
 */
public class AbgeordneterImpl implements Abgeordneter {
    //Deklaration der Klassenattribute

    private String pName = "";
    private String pVorname = "";
    private String pOrtszusatz = "";
    private String pAdelssuffix = "";
    private String pAnrede = "";
    private String pAkadTitel = "";
    private Date pGeburtsDatum = null;
    private String pGeburtsOrt = "";
    private Date pSterbeDatum = null;
    private Types.GESCHLECHT pGeschlecht = null;
    private String pReligion = "";
    private String pBeruf = "";
    private String pVita = "";
    private String pLabel = "";
    private Set<Mandat> pMandat = null;
    private Set<Mitgliedschaft> pMitgliedschaft = null;
    private Set<Abstimmung> pAbstimmung = null;
    private Partei pPartei = null;
    private Object pID = null;


    //TODO ergänzen der noch fehlenden Attribute

    // Initialisierung des Abgeordneten Objekts
    public AbgeordneterImpl(String pVorname, String pName,
                            String pOrtszusatz, String pAdelssuffix,
                            String pAnrede, String pAkadTitel, Date pGeburtsDatum,
                            String pGeburtsOrt, Date pSterbeDatum,
                            Types.GESCHLECHT pGeschlecht, String pReligion,
                            String pBeruf, String pVita, String pLabel, Set<Mandat> pMandat,
                            Set<Mitgliedschaft> pMitgliedschaft, Set<Abstimmung> pAbstimmung,
                            Partei pPartei, Object pID)
    {
        this.pName = pName;
        this.pVorname = pVorname;
        this.pOrtszusatz = pOrtszusatz;
        this.pAdelssuffix = pAdelssuffix;
        this.pAnrede = pAnrede;
        this.pAkadTitel = pAkadTitel;
        this.pGeburtsDatum = pGeburtsDatum;
        this.pGeburtsOrt = pGeburtsOrt;
        this.pSterbeDatum = pSterbeDatum;
        this.pGeschlecht = pGeschlecht;
        this.pReligion = pReligion;
        this.pBeruf = pBeruf;
        this.pVita = pVita;
        this.pLabel = pLabel;
        this.pMandat = pMandat;
        this.pMitgliedschaft = pMitgliedschaft;
        this.pAbstimmung = pAbstimmung;
        this.pPartei = pPartei;
        this.pID = pID;

        //TODO ergänzen der noch zu implementierenden Attribute
    }

  // Definition der Klassenmethoden
    @Override
    public String getName() {
        return this.pName;
    }

    @Override
    public String getVorname() {
        return this.pVorname;
    }

    @Override
    public String getOrtszusatz() {
        return this.pOrtszusatz;
    }

    @Override
    public String getAdelssuffix() {
        return this.pAdelssuffix;
    }

    @Override
    public String getAnrede() {
        return this.pAnrede;
    }

    @Override
    public String getAkadTitel() {
        return this.pAkadTitel;
    }

    @Override
    public Date getGeburtsDatum() {
        return this.pGeburtsDatum;
    }

    @Override
    public String getGeburtsOrt() {
        return this.pGeburtsOrt;
    }

    @Override
    public Date getSterbeDatum() throws NullPointerException {
        return this.pSterbeDatum;
    }

    @Override
    public Types.GESCHLECHT getGeschlecht() {
        return this.pGeschlecht;
    }

    @Override
    public String getReligion() {
        return this.pReligion;
    }

    @Override
    public String getBeruf() {
        return this.pBeruf;
    }

    @Override
    public String getVita() throws NullPointerException {
        return this.pVita;
    }

    @Override
    public Set<Mandat> listMandate(Wahlperiode pValue) {
        return null;
        //TODO implementieren
    }

    @Override
    public Set<Mandat> listMandate() {
        return this.pMandat;
    }

    @Override
    public boolean hasMandat(Wahlperiode pValue) {
        return false; //TODO implementieren
    }

    @Override
    public Set<Mitgliedschaft> listMitgliedschaften() {
        return this.pMitgliedschaft;
    }

    @Override
    public Set<Mitgliedschaft> listMitgliedschaften(Wahlperiode pValue) {
        return null; //TODO implementieren
    }

    @Override
    public Set<Abstimmung> listAbstimmungen() {
        return this.pAbstimmung;
    }

    @Override
    public Set<Abstimmung> listAbstimmungen(Wahlperiode pValue) {
        return null; //TODO implementieren
    }

    @Override
    public Set<Abstimmung> listAbstimmungen(Wahlperiode pValue, Types.ABSTIMMUNG pType) {
        return null; //TODO implementierung
    }

    @Override
    public Partei getPartei() throws BundestagException {
        return this.pPartei;
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
        return 0; //TODO implementieren
    }
}

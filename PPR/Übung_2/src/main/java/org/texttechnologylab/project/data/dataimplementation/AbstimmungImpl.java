package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.Abstimmung;
import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Types;
import org.texttechnologylab.project.exception.BundestagException;

/**
 * Implementierung Abstimmunen
 * @author Felix Tams
 */
public class AbstimmungImpl implements Abstimmung {
    // Definition Attribute
    private Types.ABSTIMMUNG pErgebnis = null;
    private String pBeschreibung = "";
    private String pLabel = "";
    private Object pID = null;


    //Initialisierung Abstimmungsobjekt
    public AbstimmungImpl(Types.ABSTIMMUNG pErgebnis, String pBeschreibung,
                          String pLabel, Object pID){
        this.pErgebnis = pErgebnis;
        this.pBeschreibung = pBeschreibung;
        this.pLabel = pLabel;
        this.pID = pID;
    }

    // Definition der Klassenmethoden
    @Override
    public Abgeordneter getAbgeordneter() {
        return null;
    }

    @Override
    public Types.ABSTIMMUNG getErgebnis() {
        return this.pErgebnis;
    }

    @Override
    public String getBeschreibung() throws BundestagException {
        return this.pBeschreibung;
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
        return 0; //TODO Implement
    }
}

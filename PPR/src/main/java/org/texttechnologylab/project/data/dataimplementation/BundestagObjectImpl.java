package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.BundestagObject;

/**
 * Implementierung BundestagsObject
 * @author Felix Tams
 */
public class BundestagObjectImpl implements BundestagObject {
    //Klassenattribute
    private Object pID = null;
    private String pLabel = "";

    // Init BundestagsObject Objekt
    public BundestagObjectImpl(Object pID, String pLabel){
        this.pID = pID;
        this.pLabel = pLabel;
    }
    // Klassenmethoden
    @Override
    public Object getID() {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public int compareTo(BundestagObject o) {
        return 0;
    }
}

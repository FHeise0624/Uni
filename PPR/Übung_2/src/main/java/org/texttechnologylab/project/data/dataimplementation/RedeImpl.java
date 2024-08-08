package org.texttechnologylab.project.data.dataimplementation;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.BundestagObject;
import org.texttechnologylab.project.data.Rede;

import java.sql.Date;

public class RedeImpl implements Rede {
    // Attribute
    private Abgeordneter pRedner;
    private String pText;
    private Date pDate;
    private Object pID;
    private String pLabel;


    // Instanziierung
    public RedeImpl(Abgeordneter pRedner, String pText, Date pDate, Object pID,
                    String pLabel){
        this.pRedner = pRedner;
        this.pText = pText;
        this.pDate = pDate;
        this.pID = pID;
        this.pLabel = pLabel;
    }


    // Methoden
    @Override
    public Abgeordneter getRedner() {
        return this.pRedner;
    }

    @Override
    public String getText() {
        return this.pText;
    }

    @Override
    public Date getDate() {
        return this.pDate;
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

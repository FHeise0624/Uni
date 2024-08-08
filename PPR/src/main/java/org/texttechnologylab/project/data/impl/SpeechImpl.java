package org.texttechnologylab.project.data.impl;

import org.texttechnologylab.project.data.Speech;

import java.sql.Date;

/**
 * Class that implements the Speech interface.
 */
public class SpeechImpl implements Speech {
    // Attributes
    private int pSpeakerId = 0;
    private String  pText = "";

    private Date pDate = null;

    //init-method
    public SpeechImpl(){
    }

    @Override
    public int getSpeakerID() {
        return this.pSpeakerId;
    }


    public SpeechImpl setSpeakerId(int pValue){
        this.pSpeakerId = pValue;
        return this;
    }

    @Override
    public String getText() {
        return this.pText;
    }

    public SpeechImpl setText(String sValue){
        this.pText = sValue;
        return this;
    }

    @Override
    public Date getDate() {
        return this.pDate;
    }

    public SpeechImpl setDate(Date pValue){
        this.pDate = new Date(pValue.getTime());
        return this;
    }
}

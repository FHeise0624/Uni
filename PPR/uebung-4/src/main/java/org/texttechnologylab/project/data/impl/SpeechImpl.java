package org.texttechnologylab.project.data.impl;

import org.texttechnologylab.project.data.Speech;

/**
 * Class that implements the Speech interface.
 * @author Felix Tams
 */
public class SpeechImpl implements Speech {
    // Attributes
    private int pSpeakerId = 0;
    private String  pText = "";

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
}

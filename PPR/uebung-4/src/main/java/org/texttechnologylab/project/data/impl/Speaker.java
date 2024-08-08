package org.texttechnologylab.project.data.impl;

import com.mongodb.client.MongoCursor;

import javax.swing.text.Document;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Calss that defines a Speaker and the information that the class holds.
 * @author Felix Tams
 */
public class Speaker implements org.texttechnologylab.project.data.Speaker {
    private int pID = 0;
    private String pLastName = "";
    private String pFirstName = "";
    private String pLocalAddition = "";
    private String pNobleTitel = "";
    private String pSalutation = "";
    private String pAcadTitle = "";
    private Date pDOB = null;
    private String pPlaceOfBirth = "";
    private Date pDOD = null;
    private String pGender = "";
    private String pReligion = "";
    private String pJob = "";
    private String pVita = "";
    private String pParty = "";

    // constructor
    public Speaker(){

    }

    /**
     * Class to define a MongoCursor.
     * @param pResult
     * @return
     */
    public static Set<Speaker> initSpeaker(MongoCursor<Document> pResult){
        Set<Speaker> speakerSet = new HashSet<>(0);
        while (pResult.hasNext()){
            Document pDocument = pResult.next();
            speakerSet.add(new Speaker());
        }
        return speakerSet;
    }

    //getter & setter methods.
    public int getID(){
        return this.pID;
    }

    public Speaker setID(int iValue){
        this.pID = iValue;
        return this;
    }
    @Override
    public String getLastName() {
        return this.pLastName;
    }

    public Speaker setLastName(String sValue){
        this.pLastName = sValue;
        return this;
    }

    @Override
    public String getFirstName() {
        return this.pFirstName;
    }

    public Speaker setFirstName(String sValue){
        this.pFirstName = sValue;
        return this;
    }

    @Override
    public String getLocalAddition() {
        return this.pLocalAddition;
    }

    public Speaker setLocalAddition(String sValue){
        this.pLocalAddition = sValue;
        return this;
    }
    @Override
    public String getNobleTitle() {
        return this.pNobleTitel;
    }

    public Speaker setNobleTitle(String sValue){
        this.pNobleTitel = sValue;
        return this;
    }

    @Override
    public String getSalutation() {
        return this.pSalutation;
    }

    public Speaker setSalutation(String sValue){
        this.pSalutation = sValue;
        return this;
    }

    @Override
    public String getAcadTitle() {
        return this.pAcadTitle;
    }

    public Speaker setAcadTitle(String sValue){
        this.pAcadTitle = sValue;
        return this;
    }

    @Override
    public Date getDOB() {
        return this.pDOB;
    }

    public Speaker setDOB(Date pValue){
        this.pDOB = pValue;
        return this;
    }

    @Override
    public String getPlaceOfBirth() {
        return this.pPlaceOfBirth;
    }

    public Speaker setPLaceOfBirth(String sValue){
        this.pPlaceOfBirth = sValue;
        return this;
    }

    @Override
    public Date getDOD() throws NullPointerException {
        return this.pDOD;
    }

    public Speaker setDOD(Date pValue){
        this.pDOD = pValue;
        return this;
    }

    @Override
    public String getGender() {
        return this.pGender;
    }

    public Speaker setGender(String pValue){
        this.pGender = pValue;
        return this;
    }

    @Override
    public String getReligion() {
        return this.pReligion;
    }

    public Speaker setReligion(String sValue){
        this.pReligion = sValue;
        return this;
    }

    @Override
    public String getJob() {
        return this.pJob;
    }

    public Speaker setJob(String sValue){
        this.pJob = sValue;
        return this;
    }

    @Override
    public String getVita() throws NullPointerException {
        return this.pVita;
    }

    public Speaker setVita(String sValue){
        this.pVita = sValue;
        return this;
    }

    @Override
    public String getParty() {
        return this.pParty;
    }

    public Speaker setParty(String sValue){
        this.pParty = sValue;
        return this;
    }
}

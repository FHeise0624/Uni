package org.texttechnologylab.project.data.impl;

import org.texttechnologylab.project.data.MP;

import java.util.Date;

public class MPImpl implements MP {
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

    public MPImpl() {

    }

    public int getID(){
        return this.pID;
    }

    public MPImpl setID(int iValue){
        this.pID = iValue;
        return this;
    }
    @Override
    public String getLastName() {
        return this.pLastName;
    }

    public MPImpl setLastName(String sValue){
        this.pLastName = sValue;
        return this;
    }

    @Override
    public String getFirstName() {
        return this.pFirstName;
    }

    public MPImpl setFirstName(String sValue){
        this.pFirstName = sValue;
        return this;
    }

    @Override
    public String getLocalAddition() {
        return this.pLocalAddition;
    }

    public MPImpl setLocalAddition(String sValue){
        this.pLocalAddition = sValue;
        return this;
    }
    @Override
    public String getNobleTitle() {
        return this.pNobleTitel;
    }

    public MPImpl setNobleTitle(String sValue){
        this.pNobleTitel = sValue;
        return this;
    }

    @Override
    public String getSalutation() {
        return this.pSalutation;
    }

    public MPImpl setSalutation(String sValue){
        this.pSalutation = sValue;
        return this;
    }

    @Override
    public String getAcadTitle() {
        return this.pAcadTitle;
    }

    public MPImpl setAcadTitle(String sValue){
        this.pAcadTitle = sValue;
        return this;
    }

    @Override
    public Date getDOB() {
        return this.pDOB;
    }

    public MPImpl setDOB(Date pValue){
        this.pDOB = pValue;
        return this;
    }

    @Override
    public String getPlaceOfBirth() {
        return this.pPlaceOfBirth;
    }

    public MPImpl setPLaceOfBirth(String sValue){
        this.pPlaceOfBirth = sValue;
        return this;
    }

    @Override
    public Date getDOD() throws NullPointerException {
        return this.pDOD;
    }

    public MPImpl setDOD(Date pValue){
        this.pDOD = pValue;
        return this;
    }

    @Override
    public String getGender() {
        return this.pGender;
    }

    public MPImpl setGender(String pValue){
        this.pGender = pValue;
        return this;
    }

    @Override
    public String getReligion() {
        return this.pReligion;
    }

    public MPImpl setReligion(String sValue){
        this.pReligion = sValue;
        return this;
    }

    @Override
    public String getJob() {
        return this.pJob;
    }

    public MPImpl setJob(String sValue){
        this.pJob = sValue;
        return this;
    }

    @Override
    public String getVita() throws NullPointerException {
        return this.pVita;
    }

    public MPImpl setVita(String sValue){
        this.pVita = sValue;
        return this;
    }

    @Override
    public String getParty() {
        return this.pParty;
    }

    public MPImpl setParty(String sValue){
        this.pParty = sValue;
        return this;
    }
}

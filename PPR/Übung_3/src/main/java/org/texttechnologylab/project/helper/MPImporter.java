package org.texttechnologylab.project.helper;

import org.texttechnologylab.project.data.MP;
import org.texttechnologylab.project.data.impl.MPImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import static javafx.beans.binding.Bindings.isEmpty;

/**
 * Class to import the MP data from an XML-File.
 * @author Felix Tams
 * this class is based on the DOM-Parser from mkyong & the AbgeordnetenImporter from Giuseppe Abrami, aswell as on my
 * own StammdatenReader.
 */
public class MPImporter {
    private File pFile = null;

    public MPImporter(File pFile) {
        this.pFile = pFile;

        try {
            Import();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<MP> Import() throws ParserConfigurationException, IOException, SAXException, ParseException {

        Set<MP> mpSet = new HashSet<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document pDocument = db.parse(this.pFile);
        NodeList nl = pDocument.getElementsByTagName("MDB");

        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);

            Node nID = XMLReader.getSingleNodesFromXML(n, "ID");
            Node nLastName = XMLReader.getSingleNodesFromXML(n, "NACHNAME");
            Node nFirstName = XMLReader.getSingleNodesFromXML(n, "VORNAME");
            Node nLocalAddition = XMLReader.getSingleNodesFromXML(n, "ORTSZUSATZ");
            Node nNobleTitel = XMLReader.getSingleNodesFromXML(n, "ADEL");
            Node nSalutation = XMLReader.getSingleNodesFromXML(n, "ANREDE_TITEL");
            Node nAcadTitel = XMLReader.getSingleNodesFromXML(n, "AKAD_TITEL");
            Node nDOB = XMLReader.getSingleNodesFromXML(n, "GEBURTSDATUM");
            Node nPlaceOfBirth = XMLReader.getSingleNodesFromXML(n, "GEBURTSORT");
            Node nDOD = XMLReader.getSingleNodesFromXML(n, "STERBEDATUM");
            Node nGender = XMLReader.getSingleNodesFromXML(n, "GESCHLECHT");
            Node nReligion = XMLReader.getSingleNodesFromXML(n, "RELIGION");
            Node nJob = XMLReader.getSingleNodesFromXML(n, "BERUF");
            Node nVita = XMLReader.getSingleNodesFromXML(n, "VITA_KURZ");
            Node nParty = XMLReader.getSingleNodesFromXML(n, "PARTEI_KURZ");

            // creating MP
            MPImpl pMP = new MPImpl();
            if (nID != null){
                int pID = Integer.valueOf(nID.getTextContent());
                pMP.setID(pID);
            }
            if (nLastName != null){
                pMP.setLastName(nLastName.getTextContent());
            }
            if (nFirstName != null){
                pMP.setFirstName(nFirstName.getTextContent());
            }
            if (nLocalAddition != null){
                pMP.setLocalAddition(nLocalAddition.getTextContent());
            }
            if (nNobleTitel != null){
                pMP.setNobleTitle(nNobleTitel.getTextContent());
            }
            if (nSalutation != null){
                pMP.setSalutation(nSalutation.getTextContent());
            }
            if (nAcadTitel != null){
                pMP.setAcadTitle(nAcadTitel.getTextContent());
            }
            if (nDOB != null){
                try{
                    pMP.setDOB(DateHelper.SDF.parse(nDOB.getTextContent()));
                } catch (ParseException e){
                    throw new RuntimeException(e);
                }
            }
            if (nPlaceOfBirth != null){
                pMP.setPLaceOfBirth(nPlaceOfBirth.getTextContent());
            }
            if (nDOD != null){
                String dod = nDOD.getTextContent().trim();
                if (!dod.isEmpty()){
                    try{
                        pMP.setDOD(DateHelper.SDF.parse(nDOD.getTextContent()));
                    } catch (ParseException e){
                        throw new RuntimeException(e);
                    }
                }
            }
            if (nGender != null){
                if(nGender.getTextContent().length() > 0){
                    String sGender = nGender.getTextContent();
                    sGender = sGender.replace("ä","ae");
                    pMP.setGender(sGender.toUpperCase());
                }
            }
            if (nReligion != null){
                pMP.setReligion(nReligion.getTextContent());
            }
            if (nJob != null){
                pMP.setJob(nJob.getTextContent());
            }
            if (nVita != null){
                pMP.setVita(nVita.getTextContent());
            }
            if (nParty != null){
                pMP.setParty(nParty.getTextContent());
            }

            mpSet.add(pMP);
        }
        return mpSet;
    }
}


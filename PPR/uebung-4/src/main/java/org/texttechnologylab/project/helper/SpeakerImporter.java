package org.texttechnologylab.project.helper;

import org.texttechnologylab.project.data.Speaker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;



/**
 * Class to import the MP data from an XML-File.
 * @author Felix Tams
 * this class is based on the DOM-Parser from mkyong & the AbgeordnetenImporter from Giuseppe Abrami, aswell as on my
 * own StammdatenReader.
 */
public class SpeakerImporter {
    private File pFile = null;

    public SpeakerImporter(File pFile) {
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

    /**
     * Method that extracts speaker-data and stores it in a list.
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws ParseException
     */
    public Set<Speaker> Import() throws ParserConfigurationException, IOException, SAXException, ParseException {

        Set<Speaker> speakerSet = new HashSet<>();

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
            org.texttechnologylab.project.data.impl.Speaker pSpeaker = new org.texttechnologylab.project.data.impl.Speaker();
            if (nID != null){
                int pID = Integer.valueOf(nID.getTextContent());
                pSpeaker.setID(pID);
            }
            if (nLastName != null){
                pSpeaker.setLastName(nLastName.getTextContent());
            }
            if (nFirstName != null){
                pSpeaker.setFirstName(nFirstName.getTextContent());
            }
            if (nLocalAddition != null){
                pSpeaker.setLocalAddition(nLocalAddition.getTextContent());
            }
            if (nNobleTitel != null){
                pSpeaker.setNobleTitle(nNobleTitel.getTextContent());
            }
            if (nSalutation != null){
                pSpeaker.setSalutation(nSalutation.getTextContent());
            }
            if (nAcadTitel != null){
                pSpeaker.setAcadTitle(nAcadTitel.getTextContent());
            }
            if (nDOB != null){
                try{
                    pSpeaker.setDOB(DateHelper.SDF.parse(nDOB.getTextContent()));
                } catch (ParseException e){
                    throw new RuntimeException(e);
                }
            }
            if (nPlaceOfBirth != null){
                pSpeaker.setPLaceOfBirth(nPlaceOfBirth.getTextContent());
            }
            if (nDOD != null){
                String dod = nDOD.getTextContent().trim();
                if (!dod.isEmpty()){
                    try{
                        pSpeaker.setDOD(DateHelper.SDF.parse(nDOD.getTextContent()));
                    } catch (ParseException e){
                        throw new RuntimeException(e);
                    }
                }
            }
            if (nGender != null){
                if(nGender.getTextContent().length() > 0){
                    String sGender = nGender.getTextContent();
                    sGender = sGender.replace("ä","ae");
                    pSpeaker.setGender(sGender.toUpperCase());
                }
            }
            if (nReligion != null){
                pSpeaker.setReligion(nReligion.getTextContent());
            }
            if (nJob != null){
                pSpeaker.setJob(nJob.getTextContent());
            }
            if (nVita != null){
                pSpeaker.setVita(nVita.getTextContent());
            }
            if (nParty != null){
                pSpeaker.setParty(nParty.getTextContent());
            }

            speakerSet.add(pSpeaker);
        }
        return speakerSet;
    }
}


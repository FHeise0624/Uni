package org.texttechnologylab.project.helper;

import org.texttechnologylab.project.data.Speech;
import org.texttechnologylab.project.data.impl.SpeechImpl;
import org.texttechnologylab.utilities.helper.ArchiveUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Class for importing Speechdata from a zip-archive.
 * @author Felix Tams
 * This class is based on the Redenimporter from Giuseppe Abrami
 */
public class SpeechImporter {

    private File pFile = null;

    public SpeechImporter(File pFile){
    this.pFile = pFile;
    try{
        Import();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

    public Set<Speech> Import(){
        String sTempFile = "/tmp/speeches/";
        new File(sTempFile).mkdir();
        File tFile = new File(sTempFile);
        Set<Speech> speechSet = new HashSet<>();

        if (tFile.list().length != 0){
            // extracting fomr Zip-archive
            if (pFile.getName().endsWith(".zip")){

                Set<File> unzipSet = null;
                try{
                    unzipSet = ArchiveUtils.unzip(pFile);
                    unzipSet.stream()
                            .filter(f -> f.getName().endsWith(".xml"))
                            .forEach(f -> {
                                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                                try {
                                    //parse XML
                                    DocumentBuilder db = dbf.newDocumentBuilder();

                                    Document pDocument = db.parse(f);
                                    Node pDateNode = pDocument.getElementsByTagName("datum").item(0);

                                    String sDatum =
                                            pDateNode.getAttributes().getNamedItem("date").getFirstChild().getTextContent();
                                    Date pDate = DateHelper.SDF.parse(sDatum);

                                    NodeList speechNodes = pDocument.getElementsByTagName("rede");

                                    int iSpeakerId = 0;
                                    for (int i = 0; i < speechNodes.getLength(); i++) {
                                        Node sNode = speechNodes.item(i);

                                        Node pSpeakerNode = XMLReader.getSingleNodesFromXML(sNode, "redner");
                                        iSpeakerId =
                                                Integer.valueOf(pSpeakerNode.getAttributes().getNamedItem("id").getTextContent());
                                    }

                                    //initialasing speech object
                                    SpeechImpl pSpeech = new SpeechImpl();

                                    if (pDate != null) {
                                        pSpeech.setDate((java.sql.Date) pDate);
                                    }
                                    if (iSpeakerId != 0) {
                                        pSpeech.setSpeakerId(iSpeakerId);
                                    }
                                    if (speechNodes.getLength()>0){
                                        String pText = "";
                                        for (int i = 0; i < speechNodes.getLength(); i++) {
                                            if (speechNodes.item(i) != null){
                                            String sText = speechNodes.item(i).getTextContent();
                                            pText = pText + sText;
                                            }
                                            else {
                                                pText = pText + "";
                                            }
                                        }
                                        pSpeech.setText(pText);
                                    }

                                    speechSet.add(pSpeech);
                                } catch (ParserConfigurationException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (SAXException e) {
                                    throw new RuntimeException(e);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                    System.out.println(speechSet);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to unzip speeches");
                }
            }
            System.out.println(speechSet);
        } return speechSet;
    }
}


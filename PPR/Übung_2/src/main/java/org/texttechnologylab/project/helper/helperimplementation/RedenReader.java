package org.texttechnologylab.project.helper.helperimplementation; //package com.mkyong.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * DOM-Parser für XML Dateien. Via Discord wurde das OK gegeben den DOMParser zu verwenden.
 * daher habe ich diesen hier eingebunden
 * @author mkyong
 */

public class RedenReader {
     public static void main(String[] args){
        String filename = "C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester 3\\PPR\\PPR-Programme\\uebung-2\\src\\main\\resources\\Bundestagsreden20\\1.xml";

    //public Map<String, Map> Stammdatenrd(String filename) {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        Map<String, Map> rede = null;
        try {
            InputStream inputStream = new FileInputStream(filename);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(filename));


            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <MDB>
            NodeList list = doc.getElementsByTagName("dbtplenarprotokoll");
            rede = new HashMap<>();

            for (int temp = 0; temp < 1; temp++) { //TODO 1 durch list.getlenght() ersetzten

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;


                    // get Datum
                    String datum = element.getElementsByTagName("datum").item(0).getTextContent();
                    //TODO in einem HashSet mit den restlichen Redeninformationen kapseln


                    // Rednerdaten

                    //extrahieren der ID
                    Element redner = (Element) element.getElementsByTagName("redner").item(0);
                    String rednerID = redner.getAttribute("id");
                    //TODO in einem Hasset mit den anderen Rednerinformationen ablegen.

                    //Restliche Rednerinformationen
                    String rednerTitel = element.getElementsByTagName("titel").item(0).getTextContent();
                    String rednerVorname = element.getElementsByTagName("vorname").item(0).getTextContent();
                    String rednerName = element.getElementsByTagName("nachname").item(0).getTextContent();
                    String rednerFraktion = element.getElementsByTagName("fraktion").item(0).getTextContent();

                    //TODO Reden Texte einlesen.

                    //Test print Statements.

                    System.out.println("Datum: " + datum + "\n" + "Redner-ID: " + rednerID
                    + "\n" + "Redner: " + rednerTitel + " " + rednerVorname + " " + rednerName
                    + "\n" + "Fraktion: " + rednerFraktion);


                    /*
                    Map<String, String> redner = new HashMap<>();
                    //TODO schleife überarbeiten: Ist diese so wirklich sinnvoll?
                    for (int i = 0; i <= element.getElementsByTagName("NAME").getLength() - 1; i++) {
                        redner.put("Nachname", element.getElementsByTagName("NACHNAME").item(i).getTextContent());
                        redner.put("Vorname", element.getElementsByTagName("VORNAME").item(i).getTextContent());
                        redner.put("Ortszusatz", element.getElementsByTagName("ORTSZUSATZ").item(i).getTextContent());
                        redner.put("Adel", element.getElementsByTagName("ADEL").item(i).getTextContent());
                        redner.put("Anrede", element.getElementsByTagName("ANREDE_TITEL").item(i).getTextContent());
                        redner.put("AkadTitel", element.getElementsByTagName("AKAD_TITEL").item(i).getTextContent());
                        redner.put("HistorieVon", element.getElementsByTagName("HISTORIE_VON").item(i).getTextContent());
                        redner.put("HistorieBis", element.getElementsByTagName("HISTORIE_BIS").item(i).getTextContent());
                    }

                     */












                    /*
                    // Zusammenfassen der thematisierten Hashmaps in eine Abgeordneten Hashmap.
                    abgeordneter.put(id, name);
                    abgeordneter.put("Biografie", bio);
                    abgeordneter.put("Wahlperioden", wpms);
                    abgeordneter.put("Institutionen", insn);


                    System.out.println("Abgeordneter:" + abgeordneter);

                    */



                }


            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        //return abgeordneter; //TODO implementierung der Rückgabe der Abgeordneten Struktur an die Factory
    }

}


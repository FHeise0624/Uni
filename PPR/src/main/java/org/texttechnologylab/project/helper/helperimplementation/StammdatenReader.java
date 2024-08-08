package org.texttechnologylab.project.helper.helperimplementation;
//package com.mkyong.xml.dom;

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
public class StammdatenReader {
    public static void main(String[] args){
        String filename = "C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester 3\\PPR\\PPR-Programme\\uebung-2\\src\\main\\resources\\MdB-Stammdaten\\MDB_STAMMDATEN.XML";

    //public Map<String, Map> Stammdatenrd(String filename) {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        Map<String, Map> abgeordneter = null;
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
            NodeList list = doc.getElementsByTagName("MDB");
            abgeordneter = new HashMap<>();

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;


                    // get text
                    String id = element.getElementsByTagName("ID").item(0).getTextContent();

                    // Namens bezogene Daten
                    Map<String, String> name = new HashMap<>();

                    for (int i = 0; i <= element.getElementsByTagName("NAME").getLength() - 1; i++) {
                        name.put("Nachname", element.getElementsByTagName("NACHNAME").item(i).getTextContent());
                        name.put("Vorname", element.getElementsByTagName("VORNAME").item(i).getTextContent());
                        name.put("Ortszusatz", element.getElementsByTagName("ORTSZUSATZ").item(i).getTextContent());
                        name.put("Adel", element.getElementsByTagName("ADEL").item(i).getTextContent());
                        name.put("Anrede", element.getElementsByTagName("ANREDE_TITEL").item(i).getTextContent());
                        name.put("AkadTitel", element.getElementsByTagName("AKAD_TITEL").item(i).getTextContent());
                        name.put("HistorieVon", element.getElementsByTagName("HISTORIE_VON").item(i).getTextContent());
                        name.put("HistorieBis", element.getElementsByTagName("HISTORIE_BIS").item(i).getTextContent());
                    }

                    // Biografische Daten
                    /**
                     * @bio Hashmap für die speicherung der ausgelesenen Biogrfischen Daten eines Abgeordneten.
                     * Die Schleife liest für jeden Abgeordneten die biografischen Daten aus und
                     * speichert diese in einer Hashmap.,
                     */
                    Map<String, String> bio = new HashMap<>();

                    for (int i = 0; i <= (element.getElementsByTagName("BIOGRAFISCHE_ANGABEN").getLength() - 1); i++) {
                        bio.put("Geburtsdatum", element.getElementsByTagName("GEBURTSDATUM").item(i).getTextContent());
                        bio.put("Geburtsort", element.getElementsByTagName("GEBURTSORT").item(i).getTextContent());
                        bio.put("Geburtsland", element.getElementsByTagName("GEBURTSLAND").item(i).getTextContent());
                        bio.put("Sterbedatum", element.getElementsByTagName("STERBEDATUM").item(i).getTextContent());
                        bio.put("Geschlecht", element.getElementsByTagName("GESCHLECHT").item(i).getTextContent());
                        bio.put("Familienstand", element.getElementsByTagName("FAMILIENSTAND").item(i).getTextContent());
                        bio.put("Religion", element.getElementsByTagName("RELIGION").item(i).getTextContent());
                        bio.put("Beruf", element.getElementsByTagName("BERUF").item(i).getTextContent());
                        bio.put("Partei", element.getElementsByTagName("PARTEI_KURZ").item(i).getTextContent());
                        bio.put("Vita", element.getElementsByTagName("VITA_KURZ").item(i).getTextContent());
                        bio.put("Veröffent", element.getElementsByTagName("VEROEFFENTLICHUNGSPFLICHTIGES").item(i).getTextContent());
                    }

                    //Wahlperioden Daten
                    /**
                     * @wpm ist eine Hashmap die in Key, Value Paaren die Wahlperioden Informationen zu einem Abgeordneten
                     * für jeweils eine Wahlperiode speichert.
                     * @wpms ist eine Hashmap, die in Key = WP Nummer, Value = WP Paaren eine Sammlung
                     * der gesamten Wahlperioden eines Abgeordneten darstellt.
                     * Die Schleife durchlaeuft die XML-Datei und erstellt die entsprechenden
                     * Hashmaps.
                     */
                    Map<String, String> wpm = new HashMap<>();
                    Map<String, Map> wpms = new HashMap<>();

                    for (int i = 0; i <= (element.getElementsByTagName("WAHLPERIODE").getLength() - 1); i++) {
                        wpm.put("Wahlperiode", element.getElementsByTagName("WP").item(i).getTextContent());
                        wpm.put("Beginn", element.getElementsByTagName("MDBWP_VON").item(i).getTextContent());
                        wpm.put("Ende", element.getElementsByTagName("MDBWP_BIS").item(i).getTextContent());
                        wpm.put("WahlkreisNr", element.getElementsByTagName("WKR_NUMMER").item(i).getTextContent());
                        wpm.put("Wahlkreisname", element.getElementsByTagName("WKR_NAME").item(i).getTextContent());
                        wpm.put("Wahlkreisland", element.getElementsByTagName("WKR_LAND").item(i).getTextContent());
                        wpm.put("Liste", element.getElementsByTagName("LISTE").item(i).getTextContent());
                        wpm.put("Mandatsart", element.getElementsByTagName("MANDATSART").item(i).getTextContent());
                        wpms.put("Wahlperiode", wpm);
                        System.out.println();
                    }

                    // Institutions Daten
                    /**
                     * @ins = eine Hashmap mit den Informationen über die jeweils zu einer Whalperiode gehoerigen
                     * Institution.
                     * @insn = Hashmap aller Institutionen eines Abgeordneten.
                     * die Schleife erstellt die Hahsmaps.
                     */
                    Map<String, String> ins = new HashMap<>();
                    Map<String, Map> insn = new HashMap<>();

                    for (int i = 0; i <= element.getElementsByTagName("INSTITUTION").getLength() - 1; i++) {
                        ins.put("Art", element.getElementsByTagName("INSART_LANG").item(i).getTextContent());
                        ins.put("Bezeichung", element.getElementsByTagName("INS_LANG").item(i).getTextContent());
                        ins.put("Beginn", element.getElementsByTagName("MDBINS_VON").item(i).getTextContent());
                        ins.put("Ende", element.getElementsByTagName("MDBINS_BIS").item(i).getTextContent());
                        ins.put("Funktion", element.getElementsByTagName("FKT_LANG").item(i).getTextContent());
                        ins.put("FktBeginn", element.getElementsByTagName("FKTINS_VON").item(i).getTextContent());
                        ins.put("FktEnde", element.getElementsByTagName("FKTINS_BIS").item(i).getTextContent());
                        insn.put("Institution", ins);
                    }


                    // Zusammenfassen der thematisierten Hashmaps in eine Abgeordneten Hashmap.
                    abgeordneter.put(id, name);
                    abgeordneter.put("Biografie", bio);
                    abgeordneter.put("Wahlperioden", wpms);
                    abgeordneter.put("Institutionen", insn);


                    System.out.println("Abgeordneter:" + abgeordneter);


                }


            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        //return abgeordneter; //TODO implementierung der Rückgabe der Abgeordneten Struktur an die Factory
    }

}

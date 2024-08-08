package org.texttechnologylab.project.database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.texttechnologylab.project.data.MP;
import org.texttechnologylab.project.data.Speech;
import org.texttechnologylab.project.data.impl.MPImpl;
import org.texttechnologylab.project.helper.DatabaseCredentialReader;
import org.texttechnologylab.project.helper.MPImporter;
import org.texttechnologylab.project.helper.SpeechImporter;
import org.texttechnologylab.project.helper.XMLReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;



/**
 * Datenverarbeitung für Stammdaten der Abgeordneten.
 *
 * @author Felix Tams
 */


public class DatabaseImpl {

    public static void main(String[] args) throws ParserConfigurationException, IOException, ParseException, SAXException {
        mongoDB();
    }

    public static void mongoDB() throws ParserConfigurationException, IOException, ParseException, SAXException {
        String path = "C:\\Users\\felix\\OneDrive" +
                    "\\Desktop\\Uni\\Semester 3\\PPR\\PPR-Programme\\uebung-3\\src\\main\\resources\\PPR_WiSe23_266" +
                    ".txt";

        /**
         * Method to extract database access-credentials from a .txt-File
         *
         * @param path
         * enter your file Path in the path-variable.
         */
        DatabaseCredentialReader dcr = new DatabaseCredentialReader(path);

        String hostName = dcr.getPHostName();
        String database = dcr.getPDatabase();
        String username = dcr.getPUsername();
        String password = dcr.getPPassword();
        int hostPort = dcr.getPHostPort();
        String collection = dcr.getPCollection();

        MongoDBConnectionHandler db = new MongoDBConnectionHandler(hostName, database, username, password, hostPort);
        db.createCollection("Abgeordnete");
        db.createCollection("Reden");

        MongoCollection pCollection = db.getCollection("Abgeordnete");
        MongoCollection qCollection = db.getCollection("Reden");


        // Extracting the MP Date and uploadig it into the Database

        File pFile = new File("C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester " +
                "3\\PPR\\PPR-Programme\\uebung-3\\src\\main\\resources\\MdB-Stammdaten\\MDB_STAMMDATEN.XML");

        MPImporter mpi =  new MPImporter(pFile);

        Set<MP> mpSet = mpi.Import();

        for (MP i :mpSet) {
            Document pDocument = new Document();
            pDocument.append("Id", i.getID());
            pDocument.append("Name", i.getLastName());
            pDocument.append("Vorname", i.getFirstName());
            pDocument.append("Ortszusatz", i.getLocalAddition());
            pDocument.append("Adelstitel", i.getNobleTitle());
            pDocument.append("Anrede", i.getSalutation());
            pDocument.append("Akad_Titel", i.getAcadTitle());
            pDocument.append("Geburtsdatum", i.getDOB());
            pDocument.append("Geburtsort", i.getPlaceOfBirth());
            pDocument.append("Sterbedatum", i.getDOD());
            pDocument.append("Geschlecht", i.getGender());
            pDocument.append("Religion", i.getReligion());
            pDocument.append("Beruf", i.getJob());
            pDocument.append("Vita", i.getVita());
            pDocument.append("Partei", i.getParty());

            pCollection.insertOne(pDocument);
        }

        // Extracting Speechdata and uploading to DB.
        File qFile = new File("C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester 3\\PPR\\PPR-Programme\\uebung-3\\src\\main\\resources\\Bundestagsreden20.zip");

        SpeechImporter sImporter = new SpeechImporter(qFile);

        Set<Speech> speechSet = sImporter.Import();

        for (Speech i: speechSet){
            Document pDocument = new Document();
            pDocument.append("Datum", i.getDate());
            pDocument.append("Redner", i.getSpeakerID());
            pDocument.append("Text", i.getText());

            qCollection.insertOne(pDocument);

        }

        System.out.println(speechSet);


    }

}


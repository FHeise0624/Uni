package org.texttechnologylab.project.database;

import com.mongodb.client.MongoCollection;
import org.texttechnologylab.project.helper.DatabaseCredentialReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;



/**
 * Class that implements the database functionalities.
 * @author Felix Tams
 */

public class DatabaseImpl {

    /**
     * Method that uses given functionalities.
     * @param args
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws ParseException
     * @throws SAXException
     */
    public static void main(String[] args) throws ParserConfigurationException, IOException, ParseException, SAXException {
        mongoDB();
    }

    /**
     * Method that copies data from one DB to another.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws ParseException
     * @throws SAXException
     */
    public static void mongoDB() throws ParserConfigurationException, IOException, ParseException, SAXException {
        // defining paths for db access.
        String sourcePath = "C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester " +
                "3\\PPR\\PPR-Programme\\uebung-4\\src\\main\\resources\\dbconnection_ro";
        String toPath = "C:\\Users\\felix\\OneDrive\\Desktop\\Uni\\Semester " +
                "3\\PPR\\PPR-Programme\\uebung-4\\src\\main\\resources\\PPR_WiSe23_266.txt";

        // extracting sign-in credentials for databases
        DatabaseCredentialReader from = new DatabaseCredentialReader(sourcePath);
        String hostName = from.getPHostName();
        String database = from.getPDatabase();
        String username = from.getPUsername();
        String password = from.getPPassword();
        int hostPort = from.getPHostPort();
        String collection = from.getPCollection("Speaker");

        DatabaseCredentialReader to = new DatabaseCredentialReader(toPath);
        String toHostName = to.getPHostName();
        String toDatabase = to.getPDatabase();
        String toUsername = to.getPUsername();
        String toPassword = to.getPPassword();
        int toHostPort = to.getPHostPort();
        String toCollection = to.getPCollection("Speaker");


        //connection to databases.
        MongoDBConnectionHandler fromDB = new MongoDBConnectionHandler(hostName, database, username, password,
                hostPort);
        MongoDBConnectionHandler toDB = new MongoDBConnectionHandler(toHostName, toDatabase, toUsername, toPassword,
                toHostPort);
        toDB.createCollection("Speaker");
        toDB.createCollection("Speech");

        MongoCollection speakerSource = fromDB.getCollection("speaker");
        MongoCollection speechSource = fromDB.getCollection("speech");
        MongoCollection cSpeaker = toDB.getCollection("Speaker");
        MongoCollection cSpeech = toDB.getCollection("Speech");

        // copy of data.
        MongoDBConnectionHandler.copyCollection(speakerSource, cSpeaker);
        MongoDBConnectionHandler.copyCollection(speechSource, cSpeech);
    }

}


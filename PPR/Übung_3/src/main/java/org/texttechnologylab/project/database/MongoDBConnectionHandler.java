package org.texttechnologylab.project.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Calss for MongoDB interaction
 * @author Felix Tams
 * This class is based on the ConnectionHandler by Giuseppe Abrami.
 * It originates in the nosqlexample-main Prject.
 * I added a Method for creating a new Collection in the MongoDB.
 */
public class MongoDBConnectionHandler {
    /**
     *  Connection with DB
     */
    private MongoClient pClient = null;

    /**
     * object for selected database
     */
    private MongoDatabase pDatabase = null;

    /**
     * Amount of all Collections
     */
    private MongoCollection<Document> pCollection = null;

    /**
     * @param sHostName
     * @param sDatabase
     * @param sUsername
     * @param sPassword
     * @param iHostPort
     */
    public MongoDBConnectionHandler(String sHostName, String sDatabase, String sUsername, String sPassword, int iHostPort){

        //define credentials (Username, database, password)
        MongoCredential credential = MongoCredential.createScramSha1Credential(sUsername, sDatabase, sPassword.toCharArray());
        //define Host & Port
        ServerAddress seed = new ServerAddress(sHostName, iHostPort);
        List<ServerAddress> seeds = new ArrayList(0);
        seeds.add(seed);
        // define connection Options
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(2)
                .socketTimeout(10000)
                .maxWaitTime(10000)
                .connectTimeout(1000)
                .sslEnabled(false)
                .build();

        //connect to DB
        pClient = new MongoClient(seeds, credential, options);

        this.pDatabase = pClient.getDatabase(sDatabase);
    }

    public MongoCollection getCollection(){
        return this.pCollection;
    }

    /**
     * Method returning default Collection
     *
     * @return MongoCollection
     */
    public MongoCollection getCollection(String sCollection){
        return this.pDatabase.getCollection(sCollection);
    }

    /**
     * Methode to create new Collections in DB
     * @param cName
     * @return
     */
    public MongoCollection createCollection(String  cName) {
        return this.pDatabase.getCollection(cName);
    }
}

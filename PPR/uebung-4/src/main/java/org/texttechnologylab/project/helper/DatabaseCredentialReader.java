package org.texttechnologylab.project.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that extract database connection information from a .txt-File
 * @author Felix Tams
 */
public class DatabaseCredentialReader {

    private static String pHostName;
    private static String pDatabase;
    private static String pUsername;
    private static String pPassword;
    private static int pHostPort;
    private static String pCollection;

    public DatabaseCredentialReader(String filePath){
        readCredentialsFromFile(filePath);
    }

    /**
     * Method that parses txt.-files and stores sign-in credentials.
     * @param filePath
     */
    private void readCredentialsFromFile(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split("=");
                if (parts.length == 2){
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "remote_host":
                            pHostName = value;
                            break;
                        case "remote_database":
                            pDatabase = value;
                            break;
                        case "remote_user":
                            pUsername = value;
                            break;
                        case "remote_password":
                            pPassword = value;
                            break;
                        case "remote_port":
                            pHostPort = Integer.parseInt(value);
                            break;
                        case "remote_collection":
                            pCollection = value;
                            break;
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //getter & setter-Methods of class.
    public static String getPHostName(){
        return pHostName;
    }

    public static String getPDatabase(){
        return pDatabase;
    }

    public static String getPUsername(){
        return pUsername;
    }

    public static String getPPassword(){
        return pPassword;
    }

    public static int getPHostPort(){
        return pHostPort;
    }

    public static String getPCollection(String speaker){
        return pCollection;
    }


}

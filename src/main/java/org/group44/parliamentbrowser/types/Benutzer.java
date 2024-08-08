package org.group44.parliamentbrowser.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.group44.parliamentbrowser.exception.BundestagJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Enthält die Informationen über den Benutzer der Anwendung
 *
 * @author Cora
 */
public class Benutzer {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(Benutzer.class);
    
    private String benutzerId;
    private String benutzername;
    private String password;
    private String vorname;
    private String nachname;
    private String email;
    private List<String> gruppenIds;

    /**
     * Erstellt aus einem Json string ein Objekt Benutzer
     *
     * @param benutzerAsJson - der Benutzer als Json
     *
     * @return Objekt benutzer
     *
     * @throws BundestagJsonException - wird geworfen, wenn das json ungültig für den Benutzer ist
     */
    public static Benutzer fromJson(String benutzerAsJson) throws BundestagJsonException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Benutzer benutzer = mapper.readValue(benutzerAsJson, Benutzer.class);
            return benutzer;
        } catch (JsonProcessingException e) {
            String message = String.format("Fehler bei der JSON-Deserialisierung des Benutzers aus dem JSON %s.", benutzerAsJson);
            LOGGER.error(message);
            throw new BundestagJsonException(message, e);
        }
    }

    /**
     * Erstellt aus einem Objekt Benutzer einen Json String
     *
     * @return Json String
     *
     * @throws BundestagJsonException - wird geworfen, wenn das json ungültig für den Benutzer ist
     */
    public String toJson() throws BundestagJsonException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            String message = String.format("Fehler bei der JSON-Serialisierung des Benutzers mit der ID %s.", getBenutzerId());
            LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Benutzer-Objekts: {}", this);
            throw new BundestagJsonException(message, e);
        }
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "id='" + benutzerId + '\'' +
                ", benutzername='" + benutzername + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", email='" + email + '\'' +
                ", gruppenIds=" + gruppenIds +
                '}';
    }

    /**
     * Gibt das Passwort des Benutzers zurück
     *
     * @return Passwort des Benutzers
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setzt das Passwort des Benutzers
     *
     * @param password des Benutzer Accounts
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gibt die ID des Benutzers zurück
     *
     * @return ID des Benutzers
     */
    public String getBenutzerId() {
        return benutzerId;
    }

    /**
     * Setzt die ID des Benutzers
     *
     * @param benutzerId des Benutzer Accounts
     */
    public void setBenutzerId(String benutzerId) {
        this.benutzerId = benutzerId;
    }

    /**
     * Gibt den Benutzernamen des Benutzers zurück
     *
     * @return Benutzername des Benutzers
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * Setzt den Benutzernamen des Benutzers
     *
     * @param benutzername des Benutzer Accounts
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    /**
     * Gibt den Vornamen des Benutzers zurück
     *
     * @return Vorname des Benutzers
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Benutzers
     *
     * @param vorname des Benutzers
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt den Nachnamen des Benutzers zurück
     *
     * @return Benutzername des Benutzers
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setzt den Nachnamen des Benutzers
     *
     * @param nachname des Benutzers
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Gibt die E-Mail des Benutzers zurück
     *
     * @return E-Mail des Benutzers
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setzt die E-Mail des Benutzers
     *
     * @param email des Benutzers
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gibt die GruppenId des Benutzers zurück
     *
     * @return GruppenId des Benutzers
     */
    public List<String> getGruppenIds() {
        return gruppenIds;
    }

    /**
     * Setzt die GruppenIds, der Gruppen, der der Benutzer angehört
     *
     * @param gruppenIds des Benutzers
     */
    public void setGruppenIds(List<String> gruppenIds) {
        this.gruppenIds = gruppenIds;
    }

    public static void main(String[] args) {
        System.out.println(new Benutzer().toJson());
    }
}

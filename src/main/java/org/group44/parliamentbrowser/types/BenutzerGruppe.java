package org.group44.parliamentbrowser.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.group44.parliamentbrowser.exception.BundestagJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Enthält die Informationen der Benutzergruppen, jeder Benutzer ist in mindestens einer Benutzergruppe
 *
 * @author Cora
 */

public class BenutzerGruppe {
    private static final Logger LOGGER = LoggerFactory.getLogger(BenutzerGruppe.class);
    private String gruppeId;
    private String name;
    private List<String> rechte;

    /**
     * Gibt die GruppenId zurück
     *
     * @return gruppeId
     */
    public String getGruppeId() {
        return gruppeId;
    }

    /**
     * Setzt die GruppenId zurück
     *
     * @param gruppeId - Id der Gruppe
     */
    public void setGruppeId(String gruppeId) {
        this.gruppeId = gruppeId;
    }

    /**
     * Gibt den Namen der Benutzergruppe zurück
     *
     * @return Name der Benutzergruppe
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Gruppe
     *
     * @param name - wie die Gruppe heißen soll
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Rechte der Benutzergruppe zurück
     *
     * @return Rechte der Benutzergruppe
     */
    public List<String> getRechte() {
        return rechte;
    }

    /**
     * Setzt die Rechte der Benutzergruppe#
     *
     * @param rechte - was die Benutzergruppe darf
     */
    public void setRechte(List<String> rechte) {
        this.rechte = rechte;
    }

    /**
     * Erstellt aus einem Json string ein Objekt Benutzergruppe
     *
     * @param gruppeAsJson - der Benutzer als Json
     *
     * @return Objekt benutzergruppe
     *
     * @throws BundestagJsonException - wird geworfen, wenn das json ungültig für die Benutzergruppe ist
     */
    public static BenutzerGruppe fromJson(String gruppeAsJson) throws BundestagJsonException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            BenutzerGruppe gruppe = mapper.readValue(gruppeAsJson, BenutzerGruppe.class);
            return gruppe;
        } catch (JsonProcessingException e) {
            String message = String.format("Fehler bei der JSON-Deserialisierung der Gruppe aus dem JSON %s.", gruppeAsJson);
            LOGGER.error(message);
            throw new BundestagJsonException(message, e);
        }
    }

    /**
     * Erstellt aus einem Objekt Benutzergruppe einen Json String
     *
     * @return Json String
     *
     * @throws BundestagJsonException - wird geworfen, wenn das json ungültig für die Benutzergruppe ist
     */
    public String toJson() throws BundestagJsonException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            String message = String.format("Fehler bei der JSON-Serialisierung der Gruppe mit der ID %s.", getGruppeId());
            LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des BenutzerGruppe-Objekts: {}", this);
            throw new BundestagJsonException(message, e);
        }
    }

    @Override
    public String toString() {
        return "BenutzerGruppe{" +
                "id='" + gruppeId + '\'' +
                ", name='" + name + '\'' +
                ", rechte=" + rechte +
                '}';
    }
}

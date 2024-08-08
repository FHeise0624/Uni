package org.group44.parliamentbrowser.types;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.group44.parliamentbrowser.exception.BundestagException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import org.group44.parliamentbrowser.exception.BundestagJsonException;

/**
 * Enthält die Informationen der Tagesordnungen
 *
 * @author Cora, Dominik
 */
public interface Rede extends BundestagObject {

    /**
     * Gibt die Wahlperiode zurück
     *
     * @return wahlperiode
     *
     * @author Cora
     */
    public Integer getWahlperiode();

    /**
     * Setzt die Wahlperiode
     *
     * @param wahlperiode
     *
     * @author Cora
     */
    public void setWahlperiode(Integer wahlperiode);

    /**
     * Gibt die Sitzungsnummer zurück
     *
     * @return sitzungsnummer
     *
     * @author Cora
     */
	public Integer getSitzungNummer();

    /**
     * Setzt die Sitzungsnummer
     *
     * @param sitzungsNummer
     *
     * @author Cora
     */
	public void setSitzungNummer(Integer sitzungsNummer);

    /**
     * Gibt die TopId der Tagesordnung zurück
     *
     * @return TopId
     *
     * @author Cora
     */
	public String getTagesordnungTopId();

    /**
     * Setzt die TopId der Tagesordnung
     *
     * @param tagesordnungTopId
     *
     * @author Cora
     */
	public void setTagesordnungTopId(String tagesordnungTopId);

    /**
     * Gibt die RedeId zurück
     *
     * @return RedeId
     *
     * @author Cora
     */
	public String getRedeId();

    /**
     * Setzt die redeID
     *
     * @param redeId
     *
     * @author Cora
     */
	public void setRedeId(String redeId);

    /**
     * Gibt den Abgeordneten zurück, der die Rede gehalten hat
     * 
     * @return Abgeordneter der die Rede gehalten hat
     *
     * @author Cora
     */
	public String getRednerId();

    /**
     * Setzt die RednerId
     *
     * @param rednerId
     *
     * @author Cora
     */
	public void setRednerId(String rednerId);
	
    /**
     * Gibt den Redetext der Rede zurück
     * 
     * @return Redetext der Rede
     *
     * @author Cora
     */
    public String getText();

    /**
     * Setzt den Redetext
     *
     * @param text
     *
     * @author Cora
     */
    public void setText(String text);
    
    
    /**
     * Gibt die Kommentare zu der Rede zurück
     * 
     * @return Kommentare zu der Rede
     *
     * @author Cora
     */
    public List<String> getKommentare();

    /**
     * Setzt die Kommentare
     *
     * @param kommentare
     *
     * @author Cora
     */
    public void setKommentare(List<String> kommentare);
    
    /**
     * Gibt das Datum zurück, an dem die Rede gehalten wurde
     *
     * @return Datum, an dem die Rede gehalten wurde
     *
     * @author Cora
     */
    public Date getDate();

    /**
     * Setzt das Datum, an dem die Rede gehalten wurde
     *
     * @@param  date, an dem die Rede gehalten wurde
     *
     * @author Cora
     */
    public void setDate(java.util.Date date);

    /**
     * Liefert die Anzahl des Auftretens eines Stichwort in der rede
     * 
     * @param stichwort Stichwort nach dem gesucht wird
     * 
     * @return Anzahl des Auftretens des Stichwortes
     *
     * @author Cora
     */
    public int getAnzahlStichwort(String stichwort);
    
    public void clearSentences();
    
    public void applySentences(Collection<Sentence> newSentences);
    
    public boolean hasSentences();

    /**
     * Gibt alle Sätze der Rede zurück
     * @return sentences
     * @author dominik
     */

    public List<String> getSentences();

    /**
     * Setzt die Sätze einer Rede
     * @param sentences
     * @author dominik
     */

    public void setSentences(List<String> sentences);

    /**
     * Gibt alle Tokens einer Rede zurück
     * @return tokens
     * @author dominik
     */
    public List<String> getTokens();

    /**
     * Setzt alle Tokens einer Rede
     * @param tokens
     * @author dominik
     */

    public void setTokens(List<String> tokens);

    /**
     * Gibt alle Sentiments der Rede wieder
     * @return sentiments
     * @author dominik
     */
    public List<Double> getSentiments();

    /**
     * Setzt die Sentiments einer Rede
     * @param sentiments
     * @author dominik
     */
    public void setSentiments(List<Double> sentiments);

    /**
     * Gibt alle Pos Attribute einer Rede wieder
     * @return pos
     * @author dominik
     */
    public List<String> getPos();

    /**
     * setzt alle pos attribute einer rede
     * @param pos
     * @author dominik
     */
    public void setPos(List<String> pos);

    /**
     * gibt alle dependencies einer rede wieder
     * @return dependencies
     * @author dominik
     */
    public List<String> getDependencies();

    /**
     * setzt alle dependencies einer rede
     * @param dependencies
     * @author dominik
     */
    public void setDependencies(List<String> dependencies);

    /**
     * gibt alle named enteties einer rede wieder
     * @return namedEnteties
     * @author dominik
     */
    public List<String> getNamedEntities();

    /**
     * setzt alle named enteties einer rede
     * @param namedEntities
     * @author dominik
     */
    public void setNamedEntities(List<String> namedEntities);

    /**
     * Schreibt das Objekt Rede als Json weg
     *
     * @return Rede as Json string
     *
     * @throws BundestagJsonException -
     *
     * @author Cora
     */
    public String toJson() throws BundestagException;

    /**
     * Für Menschen schöner zu lesen, wird nicht gebraucht
     *
     * @return Rede as Json
     *
     * @throws BundestagJsonException -
     *
     * @author Cora
     */
    public String toJsonPretty() throws BundestagException;

    /**
     * Erstellt String in Latex Format
     * @return string
     * @author Dominik
     */
    public String toTex();

    /**
     * setzt die titel der rede
     * @param topics
     * @author dominik
     */
    public void setTopics(List<String> topics);

    /**
     * gibt die topics der reden zurück
     * @return topics
     * @author dominik
     */
    public List<String> getTopics();
    
}

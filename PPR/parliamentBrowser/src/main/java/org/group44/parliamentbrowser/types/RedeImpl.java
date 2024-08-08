package org.group44.parliamentbrowser.types;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.uima.fit.util.JCasUtil;
import org.group44.parliamentbrowser.container.Tagesordnungen;
import org.group44.parliamentbrowser.exception.BundestagJsonException;
import org.group44.parliamentbrowser.util.TypeHelper;
import org.hucompute.textimager.uima.type.Sentiment;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Informationen zu einer Rede die ein Abgeordneter gehalten hat
 * 
 * @author Cora, Dominik
 */
public class RedeImpl implements Rede {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RedeImpl.class);
	protected Integer wahlperiode;
	protected Integer sitzungNummer;
	protected String tagesordnungTopId;
	protected String redeId;
	protected String rednerId;
	protected String text = "";
	protected List<String> kommentare = new ArrayList<>();
	protected Date date;
	protected List<String> sentences = new ArrayList<>();
	protected List<String> tokens = new ArrayList<>();
	protected List<Double> sentiments = new ArrayList<>();
	protected List<String> pos = new ArrayList<>();
	protected List<String> dependencies = new ArrayList<>();
	protected List<String> namedEntities = new ArrayList<>();
	protected List<String> topics = new ArrayList<>();

	public RedeImpl() {
	}

	/**
	 * Erzeugt eine Rede eines Abgeordneten
	 *
	 * @param id           Eindeutige Id der Rede
	 * @param abgeordneter Abgeordneter der die Rede gehalten hat
	 * @param date         Datum wann die Rede gehalten wurde
	 * @return rede XML Pfad mit dem Redeinhalt ("tagesordnungspunkt")
	 */

	public RedeImpl(String id, Abgeordneter abgeordneter, Date date, Element rede) {
		this.wahlperiode = Tagesordnungen.getTagesordnung(id).getWahlperiode();
		this.sitzungNummer = Tagesordnungen.getTagesordnung(id).getSitzungNummer();
		this.tagesordnungTopId = Tagesordnungen.getTagesordnung(id).getTopId();
		this.redeId = id;
		this.rednerId = abgeordneter == null ? null : abgeordneter.getID().toString();
		this.date = date;
		boolean isRedeStarted = false;
		StringBuilder redeBeitrag = new StringBuilder();
		for (Element beitrag : rede.getChildren("p")) {
			if (beitrag.getAttributeValue("klasse") != null) {
				String klasse = beitrag.getAttributeValue("klasse");
				if (klasse.equalsIgnoreCase("J_1") && !isRedeStarted) {
					isRedeStarted = true;
					redeBeitrag.append(beitrag.getValue());
				} else if (!klasse.equalsIgnoreCase("redner") && !klasse.equalsIgnoreCase("J_1")) {
					redeBeitrag.append(beitrag.getValue());
				}
			}
		}
		for (Element beitrag : rede.getChildren("kommentar")) {
			kommentare.add(beitrag.getValue());
		}
		this.text = redeBeitrag.toString();
	}

	@Override
	public String getRednerId() {
		return rednerId;
	}

    @Override
	public void setRednerId(String rednerId) {
		this.rednerId = rednerId;
	}

	@Override
	public String getText() {
		return text;
	}

    @Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Date getDate() {
		return date;
	}
//
//	@Override
//	public void setDate(Date date) {
//		this.date = date;
//	}

	@Override
	public void setDate(java.util.Date date) {
		this.date = (date == null ? null : new java.sql.Date(date.getTime()));
	}

	@Override
	public List<String> getKommentare() {
		return kommentare;
	}

    @Override
	public void setKommentare(List<String> kommentare) {
		this.kommentare = kommentare;
	}

	@Override
	public int getAnzahlStichwort(String stichwort) {
		return text.split(stichwort, -1).length - 1;
	}

	@Override
	public Object getID() {
		return redeId;
	}

	@Override
	public Integer getWahlperiode() {
		return wahlperiode;
	}

	@Override
	public void setWahlperiode(Integer wahlperiode) {
		this.wahlperiode = wahlperiode;
	}

	@Override
	public Integer getSitzungNummer() {
		return sitzungNummer;
	}

    @Override
	public void setSitzungNummer(Integer sitzungNummer) {
		this.sitzungNummer = sitzungNummer;
	}

	@Override
	public String getTagesordnungTopId() {
		return tagesordnungTopId;
	}

    @Override
	public void setTagesordnungTopId(String tagesordnungTopId) {
		this.tagesordnungTopId = tagesordnungTopId;
	}

    @Override
	public String getRedeId() {
		return redeId;
	}

    @Override
	public void setRedeId(String redeId) {
		this.redeId = redeId;
	}

	@Override
	public void clearSentences() {
		this.sentences = new ArrayList<String>();
		this.tokens = new ArrayList<String>();
		this.pos = new ArrayList<String>();
		this.sentiments = new ArrayList<Double>();
		this.namedEntities = new ArrayList<String>();
		this.dependencies = new ArrayList<String>();
	}

    @Override
	public void applySentences(Collection<Sentence> newSentences) {
		// nun durch alle sätze gehen
		for (Sentence sentence : newSentences) {
			// füge sätze der rede hinzu
			sentences.add(sentence.getCoveredText());
			// Tokens in satz
			for (Token token : JCasUtil.selectCovered(Token.class, sentence)) {
				// füge tokens der rede hinzu
				tokens.add(token.getCoveredText());
				// füge pos values der rede hinzu
				pos.add(token.getPosValue());
			}
			// Sentiment in Satz
			for (Sentiment sentiment : JCasUtil.selectCovered(Sentiment.class, sentence)) {
				// füge sentiments der rede hinzu
				sentiments.add(sentiment.getSentiment());
			}
			// namedEnteties in Satz
			for (NamedEntity namedEntity : JCasUtil.selectCovered(NamedEntity.class, sentence)) {
				// füge named enteties der rede hinzu
				namedEntities.add(namedEntity.getCoveredText());
			}
			// Dependency in Satz
			for (Dependency dependency : JCasUtil.selectCovered(Dependency.class, sentence)) {
				// füge Dependencys der rede hinzu
				dependencies.add(dependency.getDependencyType());
			}
		}
	}

    @Override
	public boolean hasSentences() {
		return sentences != null && !sentences.isEmpty();
	}

	/**
	 * Gibt die Bezeichnung der Rede zurück
	 *
	 * @return Bezeichnung der Rede
	 */
	@Override
	public String getLabel() {
		return rednerId + "/" + redeId + "/" + TypeHelper.toDateString(date);
	}

	@Override
	public int compareTo(BundestagObject o) {
		if (o == null) {
			return -1;
		}
		return this.getID().toString().compareTo(o.getID().toString());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Rede) {
			return compareTo((Rede) o) == 0;
		}
		return false;
	}

	@Override
	public String toString() {
		return "RedeImpl{" + "redeId=" + redeId + ", " + "datum=" + TypeHelper.toDateString(date) + ", " + "rednerId="
				+ rednerId + ", " + "text=" + text + ", " + "kommentare=["
				+ kommentare.stream().collect(Collectors.joining(",")) + "]" + '}';
	}

	@Override
	public String toJson() throws BundestagJsonException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung der Rede mit der ID %s.", getID());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Rede-Objekts: {}", this);
			throw new BundestagJsonException(message, e);
		}
	}
    
    public static Rede fromJson(String json) throws BundestagJsonException {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			Rede rede = mapper.readValue(json, RedeImpl.class);
			return rede;
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Deserialisierung der Rede aus dem JSON %s.", json);
			LOGGER.error(message);
			throw new BundestagJsonException(message, e);
		}
    }

	@Override
	public String toJsonPretty() throws BundestagJsonException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung der Rede mit der ID %s.", getID());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Rede-Objekts: {}", this);
			throw new BundestagJsonException(message, e);
		}
	}

	/**
	 * erstellt String in Latex format
	 * @return String
	 * @author dominik
	 */
	@Override
	public String toTex() {
		return  "\\subsubsection{Rede von Redner: " + rednerId + "}\n " + text;
	}

	/**
	 * gibt alle sätze der rede zurück
	 * @return sentences
	 * @author dominik
	 */
	@Override
	public List<String> getSentences() {
		return sentences;
	}

	/**
	 * setzt die sätze einer rede
	 * @param sentences
	 * @author dominik
	 */
	@Override
	public void setSentences(List<String> sentences) {
		this.sentences = (sentences == null ? new ArrayList<String>() : sentences);
	}

	/**
	 * gibt alle tokens einer rede zurück
	 * @return tokens
	 * @author dominik
	 */
	@Override
	public List<String> getTokens() {
		return tokens;
	}

	/**
	 * setzt alle tokens einer rede
	 * @param tokens
	 * @author dominik
	 */
	@Override
	public void setTokens(List<String> tokens) {
		this.tokens = (tokens == null ? new ArrayList<String>() : tokens);
	}

	/**
	 * gibt alle sentiments der rede wieder
	 * @return sentiments
	 * @author dominik
	 */
	@Override
	public List<Double> getSentiments() {
		return sentiments;
	}

	/**
	 * setzt die sentiments einer rede
	 * @param sentiments
	 * @author dominik
	 */
	@Override
	public void setSentiments(List<Double> sentiments) {
		this.sentiments = (sentiments == null ? new ArrayList<Double>() : sentiments);
	}

	/**
	 * gibt alle pos attribute einer rede wieder
	 * @return pos
	 * @author dominik
	 */
	@Override
	public List<String> getPos() {
		return pos;
	}

	/**
	 * setzt alle pos attribute einer rede
	 * @param pos
	 * @author dominik
	 */
	@Override
	public void setPos(List<String> pos) {
		this.pos = (pos == null ? new ArrayList<String>() : pos);
	}

	/**
	 * gibt alle dependencies einer rede wieder
	 * @return dependencies
	 * @author dominik
	 */
	@Override
	public List<String> getDependencies() {
		return dependencies;
	}

	/**
	 * setzt alle dependencies einer rede
	 * @param dependencies
	 * @author dominik
	 */
	@Override
	public void setDependencies(List<String> dependencies) {
		this.dependencies = (dependencies == null ? new ArrayList<String>() : dependencies);
	}

	/**
	 * gibt alle named enteties einer rede wieder
	 * @return namedEnteties
	 * @author dominik
	 */
	@Override
	public List<String> getNamedEntities() {
		return namedEntities;
	}

	/**
	 * setzt alle named enteties einer rede
	 * @param namedEntities
	 * @author dominik
	 */
	@Override
	public void setNamedEntities(List<String> namedEntities) {
		this.namedEntities = (namedEntities == null ? new ArrayList<String>() : namedEntities);
	}
	/**
	 * setzt die titel der rede
	 * @param topics
	 * @author dominik
	 */
	@Override
	public void setTopics(List<String> topics) {
		this.topics = (topics == null? new ArrayList<String>() : topics);
	}

	/**
	 * gibt die topics der reden zurück
	 * @return topics
	 * @author dominik
	 */
	public List<String> getTopics(){
		return topics;
	}
}

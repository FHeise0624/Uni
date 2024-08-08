package org.group44.parliamentbrowser.types;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.group44.parliamentbrowser.exception.BundestagJsonException;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Enthält die Informationen der Tagesordnungen
 *
 * @author Cora, Dominik
 */
public class TagesordnungImpl implements Tagesordnung {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TagesordnungImpl.class);
	
	protected Integer wahlperiode;
	protected Integer sitzungNummer;
	protected String topId;
	protected String text;
	protected List<String> redenIds = new ArrayList<String>(); 
	
	public TagesordnungImpl() {}
	
	public TagesordnungImpl(String wahlperiode, String sitzungNummer, Element ivzBlock) {
		this.wahlperiode = Integer.valueOf(wahlperiode);
		this.sitzungNummer = Integer.valueOf(sitzungNummer);
		this.topId = ivzBlock.getChildText("ivz-block-titel");
		StringBuilder textBuilder = new StringBuilder();
		for (Element ivzEintrag : ivzBlock.getChildren("ivz-eintrag")) {
			Element ivzEintragInhalt = ivzEintrag.getChild("ivz-eintrag-inhalt");
			Element xref = ivzEintrag.getChild("xref");
			if (xref != null) {
				if (xref.getAttributeValue("rid") != null) {
					this.redenIds.add(xref.getAttributeValue("rid"));
				}
			} else if (ivzEintragInhalt != null && ivzEintragInhalt.getChild("redner") == null) {
				textBuilder.append(ivzEintragInhalt.getText() == null ? "" : ivzEintragInhalt.getText().trim());
			}
		}
		this.text = textBuilder.toString().replaceAll("\\r?\\n", "");
	}

	@Override
	public String getTopId() {
		return topId;
	}

    public void setTopId(String topId) {
		this.topId = topId;
	}

	@Override
	public String getText() {
		return text;
	}

    public void setText(String text) {
		this.text = text;
	}

	@Override
	public List<String> getRedenIds() {
		return redenIds;
	}

    public void setRedenIds(List<String> redenIds) {
		this.redenIds = redenIds;
	}

    @Override
    public String toString() {
        return "TagesordnungImpl{" +
                "wahlperiode=" + wahlperiode + ", " +
                "sitzung=" + sitzungNummer + ", " +
                "topId=" + topId + ", " +
                "text=" + text + ", " +
                "redenIds=[" + redenIds.stream().collect(Collectors.joining(",")) + "]" + 
                '}';
    }

    @Override
    public String toJson() throws BundestagJsonException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung der Tagesordnung mit der Top-Id %s.", getTopId());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Abgeordneten-Objekts: {}", this);
			throw new BundestagJsonException(message, e);
		}
    }
    
    @Override
    public String toJsonPretty() throws BundestagJsonException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung der Tagesordnung mit der Top-Id %s.", getTopId());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Abgeordneten-Objekts: {}", this);
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
		return "\\subsection{" + topId + "}\n" + text;
	}

    public static Tagesordnung fromJson(String json) throws BundestagJsonException {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		Tagesordnung tagesordnung = mapper.readValue(json, TagesordnungImpl.class);
			return tagesordnung;
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Deserialisierung der Tagesordnung aus dem JSON %s.", json);
			LOGGER.error(message);
			throw new BundestagJsonException(message, e);
		}
    }

	@Override
	public Object getID() {
		return wahlperiode + "-" + sitzungNummer + "-" + topId;
	}
	
	@Override
	public String getLabel() {
		return topId;
	}

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tagesordnung) {
            return compareTo((Tagesordnung)o) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(BundestagObject o) {
    	if (o == null) {
    		return -1;
    	}
        return getID().toString().compareTo(o.getID().toString());
    }

	@Override
	public Integer getWahlperiode() {
		return wahlperiode;
	}

	public void setWahlperiode(Integer wahlperiode) {
		this.wahlperiode = wahlperiode;
	}
	
	@Override
	public Integer getSitzungNummer() {
		return sitzungNummer;
	}

	public void setSitzungNummer(Integer sitzungNummer) {
		this.sitzungNummer = sitzungNummer;
	}

}

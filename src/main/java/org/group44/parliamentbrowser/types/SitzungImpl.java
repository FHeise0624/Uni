package org.group44.parliamentbrowser.types;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.group44.parliamentbrowser.exception.BundestagJsonException;
import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Enthält die Informationen der Sitzungen
 *
 * @author Cora, Dominik
*/
public class SitzungImpl implements Sitzung {

	private static Logger LOGGER = LoggerFactory.getLogger(SitzungImpl.class);

	protected Integer nummer;
	protected String ort;
	protected Date beginn;
	protected Date ende;
	protected Integer wahlperiode;
	
	public SitzungImpl() {}
	
	public SitzungImpl(Element root) {
		nummer = Integer.valueOf(root.getAttributeValue("sitzung-nr"));
		ort = root.getAttributeValue("sitzung-ort");
		beginn = TypeHelper.toDateTime(root.getAttributeValue("sitzung-datum"), root.getAttributeValue("sitzung-start-uhrzeit"));
		ende = TypeHelper.toDateTime(root.getAttributeValue("sitzung-datum"), root.getAttributeValue("sitzung-ende-uhrzeit"));
		if (ende.before(beginn)) {
			ende = new Date(Instant.ofEpochMilli(ende.getTime()).plus(1, ChronoUnit.DAYS).toEpochMilli());
		}
		wahlperiode = Integer.valueOf(root.getAttributeValue("wahlperiode"));
	}
	
	@Override
	public Integer getNummer() {
		return nummer;
	}

	@Override
	public String getOrt() {
		return ort;
	}

	@Override
	public Date getBeginn() {
		return beginn;
	}

	@Override
	public Date getEnde() {
		return ende;
	}

	@Override
	@JsonIgnore
	public Integer getDauerInMinuten() {
		long dauer = ende.getTime() - beginn.getTime();
		return Long.valueOf(TimeUnit.MINUTES.convert(dauer, TimeUnit.MILLISECONDS)).intValue();
	}
	
	@Override
	public Integer getWahlperiode() {
		return wahlperiode;
	}

	@Override
	public Object getID() {
		return wahlperiode == null ? nummer : wahlperiode.toString() + "-" + nummer;
	}

	@Override
	public String getLabel() {
		return wahlperiode.toString() + "/" + nummer + "/" + "/" + ort + "/" + TypeHelper.toDateTimeString(beginn) + "/" + TypeHelper.toDateTimeString(ende);
	}

	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}
    
	public void setOrt(String ort) {
		this.ort = ort;
	}
    
	public void setBeginn(java.util.Date beginn) {
		this.beginn = (beginn == null ? null : new Date(beginn.getTime()));
	}
    
	public void setEnde(java.util.Date  ende) {
		this.ende = (ende == null ? null : new Date(ende.getTime()));
	}
    
	public void setWahlperiode(Integer wahlperiode) {
		this.wahlperiode = wahlperiode;
	}

    @Override
    public int compareTo(BundestagObject o) {
    	if (o == null || !(o instanceof Sitzung)) {
    		return -1;
    	}
        return getNummer().compareTo(((Sitzung)o).getNummer());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sitzung) {
            return compareTo((Sitzung)o) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SitzungImpl{" +
                "nummer=" + nummer +
                ", ort=" + ort +
                ", beginn=" + TypeHelper.toDateTimeString(beginn) +
                ", ende=" + TypeHelper.toDateTimeString(ende) +
                ", wahlperiode=" + wahlperiode +
                '}';
    }
    
    @Override
    public String toJson() throws BundestagJsonException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung der Sitzung mit der ID %s.", getID());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Sitzung-Objekts: {}", this);
			throw new BundestagJsonException(message, e);
		}
    }
    
    @Override
    public String toJsonPretty() throws BundestagJsonException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung der Sitzung mit der ID %s.", getID());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Sitzung-Objekts: {}", this);
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
		return "\\section{Sitzung " + nummer + ", vom " + beginn + ", bis " + ende +
				", Wahlperiode " + wahlperiode + "}";
	}

    public static Sitzung fromJson(String json) throws BundestagJsonException {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			Sitzung sitzung = mapper.readValue(json, SitzungImpl.class);
			return sitzung;
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Deserialisierung der Sitzung aus dem JSON %s.", json);
			LOGGER.error(message);
			throw new BundestagJsonException(message, e);
		}
    }

}

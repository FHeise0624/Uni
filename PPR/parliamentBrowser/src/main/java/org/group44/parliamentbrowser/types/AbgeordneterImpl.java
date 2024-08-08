package org.group44.parliamentbrowser.types;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.group44.parliamentbrowser.exception.BundestagJsonException;
import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Die Klasse bündelt die Informationen zu einem Abgeordneten
 * 
 * @author Cora
 */
public class AbgeordneterImpl implements Abgeordneter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbgeordneterImpl.class);

    protected String abgeordneterId;
    protected String name;
    protected String vorname;
    protected String ortszusatz;
    protected String adelssuffix;
    protected String anrede;
    protected String akadTitel;
    protected Date geburtsDatum;
    protected String geburtsOrt;
    protected Date sterbeDatum;
    protected Types.GESCHLECHT geschlecht;
    protected String religion;
    protected String beruf;
    protected String vita;
    protected String parteiId;
    @JsonDeserialize(contentAs = MandatImpl.class)
    protected List<Mandat> mandate= new ArrayList<>();
    @JsonDeserialize(contentAs = MitgliedschaftImpl.class)
    protected List<Mitgliedschaft> mitgliedschaften= new ArrayList<>();
    protected List<String> redenIds = new ArrayList<>();
    protected List<String> bildUrls = new ArrayList<>();

    public AbgeordneterImpl() {
    }
    
    /**
     * Erzeugt einen Abgeordneten aus dem XML Pfad "MDB"
     *
     * @param mdb Pfad "MDB" aus den XML-Stammdaten
     */
    public AbgeordneterImpl(Element mdb) {
        this.abgeordneterId = mdb.getChild("ID").getValue();
        Element name = mdb.getChild("NAMEN").getChildren("NAME").get(mdb.getChild("NAMEN").getChildren("NAME").size() - 1);
        this.name = name.getChild("NACHNAME").getValue();
        this.vorname = name.getChild("VORNAME").getValue();
        this.ortszusatz = name.getChild("ORTSZUSATZ").getValue();
        this.adelssuffix = name.getChild("ADEL").getValue();
        this.anrede = name.getChild("ANREDE_TITEL").getValue();
        this.akadTitel = name.getChild("AKAD_TITEL").getValue();
        Element biografie = mdb.getChild("BIOGRAFISCHE_ANGABEN");
        this.geburtsDatum = TypeHelper.toDate(biografie.getChild("GEBURTSDATUM").getValue());
        this.geburtsOrt = biografie.getChild("GEBURTSORT").getValue();
        this.sterbeDatum = TypeHelper.toDate(biografie.getChild("STERBEDATUM").getValue());
        this.geschlecht = TypeHelper.toGeschlecht(biografie.getChild("GESCHLECHT").getValue());
        this.religion = biografie.getChild("RELIGION").getValue();
        this.beruf = biografie.getChild("BERUF").getValue();
        this.vita = biografie.getChild("VITA_KURZ").getValue();
   }

    @Override
    public Object getID() {
        return abgeordneterId;
    }

    public String getAbgeordneterId() {
        return abgeordneterId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVorname() {
        return vorname;
    }

    @Override
    public String getOrtszusatz() {
        return ortszusatz;
    }

    @Override
    public String getAdelssuffix() {
        return adelssuffix;
    }

    @Override
    public String getAnrede() {
        return anrede;
    }

    @Override
    public String getAkadTitel() {
        return akadTitel;
    }

    @Override
    public Date getGeburtsDatum() {
        return geburtsDatum;
    }

    @Override
    public String getGeburtsOrt() {
        return geburtsOrt;
    }

    @Override
    public Date getSterbeDatum() {
        return sterbeDatum;
    }

    @Override
    public Types.GESCHLECHT getGeschlecht() {
        return geschlecht;
    }

    @Override
    public String getReligion() {
        return religion;
    }

    @Override
    public String getBeruf() {
        return beruf;
    }

    @Override
    public String getVita() {
        return vita;
    }

    @Override
    public String getParteiId() {
        return parteiId;
    }

	@Override
	public void setParteiId(String parteiId) {
		this.parteiId = parteiId;
	}

	@Override
	public List<Mandat> getMandate() {
		return mandate;
	}

	@Override
	public Mandat getMandat(String wahlperiodeId) {
    	for (Mandat mandat : mandate) {
    		if (mandat.getWahlperiodeId().equals(wahlperiodeId)) {
    			return mandat;
    		}
		}
    	return null;
	}

	@Override
	public void addMandat(Mandat mandat) {
		this.mandate.add(mandat);
	}

	@Override
	public List<Mitgliedschaft> getMitgliedschaften() {
		return mitgliedschaften;
	}

	@Override
	public List<Mitgliedschaft> getMitgliedschaften(String wahlperiodeId) {
		List<Mitgliedschaft> mitgliedschaftenInWahlperiode = new ArrayList<>();
		for (Mitgliedschaft mitgliedschaft : mitgliedschaften) {
			if (mitgliedschaft.getWahlperiodeId().equals(wahlperiodeId)) {
				mitgliedschaftenInWahlperiode.add(mitgliedschaft);
			}
		}
		return mitgliedschaftenInWahlperiode;
	}

	@Override
	public void addMitgliedschaft(Mitgliedschaft mitgliedschaft) {
		this.mitgliedschaften.add(mitgliedschaft);
	}

    @Override
    public void addRedeId(String redeId) {
        this.redenIds.add(redeId);
    }

    @Override
    public List<String> getRedenIds() {
        return this.redenIds;
    }

    /**
     * Gibt die Fraktion, der der Abgeordnete zuletzt angehört hat, zurück
     *
     * @return  Fraktion, der der Abgeordnete zuletzt angehört hat
     */
    @JsonIgnore
    @BsonIgnore
    public Gruppe getFraktion() {
    	Mandat letztesMandat = getLastMandat();
    	List<Mitgliedschaft> letzteMitgliedschaften = getMitgliedschaften(letztesMandat.getWahlperiodeId());
    	for (Mitgliedschaft mitgliedschaft : letzteMitgliedschaften) {
			if (mitgliedschaft.getGruppe().getTyp().equals(Types.GRUPPE_TYP.Fraktion_Gruppe)) {
				return mitgliedschaft.getGruppe();
			}
		}
    	return null;
    }

    /**
     * Gibt das letzte Mandat des Abgeordneten zurück
     *
     * @return das letzte Mandat des Abgeordneten
     */
    @JsonIgnore
    public Mandat getLastMandat() {
    	Mandat letztesMandat = null;
    	for (Mandat mandat : mandate) {
			if (letztesMandat == null) {
				letztesMandat = mandat;
			} else {
				if (mandat.getWahlperiodeId().compareTo(letztesMandat.getWahlperiodeId()) > 0) {
					letztesMandat = mandat;
				}
			}
		}
    	return letztesMandat;
    }

    /**
     * Gibt das Label (Informationen, mit denen man jeden Abgeordneten erkennt) zurück
     *
     * @return string aus vorname, name, ortszusatz
     */
    @Override
    public String getLabel() {
        return vorname + " " + name + (ortszusatz == null || ortszusatz.trim().length() == 0 ? "" : " " + ortszusatz);
    }
    
    /**
     * Gibt die Liste der Funktionen eines Abgeordneten in einer Wahlperdiode zurück, z.B. "Ordentliches Mitglied"
     *
     * @return Liste der Funktionen eines Abgeordneten in einer Wahlperdiode
     */
    @JsonIgnore
	public List<String> getFunktionen(Wahlperiode wahlperiode) {
		List<String> funktionen = new ArrayList<>();
		if (mitgliedschaften != null && !mitgliedschaften.isEmpty()) {
			for (Mitgliedschaft mitgliedschaft : mitgliedschaften) {
				if (mitgliedschaft.getFunktion() != null && mitgliedschaft.getFunktion().trim().length() > 0) {
					funktionen.add(mitgliedschaft.getFunktion());
				}
			}
		}
		return funktionen;
	}

    public void setAbgeordneterId(String abgeordneterId) {
		this.abgeordneterId = abgeordneterId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setOrtszusatz(String ortszusatz) {
		this.ortszusatz = ortszusatz;
	}

	public void setAdelssuffix(String adelssuffix) {
		this.adelssuffix = adelssuffix;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public void setAkadTitel(String akadTitel) {
		this.akadTitel = akadTitel;
	}

	public void setGeburtsDatum(java.util.Date geburtsDatum) {
		this.geburtsDatum = geburtsDatum == null ? null : new Date(geburtsDatum.getTime());
	}

	public void setGeburtsOrt(String geburtsOrt) {
		this.geburtsOrt = geburtsOrt;
	}

	public void setSterbeDatum(java.util.Date  sterbeDatum) {
		this.sterbeDatum = sterbeDatum == null ? null : new Date(sterbeDatum.getTime());
	}

	public void setGeschlecht(Types.GESCHLECHT geschlecht) {
		this.geschlecht = geschlecht;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public void setBeruf(String beruf) {
		this.beruf = beruf;
	}

	public void setVita(String vita) {
		this.vita = vita;
	}

	public void setMandate(List<Mandat> mandate) {
		this.mandate = mandate;
	}

	public void setMitgliedschaften(List<Mitgliedschaft> mitgliedschaften) {
		this.mitgliedschaften = mitgliedschaften;
	}

	public void setRedenIds(List<String> redenIds) {
		this.redenIds = redenIds;
	}

    @Override
    public boolean equals(Object o) {
        if (o instanceof Abgeordneter) {
            return compareTo((Abgeordneter)o) == 0;
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
    public String toString() {
        return "AbgeordneterImpl{" +
                "abgeordneterId=" + abgeordneterId +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", ortszusatz='" + ortszusatz + '\'' +
                ", adelssuffix='" + adelssuffix + '\'' +
                ", anrede='" + anrede + '\'' +
                ", akadTitel='" + akadTitel + '\'' +
                ", geburtsDatum=" + geburtsDatum +
                ", geburtsOrt='" + geburtsOrt + '\'' +
                ", sterbeDatum=" + sterbeDatum +
                ", geschlecht=" + geschlecht +
                ", religion='" + religion + '\'' +
                ", beruf='" + beruf + '\'' +
                ", vita='" + vita + '\'' +
                ", parteiId=" + parteiId +
                ", mandate=[" + mandate.stream().map(mandat -> mandat.toString()).collect(Collectors.joining(",")) + "]" +
                ", mitgliedschaften=[" + mitgliedschaften.stream().map(mitgliedschaft -> mitgliedschaft.toString()).collect(Collectors.joining(",")) + "]" +
                '}';
    }
    
    @Override
    public String toJson() throws BundestagJsonException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
			return objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Serialisierung des Abgeordneten mit der ID %s.", getID());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Abgeordneten-Objekts: {}", this);
			throw new BundestagJsonException(message, e);
		}
    }
    
    public static Abgeordneter fromJson(String json) throws BundestagJsonException {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			Abgeordneter abgeordneter = mapper.readValue(json, AbgeordneterImpl.class);
			return abgeordneter;
		} catch (JsonProcessingException e) {
			String message = String.format("Fehler bei der JSON-Deserialisierung des Abgeordneten aus dem JSON %s.", json);
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
			String message = String.format("Fehler bei der JSON-Serialisierung des Abgeordneten mit der ID %s.", getID());
			LOGGER.error("Fehler aufgetreten bei der JSON-Serialisierung des Abgeordneten-Objekts: {}", this);
			throw new BundestagJsonException(message, e);
		}
    }

    public void addBildURL(String bildURL){
        this.bildUrls.add(bildURL);
    }

    public List<String> getBilder(){
        return bildUrls;
    }

    public void setBilder(List<String> bildUrls) {
    	this.bildUrls = bildUrls;
    }

//
//  /**
//   * Gibt die Dauer der Bundestagszugehoerigkeit eines Abgeordneten zurück
//   *
//   * @return  Dauer der Bundestagszugehoerigkeit eines Abgeordneten
//   */
//  public int getDauerBundestagszugehoerigkeit() {
//      int dauerBundestagszugehoerigkeit = 0;
//      for (Mandat mandat : getMandate()) {
//          Wahlperiode wp = mandat.getWahlperiode();
//          dauerBundestagszugehoerigkeit += TimeUnit.MILLISECONDS.toDays(wp.getEndDate() == null ? System.currentTimeMillis() - wp.getStartDate().getTime() : wp.getEndDate().getTime() - wp.getStartDate().getTime());
//      }
//      return dauerBundestagszugehoerigkeit;
//  }

}

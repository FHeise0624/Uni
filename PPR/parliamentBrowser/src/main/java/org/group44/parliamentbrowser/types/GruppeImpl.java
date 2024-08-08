package org.group44.parliamentbrowser.types;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.group44.parliamentbrowser.types.Types.GRUPPE_TYP;
import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Enthält die Informationen der Gruppen
 *
 * @author Cora
 */
public class GruppeImpl implements Gruppe {

	private String wahlperiodeId;
	private Types.GRUPPE_TYP typ;
	private String name;
	private Date startDate;
	private Date endDate;
	@JsonIgnore
	private List<String> mitgliederIds = new ArrayList<>();
	
	public GruppeImpl() {}
	
	public GruppeImpl(Element elementInstitution, Wahlperiode wahlperiode) {
		this.wahlperiodeId = wahlperiode.getID().toString();
		this.typ = GRUPPE_TYP
				.valueOf(elementInstitution.getChildText("INSART_LANG").replace('/', '_').replace(' ', '_').replace('-', '_'));
		this.name = elementInstitution.getChildText("INS_LANG");
		this.startDate = TypeHelper.toDate(elementInstitution.getChildText("MDBINS_VON"));
		this.endDate = TypeHelper.toDate(elementInstitution.getChildText("MDBINS_BIS"));
	}

	@Override
	public String getWahlperiodeId() {
		return wahlperiodeId;
	}
	
	@Override
	public Types.GRUPPE_TYP getTyp() {
		return typ;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public List<String> getMitgliederIds() {
		return mitgliederIds;
	}

	@Override
	public void addMitglied(Abgeordneter mitglied) {
		mitgliederIds.add(mitglied.getID().toString());
	}

	@Override
	public Object getID() {
		return wahlperiodeId + "#" + typ.name() + "#" + name;
	}

	@Override
	public String getLabel() {
		return wahlperiodeId + ": " + typ.name() + " / " + name + " (" + TypeHelper.toDateString(startDate) + " - "
				+ (TypeHelper.toDateString(endDate) == null ? "aktiv" : TypeHelper.toDateString(endDate)) + " + ";
	}

	public void setWahlperiodeId(String wahlperiodeId) {
		this.wahlperiodeId = wahlperiodeId;
	}

	public void setTyp(Types.GRUPPE_TYP typ) {
		this.typ = typ;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = (startDate == null ? null : new Date(startDate.getTime()));
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = (endDate == null ? null : new Date(endDate.getTime()));
	}

	public void setMitgliederIds(List<String> mitgliederIds) {
		this.mitgliederIds = mitgliederIds;
	}

	@Override
	public int compareTo(BundestagObject o) {
    	if (o == null) {
    		return -1;
    	}
		return getID().toString().compareTo(o.getID().toString());
	}

    @Override
    public boolean equals(Object o) {
        if (o instanceof Gruppe) {
            return compareTo((Gruppe)o) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "GruppeImpl{" +
                "wahlperiodeId=" + wahlperiodeId + 
                ", typ=" + typ.name() +
                ", name=" + name +
                ", startDate=" + TypeHelper.toDateString(startDate) +
                ", endDate=" + TypeHelper.toDateString(endDate) +
                ", mitgliederIds=[" + mitgliederIds.stream().collect(Collectors.joining(",")) + "]" + 
                '}';
    }

}

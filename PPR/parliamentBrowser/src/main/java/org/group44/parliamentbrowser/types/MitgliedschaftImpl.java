package org.group44.parliamentbrowser.types;

import java.sql.Date;

import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Alle Informationen zu einer Mitgliedschaft eines Abgeordneten, z.B. zu einer
 * Mitgliedschaft in einer Fraktion oder Ausschuss
 * 
 * @author Cora
 */
public class MitgliedschaftImpl implements Mitgliedschaft {

	private String abgeordneterId;
	private String wahlperiodeId;
	private String funktion;
	private Date startDate;
	private Date endDate;
	private GruppeImpl gruppe;

	public MitgliedschaftImpl() {}
	
	/**
	 * Erzeugt eine Mitgliedschaft für einen Abgeordneten in einer Gruppe für eine
	 * Wahlperiode
	 *
	 * @param elementInstitution XML Element das die Daten der Mitgleidschaft
	 *                           enthält
	 * @param abgeordneter       Abgeordnneter für den die Mitgleidschaft erstellt
	 *                           werden soll
	 * @param wahlperiode        Wahlperiode in der die Mitgleidschaft besteht
	 * @param gruppe             Gruppe zu der eine Mitgleidschaft besteht
	 */
	public MitgliedschaftImpl(Element elementInstitution, Abgeordneter abgeordneter, Wahlperiode wahlperiode,
			GruppeImpl gruppe) {
		this.funktion = elementInstitution.getChildText("FKT_LANG");
		this.startDate = TypeHelper.toDate(elementInstitution.getChildText("FKTINS_VON"));
		this.endDate = TypeHelper.toDate(elementInstitution.getChildText("FKTINS_BIS"));
		this.abgeordneterId = abgeordneter.getID().toString();
		this.wahlperiodeId = wahlperiode.getID().toString();
		this.gruppe = gruppe;
	}

	@Override
	public String getAbgeordneterId() {
		return abgeordneterId;
	}

	@Override
	public String getWahlperiodeId() {
		return wahlperiodeId;
	}

	@Override
	public String getFunktion() {
		return funktion;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Gibt an, ob die Mitgliedschaft aktiv ist
	 *
	 * @return boolean, wahr (wenn aktiv), falsch (wenn inaktiv)
	 */
	@JsonIgnore
	public boolean isActive() {
		return getEndDate() == null;
	}

	/**
	 * Gibt die Gruppe zurück deren Mitglied der Abgeordnete ist
	 *
	 * @return Gruppe deren Mitglied der Abgeordnete ist
	 */
	@Override
	public GruppeImpl getGruppe() {
		return gruppe;
	}

	@Override
	public String toString() {
		return "MitgliedschaftImpl{" + 
				"abgeordneterId=" + abgeordneterId + 
				", wahlperiodeId=" + wahlperiodeId + 
				", funktion='" + funktion + '\'' + 
				", startDate=" + TypeHelper.toDateString(startDate) + 
				", endDate=" + TypeHelper.toDateString(endDate) + 
				", gruppe=" + gruppe.toString() + 
				'}';
	}

	@Override
	public Object getID() {
		return abgeordneterId + "/" + wahlperiodeId + "/" + gruppe.getID() + "/" + funktion + "/"
				+ TypeHelper.toDateString(startDate) + "/"
				+ (TypeHelper.toDateString(endDate) == null ? "aktiv" : TypeHelper.toDateString(endDate));
	}

	@Override
	public String getLabel() {
		return abgeordneterId + "/" + wahlperiodeId + "/" + gruppe.getID() + "/" + funktion + "/"
				+ TypeHelper.toDateString(startDate) + "/"
				+ (TypeHelper.toDateString(endDate) == null ? "aktiv" : TypeHelper.toDateString(endDate));
	}

	public void setAbgeordneterId(String abgeordneterId) {
		this.abgeordneterId = abgeordneterId;
	}

	public void setWahlperiodeId(String wahlperiodeId) {
		this.wahlperiodeId = wahlperiodeId;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = (startDate == null ? null : new Date(startDate.getTime()));
	}

	public void setEndDate(java.util.Date  endDate) {
		this.endDate = (endDate == null ? null : new Date(endDate.getTime()));
	}

	public void setGruppe(GruppeImpl gruppe) {
		this.gruppe = gruppe;
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
		if (o instanceof Mitgliedschaft) {
			return compareTo((Mitgliedschaft) o) == 0;
		}
		return false;
	}

}

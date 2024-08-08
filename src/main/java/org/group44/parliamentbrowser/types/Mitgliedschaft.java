package org.group44.parliamentbrowser.types;

import java.sql.Date;

/**
 * Enthält die Informationen der Mitgliedschaften im Bundestag
 *
 * @author Cora
 */
public interface Mitgliedschaft extends BundestagObject {

	/**
	 * Gibt die Id des Abgeordneten zurück für den die Mitgliedschaft besteht
	 * 
	 * @return Id des Abgeordneten für den die Mitgliedschaft besteht
	 */
	public String getAbgeordneterId();

	/**
	 * Gibt die Id der Wahlperiode zurück, in der die Mitgliedschaft bestand
	 * 
	 * @return Id der Wahlperiode, in der die Mitgliedschaft bestand
	 */
	public String getWahlperiodeId();

	/**
	 * Gibt die Funktion des Abgeordneten in der Mitgliedschaft zurück
	 *
	 * @return Funktion des Abgeordneten in der Mitgliedschaft
	 */
	public String getFunktion();

	/**
	 * Gibt den Beginn der Mitgliedschaft zurück
	 * 
	 * @return Beginn der Mitgliedschaft
	 */
	public Date getStartDate();

	/**
	 * Gibt das Ende der Mitgliedschaft zurück
	 * 
	 * @return Ende der Mitgliedschaft
	 */
	public Date getEndDate();

	/**
	 * Gibt die Gruppe zurück, in der die Mitgliedschaft besteht
	 * 
	 * @return Gruppe, in der die Mitgliedschaft besteht
	 */
	public Gruppe getGruppe();

}

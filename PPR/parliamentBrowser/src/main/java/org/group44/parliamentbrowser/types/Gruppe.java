package org.group44.parliamentbrowser.types;

import java.util.Date;
import java.util.List;

/**
 * Enthält die Informationen der Gruppen
 *
 * @author Cora
 */
public interface Gruppe extends BundestagObject{

	
	public String getWahlperiodeId();
	
	/**
	 * Gibt den Typ der Gruppe zurück: Fraktion, Ausschuss, Unterausschuss,...
	 * 
	 * @return Typ der Gruppe zurück: Fraktion, Ausschuss, Unterausschuss,...
	 */
	public Types.GRUPPE_TYP getTyp();
	
	/**
	 * Gibt den Namen der Gruppe zurück
	 * 
	 * @return Name der Gruppe, bspw. Wirtschaftsausschuss
	 */
	public String getName();
	
	/**
	 * Gibt das Datum zurück, seit dem die Gruppe besteht
	 * 
	 * @return Datum seit dem die Gruppe besteht
	 */
	public Date getStartDate();
	
	/**
	 * Gibt das Datum zurück bis zu dem die Gruppe bestand
	 * 
	 * @return Datum bis zu dem die Gruppe bestand (NULL, wenn die Gruppe noch
	 *         besteht)
	 */
	public Date getEndDate();

	/**
	 * Gibt die Abgeordneten Ids der Mitglieder der Gruppe zurück
	 * 
	 * @return Abgeordneten Ids der Mitglieder der Gruppe zurück
	 */
	public List<String> getMitgliederIds();

	/**
	 * Fügt der Gruppe ein Mitglied hinzu
	 * 
	 * @param mitglied Mitglied das der Gruppe hinzugefügt werden soll
	 */
	public void addMitglied(Abgeordneter mitglied);

}

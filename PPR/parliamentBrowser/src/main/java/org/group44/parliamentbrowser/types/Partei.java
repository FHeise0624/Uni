package org.group44.parliamentbrowser.types;

import java.util.List;


/**
 * Enthält die Informationen einer Partei
 *
 * @author Cora
 */
public interface Partei extends BundestagObject {
	
	/**
	 * Gibt den Namen der Partei zurück
	 *
	 * @return Name der Partei
	 */
	public String getName();

	/**
	 * Fügt der Parte ein Mitglied hinzu
	 *
	 * @param abgeordneterId - Id des Abgeordneten, der Mitglied der Partei ist
	 */
	public void addMitgliedId(String abgeordneterId);
	
	/**
	 * Gibt die Abgeordneten-Ids der Mitglieder der Partei zurück
	 *
	 * @return Abgeordneten Id der Mitglieder der Partei
	 */
	public List<String> getMitgliederIds();
	
}

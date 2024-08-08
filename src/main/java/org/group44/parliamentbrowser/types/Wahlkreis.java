package org.group44.parliamentbrowser.types;

/**
 * Enthält die Informationen der Wahlkreise
 *
 * @author Cora
 */
public interface Wahlkreis extends BundestagObject {

	/**
	 * Gibt die Nummer des Wahlkreises zurück
	 *
	 * @return Nummer des Wahlkreises
	 */
	public Integer getNumber();

	/**
	 * Gibt den Namen des Wahlkreises zurück
	 *
	 * @return Name des Wahlkreises
	 */
	public String getName();

	/**
	 * Gibt das Bundesland zurück, in dem der Wahlreis liegt
	 *
	 * @return Bundesland, in dem der Wahlreis liegt
	 */
	public String getLand();

}

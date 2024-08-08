package org.group44.parliamentbrowser.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Basisklasse für alle Entitäten
 * 
 * @author Cora
 */
public interface BundestagObject extends Comparable<BundestagObject> {

	/**
	 * ID des Objektes
	 *
	 * @return id des Objekts
	 */
	@JsonIgnore
	public Object getID();

	/**
	 * Label des Objektes
	 * 
	 * @return label des Objekts
	 */
	@JsonIgnore
	public String getLabel();

}

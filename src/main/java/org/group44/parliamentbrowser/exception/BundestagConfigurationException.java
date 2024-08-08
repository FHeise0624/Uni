package org.group44.parliamentbrowser.exception;

/**
 * Exception für Fehler in der Konfiguration
 * 
 * @author Cora
 */
public class BundestagConfigurationException extends BundestagException {
	
	private static final long serialVersionUID = 4648832844356461094L;

	public BundestagConfigurationException(String message) {
		super(message);
	}

}

package org.group44.parliamentbrowser.exception;

/**
 * Exception für Fehler bei der JSON Serialisierung/Deserialisierung
 * 
 * @author Cora
 */
public class BundestagJsonException extends BundestagException {

	private static final long serialVersionUID = -987717009995338555L;

	public BundestagJsonException(String message) {
		super(message);
	}

	public BundestagJsonException(String message, Exception cause) {
		super(message, cause);
	}

}

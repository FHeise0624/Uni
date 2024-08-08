package org.group44.parliamentbrowser.exception;

/**
 * Exception für allgemeine Fehler
 * 
 * @author Cora
 */
public class BundestagException extends RuntimeException {

	private static final long serialVersionUID = -6510536921455550137L;

	public BundestagException(String message) {
		super(message);
	}

	public BundestagException(String message, Exception cause) {
		super(message, cause);
	}

}

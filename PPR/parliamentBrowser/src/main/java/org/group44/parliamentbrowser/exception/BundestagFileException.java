package org.group44.parliamentbrowser.exception;

/**
 * Exception für Fehler beim Dateizugriff
 * 
 * @author Cora
 */
public class BundestagFileException extends BundestagException {

	private static final long serialVersionUID = 5679973239002161427L;

	public BundestagFileException(String message) {
		super(message);
	}

	public BundestagFileException(String message, Exception cause) {
		super(message, cause);
	}
}

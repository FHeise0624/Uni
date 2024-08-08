package org.texttechnologylab.project.exception;

/**
 * BundestagsException für Fehler
 * @author Giuseppe Abrami
 */
public class BundestagException extends Exception{

    public BundestagException() {
    }

    public BundestagException(Throwable pCause) {
        super(pCause);
    }

    public BundestagException(String pMessage) {
        super(pMessage);
    }

    public BundestagException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }


}

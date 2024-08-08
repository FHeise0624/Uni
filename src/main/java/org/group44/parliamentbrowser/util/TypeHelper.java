package org.group44.parliamentbrowser.util;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.group44.parliamentbrowser.types.Types;

/**
 * Hilfsklasse die Methoden enthält um Typen zu konvertieren
 * 
 * @author Cora
 */
public class TypeHelper {

	private static final SimpleDateFormat DATUM = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat DATUM_UHRZEIT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	private static final SimpleDateFormat TIMESTAMP = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    /**
     * Wandelt einen String im Format "dd.MM.yyyy HH:mm:ss" in ein SQL Date
     *
     * @param datum String im Format "dd.MM.yyyy HH:mm:ss"
     *
     * @return Entsprechend SQL Date
     */
    public static Date toTimeStamp(String timestamp) {
        try {
            return new Date(TIMESTAMP.parse(timestamp).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Wandelt ein Date in einen String im Format "dd.MM.yyyy HH:mm:ss"
     *
     * @param date Datum das ausgegeben werden soll
     *
     * @return Zeitstempel als String im Format "dd.MM.yyyy HH:mm:ss"
     */
    public static String toTimeStamp(Date date) {
    	if (date == null) {
    		return null;
    	}
    	return TIMESTAMP.format(date);
    }

    /**
     * Wandelt einen String im Format "dd.MM.yyyy" in ein SQL Date
     *
     * @param datum String im Format "dd.MM.yyyy"
     *
     * @return Entsprechend SQL Date
     */
    public static Date toDate(String datum) {
        try {
            return new Date(DATUM.parse(datum).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Wandelt Strings im Format "dd.MM.yyyy" und "HH:mm" in ein SQL Date
     *
     * @param datum String im Format "dd.MM.yyyy"
     * @param time String im Format "HH:mm"
     *
     * @return Entsprechend SQL Date
     */
    public static Date toDateTime(String datum, String time) {
        try {
            return new Date(DATUM_UHRZEIT.parse(datum + " " + time.replace('.', ':')).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Wandelt Strings im Format "dd.MM.yyyy HH:mm" in ein SQL Date
     *
     * @param timestamp String im Format "dd.MM.yyyy HH:mm"
     *
     * @return Entsprechend SQL Date
     */
    public static Date toDateTime(String timestamp) {
        try {
            return new Date(DATUM_UHRZEIT.parse(timestamp).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    
    /**
     * Wandelt ein Date in einen String im Format "dd.MM.yyyy"
     *
     * @param date Datum das ausgegeben werden soll
     *
     * @return Zeitstempel als String im Format "dd.MM.yyyy"
     */
    public static String toDateString(Date date) {
    	if (date == null) {
    		return null;
    	}
    	return DATUM.format(date);
    }

    /**
     * Wandelt einen Date in einen String im Format "dd.MM.yyyy HH:mm"
     *
     * @param date Datum das ausgegeben werden soll
     *
     * @return Zeitstempel als String im Format "dd.MM.yyyy HH:mm"
     */
    public static String toDateTimeString(Date date) {
    	if (date == null) {
    		return null;
    	}
    	return DATUM_UHRZEIT.format(date);
    }

    /**
     * Wandelt einen String in den Typ GESCHLECHT
     *
     * @param geschlecht String der umgewandelt werden soll ("männlich" oder "weiblich", Groß-/Kleinschreibung wird ignoriert)
     *
     * @return Enum Geschlecht
     */
    public static Types.GESCHLECHT toGeschlecht(String geschlecht) {
        if (geschlecht.equalsIgnoreCase("männlich")) {
            return Types.GESCHLECHT.MAENNLICH;
        } else if (geschlecht.equalsIgnoreCase("weiblich")) {
            return Types.GESCHLECHT.WEIBLICH;
        } else {
            return null;
        }
    }

    /**
     * Wandelt einen String in das Enum MANDAT
     *
     * @param mandat String der umgewandelt werden soll ("direktwahl" oder "landesliste", Groß-/Kleinschreibung wird ignoriert)
     *
     * @return Enum Mandat
     */
    public static Types.MANDAT toMandat(String mandat) {
        if (mandat.equalsIgnoreCase("direktwahl")) {
            return  Types.MANDAT.DIREKTWAHL;
        } else if (mandat.equalsIgnoreCase("landesliste")) {
            return Types.MANDAT.LANDESLISTE;
        }
        return null;
    }

    /**
     * Wandelt einen String in ein Integer
     *
     * @param value String-Darstellung einer Zahl, z.B. "15"
     *
     * @return Wert des String als Integer, z.B. 15
     */
    public static Integer ToInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

}

package org.group44.parliamentbrowser.types;

import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;

/**
 * Informationen zum Wahlkreis
 * 
 * @author Cora
 */
public class WahlkreisImpl implements Wahlkreis {

    private Integer number;
    private String name;
    private String land;
    
    /**
     * Erzeugt eines Wahlkreis aus dem XML Pfad "WAHLPERIODE"
     *
     * @param wahlperiode XML Pfad "WAHLPERIODE" der die Wahhlkreisinformationen enthölt
     */
    public WahlkreisImpl(Element wahlperiode) {
        this.number = TypeHelper.ToInteger(wahlperiode.getChildText("WKR_NUMMER"));
        this.name = wahlperiode.getChildText("WKR_NAME");
        this.land = wahlperiode.getChildText("WKR_LAND");
    }

    /**
     * Gibt die Nummer des Wahlkreises zurück
     *
     * @return Nummer des Wahlkreises
     */
    @Override
    public Integer getNumber() {
        return number;
    }

    /**
     * Gibt die Bezeichnung des Wahlkreis zurück
     *
     * @return Bezeichnung des Wahlkreis
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gibt das Land des Wahlkreis zurück
     *
     * @return Land des Wahlkreis
     */
    @Override
    public String getLand() {
        return land;
    }

    @Override
    public Object getID() {
        return String.valueOf(number);
    }

    @Override
    public String getLabel() {
        return String.valueOf(number) + ":" + name + " (" + land + ")";
    }

    @Override
    public int compareTo(BundestagObject o) {
    	if (o == null) {
    		return -1;
    	}
        return this.getID().toString().compareTo(o.getID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Wahlkreis) {
            return compareTo((Wahlkreis)o) == 0;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "WahlkreisImpl{" +
                "number=" + number + ", " +
                "name=" + name + ", " + 
                "land=" + land +
                '}';
    }}

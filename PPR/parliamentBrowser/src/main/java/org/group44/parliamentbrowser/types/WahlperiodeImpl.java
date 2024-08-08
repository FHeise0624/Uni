package org.group44.parliamentbrowser.types;

import java.sql.Date;

import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;

/**
 * Informationen zu einer Wahlperiode
 * 
 * @author Cora
 */
public class WahlperiodeImpl implements Wahlperiode {

    private Integer number;
    private Date startDate;
    private Date endDate;

    /**
     * Erzeugt eine Wahlperiode aus dem XML Pfad "WAHLPERIODE"
     *
     * @param wahlperiode XML Pfad "WAHLPERIODE"
     */
    public WahlperiodeImpl(Element wahlperiode) {
        this.number = TypeHelper.ToInteger(wahlperiode.getChild("WP").getValue());
        this.startDate = TypeHelper.toDate(wahlperiode.getChildText("MDBWP_VON"));
        this.endDate = TypeHelper.toDate(wahlperiode.getChildText("MDBWP_BIS"));
    }
    /**
     * Gibt die Nummer der Wahlperiode zurück
     *
     * @return Nummer der Wahlperiode
     */
    @Override
    public Integer getNumber() {
        return this.number;
    }

    /**
     * Gibt das Startdatum der Wahlperiode zurück
     *
     * @return Startdatum der Wahlperiode
     */
    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Gibt das Datum zurück, an dem die Wahlperiode endet
     *
     * @return Datum, an dem die Wahlperiode endet
     */
    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public Object getID() {
        return String.valueOf(number);
    }

    @Override
    public String getLabel() {
        return String.valueOf(number);
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
        if (o instanceof Wahlperiode) {
            return compareTo((Wahlperiode)o) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "WahlperiodeImpl{" +
                "number=" + number +
                ", startDate=" + TypeHelper.toDateString(startDate) +
                ", endDate=" + TypeHelper.toDateString(endDate) +
                '}';
    }
}

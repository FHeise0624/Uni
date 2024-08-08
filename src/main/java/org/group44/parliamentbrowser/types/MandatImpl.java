package org.group44.parliamentbrowser.types;

import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;

/**
 * Enthält die Informationen zu einem Mandat (ein Abgordneter erhölt ein Mandat in einem Wahlkreis für eine Wahlperdiode)
 * 
 * @author Cora
 */
public class MandatImpl implements Mandat {

    private Types.MANDAT typ;
    private String abgeordneterId;
    private String wahlperiodeId;
    private String wahlkreisId;

    public MandatImpl() {}
    
    /**
     * Erzeugt ein Mandat für einen Abgeordneten aus dem XML Pfad "WAHLPERIODE"
     *
     * @param wahlperiode XML Pfad "WAHLPERIODE" mit den Mandatsinformationen
     */
    public MandatImpl(Element elementWahlperiode, Wahlperiode wahlperiode, Wahlkreis wahlkreis, Abgeordneter abgeordneter) {
        this.typ =  TypeHelper.toMandat(elementWahlperiode.getChild("MANDATSART").getValue());
        this.wahlperiodeId = wahlperiode.getID().toString();
        this.wahlkreisId = wahlkreisId == null ? null :  wahlkreis.getID().toString();
        this.abgeordneterId = abgeordneter.getID().toString();
    }

	@Override
    public Types.MANDAT getTyp() {
        return typ;
    }

	@Override
	public String getWahlperiodeId() {
		return wahlperiodeId;
	}

	@Override
    public String getWahlkreisId() {
        return wahlkreisId;
    }

	@Override
    public String getAbgeordneterId() {
    	return abgeordneterId;
    }
    
    @Override
    public Object getID() {
    	return wahlperiodeId + "/" + (this.typ == null ? "NULL" : this.typ.name());
    }

    @Override
    public String getLabel() {
    	return "Abgeordneter mit Id " + abgeordneterId + ": " + (this.typ == null ? "NULL" : this.typ.name()) + " in Wahlperiode mit Id " + wahlperiodeId + ", im Wahlkreis mit der Id " + wahlkreisId;
    }

    public void setTyp(Types.MANDAT typ) {
		this.typ = typ;
	}

	public void setAbgeordneterId(String abgeordneterId) {
		this.abgeordneterId = abgeordneterId;
	}

	public void setWahlperiodeId(String wahlperiodeId) {
		this.wahlperiodeId = wahlperiodeId;
	}

	public void setWahlkreisId(String wahlkreisId) {
		this.wahlkreisId = wahlkreisId;
	}

	@Override
    public int compareTo(BundestagObject o) {
    	if (o == null) {
    		return -1;
    	}
        return getID().toString().compareTo(o.getID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Mandat) {
            return compareTo((Mandat)o) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "MandatImpl{" +
                "abgeordneterId=" + abgeordneterId +
                ", typ=" + (this.typ == null ? "NULL" : this.typ.name()) +
                ", wahlperiodeId=" + wahlperiodeId +
                ", wahlkreisId=" + wahlkreisId  +
                '}';
    }
}

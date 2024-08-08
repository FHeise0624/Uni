package org.group44.parliamentbrowser.builder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.group44.parliamentbrowser.types.Abgeordneter;
import org.group44.parliamentbrowser.types.Rede;
import org.group44.parliamentbrowser.types.RedeImpl;
import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Klasse erzeugt aus Plenarprotokollen die Rede-Objekt die in der Datenbank gespeichert werden können.
 * 
 * @author Cora
 */
public class RedeBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedeBuilder.class);

	public static List<String> FAULTY_REDEN_IDS = new ArrayList<String>();
	
	private List<Abgeordneter> abgeordneten;
	
	/**
	 * Erzeugt den RedeBuilder. Um Reden zu erzeugen benötigt man Abgeordneten Informationen
	 *  
	 * @param abgeordneten - Liste mit allen Abgeordneten
	 */
	public RedeBuilder(List<Abgeordneter> abgeordneten) {
		this.abgeordneten = abgeordneten;
		FAULTY_REDEN_IDS.clear();
	}
	
	/**
	 * Erzeugt aus XML-Dokumenten Rede-Objekte
	 * 
	 * @param documents - mehrere XML Plenarprotokoll
	 * 
	 * @return Liste von erzeugten Rede-Objekten
	 */
	public List<Rede> from(List<Document> documents) {
        List<Rede> reden = new ArrayList<>();
        for (Document document : documents) {
			reden.addAll(from(document));
		}
		LOGGER.info("{} Reden aus {} XML-Dokumenten erzeugt!", reden.size(), documents.size());
        return reden;
	}
	
	/**
	 * Erzeugt aus einem XML-Dokument Rede-Objekte
	 * 
	 * @param document - ein XML Plenarprotokoll
	 * 
	 * @return Liste von erzeugten Rede-Objekten
	 */
	public List<Rede> from(Document document) {
        List<Rede> reden = new ArrayList<>();
        Date datum = TypeHelper.toDate(document.getRootElement().getChild("vorspann").getChild("kopfdaten").getChild("veranstaltungsdaten").getChild("datum").getAttributeValue("date"));
        reden.addAll(createReden(datum, abgeordneten, document.getRootElement().getChild("sitzungsverlauf").getChildren("tagesordnungspunkt")));
        return reden;
	}

    private List<Rede> createReden(Date datum, List<Abgeordneter> abgeordnete, List<Element> tagesOrdnungsPunkte) {
        List<Rede> reden = new ArrayList<>();
        for (Element tagesOrdnungsPunkt : tagesOrdnungsPunkte) {
            for (Element elementRede : tagesOrdnungsPunkt.getChildren("rede")) {
                String redeId = elementRede.getAttributeValue("id");
                String rednerId = getRednerId(elementRede);
	            Abgeordneter abgeordneter = getAbgeordneter(abgeordnete, rednerId);
            	Rede rede = new RedeImpl(redeId, abgeordneter, datum, elementRede); 
            	if (rede.getWahlperiode() == null || rede.getRednerId() == null) {
            		FAULTY_REDEN_IDS.add(redeId);
            	}
                reden.add(rede);
                if (abgeordneter != null) {
                	abgeordneter.addRedeId(rede.getID().toString());
                }
                LOGGER.debug("Rede mit Id {} erzeugt!", rede.getID());
            }
        }
        return reden;
    }
    
    private String getRednerId(Element elementRede) {
        for (Element p : elementRede.getChildren("p")) {
            if (p.getAttributeValue("klasse") != null && p.getAttributeValue("klasse").equalsIgnoreCase("redner")) {
                return p.getChild("redner").getAttributeValue("id");
            }
        }
        return null;
    }
    
    private Abgeordneter getAbgeordneter(List<Abgeordneter> abgeordnete, String rednerId) {
    	if (rednerId != null) {
    		return abgeordnete.stream().filter(a -> a.getID().toString().equalsIgnoreCase(rednerId)).findAny().orElse(null);
    	}
        return null;
    }

}

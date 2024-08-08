package org.group44.parliamentbrowser.builder;

import java.util.ArrayList;
import java.util.List;

import org.group44.parliamentbrowser.types.Sitzung;
import org.group44.parliamentbrowser.types.SitzungImpl;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Erzeugt aus den XML Plenarprotokollen Sitzungs-Objekte
 * 
 * @author Cora
 */
public class SitzungBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SitzungBuilder.class);
	
	/**
	 * Erzeugt aus mehreren XML-Plenarprotokollen Sitzungs-Objekte
	 * 
	 * @param documents - Liste von XML Plenarprotokollen
	 * 
	 * @return Liste von Sitzungs-Objekten
	 */
	public List<Sitzung> from(List<Document> documents) {
        List<Sitzung> sitzungen = new ArrayList<>();
		for (Document document : documents) {
			sitzungen.add(from(document));
		}
		LOGGER.info("{} Sitzungen aus {} XML-Dokumenten erzeugt!", sitzungen.size(), documents.size());
        return sitzungen;
	}
	
	/**
	 * Erzeugt aus einem XML-Plenarprotokoll ein Sitzungs-Objekt
	 * 
	 * @param document - ein XML Plenarprotokollen
	 * 
	 * @return ein Sitzungs-Objekten
	 */
	public Sitzung from(Document document) {
		Sitzung sitzung = new SitzungImpl(document.getRootElement());
		LOGGER.debug("Sitzung mit Id {} erzeugt!", sitzung.getID());
		return sitzung;
	}
	
}

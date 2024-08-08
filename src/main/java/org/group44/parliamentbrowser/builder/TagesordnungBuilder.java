package org.group44.parliamentbrowser.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.group44.parliamentbrowser.container.Tagesordnungen;
import org.group44.parliamentbrowser.types.Tagesordnung;
import org.group44.parliamentbrowser.types.TagesordnungImpl;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Erzeugt aus den XML Plenarprotokollen Tagesordnungs-Objekte
 * 
 * @author Cora
 */
public class TagesordnungBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TagesordnungBuilder.class);
	
	/**
	 * Erzeugt aus mehreren XML-Plenarprotokollen Tagesordnungs-Objekte
	 * 
	 * @param documents - Liste von XML Plenarprotokollen
	 * 
	 * @return Liste von Tagesordnungs-Objekten
	 */
	public List<Tagesordnung> from(List<Document> documents) {
        List<Tagesordnung> tagesordnungen = new ArrayList<>();
		for (Document document : documents) {
			tagesordnungen.addAll(from(document));
		}
		LOGGER.info("{} Tagesordnungen aus {} XML-Dokumenten erzeugt!", tagesordnungen.size(), documents.size());
        return tagesordnungen;
	}
	
	/**
	 * Erzeugt aus einem XML-Plenarprotokollen Tagesordnungs-Objekte
	 * 
	 * @param document - ein XML Plenarprotokoll
	 * 
	 * @return Liste von Tagesordnungs-Objekten
	 */
	public List<Tagesordnung> from(Document document) {
		String wahlperiode = document.getRootElement().getAttributeValue("wahlperiode");
		String sitzungNr = document.getRootElement().getAttributeValue("sitzung-nr");
		List<Tagesordnung> tagesordnungen = new ArrayList<>();
		for (Element ivzBlock : document.getRootElement().getChild("vorspann").getChild("inhaltsverzeichnis").getChildren("ivz-block")) {
			Tagesordnung tagesordnung = new TagesordnungImpl(wahlperiode, sitzungNr, ivzBlock);
			tagesordnungen.add(tagesordnung);
			Tagesordnungen.put(tagesordnung);
			LOGGER.debug("Tagesordnung {}: {} mit den Reden [{}] erzeugt!", tagesordnung.getID(), tagesordnung.getText(), tagesordnung.getRedenIds().stream().collect(Collectors.joining(",")));
		}
		return tagesordnungen;
	}

}

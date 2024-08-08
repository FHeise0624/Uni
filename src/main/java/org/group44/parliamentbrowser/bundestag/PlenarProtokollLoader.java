package org.group44.parliamentbrowser.bundestag;

import static java.text.MessageFormat.format;
import static org.jsoup.Jsoup.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;

import org.jdom2.Document;
import org.jdom2.input.StAXEventBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lädt die Plenarprotokolle von der Web-Seite des Bundestages. Die Klasse ist als Iterator implementiert. Es wird die Klasse erzeugt,
 * zu späteren Nutzung können die hasNext und next Methode des Iterator-Interfaces verwendet werden. Darüber werden schrittweise alle
 * XML Plenarprotokolle geladen.
 * 
 * @author Cora
 */
public class PlenarProtokollLoader implements Iterator<Document> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlenarProtokollLoader.class);
	private static String API_KEY = "eyJleHAiOjE3MDkxNDg2NTEsImNvbnRlbnQiOnRydWUsImF1ZCI6ImF1dGgiLCJIb3N0IjoiZGlwLmJ1bmRlc3RhZy5kZSIsIlNvdXJjZUlQIjoiOTUuMjIzLjczLjM1IiwiQ29uZmlnSUQiOiI4ZGFkY2UxMjVmZDJjMzkzMmI5NDNiNTJlOWQyY2Q2NTA1NzU0ZTE2MjIxMmEyY2UxYmI1YWYxNWMwZDRiYmZlIn0=.F3oTeyh3sBo5mn2PEsjBbEY2wdmtCc2Bvu7gy68p0FQ=";
	private static final String WAHLPERIODE_19_URL = "https://www.bundestag.de/ajax/filterlist/de/services/opendata/543410-543410?offset={0}&enodia={1}";
	private static final String WAHLPERIODE_20_URL = "https://www.bundestag.de/ajax/filterlist/de/services/opendata/866354-866354?offset={0}&enodia={1}";

	private XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
	private String overviewUrl;
	private List<Document> xmlPlenarProtokolle = new ArrayList<Document>();
	private int overviewUrlOffset;
	private int plenarProtokollIndex;
	
	/**
	 * Erzeugt den PlenarprotokollLoader (standardmäßig wird er die Plenarprotokolle der 20. Wahlperiode laden)
	 */
	public PlenarProtokollLoader() {
		this(WAHLPERIODE_20_URL, 0);
	}
	
	/**
	 * Erzeugt den PlenarprotokollLoader (sMit Angabe der Start URL und des Offset des Paging)
	 */
	public PlenarProtokollLoader(String overviewUrl, int startOffset) {
		xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		this.overviewUrl = overviewUrl;
		overviewUrlOffset = startOffset;
		plenarProtokollIndex = 0;
		List<String> plenarProtokollUrls = getPlenarProtokollUrls();
		xmlPlenarProtokolle = getXmlPlenarProtokolle(plenarProtokollUrls);
	}
	
	private List<String> getPlenarProtokollUrls() {
		List<String> plenarProtokolleUrls = getUrls(overviewUrl, overviewUrlOffset);
		if (plenarProtokolleUrls.size() == 0 && overviewUrl.equals(WAHLPERIODE_20_URL)) {
			overviewUrl = WAHLPERIODE_19_URL;
			overviewUrlOffset = 0;
			LOG.info("Schalte auf Overview-URL {} aus Wahlperiode 19 um und setze Index auf {}.", overviewUrl, overviewUrlOffset);
			plenarProtokolleUrls = getUrls(overviewUrl, overviewUrlOffset);
		}
		return plenarProtokolleUrls;
	}

	private List<String> getUrls(String overviewUrl, int offset) {
		String realUrl = format(overviewUrl, String.valueOf(offset), API_KEY);
		try {
			org.jsoup.nodes.Document doc = connect(realUrl).get();
			List<String> plenarProtokolleUrls = new ArrayList<String>();
			doc.select("a[href]").forEach(a -> plenarProtokolleUrls.add(a.attr("abs:href")));
			LOG.info("{} URLs für XML Plenarprotokolle gefunden auf der Seite '{}'.", plenarProtokolleUrls.size(), realUrl);
			return plenarProtokolleUrls;
		} catch (IOException e) {
			LOG.error("Fehler aufgetreten während des Ladens der URLs defr XML Dokumente von " + realUrl + ".", e);
			return new ArrayList<String>();
		}
	}
	
	private List<Document> getXmlPlenarProtokolle(List<String> plenarProtokollUrls) {
		List<Document> tempPlenarProtokolle = new ArrayList<Document>();
		for (String plenarProtokollUrl : plenarProtokollUrls) {
			try {
				URL u = new URL(plenarProtokollUrl);
				try (InputStream in = u.openStream()) {
					String xmlContent = new String(in.readAllBytes(), StandardCharsets.UTF_8);
					Reader reader = new StringReader(xmlContent);
					XMLEventReader xmlReader = xmlInputFactory.createXMLEventReader(reader);
					Document xmlDocument = new StAXEventBuilder().build(xmlReader);
					tempPlenarProtokolle.add(xmlDocument);
				}
			} catch (Exception e) {
				LOG.warn("XML Plenar-Protokoll von URL " + plenarProtokollUrl + " konnte nicht geladen werden.", e);
			}
			LOG.info("XML Plenar-Protokoll von URL '{}' geladen.", plenarProtokollUrl);
		}
		LOG.info("{} XML Plenar-Protokolle von den Bundestagsseiten geladen.", tempPlenarProtokolle.size());
		return tempPlenarProtokolle;
	}
	
	/**
	 * Prüft ob noch weitere Plenarprotokolle auf der Web-seite des Bundestags vorhanden sind
	 * 
	 * @return true falls noch Plenarprotokolle vorhanden sind die noch nicht mit next abgerufen wurden, sonst false 
	 */
	@Override
	public boolean hasNext() {
		if (plenarProtokollIndex < xmlPlenarProtokolle.size()) {
			return true;
		} 
		overviewUrlOffset = overviewUrlOffset + 10;
		plenarProtokollIndex = 0;
		List<String> plenarProtokollUrls = getPlenarProtokollUrls();
		if (plenarProtokollIndex == plenarProtokollUrls.size()) {
			LOG.info("Keine weiteren URLs für XML-Plenar-Protokolle gefunden!");
			this.xmlPlenarProtokolle = new ArrayList<Document>();
			return false;
		}
		xmlPlenarProtokolle = getXmlPlenarProtokolle(plenarProtokollUrls);
		if (plenarProtokollIndex < xmlPlenarProtokolle.size()) {
			return true;
		} else {
			LOG.info("Es konnten keine weiteren XML Plenar-Protokolle geladen werden!");
			return false;
		}
	}

	/**
	 * Liefert das nächste XML Plenarprotokoll, die Liste ist absteigend nach Datum sortiert
	 * 
	 * @return nächstes XMl Plenarprotokoll 
	 */
	@Override
	public Document next() {
		Document xmlPlenarProtokoll = null;
		if (plenarProtokollIndex < xmlPlenarProtokolle.size()) {
			xmlPlenarProtokoll = xmlPlenarProtokolle.get(plenarProtokollIndex);
			plenarProtokollIndex += 1;
		}
		return xmlPlenarProtokoll;
	}
	
}

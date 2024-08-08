package org.group44.parliamentbrowser.bundestag;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.group44.parliamentbrowser.exception.BundestagFileException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.StAXEventBuilder;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Klasse dient zum Laden der Abegordneten Stammdaten von der Web-Seite des Bundestages
 * 
 *  @author Cora
 */
public class AbgeordneterLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbgeordneterLoader.class);
	private static final String ABGEORDNETE_URL = "https://www.bundestag.de/resource/blob/472878/c589cb3836f59d76780397e8b131379c/MdB-Stammdaten.zip";

	/**
	 * Lädt die Stammdaten der Abgeordneten als ZIP Archiv von der Web-Seite des Bundestages, entpackt das ZIP und liefert das darin enthaltene
	 * XML Dokument zurück
	 * 
	 * @return XML Dokument mit allen Abgeordneten Informationen
	 */
	public Document getXmlAbgeordnete() {
		Document document = null;
		LOGGER.info("Lade Stammdaten aus der Datei {}", ABGEORDNETE_URL);
		HttpURLConnection connection = null;
		ZipInputStream zipIn = null;
		try {
			URL url = new URL(ABGEORDNETE_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			zipIn = new ZipInputStream(connection.getInputStream());
			ZipEntry entry;
			while ((entry = zipIn.getNextEntry()) != null) {
				if (entry.getName().toLowerCase().endsWith(".xml")) {
					document = zipEntryToXmlDocument(zipIn);
				}
				zipIn.closeEntry();
			}
			zipIn.close();
		} catch (IOException e) {
			LOGGER.error("Fehler beim Download der Stammdaten aufgetreten.", e);
		} finally {
			if (zipIn != null) {
				try {
					zipIn.close();
				} catch (IOException e) {}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return document;
	}

	private Document zipEntryToXmlDocument(ZipInputStream zipIn) throws BundestagFileException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
		xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		try {
			Reader reader = new InputStreamReader(zipIn, "UTF-8");
			XMLEventReader xmlReader = xmlInputFactory.createXMLEventReader(reader);
			Document document = new StAXEventBuilder().build(xmlReader);
			return document;
		} catch (UnsupportedEncodingException | XMLStreamException | JDOMException e) {
			String message = String.format("ZipIn is no valid XML Document!");
			LOGGER.error(message);
			throw new BundestagFileException(message, e);
		}
	}
}

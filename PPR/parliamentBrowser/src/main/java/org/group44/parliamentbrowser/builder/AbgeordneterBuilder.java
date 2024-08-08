package org.group44.parliamentbrowser.builder;

import java.util.ArrayList;
import java.util.List;

import org.group44.parliamentbrowser.container.Gruppen;
import org.group44.parliamentbrowser.container.Parteien;
import org.group44.parliamentbrowser.container.Wahlkreise;
import org.group44.parliamentbrowser.container.Wahlperioden;
import org.group44.parliamentbrowser.picture_scraper.AbgeordnetenBilder;
import org.group44.parliamentbrowser.types.Abgeordneter;
import org.group44.parliamentbrowser.types.AbgeordneterImpl;
import org.group44.parliamentbrowser.types.GruppeImpl;
import org.group44.parliamentbrowser.types.MandatImpl;
import org.group44.parliamentbrowser.types.MitgliedschaftImpl;
import org.group44.parliamentbrowser.types.Partei;
import org.group44.parliamentbrowser.types.Wahlkreis;
import org.group44.parliamentbrowser.types.Wahlperiode;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Baut die Instanzen der Abgeordneten aus dem XML file
 *
 * @author Cora
 */
public class AbgeordneterBuilder {

	public static List<Abgeordneter> IGNORED_ABGEORDNETE = new ArrayList<Abgeordneter>();

	private static final Logger LOGGER = LoggerFactory.getLogger(AbgeordneterBuilder.class);

	public List<Abgeordneter> from(List<Document> documents) {
		List<Abgeordneter> abgeordnete = new ArrayList<>();
		for (Document document : documents) {
			abgeordnete.addAll(from(document));
		}
		LOGGER.info("{} Abgeordnete aus {} XML-Dokumenten erzeugt!", abgeordnete.size(), documents.size());
		return abgeordnete;
	}

	public List<Abgeordneter> from(Document document) {
		List<Abgeordneter> abgeordnete = new ArrayList<>();
		for (Element mdb : document.getRootElement().getChildren("MDB")) {
			Abgeordneter abgeordneter = createAbgeordneter(mdb);
			if (abgeordneter.getMandat("19") != null || abgeordneter.getMandat("20") != null) {
				abgeordnete.add(abgeordneter);
			} else {
				IGNORED_ABGEORDNETE.add(abgeordneter);
			}
		}
		LOGGER.info("{} Abgeordnete aus XML-Dokument erzeugt!", abgeordnete.size());
		return abgeordnete;
	}

	/**
	 *
	 * @param mdb
	 * @return
	 * @author dominik
	 * @author cora
	 */
	public Abgeordneter createAbgeordneter(Element mdb) {
		Partei partei = Parteien.getPartei(mdb.getChild("BIOGRAFISCHE_ANGABEN").getChildText("PARTEI_KURZ"));
		Abgeordneter abgeordneter = new AbgeordneterImpl(mdb);

		// holle die bilder des abgeordneten und übergebe sie ihm
		new AbgeordnetenBilder(abgeordneter);

		abgeordneter.setParteiId(partei.getID().toString());
		partei.addMitgliedId(abgeordneter.getID().toString());
		List<Element> wahlperioden = mdb.getChild("WAHLPERIODEN").getChildren("WAHLPERIODE");
		for (Element elementWahlperiode : wahlperioden) {
			Wahlperiode wahlperiode = Wahlperioden.getWahlperiode(elementWahlperiode);
			Wahlkreis wahlkreis = Wahlkreise.getWahlkreis(elementWahlperiode);
			MandatImpl mandat = new MandatImpl(elementWahlperiode, wahlperiode, wahlkreis, abgeordneter);
			abgeordneter.addMandat(mandat);
			List<Element> elementInstitutionen = elementWahlperiode.getChild("INSTITUTIONEN")
					.getChildren("INSTITUTION");
			for (Element elementInstitution : elementInstitutionen) {
				GruppeImpl gruppe = Gruppen.getGruppe(elementInstitution, wahlperiode);
				gruppe.addMitglied(abgeordneter);
				MitgliedschaftImpl mitgliedschaft = new MitgliedschaftImpl(elementInstitution, abgeordneter,
						wahlperiode, gruppe);
				abgeordneter.addMitgliedschaft(mitgliedschaft);
			}
		}
		LOGGER.debug("Abgeordneter mit Id {} erzeugt!", abgeordneter.getID());
		return abgeordneter;
	}
}

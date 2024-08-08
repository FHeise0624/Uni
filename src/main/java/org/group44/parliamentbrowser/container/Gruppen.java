package org.group44.parliamentbrowser.container;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.group44.parliamentbrowser.types.Gruppe;
import org.group44.parliamentbrowser.types.GruppeImpl;
import org.group44.parliamentbrowser.types.Types;
import org.group44.parliamentbrowser.types.Wahlperiode;
import org.jdom2.Element;

/**
 * Enthält alle Gruppen, die es gibt
 * 
 * @author Cora
 */

public class Gruppen {

	private static Map<Wahlperiode, Map<String, GruppeImpl>> gruppenInWahlperiode = new HashMap<>();

	/**
	 * Ermittelt aus dem XML-Zweig INSTITUTION die Gruppe und erzeugt diese, falls
	 * sie noch nicht besteht.
	 * 
	 * @param wahlperiode        Wahlperiode in der die Gruppe ermittelt werden soll
	 * @param elementInstitution XML-Zweig INSTITUTION aus dem die Gruppe erzeugt
	 *                           wird
	 * 
	 * @return Neue oder bereits bestehende Gruppe
	 */
	public static GruppeImpl getGruppe(Element elementInstitution, Wahlperiode wahlperiode) {
		if (!gruppenInWahlperiode.containsKey(wahlperiode)) {
			gruppenInWahlperiode.put(wahlperiode, new HashMap<>());
		}
		Map<String, GruppeImpl> gruppen = gruppenInWahlperiode.get(wahlperiode);
		String instutionArt = elementInstitution.getChildText("INSART_LANG");
		String instutionName = elementInstitution.getChildText("INS_LANG");
		String key = instutionArt + "/" + instutionName;
		if (gruppen.containsKey(key)) {
			return gruppen.get(key);
		} else {
			GruppeImpl gruppe = new GruppeImpl(elementInstitution, wahlperiode);
			gruppen.put(key, gruppe);
			return gruppe;
		}
	}

	/**
	 * Ermittelt alle Gruppen einer Wahlperiode
	 * 
	 * @param wahlperiode Wahlperiode für die die Gruppen ermittelt werden sollen
	 * 
	 * @return Alle Gruppen einer Wahlperiode
	 */
	public static List<Gruppe> getGruppen(Wahlperiode wahlperiode) {
		List<Gruppe> gruppen = new ArrayList<>();
		for (Gruppe gruppe : gruppenInWahlperiode.get(wahlperiode).values()) {
			gruppen.add(gruppe);
		}
		return gruppen;
	}

	/**
	 * Ermittelt alle Gruppen eines Typs einer Wahlperiode (z.B. Ausschuesse)
	 * 
	 * @param wahlperiode Wahlperiode für die die Gruppen ermittelt werden sollen
	 * 
	 * @return Alle Gruppen einer Wahlperiode
	 */
	public static List<Gruppe> getGruppen(Wahlperiode wahlperiode, Types.GRUPPE_TYP typ) {
		return getGruppen(wahlperiode).stream().filter(gruppe -> gruppe.getTyp().equals(typ)).collect(toList());
	}
}

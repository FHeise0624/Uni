package org.group44.parliamentbrowser.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.group44.parliamentbrowser.types.Wahlkreis;
import org.group44.parliamentbrowser.types.WahlkreisImpl;
import org.jdom2.Element;

/**
 * Container der alle geladenen Wahlkreise beinhaltet.
 * 
 * @author Cora
 */
public class Wahlkreise {

	private static Map<String, Wahlkreis> wahlkreise = new HashMap<>();

    /**
     * Ermittelt aus dem XML-Zweig "WAHLPERIODE" den Wahlkreis des Abgeordneten in einer Wahlperiode
     *
     * @param elementWahlperiode XML String der die Wahlkreisinformation enthält (WAHLPERIODE)
     *
     * @return Wahlkreis mit Name und Nummer der ermittelt wurde
     */
    public static Wahlkreis getWahlkreis(Element elementWahlperiode) {
        String wahlkreisNummer = elementWahlperiode.getChildText("WKR_NUMMER");
        if (wahlkreisNummer == null || wahlkreisNummer.isBlank()) {
        	return null;
        }
        Wahlkreis wahlkreis = wahlkreise.get(wahlkreisNummer) != null ? wahlkreise.get(wahlkreisNummer) : null;
        if (wahlkreis == null) {
            wahlkreis = new WahlkreisImpl(elementWahlperiode);
            wahlkreise.put(wahlkreisNummer, wahlkreis);
        }
        return wahlkreis;
    }

    /**
     * Gibt die Liste aller bereits geladenen Wahlkreise zurück.
     *
     * @return Liste aller Wahlkreise über alle Wahlperioden
     */
    public static List<Wahlkreis> getWahlkreise() {
        List<Wahlkreis> alleWahlkreise = new ArrayList<>();
        for (Wahlkreis wahlkreis : wahlkreise.values()) {
            alleWahlkreise.add(wahlkreis);
        }
        return alleWahlkreise;
    }

    /**
     * Löscht die Listen aller Wahlkreise
     */
    public static void reset() {
        wahlkreise = new HashMap<>();
    }
}

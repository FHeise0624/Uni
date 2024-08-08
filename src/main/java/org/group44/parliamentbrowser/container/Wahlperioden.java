package org.group44.parliamentbrowser.container;

import org.group44.parliamentbrowser.types.Wahlperiode;
import org.group44.parliamentbrowser.types.WahlperiodeImpl;
import org.group44.parliamentbrowser.util.TypeHelper;
import org.jdom2.Element;

import java.util.*;

/**
 * Container der alle geladenen Wahlperioden beinhaltet.
 * 
 * @author Cora
 */
public class Wahlperioden {

    private static Map<Integer, Wahlperiode> wahlperioden = new HashMap<>();

    /**
     * Ermittelt aus dem XML-Zweig "WAHLPERIODE" die Wahlperiode
     *
     * @param elementWahlperiode XML String der die Wahlperiodeninformation enthält (WAHLPERIODE)
     *
     * @return Wahlperiode mit Nummer und Zeitraum die ermittelt wurde
     */
    public static Wahlperiode getWahlperiode(Element elementWahlperiode) {
        Integer number = TypeHelper.ToInteger(elementWahlperiode.getChild("WP").getValue());
        Wahlperiode wahlperiode = wahlperioden.get(number);
        if (wahlperiode == null) {
            wahlperiode = new WahlperiodeImpl(elementWahlperiode);
            wahlperioden.put(number, wahlperiode);
        }
        return wahlperiode;
    }

    /**
     * Gibt die Liste aller bereits geladenen Wahlperioden zurück.
     *
     * @return Liste aller Wahlperdioden
     */
    public static List<Wahlperiode> getWahlPerioden() {
        return new ArrayList<>(wahlperioden.values());
    }

    /**
     * Gibt die Wahlperiode mit der angegebenen Nummer zurück.
     *
     * @param number Nummer der Wahlperiode die man ermitteln möchte
     * 
     * @return Wahlperiode mit der angegebenen Nummer
     */
    public static Wahlperiode getWahlPeriode(String number) {
        return wahlperioden.get(Integer.valueOf(number));
    }

    /**
     * Gibt die aktuelle Wahlperiode zurück.
     *
     * @return aktuelle Wahlperdiode
     */
    public static Wahlperiode getAktuelleWahlperiode() {
        int max = wahlperioden.keySet().stream().mapToInt(k -> k).max().orElseThrow(NoSuchElementException::new);
        return wahlperioden.get(max);
    }

    /**
     * Löscht die Liste der Wahlperioden
     */
    public static void reset() {
        wahlperioden = new HashMap<>();
    }
}

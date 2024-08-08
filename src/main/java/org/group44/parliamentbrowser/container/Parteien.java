package org.group44.parliamentbrowser.container;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.group44.parliamentbrowser.types.Partei;
import org.group44.parliamentbrowser.types.ParteiImpl;

/**
 * Container der alle aus den Stammdaten geladenen Parteien beinhaltet.
 * 
 * @author Cora
 */
public class Parteien {

    private static Map<String, Partei> parteien = new HashMap<>();

    /**
     * Erzeugt eine Partei mit dem Namen parteiName
     *
     * @param parteiName Der Name der Partei die gerade erzeugt wird
     *
     * @return Partei mit Name die ermittelt wurde
     */
    public static Partei getPartei(String parteiName) {
    	Partei partei = parteien.get(parteiName);
        if (partei == null) {
            partei = new ParteiImpl(parteiName);
            parteien.put(parteiName, partei);
        }
        return partei;
    }

    /**
     * Gibt die Liste aller bereits geladenen Parteien zurück.
     *
     * @return Liste aller Parteien über alle Wahlperioden
     */
    public static List<Partei> getParteien() {
        return parteien.values().stream().sorted().collect(Collectors.toList());
    }

    /**
     * Löscht die Liste der Parteien
     */
    public static void reset() {
        parteien = new HashMap<>();
    }
}

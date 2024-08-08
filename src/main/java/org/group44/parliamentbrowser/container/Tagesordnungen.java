package org.group44.parliamentbrowser.container;

import java.util.HashMap;
import java.util.Map;

import org.group44.parliamentbrowser.types.Tagesordnung;
import org.group44.parliamentbrowser.types.TagesordnungImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Container der alle aus den Plenarprotokollen geladenen Tagesordnungen beinhaltet.
 * 
 * @author Cora
 */
public class Tagesordnungen {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Tagesordnungen.class);
	
	private static Tagesordnung UNKNOWN_TAGESORDNUNG = new TagesordnungImpl();
	
	private static Map<String, Tagesordnung> tagesordnungenByRedeId = new HashMap<>();

    /**
     * Speichert eine Tagesordnung
     *
     * @param tagesordnung - neue Tagesordnung
     */
	public static void put(Tagesordnung tagesordnung) {
		for (String redeId : tagesordnung.getRedenIds()) {
			tagesordnungenByRedeId.put(redeId, tagesordnung);
		}
	}

    /**
     * Sucht eine Tagesordnung anhand der RedeID
     *
     * @param redeId - eine RedeId
     *
     * @return Tagesordnung zu der angegebenen RedeId
     */
	public static Tagesordnung getTagesordnung(String redeId) {
		if (!tagesordnungenByRedeId.containsKey(redeId)) {
			LOGGER.debug("Keine Tagesordnung zu Rede mit Id {} gefunden!", redeId);
			tagesordnungenByRedeId.put(redeId, UNKNOWN_TAGESORDNUNG);
		}
		return tagesordnungenByRedeId.get(redeId);
	}

}

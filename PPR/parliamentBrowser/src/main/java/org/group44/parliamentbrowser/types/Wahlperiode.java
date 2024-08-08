package org.group44.parliamentbrowser.types;

import java.sql.Date;

/**
 * Enthält die Informationen der Wahlperioden
 *
 * @author Cora
 */
public interface Wahlperiode extends BundestagObject {

  /**
   * Gibt die Nummer der Wahlperiode zurück
   * 
   * @return Nummer der Wahlperiode zurück
   */
  Integer getNumber();

  /**
   * Gibt den Beginn der Wahlperiode zurück
   * 
   * @return Beginn der Wahlperiode
   */
  Date getStartDate();

  /**
   * Gibt das Ende der Wahlperiode zurück
   * 
   * @return Ende der Wahlperiode
   */
  Date getEndDate();

}

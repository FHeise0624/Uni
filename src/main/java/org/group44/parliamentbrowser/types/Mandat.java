package org.group44.parliamentbrowser.types;

/**
 * Enthält die Informationen der Mandate
 *
 * @author Cora
 */
public interface Mandat extends BundestagObject{

  /**
   * Rückgabe des Mandattypes
   * 
   * @return Type des Mandats (Direktwahl, Landesliste)
   */
  public Types.MANDAT getTyp();

  /**
   * Gibt die Id der Wahlperiode zurück in der das Mandat errungen wurde
   * 
   * @return Id der Wahlperiode in der das Mandat errungen wurde
   */
  public String getWahlperiodeId();

  /**
   * Gibt die Id des Wahlkreises zurück in dem das Mandat errungen wurde
   *
   * @return Id des Wahlkreises in dem das Mandat errungen wurde
   */
  public String getWahlkreisId();

  /**
   * Gibt den Abgeordneten zurück, der das Mandat errungen hat
   *
   * @return ID des Abgeordneten der das Mandat errungen hat
   */
  public String getAbgeordneterId();

}

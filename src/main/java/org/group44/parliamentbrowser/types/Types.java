package org.group44.parliamentbrowser.types;

/**
 * Enthält die Informationen der Typen (enums)
 *
 * @author Cora
 */
public class Types {

  public enum MANDAT {DIREKTWAHL,LANDESLISTE}
  
  public enum GESCHLECHT {MAENNLICH,WEIBLICH}
  
  public enum GRUPPE_TYP {Fraktion_Gruppe, Ausschuss, Unterausschuss, Untersuchungsausschuss, Sonstiges_Gremium, Parlamentariergruppen, Deutscher_Bundestag, Ministerium, Enquete_Kommission}

  public enum ABSTIMMUNG {JA, NEIN, ENTHALTUNG, NONE}

}

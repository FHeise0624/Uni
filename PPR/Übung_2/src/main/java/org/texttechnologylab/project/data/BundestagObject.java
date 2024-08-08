package org.texttechnologylab.project.data;

/**
 * Oberklasse für alle Objekte in der Anwendung *Inside Bundestag*
 * @author Giuseppe Abrami
 */
public interface BundestagObject extends Comparable<BundestagObject>{

    /**
     * ID des Objektes
     *
     * @return
     */
    Object getID();

    /**
     * Label des Objektes
     * @return
     */
    String getLabel();

}

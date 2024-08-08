package org.texttechnologylab.project.helper;

import org.texttechnologylab.project.data.Abgeordneter;
import org.texttechnologylab.project.data.Fraktion;
import org.texttechnologylab.project.data.Mitgliedschaft;

import java.util.Set;

/**
 * Interface für die BundestagsFactory als Basis-Interface zur Erweiterung
 *
 * @author Giuseppe Abrami
 */
public interface BundestagFactory {

    Set<Abgeordneter> listAbgeordnete();


    Set<Fraktion> listFraktionen();

    Set<Mitgliedschaft> listMitgliedschaften();

}

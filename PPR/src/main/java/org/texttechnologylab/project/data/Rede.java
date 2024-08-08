package org.texttechnologylab.project.data;

import java.sql.Date;

/**
 * Interface für eine Rede
 *
 * @author Giuseppe Abrami
 */
public interface Rede extends BundestagObject {

    /**
     * Abgeordneter, der eine Rede gehalten hat
     * @return
     */
    Abgeordneter getRedner();

    /**
     * Der Redetext der Rede
     * @return
     */
    String getText();

    /**
     * Datum der Rede
     *
     * @return
     */
    Date getDate();

}

package org.texttechnologylab.project.data;

import java.sql.Date;
import java.util.Set;


/**
 * Interface für eine Wahlperiode
 *
 * @author Giuseppe Abrami
 */
public interface Wahlperiode extends BundestagObject {

    /**
     * Nummer der Wahlperiode
     * @return
     */
    int getNumber();

    /**
     * Beginn der Wahlperiode
     * @return
     */
    Date getStartDate();

    /**
     * Ende der Wahlperiode
     * @return
     */
    Date getEndDate();

    /**
     * Mandate der Wahlperiode
     * @return
     */
    Set<Mandat> listMandate();

    /**
     * Mandate nach Mandatstyp
     * @param pType
     * @return
     */
    Set<Mandat> listMandate(Types.MANDAT pType);

}

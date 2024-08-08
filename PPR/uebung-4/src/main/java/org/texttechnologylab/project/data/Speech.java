package org.texttechnologylab.project.data;

import java.sql.Date;

/**
 * Interface for a Speach.
 * @ Felix Tams
 * this interface is based on the Interface  Rede by Giuseppe Abrami
 */
public interface Speech {
    int getSpeakerID();

    /**
     * text of the speach
     * @return
     */
    String getText();

    /**
     * Date of speach
     *
     * @return
     */
}

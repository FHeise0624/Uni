package org.texttechnologylab.project.data;

import java.util.Date;

/**
 * Interface for an MP
 * @author Felix Tams
 * this infertace is based on the Abgeordneter interface of Giuseppe Abrami
 */
public interface MP {
    int getID();
    /**
     * Returns last name of MP
     * @return
     */
    String getLastName();

    /**
     * Returns first name of MP
     * @return
     */
    String getFirstName();

    /**
     * Returns local addition of MP
     * @return
     */
    String getLocalAddition();

    /**
     * Returns noble  title of MP
     * @return
     */
    String getNobleTitle();

    /**
     * Returns salutation of MP
     * @return
     */
    String getSalutation();

    /**
     * Returns academic titles of MP
     * @return
     */
    String getAcadTitle();

    /**
     * Returns Date of Birth of the MP
     * @return
     */
    Date getDOB();

    /**
     * Returns MPs place of birth
     * @return
     */
    String getPlaceOfBirth();

    /**
     * Returns MPS date of death
     * @return
     * @throws NullPointerException
     */
    Date getDOD() throws NullPointerException;

    /**
     * returns MPs gender
     *
     * @return
     */
    String getGender();

    /**
     * returns MPs religion
     * @return
     */
    String getReligion();

    /**
     * returns MPs job
     * @return
     */
    String getJob();

    /**
     * returns short vita of MP
     * @return
     * @throws NullPointerException
     */
    String getVita() throws NullPointerException;

    /**
     * returns MPs party
     * @return
     */
    String getParty();
}

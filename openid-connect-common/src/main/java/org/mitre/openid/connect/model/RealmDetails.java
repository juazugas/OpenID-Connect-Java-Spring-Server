package org.mitre.openid.connect.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Definition of the realm details.
 *
 */
public interface RealmDetails extends Serializable {

    /**
     * Gets the name.
     * 
     * @return name of the realm.
     */
    String getName();

    /**
     * Gets the short description.
     * 
     * @return short description.
     */
    String getShortDesc();

    /**
     * Gets if it is enabled.
     * 
     * @return true if enabled.
     * 
     */
    boolean isEnabled();

    /**
     * Gets the description
     * 
     * @return realm description.
     */
    String getDescription();

    /**
     * Gets the creation stamp.
     * 
     * @return the date of creation
     */
    Date getCreatedDt();

    /**
     * Gets the deletion stamp.
     * 
     * @return the date of deletion.
     */
    Date getDeleteDt();

    /**
     * Gets the administration email.
     * 
     * @return email of admin
     */
    String getAdminEmail();

    /**
     * Gets the disable date.
     * 
     * @return the date of disable.
     */
    String getDisableDt();

}

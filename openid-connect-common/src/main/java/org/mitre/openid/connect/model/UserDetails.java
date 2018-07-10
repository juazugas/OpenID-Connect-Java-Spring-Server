package org.mitre.openid.connect.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Entidad usuario para relacionar con userinfo por el campo preferred_username == username 
 * 
 * @author jzuriaga
 *
 */
public interface UserDetails extends Serializable {
	
    /**
     * Gets the user name.
     * 
     * @return name of the user.
     */
    String getUsername();

    /**
     * Gets if it is enabled.
     * 
     * @return true if enabled.
     * 
     */
    boolean isEnabled();

    /**
     * Gets if it is enabled.
     * 
     * @return true if enabled.
     * 
     */
    boolean isEnrollment();
    
    /**
     * Gets the properties of the user.
     * 
     * @return the map of the user properties.
     */
    Map<String,String> getUserProperties();
    
}

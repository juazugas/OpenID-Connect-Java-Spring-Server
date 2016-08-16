package org.mitre.openid.connect.model;

import java.io.Serializable;

import org.mitre.oauth2.model.ClientDetailsEntity;

/**
 * Representa a la obtención de propiedades de un usuario para un determinado cliente.
 *
 */
public interface UserInfoClientProperty extends Serializable {
    
    
    /**
     * Obtiene el cliente relacionado.
     *  
     * @return the client details entity object.
     */
    ClientDetailsEntity getClient();
    
    /**
     * Obtiene la propiedad relacionada.
     * @return
     */
    String getProperty();
    
    /**
     * Obtiene el valor de las propiedad relacionada.
     * @return
     */
    String getValue();

}

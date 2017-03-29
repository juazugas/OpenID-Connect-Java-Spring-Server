package org.mitre.openid.connect.model;

import java.io.Serializable;

/**
 * the property of the user_info by realm.
 *
 */
public interface UserInfoRealmProperty extends Serializable {

    
    /**
     * Obtiene el cliente relacionado.
     *  
     * @return the client details entity object.
     */
    UserInfoRealmDetails getUserInfoRealm();
    
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

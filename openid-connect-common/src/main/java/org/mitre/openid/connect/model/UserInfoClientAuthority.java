package org.mitre.openid.connect.model;

import java.io.Serializable;

import org.mitre.oauth2.model.ClientDetailsEntity;

/**
 * Representa a la obtención de roles/permisos de un usuario para un determinado cliente.
 *
 */
public interface UserInfoClientAuthority extends Serializable {
    
    
    /**
     * Obtiene el cliente relacionado.
     *  
     * @return the client details entity object.
     */
    ClientDetailsEntity getClient();
    
    /**
     * Obtiene el rol/permiso para el cliente.
     * 
     * @return
     */
    String getAuthority();
    
}

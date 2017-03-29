package org.mitre.openid.connect.model;

import java.io.Serializable;

/**
 * Represents the relation between user info and realm.
 *
 */
public interface UserInfoRealmDetails extends Serializable {
    
    /**
     * Gets the realm of the relation.
     * 
     * @return Realm details entity.
     */
    RealmDetails getRealm();
    
    /**
     * Gets if the user is admin in the relation.
     * 
     * @return
     */
    boolean isAdmin();

}

package org.mitre.openid.connect.model;

import java.io.Serializable;
import java.util.Set;

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
    RealmDetailsEntity getRealm();
    
    /**
     * Gets if the user is admin in the relation.
     * 
     * @return true if is admin of the realm.
     */
    boolean isAdmin();
    
    /**
     * Gets the properties of the user_info in the realm.
     * 
     * @return the property list
     */
    Set<UserInfoRealmProperty> getRealmProperties();

}

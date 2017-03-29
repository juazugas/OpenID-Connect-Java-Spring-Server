package org.mitre.openid.connect.repository;

import java.util.Set;

import org.mitre.openid.connect.model.RealmDetailsEntity;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoRealmProperty;

/**
 * Repository for the properties of the user_info in a realm.
 * 
 * @author jzuriaga
 */
public interface UserInfoRealmPropertyRepository {

    /**
     * Retrieves the properties of the user_info in a realm.
     * 
     * @param userInfo
     *            the user_info
     * @return 
     *            the user_info properties by realm
     */
    Set<UserInfoRealmProperty> getByUserInfoAndRealm(UserInfo userInfo, RealmDetailsEntity realm);

}

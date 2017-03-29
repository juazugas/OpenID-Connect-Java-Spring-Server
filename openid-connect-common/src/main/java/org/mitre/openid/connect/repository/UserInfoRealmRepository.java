package org.mitre.openid.connect.repository;

import java.util.Set;

import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoRealmDetails;

/**
 * Repository of the relation between the user_info and the realm.
 * 
 * @author jzuriaga
 *
 */
public interface UserInfoRealmRepository {
    
    /**
     * Retrieves the relation between of the user_info in a realm.
     * 
     * @param userInfo
     *          the user_info
     * @return 
     *          the user_info properties by realm
     */
    Set<UserInfoRealmDetails> getByUserInfo(UserInfo userInfo);

}

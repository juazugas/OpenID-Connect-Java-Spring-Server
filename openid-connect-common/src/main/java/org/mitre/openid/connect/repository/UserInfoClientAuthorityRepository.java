package org.mitre.openid.connect.repository;

import java.util.Set;

import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoClientAuthority;

/**
 * Repository for the user info properties by client.
 *
 */
public interface UserInfoClientAuthorityRepository {

    /**
     * Retrieves the authorities.
     * 
     * @param userInfo
     * @return the user info authorities by client.
     */
    Set<UserInfoClientAuthority> getByUserInfo(UserInfo userInfo);

}

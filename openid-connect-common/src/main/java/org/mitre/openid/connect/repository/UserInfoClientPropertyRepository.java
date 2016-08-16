package org.mitre.openid.connect.repository;

import java.util.Set;

import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoClientProperty;

/**
 * Repository for the user info properties by client.
 *
 */
public interface UserInfoClientPropertyRepository {

    /**
     * Retrieves the properties.
     * 
     * @param userInfo
     * @return the user info properties by client.
     */
    Set<UserInfoClientProperty> getByUserInfo(UserInfo userInfo);

}

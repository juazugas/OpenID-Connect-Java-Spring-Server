/**
 * 
 */
package org.mitre.openid.connect.repository;

import java.util.Set;

import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoClientDetails;

/**
 * Repository for the user info details by client.
 *
 */
public interface UserInfoClientDetailsRepository {

    /**
     * Retrieves the details.
     * 
     * @param userInfo
     * @return the user info details by client.
     */
    Set<UserInfoClientDetails> getByUserInfo(UserInfo userInfo);

}

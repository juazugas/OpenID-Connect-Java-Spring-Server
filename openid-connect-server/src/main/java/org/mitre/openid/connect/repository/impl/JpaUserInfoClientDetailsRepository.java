package org.mitre.openid.connect.repository.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.DefaultUserInfoClientDetails;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoClientDetails;
import org.mitre.openid.connect.repository.UserInfoClientDetailsRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Implementation of the Repository.
 *
 */
@Repository("jpaUserInfoClientDetailsRepository")
public class JpaUserInfoClientDetailsRepository implements UserInfoClientDetailsRepository {

    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManager manager;
    
    /* (non-Javadoc)
     * @see org.mitre.openid.connect.repository.UserInfoClientDetailsRepository#getByUserInfo(org.mitre.openid.connect.model.UserInfo)
     */
    @Override
    public Set<UserInfoClientDetails> getByUserInfo(UserInfo userInfo) {
        DefaultUserInfo defaultUserInfo = (DefaultUserInfo) userInfo;
        
        TypedQuery<DefaultUserInfoClientDetails> query = manager.createNamedQuery(DefaultUserInfoClientDetails.QUERY_BY_USERINFO, DefaultUserInfoClientDetails.class);
        query.setParameter(DefaultUserInfoClientDetails.PARAM_USERINFO, defaultUserInfo.getId());

        Collection<DefaultUserInfoClientDetails> defaultUserInfoDetails = query.getResultList();
        Set<UserInfoClientDetails> userInfoDetails = null;
        if (defaultUserInfoDetails!=null) {
            userInfoDetails = new HashSet<>();
            userInfoDetails.addAll(defaultUserInfoDetails);
        }
        return userInfoDetails;
    }

}

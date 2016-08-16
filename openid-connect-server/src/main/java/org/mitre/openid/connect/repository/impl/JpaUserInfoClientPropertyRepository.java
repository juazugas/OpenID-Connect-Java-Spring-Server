package org.mitre.openid.connect.repository.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.DefaultUserInfoClientProperty;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoClientProperty;
import org.mitre.openid.connect.repository.UserInfoClientPropertyRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Implementation of the Repository.
 *
 */
@Repository("jpaUserInfoClientPropertyRepository")
public class JpaUserInfoClientPropertyRepository implements UserInfoClientPropertyRepository {

    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManager manager;
    
    /* (non-Javadoc)
     * @see org.mitre.openid.connect.repository.UserInfoClientPropertyRepository#getByUserInfo(org.mitre.openid.connect.model.UserInfo)
     */
    @Override
    public Set<UserInfoClientProperty> getByUserInfo(UserInfo userInfo) {
        DefaultUserInfo defaultUserInfo = (DefaultUserInfo) userInfo;
        
        TypedQuery<DefaultUserInfoClientProperty> query = manager.createNamedQuery(DefaultUserInfoClientProperty.QUERY_BY_USERINFO, DefaultUserInfoClientProperty.class);
        query.setParameter(DefaultUserInfoClientProperty.PARAM_USERINFO, defaultUserInfo.getId());

        Collection<DefaultUserInfoClientProperty> defaultUserInfoProperty = query.getResultList();
        Set<UserInfoClientProperty> userInfoProperty = null;
        if (defaultUserInfoProperty!=null) {
            userInfoProperty = new HashSet<>();
            userInfoProperty.addAll(defaultUserInfoProperty);
        }
        return userInfoProperty;
    }

}


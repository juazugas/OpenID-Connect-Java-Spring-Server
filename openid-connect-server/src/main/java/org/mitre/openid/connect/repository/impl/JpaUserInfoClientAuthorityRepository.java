package org.mitre.openid.connect.repository.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.DefaultUserInfoClientAuthority;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoClientAuthority;
import org.mitre.openid.connect.repository.UserInfoClientAuthorityRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA Implementation of the Repository.
 *
 */
@Repository("jpaUserInfoClientAuthorityRepository")
public class JpaUserInfoClientAuthorityRepository implements UserInfoClientAuthorityRepository {

    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManager manager;
    
    /* (non-Javadoc)
     * @see org.mitre.openid.connect.repository.UserInfoClientAuthorityRepository#getByUserInfo(org.mitre.openid.connect.model.UserInfo)
     */
    @Override
    public Set<UserInfoClientAuthority> getByUserInfo(UserInfo userInfo) {
        DefaultUserInfo defaultUserInfo = (DefaultUserInfo) userInfo;
        
        TypedQuery<DefaultUserInfoClientAuthority> query = manager.createNamedQuery(DefaultUserInfoClientAuthority.QUERY_BY_USERINFO, DefaultUserInfoClientAuthority.class);
        query.setParameter(DefaultUserInfoClientAuthority.PARAM_USERINFO, defaultUserInfo.getId());

        Collection<DefaultUserInfoClientAuthority> defaultUserInfoAuthority = query.getResultList();
        Set<UserInfoClientAuthority> userInfoAuthority = null;
        if (defaultUserInfoAuthority!=null) {
            userInfoAuthority = new HashSet<>();
            userInfoAuthority.addAll(defaultUserInfoAuthority);
        }
        return userInfoAuthority;
    }

}


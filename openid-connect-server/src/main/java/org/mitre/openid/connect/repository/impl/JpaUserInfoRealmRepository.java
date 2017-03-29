package org.mitre.openid.connect.repository.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.DefaultUserInfoRealmDetails;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoRealmDetails;
import org.mitre.openid.connect.repository.UserInfoRealmRepository;
import org.springframework.stereotype.Repository;


/**
 * Implements the Repository for relations between user_info and realms.
 * 
 * @author jzuriaga
 *
 */
@Repository("jpaUserInfoRealmRepository")
public class JpaUserInfoRealmRepository implements UserInfoRealmRepository {


    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManager manager;
    
    /* (non-Javadoc)
     * @see org.mitre.openid.connect.repository.UserInfoRealmRepository#getByUserInfo(org.mitre.openid.connect.model.UserInfo)
     */
    @Override
    public Set<UserInfoRealmDetails> getByUserInfo(UserInfo userInfo) {
        DefaultUserInfo defaultUserInfo = (DefaultUserInfo) userInfo;
        
        TypedQuery<DefaultUserInfoRealmDetails> query = manager.createNamedQuery(DefaultUserInfoRealmDetails.QUERY_ALL_BY_USERINFO, DefaultUserInfoRealmDetails.class);
        query.setParameter(DefaultUserInfoRealmDetails.PARAM_USERINFO, defaultUserInfo.getId());

        Collection<DefaultUserInfoRealmDetails> defaultUserInfoRealms = query.getResultList();
        Set<UserInfoRealmDetails> userInfoRealms = null;
        if (defaultUserInfoRealms!=null) {
            userInfoRealms = new HashSet<>();
            userInfoRealms.addAll(defaultUserInfoRealms);
        }
        return userInfoRealms;
    }

}

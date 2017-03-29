package org.mitre.openid.connect.repository.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.DefaultUserInfoRealmProperty;
import org.mitre.openid.connect.model.RealmDetailsEntity;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.model.UserInfoRealmProperty;
import org.mitre.openid.connect.repository.UserInfoRealmPropertyRepository;
import org.springframework.stereotype.Repository;


/**
 * Jpa implementation for repository of properties of the user_info in a realm. 
 * 
 * @author jzuriaga
 *
 */
@Repository("JpaUserInfoRealmPropertyRepository")
public class JpaUserInfoRealmPropertyRepository implements UserInfoRealmPropertyRepository {

    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManager manager;
    
    /* (non-Javadoc)
     * @see org.mitre.openid.connect.repository.UserInfoRealmPropertyRepository#getByUserInfoAndRealm(org.mitre.openid.connect.model.UserInfo, org.mitre.openid.connect.model.RealmDetailsEntity)
     */
    @Override
    public Set<UserInfoRealmProperty> getByUserInfoAndRealm(UserInfo userInfo, RealmDetailsEntity realm) {
        DefaultUserInfo defaultUserInfo = (DefaultUserInfo) userInfo;
        
        TypedQuery<DefaultUserInfoRealmProperty> query = manager.createNamedQuery(DefaultUserInfoRealmProperty.QUERY_BY_USER_INFO_AND_REALM, DefaultUserInfoRealmProperty.class);
        query.setParameter(DefaultUserInfoRealmProperty.PARAM_USERINFO, defaultUserInfo.getId());
        query.setParameter(DefaultUserInfoRealmProperty.PARAM_REALM, realm.getId());

        Collection<DefaultUserInfoRealmProperty> defaultRealmProperties = query.getResultList();
        Set<UserInfoRealmProperty> realmProperties = null;
        if (defaultRealmProperties!=null) {
            realmProperties = new HashSet<>();
            realmProperties.addAll(defaultRealmProperties);
        }
        return realmProperties;
    }

}

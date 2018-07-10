package org.mitre.openid.connect.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.openid.connect.model.UserDetailsEntity;
import org.mitre.openid.connect.repository.UserDetailsRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa implementation for the repository of user entity.
 * 
 * @author jzuriaga
 *
 */
@Repository("JpaUserDetailsRepository")
public class JpaUserDetailsRepository implements UserDetailsRepository {
	
    @PersistenceContext(unitName="defaultPersistenceUnit")
    private EntityManager manager;

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.repository.UserDetailsRepository#getByUsername(java.lang.String)
	 */
	@Override
	public UserDetailsEntity getByUsername(String username) {
        TypedQuery<UserDetailsEntity> query = manager.createNamedQuery(UserDetailsEntity.QUERY_BY_USER_NAME, UserDetailsEntity.class);
        query.setParameter(UserDetailsEntity.PARAM_USER_NAME, username);
        
        return query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.repository.UserDetailsRepository#getById(java.lang.Long)
	 */
	@Override
	public UserDetailsEntity getById(Long id) {
        TypedQuery<UserDetailsEntity> query = manager.createNamedQuery(UserDetailsEntity.QUERY_BY_USER_ID, UserDetailsEntity.class);
        query.setParameter(UserDetailsEntity.PARAM_USER_ID, id);
        
        return query.getSingleResult();
	}

}

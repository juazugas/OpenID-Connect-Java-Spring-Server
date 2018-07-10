package org.mitre.openid.connect.repository;

import org.mitre.openid.connect.model.UserDetailsEntity;

/**
 * Represents a repository for the user object.
 * 
 * @author jzuriaga
 *
 */
public interface UserDetailsRepository {
	
	/**
	 * Gets the user entity by the unique user name key.
	 * 
	 * @param username 
	 * 				the user name.
	 * @return
	 * 				the user entity.
	 */
	UserDetailsEntity getByUsername(String username);
	
	/**
	 * Gets the user entity by the primary key.
	 * 
	 * @param id
	 * @return
	 */
	UserDetailsEntity getById(Long id);
	
}

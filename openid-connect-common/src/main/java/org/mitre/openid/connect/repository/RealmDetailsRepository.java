package org.mitre.openid.connect.repository;

import org.mitre.openid.connect.model.RealmDetailsEntity;

/**
 * Represents a repository for the realm object.
 * 
 * @author jzuriaga
 */
public interface RealmDetailsRepository {
    
    /**
     * Gets the realm by its identifier.
     * 
     * @param id
     *          the identifier
     * @return
     *          the realm details
     */
    public RealmDetailsEntity getById(Long id);

    /**
     * Gets the realm by its name. 
     * 
     * @param realmName
     *          the name
     * @return
     *          the realm details
     */
    public RealmDetailsEntity getClientByName(String realmName);


}

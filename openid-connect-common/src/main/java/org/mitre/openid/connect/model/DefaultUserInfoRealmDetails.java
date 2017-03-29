package org.mitre.openid.connect.model;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Represents the details of a user_info relation with the realm.
 *
 */
@Entity
@Table(name="user_realms")
@NamedQueries({
    @NamedQuery(name  = DefaultUserInfoRealmDetails.QUERY_ALL_BY_USERINFO, 
                query = "SELECT c FROM DefaultUserInfoRealmDetails c WHERE c.idUserInfo = :" + DefaultUserInfoRealmDetails.PARAM_USERINFO),
    @NamedQuery(name  = DefaultUserInfoRealmDetails.QUERY_BY_USERINFO_AND_REALM, 
                query = "SELECT u FROM DefaultUserInfoRealmDetails u " + 
                        " WHERE u.idUserInfo = :" + DefaultUserInfoRealmDetails.PARAM_USERINFO + " AND u.realm.id = :realm" + DefaultUserInfoRealmDetails.PARAM_REALM)  
})
public class DefaultUserInfoRealmDetails implements UserInfoRealmDetails {

    public static final String QUERY_ALL_BY_USERINFO = "DefaultUserInfoRealmDetails.findAllByUserInfo";
    public static final String QUERY_BY_USERINFO_AND_REALM = "DefaultUserInfoRealmDetails.findByUserInfoAndRealm";
    
    public static final String PARAM_USERINFO = "userInfoId";
    public static final String PARAM_REALM    = "realmId";
    
    public Long id;
    private Long idUserInfo;
    private RealmDetailsEntity realm;
    private Set<UserInfoRealmProperty> properties;
    
    /**
     * determines if the user_info is admin in the realm. 
     */
    private boolean admin;
    
    /**
     * Computed serial version Id.
     */
    private static final long serialVersionUID = 4243157902333044137L;
    
    /**
     * Gets the id of the realm
     * 
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the idUserInfo
     */
    @Basic
    @Column(name="user_info_id")
    public Long getIdUserInfo() {
        return idUserInfo;
    }

    
    /**
     * @param idUserInfo the idUserInfo to set
     */
    public void setIdUserInfo(Long idUserInfo) {
        this.idUserInfo = idUserInfo;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoRealmDetails#getRealm()
     */
    @OneToOne
    @JoinColumn(name="realm_id", referencedColumnName= "id")
    @Override
    public RealmDetailsEntity getRealm() {
        return this.realm;
    }
    
    /**
     * Introduces the realm.
     * 
     * @param realm
     *          realm of the relation.
     */
    public void setRealm(RealmDetailsEntity realm) {
        this.realm = realm;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoRealmDetails#isAdmin()
     */
    @Basic
    @Column(name="is_admin")
    @Override
    public boolean isAdmin() {
        return this.admin;
    }

    /**
     * Introduces if the user is admin in the realm.
     * 
     * @param admin
     *          true if the user is admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
    /**
     * @return the properties
     */
    @Transient
    @Override
    public Set<UserInfoRealmProperty> getRealmProperties() {
        return properties;
    }

    
    /**
     * @param properties the properties to set
     */
    public void setRealmProperties(Set<UserInfoRealmProperty> properties) {
        this.properties = properties;
    }
    
    
    
}

package org.mitre.openid.connect.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.mitre.oauth2.model.ClientDetailsEntity;

/**
 * 
 *
 */
@Entity
@Table(name="user_info_details")
@NamedQueries({
    @NamedQuery(name=DefaultUserInfoClientDetails.QUERY_BY_USERINFO, query = "select u from DefaultUserInfoClientDetails u WHERE u.idUserInfo = :" + DefaultUserInfoClientDetails.PARAM_USERINFO)  
})
public class DefaultUserInfoClientDetails implements UserInfoClientDetails {
    
    
    public static final String QUERY_BY_USERINFO = "DefaultUserInfoClientDetails.QueryByUserInfo";
    public static final String PARAM_USERINFO = "userInfoId";
    
    private static final long serialVersionUID = 1L;
    
    private long id;
    
    private long idUserInfo;
    private ClientDetailsEntity client;
    
    private boolean enabled;
    

    /**
     * Retrieves the id 
     * 
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }
    
    /**
     * Sets the id of the table.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }
    

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#getUserInfo()
     */
    /*@OneToOne(fetch=FetchType.LAZY, targetEntity = DefaultUserInfo.class)
    @JoinTable(name="user_info", 
        joinColumns = { @JoinColumn(name="id", referencedColumnName="id_user_info") },
        inverseJoinColumns = { @JoinColumn(name="id", referencedColumnName="id_user_info") }
    )*/
    @Column(name="id_user_info")
    public long getIdUserInfo() {
        return idUserInfo;
    }
    
    public void setIdUserInfo(long id) {
        this.idUserInfo = id;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#getClientDetails()
     */
    @OneToOne
    @JoinColumn(name="id_client_details", referencedColumnName= "id")
    @Override
    public ClientDetailsEntity getClient() {
        return client;
    }
    
    public void setClient (ClientDetailsEntity clientDetails) {
        this.client = clientDetails;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#getUsername()
     */
	@Transient
    @Override
    public String getUsername() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#isAccountNonExpired()
     */
	@Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#isAccountNonLocked()
     */
	@Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#isCredentialsNonExpired()
     */
	@Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#isEnabled()
     */
    @Basic
    @Column(name="user_enabled")
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

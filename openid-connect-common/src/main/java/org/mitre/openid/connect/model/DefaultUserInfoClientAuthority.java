package org.mitre.openid.connect.model;

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

import org.mitre.oauth2.model.ClientDetailsEntity;


/**
 * Autoridades del usuario por cliente.
 * 
 * @see UserInfoClientAuthority
 * @see UserInfo
 * @see org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
 */
@Entity
@Table(name="user_info_authority")
@NamedQueries({
    @NamedQuery(name=DefaultUserInfoClientAuthority.QUERY_BY_USERINFO, query = "select u from DefaultUserInfoClientAuthority u WHERE u.idUserInfo = :" + DefaultUserInfoClientAuthority.PARAM_USERINFO)  
})
public class DefaultUserInfoClientAuthority implements UserInfoClientAuthority {
    
    public static final String QUERY_BY_USERINFO = "DefaultUserInfoClientAuthority.QueryByUserInfo";
    public static final String PARAM_USERINFO = "userInfoId";

    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la tupla.
     */
    private Long id;
    
    /**
     * Identificador del usuario de la propiedad.
     */
    private Long idUserInfo;
    
    /**
     * Cliente asociado a la propiedad.
     */
    private ClientDetailsEntity client;
    
    /**
     * Clave de la Propiedad. 
     */
    private String authority;
    
    
    
    /**
     * 
     */
    public DefaultUserInfoClientAuthority() {
    }

    /**
     * Retrieves the id 
     * 
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the id of the table.
     * 
     * @param id
     */
    public void setId(Long id) {
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
    public Long getIdUserInfo() {
        return idUserInfo;
    }
    
    /**
     * Sets the user_info identifier.
     * @param id
     */
    public void setIdUserInfo(Long id) {
        this.idUserInfo = id;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientDetails#getClientDetails()
     */
    @OneToOne
    @JoinColumn(name="id_client_details", referencedColumnName= "id")
    @Override
    public ClientDetailsEntity getClient() {
        return this.client;
    }
    
    /**
     * Sets the client_details for the property.
     * @param client
     */
    public void setClient(ClientDetailsEntity client) {
        this.client = client;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientAuthority#getAuthority()
     */
    @Column(name="authority")
    @Override
    public String getAuthority() {
        return this.authority;
    }
    
    /**
     * Sets the authority
     * 
     * @param authority
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

}

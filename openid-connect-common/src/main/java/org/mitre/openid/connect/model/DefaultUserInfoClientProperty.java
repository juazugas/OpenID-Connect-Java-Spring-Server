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
 * Propiedades del usuario por cliente.
 * 
 * @see UserInfoClientProperty
 * @see UserInfo
 */
@Entity
@Table(name="user_info_property")
@NamedQueries({
    @NamedQuery(name=DefaultUserInfoClientProperty.QUERY_BY_USERINFO, query = "select u from DefaultUserInfoClientProperty u WHERE u.idUserInfo = :" + DefaultUserInfoClientProperty.PARAM_USERINFO)  
})
public class DefaultUserInfoClientProperty implements UserInfoClientProperty {
    
    public static final String QUERY_BY_USERINFO = "DefaultUserInfoClientProperty.QueryByUserInfo";
    public static final String PARAM_USERINFO = "userInfoId";

    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la tupla.
     */
    private long id;
    
    /**
     * Identificador del usuario de la propiedad.
     */
    private long idUserInfo;
    
    /**
     * Cliente asociado a la propiedad.
     */
    private ClientDetailsEntity client;
    
    /**
     * Clave de la Propiedad. 
     */
    private String property;
    
    /**
     * Valor de la propiedad.
     */
    private String value;
    

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
    
    /**
     * Sets the user_info identifier.
     * @param id
     */
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
     * @see org.mitre.openid.connect.model.UserInfoClientProperty#getProperty()
     */
    @Column(name="property_key")
    @Override
    public String getProperty() {
        return this.property;
    }
    
    /**
     * Sets the property key
     * @param property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /* (non-Javadoc)
     * @see org.mitre.openid.connect.model.UserInfoClientProperty#getValue()
     */
    @Column(name="property_value")
    @Override
    public String getValue() {
        return this.value;
    }
    
    /**
     * Sets the property value.
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

}

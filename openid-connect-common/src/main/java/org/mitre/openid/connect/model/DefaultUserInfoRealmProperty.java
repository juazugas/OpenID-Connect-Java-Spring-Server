package org.mitre.openid.connect.model;

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

/**
 * Represents the property of the user_info in the realm.
 * 
 * @author jzuriaga
 *
 */
@Entity
@Table(name = "user_realms_property")
@NamedQueries({ 
    @NamedQuery(name = DefaultUserInfoRealmProperty.QUERY_ALL_BY_USER_INFO,
                query = "SELECT u FROM DefaultUserInfoRealmProperty u WHERE u.userInfoRealm.user_info_id = :" +
                        DefaultUserInfoRealmProperty.PARAM_USERINFO + " ORDER BY u.userInfoRealm.id, u.id") })
public class DefaultUserInfoRealmProperty implements UserInfoRealmProperty {

    public static final String QUERY_ALL_BY_USER_INFO = "DefaultUserInfoRealmProperty.QueryAllByUserInfo";
    public static final String PARAM_USERINFO = "userInfoId";

    /**
     * Computed serial version Id.
     */
    private static final long serialVersionUID = -1289037485802934590L;

    private Long id;
    private DefaultUserInfoRealmDetails userInfoRealm;
    private String property;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
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

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.UserInfoRealmProperty#getUserInfoRealm()
     */
    @OneToOne
    @JoinColumn(name = "user_realms_id", referencedColumnName = "id")
    @Override
    public UserInfoRealmDetails getUserInfoRealm() {
        return userInfoRealm;
    }

    /**
     * Introduces the relation between user_info and the realm.
     * 
     * @param userInfoRealm
     */
    public void setUserInfoRealm(DefaultUserInfoRealmDetails userInfoRealm) {
        this.userInfoRealm = userInfoRealm;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.UserInfoRealmProperty#getProperty()
     */
    @Basic
    @Column(name = "property")
    @Override
    public String getProperty() {
        return this.property;
    }

    /**
     * Introduces the property.
     * 
     * @param property
     *            the property key
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.UserInfoRealmProperty#getValue()
     */
    @Basic
    @Column(name = "value")
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * Introduces the value of the property.
     * 
     * @param value
     *            the value
     */
    public void setValue(String value) {
        this.value = value;
    }
}

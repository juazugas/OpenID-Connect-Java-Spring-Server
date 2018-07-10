package org.mitre.openid.connect.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * User entity from table `user`
 *
 * @author jzuriaga
 *
 */
@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = UserDetailsEntity.QUERY_BY_USER_ID,
            query = "select c from UserDetailsEntity c where c.id = :" + UserDetailsEntity.PARAM_USER_ID),
    @NamedQuery(name = UserDetailsEntity.QUERY_BY_USER_NAME,
            query = "select c from UserDetailsEntity c where c.username = :" + UserDetailsEntity.PARAM_USER_NAME) })
public class UserDetailsEntity implements UserDetails {

    public static final String QUERY_BY_USER_ID = "UserDetailsEntity.getById";
    public static final String QUERY_BY_USER_NAME = "UserDetailsEntity.getByUsername";

    public static final String PARAM_USER_ID = "id";
    public static final String PARAM_USER_NAME = "name";

    /**
     * Add computation of serial version Id.
     */
    private static final long serialVersionUID = 2178690613564473682L;

    private Long id;
    private String username;
    private boolean enabled;
    private boolean enrollment;
	private Date createDt;
    private Map<String, String> userProperties;

    /**
     * gets the user `id`
     *
     * @return the long with the `user_id`
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long getId() {
		return id;
	}

    /**
     * Sets the user `id`
     *
     * @param id
     */
    public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.model.UserDetails#getUsername()
	 */
    @Basic
	@Column(name = "username")
	@Override
	public String getUsername() {
		return this.username;
	}

    /**
     * Sets the user name.
     *
     * @param username string with the username.
     */
    public void setUsername(String username) {
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.model.UserDetails#isEnabled()
	 */
    @Basic
    @Column(name = "is_enabled")
	@Override
	public boolean isEnabled() {
    	return this.enabled;
	}

    /**
     * sets the enabled flag of the user.
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.model.UserDetails#isEnrollment()
	 */
    @Basic
    @Column(name = "is_enrollment")
	@Override
	public boolean isEnrollment() {
		return this.enrollment;
	}

    /**
     * Sets the enrollment property.
     *
     * @param enrollment
     */
    public void setEnrollment(boolean enrollment) {
		this.enrollment = enrollment;
	}

	/* (non-Javadoc)
	 * @see org.mitre.openid.connect.model.UserDetails#getUserProperties()
	 */
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_property", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "property_key")
    @Column(name = "property_value")
	@Override
	public Map<String, String> getUserProperties() {
		return this.userProperties;
	}

    /**
     * Sets the user properties.
     *
     * @param userProperties
     */
    public void setUserProperties(Map<String, String> userProperties) {
    	this.userProperties = userProperties;
	}

    /*
     * (non-Javadoc)
     *
     * @see org.mitre.openid.connect.model.RealmDetails#getCreatedDt()
     */
    @Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_dt")
	public Date getCreateDt () {
		return this.createDt;
    }

    /**
     * Introduces de creation date.
     *
     * @param createdDt
     *            creation date
     */
	public void setCreateDt (Date createdDt) {
		this.createDt = createdDt;
    }


}

package org.mitre.openid.connect.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Represents the details of a realm.
 *
 */
@Entity
@Table(name = "realm")
@NamedQueries({
    @NamedQuery(name = RealmDetailsEntity.QUERY_BY_REALM_ID,
            query = "select c from RealmDetailsEntity c where c.id = :" + RealmDetailsEntity.PARAM_REALM_ID),
    @NamedQuery(name = RealmDetailsEntity.QUERY_BY_REALM_NAME,
            query = "select c from RealmDetailsEntity c where c.name = :" + RealmDetailsEntity.PARAM_REALM_NAME) })
public class RealmDetailsEntity implements RealmDetails {

    public static final String QUERY_BY_REALM_ID = "RealmDetailsEntity.getById";
    public static final String QUERY_BY_REALM_NAME = "RealmDetailsEntity.getByName";

    public static final String PARAM_REALM_ID = "id";
    public static final String PARAM_REALM_NAME = "name";

    /**
     * Add computation of serial version Id.
     */
    private static final long serialVersionUID = 2178690613564473682L;

    private Long id;
    private String name;
    private String shortDesc;
    private boolean enabled;
    private String description;
    private Date createdDt;
    private Date deleteDt;
    private Date disableDt;
    private String adminEmail;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getName()
     */
    @Basic
    @Column(name = "name")
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Introduces the name of the realm.
     * 
     * @param name
     *            realm name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getShortDesc()
     */
    @Basic
    @Column(name = "short_desc")
    @Override
    public String getShortDesc() {
        return this.shortDesc;
    }

    /**
     * Introduces the short description.
     * 
     * @param shortDesc
     *            realm short desc
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#isEnabled()
     */
    @Basic
    @Column(name = "is_enabled")
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Introduces if the realm is enabled.
     * 
     * @param enabled
     *            true if it is enabled.
     */

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getDescription()
     */
    @Basic
    @Column(name = "description")
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Introduces the description of the realm.
     * 
     * @param description
     *            the realm description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getCreatedDt()
     */
    @Basic
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "created_dt")
    @Override
    public Date getCreatedDt() {
        return this.createdDt;
    }

    /**
     * Introduces de creation date.
     * 
     * @param createdDt
     *            creation date
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getDeleteDt()
     */
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "delete_dt")
    @Override
    public Date getDeleteDt() {
        return this.deleteDt;
    }

    /**
     * Introduces the deletion date.
     * 
     * @param deleteDt
     *            deletion date
     */
    public void setDeleteDt(Date deleteDt) {
        this.deleteDt = deleteDt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getAdminEmail()
     */
    @Basic
    @Column(name = "admin_email")
    @Override
    public String getAdminEmail() {
        return this.adminEmail;
    }

    /**
     * Introduces
     * 
     * @param adminEmail
     */
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mitre.openid.connect.model.RealmDetails#getDisableDt()
     */
    @Basic
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "disable_dt")
    @Override
    public Date getDisableDt() {
        return this.disableDt;
    }

    /**
     * Introduces the disable date
     * 
     * @param disableDt
     *            disable date
     */
    public void setDisableDt(Date disableDt) {
        this.disableDt = disableDt;
    }

}

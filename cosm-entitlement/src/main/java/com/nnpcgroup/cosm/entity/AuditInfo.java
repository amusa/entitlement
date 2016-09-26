package com.nnpcgroup.cosm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by maliska on 9/24/16.
 */

@Embeddable
public class AuditInfo implements Serializable{
    private String createdBy;
    private Date createdDate;
    private Date lastModifiedDate;
    private String lastModifiedBy;
    @Transient
    private String currentUser;


    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Column(name = "LAST_MODIFIED_BY")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setCurrentUser(String user) {
        currentUser = user;
    }

    public void prePersist() {
        createdBy = lastModifiedBy;//currentUser;
        createdDate = new Date();
        preUpdate();
    }

    public void preUpdate() {
        //lastModifiedBy = currentUser;
        lastModifiedDate = new Date();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.domain.model;

import org.eclipse.persistence.annotations.Customizer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 18359
 */
@Customizer(ProductionCustomizer.class)
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "PRODUCTION")
public class Production implements Auditable, Serializable {

    private static final long serialVersionUID = -705843614381155070L;

    private ProductionPK productionPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Long jvId;
    private String remark;
    private List<ProductionDetail> productionDetails;
    private List<ProductionEntitlement> entitlements;
    private AuditInfo auditInfo = new AuditInfo();

    public Production() {
    }

    @EmbeddedId
    public ProductionPK getProductionPK() {
        return productionPK;
    }

    public void setProductionPK(ProductionPK productionPK) {
        this.productionPK = productionPK;
    }

    @Column(name = "PERIOD_YEAR", updatable = false, insertable = false)
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(name = "PERIOD_MONTH", updatable = false, insertable = false)
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Column(name = "JV_ID", updatable = false, insertable = false)
    public Long getJvId() {
        return jvId;
    }

    public void setJvId(Long jvId) {
        this.jvId = jvId;
    }


    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    public void initialize(Forecast forecast) {
//        this.periodMonth = forecast.getPeriodMonth();
//        this.periodYear = forecast.getPeriodYear();
//        this.contract = forecast.getFiscalArrangement();
//        this.productionPK = forecast.makeProductionPK();
//    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<ProductionDetail> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<ProductionDetail> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public void addProductionDetail(ProductionDetail productionDetail) {
        if (productionDetails == null) {
            productionDetails = new ArrayList<>();

        }
        productionDetails.add(productionDetail);
    }

    public void addEntitlement(ProductionEntitlement entitlement) {
        if (entitlements == null) {
            entitlements = new ArrayList<>();

        }
        entitlements.add(entitlement);
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<ProductionEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<ProductionEntitlement> entitlements) {
        this.entitlements = entitlements;
    }

    public void setCurrentUser(String user) {
//        auditInfo.setCurrentUser(user);
        getAuditInfo().setLastModifiedBy(user);
    }

    @Embedded
    public AuditInfo getAuditInfo() {
        if (auditInfo == null) {
            auditInfo = new AuditInfo();
        }
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.productionPK);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Production other = (Production) obj;
        if (!Objects.equals(this.productionPK, other.productionPK)) {
            return false;
        }
        return true;
    }

}

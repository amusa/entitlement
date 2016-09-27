/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.AuditListener;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.forecast.jv.Forecast;
import org.eclipse.persistence.annotations.Customizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * @author 18359
 * @param <E>
 */
@Customizer(ProductionCustomizer.class)
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "PRODUCTION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PTYPE")
public abstract class Production<E extends ProductionDetail> implements Auditable, Serializable {

    private static final long serialVersionUID = -705843614381155070L;

    private ProductionPK productionPK;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement fiscalArrangement;
    private String remark;
    private List<E> productionDetails;
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

    @ManyToOne
    @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    @MapsId("fiscalArrangementId")
    public FiscalArrangement getFiscalArrangement() {
        return fiscalArrangement;
    }

    public void setFiscalArrangement(FiscalArrangement fiscalArrangement) {
        this.fiscalArrangement = fiscalArrangement;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = ProductionDetail.class)
    public List<E> getProductionDetails() {
        return productionDetails;
    }

    public void setProductionDetails(List<E> productionDetails) {
        this.productionDetails = productionDetails;
    }

    public void addProductionDetail(E productionDetail) {
        if (productionDetails == null) {
            productionDetails = new ArrayList<>();

        }
        productionDetails.add(productionDetail);
    }

    public void initialize(Forecast forecast) {
        this.periodMonth = forecast.getPeriodMonth();
        this.periodYear = forecast.getPeriodYear();
        this.fiscalArrangement = forecast.getFiscalArrangement();
        this.productionPK = forecast.makeProductionPK();
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
        final Production<?> other = (Production<?>) obj;
        if (!Objects.equals(this.productionPK, other.productionPK)) {
            return false;
        }
        return true;
    }

}

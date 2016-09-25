/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.AuditInfo;
import com.nnpcgroup.cosm.entity.AuditListener;
import com.nnpcgroup.cosm.entity.Auditable;
import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import org.eclipse.persistence.annotations.Customizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 * @author 18359
 * @param <E>
 */
@Customizer(ForecastCustomizer.class)
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "FORECAST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
public abstract class Forecast<E extends ForecastDetail>  implements Auditable, Serializable {

    private static final long serialVersionUID = -295843614383355072L;

    private static final Logger LOG = Logger.getLogger(Forecast.class.getName());

    private ForecastPK forecastPK;
    private Integer periodYear;
    private Integer periodMonth;
    private FiscalArrangement fiscalArrangement;
    private String remark;
    private List<E> forecastDetails;
    private AuditInfo auditInfo = new AuditInfo();

    public Forecast() {
    }

    @EmbeddedId
    public ForecastPK getForecastPK() {
        return forecastPK;
    }

    public void setForecastPK(ForecastPK forecastPK) {
        this.forecastPK = forecastPK;
        this.periodYear = forecastPK.getPeriodYear();
        this.periodMonth = forecastPK.getPeriodMonth();
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

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = ForecastDetail.class)
    public List<E> getForecastDetails() {
        return forecastDetails;
    }

    public void setForecastDetails(List<E> forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public void addForecastDetails(E forecastDetail) {
        if (forecastDetails == null) {
            forecastDetails = new ArrayList<>();

        }
        forecastDetails.add(forecastDetail);
    }

    public ProductionPK makeProductionPK() {
        ProductionPK pPK
                = new ProductionPK(this.getPeriodYear(), this.getPeriodMonth(), this.getFiscalArrangement().getId());
        return pPK;
    }

    public void setCurrentUser(String user) {
//        auditInfo.setCurrentUser(user);
        auditInfo.setLastModifiedBy(user);
    }

    @Embedded
    public AuditInfo getAuditInfo() { return auditInfo; }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }
}

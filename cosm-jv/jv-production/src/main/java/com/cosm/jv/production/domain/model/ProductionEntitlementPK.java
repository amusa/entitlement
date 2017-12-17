/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 *
 * @author 18359
 */
@Embeddable
public class ProductionEntitlementPK implements Serializable{

    private static final long serialVersionUID = 2983325339937581443L;

    private Integer periodYear;
    private Integer periodMonth;
    private Long jvId;
    private Long contractId;
    private String crudeTypeCode;

    public ProductionEntitlementPK() {
    }

    public ProductionEntitlementPK(Integer periodYear, Integer periodMonth, Long jvId, Long contractId, String crudeTypeCode) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.jvId = jvId;
        this.contractId = contractId;
        this.crudeTypeCode = crudeTypeCode;
    }

    public ProductionEntitlementPK(ProductionPK productionPK, Long contractId, String crudeTypeCode) {
        this.periodYear = productionPK.getPeriodYear();
        this.periodMonth = productionPK.getPeriodMonth();
        this.jvId = productionPK.getJvId();
        this.contractId = contractId;
        this.crudeTypeCode = crudeTypeCode;
    }

    @Column(name = "PERIOD_YEAR")
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(name = "PERIOD_MONTH")
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Column(name = "JV_ID")
    public Long getJvId() {
        return jvId;
    }

    public void setJvId(Long jvId) {
        this.jvId = jvId;
    }

    @Transient
    public ProductionPK getProductionPK() {
        return new ProductionPK(periodYear, periodMonth, jvId);
    }

    @Column(name = "CONTRACT_ID")
    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }


    @Column(name = "CRUDE_TYPE_CODE")
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductionEntitlementPK that = (ProductionEntitlementPK) o;

        if (!periodYear.equals(that.periodYear)) return false;
        if (!periodMonth.equals(that.periodMonth)) return false;
        if (!jvId.equals(that.jvId)) return false;
        if (!contractId.equals(that.contractId)) return false;
        return crudeTypeCode.equals(that.crudeTypeCode);
    }

    @Override
    public int hashCode() {
        int result = periodYear.hashCode();
        result = 31 * result + periodMonth.hashCode();
        result = 31 * result + jvId.hashCode();
        result = 31 * result + contractId.hashCode();
        result = 31 * result + crudeTypeCode.hashCode();
        return result;
    }
}

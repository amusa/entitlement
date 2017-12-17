/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.domain.model;

import com.cosm.common.domain.util.AuditInfo;
import com.cosm.common.domain.util.Auditable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 18359
 */
@Entity(name = "PRODUCTION_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
@DiscriminatorValue("JV")
public class ProductionDetail implements Auditable, Serializable {

    private static final long serialVersionUID = -115843614381155072L;

    private ProductionDetailPK productionDetailPK;
    protected Integer periodYear;
    protected Integer periodMonth;
    private Double grossProduction;
    private Double dailyProduction;
    private Double stockAdjustment;
    private Double bswLoss;
    private Double meteringInacuracyLoss;
    private Double theftLoss;
    private Double terminalAdjustment;
    private Double productionAdjustment;
    private Double unitization;
    private Double operatorDeclaredVolume;

    protected Long jvId;
    private Long contractId;
    private String crudeTypeCode;
    private Production production;
    protected AuditInfo auditInfo = new AuditInfo();

    public ProductionDetail() {
    }

    @EmbeddedId
    public ProductionDetailPK getProductionDetailPK() {
        return productionDetailPK;
    }

    public void setProductionDetailPK(ProductionDetailPK productionDetailPK) {
        this.productionDetailPK = productionDetailPK;
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

    @Column(name = "CONTRACT_ID", updatable = false, insertable = false)
    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    @Column(name = "CRUDE_TYPE_CODE", updatable = false, insertable = false)
    public String getCrudeTypeCode() {
        return crudeTypeCode;
    }

    public void setCrudeTypeCode(String crudeTypeCode) {
        this.crudeTypeCode = crudeTypeCode;
    }

    @Column(name = "GROSS_PRODUCTION")
    public Double getGrossProduction() {
        return grossProduction;
    }

    public void setGrossProduction(Double grossProduction) {
        this.grossProduction = grossProduction;
    }

    @Column(name = "DAILY_PRODUCTION")
    public Double getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(Double dailyProduction) {
        this.dailyProduction = dailyProduction;
    }


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", insertable = false, updatable = false),
            @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", insertable = false, updatable = false),
            @JoinColumn(name = "JV_ID", referencedColumnName = "JV_ID", insertable = false, updatable = false)
    })
    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    @Transient
    public Double getNetProduction() {
        Double netProduction;
        Double gp = grossProduction != null ? grossProduction : 0;
        Double sa = stockAdjustment != null ? stockAdjustment : 0;
        Double bsw = bswLoss != null ? bswLoss : 0;
        Double metering = meteringInacuracyLoss != null ? meteringInacuracyLoss : 0;
        Double theft = theftLoss != null ? theftLoss : 0;
        Double termAdj = terminalAdjustment != null ? terminalAdjustment : 0;
        Double prodAdj = productionAdjustment != null ? productionAdjustment : 0;
        Double unit = unitization != null ? unitization : 0;

        netProduction = gp + sa - bsw - metering - theft + termAdj + prodAdj + unit;
        return netProduction;
    }

    public Double getStockAdjustment() {
        return stockAdjustment;
    }

    public void setStockAdjustment(Double stockAdjustment) {
        this.stockAdjustment = stockAdjustment;
    }

    public Double getBswLoss() {
        return bswLoss;
    }

    public void setBswLoss(Double bswLoss) {
        this.bswLoss = bswLoss;
    }

    public Double getMeteringInacuracyLoss() {
        return meteringInacuracyLoss;
    }

    public void setMeteringInacuracyLoss(Double meteringInacuracyLoss) {
        this.meteringInacuracyLoss = meteringInacuracyLoss;
    }

    public Double getTheftLoss() {
        return theftLoss;
    }

    public void setTheftLoss(Double theftLoss) {
        this.theftLoss = theftLoss;
    }

    public Double getTerminalAdjustment() {
        return terminalAdjustment;
    }

    public void setTerminalAdjustment(Double terminalAdjustment) {
        this.terminalAdjustment = terminalAdjustment;
    }

    public Double getProductionAdjustment() {
        return productionAdjustment;
    }

    public void setProductionAdjustment(Double productionAdjustment) {
        this.productionAdjustment = productionAdjustment;
    }

    public Double getUnitization() {
        return unitization;
    }

    public void setUnitization(Double unitization) {
        this.unitization = unitization;
    }

    public Double getOperatorDeclaredVolume() {
        return operatorDeclaredVolume;
    }

    public void setOperatorDeclaredVolume(Double operatorDeclaredVolume) {
        this.operatorDeclaredVolume = operatorDeclaredVolume;
    }


//    public void duplicate(ForecastDetail forecastDetail) {
//        // this.setContract(forecastDetail.getContract());
//        this.setPeriodYear(forecastDetail.getPeriodYear());
//        this.setPeriodMonth(forecastDetail.getPeriodMonth());
//        //this.setProductionDetailPK(forecastDetail.makeProductionDetailPK());
//    }
//
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

}

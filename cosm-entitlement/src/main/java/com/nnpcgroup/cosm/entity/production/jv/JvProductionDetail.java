/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;
import com.nnpcgroup.cosm.entity.forecast.jv.JvForecastDetail;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * @author 18359
 */
@Entity(name = "JV_PRODUCTION_DETAIL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
@DiscriminatorValue("JV")
public class JvProductionDetail extends ProductionDetail {

    private static final long serialVersionUID = -2958434381155072L;

    private JvProductionDetailPK productionDetailPK;
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

    private Contract contract;
    private JvProduction production;

    public JvProductionDetail() {
    }

    @EmbeddedId
    public JvProductionDetailPK getProductionDetailPK() {
        return productionDetailPK;
    }

    public void setProductionDetailPK(JvProductionDetailPK productionDetailPK) {
        this.productionDetailPK = productionDetailPK;
    }

    public Double getGrossProduction() {
        return grossProduction;
    }

    public void setGrossProduction(Double grossProduction) {
        this.grossProduction = grossProduction;
    }

    public Double getDailyProduction() {
        return dailyProduction;
    }

    public void setDailyProduction(Double dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID", insertable = false, updatable = false),
        @JoinColumn(name = "CONTRACT_FISCAL_ID", referencedColumnName = "FISCALARRANGEMENTID", insertable = false, updatable = false),
        @JoinColumn(name = "CRUDETYPE_CODE", referencedColumnName = "CRUDETYPECODE", insertable = false, updatable = false)
    })
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", insertable = false, updatable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", insertable = false, updatable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public JvProduction getProduction() {
        return production;
    }

    public void setProduction(JvProduction production) {
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

    @Override
    public void duplicate(ForecastDetail forecastDetail) {
        super.duplicate(forecastDetail);
        JvForecastDetail jvDetail = (JvForecastDetail) forecastDetail;

        this.grossProduction = jvDetail.getGrossProduction();

//         this.openingStock = jvDetail.getOpeningStock();
//        this.partnerOpeningStock = jvDetail.getPartnerOpeningStock();
//        this.closingStock = jvDetail.getClosingStock();
//        this.partnerClosingStock = jvDetail.getPartnerClosingStock();
//        this.grossProduction = jvDetail.getGrossProduction();
//        this.lifting = jvDetail.getLifting();
//        this.partnerLifting = jvDetail.getPartnerLifting();
//        this.cargos = jvDetail.getCargos();
//        this.partnerCargos = jvDetail.getPartnerCargos();
    }

}

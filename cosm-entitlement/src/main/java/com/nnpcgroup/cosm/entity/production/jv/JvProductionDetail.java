/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvProductionDetail extends ProductionDetail<JvProduction> {

    private static final long serialVersionUID = -2958434381155072L;

    private Double openingStock;
    private Double partnerOpeningStock;
    private Double closingStock;
    private Double partnerClosingStock;
    private Double grossProduction;
    private Double ownShareEntitlement;
    private Double partnerShareEntitlement;
    private Double lifting;
    private Double partnerLifting;
    private Integer cargos;
    private Integer partnerCargos;
    private Double availability;
    private Double partnerAvailability;
    private Double stockAdjustment;
    private Double overlift;
    private Double partnerOverlift;
    private Double bswLoss;
    private Double meteringInacuracyLoss;
    private Double theftLoss;
    private Double terminalAdjustment;
    private Double productionAdjustment;
    private Double unitization;
    private Double operatorDeclaredVolume;
    private Double operatorDeclaredOwnAvailability;
    private Double operatorDeclaredPartnerAvailability;

    public JvProductionDetail() {
    }

    @NotNull
    public Double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Double openingStock) {
        this.openingStock = openingStock;
    }

    public Double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(Double closingStock) {
        this.closingStock = closingStock;
    }

    @NotNull
    public Double getOwnShareEntitlement() {
        return ownShareEntitlement;
    }

    public void setOwnShareEntitlement(Double ownShareEntitlement) {
        this.ownShareEntitlement = ownShareEntitlement;
    }

    public Double getPartnerShareEntitlement() {
        return partnerShareEntitlement;
    }

    public void setPartnerShareEntitlement(Double partnerShareEntitlement) {
        this.partnerShareEntitlement = partnerShareEntitlement;
    }

    public Double getGrossProduction() {
        return grossProduction;
    }

    public void setGrossProduction(Double grossProduction) {
        this.grossProduction = grossProduction;
    }

    public Double getLifting() {
        return lifting;
    }

    public void setLifting(Double lifting) {
        this.lifting = lifting;
    }

    public Integer getCargos() {
        return cargos;
    }

    public void setCargos(Integer cargos) {
        this.cargos = cargos;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    public Double getPartnerOpeningStock() {
        return partnerOpeningStock;
    }

    public void setPartnerOpeningStock(Double partnerOpeningStock) {
        this.partnerOpeningStock = partnerOpeningStock;
    }

    public Double getPartnerClosingStock() {
        return partnerClosingStock;
    }

    public void setPartnerClosingStock(Double partnerClosingStock) {
        this.partnerClosingStock = partnerClosingStock;
    }

    public Double getPartnerAvailability() {
        return partnerAvailability;
    }

    public void setPartnerAvailability(Double partnerAvailability) {
        this.partnerAvailability = partnerAvailability;
    }

    public Double getPartnerLifting() {
        return partnerLifting;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

    public Integer getPartnerCargos() {
        return partnerCargos;
    }

    public void setPartnerCargos(Integer partnerCargos) {
        this.partnerCargos = partnerCargos;
    }

    public Double getStockAdjustment() {
        return stockAdjustment;
    }

    public void setStockAdjustment(Double stockAdjustment) {
        this.stockAdjustment = stockAdjustment;
    }

    public Double getOverlift() {
        return overlift;
    }

    public void setOverlift(Double overlift) {
        this.overlift = overlift;
    }

    public Double getPartnerOverlift() {
        return partnerOverlift;
    }

    public void setPartnerOverlift(Double partnerOverlift) {
        this.partnerOverlift = partnerOverlift;
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

    @Transient
    public Double getTotalAvailability() {
        return availability + partnerAvailability;
    }

    @Transient
    public Double getOwnEquityRatio() {
        if (operatorDeclaredVolume == null) {
            return null;
        }

        return availability / getTotalAvailability();

    }

    @Transient
    public Double getPartnerEquityRatio() {
        if (operatorDeclaredVolume == null) {
            return null;
        }

        return partnerAvailability / getTotalAvailability();
    }

    public Double getOperatorDeclaredOwnAvailability() {
        return operatorDeclaredOwnAvailability;
    }

    public void setOperatorDeclaredOwnAvailability(Double operatorDeclaredOwnAvailability) {
        this.operatorDeclaredOwnAvailability = operatorDeclaredOwnAvailability;
    }

    public Double getOperatorDeclaredPartnerAvailability() {
        return operatorDeclaredPartnerAvailability;
    }

    public void setOperatorDeclaredPartnerAvailability(Double operatorDeclaredPartnerAvailability) {
        this.operatorDeclaredPartnerAvailability = operatorDeclaredPartnerAvailability;
    }

}

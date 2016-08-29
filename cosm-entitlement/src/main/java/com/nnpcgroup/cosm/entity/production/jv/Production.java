/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "PRODUCTION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PTYPE")
public abstract class Production implements Serializable {

    private static final long serialVersionUID = -795843614381155072L;

    private ProductionPK productionPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
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

    public Production() {
    }

//    public Production(int periodYear, int periodMonth, Contract contract) {
//        this.periodYear = periodYear;
//        this.periodMonth = periodMonth;
//        this.contract = contract;
//    }
    @EmbeddedId
    public ProductionPK getProductionPK() {
        return productionPK;
    }

    public void setProductionPK(ProductionPK productionPK) {
        this.productionPK = productionPK;
    }

    @Column(updatable = false, insertable = false)
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Column(updatable = false, insertable = false)
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

//    @ManyToOne
//    @JoinColumns({
//        @JoinColumn(name = "FISCALARRANGEMENTID", referencedColumnName = "FISCALARRANGEMENTID", insertable = false, updatable = false),
//        @JoinColumn(name = "CRUDETYPECODE", referencedColumnName = "CRUDETYPECODE", insertable = false, updatable = false)
//    })
    @ManyToOne
    @MapsId("contract")
    @JoinColumns({
        @JoinColumn(name = "FISCALARRANGEMENTID", referencedColumnName = "FISCALARRANGEMENTID", insertable = false, updatable = false),
        @JoinColumn(name = "CRUDETYPECODE", referencedColumnName = "CRUDETYPECODE", insertable = false, updatable = false)
    })
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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

        netProduction = gp + sa - bsw - metering - theft - termAdj + prodAdj + unit;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.productionPK);
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

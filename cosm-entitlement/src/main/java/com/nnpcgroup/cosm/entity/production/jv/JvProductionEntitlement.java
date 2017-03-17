/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;

/**
 * @author 18359
 */
@Entity(name = "JV_PRODUCTION_ENTITLEMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
@DiscriminatorValue("JV")
public class JvProductionEntitlement extends ProductionEntitlement {

    private static final long serialVersionUID = -2958434381155072L;

    private JvProductionEntitlementPK productionEntitlementPK;
    private Double openingStock;
    private Double partnerOpeningStock;
    private Double closingStock;
    private Double partnerClosingStock;
    private Double ownShareEntitlement;
    private Double partnerShareEntitlement;
    private Double lifting;
    private Double partnerLifting;
    private Integer cargoes;
    private Integer partnerCargoes;
    private Double availability;
    private Double partnerAvailability;
    private Double overlift;
    private Double partnerOverlift;
    private Double operatorDeclaredOwnAvailability;
    private Double operatorDeclaredPartnerAvailability;
    private Double operatorDeclaredVolume;

    private Contract contract;
    private JvProduction production;

    public JvProductionEntitlement() {
    }

    @EmbeddedId
    public JvProductionEntitlementPK getProductionEntitlementPK() {
        return productionEntitlementPK;
    }

    public void setProductionEntitlementPK(JvProductionEntitlementPK productionEntitlementPK) {
        this.productionEntitlementPK = productionEntitlementPK;
    }

    @NotNull
    @Column(name = "OPENING_STOCK")
    public Double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Double openingStock) {
        this.openingStock = openingStock;
    }

    @Column(name = "CLOSING_STOCK")
    public Double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(Double closingStock) {
        this.closingStock = closingStock;
    }

    @NotNull
    @Column(name = "OWN_SHARE_ENTITLEMENT")
    public Double getOwnShareEntitlement() {
        return ownShareEntitlement;
    }

    public void setOwnShareEntitlement(Double ownShareEntitlement) {
        this.ownShareEntitlement = ownShareEntitlement;
    }

    @Column(name = "PARTNER_SHARE_ENTITLEMENT")
    public Double getPartnerShareEntitlement() {
        return partnerShareEntitlement;
    }

    public void setPartnerShareEntitlement(Double partnerShareEntitlement) {
        this.partnerShareEntitlement = partnerShareEntitlement;
    }

    @Column(name = "LIFTED_VOLUME")
    public Double getLifting() {
        return lifting;
    }

    public void setLifting(Double lifting) {
        this.lifting = lifting;
    }

    @Column(name = "LIFTED_CARGOES")
    public Integer getCargoes() {
        return cargoes;
    }

    public void setCargoes(Integer cargoes) {
        this.cargoes = cargoes;
    }

    @Column(name = "AVAILABILITY")
    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    @Column(name = "PARTNER_OPENING_STOCK")
    public Double getPartnerOpeningStock() {
        return partnerOpeningStock;
    }

    public void setPartnerOpeningStock(Double partnerOpeningStock) {
        this.partnerOpeningStock = partnerOpeningStock;
    }

    @Column(name = "PARTNER_CLOSING_STOCK")
    public Double getPartnerClosingStock() {
        return partnerClosingStock;
    }

    public void setPartnerClosingStock(Double partnerClosingStock) {
        this.partnerClosingStock = partnerClosingStock;
    }

    @Column(name = "PARTNER_AVAILABILITY")
    public Double getPartnerAvailability() {
        return partnerAvailability;
    }

    public void setPartnerAvailability(Double partnerAvailability) {
        this.partnerAvailability = partnerAvailability;
    }

    @Column(name = "PARTNER_LIFTED_VOLUME")
    public Double getPartnerLifting() {
        return partnerLifting;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

    @Column(name = "PARTNER_LIFTED_CARGOES")
    public Integer getPartnerCargoes() {
        return partnerCargoes;
    }

    public void setPartnerCargoes(Integer partnerCargoes) {
        this.partnerCargoes = partnerCargoes;
    }

    @Column(name = "OVERLIFTED_VOLUME")
    public Double getOverlift() {
        return overlift;
    }

    public void setOverlift(Double overlift) {
        this.overlift = overlift;
    }

    @Column(name = "PARTNER_OVERLIFTED_VOLUME")
    public Double getPartnerOverlift() {
        return partnerOverlift;
    }

    public void setPartnerOverlift(Double partnerOverlift) {
        this.partnerOverlift = partnerOverlift;
    }

    @Transient
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

//    @Override
//    public void duplicate(ForecastEntitlement entitlement) {
//        super.duplicate(entitlement);
//        JvForecastEntitlement jvEntitlement = (JvForecastEntitlement) entitlement;
//
//        this.grossProduction = jvEntitlement.getGrossProduction();
//
////         this.openingStock = jvDetail.getOpeningStock();
////        this.partnerOpeningStock = jvDetail.getPartnerOpeningStock();
////        this.closingStock = jvDetail.getClosingStock();
////        this.partnerClosingStock = jvDetail.getPartnerClosingStock();
////        this.grossProduction = jvDetail.getGrossProduction();
////        this.lifting = jvDetail.getLifting();
////        this.partnerLifting = jvDetail.getPartnerLifting();
////        this.cargos = jvDetail.getCargos();
////        this.partnerCargos = jvDetail.getPartnerCargos();
//    }
    @ManyToOne
//    @MapsId("contract")
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
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", updatable = false, insertable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", updatable = false, insertable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public JvProduction getProduction() {
        return production;
    }

    public void setProduction(JvProduction production) {
        this.production = production;
    }
    
    

}

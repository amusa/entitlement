/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.domain.model;

import com.cosm.common.domain.util.AuditInfo;
import com.cosm.common.domain.util.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 18359
 */
@Entity(name = "PRODUCTION_ENTITLEMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
@DiscriminatorValue("JV")
public class ProductionEntitlement implements Auditable, Serializable {

    private static final long serialVersionUID = -115843614381155072L;

    private ProductionEntitlementPK productionEntitlementPK;
    protected Integer periodYear;
    protected Integer periodMonth;
    private Long jvId;
    private Long contractId;
    private String crudeTypeCode;
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


    private Production production;
    protected AuditInfo auditInfo = new AuditInfo();

    public ProductionEntitlement() {
    }

    @EmbeddedId
    public ProductionEntitlementPK getProductionEntitlementPK() {
        return productionEntitlementPK;
    }

    public void setProductionEntitlementPK(ProductionEntitlementPK productionEntitlementPK) {
        this.productionEntitlementPK = productionEntitlementPK;
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


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", updatable = false, insertable = false),
            @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", updatable = false, insertable = false),
            @JoinColumn(name = "JV_ID", referencedColumnName = "JV_ID", insertable = false, updatable = false)
    })
    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }



//    public void duplicate(ForecastDetail forecastDetail) {
//        // this.setContract(forecastDetail.getContract());
//        this.setPeriodYear(forecastDetail.getPeriodYear());
//        this.setPeriodMonth(forecastDetail.getPeriodMonth());
////        this.setProductionDetailPK(forecastDetail.makeProductionDetailPK());
//    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductionEntitlement that = (ProductionEntitlement) o;

        return productionEntitlementPK.equals(that.productionEntitlementPK);
    }

    @Override
    public int hashCode() {
        return productionEntitlementPK.hashCode();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionDetailPK;
import com.nnpcgroup.cosm.entity.production.jv.ProductionDetailPK;
import com.nnpcgroup.cosm.entity.production.jv.ProductionPK;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity(name = "JV_FORECAST_ENTITLEMENT")
//@Table(uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"PERIOD_YEAR", "PERIOD_MONTH", "FISCALARRANGEMENT_ID", "CONTRACT_ID", "CONTRACT_FISCAL_ID", "CRUDETYPE_CODE"})
//})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
@DiscriminatorValue("JV")
public class JvForecastEntitlement extends ForecastEntitlement implements Serializable {

    private static final long serialVersionUID = 2917191116735019064L;

    private JvForecastEntitlementPK entitlementPK;
    private Double openingStock;
    private Double partnerOpeningStock;
    private Double closingStock;
    private Double partnerClosingStock;
    private Double ownShareEntitlement;
    private Double partnerShareEntitlement;
    private Double lifting;
    private Double partnerLifting;
    private Integer cargos;
    private Integer partnerCargos;
    private Double availability;
    private Double partnerAvailability;
    private Contract contract;
    private JvForecast forecast;

    public JvForecastEntitlement() {
    }

    @EmbeddedId
    public JvForecastEntitlementPK getEntitlementPK() {
        return entitlementPK;
    }

    public void setEntitlementPK(JvForecastEntitlementPK entitlementPK) {
        this.entitlementPK = entitlementPK;
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

    @Column(name = "LIFTING")
    public Double getLifting() {
        return lifting;
    }

    public void setLifting(Double lifting) {
        this.lifting = lifting;
    }

    @Column(name = "CARGOES")
    public Integer getCargos() {
        return cargos;
    }

    public void setCargos(Integer cargos) {
        this.cargos = cargos;
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

    @Column(name = "PARTNER_LIFTING")
    public Double getPartnerLifting() {
        return partnerLifting;
    }

    public void setPartnerLifting(Double partnerLifting) {
        this.partnerLifting = partnerLifting;
    }

    @Column(name = "PARTNER_CARGOES")
    public Integer getPartnerCargos() {
        return partnerCargos;
    }

    public void setPartnerCargos(Integer partnerCargos) {
        this.partnerCargos = partnerCargos;
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
    public JvForecast getForecast() {
        return forecast;
    }

//    @ManyToOne
//    @JoinColumn(name = "FORECAST_ID", referencedColumnName = "ID")
//    public JvForecast getForecast() {
//        return forecast;
//    }
    public void setForecast(JvForecast forecast) {
        this.forecast = forecast;
    }

    public ProductionDetailPK makeProductionDetailPK() {
        ProductionDetailPK pPK = new JvProductionDetailPK(
                new ProductionPK(this.getPeriodYear(), this.getPeriodMonth(), this.getForecast().getFiscalArrangement().getId()),
                this.getContract().getContractPK()
        );

        return pPK;
    }

}

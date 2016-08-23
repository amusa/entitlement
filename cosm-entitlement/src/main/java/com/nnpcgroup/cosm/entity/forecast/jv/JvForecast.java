/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV")
public class JvForecast extends Forecast {

    private static final long serialVersionUID = 2917192116735019964L;

    private Double openingStock;
    private Double partnerOpeningStock;
    private Double closingStock;
    private Double partnerClosingStock;
    private Double grossProduction;
    private Double productionVolume;
    private Double ownShareEntitlement;
    private Double partnerShareEntitlement;
    private Double lifting;
    private Double partnerLifting;
    private Integer cargos;
    private Integer partnerCargos;
    private Double availability;
    private Double partnerAvailability;
    private List<ForecastEntitlement> forecastEntitlements;


    public JvForecast() {
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
    @Column(name = "PRODUCTION_VOLUME")
    public Double getProductionVolume() {
        return productionVolume;
    }

    public void setProductionVolume(Double productionVolume) {
        this.productionVolume = productionVolume;
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

    @Column(name = "GROSS_PRODUCTION")
    public Double getGrossProduction() {
        return grossProduction;
    }

    public void setGrossProduction(Double grossProduction) {
        this.grossProduction = grossProduction;
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

    @OneToMany(mappedBy = "forecast", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    public List<ForecastEntitlement> getForecastEntitlements() {
        return forecastEntitlements;
    }

    public void setForecastEntitlements(List<ForecastEntitlement> forecastEntitlements) {
        this.forecastEntitlements = forecastEntitlements;
    }


}
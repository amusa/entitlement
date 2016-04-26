/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "FORECAST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE")
public abstract class Forecast implements Serializable {

    private static final long serialVersionUID = -795843614381155072L;

    private ForecastPK forecastPK;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
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

    public Forecast() {
    }

    @EmbeddedId
    public ForecastPK getForecastPK() {
        return forecastPK;
    }

    public void setForecastPK(ForecastPK forecastPK) {
        this.forecastPK = forecastPK;
    }

    @NotNull
    @Column(insertable = false, updatable = false)
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @NotNull
    @Column(insertable = false, updatable = false)
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @ManyToOne
    @NotNull
    @MapsId("contractPK")
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
    public Double getProductionVolume() {
        return productionVolume;
    }

    public void setProductionVolume(Double productionVolume) {
        this.productionVolume = productionVolume;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.forecastPK);
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
            LOG.log(Level.INFO, "{0} != {1}", new Object[]{getClass(), obj.getClass()});
            return false;
        }
        final Forecast other = (Forecast) obj;
        if (!Objects.equals(this.forecastPK, other.forecastPK)) {
            LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.forecastPK, other.forecastPK});
            return false;
        }
        LOG.log(Level.INFO, "{0} = {1}", new Object[]{this.forecastPK, other.forecastPK});
        return true;
    }
    private static final Logger LOG = Logger.getLogger(Forecast.class.getName());

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.Company;
import com.nnpcgroup.cosm.entity.contract.Contract;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author 18359
 */
@Entity
@Table(name = "FORECAST_ENTITLEMENT")
@IdClass(value = ForecastEntitlementPK.class)
public class ForecastEntitlement implements Serializable {

    private static final long serialVersionUID = -795843614381155072L;

    private static final Logger LOG = Logger.getLogger(ForecastEntitlement.class.getName());

    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
    private Company company;
    private Double openingStock;
    private Double closingStock;
    private Double equityEntitlement;
    private Double liftableVolume;
    private Integer liftableCargos;
    private Double availability;
    private Forecast forecast;

    public ForecastEntitlement() {
    }

    @Id
    public Integer getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    @Id
    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    @Id
    @OneToOne
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Id
    @OneToOne
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne
    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
        
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

    public Double getEquityEntitlement() {
        return equityEntitlement;
    }

    public void setEquityEntitlement(Double equityEntitlement) {
        this.equityEntitlement = equityEntitlement;
    }

    public Double getLiftableVolume() {
        return liftableVolume;
    }

    public void setLiftableVolume(Double liftableVolume) {
        this.liftableVolume = liftableVolume;
    }

    public Integer getLiftableCargos() {
        return liftableCargos;
    }

    public void setLiftableCargos(Integer liftableCargos) {
        this.liftableCargos = liftableCargos;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.periodYear);
        hash = 17 * hash + Objects.hashCode(this.periodMonth);
        hash = 17 * hash + Objects.hashCode(this.contract);
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
        final ForecastEntitlement other = (ForecastEntitlement) obj;
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            return false;
        }
        if (!Objects.equals(this.periodMonth, other.periodMonth)) {
            return false;
        }
        if (!Objects.equals(this.contract, other.contract)) {
            return false;
        }
        return true;
    }

}

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
//@Embeddable
public class ForecastEntitlementPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(ForecastEntitlementPK.class.getName());
    
    private static final long serialVersionUID = -5632726719147425922L;
    private Integer periodYear;
    private Integer periodMonth;
    private Contract contract;
    private Company company;

    public ForecastEntitlementPK() {
    }

    public ForecastEntitlementPK(Integer periodYear, Integer periodMonth, Contract contract) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.contract = contract;
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public Contract getContract() {
        return contract;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
        
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.periodYear);
        hash = 79 * hash + Objects.hashCode(this.periodMonth);
        hash = 79 * hash + Objects.hashCode(this.contract);
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
        final ForecastEntitlementPK other = (ForecastEntitlementPK) obj;
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.periodYear, other.periodYear});
            return false;
        }
        if (!Objects.equals(this.periodMonth, other.periodMonth)) {
            return false;
        }
        if (!Objects.equals(this.contract, other.contract)) {
            LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.contract, other.contract});
            return false;
        }
        LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.contract, other.contract});
        return true;
    }
}

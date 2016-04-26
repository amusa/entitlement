/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import com.nnpcgroup.cosm.entity.contract.ContractPK;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
@Embeddable
public class ForecastPK implements Serializable {

    private static final long serialVersionUID = -5632726719147425922L;
    private Integer periodYear;
    private Integer periodMonth;
    private ContractPK contractPK;

    public ForecastPK() {
    }

    public ForecastPK(Integer periodYear, Integer periodMonth, ContractPK contractPK) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.contractPK = contractPK;
    }

    public Integer getPeriodYear() {
        return periodYear;
    }

    public Integer getPeriodMonth() {
        return periodMonth;
    }

    public ContractPK getContractPK() {
        return contractPK;
    }

    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }

    public void setPeriodMonth(Integer periodMonth) {
        this.periodMonth = periodMonth;
    }

    public void setContractPK(ContractPK contractPK) {
        this.contractPK = contractPK;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.periodYear);
        hash = 79 * hash + Objects.hashCode(this.periodMonth);
        hash = 79 * hash + Objects.hashCode(this.contractPK);
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
        final ForecastPK other = (ForecastPK) obj;
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.periodYear, other.periodYear});
            return false;
        }
        if (!Objects.equals(this.periodMonth, other.periodMonth)) {
            return false;
        }
        if (!Objects.equals(this.contractPK, other.contractPK)) {
            LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.contractPK, other.contractPK});
            return false;
        }
        LOG.log(Level.INFO, "{0} != {1}", new Object[]{this.contractPK, other.contractPK});
        return true;
    }
    private static final Logger LOG = Logger.getLogger(ForecastPK.class.getName());

}
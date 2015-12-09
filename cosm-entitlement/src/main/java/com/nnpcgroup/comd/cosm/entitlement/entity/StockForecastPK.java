/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
@Embeddable
public class StockForecastPK implements Serializable {

    private static final long serialVersionUID = 4206398753671853122L;

    private int periodYear;
    private int periodMonth;
    private int partnershipId;

    public StockForecastPK() {
    }

    public StockForecastPK(int periodYear, int periodMonth, int partnershipId) {
        this.periodYear = periodYear;
        this.periodMonth = periodMonth;
        this.partnershipId = partnershipId;
    }

    public int getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    public int getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(int partnershipId) {
        this.partnershipId = partnershipId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) periodYear;
        hash += (int) periodMonth;
        hash += (int) partnershipId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockForecastPK)) {
            return false;
        }
        StockForecastPK other = (StockForecastPK) object;
        if (this.periodYear != other.periodYear) {
            return false;
        }
        if (this.periodMonth != other.periodMonth) {
            return false;
        }
        if (this.partnershipId != other.partnershipId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nnpcgroup.comd.cosm.entitlement.entity.StockForecastPK[ periodYear=" + periodYear + ", periodMonth=" + periodMonth + ", partnershipId=" + partnershipId + " ]";
    }
    
}

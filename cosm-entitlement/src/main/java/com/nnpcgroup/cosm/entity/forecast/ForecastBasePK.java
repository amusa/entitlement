/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author 18359
 */
@Embeddable
public class ForecastBasePK implements Serializable {

    private static final Logger LOG = Logger.getLogger(ForecastBasePK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;
    private Integer periodYear;
    private Long fiscalArrangementId;
    private Long oilFieldId;

    public ForecastBasePK() {
    }

    public ForecastBasePK(Integer periodYear, Long fiscalArrangementId, Long oilFieldId) {
        this.periodYear = periodYear;
        this.fiscalArrangementId = fiscalArrangementId;
        this.oilFieldId = oilFieldId;
    }

    @Column(name = "PERIOD_YEAR")
    public Integer getPeriodYear() {
        return periodYear;
    }
    
    public void setPeriodYear(Integer periodYear) {
        this.periodYear = periodYear;
    }
   
    @Column(name = "FISCALARRANGEMENT_ID")
    public Long getFiscalArrangementId() {
        return fiscalArrangementId;
    }

    public void setFiscalArrangementId(Long fiscalArrangementId) {
        this.fiscalArrangementId = fiscalArrangementId;
    }

    @Column(name = "OIL_FIELD_ID")
    public Long getOilFieldId() {
        return oilFieldId;
    }

    public void setOilFieldId(Long oilFieldId) {
        this.oilFieldId = oilFieldId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.periodYear);
        hash = 97 * hash + Objects.hashCode(this.fiscalArrangementId);
        hash = 97 * hash + Objects.hashCode(this.oilFieldId);
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
        final ForecastBasePK other = (ForecastBasePK) obj;
        if (!Objects.equals(this.periodYear, other.periodYear)) {
            return false;
        }
        if (!Objects.equals(this.fiscalArrangementId, other.fiscalArrangementId)) {
            return false;
        }
        if (!Objects.equals(this.oilFieldId, other.oilFieldId)) {
            return false;
        }
        return true;
    }

    
}

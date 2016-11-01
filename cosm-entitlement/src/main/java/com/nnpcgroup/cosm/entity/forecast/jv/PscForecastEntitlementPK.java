/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author 18359
 */
@Embeddable
public class PscForecastEntitlementPK extends ForecastEntitlementPK implements Serializable {

    private static final Logger LOG = Logger.getLogger(PscForecastEntitlementPK.class.getName());
    private static final long serialVersionUID = -5632726719147425922L;

    private Long oilField;

    public PscForecastEntitlementPK() {
    }

    @Column(name = "OIL_FIELD_ID")
    public Long getOilField() {
        return oilField;
    }

    public void setOilField(Long oilFieldId) {
        this.oilField = oilFieldId;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + Objects.hashCode(this.oilField);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        super.equals(obj);

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PscForecastEntitlementPK other = (PscForecastEntitlementPK) obj;
        if (!Objects.equals(this.oilField, other.oilField)) {
            return false;
        }
        return true;
    }

}

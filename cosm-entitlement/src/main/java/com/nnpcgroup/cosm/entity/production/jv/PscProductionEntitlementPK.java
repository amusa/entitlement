/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Column;

/**
 *
 * @author 18359
 */
@Embeddable
public class PscProductionEntitlementPK extends ProductionEntitlementPK {

    private static final long serialVersionUID = 2983325339937581443L;

    private Long oilField;

    public PscProductionEntitlementPK() {
    }

    public PscProductionEntitlementPK(ProductionPK productionPK, Long oilField) {
        super(productionPK);
        this.oilField = oilField;
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
        hash = 11 * hash + Objects.hashCode(this.oilField);
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
        final PscProductionEntitlementPK other = (PscProductionEntitlementPK) obj;
        if (!Objects.equals(this.oilField, other.oilField)) {
            return false;
        }
        return true;
    }

}

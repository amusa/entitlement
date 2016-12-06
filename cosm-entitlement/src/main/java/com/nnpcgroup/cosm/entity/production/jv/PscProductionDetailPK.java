/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import javax.persistence.Embeddable;

/**
 *
 * @author 18359
 */
@Embeddable
public class PscProductionDetailPK extends ProductionDetailPK {

    private static final long serialVersionUID = 2983325339937581443L;

    private Long oilField;

    public PscProductionDetailPK() {
    }

    public PscProductionDetailPK(ProductionPK productionPK, Long oilField) {
        super(productionPK);
        this.oilField = oilField;
    }

    public Long getOilField() {
        return oilField;
    }

    public void setOilField(Long oilField) {
        this.oilField = oilField;
    }

}

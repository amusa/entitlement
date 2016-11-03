/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.contract.OilField;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 * @author 18359
 */
@Entity(name = "PSC_PRODUCTION_DETAIL")
public class PscProductionDetail extends ProductionDetail {

    private static final long serialVersionUID = -2958434381155072L;

    private PscProductionDetailPK productionDetailPK;
    private PscProduction production;
    private OilField oilField;

    public PscProductionDetail() {
    }

    @EmbeddedId
    public PscProductionDetailPK getProductionDetailPK() {
        return productionDetailPK;
    }

    public void setProductionDetailPK(PscProductionDetailPK productionDetailPK) {
        this.productionDetailPK = productionDetailPK;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PERIOD_YEAR", referencedColumnName = "PERIOD_YEAR", updatable = false, insertable = false),
        @JoinColumn(name = "PERIOD_MONTH", referencedColumnName = "PERIOD_MONTH", updatable = false, insertable = false),
        @JoinColumn(name = "FISCALARRANGEMENT_ID", referencedColumnName = "FISCALARRANGEMENT_ID", insertable = false, updatable = false)
    })
    public PscProduction getProduction() {
        return production;
    }

    public void setProduction(PscProduction production) {
        this.production = production;
    }

    @ManyToOne
    @MapsId("oilField")
    @JoinColumn(name = "OIL_FIELD_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    public OilField getOilField() {
        return oilField;
    }

    public void setOilField(OilField oilField) {
        this.oilField = oilField;
    }

}

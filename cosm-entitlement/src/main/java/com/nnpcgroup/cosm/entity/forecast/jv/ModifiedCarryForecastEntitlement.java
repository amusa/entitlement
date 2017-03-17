/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author 18359
 */
@Entity(name = "MCA_FORECAST_ENTITLEMENT")
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"PERIOD_YEAR", "PERIOD_MONTH", "FISCALARRANGEMENT_ID", "CONTRACT_ID", "CONTRACT_FISCAL_ID", "CRUDETYPE_CODE"})
})
@DiscriminatorValue("MCA")
public class ModifiedCarryForecastEntitlement extends AlternativeFundingForecastEntitlement {

    private static final long serialVersionUID = 4881837273578907336L;

    private Double nonDrillingCapex;

    public ModifiedCarryForecastEntitlement() {
    }

    public Double getNonDrillingCapex() {
        return nonDrillingCapex;
    }

    public void setNonDrillingCapex(Double nonDrillingCapex) {
        this.nonDrillingCapex = nonDrillingCapex;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author 18359
 */
@Entity
//@Table(uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"PERIOD_YEAR", "PERIOD_MONTH", "FISCALARRANGEMENT_ID", "CONTRACT_ID", "CONTRACT_FISCAL_ID", "CRUDETYPE_CODE"})
//})
@DiscriminatorValue("MCA")
public class ModifiedCarryForecastDetail extends AlternativeFundingForecastDetail {

    private static final long serialVersionUID = 4881837273578907336L;

    private Double nonDrillingCapex;

    public ModifiedCarryForecastDetail() {
    }

    @Column(name = "NON_DRILLING_CAPEX")
    public Double getNonDrillingCapex() {
        return nonDrillingCapex;
    }

    public void setNonDrillingCapex(Double nonDrillingCapex) {
        this.nonDrillingCapex = nonDrillingCapex;
    }

}

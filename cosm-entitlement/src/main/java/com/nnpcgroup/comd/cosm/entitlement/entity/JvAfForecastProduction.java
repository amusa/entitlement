/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("JV_AF_FORECAST")
public class JvAfForecastProduction extends JvForecastProduction {

    private static final long serialVersionUID = 2917192116735019964L;

    private Double tangibleCost;
    private Double intangibleCost;

    public JvAfForecastProduction() {
    }

    public Double getTangibleCost() {
        return tangibleCost;
    }

    public void setTangibleCost(Double tangibleCost) {
        this.tangibleCost = tangibleCost;
    }

    public Double getIntangibleCost() {
        return intangibleCost;
    }

    public void setIntangibleCost(Double intangibleCost) {
        this.intangibleCost = intangibleCost;
    }

}

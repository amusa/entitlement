/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("AF")
public class AlternativeFundingProduction extends Production {

    private static final long serialVersionUID = 4881837273578907336L;

    private Double tangibleCost;
    private Double intangibleCost;

    public AlternativeFundingProduction() {
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

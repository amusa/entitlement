/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.production.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("CA")
public class CarryProductionDetail extends AlternativeFundingProductionDetail {

    private static final long serialVersionUID = 4881837273578907336L;

//    @Override
//    public void duplicate(ForecastDetail forecastDetail) {
//        super.duplicate(forecastDetail);
//
//    }
}

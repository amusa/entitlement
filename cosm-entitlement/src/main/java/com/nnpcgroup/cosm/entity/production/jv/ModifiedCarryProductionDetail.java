/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.production.jv;

import com.nnpcgroup.cosm.entity.forecast.jv.ForecastDetail;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("MCA")
public class ModifiedCarryProductionDetail extends AlternativeFundingProductionDetail {

    private static final long serialVersionUID = 4881837273578907336L;

    @Override
    public void duplicate(ForecastDetail forecastDetail) {
        super.duplicate(forecastDetail);

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.forecast.jv;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author 18359
 */
@Entity
@DiscriminatorValue("MCA")
public class ModifiedCarryForecast extends AlternativeFundingForecast{

    private static final long serialVersionUID = 4881837273578907336L;

    public ModifiedCarryForecast() {
    }
    
            
}

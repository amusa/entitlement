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
    
    public JvAfForecastProduction() {
    }
        
   
}

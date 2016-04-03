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
@DiscriminatorValue("JV_MCA_FORECAST")
public class JvMcaForecastProduction extends JvAfForecastProduction {

    private static final long serialVersionUID = 4881837273578907336L;
        
}

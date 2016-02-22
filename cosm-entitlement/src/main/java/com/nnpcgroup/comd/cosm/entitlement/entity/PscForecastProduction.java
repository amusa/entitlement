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
@DiscriminatorValue("PSC_FORECAST")
public class PscForecastProduction extends PscProduction {

    private static final long serialVersionUID = -5206065666784730417L;

    public PscForecastProduction() {
    }

}

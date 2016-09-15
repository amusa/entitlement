/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.production.jv.ProductionDetail;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author 18359
 */

@MappedSuperclass
public abstract class PscProduction extends ProductionDetail {

    private static final long serialVersionUID = 4881837273578907336L;

   
    public PscProduction() {
    }

    
}

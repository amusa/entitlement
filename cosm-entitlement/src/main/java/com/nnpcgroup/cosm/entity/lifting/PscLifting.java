/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity.lifting;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ayemi
 */
@Entity
@DiscriminatorValue("PSC")
public class PscLifting extends Lifting {

    private ProductionSharingContract psc;

    public PscLifting() {
    }

    @ManyToOne
    @JoinColumn(name = "PSC_ID", referencedColumnName = "ID")
    public ProductionSharingContract getPsc() {
        return psc;
    }

    public void setPsc(ProductionSharingContract psc) {
        this.psc = psc;
    }

}

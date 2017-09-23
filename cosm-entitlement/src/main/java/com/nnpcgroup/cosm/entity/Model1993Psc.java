/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import javax.persistence.*;

/**
 * @author 18359
 */
@Entity
@Table
@DiscriminatorValue("1993_PSC")
public class Model1993Psc extends ProductionSharingContract {

    public Model1993Psc() {

    }

    public Model1993Psc(Long id) {
        super(id);
    }

    @Transient
    public Double getRoyaltyRate() {
        if (getTerrain() != null) {
            if (getTerrain().equalsIgnoreCase("OFFSHORE")) {
                if (getWaterDepth() != null) {
                    if (getWaterDepth() > 0 && getWaterDepth() <= 200.0) {
                        return 16.67;
                    } else if (getWaterDepth() > 200.0 && getWaterDepth() <= 500.00) {
                        return 12.0;
                    } else if (getWaterDepth() > 500.0 && getWaterDepth() <= 800.00) {
                        return 8.0;
                    } else if (getWaterDepth() > 800.0 && getWaterDepth() <= 1000.00) {
                        return 4.0;
                    } else if (getWaterDepth() > 1000) {
                        return 0.0;
                    }
                }
            } else if (getTerrain().equalsIgnoreCase("ONSHORE")) {
                return 20.0;
            } else if (getTerrain().equalsIgnoreCase("INLAND BASIN")) {
                return 10.0;
            }
        }
        return null;
    }

}

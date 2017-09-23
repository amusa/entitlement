/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

import com.nnpcgroup.cosm.entity.crude.CrudeType;
import com.nnpcgroup.cosm.util.DateUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 18359
 */
@Entity
@Table
@DiscriminatorValue("2005_PSC")
public class Model2005Psc extends ProductionSharingContract {

    private Double costRecoveryLimit;
    private Double costUplift;

    public Model2005Psc() {

    }

    public Model2005Psc(Long id) {
        super(id);
    }


    @Column(name = "COST_RECOVERY_LIMIT")
    public Double getCostRecoveryLimit() {
        return costRecoveryLimit;
    }

    public void setCostRecoveryLimit(Double costRecoveryLimit) {
        this.costRecoveryLimit = costRecoveryLimit;
    }

    @Column(name = "COST_UPLIFT")
    public Double getCostUplift() {
        return costUplift;
    }

    public void setCostUplift(Double costUplift) {
        this.costUplift = costUplift;
    }

    //    @Column(name = "ROYALTY_RATE")
    @Transient
    public Double getRoyaltyRate() {
        if (getTerrain() != null) {
            if (getTerrain().equalsIgnoreCase("OFFSHORE")) {
                if (getWaterDepth() != null) {
                    if (getWaterDepth() <= 100.0) {
                        return 18.5;
                    } else if (getWaterDepth() > 100.0 & getWaterDepth() <= 200.0) {
                        return 16.5;
                    } else if (getWaterDepth() > 200.0 & getWaterDepth() <= 500.00) {
                        return 12.0;
                    } else if (getWaterDepth() > 500.0) {
                        return 8.0;
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

package com.nnpcgroup.cosm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Created by maliska on 8/24/16.
 */
@Embeddable
public class TaxAndAllowance implements Serializable {

    private Double capitalAllowanceRate;

    @NotNull
    @Column(name = "CAPITAL_ALLOWANCE_RATE")
    public Double getCapitalAllowanceRate() {
        return capitalAllowanceRate;
    }

    public void setCapitalAllowanceRate(Double capitalAllowanceRate) {
        this.capitalAllowanceRate = capitalAllowanceRate;
    }

    @Transient
    public Double getEducationTax() {
        return 2.0;
    }
}

package com.cosm.psc.psc.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by maliska on 8/24/16.
 */
@Embeddable
public class CapitalAllowanceRate implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double capitalAllowanceRate;

    @NotNull
    @Column(name = "CAPITAL_ALLOWANCE_RATE")
    public Double getCapitalAllowanceRate() {
        return capitalAllowanceRate;
    }

    public void setCapitalAllowanceRate(Double capitalAllowanceRate) {
        this.capitalAllowanceRate = capitalAllowanceRate;
    }    
}

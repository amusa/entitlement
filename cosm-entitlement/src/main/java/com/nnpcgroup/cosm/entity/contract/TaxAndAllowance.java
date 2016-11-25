package com.nnpcgroup.cosm.entity.contract;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by maliska on 8/24/16.
 */
@Embeddable
public class TaxAndAllowance implements Serializable {

//    private Double investmentTaxAllowance;
    private Double capitalAllowanceRate;
    private Double educationTax;

//    @Column(name = "INVESTMENT_TAX_ALLOWANCE")
//    public Double getInvestmentTaxAllowance() {
//        return investmentTaxAllowance;
//    }
//
//    public void setInvestmentTaxAllowance(Double investmentTaxAllowance) {
//        this.investmentTaxAllowance = investmentTaxAllowance;
//    }
    @NotNull
    @Column(name = "CAPITAL_ALLOWANCE_RATE")
    public Double getCapitalAllowanceRate() {
        return capitalAllowanceRate;
    }

    public void setCapitalAllowanceRate(Double capitalAllowanceRate) {
        this.capitalAllowanceRate = capitalAllowanceRate;
    }

    @NotNull
    @Column(name = "EDUCATION_TAX")
    public Double getEducationTax() {
        return educationTax;
    }

    public void setEducationTax(Double educationTax) {
        this.educationTax = educationTax;
    }
}

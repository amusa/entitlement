package com.nnpcgroup.cosm.entity.contract;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by maliska on 8/24/16.
 */
@Embeddable
public class TaxAndAllowance implements Serializable {
    private Double investmentTaxAllowance;
    private Double capitalAllowanceRate;
    private Double petroleumProfitTaxRateAferFiveYrs;
    private Double petroleumProfitTaxRateFirstFiveYrs;
    private Double educationTax;

    @Column(name = "INVESTMENT_TAX_ALLOWANCE")
    public Double getInvestmentTaxAllowance() {
        return investmentTaxAllowance;
    }

    public void setInvestmentTaxAllowance(Double investmentTaxAllowance) {
        this.investmentTaxAllowance = investmentTaxAllowance;
    }

    @Column(name = "CAPITAL_ALLOWANCE_RATE")
    public Double getCapitalAllowanceRate() {
        return capitalAllowanceRate;
    }

    public void setCapitalAllowanceRate(Double capitalAllowanceRate) {
        this.capitalAllowanceRate = capitalAllowanceRate;
    }

    @Column(name = "PETROLEUM_PROFIT_TAX_RATE_AFTER_FIVE_YRS")
    public Double getPetroleumProfitTaxRateAferFiveYrs() {
        return petroleumProfitTaxRateAferFiveYrs;
    }

    public void setPetroleumProfitTaxRateAferFiveYrs(Double petroleumProfitTaxRateAferFiveYrs) {
        this.petroleumProfitTaxRateAferFiveYrs = petroleumProfitTaxRateAferFiveYrs;
    }

    @Column(name = "PETROLEUM_PROFIT_TAX_RATE_FIRST_FIVE_YRS")
    public Double getPetroleumProfitTaxRateFirstFiveYrs() {
        return petroleumProfitTaxRateFirstFiveYrs;
    }

    public void setPetroleumProfitTaxRateFirstFiveYrs(Double petroleumProfitTaxRateFirstFiveYrs) {
        this.petroleumProfitTaxRateFirstFiveYrs = petroleumProfitTaxRateFirstFiveYrs;
    }

    @Column(name = "EDUCATION_TAX")
    public Double getEducationTax() {
        return educationTax;
    }

    public void setEducationTax(Double educationTax) {
        this.educationTax = educationTax;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.domain.model;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import java.util.Optional;

/**
 * @author Ayemi
 */
public class TaxOilCalculator {

    private final FiscalPeriod fiscalPeriod;
    private final ProductionSharingContractId pscId;
    private final double opex;
    private final double currentYearCapex;
    private final double grossIncome;
    private final double corporationProceed;
    private final double royalty;
    private final double currentCapitalAllowance;
    private final double educationTax;
    private final double educationTaxRate;
    private final double investmentTaxAllowanceRate;
    private final double petroleumProfitTaxRate;
    private final double lossBfw;
    private final Optional<TaxOilProjection> priorTaxOilProjection;

    private TaxOilCalculator(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double opex, double currentYearCapex, double grossIncome, double corporationProceed, double royalty,
            double currentCapitalAllowance, double educationTax, double educationTaxRate,
            double investmentTaxAllowanceRate, double petroleumProfitTaxRate, double lossBfw,
            Optional<TaxOilProjection> priorTaxOilProj
    ) {
        super();
        this.fiscalPeriod = fiscalPeriod;
        this.pscId = pscId;
        this.opex = opex;
        this.currentYearCapex = currentYearCapex;
        this.grossIncome = grossIncome;
        this.corporationProceed = corporationProceed;
        this.royalty = royalty;
        this.currentCapitalAllowance = currentCapitalAllowance;
        this.educationTax = educationTax;
        this.educationTaxRate = educationTaxRate;
        this.investmentTaxAllowanceRate = investmentTaxAllowanceRate;
        this.petroleumProfitTaxRate = petroleumProfitTaxRate;
        this.lossBfw = lossBfw;
        this.priorTaxOilProjection = priorTaxOilProj;
    }

    public FiscalPeriod getFiscalPeriod() {
        return fiscalPeriod;
    }

    public ProductionSharingContractId getPscId() {
        return pscId;
    }

    public double getOpex() {
        return opex;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public double getCorporationProceed() {
        return corporationProceed;
    }

    public double getRoyalty() {
        return royalty;
    }

    public double getLossBfw() {
        return lossBfw;
    }

    public double getTotalDeduction() {
        return opex + royalty;
    }

    public double getAdjustedProfit() {
        return Math.max(0, grossIncome - getTotalDeduction());
    }

    public double getAssessableProfit() {
        return getAdjustedProfit() - lossBfw;
    }

    public double getEducationTaxRate() {
        return educationTaxRate;
    }

    public double getEducationTax() {
        if (educationTax != 0) {
            return 0; // avoid double edu tax
        }

        return Math.max(0, (getEducationTaxRate() / 102) * getAssessableProfit());
    }

    public double getAdjustedAssessableProfit() {
        return getAssessableProfit() - getEducationTax();
    }

    public double getInvestmentTaxAllowanceRate() {
        return investmentTaxAllowanceRate;
    }

    public double getCurrentYearCapex() {
        return currentYearCapex;
    }

    public double getCurrentYearITA() {
        return getCurrentYearCapex() * (getInvestmentTaxAllowanceRate() / 100.0);
    }

    public double getAdjustedProfitLessITA() {
        return (0.85 * getAdjustedAssessableProfit() - (1.7 * getCurrentYearITA()));
    }

    public double getCurrentCapitalAllowance() {
        return currentCapitalAllowance;
    }

    public double getTotalAnnualAllowance() {
        return getCurrentYearITA() + getCurrentCapitalAllowance() + getPriorYearAnnualAllowance();
    }

    public double getSection18DeductionLower() {
        return Math.max(0, Math.min(getAdjustedProfitLessITA(), getTotalAnnualAllowance()));
    }

    public double getChargeableProfitToDate() {
        return Math.max(0, getAdjustedAssessableProfit() - getSection18DeductionLower());
    }

    public double getPetroleumProfitTaxRate() {
        return petroleumProfitTaxRate;
    }

    public double getChargeableTaxToDate() {
        return getChargeableProfitToDate() * (getPetroleumProfitTaxRate() / 100);
    }

    public Optional<TaxOilProjection> getPriorTaxOilProjection() {
        return priorTaxOilProjection;
    }

    public double getPriorMinimumTax() {
        //return priorMinimumTax;
        return getPriorTaxOilProjection().isPresent() ? getPriorTaxOilProjection().get().getMinimumTax() : 0;
    }

    public double getPayableTaxToDate() {
        return Math.max(getMonthlyMinimumTax(),
                (getMonthlyMinimumTax() < 0 && getChargeableTaxToDate() < 0) ? 0 : getChargeableTaxToDate());
    }

    public double getUnrecoupedAnnualAllowance() {
        return Math.min(0, getAdjustedAssessableProfit() - getTotalAnnualAllowance());
    }

    public double getPriorYearAnnualAllowance() {
        //return priorYearAnnualAllowance;
        return -1 * (getPriorTaxOilProjection().isPresent() ? getPriorTaxOilProjection().get().getUnrecoupedAnnualAllowance() : 0);
    }

    public double getMonthlyMinimumTax() {
        return getMinimumTax() - getPriorMinimumTax();
    }

    public double getMinimumTax() {
        return getAdjustedAssessableProfit() * 0.15;
    }

    public double getTaxOil() {
        return Math.max(0, getPayableTaxToDate());
    }

    public double getPriorTaxOilToDate() {
        return getPriorTaxOilProjection().isPresent() ? getPriorTaxOilProjection().get().getTaxOilToDate() : 0;
    }

    public double getTaxOilToDate() {
        return getPriorTaxOilToDate() + getTaxOil();
    }

    public TaxOilAllocation getTaxOilAllocation() {
        return new TaxOilAllocation(fiscalPeriod, pscId,
                getPriorTaxOilProjection().isPresent() ? getPriorTaxOilProjection().get().getAllocation().getTaxOilCarriedForward() : 0,
                getTaxOil(),
                getCorporationProceed(),
                getPriorTaxOilProjection().isPresent() ? getPriorTaxOilProjection().get().getTaxOilToDate() : 0,
                getRoyalty()
        );
    }

    public static class Builder {

        private FiscalPeriod newFiscalPeriod;
        private ProductionSharingContractId newPscId;
        private double newRoyalty;
        private double newGrossIncome;
        private double newCorporationProceed;
        private double newLossBfw;
        private double newCurrentCapitalAllowance;
        private double newEducationTax;
        private double newOpex;
        private double newCurrentYearCapex;
        private double newEducationTaxRate;
        private double newInvestmentTaxAllowanceRate;
        private double newPetroleumProfitTaxRate;
        private Optional<TaxOilProjection> newPriorTaxOilProjection;

        public Builder withGrossIncome(double gIncome) {
            this.newGrossIncome = gIncome;
            return this;
        }

        public Builder withOpex(double opex) {
            this.newOpex = opex;
            return this;
        }

        public Builder withRoyalty(double roy) {
            this.newRoyalty = roy;
            return this;
        }

        public Builder withLossBfw(double lbw) {
            this.newLossBfw = lbw;
            return this;
        }

        public Builder withCurrentCapitalAllowance(double cca) {
            this.newCurrentCapitalAllowance = cca;
            return this;
        }

        public Builder withEducationTax(double eduTax) {
            this.newEducationTax = eduTax;
            return this;
        }

        public Builder withCurrentYearCapex(double currYrCapex) {
            this.newCurrentYearCapex = currYrCapex;
            return this;
        }

        public Builder withEducationTaxRate(double eduTaxRate) {
            this.newEducationTaxRate = eduTaxRate;
            return this;
        }

        public Builder withInvestmentTaxAllowanceRate(double itaRate) {
            this.newInvestmentTaxAllowanceRate = itaRate;
            return this;
        }

        public Builder withPetroleumProtitTaxRate(double pptRate) {
            this.newPetroleumProfitTaxRate = pptRate;
            return this;
        }

        public Builder withCorporationProceed(double corpProceed) {
            this.newCorporationProceed = corpProceed;
            return this;
        }

        public Builder withPriorTaxOilProjection(Optional<TaxOilProjection> toProj) {
            this.newPriorTaxOilProjection = toProj;
            return this;
        }

        public Builder withFiscalPeriod(FiscalPeriod fp) {
            this.newFiscalPeriod = fp;
            return this;
        }

        public Builder withContractId(ProductionSharingContractId pscId) {
            this.newPscId = pscId;
            return this;
        }

        public TaxOilCalculator build() {
            return new TaxOilCalculator(newFiscalPeriod, newPscId, newOpex, newCurrentYearCapex, newGrossIncome, newCorporationProceed, newRoyalty, newCurrentCapitalAllowance,
                    newEducationTax, newEducationTaxRate, newInvestmentTaxAllowanceRate, newPetroleumProfitTaxRate,
                    newLossBfw, newPriorTaxOilProjection);
        }
    }
}

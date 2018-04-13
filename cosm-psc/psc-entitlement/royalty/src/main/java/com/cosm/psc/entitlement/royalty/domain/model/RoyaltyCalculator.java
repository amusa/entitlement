/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.royalty.domain.model;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.util.DateUtil;
import java.util.Optional;

/**
 * @author Ayemi
 */
public class RoyaltyCalculator {

    private final FiscalPeriod fiscalPeriod;
    private final ProductionSharingContractId pscId;
    private final double grossProduction;
    private final double weightedAveragePrice;
    private final double proceed;
    private final double cashPayment;
    private final double concessionRental;
    private final Optional<RoyaltyProjection> priorRoyaltyProjection;

    public RoyaltyCalculator(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId, double grossProduction, double weightedAveragePrice, double proceed, double cashPayment, double concessionRental, Optional<RoyaltyProjection> priorRoyaltyProjection) {
        this.fiscalPeriod = fiscalPeriod;
        this.pscId = pscId;
        this.grossProduction = grossProduction;
        this.weightedAveragePrice = weightedAveragePrice;
        this.proceed = proceed;
        this.cashPayment = cashPayment;
        this.concessionRental = concessionRental;
        this.priorRoyaltyProjection = priorRoyaltyProjection;
    }

    public FiscalPeriod getFiscalPeriod() {
        return fiscalPeriod;
    }

    public ProductionSharingContractId getPscId() {
        return pscId;
    }

    public Optional<RoyaltyProjection> getPriorRoyaltyProjection() {
        return priorRoyaltyProjection;
    }

    public double getGrossProduction() {
        return grossProduction;
    }

    public double getWeightedAveragePrice() {
        return weightedAveragePrice;
    }

    public double getProceed() {
        return proceed;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public double getConcessionRental() {
        return concessionRental;
    }

    public RoyaltyAllocation getRoyaltyAllocation() {
        return new RoyaltyAllocation(fiscalPeriod, pscId,
                getPriorRoyaltyProjection().isPresent() ? getPriorRoyaltyProjection().get().getAllocation().getRoyaltyCarriedForward() : 0,
                royalty(),
                getProceed(),
                getPriorRoyaltyProjection().isPresent() ? getPriorRoyaltyProjection().get().getRoyaltyToDate() : 0,
                getCashPayment()
        );
    }

    public double royalty() {
        double royalty, royRate;

        int days = DateUtil.daysOfMonth(fiscalPeriod.getYear(), fiscalPeriod.getMonth());
        Double dailyProd = grossProduction / days;
        royRate = royaltyRate(dailyProd);

        royalty = (grossProduction * (royRate / 100) * weightedAveragePrice) + concessionRental;

        return royalty;
    }

    private double royaltyRate(double dailyProd) {
        if (dailyProd < 2000.0) {
            return 5.0;
        } else if (dailyProd >= 2000.0 && dailyProd < 5000.0) {
            return 7.5;
        } else if (dailyProd >= 5000.0 && dailyProd < 10000.0) {
            return 15.0;
        }
        return 20.0;

    }

    public static class Builder {

        private FiscalPeriod newFiscalPeriod;
        private ProductionSharingContractId newPscId;
        private double newGrossProduction;
        private double newWeightedAveragePrice;
        private double newProceed;
        private double newCashPayment;
        private double newConcessionRental;
        private Optional<RoyaltyProjection> newPriorRoyaltyProjection;

        public Builder withPeriod(FiscalPeriod fp) {
            this.newFiscalPeriod = fp;
            return this;
        }

        public Builder withContractId(ProductionSharingContractId pscId) {
            this.newPscId = pscId;
            return this;
        }

        public Builder withGrossProduction(double gProduction) {
            this.newGrossProduction = gProduction;
            return this;
        }

        public Builder withWeightedAveragePrice(double wap) {
            this.newWeightedAveragePrice = wap;
            return this;
        }

        public Builder withProceed(double proceed) {
            this.newProceed = proceed;
            return this;
        }

        public Builder withCashPayment(double cashPayment) {
            this.newCashPayment = cashPayment;
            return this;
        }

        public Builder withConcessionRental(double concessionRental) {
            this.newConcessionRental = concessionRental;
            return this;
        }

        public Builder withPriorRoyaltyProjection(Optional<RoyaltyProjection> priorRoyProjection) {
            this.newPriorRoyaltyProjection = priorRoyProjection;
            return this;
        }

        public RoyaltyCalculator build() {
            return new RoyaltyCalculator(newFiscalPeriod, newPscId, newGrossProduction,
                    newWeightedAveragePrice, newProceed, newCashPayment, newConcessionRental,
                    newPriorRoyaltyProjection);
        }
    }
}

package com.cosm.common.event;

import javax.json.JsonObject;

public class RoyaltyReady extends CosmEvent {

    // lifting
    private double corporationProceed;
    private double cashPayment;
    private double weightedAveragePrice;

    // production
    private double grossProduction;

    private RoyaltyReady(EventPeriod period, String pscId, double grossProduction, double corporationProceed, double cashPayment,
            double weightedAveragePrice) {

        super(period, pscId);

        this.corporationProceed = corporationProceed;
        this.cashPayment = cashPayment;
        this.weightedAveragePrice = weightedAveragePrice;
        this.grossProduction = grossProduction;

    }

    public RoyaltyReady(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                jsonObject.getJsonNumber("grossProduction").doubleValue(),
                jsonObject.getJsonNumber("corporationProceed").doubleValue(),
                jsonObject.getJsonNumber("cashPayment").doubleValue(),
                jsonObject.getJsonNumber("weightedAveragePrice").doubleValue()
        );
    }

    public double getCorporationProceed() {
        return corporationProceed;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public double getWeightedAveragePrice() {
        return weightedAveragePrice;
    }

    public double getGrossProduction() {
        return grossProduction;
    }
        
    public static class Builder {

        private EventPeriod newPeriod;
        private String newPscId;

        // lifting
        private double newCorporationProceed;
        private double newCashPayment;
        private double newWeightedAveragePrice;

        // production
        private double newGrossProduction;

        public Builder withPeriod(EventPeriod period) {
            this.newPeriod = period;
            return this;
        }

        public Builder withContract(String pscId) {
            this.newPscId = pscId;
            return this;
        }

        public Builder withCorporationProceed(double corpProceed) {
            this.newCorporationProceed = corpProceed;
            return this;
        }

        public Builder withCashPayment(double cashPayment) {
            this.newCashPayment = cashPayment;
            return this;
        }

        public Builder withWeightedAveragePrice(double wap) {
            this.newWeightedAveragePrice = wap;
            return this;
        }

        public Builder withGrossProduction(double aProduction) {
            this.newGrossProduction = aProduction;
            return this;
        }

        public RoyaltyReady build() {
            return new RoyaltyReady(newPeriod, newPscId, newGrossProduction, newCorporationProceed, newCashPayment,
                    newWeightedAveragePrice);
        }
    }

}

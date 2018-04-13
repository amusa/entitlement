package com.cosm.common.event;

import javax.json.JsonObject;

public class TaxOilReady extends CosmEvent {

    // cost
    private double amortizedCapex;
    private double currentYearCapex;
    private double currentYearOpex;
    private double educationTax;

    // lifting
    private double grossIncome;
    private double corporationProceed;

    //royalty
    private double royalty;

    private TaxOilReady(EventPeriod period, String pscId, double amortizedCapex, double currentYearCapex,
            double currentYearOpex, double educationTax, double grossIncome, double corporationProceed, double royalty) {

        super(period, pscId);

        this.amortizedCapex = amortizedCapex;
        this.currentYearCapex = currentYearCapex;
        this.currentYearOpex = currentYearOpex;
        this.educationTax = educationTax;
        this.grossIncome = grossIncome;
        this.corporationProceed = corporationProceed;
        this.royalty = royalty;

    }

    public TaxOilReady(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                jsonObject.getJsonNumber("amortizedCapex").doubleValue(),
                jsonObject.getJsonNumber("currentYearCapex").doubleValue(),
                jsonObject.getJsonNumber("currentYearOpex").doubleValue(),
                jsonObject.getJsonNumber("educationTax").doubleValue(),
                jsonObject.getJsonNumber("grossIncome").doubleValue(),
                jsonObject.getJsonNumber("corporationProceed").doubleValue(),
                jsonObject.getJsonNumber("royalty").doubleValue()
        );
    }

    public double getAmortizedCapex() {
        return amortizedCapex;
    }

    public double getCurrentYearCapex() {
        return currentYearCapex;
    }

    public double getCurrentYearOpex() {
        return currentYearOpex;
    }

    public double getEducationTax() {
        return educationTax;
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

    public static class Builder {

        private EventPeriod newPeriod;
        private String newPscId;

        // cost
        private double newAmortizedCapex;
        private double newCurrentYearCapex;
        private double newCurrentYearOpex;
        private double newEducationTax;

        // lifting
        private double newGrossIncome;
        private double newCorporationProceed;

        //Royalty
        private double newRoyalty;

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

        public Builder withAmortizedCapex(double aCapex) {
            this.newAmortizedCapex = aCapex;
            return this;
        }

        public Builder withCurrentYearCapex(double currYrCapex) {
            this.newCurrentYearCapex = currYrCapex;
            return this;
        }

        public Builder withGrossIncome(double gIncome) {
            this.newGrossIncome = gIncome;
            return this;
        }

        public Builder withCurrentYearOpex(double currYrOpex) {
            this.newCurrentYearOpex = currYrOpex;
            return this;
        }

        public Builder withEducationTax(double edTax) {
            this.newEducationTax = edTax;
            return this;
        }

        public Builder withRoyalty(double roy) {
            this.newRoyalty = roy;
            return this;
        }

        public TaxOilReady build() {
            return new TaxOilReady(newPeriod, newPscId, newAmortizedCapex, newCurrentYearCapex,
                    newCurrentYearOpex, newEducationTax, newGrossIncome, newCorporationProceed, newRoyalty);
        }
    }

}

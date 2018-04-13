package com.cosm.common.event;

import javax.json.JsonObject;

public class TaxOilDue extends CosmEvent {

    private double taxOilMonthlyCharge;
    private double taxOilMonthlyChargeToDate;
    private double taxOilReceived;
    private double educationTax;

    private TaxOilDue(EventPeriod period, String pscId, double toMonthlyCharge, double toMonthlyChargeToDate, double toReceived, double eduTax) {

        super(period, pscId);

        this.taxOilMonthlyCharge = toMonthlyCharge;
        this.taxOilMonthlyChargeToDate = toMonthlyChargeToDate;
        this.taxOilReceived = toReceived;
        this.educationTax = eduTax;

    }

    public TaxOilDue(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                jsonObject.getJsonNumber("taxOilMonthlyCharge").doubleValue(),
                jsonObject.getJsonNumber("taxOilMonthlyChargeToDate").doubleValue(),
                jsonObject.getJsonNumber("taxOilReceived").doubleValue(),
                jsonObject.getJsonNumber("educationTax").doubleValue()
        );
    }

    public double getTaxOilMonthlyCharge() {
        return taxOilMonthlyCharge;
    }

    public double getTaxOilMonthlyChargeToDate() {
        return taxOilMonthlyChargeToDate;
    }

    public double getTaxOilReceived() {
        return taxOilReceived;
    }

    public double getEducationTax() {
        return educationTax;
    }

        
    public static class Builder {

        private EventPeriod newPeriod;
        private String newPscId;
        private double newTaxOilMonthlyCharge;
        private double newTaxOilMonthlyChargeToDate;
        private double newTaxOilReceived;
        private double newEducationTax;

        public Builder withPeriod(EventPeriod period) {
            this.newPeriod = period;
            return this;
        }

        public Builder withContract(String pscId) {
            this.newPscId = pscId;
            return this;
        }

        public Builder withMonthlyCharge(double mCharge) {
            this.newTaxOilMonthlyCharge = mCharge;
            return this;
        }

        public Builder withMonthlyChargeToDate(double mChargeToDate) {
            this.newTaxOilMonthlyChargeToDate = mChargeToDate;
            return this;
        }

        public Builder withRecieved(double received) {
            this.newTaxOilReceived = received;
            return this;
        }

        public Builder withEducationTax(double eduTax) {
            this.newEducationTax = eduTax;
            return this;
        }

        public TaxOilDue build() {
            return new TaxOilDue(newPeriod, newPscId, newTaxOilMonthlyCharge, newTaxOilMonthlyChargeToDate, newTaxOilReceived, newEducationTax);
        }
    }

}

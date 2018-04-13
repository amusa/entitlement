package com.cosm.common.event;

import javax.json.JsonObject;

public class CostOilDue extends CosmEvent {

    private double costOilMonthlyCharge;
    private double costOilMontlyChargeToDate;
    private double costOilReceived;

    private CostOilDue(EventPeriod period, String pscId, double coMonthlyCharge, double coMontlyChargeToDate, double coReceived) {

        super(period, pscId);

        this.costOilMonthlyCharge = coMonthlyCharge;
        this.costOilMontlyChargeToDate = coMontlyChargeToDate;
        this.costOilReceived = coReceived;

    }

    public CostOilDue(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                jsonObject.getJsonNumber("costOilMonthlyCharge").doubleValue(),
                jsonObject.getJsonNumber("costOilMontlyChargeToDate").doubleValue(),
                jsonObject.getJsonNumber("costOilReceived").doubleValue()               
        );
    }

    public double getCostOilMonthlyCharge() {
        return costOilMonthlyCharge;
    }

    public double getCostOilMontlyChargeToDate() {
        return costOilMontlyChargeToDate;
    }

    public double getCostOilReceived() {
        return costOilReceived;
    }

    public static class Builder {

        private EventPeriod newPeriod;
        private String newPscId;
        private double newCostOilMonthlyCharge;
        private double newCostOilMontlyChargeToDate;
        private double newCostOilReceived;

        public Builder withPeriod(EventPeriod period) {
            this.newPeriod = period;
            return this;
        }

        public Builder withContract(String pscId) {
            this.newPscId = pscId;
            return this;
        }

        public Builder withMonthlyCharge(double mCharge) {
            this.newCostOilMonthlyCharge = mCharge;
            return this;
        }

        public Builder withMonthlyChargeToDate(double mChargeToDate) {
            this.newCostOilMontlyChargeToDate = mChargeToDate;
            return this;
        }

        public Builder withRecieved(double received) {
            this.newCostOilReceived = received;
            return this;
        }

        public CostOilDue build() {
            return new CostOilDue(newPeriod, newPscId, newCostOilMonthlyCharge, newCostOilMontlyChargeToDate, newCostOilReceived);
        }
    }

}

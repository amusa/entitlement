package com.cosm.common.event;

import java.time.Instant;
import javax.json.JsonObject;

public class LiftingPosted extends CosmEvent {

    private double corporationProceed;
    private double cashPayment;
    private double weightedAveragePrice;
    private double contractorProceed;
    private double grossIncome;
    private double monthlyIncome;

    public LiftingPosted(EventPeriod period, String pscId, double grossIncome, double monthlyIncome, double corpProceed, double contProceed, double weightedAvePrice, double cashPayment) {
        super(period, pscId);
        this.grossIncome = grossIncome;
        this.monthlyIncome = monthlyIncome;
        this.corporationProceed = corpProceed;
        this.contractorProceed = contProceed;
        this.weightedAveragePrice = weightedAvePrice;
        this.cashPayment = cashPayment;
    }

    public LiftingPosted(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                 jsonObject.getJsonNumber("grossIncome").doubleValue(),
               jsonObject.getJsonNumber("monthlyIncome").doubleValue(),
                jsonObject.getJsonNumber("corporationProceed").doubleValue(),
                jsonObject.getJsonNumber("contractorProceed").doubleValue(),
                jsonObject.getJsonNumber("weightedAveragePrice").doubleValue(),              
                jsonObject.getJsonNumber("cashPayment").doubleValue()
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

    public double getContractorProceed() {
        return contractorProceed;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

}

package com.cosm.common.event;

import java.time.Instant;
import javax.json.JsonObject;

public class CostPosted extends CosmEvent {

    private double currentYearCapex;
    private double amortizedCapex;
    private double currentYearOpex;
    private double currentMonthOpex;
    private double costToDate;
    private double educationTax;

    public CostPosted(EventPeriod period, String pscId, double currentYearCapex,
            double amortizedCapex, double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax) {
        super(period, pscId);
        this.currentYearCapex = currentYearCapex;
        this.amortizedCapex = amortizedCapex;
        this.currentYearOpex = currentYearOpex;
        this.currentMonthOpex = currentMonthOpex;
        this.costToDate = costToDate;
        this.educationTax = educationTax;
    }

    public CostPosted(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),                
                jsonObject.getJsonNumber("currentYearCapex").doubleValue(),
                jsonObject.getJsonNumber("amortizedCapex").doubleValue(),
                jsonObject.getJsonNumber("currentYearOpex").doubleValue(),
                jsonObject.getJsonNumber("currentMonthOpex").doubleValue(),
                jsonObject.getJsonNumber("costToDate").doubleValue(),
                jsonObject.getJsonNumber("educationTax").doubleValue()
        );
    }

    public double getCurrentYearCapex() {
        return currentYearCapex;
    }

    public double getAmortizedCapex() {
        return amortizedCapex;
    }

    public double getCurrentYearOpex() {
        return currentYearOpex;
    }

    public double getCurrentMonthOpex() {
        return currentMonthOpex;
    }

    public double getCostToDate() {
        return costToDate;
    }

    public double getEducationTax() {
        return educationTax;
    }

    public void setCurrentYearCapex(double currentYearCapex) {
        this.currentYearCapex = currentYearCapex;
    }

    public void setAmortizedCapex(double amortizedCapex) {
        this.amortizedCapex = amortizedCapex;
    }

    public void setCurrentYearOpex(double currentYearOpex) {
        this.currentYearOpex = currentYearOpex;
    }

    public void setCurrentMonthOpex(double currentMonthOpex) {
        this.currentMonthOpex = currentMonthOpex;
    }

    public void setCostToDate(double costToDate) {
        this.costToDate = costToDate;
    }

    public void setEducationTax(double educationTax) {
        this.educationTax = educationTax;
    }

    
}

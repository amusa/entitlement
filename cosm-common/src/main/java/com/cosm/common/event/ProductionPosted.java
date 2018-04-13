package com.cosm.common.event;

import java.time.Instant;
import javax.json.JsonObject;

public class ProductionPosted extends CosmEvent {

    private double grossProduction;

    public ProductionPosted() {
        super();
    }

    public ProductionPosted(EventPeriod period, String pscId, double grossProduction) {
        super(period, pscId);
        this.grossProduction = grossProduction;
    }

    public ProductionPosted(EventPeriod period, String pscId, Instant instant, double grossProduction) {
        super(period, pscId, instant);
        this.grossProduction = grossProduction;
    }

    public ProductionPosted(JsonObject jsonObject) {
        this(new EventPeriod(jsonObject.getJsonObject("eventPeriod")),
                jsonObject.getString("pscId"),
                Instant.parse(jsonObject.getString("instant")),
                jsonObject.getJsonNumber("grossProduction").doubleValue()
        );
       
    }

    public double getGrossProduction() {
        return grossProduction;
    }

}

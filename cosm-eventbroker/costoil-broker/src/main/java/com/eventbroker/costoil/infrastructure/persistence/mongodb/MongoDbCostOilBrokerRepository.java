package com.eventbroker.costoil.infrastructure.persistence.mongodb;

import static com.mongodb.client.model.Filters.*;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.EventPeriod;
import com.eventbroker.costoil.application.CostOilBrokerRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.UpdateOptions;

public class MongoDbCostOilBrokerRepository implements CostOilBrokerRepository {

    @Inject
    MongoDatabase mongoCosmDataBase;

    private MongoCollection<Document> eventsCollection() {
        return mongoCosmDataBase.getCollection("events");
    }

    @Override
    public Document addLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
            double corpProceed, double contProceed, double weightedAvePrice, double cashPayment) {

        Document liftingDoc = new Document("periodYear", period.getYear())
                .append("periodMonth", period.getMonth())
                .append("pscId", pscId).append("lifting",
                new Document("grossIncome", grossIncome)
                        .append("monthlyIncome", monthlyIncome)
                        .append("corporationProceed", corpProceed)
                        .append("contractorProceed", contProceed)
                        .append("weightedAveragePrice", weightedAvePrice)
                        .append("cashPayment", cashPayment)
        );

        eventsCollection().insertOne(liftingDoc);

        return liftingDoc;

    }

    @Override
    public Document addProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
            double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax) {

        Document costDoc = new Document("periodYear", period.getYear())
                .append("periodMonth", period.getMonth())
                .append("pscId", pscId).append("cost",
                new Document("currentYearCapex", currentYearCapex)
                        .append("amortizedCapex", amortizedCapex)
                        .append("currentYearOpex", currentYearOpex)
                        .append("currentMonthOpex", currentYearOpex)
                        .append("costToDate", costToDate)
                        .append("educationTax", educationTax));

        eventsCollection().insertOne(costDoc);

        return costDoc;

    }

    @Override
    public void updateLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
            double corpProceed, double contProceed, double weightedAvePrice, double cashPayment) {

        eventsCollection().updateOne(
                and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)
                //exists("lifting")
                ),
                Updates.set("lifting",
                        new Document("grossIncome", grossIncome)
                                .append("monthlyIncome", monthlyIncome)
                                .append("corporationProceed", corpProceed)
                                .append("contractorProceed", contProceed)
                                .append("weightedAveragePrice", weightedAvePrice)
                                .append("cashPayment", cashPayment)),
                new UpdateOptions().upsert(true));

    }

    @Override
    public void updateProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex,
            double amortizedCapex, double currentYearOpex, double currentMonthOpex, double costToDate,
            double educationTax) {

        eventsCollection().updateOne(
                and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)
//                        exists("cost")
                ),
                Updates.set("cost",
                        new Document("currentYearCapex", currentYearCapex).append("amortizedCapex", amortizedCapex)
                                .append("currentYearOpex", currentYearOpex).append("currentMonthOpex", currentYearOpex)
                                .append("costToDate", costToDate).append("educationTax", educationTax)),
                new UpdateOptions().upsert(true));
    }

    @Override
    public Optional<Document> costOilEventOfPeriod(EventPeriod period, String pscId) {
        Document doc = eventsCollection()
                .find(and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)))
                .first();

        return Optional.ofNullable(doc);
    }

    @Override
    public Document addTaxOilEvent(EventPeriod period, String pscId, double taxOilMonthlyCharge, double taxOilMontlyChargeToDate,
            double taxOilReceived, double educationTax) {

        Document doc = new Document("periodYear", period.getYear())
                .append("periodMonth", period.getMonth())
                .append("pscId", pscId)
                .append("taxoil",
                        new Document("monthlyCharge", taxOilMonthlyCharge))
                .append("montlyChargeToDate", taxOilMontlyChargeToDate)
                .append("received", taxOilReceived)
                .append("educationTax", educationTax);

        eventsCollection().insertOne(doc);

        return doc;
    }

    @Override
    public void updateTaxOilEvent(EventPeriod period, String pscId, double taxOilMonthlyCharge, double taxOilMontlyChargeToDate,
            double taxOilReceived, double educationTax) {
        eventsCollection().updateOne(
                and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)
//                        exists("taxoil")
                ),
                Updates.set("taxoil", new Document("monthlyCharge", taxOilMonthlyCharge)
                        .append("montlyChargeToDate", taxOilMontlyChargeToDate)
                        .append("received", taxOilReceived)
                        .append("educationTax", educationTax)
                ),
                new UpdateOptions().upsert(true));

    }

}

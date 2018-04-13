package com.eventbroker.taxoil.infrastructure.persistence.mongodb;

import static com.mongodb.client.model.Filters.*;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.EventPeriod;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.UpdateOptions;
import com.eventbroker.taxoil.application.TaxOilRepository;

public class MongoDbTaxOilRepository implements TaxOilRepository {

	@Inject
	MongoDatabase mongoCosmDataBase;

	private MongoCollection<Document> eventsCollection() {
		return mongoCosmDataBase.getCollection("events");
	}

	@Override
	public Document addLiftingEvent(EventPeriod period, String pscId, double grossIncome, double corpProceed) {

		Document liftingDoc = new Document("periodYear", period.getYear())
				.append("periodMonth", period.getMonth())
				.append("pscId", pscId)
				.append("lifting",
						new Document("grossIncome", grossIncome)
						.append("corporationProceed", corpProceed));

		eventsCollection().insertOne(liftingDoc);
		
		return liftingDoc;

	}
	


	@Override
	public Document addProductionCostEvent(EventPeriod period, String pscId, double amortizedCapex, double currentYearCapex, 
			double currentYearOpex,  double educationTax) {

		Document costDoc = new Document("periodYear", period.getYear())
				.append("periodMonth", period.getMonth())
				.append("pscId", pscId)
				.append("cost",
						new Document("currentYearCapex", currentYearCapex)
						.append("amortizedCapex", amortizedCapex)
								.append("currentYearOpex", currentYearOpex)
								.append("educationTax", educationTax));

		eventsCollection().insertOne(costDoc);
		
		return costDoc;

	}

	@Override
	public Document addRoyaltyEvent(EventPeriod period, String pscId, double royaltyMonthlyCharge, double royaltyMontlyChargeToDate,
			double royaltyReceived) {

		Document royDoc = new Document("periodYear", period.getYear())
				.append("periodMonth", period.getMonth())
				.append("pscId", pscId)
				.append("royalty", 
						new Document("montlyCharge", royaltyMonthlyCharge)
						.append("montlyChargeToDate", royaltyMontlyChargeToDate)
						.append("received", royaltyReceived)
						);

		eventsCollection().insertOne(royDoc);

		return royDoc;
	}

	@Override
	public void updateLiftingEvent(EventPeriod period, String pscId, double grossIncome, double corpProceed) {

		eventsCollection().updateOne(
				and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)
						//,exists("lifting")
                                ),
				Updates.set("lifting",
						new Document("grossIncome", grossIncome)				
								.append("corporationProceed", corpProceed)
								
								),
				new UpdateOptions().upsert(true)
                );

	}

	@Override
	public void updateProductionCostEvent(EventPeriod period, String pscId, double amortizedCapex, double currentYearCapex, 
			double currentYearOpex,  double educationTax) {
		
		eventsCollection().updateOne(
				and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)
//						exists("cost")
                                ),
				Updates.set("cost",
						new Document("amortizedCapex", amortizedCapex)
						.append("currentYearCapex", currentYearCapex)
								.append("currentYearOpex", currentYearOpex)
								.append("educationTax", educationTax)),
				new UpdateOptions().upsert(true));
	}

	@Override
	public void updateRoyaltyEvent(EventPeriod period, String pscId, double royaltyMonthlyCharge, double royaltyMontlyChargeToDate,
			double royaltyReceived) {
		eventsCollection().updateOne(
				and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)//,
						//exists("production")
                                ),
				Updates.set("royalty", 
						new Document("montlyCharge", royaltyMonthlyCharge)
						.append("montlyChargeToDate", royaltyMontlyChargeToDate)
						.append("received", royaltyReceived)						
						),
				new UpdateOptions().upsert(true));

	}

	@Override
	public Optional<Document> taxOilEventOfPeriod(EventPeriod period, String pscId) {
		Document doc = eventsCollection()
				.find(and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)))
				.first();

		return Optional.ofNullable(doc);
	}
}

package com.eventbroker.royalty.infrastructure.persistence.mongodb;

import static com.mongodb.client.model.Filters.*;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.EventPeriod;
import com.eventbroker.royalty.application.RoyaltyBrokerRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.UpdateOptions;

public class MongoDbRoyaltyBrokerRepository implements RoyaltyBrokerRepository {

	@Inject
	MongoDatabase mongoCosmDataBase;

	private MongoCollection<Document> eventsCollection() {
		return mongoCosmDataBase.getCollection("events");
	}

	@Override
	public Document addLiftingEvent(EventPeriod period, String pscId, double corpProceed, double weightedAvePrice, double cashPayment) {

		Document liftingDoc = new Document("periodYear", period.getYear())
				.append("periodMonth", period.getMonth())
				.append("pscId", pscId).append("lifting",
						new Document("corporationProceed", corpProceed)	
								.append("weightedAveragePrice", weightedAvePrice)
								.append("cashPayment", cashPayment)
								);

		eventsCollection().insertOne(liftingDoc);
		
		return liftingDoc;

	}

	
	@Override
	public Document addProductionEvent(EventPeriod period, String pscId, double grossProduction) {

		Document prodDoc = new Document("periodYear", period.getYear())
				.append("periodMonth", period.getMonth())
				.append("pscId", pscId)
				.append("production", new Document("grossProduction", grossProduction));

		eventsCollection().insertOne(prodDoc);

		return prodDoc;
	}

	@Override
	public void updateLiftingEvent(EventPeriod period, String pscId, double corpProceed, double weightedAvePrice, double cashPayment) {


		eventsCollection().updateOne(
				and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId),
						exists("lifting")),
				Updates.set("lifting",
						new Document("corporationProceed", corpProceed)						
								.append("weightedAveragePrice", weightedAvePrice)
								.append("cashPayment", cashPayment)),
				new UpdateOptions().upsert(true));

	}

	
	
	@Override
	public void updateProductionEvent(EventPeriod period, String pscId, double grossProduction) {
		eventsCollection().updateOne(
				and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId),
						exists("production")),
				Updates.set("production", new Document("grossProduction", grossProduction)),
				new UpdateOptions().upsert(true));

	}

	@Override
	public Optional<Document> royaltyEventOfPeriod(EventPeriod period, String pscId) {
		Document doc = eventsCollection()
				.find(and(eq("periodYear", period.getYear()), eq("periodMonth", period.getMonth()), eq("pscId", pscId)))
				.first();

		return Optional.of(doc);
	}
}

package com.eventbroker.royalty.application;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;
import com.cosm.common.event.RoyaltyReady;
import com.eventbroker.royalty.event.kafka.EventProducer;

public class DefaultRoyaltyBrokerService implements RoyaltyBrokerService {

	@Inject
	RoyaltyBrokerRepository brokerRepository;

	@Inject
	EventProducer eventProducer;

	public void when(ProductionPosted event) {

		Optional<Document> eventDoc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateProductionEvent(event.getEventPeriod(), event.getPscId(),
					event.getGrossProduction());
		} else {
			Document doc = brokerRepository.addProductionEvent(event.getEventPeriod(), event.getPscId(),
					event.getGrossProduction());

			fireOnEventCompleted(doc);
		}

	}

	
	public void when(LiftingPosted event) {

		Optional<Document> eventDoc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateLiftingEvent(event.getEventPeriod(), event.getPscId(), event.getCorporationProceed(),
					event.getWeightedAveragePrice(), event.getCashPayment());
		} else {
			Document doc = brokerRepository.addLiftingEvent(event.getEventPeriod(), event.getPscId(),
					event.getCorporationProceed(), event.getWeightedAveragePrice(), event.getCashPayment());

			fireOnEventCompleted(doc);
		}
	}

	private void fireOnEventCompleted(Document doc) {
		if (!doc.containsKey("production"))
			return;

		if (!doc.containsKey("lifting"))
			return;

		eventProducer.publish(royaltyReadyEvent(doc));
	}

	private RoyaltyReady royaltyReadyEvent(Document doc) {

		RoyaltyReady.Builder builder = new RoyaltyReady.Builder();
	

		return builder.withGrossProduction(doc.getDouble("grossProduction"))
				.withCorporationProceed(doc.getDouble("corporationProceed"))
				.withCashPayment(doc.getDouble("cashPayment"))
				.withWeightedAveragePrice(doc.getDouble("weightedAveragePrice"))
				.build();
		
	}

}

package com.eventbroker.royalty.application;

import com.cosm.common.event.EventPeriod;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;
import com.cosm.common.event.RoyaltyReady;
import com.eventbroker.royalty.event.kafka.EventProducer;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultRoyaltyService implements RoyaltyService {

    @Inject
    RoyaltyRepository brokerRepository;

    @Inject
    EventProducer eventProducer;

    @Override
    public void when(ProductionPosted event) {

//        Optional<Document> eventDoc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateProductionEvent(event.getEventPeriod(), event.getPscId(),
                event.getGrossProduction());
//        } else {
//            Document doc = brokerRepository.addProductionEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getGrossProduction());
//
        Optional<Document> doc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    @Override
    public void when(LiftingPosted event) {

//		Optional<Document> eventDoc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());
//		if (eventDoc.isPresent()) {
        brokerRepository.updateLiftingEvent(event.getEventPeriod(), event.getPscId(), event.getCorporationProceed(),
                event.getWeightedAveragePrice(), event.getCashPayment());
//		} else {
//			Document doc = brokerRepository.addLiftingEvent(event.getEventPeriod(), event.getPscId(),
//					event.getCorporationProceed(), event.getWeightedAveragePrice(), event.getCashPayment());
        Optional<Document> doc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//		}
    }

    private void fireOnEventCompleted(Document doc) {
        if (!doc.containsKey("production")) {
            return;
        }

        if (!doc.containsKey("lifting")) {
            return;
        }

        eventProducer.publish(royaltyReadyEvent(doc));
    }

    private RoyaltyReady royaltyReadyEvent(Document doc) {

        RoyaltyReady.Builder builder = new RoyaltyReady.Builder();
        Document prodDoc = doc.get("production", Document.class);
        Document liftDoc = doc.get("lifting", Document.class);

        return builder.withPeriod(
                new EventPeriod(doc.getInteger("periodYear"), doc.getInteger("periodMonth")))
                .withContract(doc.getString("pscId"))
                .withGrossProduction(prodDoc.getDouble("grossProduction"))
                .withCorporationProceed(liftDoc.getDouble("corporationProceed"))
                .withCashPayment(liftDoc.getDouble("cashPayment"))
                .withWeightedAveragePrice(liftDoc.getDouble("weightedAveragePrice"))
                .build();

    }

}

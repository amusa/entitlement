package com.eventbroker.taxoil.application.internal;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostPosted;
import com.cosm.common.event.EventPeriod;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.TaxOilReady;
import com.eventbroker.taxoil.application.TaxOilRepository;
import com.eventbroker.taxoil.application.TaxOilService;
import com.eventbroker.taxoil.event.kafka.EventProducer;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultTaxOilService implements TaxOilService {

    @Inject
    TaxOilRepository brokerRepository;

    @Inject
    EventProducer eventProducer;

    public void when(RoyaltyDue event) {

//        Optional<Document> eventDoc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
                event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());
//        } else {
//            Document doc = brokerRepository.addRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());
//
        Optional<Document> doc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    public void when(CostPosted event) {
//        Optional<Document> eventDoc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateProductionCostEvent(event.getEventPeriod(), event.getPscId(),
                event.getAmortizedCapex(), event.getCurrentYearCapex(), event.getCurrentYearOpex(),
                event.getEducationTax());
//        } else {
//            Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getAmortizedCapex(), event.getCurrentYearCapex(), event.getCurrentYearOpex(),
//                    event.getEducationTax());
//
        Optional<Document> doc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    public void when(LiftingPosted event) {

//		Optional<Document> eventDoc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//		if (eventDoc.isPresent()) {
        brokerRepository.updateLiftingEvent(event.getEventPeriod(), event.getPscId(),
                event.getGrossIncome(), event.getCorporationProceed());
//		} else {
//			Document doc = brokerRepository.addLiftingEvent(event.getEventPeriod(), event.getPscId(),
//					event.getGrossIncome(),	event.getCorporationProceed());
//
        Optional<Document> doc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//		}
    }

    private void fireOnEventCompleted(Document doc) {
        if (!doc.containsKey("royalty")) {
            return;
        }

        if (!doc.containsKey("lifting")) {
            return;
        }

        if (!doc.containsKey("cost")) {
            return;
        }

        eventProducer.publish(taxOilReadyEvent(doc));
    }

    private TaxOilReady taxOilReadyEvent(Document doc) {

        TaxOilReady.Builder builder = new TaxOilReady.Builder();
        Document royaltyDoc = doc.get("royalty", Document.class);
        Document liftDoc = doc.get("lifting", Document.class);
        Document costDoc = doc.get("cost", Document.class);

        return builder.withPeriod(
                new EventPeriod(doc.getInteger("periodYear"), doc.getInteger("periodMonth")))
                .withContract(doc.getString("pscId"))
                .withAmortizedCapex(costDoc.getDouble("amortizedCapex"))
                .withCurrentYearCapex(costDoc.getDouble("currentYearCapex"))
                .withCurrentYearOpex(costDoc.getDouble("currentYearOpex"))
                .withEducationTax(costDoc.getDouble("educationTax"))
                .withGrossIncome(liftDoc.getDouble("grossIncome"))
                .withCorporationProceed(liftDoc.getDouble("corporationProceed"))
                .withRoyalty(royaltyDoc.getDouble("montlyCharge"))
                .build();

    }

}

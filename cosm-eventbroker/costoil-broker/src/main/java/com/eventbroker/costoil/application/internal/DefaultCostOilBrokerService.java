package com.eventbroker.costoil.application.internal;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostOilReady;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.EventPeriod;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.TaxOilDue;
import com.eventbroker.costoil.application.CostOilBrokerRepository;
import com.eventbroker.costoil.application.CostOilBrokerService;
import com.eventbroker.costoil.event.kafka.EventProducer;

public class DefaultCostOilBrokerService implements CostOilBrokerService {

    @Inject
    CostOilBrokerRepository brokerRepository;

    @Inject
    EventProducer eventProducer;

    public void when(TaxOilDue event) {

//		Optional<Document> eventDoc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//		if (eventDoc.isPresent()) {
        brokerRepository.updateTaxOilEvent(event.getEventPeriod(), event.getPscId(),
                event.getTaxOilMonthlyCharge(), event.getTaxOilMonthlyChargeToDate(), event.getTaxOilReceived(),
                event.getEducationTax());
//		} else {
//			Document doc = brokerRepository.addTaxOilEvent(event.getEventPeriod(), event.getPscId(),
//					event.getTaxOilMonthlyCharge(), event.getTaxOilMontlyChargeToDate(), event.getTaxOilReceived(), 
//					event.getEductionTax());
//
        Optional<Document> doc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//		}

    }

    public void when(CostPosted event) {
//        Optional<Document> eventDoc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateProductionCostEvent(event.getEventPeriod(), event.getPscId(),
                event.getCurrentYearCapex(), event.getAmortizedCapex(), event.getCurrentYearOpex(),
                event.getCurrentMonthOpex(), event.getCostToDate(), event.getEducationTax());
//        } else {
//            Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getCurrentYearCapex(), event.getAmortizedCapex(), event.getCurrentYearOpex(),
//                    event.getCurrentMonthOpex(), event.getCostToDate(), event.getEducationTax());
//
        Optional<Document> doc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    public void when(LiftingPosted event) {

//        Optional<Document> eventDoc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateLiftingEvent(event.getEventPeriod(), event.getPscId(), event.getGrossIncome(),
                event.getMonthlyIncome(), event.getCorporationProceed(), event.getContractorProceed(),
                event.getWeightedAveragePrice(), event.getCashPayment());
//        } else {
//            Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getGrossIncome(), event.getMonthlyIncome(), event.getCorporationProceed(),
//                    event.getContractorProceed(), event.getWeightedAveragePrice(), event.getCashPayment());
//
        Optional<Document> doc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }
    }

    private void fireOnEventCompleted(Document doc) {
        if (!doc.containsKey("taxoil")) {
            return;
        }

        if (!doc.containsKey("lifting")) {
            return;
        }

        if (!doc.containsKey("cost")) {
            return;
        }

        eventProducer.publish(costOilReadyEvent(doc));
    }

    private CostOilReady costOilReadyEvent(Document doc) {

        CostOilReady.Builder builder = new CostOilReady.Builder();
        Document taxoilDoc = doc.get("taxoil", Document.class);
        Document liftDoc = doc.get("lifting", Document.class);
        Document costDoc = doc.get("cost", Document.class);

        return builder.withPeriod(
                new EventPeriod(doc.getInteger("periodYear"), doc.getInteger("periodMonth")))
                .withContract(doc.getString("pscId"))
                .withAmortizedCapex(costDoc.getDouble("amortizedCapex"))
                .withCurrentMonthOpex(costDoc.getDouble("currentMonthOpex"))
                .withEducationTax(taxoilDoc.getDouble("educationTax"))
                .withContractorProceed(liftDoc.getDouble("contractorProceed"))
                .withMonthlyIncome(liftDoc.getDouble("monthlyIncome"))
                .build();

    }

}

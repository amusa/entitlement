package com.eventbroker.profitoil.application.internal;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostOilDue;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.EventPeriod;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProfitOilReady;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.TaxOilDue;
import com.eventbroker.profitoil.application.ProfitOilBrokerRepository;
import com.eventbroker.profitoil.application.ProfitOilBrokerService;
import com.eventbroker.profitoil.event.kafka.EventProducer;

public class DefaultProfitOilBrokerService implements ProfitOilBrokerService {

    @Inject
    ProfitOilBrokerRepository brokerRepository;

    @Inject
    EventProducer eventProducer;

    public void when(RoyaltyDue event) {

//		Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//		if (eventDoc.isPresent()) {
        brokerRepository.updateRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
                event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());
//		} else {
//			Document doc = brokerRepository.addRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
//					event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());
//
        Optional<Document> doc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//		}

    }

    public void when(TaxOilDue event) {

//        Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateTaxOilEvent(event.getEventPeriod(), event.getPscId(),
                event.getTaxOilMonthlyCharge(), event.getTaxOilMonthlyChargeToDate(), event.getTaxOilReceived(),
                event.getEducationTax());
//        } else {
//            Document doc = brokerRepository.addTaxOilEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getTaxOilMonthlyCharge(), event.getTaxOilMontlyChargeToDate(), event.getTaxOilReceived(),
//                    event.getEductionTax());

        Optional<Document> doc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    public void when(CostOilDue event) {

//        Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateCostOilEvent(event.getEventPeriod(), event.getPscId(),
                event.getCostOilMonthlyCharge(), event.getCostOilMontlyChargeToDate(), event.getCostOilReceived());
//        } else {
//            Document doc = brokerRepository.addRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getCostOilMonthlyCharge(), event.getCostOilMontlyChargeToDate(), event.getCostOilReceived());

        Optional<Document> doc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    public void when(CostPosted event) {
//        Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateProductionCostEvent(event.getEventPeriod(), event.getPscId(),
                event.getCurrentYearCapex(), event.getAmortizedCapex(), event.getCurrentYearOpex(),
                event.getCurrentMonthOpex(), event.getCostToDate(), event.getEducationTax());
//        } else {
//            Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getCurrentYearCapex(), event.getAmortizedCapex(), event.getCurrentYearOpex(),
//                    event.getCurrentMonthOpex(), event.getCostToDate(), event.getEducationTax());

        Optional<Document> doc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
        fireOnEventCompleted(doc.get());
//        }

    }

    public void when(LiftingPosted event) {

//        Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
//
//        if (eventDoc.isPresent()) {
        brokerRepository.updateLiftingEvent(event.getEventPeriod(), event.getPscId(), event.getGrossIncome(),
                event.getMonthlyIncome(), event.getCorporationProceed(), event.getContractorProceed(),
                event.getWeightedAveragePrice(), event.getCashPayment());
//        } else {
//            Document doc = brokerRepository.addLiftingEvent(event.getEventPeriod(), event.getPscId(),
//                    event.getGrossIncome(), event.getMonthlyIncome(), event.getCorporationProceed(),
//                    event.getContractorProceed(), event.getWeightedAveragePrice(), event.getCashPayment());

        Optional<Document> doc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());
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

        if (!doc.containsKey("costoil")) {
            return;
        }

        if (!doc.containsKey("royalty")) {
            return;
        }

        eventProducer.publish(profitOilReadyEvent(doc));
    }

    private ProfitOilReady profitOilReadyEvent(Document doc) {

        ProfitOilReady.Builder builder = new ProfitOilReady.Builder();
        Document costDoc = doc.get("cost", Document.class);
        Document liftDoc = doc.get("lifting", Document.class);
        Document royDoc = doc.get("royalty", Document.class);
        Document costoilDoc = doc.get("costoil", Document.class);
        Document taxoilDoc = doc.get("taxoil", Document.class);

        return builder.withPeriod(
                new EventPeriod(doc.getInteger("periodYear"), doc.getInteger("periodMonth")))
                .withContractId(doc.getString("pscId"))
                .withCostToDate(costDoc.getDouble("costToDate"))
                .withMonthlyIncome(liftDoc.getDouble("monthlyIncome"))
                .withCorporationProceed(liftDoc.getDouble("corporationProceed"))
                .withContractorProceed(liftDoc.getDouble("contractorProceed"))
                .withRoyaltyMonthlyCharge(royDoc.getDouble("royaltyMonthlyCharge"))
                .withRoyaltyMonthlyChargeToDate(royDoc.getDouble("royaltyMontlyChargeToDate"))
                .withRoyaltyReceived(royDoc.getDouble("royaltyReceived"))
                .withTaxOilMonthlyCharge(costoilDoc.getDouble("costOilMonthlyCharge"))
                .withTaxOilMonthlyChargeToDate(costoilDoc.getDouble("costOilMonthlyChargeToDate"))
                .withTaxOilReceived(costoilDoc.getDouble("costOilReceived"))
                .withCostOilMonthlyCharge(taxoilDoc.getDouble("taxOilMonthlyCharge"))
                .withCostOilMonthlyChargeToDate(taxoilDoc.getDouble("taxOilMonthlyChargeToDate"))
                .withCostOilReceived(taxoilDoc.getDouble("taxOilReceived"))
                .build();

    }

}

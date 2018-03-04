package com.eventbroker.taxoil.application;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.TaxOilReady;
import com.eventbroker.taxoil.event.kafka.EventProducer;

public class DefaultTaxOilBrokerService implements TaxOilBrokerService {

	@Inject
	TaxOilBrokerRepository brokerRepository;

	@Inject
	EventProducer eventProducer;

	public void when(RoyaltyDue event) {

		Optional<Document> eventDoc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
					event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());
		} else {
			Document doc = brokerRepository.addRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
					event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());

			fireOnEventCompleted(doc);
		}

	}

	public void when(CostPosted event) {
		Optional<Document> eventDoc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateProductionCostEvent(event.getEventPeriod(), event.getPscId(),
					event.getArmotizedCapex(), event.getCurrentYearCapex(), event.getCurrentYearOpex(),
					event.getEducationTax());
		} else {
			Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
					event.getArmotizedCapex(), event.getCurrentYearCapex(), event.getCurrentYearOpex(),
					event.getEducationTax());

			fireOnEventCompleted(doc);
		}

	}

	public void when(LiftingPosted event) {

		Optional<Document> eventDoc = brokerRepository.taxOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateLiftingEvent(event.getEventPeriod(), event.getPscId(),
					event.getGrossIncome(),	event.getCorporationProceed());
		} else {
			Document doc = brokerRepository.addLiftingEvent(event.getEventPeriod(), event.getPscId(),
					event.getGrossIncome(),	event.getCorporationProceed());

			fireOnEventCompleted(doc);
		}
	}

	private void fireOnEventCompleted(Document doc) {
		if (!doc.containsKey("royalty"))
			return;

		if (!doc.containsKey("lifting"))
			return;

		if (!doc.containsKey("cost"))
			return;

		eventProducer.publish(taxOilReadyEvent(doc));
	}

	private TaxOilReady taxOilReadyEvent(Document doc) {

		TaxOilReady.Builder builder = new TaxOilReady.Builder();
	
		return builder.withAmortizedCapex(doc.getDouble("armotizedCapex"))
				.withCurrentYearCapex(doc.getDouble("currentYearCapex"))
				.withCurrentYearOpex(doc.getDouble("currentYearOpex"))
				.withEducationTax(doc.getDouble("educationTax"))
				.withGrossIncome(doc.getDouble("grossIncome"))
				.withCorporationProceed(doc.getDouble("corporationProceed"))								
				.build();
		
	}

}

package com.eventbroker.costoil.application;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;
import com.cosm.common.event.RoyaltyDue;
import com.eventbroker.costoil.event.kafka.EventProducer;

public class DefaultCostOilBrokerService implements CostOilBrokerService {

	@Inject
	CostOilBrokerRepository brokerRepository;

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

	public void when(CostPosted event) {
		Optional<Document> eventDoc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateProductionCostEvent(event.getEventPeriod(), event.getPscId(),
					event.getCurrentYearCapex(), event.getArmotizedCapex(), event.getCurrentYearOpex(),
					event.getCurrentMonthOpex(), event.getCostToDate(), event.getEducationTax());
		} else {
			Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
					event.getCurrentYearCapex(), event.getArmotizedCapex(), event.getCurrentYearOpex(),
					event.getCurrentMonthOpex(), event.getCostToDate(), event.getEducationTax());

			fireOnEventCompleted(doc);
		}

	}

	public void when(LiftingPosted event) {

		Optional<Document> eventDoc = brokerRepository.royaltyEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateProductionCostEvent(event.getEventPeriod(), event.getPscId(), event.getGrossIncome(),
					event.getMonthlyIncome(), event.getCorporationProceed(), event.getContractorProceed(),
					event.getWeightedAveragePrice(), event.getCashPayment());
		} else {
			Document doc = brokerRepository.addProductionCostEvent(event.getEventPeriod(), event.getPscId(),
					event.getGrossIncome(), event.getMonthlyIncome(), event.getCorporationProceed(),
					event.getContractorProceed(), event.getWeightedAveragePrice(), event.getCashPayment());

			fireOnEventCompleted(doc);
		}
	}

	private void fireOnEventCompleted(Document doc) {
		if (!doc.containsKey("production"))
			return;

		if (!doc.containsKey("lifting"))
			return;

		if (!doc.containsKey("cost"))
			return;

		eventProducer.publish(royaltyDueEvent(doc));
	}

	private RoyaltyDue royaltyDueEvent(Document doc) {

		RoyaltyDue.RoyaltyEventBuilder builder = new RoyaltyDue.RoyaltyEventBuilder();
	

		return builder.withCurrentYearCapex(doc.getDouble("currentYearCapex"))
				.withAmortizedCapex(doc.getDouble("armotizedCapex"))
				.withCurrentYearOpex(doc.getDouble("currentYearOpex"))
				.withCurrentMonthOpex(doc.getDouble("currentMonthlyOpex"))
				.withCostToDate(doc.getDouble("costToDate"))
				.withEducationTax(doc.getDouble("educationTax"))
				.withCorporationProceed(doc.getDouble("corporationProceed"))
				.withCashPayment(doc.getDouble("cashPayment"))
				.withWeightedAveragePrice(doc.getDouble("weightedAveragePrice"))
				.withContractorProceed(doc.getDouble("contractorProceed"))
				.withGrossIncome(doc.getDouble("grossIncome"))
				.withMonthlyIncome(doc.getDouble("monthlyIncome"))
				.withGrossProduction(doc.getDouble("grossProduction"))
				.build();
		
	}

}

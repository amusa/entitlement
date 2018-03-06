package com.cosm.psc.entitlement.costoil.application.internal;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostOilReady;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.TaxOilDue;
import com.cosm.psc.entitlement.costoil.application.CostOilBrokerService;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilRepository;
import com.eventbroker.costoil.event.kafka.EventProducer;

public class DefaultCostOilBrokerService implements CostOilBrokerService {

	@Inject
	CostOilRepository brokerRepository;

	@Inject
	EventProducer eventProducer;

	public void when(TaxOilDue event) {

		Optional<Document> eventDoc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateTaxOilEvent(event.getEventPeriod(), event.getPscId(),
					event.getTaxOilMonthlyCharge(), event.getTaxOilMontlyChargeToDate(), event.getTaxOilReceived());
		} else {
			Document doc = brokerRepository.addTaxOilEvent(event.getEventPeriod(), event.getPscId(),
					event.getTaxOilMonthlyCharge(), event.getTaxOilMontlyChargeToDate(), event.getTaxOilReceived());

			fireOnEventCompleted(doc);
		}

	}

	public void when(CostPosted event) {
		Optional<Document> eventDoc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

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

		Optional<Document> eventDoc = brokerRepository.costOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

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
		if (!doc.containsKey("taxoil"))
			return;

		if (!doc.containsKey("lifting"))
			return;

		if (!doc.containsKey("cost"))
			return;

		eventProducer.publish(costOilReadyEvent(doc));
	}

	private CostOilReady costOilReadyEvent(Document doc) {

		CostOilReady.Builder builder = new CostOilReady.Builder();
	
		return builder.withAmortizedCapex(doc.getDouble("armotizedCapex"))				
				.withCurrentMonthOpex(doc.getDouble("currentMonthlyOpex"))				
				.withEducationTax(doc.getDouble("educationTax"))
				.withCorporationProceed(doc.getDouble("corporationProceed"))				
				.withGrossIncome(doc.getDouble("grossIncome"))
				.build();
		
	}

}

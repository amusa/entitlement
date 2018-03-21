package com.eventbroker.profitoil.application.internal;

import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;

import com.cosm.common.event.CostOilDue;
import com.cosm.common.event.CostPosted;
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

		Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
					event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());
		} else {
			Document doc = brokerRepository.addRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
					event.getRoyaltyMonthlyCharge(), event.getRoyaltyMontlyChargeToDate(), event.getRoyaltyReceived());

			fireOnEventCompleted(doc);
		}

	}
	
	
	public void when(TaxOilDue event) {

		Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateTaxOilEvent(event.getEventPeriod(), event.getPscId(),
					event.getTaxOilMonthlyCharge(), event.getTaxOilMontlyChargeToDate(), event.getTaxOilReceived(),
					event.getEductionTax());
		} else {
			Document doc = brokerRepository.addTaxOilEvent(event.getEventPeriod(), event.getPscId(),
					event.getTaxOilMonthlyCharge(), event.getTaxOilMontlyChargeToDate(), event.getTaxOilReceived(), 
					event.getEductionTax());

			fireOnEventCompleted(doc);
		}

	}
	
	
	public void when(CostOilDue event) {

		Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

		if (eventDoc.isPresent()) {
			brokerRepository.updateRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
					event.getCostOilMonthlyCharge(), event.getCostOilMontlyChargeToDate(), event.getCostOilReceived());
		} else {
			Document doc = brokerRepository.addRoyaltyEvent(event.getEventPeriod(), event.getPscId(),
					event.getCostOilMonthlyCharge(), event.getCostOilMontlyChargeToDate(), event.getCostOilReceived());

			fireOnEventCompleted(doc);
		}

	}
	

	public void when(CostPosted event) {
		Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

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

		Optional<Document> eventDoc = brokerRepository.profitOilEventOfPeriod(event.getEventPeriod(), event.getPscId());

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
		
		if (!doc.containsKey("costoil"))
			return;

		if (!doc.containsKey("royalty"))
			return;
		
		eventProducer.publish(profitOilReadyEvent(doc));
	}

	private ProfitOilReady profitOilReadyEvent(Document doc) {

		ProfitOilReady.Builder builder = new ProfitOilReady.Builder();
			
		return builder
				//TODO:Fix EventPeriod
//				.withPeriodYear(doc.getInteger("periodYear"))
//				.withPeriodMonth(doc.getInteger("periodMonth"))
				.withContractId(doc.getString("pscId"))
				.withCostToDate(doc.getDouble("costToDate"))
				.withMonthlyIncome(doc.getDouble("monthlyIncome"))
				.withCorporationProceed(doc.getDouble("corporationProceed"))
				.withContractorProceed(doc.getDouble("contractorProceed"))
				.withRoyaltyMonthlyCharge(doc.getDouble("royaltyMonthlyCharge"))
				.withRoyaltyMonthlyChargeToDate(doc.getDouble("royaltyMontlyChargeToDate"))
				.withRoyaltyReceived(doc.getDouble("royaltyReceived"))				
				.withTaxOilMonthlyCharge(doc.getDouble("costOilMonthlyCharge"))
				.withTaxOilMonthlyChargeToDate(doc.getDouble("costOilMonthlyChargeToDate"))
				.withTaxOilReceived(doc.getDouble("costOilReceived"))				
				.withCostOilMonthlyCharge(doc.getDouble("taxOilMonthlyCharge"))
				.withCostOilMonthlyChargeToDate(doc.getDouble("taxOilMonthlyChargeToDate"))
				.withCostOilReceived(doc.getDouble("taxOilReceived"))
				.build();
		
	}

}

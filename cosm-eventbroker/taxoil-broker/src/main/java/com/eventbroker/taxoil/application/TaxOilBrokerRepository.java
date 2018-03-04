package com.eventbroker.taxoil.application;

import java.util.Optional;

import org.bson.Document;

import com.cosm.common.event.EventPeriod;

public interface TaxOilBrokerRepository {

	Document addLiftingEvent(EventPeriod period, String pscId, double grossIncome, double corpProceed);

	Document addProductionCostEvent(EventPeriod period, String pscId, double amortizedCapex, double currentYearCapex, 
			double currentYearOpex, double educationTax);

	Document addRoyaltyEvent(EventPeriod period, String pscId, double royaltyMonthlyCharge, double royaltyMontlyChargeToDate,
			double royaltyReceived);

	void updateLiftingEvent(EventPeriod period, String pscId, double grossIncome, double corpProceed);

	void updateProductionCostEvent(EventPeriod period, String pscId, double amortizedCapex, double currentYearCapex, 
			double currentYearOpex, double educationTax);

	void updateRoyaltyEvent(EventPeriod period, String pscId, double royaltyMonthlyCharge, double royaltyMontlyChargeToDate,
			double royaltyReceived);

	Optional<Document> taxOilEventOfPeriod(EventPeriod period, String pscId);

}
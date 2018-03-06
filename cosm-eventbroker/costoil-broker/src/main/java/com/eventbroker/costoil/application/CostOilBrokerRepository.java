package com.eventbroker.costoil.application;

import java.util.Optional;

import org.bson.Document;

import com.cosm.common.event.EventPeriod;

public interface CostOilBrokerRepository {

	Document addLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
			double corpProceed, double contProceed, double weightedAvePrice, double cashPayment);

	Document addProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
			double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax);

	Document addTaxOilEvent(EventPeriod period, String pscId, double taxOil, double taxOilToDate, double taxOilReceived);
	
	void updateLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
			double corpProceed, double contProceed, double weightedAvePrice, double cashPayment);

	void updateProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
			double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax);

	void updateTaxOilEvent(EventPeriod period, String pscId, double taxOil, double taxOilToDate, double taxOilReceived);

	Optional<Document> costOilEventOfPeriod(EventPeriod period, String pscId);

}

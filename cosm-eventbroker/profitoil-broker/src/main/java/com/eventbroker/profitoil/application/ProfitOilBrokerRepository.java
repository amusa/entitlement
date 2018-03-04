package com.eventbroker.profitoil.application;

import java.util.Optional;
import org.bson.Document;
import com.cosm.common.event.EventPeriod;

public interface ProfitOilBrokerRepository {

	Document addLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
			double corpProceed, double contProceed, double weightedAvePrice, double cashPayment);

	Document addProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
			double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax);

	Document addProductionEvent(EventPeriod period, String pscId, double grossProduction);

	void updateLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
			double corpProceed, double contProceed, double weightedAvePrice, double cashPayment);

	void updateProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
			double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax);

	void updateProductionEvent(EventPeriod period, String pscId, double grossProduction);

	Optional<Document> royaltyEventOfPeriod(EventPeriod period, String pscId);

}

package com.eventbroker.profitoil.application;

import java.util.Optional;
import org.bson.Document;
import com.cosm.common.event.EventPeriod;

public interface ProfitOilBrokerRepository {

	Document addLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
			double corpProceed, double contProceed, double weightedAvePrice, double cashPayment);
	
	
	void updateLiftingEvent(EventPeriod period, String pscId, double grossIncome, double monthlyIncome,
			double corpProceed, double contProceed, double weightedAvePrice, double cashPayment);
	

	Document addProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
			double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax);
	
	
	void updateProductionCostEvent(EventPeriod period, String pscId, double currentYearCapex, double amortizedCapex,
			double currentYearOpex, double currentMonthOpex, double costToDate, double educationTax);


	Document addRoyaltyEvent(EventPeriod period, String pscId, double royaltyMonthlyCharge, double royaltyMontlyChargeToDate,	
			double royaltyReceived);
	
	
	void updateRoyaltyEvent(EventPeriod period, String pscId, double royaltyMonthlyCharge, double royaltyMontlyChargeToDate,	
			double royaltyReceived);

	
	Document addTaxOilEvent(EventPeriod period, String pscId, double taxOilMonthlyCharge, double taxOilMontlyChargeToDate,	
			double taxOilReceived, double educationTax);
	
	
	void updateTaxOilEvent(EventPeriod period, String pscId, double taxOilMonthlyCharge, double taxOilMontlyChargeToDate,	
	double taxOilReceived, double educationTax);
	
	
	Document addCostOilEvent(EventPeriod period, String pscId, double costOilMonthlyCharge, double costOilMontlyChargeToDate,	
			double costOilReceived);
	
	
	void updateCostOilEvent(EventPeriod period, String pscId, double costOilMonthlyCharge, double costOilMontlyChargeToDate,	
			double costOilReceived);
	
	
	Optional<Document> profitOilEventOfPeriod(EventPeriod period, String pscId);

}

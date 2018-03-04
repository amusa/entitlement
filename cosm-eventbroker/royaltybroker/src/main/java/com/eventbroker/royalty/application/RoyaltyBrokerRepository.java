package com.eventbroker.royalty.application;

import java.util.Optional;

import org.bson.Document;

import com.cosm.common.event.EventPeriod;

public interface RoyaltyBrokerRepository {

	Document addLiftingEvent(EventPeriod period, String pscId, double corpProceed, double weightedAvePrice, double cashPayment);

	
	Document addProductionEvent(EventPeriod period, String pscId, double grossProduction);

	
	void updateLiftingEvent(EventPeriod period, String pscId, double corpProceed, double weightedAvePrice, double cashPayment);

	
	void updateProductionEvent(EventPeriod period, String pscId, double grossProduction);

	
	Optional<Document> royaltyEventOfPeriod(EventPeriod period, String pscId);

}

package com.eventbroker.taxoil.application;


import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;

public interface TaxOilBrokerService {

	 void when(RoyaltyDue event);

	 void when(CostPosted event);

	 void when(LiftingPosted event);
}

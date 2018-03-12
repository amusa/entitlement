package com.eventbroker.profitoil.application;


import com.cosm.common.event.CostOilDue;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.TaxOilDue;

public interface ProfitOilBrokerService {

	 void when(RoyaltyDue event);
	 
	 void when(CostOilDue event);
	 
	 void when(TaxOilDue event);

	 void when(CostPosted event);

	 void when(LiftingPosted event);
}

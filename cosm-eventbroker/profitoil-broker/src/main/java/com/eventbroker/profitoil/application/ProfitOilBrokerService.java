package com.eventbroker.profitoil.application;


import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;

public interface ProfitOilBrokerService {

	 void when(ProductionPosted event);

	 void when(CostPosted event);

	 void when(LiftingPosted event);
}

package com.eventbroker.costoil.application;


import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;

public interface CostOilBrokerService {

	 void when(ProductionPosted event);

	 void when(CostPosted event);

	 void when(LiftingPosted event);
}

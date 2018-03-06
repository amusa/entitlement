package com.cosm.psc.entitlement.costoil.application;


import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.TaxOilDue;

public interface CostOilBrokerService {

	 void when(TaxOilDue event);

	 void when(CostPosted event);

	 void when(LiftingPosted event);
}

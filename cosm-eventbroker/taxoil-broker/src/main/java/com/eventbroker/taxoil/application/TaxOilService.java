package com.eventbroker.taxoil.application;


import com.cosm.common.event.CostPosted;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.RoyaltyDue;

public interface TaxOilService {

	 void when(RoyaltyDue event);

	 void when(CostPosted event);

	 void when(LiftingPosted event);
}

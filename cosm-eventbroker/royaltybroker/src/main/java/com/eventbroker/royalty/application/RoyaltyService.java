package com.eventbroker.royalty.application;


import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.ProductionPosted;

public interface RoyaltyService {

	 void when(ProductionPosted event);


	 void when(LiftingPosted event);
}

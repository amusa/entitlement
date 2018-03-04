package com.cosm.psc.production.application.internal;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.ProductionPosted;
import com.cosm.common.event.kafka.EventProducer;
import com.cosm.psc.production.application.ProductionService;
import com.cosm.psc.production.domain.model.ProductionRepository;

public class DefaultProductionService implements ProductionService {

	@Inject
	EventProducer eventProducer;

	@Inject
	Logger logger;

	@Inject
	ProductionRepository productionRepository;

	@Override
	public void post(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {

		double grossProduction = productionRepository.grossProductionOfPeriod(fiscalPeriod, pscId);

		publish(new ProductionPosted(FiscalPeriodAdapter.toEventPeriod(fiscalPeriod), pscId.toString(), grossProduction)

		);

	}

	private void publish(ProductionPosted productionEvent) {

		eventProducer.publish(productionEvent);

	}

}

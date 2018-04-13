package com.cosm.account.application.internal;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.cosm.account.application.ProductionCostService;
import com.cosm.account.domain.model.ProductionCostRepository;
import com.cosm.account.event.kafka.EventProducer;
import com.cosm.account.CosmLogger;
import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.event.CostPosted;
import com.cosm.common.event.FiscalPeriodAdapter;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class DefaultProductionCostService implements ProductionCostService {

	@Inject
	EventProducer eventProducer;

        @CosmLogger
	@Inject
	Logger logger;

	@Inject
	ProductionCostRepository costRepository;

	@Override
	public void post(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {

		double currentYearCapex = costRepository.capexOfYearToMonth(fiscalPeriod, pscId); 
		double amortizedCapex = costRepository.capitalAllowanceRecovery(fiscalPeriod, pscId);
		double currentYearOpex = costRepository.opexOfYearToMonth(fiscalPeriod, pscId);
		double currentMonthOpex = costRepository.opexOfPeriod(fiscalPeriod, pscId);
		double costToDate = costRepository.costToDate(fiscalPeriod, pscId);
		double educationTax = costRepository.educationTaxOfCost(fiscalPeriod, pscId);
	

		publish(new CostPosted(FiscalPeriodAdapter.toEventPeriod(fiscalPeriod), pscId.getId().toString(), 
				currentYearCapex, amortizedCapex, currentYearOpex, currentMonthOpex, costToDate, educationTax )

		);

	}

	private void publish(CostPosted productionCostEvent) {

		eventProducer.publish(productionCostEvent);

	}

}

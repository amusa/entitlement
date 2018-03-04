package com.cosm.shipping.lifting.application.internal;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.LiftingPosted;
import com.cosm.common.event.kafka.EventProducer;
import com.cosm.shipping.lifting.application.LiftingService;
import com.cosm.shipping.lifting.domain.model.LiftingRepository;

public class DefaultLiftingService implements LiftingService {

	@Inject
	EventProducer eventProducer;

	@Inject
	Logger logger;

	@Inject
	LiftingRepository liftingRepository;

	@Override
	public void post(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId) {
		double corpProceed = liftingRepository.corporationProceed(fiscalPeriod, pscId);
		double cashPayment = liftingRepository.cashPayment(fiscalPeriod, pscId);
		double weightedAvePrice = liftingRepository.weightedAveragePrice(fiscalPeriod, pscId);
		double contProceed = liftingRepository.contractorProceed(fiscalPeriod, pscId);
		double grossIncome = liftingRepository.grossIncome(fiscalPeriod, pscId);
		double monthlyIncome = liftingRepository.monthlyIncome(fiscalPeriod, pscId);

		publish(new LiftingPosted(FiscalPeriodAdapter.toEventPeriod(fiscalPeriod), pscId.toString(), grossIncome,
				monthlyIncome, corpProceed, contProceed, weightedAvePrice, cashPayment)

		);

	}

	private void publish(LiftingPosted liftingEv) {

		eventProducer.publish(liftingEv);

	}

}

package com.cosm.psc.entitlement.royalty.application.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.RoyaltyReady;
import com.cosm.common.util.DateUtil;
import com.cosm.psc.entitlement.royalty.application.ProductionSharingContractClientService;
import com.cosm.psc.entitlement.royalty.application.RoyaltyAllocationBuilder;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyAllocation;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyService;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyView;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyViewId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyViewRepository;
import com.cosm.psc.entitlement.royalty.event.kafka.EventProducer;


/**
 * Created by Ayemi on 23/02/2018.
 */

@Stateless
public class DefaultRoyaltyService implements RoyaltyService {

	@Inject
	private FiscalPeriodService fiscalService;

	@Inject
	private ProductionSharingContractClientService pscRest;

	@Inject
	private RoyaltyViewRepository royaltyRepository;

	@Inject
	EventProducer eventProducer;
	

	@Override
	public void when(RoyaltyReady event) {
		RoyaltyAllocation royAllocation = royaltyAllocation(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()),
				new ProductionSharingContractId(event.getPscId()), event.getGrossProduction(),
				event.getCorporationProceed(), event.getCashPayment(), event.getWeightedAveragePrice());

		RoyaltyViewId royVwId = royaltyRepository.nextRoyaltyViewId();
		RoyaltyView royaltyView = new RoyaltyView(royVwId, royAllocation);

		royaltyRepository.store(royaltyView);
		
		RoyaltyDue.Builder builder  = new RoyaltyDue.Builder();
		
		RoyaltyDue royaltyDueEvent = builder.withPeriod(event.getEventPeriod())
				.withContract(event.getPscId())
				.withMonthlyCharge(royAllocation.getMonthlyCharge())
				.withMonthlyChargeToDate(royAllocation.getCumMonthlyCharge())
				.withRecieved(royAllocation.getReceived())
				.build();
		
		eventProducer.publish(royaltyDueEvent);


	}


	private RoyaltyAllocation royaltyAllocation(FiscalPeriod fiscalPeriod, ProductionSharingContractId pscId,
			double grossProd, double corpProceed, double cashPayment, double wap) {

		double concessionRental = pscRest.concessionRental(pscId, fiscalPeriod.getYear(), fiscalPeriod.getMonth());
		double royalty = computeRoyalty(fiscalPeriod, grossProd, wap, concessionRental);

		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(fiscalPeriod);
		RoyaltyView prevRoyaltyView = royaltyRepository.royaltyViewOfFiscalPeriod(prevFp, pscId);
		double prevRoyaltyToDate = prevRoyaltyView.getRoyaltyToDate();
		double royaltyToDate = prevRoyaltyToDate + royalty;
		double royaltyBfw = prevRoyaltyView.getAllocation().getRoyaltyCarriedForward();

		RoyaltyAllocationBuilder royBuilder = new RoyaltyAllocationBuilder();

		return (RoyaltyAllocation) royBuilder
				.withCashPayment(cashPayment)
				.withChargeBfw(royaltyBfw)
				.withLiftingProceed(corpProceed)
				.withMonthlyCharge(royalty)
				.withPreviouseCumMontlyCharge(prevRoyaltyToDate)
				.withFiscalPeriod(fiscalPeriod)
				.withContractId(pscId)
				.build();

	}

	private double computeRoyalty(FiscalPeriod fiscalPeriod, double grossProd, double weightedAvePrice,
			double concessionRental) {
		double royalty, royRate;

		int days = DateUtil.daysOfMonth(fiscalPeriod.getYear(), fiscalPeriod.getMonth());
		Double dailyProd = grossProd / days;
		royRate = computeRoyaltyRate(dailyProd);

		royalty = (grossProd * (royRate / 100) * weightedAvePrice) + concessionRental;

		return royalty;
	}

	private double computeRoyaltyRate(double dailyProd) {
		if (dailyProd < 2000.0) {
			return 5.0;
		} else if (dailyProd >= 2000.0 && dailyProd < 5000.0) {
			return 7.5;
		} else if (dailyProd >= 5000.0 && dailyProd < 10000.0) {
			return 15.0;
		}
		return 20.0;

	}

	

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.entitlement.taxoil.application.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.TaxOilDue;
import com.cosm.common.event.TaxOilReady;
import com.cosm.psc.entitlement.taxoil.application.TaxOilService;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilCalculator;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilProjection;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilProjectionId;
import com.cosm.psc.entitlement.taxoil.domain.model.TaxOilProjectionRepository;
import com.cosm.psc.entitlement.taxoil.event.kafka.EventProducer;



@ApplicationScoped
public class DefaultTaxOilService implements TaxOilService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private FiscalPeriodService fiscalService;
	
	@Inject
	private TaxOilProjectionRepository taxOilRepository;
	
	@Inject
	EventProducer eventProducer;
	
	@Override
	public void when(TaxOilReady event) {
		FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()));
		TaxOilProjection prevTaxOilProj = taxOilRepository.taxOilProjectionOfFiscalPeriod(prevFp, new ProductionSharingContractId(event.getPscId()));
		
		TaxOilCalculator.Builder taxOilCalculatorBuilder = new TaxOilCalculator.Builder();
		
				
		taxOilCalculatorBuilder.withFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()))
		.withContractId(new ProductionSharingContractId(event.getPscId()))
		.withGrossIncome(event.getGrossIncome())
		.withOpex(event.getCurrentYearOpex())
		.withCurrentYearCapex(event.getCurrentYearCapex())
		.withCorporationProceed(event.getCorporationProceed())
		.withRoyalty(event.getRoyalty())
		.withCurrentCapitalAllowance(event.getAmortizedCapex())
		.withEducationTax(event.getEducationTax())
		.withEducationTaxRate(0)
		.withInvestmentTaxAllowanceRate(0)
		.withPetroleumProtitTaxRate(0)
		.withLossBfw(0)
		.withPriorTaxOilProjection(prevTaxOilProj);

		TaxOilCalculator taxOilCalculator = taxOilCalculatorBuilder.build();
		
		TaxOilProjectionId taxOilProjectionId = taxOilRepository.nextTaxOilProjectionId();
		
		TaxOilProjection taxOilProjection = new TaxOilProjection(taxOilProjectionId, taxOilCalculator);
		
		taxOilRepository.store(taxOilProjection);
		
				
        TaxOilDue.Builder builder  = new TaxOilDue.Builder();
		
        TaxOilDue taxOilDueEvent = builder.withPeriod(event.getEventPeriod())
				.withContract(event.getPscId())
				.withMonthlyCharge(taxOilProjection.getTaxOil())
				.withMonthlyChargeToDate(taxOilProjection.getTaxOilToDate())
				.withRecieved(taxOilProjection.getAllocation().getTaxOilReceived())
				.withEducationTax(taxOilProjection.getEducationTax())
				.build();
		
				
		eventProducer.publish(taxOilDueEvent);
		
	}

	

}

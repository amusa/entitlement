package com.cosm.psc.entitlement.profitoil.application.internal;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.ProfitOilDue;
import com.cosm.common.event.ProfitOilReady;
import com.cosm.psc.entitlement.profitoil.application.ProfitOilService;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilProjection;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilProjectionId;
import com.cosm.psc.entitlement.profitoil.domain.model.ProfitOilProjectionRepository;
import com.cosm.psc.entitlement.profitoil.event.kafka.EventProducer;

/**
 * Created by Ayemi on 20/02/2018.
 */
@ApplicationScoped
public class DefaultProfitOilService implements ProfitOilService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			
	@Inject
    private FiscalPeriodService fiscalService;
	
	@Inject
	private ProfitOilProjectionRepository profitOilRepository;
	
	@Inject
	EventProducer eventProducer;
    
    @Override
	public void when(ProfitOilReady event) {
    	FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()));
    	
    	ProfitOilProjection prevProfitOilProjection = profitOilRepository.profitOilProjectionOfFiscalPeriod(prevFp, new ProductionSharingContractId(event.getPscId()));
		
    	ProfitOilProjectionId id = profitOilRepository.nextId();
				
				
    	ProfitOilProjection.Builder builder = new ProfitOilProjection.Builder();
    	
		builder.withId(id)
		.withFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()))
		.withContractId(new ProductionSharingContractId(event.getPscId()))
		.withCostToDate(event.getCostToDate())
		.withMonthlyIncome(event.getMonthlyIncome())	
		.withContractorProceed(event.getContractorProceed())
		.withCorporationProceed(event.getCorporationProceed())			
		.withRoyaltyCharge(event.getRoyaltyMonthlyCharge())
		.withRoyaltyChargeToDate(event.getRoyaltyMontlyChargeToDate())
		.withRoyaltyReceived(event.getRoyaltyReceived())
		.withCostOilCharge(event.getCostOilMonthlyCharge())
		.withCostOilChargeToDate(event.getCostOilMonthlyChargeToDate())
		.withCostOilReceived(event.getCostOilReceived())
		.withTaxOilCharge(event.getTaxOilMonthlyCharge())
		.withTaxOilChargeToDate(event.getTaxOilMonthlyChargeToDate())
		.withTaxOilReceived(event.getTaxOilReceived())
		.withPriorProjection(Optional.of(prevProfitOilProjection));
		
		ProfitOilProjection profitOilProjection = builder.build();
				
		profitOilRepository.store(profitOilProjection);
						
        ProfitOilDue.Builder eventBuilder  = new ProfitOilDue.Builder();
		
    	
        ProfitOilDue profitOilDueEvent = eventBuilder.withPeriod(event.getEventPeriod())
				.withContract(event.getPscId())
				.withProfitOil(profitOilProjection.profitOil())
				.withCorporationCharge(profitOilProjection.corporationAllocation().get().getMonthlyCharge())
				.withCorporationChargeToDate(profitOilProjection.corporationAllocation().get().getMonthlyChargeToDate())
				.withCorporationCfw(profitOilProjection.corporationAllocation().get().getProfitOilCarriedForward())
				.withCorporationBfw(profitOilProjection.corporationAllocation().get().getProfitOilBroughtForward())
				.withCorporationRecieved(profitOilProjection.corporationAllocation().get().getProfitOilReceived())
				
				.withContractorCharge(profitOilProjection.contractorAllocation().get().getMonthlyCharge())
				.withContractorChargeToDate(profitOilProjection.contractorAllocation().get().getMonthlyChargeToDate())
				.withContractorCfw(profitOilProjection.contractorAllocation().get().getProfitOilCarriedForward())
				.withContractorBfw(profitOilProjection.contractorAllocation().get().getProfitOilBroughtForward())
				.withContractorRecieved(profitOilProjection.contractorAllocation().get().getProfitOilReceived())
				.build();
		
				
		eventProducer.publish(profitOilDueEvent);
		
		
	}   

	
}

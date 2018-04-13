package com.cosm.psc.entitlement.royalty.application.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.common.event.RoyaltyDue;
import com.cosm.common.event.RoyaltyReady;
import com.cosm.psc.entitlement.royalty.application.ProductionSharingContractClientService;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyCalculator;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyProjection;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyProjectionId;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyProjectionRepository;
import com.cosm.psc.entitlement.royalty.domain.model.RoyaltyService;
import com.cosm.psc.entitlement.royalty.event.kafka.EventProducer;
import java.util.Optional;

/**
 * Created by Ayemi on 23/02/2018.
 */
@ApplicationScoped
public class DefaultRoyaltyService implements RoyaltyService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    private ProductionSharingContractClientService pscRest;

    @Inject
    private RoyaltyProjectionRepository royaltyRepository;

    @Inject
    EventProducer eventProducer;

    @Override
    public void when(RoyaltyReady event) {

        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()));
        Optional<RoyaltyProjection> priorRoyaltyProjection = royaltyRepository.royaltyProjectionOfPeriod(prevFp, new ProductionSharingContractId(event.getPscId()));

        ProductionSharingContractId pscId = new ProductionSharingContractId(event.getPscId());
        double concessionRental = pscRest.concessionRental(pscId, event.getEventPeriod().getYear(), event.getEventPeriod().getMonth());

        RoyaltyCalculator.Builder royaltyCalculatorBuilder = new RoyaltyCalculator.Builder();

        royaltyCalculatorBuilder.withPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()))
                .withContractId(new ProductionSharingContractId(event.getPscId()))
                .withGrossProduction(event.getGrossProduction())
                .withWeightedAveragePrice(event.getWeightedAveragePrice())
                .withProceed(event.getCorporationProceed())
                .withCashPayment(event.getCashPayment())
                .withConcessionRental(concessionRental)
                .withPriorRoyaltyProjection(priorRoyaltyProjection);

        RoyaltyCalculator royaltyCalculator = royaltyCalculatorBuilder.build();

        RoyaltyProjectionId royaltyProjectionId = royaltyRepository.nextId();

        RoyaltyProjection royaltyProjection = new RoyaltyProjection(royaltyProjectionId, royaltyCalculator);

        royaltyRepository.store(royaltyProjection);

        RoyaltyDue.Builder builder = new RoyaltyDue.Builder();

        RoyaltyDue royaltyDueEvent = builder.withPeriod(event.getEventPeriod())
                .withContract(event.getPscId())
                .withMonthlyCharge(royaltyProjection.getRoyalty())
                .withMonthlyChargeToDate(royaltyProjection.getRoyaltyToDate())
                .withRecieved(royaltyProjection.getAllocation().getRoyaltyReceived())
                .build();

        eventProducer.publish(royaltyDueEvent);

    }

}

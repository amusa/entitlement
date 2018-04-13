package com.cosm.psc.entitlement.costoil.application.internal;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.cosm.common.domain.model.FiscalPeriod;
import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.service.FiscalPeriodService;
import com.cosm.common.event.CostOilDue;
import com.cosm.common.event.CostOilReady;
import com.cosm.common.event.FiscalPeriodAdapter;
import com.cosm.psc.entitlement.costoil.application.CostOilService;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilProjection;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilProjectionId;
import com.cosm.psc.entitlement.costoil.domain.model.CostOilProjectionRepository;
import com.cosm.psc.entitlement.costoil.event.kafka.EventProducer;

/**
 * Created by Ayemi on 20/02/2018.
 */
@ApplicationScoped
public class DefaultCostOilService implements CostOilService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private CostOilProjectionRepository costOilRepository;

    @Inject
    private FiscalPeriodService fiscalService;

    @Inject
    EventProducer eventProducer;

    @Override
    public void when(CostOilReady event) {
        FiscalPeriod prevFp = fiscalService.getPreviousFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()));
        Optional<CostOilProjection> prevCostOilProj = costOilRepository.costOilProjectionOfPeriod(prevFp, new ProductionSharingContractId(event.getPscId()));

        CostOilProjectionId id = costOilRepository.nextCostOilProjectionId();

        CostOilProjection.Builder builder = new CostOilProjection.Builder();

        builder.withId(id)
                .withFiscalPeriod(FiscalPeriodAdapter.toFiscalPeriod(event.getEventPeriod()))
                .withContractId(new ProductionSharingContractId(event.getPscId()))
                .withAmortizedCapex(event.getAmortizedCapex())
                .withOpex(event.getCurrentMonthOpex())
                .withEducationTax(event.getEducationTax())
                .withMonthlyIncome(event.getMonthlyIncome())
                .withContractorProceed(event.getContractorProceed())
                .withCostRecoveryLimit(0)
                .withPriorCostOilProjection(prevCostOilProj);

        CostOilProjection costOilProjection = builder.build();

        costOilRepository.store(costOilProjection);

        CostOilDue.Builder eventBuilder = new CostOilDue.Builder();

        CostOilDue costOilDueEvent = eventBuilder.withPeriod(event.getEventPeriod())
                .withContract(event.getPscId())
                .withMonthlyCharge(costOilProjection.getMonthlyCurrentCharge())
                .withMonthlyChargeToDate(costOilProjection.getMonthlyChargeToDate())
                .withRecieved(costOilProjection.getAllocation().getCostOilReceived())
                .build();

        eventProducer.publish(costOilDueEvent);

    }
}

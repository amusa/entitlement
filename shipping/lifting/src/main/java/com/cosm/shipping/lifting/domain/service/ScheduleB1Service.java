package com.cosm.shipping.lifting.domain.service;


import java.io.Serializable;
import java.util.List;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.shipping.lifting.domain.model.LiftingSummary;
import com.cosm.shipping.lifting.domain.model.ProceedAllocation;

/**
 * Created by Ayemi on 21/02/2018.
 */
public interface ScheduleB1Service extends Serializable {
    List<ProceedAllocation> computeProceedAllocation(ProductionSharingContractId pscId, int year, int month);

    List<LiftingSummary> getLiftingSummary(ProductionSharingContractId pscId, int year, int month);
}

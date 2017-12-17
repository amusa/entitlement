package com.cosm.psc.domain.service;

import com.cosm.psc.domain.model.LiftingSummary;
import com.cosm.psc.domain.model.ProceedAllocation;
import com.cosm.psc.domain.model.account.ProductionSharingContract;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ayemi on 23/03/2017.
 */
public interface ScheduleB1Service extends Serializable {
    List<ProceedAllocation> computeProceedAllocation(ProductionSharingContract psc, int year, int month);

    List<LiftingSummary> getLiftingSummary(ProductionSharingContract psc, int year, int month);
}

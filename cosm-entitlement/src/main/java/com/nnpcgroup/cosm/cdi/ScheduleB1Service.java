package com.nnpcgroup.cosm.cdi;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.report.schb1.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ayemi on 23/03/2017.
 */
public interface ScheduleB1Service extends Serializable {
    List<ProceedAllocation> computeProceedAllocation(ProductionSharingContract psc, int year, int month);

    List<LiftingSummary> getLiftingSummary(ProductionSharingContract psc, int year, int month);
}

package com.cosm.shipping.lifting.domain.model;

import com.cosm.common.domain.model.ProductionSharingContractId;
import com.cosm.common.domain.repository.QueryRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by maliska on 12/16/17.
 */
public interface LiftingQueryRepository extends QueryRepository<Lifting> {

    List<Lifting> find(Date from, Date to);

    List<Lifting> find(ProductionSharingContractId pscId, Date from, Date to);

    List<Lifting> find(ProductionSharingContractId pscId, int year, int month);

    double computeWeightedAvePrice(ProductionSharingContractId pscId, int year, int month);

    double getCorporationProceed(ProductionSharingContractId pscId, int year, int month);

    double getContractorProceed(ProductionSharingContractId pscId, int year, int month);

    double getMonthlyIncome(ProductionSharingContractId pscId, int year, int month);

    double getProceedToDate(ProductionSharingContractId pscId, int year, int month);

    double getGrossIncome(ProductionSharingContractId pscId, int year, int month);

    double getCashPayment(ProductionSharingContractId pscId, int year, int month);
}

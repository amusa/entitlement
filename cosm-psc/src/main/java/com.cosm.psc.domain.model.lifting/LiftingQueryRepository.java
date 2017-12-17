package com.cosm.psc.domain.model.lifting;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.psc.domain.model.account.ProductionSharingContract;

import java.util.Date;
import java.util.List;

/**
 * Created by maliska on 12/16/17.
 */
public interface LiftingQueryRepository extends QueryRepository<Lifting> {

    List<Lifting> find(Date from, Date to);

    List<Lifting> find(ProductionSharingContract psc, Date from, Date to);

    List<Lifting> find(ProductionSharingContract psc, int year, int month);

    double computeWeightedAvePrice(ProductionSharingContract psc, int year, int month);

    double getCorporationProceed(ProductionSharingContract psc, int year, int month);

    double getContractorProceed(ProductionSharingContract psc, int year, int month);

    double getMonthlyIncome(ProductionSharingContract psc, int year, int month);

    double getProceedToDate(ProductionSharingContract psc, int year, int month);

    double getGrossIncome(ProductionSharingContract psc, int year, int month);

    double getCashPayment(ProductionSharingContract psc, int year, int month);
}

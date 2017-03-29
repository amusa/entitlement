/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting;

import com.nnpcgroup.cosm.entity.ProductionSharingContract;
import com.nnpcgroup.cosm.entity.lifting.PscLifting;

import java.util.Date;
import java.util.List;

/**
 * @author 18359
 */
public interface PscLiftingServices extends LiftingServices<PscLifting> {

    List<PscLifting> find(ProductionSharingContract psc, Date from, Date to);

    List<PscLifting> find(ProductionSharingContract psc, int year, int month);

    double computeWeightedAvePrice(ProductionSharingContract psc, int year, int month);

    double getCorporationProceed(ProductionSharingContract psc, int year, int month);

    double getContractorProceed(ProductionSharingContract psc, int year, int month);

    double getMonthlyIncome(ProductionSharingContract psc, int year, int month);

    double getGrossIncome(ProductionSharingContract psc, int year, int month);
}

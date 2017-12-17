/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.psc.domain.service;


import com.cosm.psc.domain.model.Allocation;
import com.cosm.psc.domain.model.account.ProductionSharingContract;

import java.io.Serializable;

/**
 * @author 18359
 */
public interface RoyaltyService extends Serializable {

    double computeRoyalty(ProductionSharingContract psc, int year, int month);

    double computeRoyaltyCum(ProductionSharingContract psc, int year, int month);

    Allocation computeRoyaltyAllocation(ProductionSharingContract psc, int year, int month);

    Allocation computePreviousAllocation(ProductionSharingContract psc, int year, int month);

    double computeMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeCumMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeReceived(ProductionSharingContract psc, int year, int month);

}

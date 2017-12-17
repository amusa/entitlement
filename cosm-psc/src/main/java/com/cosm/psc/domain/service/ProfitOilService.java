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
public interface ProfitOilService extends Serializable {

    double computeRFactor(ProductionSharingContract psc, int year, int month);

    Allocation computeCorporationProfitOilAllocation(ProductionSharingContract psc, int year, int month);

    Allocation computeContractorProfitOilAllocation(ProductionSharingContract psc, int year, int month);

    double computeProfitOil(ProductionSharingContract psc, int year, int month);

    double computeContractorMonthlyCharge(ProductionSharingContract psc, int year, int month);

    double computeCumContractorMonthlyCharge(ProductionSharingContract psc, int year, int month);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv;

import com.nnpcgroup.cosm.entity.FiscalArrangement;
import com.nnpcgroup.cosm.entity.production.jv.JvProduction;

/**
 *
 * @author 18359
 */
public interface JvProductionServices extends ProductionServices<JvProduction> {

    public JvProduction findByContractPeriod(int year, int month, FiscalArrangement fa);

}

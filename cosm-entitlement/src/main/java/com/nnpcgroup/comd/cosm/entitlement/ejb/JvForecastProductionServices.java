/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.JvForecastProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Terminal;
import java.util.List;

/**
 *
 * @author 18359
 */
public interface JvForecastProductionServices extends JvProductionServices<JvForecastProduction> {
    public JvForecastProduction enrich(JvForecastProduction production);
    public List<JvForecastProduction> getTerminalProduction(int year, int month, Terminal terminal);
    
}

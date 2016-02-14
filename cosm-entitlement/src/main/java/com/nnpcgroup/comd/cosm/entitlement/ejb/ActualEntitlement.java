/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.ejb;

import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public interface ActualEntitlement {

    public Production computeAdjustedEntitlement(Production production);

    public Production computeTerminalLosses(Production production);

}

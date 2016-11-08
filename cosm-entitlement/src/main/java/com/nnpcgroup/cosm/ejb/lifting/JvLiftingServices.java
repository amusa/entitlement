/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.lifting;

import com.nnpcgroup.cosm.entity.contract.Contract;
import com.nnpcgroup.cosm.entity.lifting.JvLifting;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 18359
 */
public interface JvLiftingServices extends LiftingServices<JvLifting> {

    List<JvLifting> find(Contract contract, Date from, Date to);
    
}

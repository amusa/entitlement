/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.ejb.production.jv.JvProductionDetailServices;
import java.util.logging.Logger;
import com.nnpcgroup.cosm.entity.contract.JvContract;
import com.nnpcgroup.cosm.entity.production.jv.JvProductionDetail;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author 18359
 */
@Stateless
@Local(JvProductionDetailServices.class)
public class JvProductionDetailBean extends JvProductionDetailServicesImpl<JvProductionDetail, JvContract> implements JvProductionDetailServices<JvProductionDetail, JvContract> {

    private static final Logger LOG = Logger.getLogger(JvProductionDetailBean.class.getName());

    public JvProductionDetailBean() {
        super(JvProductionDetail.class);
    }

    @Override
    public JvProductionDetail openingStockChanged(JvProductionDetail production) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.ejb.production.jv.impl;

import com.nnpcgroup.cosm.entity.contract.AlternativeFundingContract;
import com.nnpcgroup.cosm.entity.production.jv.AlternativeFundingProductionDetail;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.Stateless;
import com.nnpcgroup.cosm.ejb.production.jv.AlternativeFundingProductionDetailServices;

/**
 *
 * @author 18359
 */
@Stateless
@Local(AlternativeFundingProductionDetailServices.class)
public class AlternativeFundingProductionDetailBean extends AlternativeFundingProductionDetailServicesImpl<AlternativeFundingProductionDetail, AlternativeFundingContract> implements AlternativeFundingProductionDetailServices<AlternativeFundingProductionDetail, AlternativeFundingContract> {

    private static final Logger LOG = Logger.getLogger(AlternativeFundingProductionDetailBean.class.getName());

    public AlternativeFundingProductionDetailBean() {
        super(AlternativeFundingProductionDetail.class);
    }

    public AlternativeFundingProductionDetailBean(Class<AlternativeFundingProductionDetail> entityClass) {
        super(entityClass);
    }

    //@Override
//    public AlternativeFundingProductionDetail createInstance() {
//        return new AlternativeFundingProductionDetail();
//    }

}

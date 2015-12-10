/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.ejb.ProductionBean;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.util.JV;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author 18359
 */
@Named(value = "prodController")
@SessionScoped
public class ProductionController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;

    @EJB
    @JV
    private ProductionBean productionBean;
   
    private Production production;

    /**
     * Creates a new instance of JvController
     */
    public ProductionController() {
    }

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }
    
    
    
    

}

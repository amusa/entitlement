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
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author 18359
 */
@Named(value = "prodController")
@RequestScoped
public class ProductionController implements Serializable {

    @Inject
    @JV
    private ProductionBean productionBean;

    private static final long serialVersionUID = -7596150432081506756L;

    
    private Production production;
    private List<Production>productions;
    private static final Logger log = Logger.getLogger(ProductionController.class.getName());

    /**
     * Creates a new instance of JvController
     */
    public ProductionController() {
        log.info("ProductionController::constructor activated...");
        
      // log.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
        
    }

    public Production getProduction() {
        log.info("ProductionController::getProduction called...");
        return production;
    }

    public void setProduction(Production production) {
        log.info("ProductionController::setProduction called...");
        this.production = production;
        
    }

    public List<Production> getProductions() {
        log.info("ProductionController::getProductions called...");
        if (productions == null){
            productions = productionBean.findAll();
        }
        return productions;
    }

    public void setProductions(List<Production> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    @PostConstruct
    public void postConstruct(){
        production = productionBean.createProductionEntity();
    }
    
    public Double getEntitlement(){
        return productionBean.calculateEntitlement();
    }
    

}

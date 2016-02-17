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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

/**
 *
 * @author 18359
 */
@Named(value = "prodController")
@RequestScoped
public class ProductionController implements Serializable {

    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(ProductionController.class.getName());

//    @Inject
//    @JV
    @EJB
    private ProductionBean productionBean;

    private Production currentProduction;
    private List<Production> productions;
    private boolean manualEntry = false;

    /**
     * Creates a new instance of JvController
     */
    public ProductionController() {
        log.info("ProductionController::constructor activated...");

        // log.log(Level.INFO, "Entitlement calculated: {0}", entitlement.calculateEntitlement());
    }

    public Production getCurrentProduction() {
        log.info("ProductionController::getProduction called...");
        return currentProduction;
    }

    public void setCurrentProduction(Production currentProduction) {
        log.info("ProductionController::setProduction called...");
        this.currentProduction = currentProduction;
    }

    public List<Production> getProductions() {
        log.info("ProductionController::getProductions called...");
        return productions;
    }

    public void setProductions(List<Production> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("ProductionController::postConstructor initializing production...");
      //  currentProduction = productionBean.createInstance();
    }
    
    public void processManualEntry(AjaxBehaviorEvent event) {
        log.info("ProductionController::processManualEntry called...");
        log.info("*******setting manualEntry to true********");
       // currentProduction = new JvProduction();
        setManualEntry(true);

    }

    public void addProductionItem(AjaxBehaviorEvent event) {
        log.info("ProductionController::addProductionItem called...");
        productions.add(currentProduction);
       // currentProduction = new Production();
        
    }

    public boolean isManualEntry() {
        log.log(Level.INFO, "*******isManualEntry returning {0} ***********", manualEntry);
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

}

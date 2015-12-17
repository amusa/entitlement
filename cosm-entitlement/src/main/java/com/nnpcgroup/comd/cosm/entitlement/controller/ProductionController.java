/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.ejb.ProductionBean;
import com.nnpcgroup.comd.cosm.entitlement.entity.JvProduction;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.util.JV;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
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

    @Inject
    @JV
    private ProductionBean productionBean;

    private Production production;
    private List<Production> productions;
    private boolean manualEntry = false;

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
        return productions;
    }

    public void setProductions(List<Production> productions) {
        log.info("ProductionController::setProductions called...");
        this.productions = productions;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("ProductionController::postConstructor initializing production...");
        production = productionBean.createProductionEntity();
    }

    public Double getEntitlement() {
        return productionBean.calculateEntitlement();
    }

    public void processManualEntry(AjaxBehaviorEvent event) {
        log.info("ProductionController::processManualEntry called...");
        log.info("*******setting manualEntry to true********");
        setManualEntry(true);

    }

    public boolean isManualEntry() {
        log.log(Level.INFO, "*******isManualEntry returning {0} ***********", manualEntry);
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import com.nnpcgroup.comd.cosm.entitlement.ejb.ProductionBean;
import com.nnpcgroup.comd.cosm.entitlement.entity.ContractStream;
import com.nnpcgroup.comd.cosm.entitlement.entity.FiscalArrangement;
import com.nnpcgroup.comd.cosm.entitlement.entity.Production;
import com.nnpcgroup.comd.cosm.entitlement.util.JV;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

/**
 *
 * @author 18359
 */
@Named(value = "jvProdController")
@SessionScoped
public class JvProductionController implements Serializable {
    
    private static final long serialVersionUID = -7596150432081506756L;
    private static final Logger log = Logger.getLogger(JvProductionController.class.getName());
    
    @Inject
    @JV
    private ProductionBean productionBean;
        
    private Production currentProduction;
        
    private List<Production> productions;
    
    private boolean manualEntry = false;
    private FiscalArrangement currentFiscal;
    private int periodYear;
    private int periodMonth;

    /**
     * Creates a new instance of JvController
     */
    public JvProductionController() {
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
    }
           
    public void processManualEntry() {
        log.info("JvProductionController::processManualEntry called...");
        log.info("*******setting manualEntry to true********");
        // currentProduction = new JvProduction();
        currentProduction = productionBean.createInstance();
        productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
        setManualEntry(true);
        
    }
    
    public void loadProductions(){
        productions = productionBean.findByYearAndMonth(periodYear, periodMonth);
    }
            
    public void addProductionItem() {
        log.info("JvProductionController::addProductionItem called...");
        log.log(Level.INFO, "year={0} month={1} contractStream={2} prodVolume={3}",
                new Object[]{currentProduction.getPeriodYear(), currentProduction.getPeriodMonth(), currentProduction.getContractStream(), currentProduction.getProductionVolume()});
        
        if (productions == null) {
            productions = new ArrayList<>(); //TODO:use factory method
        }
        currentProduction.setPeriodYear(periodYear);
        currentProduction.setPeriodMonth(periodMonth);
        
        productionBean.enrich(currentProduction);
        
        productions.add(currentProduction);
        //currentProduction = new JvProduction();
        currentProduction = productionBean.createInstance();
        currentFiscal = null;
        
    }
    
    public boolean isManualEntry() {
        log.log(Level.INFO, "*******isManualEntry returning {0} ***********", manualEntry);
        return manualEntry;
    }
    
    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }
    
    public FiscalArrangement getCurrentFiscal() {
        return currentFiscal;
    }
    
    public void setCurrentFiscal(FiscalArrangement currentFiscal) {
        this.currentFiscal = currentFiscal;
    }
    
    public List<ContractStream> getContractStreamList() {
        log.log(Level.INFO, "getting currentFiscal = {0}...", currentFiscal);
        if (null != currentFiscal) {
            return currentFiscal.getContractStreams();
        }
        return null;
    }
    
    public void fiscalListChanged(AjaxBehaviorEvent event) {
        log.info("jvProductionController::fiscalListChanged called...");
        
    }
    
    public void saveProductions() {
        log.log(Level.INFO, "************jvProductionController::saveProduction called...{0} records to be saved!",
                productions.size());
        productionBean.create(productions);
        reset();
                
        JsfUtil.addSuccessMessage("JV Production Saved Successfully!");
    }
    
    private void reset(){
        productions = null;
        setManualEntry(false);
    }
    public int getPeriodYear() {
        return periodYear;
    }
    
    public void setPeriodYear(int periodYear) {
        log.log(Level.INFO, "************JvProductionController::setPeriodYear called with value {0}",periodYear);
        this.periodYear = periodYear;
    }
    
    public int getPeriodMonth() {
        return periodMonth;
    }
    
    public void setPeriodMonth(int periodMonth) {
        log.log(Level.INFO, "************JvProductionController::setPeriodMonth called with value {0}",periodMonth);
        this.periodMonth = periodMonth;
    }
    
}

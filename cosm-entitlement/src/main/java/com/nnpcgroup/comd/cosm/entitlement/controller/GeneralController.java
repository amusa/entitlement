package com.nnpcgroup.comd.cosm.entitlement.controller;

import com.nnpcgroup.comd.cosm.entitlement.controller.util.JsfUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class GeneralController {

    private static final Logger log = Logger.getLogger(GeneralController.class.getName());

    @Inject
    YearGenerator yearGen;

    @Inject
    MonthGenerator monthGen;

    List<Integer> years;
    List<PeriodMonth> months;

    
    public SelectItem[] getYearsAvailableSelectOne() {
        return JsfUtil.getSelectItems(yearGen.generateYears(5), true);
    }

    public List<Integer> getYears() {
        log.log(Level.INFO, "returning Years...");
        years = yearGen.generateYears(5);
        return years;
    }

    public List<PeriodMonth> getMonths() {
        log.log(Level.INFO, "returning months...");
        return months;
    }

    public SelectItem[] getMonthsAvailableSelectOne() {
        log.log(Level.INFO, "returning getMonthsAvailableSelectOne...");
        return JsfUtil.getSelectItems(months, true);
    }

    public void yearChanged(AjaxBehaviorEvent  event) {
        Integer year = (Integer) ((UIOutput)event.getSource()).getValue();
        log.log(Level.INFO, "YearChanged event fired with value {0}, generating months...",year );
        
        months = monthGen.generateMonths(year);
    }

}

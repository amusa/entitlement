package com.nnpcgroup.cosm.controller;

import com.nnpcgroup.cosm.controller.util.JsfUtil;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class GeneralController implements Serializable {

    private static final Logger log = Logger.getLogger(GeneralController.class.getName());
    private static final long serialVersionUID = 1090003054795280004L;

    @Inject
    YearGenerator yearGen;

    @Inject
    MonthGenerator monthGen;

    List<Integer> years;
    List<PeriodMonth> months;

    public SelectItem[] getYearsAvailableSelectOne() {
        return JsfUtil.getSelectItems(yearGen.generateYears(25), false);
    }

    public List<Integer> getYears() {
        log.log(Level.INFO, "returning Years...");
        years = yearGen.generateYears(25);
        return years;
    }

    public List<PeriodMonth> getMonths() {
        log.log(Level.INFO, "returning months {0}...", months);
        return months;
    }

    public SelectItem[] getMonthsAvailableSelectOne() {
        log.log(Level.INFO, "returning getMonthsAvailableSelectOne...");

        return JsfUtil.getSelectItems(months, true);
    }

    public void yearChanged(AjaxBehaviorEvent event) {
        Integer year = (Integer) ((UIOutput) event.getSource()).getValue();
        log.log(Level.INFO, "YearChanged event fired with value {0}, generating months...", year);
        if (year != null) {
            months = monthGen.generateMonths(year);
        }
    }

    public PeriodMonth getPeriodMonth(Integer month) {
        return monthGen.find(month);
    }

    public Integer daysOfMonth(int year, int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        return yearMonthObject.lengthOfMonth();
    }

//    @FacesConverter(forClass = PeriodMonth.class)
//    public static class PeriodMonthConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            GeneralController controller = (GeneralController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "generalController");
//
//            return controller.getPeriodMonth(getKey(value));
//        }
//
//        java.lang.Integer getKey(String value) {
//            java.lang.Integer key;
//            key = Integer.valueOf(value);
//            return key;
//        }
//
//        String getStringKey(java.lang.Integer value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof PeriodMonth) {
//                PeriodMonth o = (PeriodMonth) object;
//                return getStringKey(o.getMonth());
//            } else {
//                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FiscalArrangement.class.getName());
//            }
//        }
//
//    }
}

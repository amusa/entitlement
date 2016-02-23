/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public class MonthGeneratorImpl implements MonthGenerator {
    
    private static final Logger log = Logger.getLogger(MonthGeneratorImpl.class.getName());
    
    @Override
    public List<PeriodMonth> generateMonths(int year) {
        log.log(Level.INFO, "generateMonths with year {0}", year);
        
        List<PeriodMonth> periodMonths = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int thisYear = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int months = 0;
        if (year < thisYear) {
            months = 12;
        } else if (year == thisYear && month + 2 <= 12) {
            months = month + 2;
        } else if (year == thisYear + 1) {
            months = month + 2 % 12;
        }
        
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] monthSymbols = dfs.getMonths();
        
        for (int i = 1; i <= months; i++) {
            PeriodMonth m = new PeriodMonth();
            m.setMonth(i);
            m.setMonthStr(monthSymbols[i - 1]);
            periodMonths.add(m);
        }
        log.log(Level.INFO, "returning months {0}", periodMonths);
        return periodMonths;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.comd.cosm.entitlement.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 18359
 */
@Dependent
public class YearGeneratorImpl implements YearGenerator, Serializable {

    private static final Logger log = Logger.getLogger(YearGeneratorImpl.class.getName());
    private static final long serialVersionUID = 6722112581626695924L;

    @Override
    public List<Integer> generateYears(int level) {
        log.log(Level.INFO, "generateYears with level {0}", level);
        Calendar c = new GregorianCalendar();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        if (month + 2 > 12) {
            ++year;
            ++level;
        }
        Integer years[] = new Integer[level];
        for (int i = 0; i < level; i++) {
            years[i] = year--;
        }

        List<Integer> yearsArray = Arrays.asList(years);
        
        log.log(Level.INFO, "returning years array {0}", year);
        
        return yearsArray;
    }

}

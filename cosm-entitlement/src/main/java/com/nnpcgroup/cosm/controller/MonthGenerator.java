/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.controller;

import java.util.List;

/**
 *
 * @author 18359
 */

public interface MonthGenerator {
    public List<PeriodMonth> generateMonths(int year);
    public PeriodMonth find(Integer m);
    
}
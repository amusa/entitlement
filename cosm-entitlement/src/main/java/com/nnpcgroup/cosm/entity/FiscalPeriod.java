/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nnpcgroup.cosm.entity;

/**
 *
 * @author 18359
 */
public class FiscalPeriod {
    private int year;
    private int month;

    public FiscalPeriod() {
    }
       
    public FiscalPeriod(int year, int month) {
        this.year = year;
        this.month = month;
    }
        
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    
    
}

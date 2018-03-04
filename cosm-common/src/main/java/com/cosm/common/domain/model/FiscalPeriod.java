/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


/**
 * @author 18359
 */
@Embeddable
public class FiscalPeriod {
    private int year;
    private int month;
    private int refYear;
    
    public FiscalPeriod(int year, int month) {
        setYear(year);
        setMonth( month);
        setRefYear(year);
    }

    @NotNull   
    @Column(name = "PERIOD_YEAR")
    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        this.year = year;
    }

    @NotNull   
    @Column(name = "PERIOD_MONTH")
    public int getMonth() {
        return month;
    }

    private void setMonth(int month) {
        this.month = month;
    }

    @Transient
    public int getRefYear() {
        return refYear;
    }

    private void setRefYear(int refYear) {
        this.refYear = refYear;
    }

   
    public FiscalPeriod getPreviousFiscalPeriod() {
        if (month > 1) {
            --month;
        } else {
            month = 12;
            --year;
        }

        return this;
    }

    
    public FiscalPeriod getNextFiscalPeriod() {
        month = (month % 12) + 1;

        if (year == 1) {
            ++year;
        }

        return this;
    }

    
    public FiscalPeriod getPreviousYearFiscalPeriod() {
        return new FiscalPeriod(year - 1, 12);
    }

    
    public boolean isCurrentYear() {
        return year == refYear;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiscalPeriod other = (FiscalPeriod) obj;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.common.domain.model;

/**
 * @author 18359
 */
public class FiscalYear {
    private int year;
    
    public FiscalYear(int year) {
        setYear(year);       
    }
   
    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        this.year = year;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		FiscalYear other = (FiscalYear) obj;
		if (year != other.year)
			return false;
		return true;
	}
    
	
}

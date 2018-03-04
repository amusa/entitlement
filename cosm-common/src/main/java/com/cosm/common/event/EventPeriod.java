package com.cosm.common.event;

public class EventPeriod {
	
	private int year;
	private int month;
		
	public EventPeriod(int year, int month) {
		super();
		this.year = year;
		this.month = month;
	}

	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
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
		EventPeriod other = (EventPeriod) obj;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	

	
}

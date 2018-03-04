package com.cosm.common.event;

import com.cosm.common.domain.model.FiscalPeriod;

public class FiscalPeriodAdapter {

	public static FiscalPeriod toFiscalPeriod(EventPeriod eventPeriod) {
		return new FiscalPeriod(
				eventPeriod.getYear(),
				eventPeriod.getMonth()
				);
	}
	
	public static EventPeriod toEventPeriod(FiscalPeriod fiscalPeriod) {
		return new EventPeriod(
				fiscalPeriod.getYear(),
				fiscalPeriod.getMonth()
				);
	}
}

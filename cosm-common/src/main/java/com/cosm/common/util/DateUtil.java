package com.cosm.common.util;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ayemi on 21/03/2017.
 */
public class DateUtil {

    public static Period getPeriod(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            LocalDate ld1 = getLocalDate(date1);
            LocalDate ld2 = getLocalDate(date2);

            return Period.between(ld1, ld2);

        }
        return null;
    }

    public static int yearsDiff(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Period diff = getPeriod(date1, date2);
            return diff.getYears();
        }

        return 0;
    }

    public static long secondsDiff(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            LocalDate fromDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate toDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long seconds = ChronoUnit.SECONDS.between(fromDate, toDate);
            return seconds;
        }

        return 0;
    }

    public static long secondsDiff(Instant startTime, Instant finishTime) {
        if (startTime != null && finishTime != null) {
            Duration duration = Duration.between(startTime, finishTime);
            return duration.getSeconds();
        }

        return 0;
    }

    public static LocalDate getLocalDate(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; //MONTH is zero-based
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return LocalDate.of(year, month, day);

    }

    public static Integer daysOfMonth(int year, int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        return yearMonthObject.lengthOfMonth();
    }

    public static int getYearOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }    
    
}

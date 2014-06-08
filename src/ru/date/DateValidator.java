package ru.date;

import org.joda.time.DateTime;

import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateValidator {

    public boolean validateDateOnMask(String date){
        String[] masDate = date.split(Pattern.quote("-"));
        if (masDate.length != 3) return false;
        if (masDate[0].length() != 2) return false;
        if (masDate[1].length() != 2) return false;
        if (masDate[2].length() != 4) return false;
        return true;
    }
	
	public boolean validateDatesOnValidMeans(String date){
        String[] masDate = date.split(Pattern.quote("-"));
        int day = Integer.parseInt(masDate[0]);
        int month = Integer.parseInt(masDate[1]);
        int year = Integer.parseInt(masDate[2]);
		if ((year < 1900) || (year > 3000))
			return false;
		if ((month < 1) || (month > 12))
			return false;
		if ((day < 1) || (day > 31))
			return false;
		if (!validateOnMonthAndDays(year, month, day))
			return false;
		return true;
	}

    public boolean ifDateIsEarlierThenCurrent(String date){
        String[] masDate = date.split(Pattern.quote("-"));
        int day = Integer.parseInt(masDate[0]);
        int month = Integer.parseInt(masDate[1]);
        int year = Integer.parseInt(masDate[2]);
        DateTime dateTime = new DateTime(year,month,day,0,0);
        if (dateTime.isBefore(DateTime.now()))
            return true;
        return false;
    }

    public boolean ifDateIsLaterThenCurrent(String date){
        String[] masDate = date.split(Pattern.quote("-"));
        int day = Integer.parseInt(masDate[0]);
        int month = Integer.parseInt(masDate[1]);
        int year = Integer.parseInt(masDate[2]);
        DateTime dateTime = new DateTime(year,month,day,0,0);
        if (dateTime.isAfter(DateTime.now()))
            return true;
        return false;
    }


	
	private boolean validateOnMonthAndDays(int year, int month, int day){
		switch (month) {
			case 1:
				return true;
			case 2:
				GregorianCalendar calendar = new GregorianCalendar();
				if (calendar.isLeapYear(year)){
					if (day > 29)
						return false;
					else return true;
				} else if (!calendar.isLeapYear(year)){
					if (day > 28)
						return false;
					return true;
				}
			case 3:
				return true;
			case 4:
				return check30DaysMonths(day);
			case 5:
				return true;
			case 6:
				return check30DaysMonths(day);
			case 7:
				return true;
			case 8:
				return true;
			case 9:
				return check30DaysMonths(day);
			case 10:
				return true;
			case 11:
				return check30DaysMonths(day);
			case 12:
				return true;
			default:
				return false;
		}
	}

	private boolean check30DaysMonths(int day){
		if (day > 30)
			return false;
		else return true;
	}
}

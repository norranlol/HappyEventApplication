package ru.date;

import java.util.GregorianCalendar;

public class DateValidator {
	
	public boolean validateDatesOnValidMeans(int year, int month, int day, int hour, int minute){
		if ((year < 2000) || (year > 3000)) 
			return false;
		if ((month < 1) || (month > 12))
			return false;
		if ((day < 1) || (day > 31))
			return false;
		if ((hour < 0) || (hour > 23))
			return false;
		if ((minute < 0) || (minute > 60))
			return false;
		if (!validateOnMonthAndDays(year, month, day))
			return false;
		return true;
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
	
	public boolean validateDatesOnAfter(DateStorer dateStorer) {
		if (!dateStorer.getDateTimeFuture().isAfter(dateStorer.getDateTimeCurrent()))
			return false;
		return true;
	}
	
}

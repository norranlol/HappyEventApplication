package ru.date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

public class DateCalculatorImpl implements DateCalculator {
	
	public DateStorer calculateDifferenceBetweenDates(DateStorer dateStorer){
		
		DateTime dateTime = dateStorer.getDateTimeCurrent();
		DateTime dateTimeFuture = dateStorer.getDateTimeFuture();

		int years = Years.yearsBetween(dateTime.toLocalDateTime(),dateTimeFuture.toLocalDateTime()).getYears();
		DateTime dateTimeFutureNoYears = dateTimeFuture.minusYears(years);
		
		int months = Months.monthsBetween(dateTime.toLocalDateTime(), dateTimeFutureNoYears.toLocalDateTime()).getMonths();
		DateTime dateTimeFutureNoMonths = dateTimeFutureNoYears.minusMonths(months);
		
		int weeks = Weeks.weeksBetween(dateTime.toLocalDateTime(), dateTimeFutureNoMonths.toLocalDateTime()).getWeeks();
		DateTime dateTimeFutureNoWeeks = dateTimeFutureNoMonths.minusWeeks(weeks);
		
		int days = Days.daysBetween(dateTime.toLocalDateTime(), dateTimeFutureNoWeeks.toLocalDateTime()).getDays();
		DateTime dateTimeFutureNoDays = dateTimeFutureNoWeeks.minusDays(days);
		
		int hours = Hours.hoursBetween(dateTime.toLocalDateTime(), dateTimeFutureNoDays.toLocalDateTime()).getHours();
		DateTime dateTimeFutureNoHours = dateTimeFutureNoDays.minusHours(hours);
		
		int minutes = Minutes.minutesBetween(dateTime.toLocalDateTime(), dateTimeFutureNoHours.toLocalDateTime()).getMinutes();
		
		DateDifference dateDifference = new DateDifference(years, months, weeks, days, hours, minutes);
		dateStorer.setDateDifference(dateDifference);
		return dateStorer;
	}
}

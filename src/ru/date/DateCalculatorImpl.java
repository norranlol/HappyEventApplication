package ru.date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

import java.util.regex.Pattern;

public class DateCalculatorImpl implements DateCalculator {

    @Override
    public DateDifference calculateDifferenceBeetweenFutureAndCurrentDates(String date) {
        String[] mas = date.split(Pattern.quote("-"));
        int day = Integer.parseInt(mas[0]);
        int month = Integer.parseInt(mas[1]);
        int year = Integer.parseInt(mas[2]);

        DateTime dateTimeCurrent = DateTime.now();
        DateTime dateTimeEvent = new DateTime(year, month, day, 0, 0);

        int years = Years.yearsBetween(dateTimeCurrent.toLocalDateTime(), dateTimeEvent.toLocalDateTime()).getYears();

        int months = Months.monthsBetween(dateTimeCurrent.toLocalDateTime(), dateTimeEvent.toLocalDateTime()).getMonths();
        months = months % 12;

        int weeks = Weeks.weeksBetween(dateTimeCurrent.toLocalDateTime(), dateTimeEvent.toLocalDateTime()).getWeeks();
        weeks = weeks % 4;

        int days = Days.daysBetween(dateTimeCurrent.toLocalDateTime(), dateTimeEvent.toLocalDateTime()).getDays();
        days = days % 7;

        DateDifference dateDifference = new DateDifference(years, months, weeks, days);
        return dateDifference;
    }
}

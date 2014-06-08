package ru.date;

public class DateDifference {
	
	private int years;
	private int months;
	private int weeks;
	private int days;
	
	public DateDifference(int years, int months, int weeks, int days){
		this.years = years;
		this.months = months;
		this.weeks = weeks;
		this.days = days;
	}

	public DateDifference(){}
	
	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
}

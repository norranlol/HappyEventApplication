package ru.date;

import org.joda.time.DateTime;

public class DateStorer {
	
	private DateTime dateTimeCurrent;
	private DateTime dateTimeFuture;
	private DateDifference dateDifference;
	
	public DateStorer(DateTime dateTimeFuture){
		dateTimeCurrent = new DateTime();
		this.dateTimeFuture = dateTimeFuture;
	}

	public DateTime getDateTimeCurrent() {
		return dateTimeCurrent;
	}

	public DateTime getDateTimeFuture() {
		return dateTimeFuture;
	}

	public DateDifference getDateDifference() {
		return dateDifference;
	}

	public void setDateDifference(DateDifference dateDifference) {
		this.dateDifference = dateDifference;
	}
}

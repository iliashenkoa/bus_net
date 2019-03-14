package com.iliashenko.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that represent's time entity. Methods for converting and pasing date
 * objects.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class Time implements Comparable<Time> {
	public static final ThreadLocal<SimpleDateFormat> FULL_DATE_FORMAT = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyy-MM-dd HH:mm"));
	public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("HH:mm"));

	public static final DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Locale.ENGLISH);
	public static final DateFormat MONTH_FORMAT = new SimpleDateFormat("MM", Locale.ENGLISH);
	public static final DateFormat DAY_FORMAT = new SimpleDateFormat("dd", Locale.ENGLISH);
	public static final DateFormat WEEKDAY_FORMAT = new SimpleDateFormat("EEEE", Locale.ENGLISH);
	public static final DateFormat HOURS_FORMAT = new SimpleDateFormat("HH", Locale.ENGLISH);
	public static final DateFormat MINUTES_FORMAT = new SimpleDateFormat("mm", Locale.ENGLISH);

	public static final String[] WEEKDAYS = Arrays.copyOfRange(new DateFormatSymbols(Locale.ENGLISH).getWeekdays(), 1,
			new DateFormatSymbols(Locale.ENGLISH).getWeekdays().length);

	public static final int MONTHES_IN_YEAR = 12;
	public static final int HOURS_IN_DAY = 24;
	public static final int MINUTES_IN_HOUR = 60;
	public static final int SECONDS_IN_MINUTE = 60;
	public static final int MILLIS_IN_SECOND = 1000;
	public static final int MILLIS_IN_MINUTE = SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
	public static final int MILLIS_IN_HOUR = MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
	public static final int MILLIS_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
	public static final int MILLIS_IN_MONTH = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
	public static final int MILLIS_IN_YEAR = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;

	private int year;
	private int month;
	private int day;
	private String weekday;
	private int hours;
	private int minutes;

	private static final Logger TIME_LOGGER = LoggerFactory.getLogger(Time.class);

	public Time() {

	}

	public Time(String date, ThreadLocal<SimpleDateFormat> dateFormat) {
		Date temp = getTimeFormat(date, dateFormat);
		this.year = getYear(temp);
		this.month = getMonth(temp);
		this.day = getDay(temp);
		this.weekday = getWeekday(temp);
		this.hours = getHours(temp);
		this.minutes = getMinutes(temp);
	}

	private Time(int year, int month, int day, String weekday, int hours, int minutes) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.weekday = weekday;
		this.hours = hours;
		this.minutes = minutes;
	}

	public Time(Time source) {
		this(source.getYear(), source.getMonth(), source.getDay(), source.getWeekday(), source.getHours(),
				source.getMinutes());
	}

	public static Date getTimeFormat(String date, ThreadLocal<SimpleDateFormat> dateFormat) {
		try {
			return dateFormat.get().parse(date);

		} catch (ParseException e) {
			TIME_LOGGER.error("Parse date error: {}", e);
		}
		return null;
	}

	public Date getDate() {
		return getTimeFormat(String.format("%d-%d-%d %d:%d", this.year, this.month, this.day, this.hours, this.minutes),
				FULL_DATE_FORMAT);
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public String getWeekday() {
		return weekday;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public static String getTimeString(Date date) {
		return FULL_DATE_FORMAT.get().format(date);
	}

	public static int getYear(Date date) {
		return Integer.valueOf(YEAR_FORMAT.format(date));
	}

	public static int getMonth(Date date) {
		return Integer.valueOf(MONTH_FORMAT.format(date));
	}

	public static int getDay(Date date) {
		return Integer.valueOf(DAY_FORMAT.format(date));
	}

	public static String getWeekday(Date date) {
		return WEEKDAY_FORMAT.format(date);
	}

	public static int getHours(Date date) {
		return Integer.valueOf(HOURS_FORMAT.format(date));
	}

	public int getMinutes(Date date) {
		return Integer.valueOf(MINUTES_FORMAT.format(date));
	}

	public long getTimeInMs() {
		return (long) hours * MILLIS_IN_HOUR + minutes * MILLIS_IN_MINUTE;

	}

	public int countDayInMonth() {
		LocalDate date = LocalDate.of(this.getYear(), Month.of(this.getMonth()), this.getDay());
		return date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
	}

	public String getNextWeekday() {
		int index = Arrays.asList(WEEKDAYS).indexOf(weekday);
		if (index < WEEKDAYS.length - 1) {
			return WEEKDAYS[index + 1];
		} else {
			return WEEKDAYS[0];
		}
	}

	public boolean isWeekend() {
		return Arrays.asList(WEEKDAYS).indexOf(weekday) == 0
				|| Arrays.asList(WEEKDAYS).indexOf(weekday) == WEEKDAYS.length - 1;
	}

	public void addMinutes(int count) {
		this.setMinutes(this.getMinutes() + count);
		if (this.getMinutes() >= Time.MINUTES_IN_HOUR) {
			this.setMinutes(this.getMinutes() - Time.MINUTES_IN_HOUR);
			this.setHours(this.getHours() + 1);
		}
		if (this.getHours() >= Time.HOURS_IN_DAY) {
			this.setHours(this.getHours() - Time.HOURS_IN_DAY);
			this.setWeekday(this.getNextWeekday());
		}
	}

	@Override
	public String toString() {
		return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + hours;
		result = prime * result + minutes;
		result = prime * result + month;
		result = prime * result + ((weekday == null) ? 0 : weekday.hashCode());
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
		Time other = (Time) obj;
		if (day != other.day)
			return false;
		if (hours != other.hours)
			return false;
		if (minutes != other.minutes)
			return false;
		if (month != other.month)
			return false;
		if (weekday == null) {
			if (other.weekday != null)
				return false;
		} else if (!weekday.equals(other.weekday)) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Time o) {
		return this.getDate().compareTo(o.getDate());
	}

}

package com.iliashenko;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.iliashenko.utils.Time;

public class TimeTest {

	@Test
	public void timeTest() {
		Time test = new Time("1999-01-01 11:45", Time.FULL_DATE_FORMAT);
		assertEquals(1999, test.getYear(), 0);
	}

	@Test
	public void countDayInMonthTest() {
		Time test = new Time("1999-01-01 11:45", Time.FULL_DATE_FORMAT);
		assertEquals(31, test.countDayInMonth(), 0);
	}

	@Test
	public void compareToTest() {
		Time test = new Time("1999-01-01 11:45", Time.FULL_DATE_FORMAT);
		assertEquals(0, test.compareTo(test));
	}
}

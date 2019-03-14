package com.iliashenko;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iliashenko.generation.SheduleGenerator;
import com.iliashenko.utils.Time;

public class SheduleTest {

	@Test
	public void sheduleTest() {

		BusRoute routeTest = new BusRoute.BusRouteBuilder().withNumber(11).withTimeInterval(5).withPrice(5)
				.isRoundabout(true).build();

		List<BusRoute> temp = new ArrayList<>();
		temp.add(routeTest);

		assertEquals(5, routeTest.getTimeInterval());

		BusSheduleRecord recordTest = new BusSheduleRecord(11, Direction.PERMANENT, new Time("11:45", Time.DATE_FORMAT),
				new Time("12:45", Time.DATE_FORMAT));
		BusShedule sheduleTest = new BusShedule();
		sheduleTest.addSheduleRecord(recordTest);

		assertEquals(1, sheduleTest.getSheduleRecords().size());

		sheduleTest = SheduleGenerator.sheduler(sheduleTest, temp);

		assertEquals(true, sheduleTest.getSheduleRecords().size() == 60 / 5 + 1);

	}

}

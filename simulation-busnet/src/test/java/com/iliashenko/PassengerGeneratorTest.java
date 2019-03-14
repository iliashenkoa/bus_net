package com.iliashenko;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.iliashenko.generation.PassengerGenerator;
import com.iliashenko.utils.Time;
import com.iliashenko.utils.XMLHandler;

public class PassengerGeneratorTest {
	@Test
	public void generatePassengerTest() {

		Network testNet = XMLHandler.unmarshallingNetwork(
				"/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/src/main/resources/start_configuration/network.xml",
				"/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/src/main/resources/start_configuration/schema/network.xsd");

		assertEquals(270, testNet.getNetwork().get(0).getNumber(), 0);

		BusStop bs1 = testNet.getNetwork().get(0).getBusStops().get(Direction.PERMANENT).get(0);

		assertEquals(1, bs1.getIdBusStop());

		for (BusRoute route : testNet.getNetwork()) {
			if (route.isRoundabout()) {
				for (BusStop stop : route.getBusStops().get(Direction.PERMANENT)) {
					stop.addToRoute(route);
					stop.initSemaphore();
				}
			} else {
				for (BusStop stop : route.getBusStops().get(Direction.FROM)) {
					stop.addToRoute(route);
					stop.initSemaphore();
				}
				for (BusStop stop : route.getBusStops().get(Direction.INTO)) {
					stop.addToRoute(route);
					stop.initSemaphore();
				}
			}
		}

		PassengerGenerator genTest = new PassengerGenerator(testNet);

		Time t = new Time("1999-01-01 11:45", Time.FULL_DATE_FORMAT);

		Passenger pasTest = genTest.generatePassenger(t);

		assertEquals(t, pasTest.getMaxWaitTime());

		Set<BusRoute> fromRoutes = pasTest.getStart().getPassingRoutes();
		Set<BusRoute> toRoutes = pasTest.getDestination().getPassingRoutes();

		assertEquals(fromRoutes, toRoutes);

	}
}

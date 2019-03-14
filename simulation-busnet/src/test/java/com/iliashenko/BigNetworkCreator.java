package com.iliashenko;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.iliashenko.utils.Time;

/**
 * Class which allows to create big input data (XML). Need for testing.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 10-03-2019
 * 
 */
public class BigNetworkCreator {

	public static void main(String[] args) {
		List<BusRoute> manyRoutes = new ArrayList<>();
		Random rand = new Random();
		double startx = 49.511111;
		double starty = 36.511111;
		BusShedule shedule = new BusShedule();
		Time start = new Time("6:10", Time.DATE_FORMAT);
		Time end = new Time("23:10", Time.DATE_FORMAT);

		for (int i = 0; i < 500; i++) {
			List<BusStop> stops = new LinkedList<>();
			for (int c = 0; c < rand.nextInt(1) + 2; c++) {
				stops.add(new BusStop(c + "_" + i, startx += 0.03, starty += 0.03));
			}
			Map<Direction, LinkedList<BusStop>> map = new EnumMap<>(Direction.class);
			map.put(Direction.PERMANENT, (LinkedList<BusStop>) stops);
			BusRoute temp = new BusRoute.BusRouteBuilder().withNumber(i + 3000).withPrice(10)
					.withTimeInterval(rand.nextInt(12) + 3).isRoundabout(true).withBusStops(map).build();
			BusSheduleRecord s = new BusSheduleRecord(temp.getNumber(), Direction.PERMANENT, start, end);
			shedule.addSheduleRecord(s);
			manyRoutes.add(temp);
		}
		Network net = new Network();
		net.addNetwork(manyRoutes);

		jaxbObjectToXML(net);
		jaxbObjectToXML(shedule);
		System.out.println("DONE!");
	}

	private static void jaxbObjectToXML(Network net) {
		File file = new File("/home/alex/Рабочий стол/net.xml");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Network.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(net, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static void jaxbObjectToXML(BusShedule sh) {
		File file = new File("/home/alex/Рабочий стол/shed.xml");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(BusShedule.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(sh, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}

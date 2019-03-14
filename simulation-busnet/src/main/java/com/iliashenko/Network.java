package com.iliashenko;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "bus-network")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class that represent entity of all routes.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 11-03-2019
 *
 */
public class Network {

	@XmlElement(name = "bus-route")
	private List<BusRoute> net;

	public Network() {
		net = new ArrayList<>();
	}

	public void addNetwork(List<BusRoute> net) {
		this.net = net;
	}

	public void addBusRoute(BusRoute br) {
		net.add(br);
	}

	public List<BusRoute> getNetwork() {
		return net;
	}

}

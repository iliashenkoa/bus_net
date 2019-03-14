package com.iliashenko.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.BusShedule;
import com.iliashenko.Network;
import com.iliashenko.utils.XMLHandler;

/**
 * <h1>Bus network simulation</h1>
 * <p>
 * This program implements simulation of city bus network. Buses ride on some
 * bus routes between sets of bus stops. On every stop in random time moment
 * come/go away passengers. All data from simulation keep in csv files/database.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 11-03-2019
 *
 */
public class Demo {

	private static final Logger DEMO_LOGGER = LoggerFactory.getLogger(Demo.class);

	/**
	 * This method is an entry point of all simulation programm. Can be runned with
	 * user's files of configuration or in default mode.
	 * 
	 * @param args Names of .xml/.xsd files
	 */
	public static void main(String[] args) {
		String schemaFolder = "/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/src/main/resources/start_configuration/schema/";
		String xmlFolder = "/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/src/main/resources/start_configuration/";
		Simulation simul;
		Configuration config;
		Network net;
		BusShedule shedule;
		if (args.length == 6) {
			config = XMLHandler.unmarshallingConfig(xmlFolder + args[0], schemaFolder + args[1]);
			net = XMLHandler.unmarshallingNetwork(xmlFolder + args[2], schemaFolder + args[3]);
			shedule = XMLHandler.unmarshallingShedule(xmlFolder + args[4], schemaFolder + args[5]);
		} else {
			DEMO_LOGGER.info("You don't input all conf files. Simulation will start with default params!");
			config = XMLHandler.unmarshallingConfig(xmlFolder + "configuration.xml", schemaFolder + "config.xsd");
			net = XMLHandler.unmarshallingNetwork(xmlFolder + "network.xml", schemaFolder + "network.xsd");
			shedule = XMLHandler.unmarshallingShedule(xmlFolder + "shedule.xml", schemaFolder + "shedule.xsd");
		}
		simul = new Simulation(config, net, shedule);
		DEMO_LOGGER.info("Object simulation was created");
		DEMO_LOGGER.info("Simulation started");
		simul.start();
		simul.stop();
		DEMO_LOGGER.info("Simulation ended");
	}

}
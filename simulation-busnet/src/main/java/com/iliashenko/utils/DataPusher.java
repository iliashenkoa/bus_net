package com.iliashenko.utils;

import javax.sql.DataSource;

import com.iliashenko.db.dao.BusLogDAOImpl;
import com.iliashenko.db.dao.StatisticsDAOImpl;
import com.iliashenko.simulation.statistics.BusLog;
import com.iliashenko.simulation.statistics.Statistics;

/**
 * Util class to marshalling to csv/xml from java objects.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 11-03-2019
 *
 */
public class DataPusher {

	private DataSource dataSource;
	private String csvStatistics;
	private String csvBusLog;

	public DataPusher(DataSource dataSource, String csvStatistics, String csvBusLog) {
		super();
		this.dataSource = dataSource;
		this.csvStatistics = csvStatistics;
		this.csvBusLog = csvBusLog;
	}

	public void pushStatistics(Statistics stat) {
		CSVHandler.convertStatistics2CSV(csvStatistics, stat);

		StatisticsDAOImpl statDAO = new StatisticsDAOImpl(dataSource);
		statDAO.create(stat);
	}

	public void pushBusLog(BusLog log) {
		CSVHandler.convertBusLog2CSV(csvBusLog, log);

		BusLogDAOImpl busDAO = new BusLogDAOImpl(dataSource);
		busDAO.create(log);

	}

}

package com.iliashenko.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.simulation.statistics.BusLog;
import com.iliashenko.simulation.statistics.Statistics;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * Util class to marshalling to csv from java objects.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 11-03-2019
 *
 */
public class CSVHandler {

	private static final Logger CSV_HANDL_LOGGER = LoggerFactory.getLogger(CSVHandler.class);

	private static final String COLUMNS_STATISTICS = "start_time,end_time,time_simulation,load_percentage,routes_count,bus_count,passengers_count";
	private static final String COLUMNS_BUS_LOG = "time_moment,bus_stop_id,route_num,bus_id,passengers_count_entry,passengers_count_exit,passengers_count_in";

	private CSVHandler() {
		super();
	}

	private static <E> void beanToCSV(String filePath, E bean, String columns) {
		fileChecker(filePath, columns);
		try (Writer writer = new FileWriter(filePath, true)) {
			StatefulBeanToCsv<E> beanToCsv = new StatefulBeanToCsvBuilder<E>(writer).withSeparator(',')
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(bean);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			CSV_HANDL_LOGGER.error(e.getMessage());
		}
	}

	private static void fileChecker(String filePath, String columns) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				PrintWriter out = new PrintWriter(file);
				out.println(columns.toUpperCase());
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			CSV_HANDL_LOGGER.error(e.getMessage());
		}
	}

	public static void convertStatistics2CSV(String filePath, Statistics stat) {
		beanToCSV(filePath, stat, COLUMNS_STATISTICS);
	}

	public static void convertBusLog2CSV(String filePath, BusLog busLog) {
		beanToCSV(filePath, busLog, COLUMNS_BUS_LOG);
	}
}

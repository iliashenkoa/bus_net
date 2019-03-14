package com.iliashenko.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for connections to the physical data source. For example, MariaDB.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 *
 */
public class MyDataSourceFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyDataSourceFactory.class);

	private MyDataSourceFactory() {
		super();
	}

	public static DataSource getMySQLDataSource() {
		Properties props = new Properties();
		FileInputStream fis = null;
		MariaDbDataSource mariaDS = null;
		try {
			fis = new FileInputStream(
					"/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/src/main/resources/application.properties");
			props.load(fis);
			mariaDS = new MariaDbDataSource();
			mariaDS.setUrl(props.getProperty("jdbc.url"));
			mariaDS.setUser(props.getProperty("jdbc.username"));
			mariaDS.setPassword(props.getProperty("jdbc.password"));
		} catch (IOException | SQLException e) {
			LOGGER.error(e.toString());
		}
		return mariaDS;
	}
}
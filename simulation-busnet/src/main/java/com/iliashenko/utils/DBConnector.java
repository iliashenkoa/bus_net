package com.iliashenko.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnector {
	private static final Logger CONNECTOR_LOGGER = LoggerFactory.getLogger(DBConnector.class);

	private DBConnector() {
		super();
	}

	public static DataSource initDatasource() {
		BasicDataSource basicDs = new BasicDataSource();
		Properties prop = new Properties();
		String resourceName = "/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/src/main/resources/start_configuration/application.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			prop.load(resourceStream);

			basicDs = new BasicDataSource();
			basicDs.setUrl((String) prop.get("connection.url"));
			basicDs.setUsername((String) prop.get("connection.username"));
			basicDs.setPassword((String) prop.get("connection.password"));
			basicDs.setMaxOpenPreparedStatements(50);

			CONNECTOR_LOGGER.debug("Connection properties file Loaded");
			CONNECTOR_LOGGER.debug((String) prop.get("connection.url"));
			CONNECTOR_LOGGER.debug((String) prop.get("connection.username"));
			CONNECTOR_LOGGER.debug((String) prop.get("connection.password"));

		} catch (IOException e) {
			CONNECTOR_LOGGER.error("ERROR : IO  - properties file {}", e);
		}

		return basicDs;

	}

	public static void close(Connection conn, Statement st) {
		if (st != null) {
			try {
				st.close();
				CONNECTOR_LOGGER.debug("Statement Closed");
			} catch (SQLException ex) {
				CONNECTOR_LOGGER.error("Error when closing statement", ex);
			}
		}
		if (conn != null) {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException ex) {
				CONNECTOR_LOGGER.error("Error when switching autocommit mode back to true", ex);
			}
			try {
				conn.close();
				CONNECTOR_LOGGER.debug("Connection Closed");
			} catch (SQLException ex) {
				CONNECTOR_LOGGER.error("Error when closing connection", ex);
			}
		}

	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				if (conn.getAutoCommit()) {
					throw new IllegalStateException("Connection is in the autocommit mode!");
				}
				conn.rollback();
				CONNECTOR_LOGGER.debug("Rollback completed Loaded");
			} catch (SQLException ex) {
				CONNECTOR_LOGGER.error("Error when doing rollback", ex);
			}
		}
	}

}

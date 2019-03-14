package com.iliashenko.db.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.db.dao.interfaces.GenericDAO;
import com.iliashenko.exceptions.EntityException;
import com.iliashenko.simulation.statistics.Statistics;
import com.iliashenko.utils.DBConnector;

/**
 * Data Access Object pattern implementation for statistics. Methods implements
 * CRUD commands to db.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class StatisticsDAOImpl extends ConnExcDAOImpl implements GenericDAO<Statistics, Integer> {

	private static final Logger STAT_DAO_LOGGER = LoggerFactory.getLogger(StatisticsDAOImpl.class);

	public StatisticsDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(Statistics t) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into statistics");
		sql.append(
				"(start_time, end_time, time_simulation, load_percentage, routes_count, bus_count, passengers_count)");
		sql.append(" values (?, ?, ?, ?, ?, ?, ?) ");

		Connection conn = null;
		PreparedStatement st = null;
		try {
			validateStatistics(t);
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "statistics", null);
			if (tables.next()) {
				st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				setStatementFromStatistics(st, t);

				if (st.executeUpdate() != 1) {
					STAT_DAO_LOGGER.warn("INTERNAL ERROR: more or none rows were inserted {}", t);
				}
				ResultSet rset = st.getGeneratedKeys();
				rset.next();
				conn.commit();
				STAT_DAO_LOGGER.debug("{} created", t);
			} else {
				createTable();
				create(t);
			}

		} catch (SQLException | EntityException e) {
			STAT_DAO_LOGGER.error("DB connection problem {}", e);
		} finally {
			DBConnector.close(conn, st);
		}
	}

	public void createTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE statistics (\n" + "  id int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  start_time datetime,\n" + "  end_time datetime,\n"
				+ "  time_simulation int(11) UNSIGNED NOT NULL,\n" + "  load_percentage double UNSIGNED NOT NULL,\n"
				+ "  routes_count int(11) UNSIGNED NOT NULL,\n" + "  bus_count int(11) UNSIGNED NOT NULL,\n"
				+ "  passengers_count int(11) UNSIGNED NOT NULL,\n" + "  PRIMARY KEY (id)\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\n" + "");

		connectAndExecute(sql.toString());
		STAT_DAO_LOGGER.debug("Table land created");
	}

	private Boolean validateStatistics(Statistics stat) throws EntityException {

		StringBuilder errMsg = new StringBuilder();
		if (stat.getStartTime() == null) {
			errMsg.append("start time is null \n");
		}
		if (stat.getEndTime() == null) {
			errMsg.append("start time is null \n");
		}
		if (stat.getTimeSimulation() < 0) {
			errMsg.append("time simula is not valid \n");
		}
		if (stat.getLoadPercentage() < 0.0 || stat.getLoadPercentage() > 100.0) {
			errMsg.append("load % is not valid \n");
		}
		if (stat.getRoutesCount() < 0 || stat.getBusCount() < 0 || stat.getPassengersCount() < 0) {
			errMsg.append("count is not valid ");
		}
		if (errMsg.length() > 0) {
			throw new EntityException(errMsg + stat.toString());
		}
		return true;
	}

	private void setStatementFromStatistics(PreparedStatement st, Statistics stat) throws SQLException {
		st.setObject(1, stat.getStartTime());
		st.setObject(2, stat.getEndTime());

		st.setInt(3, stat.getTimeSimulation());
		st.setDouble(4, stat.getLoadPercentage());
		st.setInt(5, stat.getRoutesCount());
		st.setInt(6, stat.getBusCount());
		st.setLong(7, stat.getPassengersCount());
	}

	@Override
	public Statistics read(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Statistics t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Statistics t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Statistics> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

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

import com.iliashenko.db.dao.interfaces.BusLogDAO;
import com.iliashenko.exceptions.EntityException;
import com.iliashenko.simulation.statistics.BusLog;
import com.iliashenko.utils.DBConnector;

/**
 * Data Access Object pattern implementation for bus log. Methods implements
 * CRUD commands to db.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class BusLogDAOImpl extends ConnExcDAOImpl implements BusLogDAO {

	private static final Logger BUS_LOG_DAO_LOGGER = LoggerFactory.getLogger(BusRouteDAOImpl.class);

	public BusLogDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(BusLog t) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into bus_log");
		sql.append(
				"(time_moment, bus_stop_id, route_num, bus_id, passengers_count_entry, passengers_count_exit, passengers_count_in)");
		sql.append(" values (?, ?, ?, ?, ?, ?, ?) ");

		Connection conn = null;
		PreparedStatement st = null;
		try {
			validateBus(t);
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "bus_log", null);
			if (tables.next()) {
				st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				setStatementFromBusRoute(st, t);

				if (st.executeUpdate() != 1) {
					BUS_LOG_DAO_LOGGER.warn("INTERNAL ERROR: more or none rows were inserted {}", t);
				} else {
					try (ResultSet rset = st.getGeneratedKeys()) {
						rset.next();
						conn.commit();
						BUS_LOG_DAO_LOGGER.debug("{} created", t);
					}
				}
			} else {
				createTable();
				create(t);
			}
		} catch (SQLException | EntityException e) {
			BUS_LOG_DAO_LOGGER.error("DB connection problem {}", e);
		} finally {
			DBConnector.close(conn, st);
		}
	}

	private Boolean validateBus(BusLog busLog) throws EntityException {

		StringBuilder errMsg = new StringBuilder();
		if (busLog.getBusId() < 0) {
			errMsg.append("bus id is not valid\n");
		}
		if (busLog.getBusStopId() < 0) {
			errMsg.append("bus stop id is not valid\n");
		}
		if (busLog.getBusRouteNum() < 0) {
			errMsg.append("bus route num is not valid\n");
		}
		if (busLog.getPassengersCountEntry() < 0) {
			errMsg.append("passengers count entry is not valid\n");
		}
		if (busLog.getPassengersCountExit() < 0) {
			errMsg.append("passengers count exit is not valid\\n");
		}
		if (busLog.getPassengersCountInBus() < 0) {
			errMsg.append("passengers count in is not valid\\n");
		}
		if (errMsg.length() > 0) {
			throw new EntityException(errMsg + busLog.toString());
		}
		return true;
	}

	private void setStatementFromBusRoute(PreparedStatement st, BusLog busLog) throws SQLException {
		st.setObject(1, busLog.getTimeMoment());
		st.setInt(2, busLog.getBusStopId());
		st.setInt(3, busLog.getBusRouteNum());
		st.setInt(4, busLog.getBusId());
		st.setInt(5, busLog.getPassengersCountEntry());
		st.setInt(6, busLog.getPassengersCountExit());
		st.setInt(7, busLog.getPassengersCountInBus());
	}

	public void createTable() {

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE bus_log (");
		sql.append("    id bigint NOT NULL AUTO_INCREMENT,");
		sql.append("    time_moment datetime,");
		sql.append("  bus_stop_id int(11) NOT NULL,");
		sql.append("  route_num int(11) NOT NULL,");
		sql.append("  bus_id int(11) UNSIGNED NOT NULL,");
		sql.append("  passengers_count_entry int(11) UNSIGNED NOT NULL,");
		sql.append("    passengers_count_exit int(11) UNSIGNED NOT NULL,");
		sql.append("   passengers_count_in int(11) UNSIGNED NOT NULL,");
		sql.append("   PRIMARY KEY (id),");
		sql.append(
				"   CONSTRAINT fk_bus_stop_id FOREIGN KEY (bus_stop_id) REFERENCES bus_stop (id) ON DELETE CASCADE ON UPDATE CASCADE,");
		sql.append(
				"     CONSTRAINT fk_bus_route_num FOREIGN KEY (route_num) REFERENCES bus_route (number) ON DELETE CASCADE ON UPDATE CASCADE");
		sql.append("    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		connectAndExecute(sql.toString());
		BUS_LOG_DAO_LOGGER.debug("Table bus_log created");
	}

	public void dropTableBusRouteLog() {
		StringBuilder sql = new StringBuilder();
		sql.append("drop table bus_log");
		connectAndExecute(sql.toString());
		BUS_LOG_DAO_LOGGER.debug("Table bus_log dropped");
	}

	@Override
	public BusLog read(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BusLog t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BusLog t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BusLog> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

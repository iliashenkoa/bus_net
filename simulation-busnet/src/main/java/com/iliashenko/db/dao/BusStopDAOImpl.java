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

import com.iliashenko.BusStop;
import com.iliashenko.db.dao.interfaces.BusStopDAO;
import com.iliashenko.exceptions.EntityException;
import com.iliashenko.utils.DBConnector;

/**
 * Data Access Object pattern implementation for bus stop. Methods implements
 * CRUD commands to db.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class BusStopDAOImpl extends ConnExcDAOImpl implements BusStopDAO {

	private static final Logger STOP_DAO_LOGGER = LoggerFactory.getLogger(BusStopDAOImpl.class);

	public BusStopDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(BusStop t) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into bus_stop");
		sql.append("(id, name, parking_space, x_coord, y_coord)");
		sql.append(" values (?, ?, ?, ?, ?) ");

		Connection conn = null;
		PreparedStatement st = null;
		try {
			validateBusRoute(t);
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "bus_stop", null);
			if (tables.next()) {
				if (isBusStopExist(conn, t.getIdBusStop()) > 0) {
					STOP_DAO_LOGGER.warn("BusRoute #{} exists!", t.getIdBusStop());
				} else {
					st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					setStatementFromBusStop(st, t);

					if (st.executeUpdate() != 1) {
						STOP_DAO_LOGGER.warn("INTERNAL ERROR: more or none rows were inserted {}", t);
					} else {
						try (ResultSet rset = st.getGeneratedKeys()) {
							rset.next();
							conn.commit();
							STOP_DAO_LOGGER.debug("{} created", t);
						}
					}
				}

			} else {
				createTable();
				create(t);
			}
		} catch (SQLException | EntityException e) {
			STOP_DAO_LOGGER.error("DB connection problem {}", e);
		} finally {
			DBConnector.close(conn, st);
		}

	}

	private Boolean validateBusRoute(BusStop busStop) throws EntityException {

		StringBuilder errMsg = new StringBuilder();
		if (busStop.getName() == null) {
			errMsg.append("stop name is null\n");
		}
		if (busStop.getParkingSpacesCount() < 1) {
			errMsg.append("parking spaces count is not valid\n");
		}
		if (busStop.getxCoordinate() <= 0) {
			errMsg.append("x coord is not valid");
		}
		if (busStop.getyCoordinate() <= 0) {
			errMsg.append("y coord is not valid");
		}
		if (errMsg.length() > 0) {
			throw new EntityException(errMsg + busStop.toString());
		}
		return true;
	}

	private void setStatementFromBusStop(PreparedStatement st, BusStop busStop) throws SQLException {
		st.setInt(1, busStop.getIdBusStop());
		st.setString(2, busStop.getName());
		st.setInt(3, busStop.getParkingSpacesCount());
		st.setDouble(4, busStop.getxCoordinate());
		st.setDouble(5, busStop.getyCoordinate());
	}

	public static int isBusStopExist(Connection conn, Integer id) throws SQLException {
		String sql = "select count(*) from bus_stop where id = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, id);
			try (ResultSet set = st.executeQuery()) {
				if (set.next()) {
					return set.getInt(1);
				}
			}
			return 0;
		}
	}

	public void createTable() {

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE bus_stop(");
		sql.append("   id int(11) NOT NULL AUTO_INCREMENT,");
		sql.append("   name varchar(255) NOT NULL,");
		sql.append("  parking_space int(11) UNSIGNED NOT NULL,");
		sql.append("  x_coord double UNSIGNED NOT NULL,");
		sql.append("  y_coord double UNSIGNED NOT NULL,");
		sql.append("  PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		connectAndExecute(sql.toString());
		STOP_DAO_LOGGER.debug("Table bus_stop created");
	}

	public void dropTableBusRouteLog() {

		StringBuilder sql = new StringBuilder();
		sql.append("drop table bus_stop");
		connectAndExecute(sql.toString());
		STOP_DAO_LOGGER.debug("Table bus_stop dropped");
	}

	@Override
	public BusStop read(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BusStop t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BusStop t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BusStop> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
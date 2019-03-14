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

import com.iliashenko.BusRoute;
import com.iliashenko.db.dao.interfaces.BusRouteDAO;
import com.iliashenko.exceptions.EntityException;
import com.iliashenko.utils.DBConnector;

/**
 * Data Access Object pattern implementation for bus route. Methods implements
 * CRUD commands to db.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class BusRouteDAOImpl extends ConnExcDAOImpl implements BusRouteDAO {

	private static final Logger ROUTE_DAO_LOGGER = LoggerFactory.getLogger(BusRouteDAOImpl.class);

	public BusRouteDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(BusRoute t) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into bus_route");
		sql.append("(number, price, is_roundabout, time_interval)");
		sql.append(" values (?, ?, ?, ?) ");

		Connection conn = null;
		PreparedStatement st = null;
		try {
			validateBusRoute(t);
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "bus_route", null);
			if (tables.next()) {
				if (isBusRouteExist(conn, t.getNumber()) == 1) {
					ROUTE_DAO_LOGGER.warn("BusRoute #{} exists!", t.getNumber());
				} else {
					st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					setStatementFromBusRoute(st, t);

					if (st.executeUpdate() != 1) {
						ROUTE_DAO_LOGGER.warn("INTERNAL ERROR: more or none rows were inserted {}", t);
					} else {
						try (ResultSet rset = st.getGeneratedKeys()) {
							rset.next();
							conn.commit();
							ROUTE_DAO_LOGGER.debug("{} created", t);
						}
					}
				}
			} else {
				createTable();
				create(t);
			}
		} catch (SQLException | EntityException e) {
			ROUTE_DAO_LOGGER.error("DB connection problem {}", e);
		} finally {
			DBConnector.close(conn, st);
		}
	}

	private Boolean validateBusRoute(BusRoute busRoute) throws EntityException {

		StringBuilder errMsg = new StringBuilder();
		if (busRoute.getNumber() <= 0) {
			errMsg.append("bus route num is not valid\n");
		}
		if (busRoute.getTimeInterval() <= 0) {
			errMsg.append("time interval is not valid\n");
		}
		if (busRoute.getPrice() <= 0) {
			errMsg.append("price is not valid");
		}
		if (errMsg.length() > 0) {
			throw new EntityException(errMsg + busRoute.toString());
		}
		return true;
	}

	public static int isBusRouteExist(Connection conn, Integer number) throws SQLException {
		String sql = "select count(*) from bus_route where number = ?";
		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, number);
			try (ResultSet set = st.executeQuery()) {
				if (set.next()) {
					return set.getInt(1);
				}
			}
			return 0;
		}
	}

	private void setStatementFromBusRoute(PreparedStatement st, BusRoute busRoute) throws SQLException {
		st.setInt(1, busRoute.getNumber());
		st.setDouble(2, busRoute.getPrice());
		st.setInt(3, busRoute.isRoundabout() ? 1 : 0);
		st.setInt(4, busRoute.getTimeInterval());
	}

	public void createTable() {

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE bus_route(");
		sql.append("  number int(11) NOT NULL,");
		sql.append("  price double UNSIGNED NOT NULL,");
		sql.append("  is_roundabout tinyint(1) NOT NULL,");
		sql.append("  time_interval int(11) UNSIGNED NOT NULL,");
		sql.append("  PRIMARY KEY (number)");
		sql.append("  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		connectAndExecute(sql.toString());
		ROUTE_DAO_LOGGER.debug("Table bus_route created");
	}

	public void dropTableBusRouteLog() {

		StringBuilder sql = new StringBuilder();
		sql.append("drop table bus_route");
		connectAndExecute(sql.toString());
		ROUTE_DAO_LOGGER.debug("Table bus_route dropped");
	}

	@Override
	public BusRoute read(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BusRoute t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BusRoute t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BusRoute> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

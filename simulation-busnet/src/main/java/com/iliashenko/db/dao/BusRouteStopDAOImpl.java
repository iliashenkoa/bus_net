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

import com.iliashenko.db.BusRouteStop;
import com.iliashenko.db.dao.interfaces.BusRouteStopDAO;
import com.iliashenko.exceptions.EntityException;
import com.iliashenko.utils.DBConnector;

/**
 * Data Access Object pattern implementation for bus route stop. Methods implements
 * CRUD commands to db.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class BusRouteStopDAOImpl extends ConnExcDAOImpl implements BusRouteStopDAO {

	private static final Logger BUS_ROUTE_M2M_DAO = LoggerFactory.getLogger(BusRouteStopDAOImpl.class);

	public BusRouteStopDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(BusRouteStop t) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into bus_route_stop");
		sql.append("(stop_id, route_number)");
		sql.append(" values (?, ?) ");

		Connection conn = null;
		PreparedStatement st = null;
		try {
			validateBusRouteStop(t);
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "bus_route_stop", null);
			if (tables.next()) {
				if ((BusStopDAOImpl.isBusStopExist(conn, t.getBusStopId()) != 1)
						|| (BusRouteDAOImpl.isBusRouteExist(conn, t.getBusRouteNumber()) != 1)) {
					BUS_ROUTE_M2M_DAO.warn("Error creating M2M record busStop {} or busRoute {} are not exists!",
							t.getBusStopId(), t.getBusRouteNumber());
				} else if (isBusRouteStopExist(conn, t.getBusStopId(), t.getBusRouteNumber()) == 1) {
					BUS_ROUTE_M2M_DAO.warn("Error creating! Record busStop {}; busRoute {} is exist!", t.getBusStopId(),
							t.getBusRouteNumber());
				} else {
					st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					setStatementFromBusRouteStop(st, t);

					if (st.executeUpdate() != 1) {
						BUS_ROUTE_M2M_DAO.warn("INTERNAL ERROR: more or none rows were inserted {}", t);
					}
					ResultSet rset = st.getGeneratedKeys();
					rset.next();
					conn.commit();
					BUS_ROUTE_M2M_DAO.debug("{} created", t);
				}
			} else {
				createTable();
				create(t);
			}
		} catch (SQLException | EntityException e) {
			BUS_ROUTE_M2M_DAO.error("DB connection problem {}", e);
		} finally {
			DBConnector.close(conn, st);
		}
	}

	private Boolean validateBusRouteStop(BusRouteStop busRouteStop) throws EntityException {

		StringBuilder errMsg = new StringBuilder();
		if (busRouteStop.getBusStopId() <= 0) {
			errMsg.append("stop id is not valid\n");
		}
		if (busRouteStop.getBusRouteNumber() <= 0) {
			errMsg.append("bus route num is not valid\n");
		}
		if (errMsg.length() > 0) {
			throw new EntityException(errMsg + busRouteStop.toString());
		}
		return true;
	}

	public static int isBusRouteStopExist(Connection conn, Integer id, Integer number) throws SQLException {
		String sql = "select count(*) from bus_route_stop where stop_id = ? && route_number = ?";
		PreparedStatement st = conn.prepareStatement(sql.toString());
		st.setInt(1, id);
		st.setInt(2, number);
		ResultSet set = st.executeQuery();
		if (set.next()) {
			return set.getInt(1);
		}
		return 0;
	}

	private void setStatementFromBusRouteStop(PreparedStatement st, BusRouteStop busRouteStop) throws SQLException {

		st.setInt(1, busRouteStop.getBusStopId());
		st.setInt(2, busRouteStop.getBusRouteNumber());
	}

	public void createTable() {

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE bus_route_stop (");
		sql.append("  stop_id int(11) NOT NULL,");
		sql.append("  route_number int(11) NOT NULL,");
		sql.append("  PRIMARY KEY (stop_id, route_number),");
		sql.append("   KEY bus_route_stop_stopidx (stop_id),");
		sql.append(
				"  CONSTRAINT fk_bus_route_stop_stopid FOREIGN KEY (stop_id) REFERENCES bus_stop (id) ON DELETE CASCADE ON UPDATE CASCADE,");
		sql.append(
				"  CONSTRAINT fk_bus_route_stop_routenum FOREIGN KEY (route_number) REFERENCES bus_route (number) ON DELETE CASCADE ON UPDATE CASCADE");
		sql.append("  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		connectAndExecute(sql.toString());
		BUS_ROUTE_M2M_DAO.debug("Table bus_route_stop created");
	}

	@Override
	public BusRouteStop read(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BusRouteStop t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BusRouteStop t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BusRouteStop> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

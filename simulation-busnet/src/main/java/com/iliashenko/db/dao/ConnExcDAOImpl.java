package com.iliashenko.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.db.dao.interfaces.ConnExcDAO;
import com.iliashenko.utils.DBConnector;

/**
 * Connection to db and execute raw sql queries.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class ConnExcDAOImpl implements ConnExcDAO {
	protected final DataSource dataSource;
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnExcDAOImpl.class);

	public ConnExcDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void connectAndExecute(String sql) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = dataSource.getConnection();
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.toString());
		} finally {
			DBConnector.close(conn, st);
		}

	}

}

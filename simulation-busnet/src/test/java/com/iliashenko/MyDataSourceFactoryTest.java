package com.iliashenko;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import com.iliashenko.db.MyDataSourceFactory;

public class MyDataSourceFactoryTest {

	@Test
	public void getMySQLDataSourceTest() throws SQLException {
		assertEquals("busnet", MyDataSourceFactory.getMySQLDataSource().getConnection().getCatalog());
	}
}

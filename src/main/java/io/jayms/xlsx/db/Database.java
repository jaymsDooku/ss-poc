package io.jayms.xlsx.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

	Connection getConnection();
	
	boolean tableExists(String tableName);
	
	void close() throws SQLException;
	
}

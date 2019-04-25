package io.jayms.xlsx.db;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerDatabase extends AbstractDatabase {

	public SQLServerDatabase(String serverName, String host, String port, String databaseName, String user,
			String pass) {
		super(serverName, host, port, databaseName, user, pass);
		
	}

	@Override
	protected void initConnection(String serverName, String host, String port, String databaseName, String user, String pass) throws SQLException {
		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		String serverPrefix = "";
		if (serverName != null && !serverName.isEmpty()) {
			serverPrefix = serverName + ".";
		}
		String url = "jdbc:sqlserver://" + serverPrefix + host + ":" + port+ ";user=" + user + ";password=" + pass + ";integratedSecurity=false;databaseName=" + databaseName + ";";
		connection = DriverManager.getConnection(url);
	}
}

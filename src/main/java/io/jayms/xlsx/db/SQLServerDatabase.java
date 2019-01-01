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
		connection = DriverManager.getConnection("jdbc:sqlserver://" + serverName + "." + host + ":" + port + ";database=" + databaseName + ";user=" + user + "@testserver6767;password={" + pass + "};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
	}
}

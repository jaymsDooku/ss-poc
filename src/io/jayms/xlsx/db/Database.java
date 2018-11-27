package io.jayms.xlsx.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	private Connection connection;
	
	public Database(String serverName, String host, String port, String databaseName, String user, String pass) {
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			connection = DriverManager.getConnection("jdbc:sqlserver://" + serverName + ".database.windows.net:" + port + ";database=" + databaseName + ";user=" + user + "@testserver6767;password={" + pass + "};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection connection() {
		return connection;
	}
	
	public boolean tableExists(String table) {
        try {
            DatabaseMetaData dmd = this.connection.getMetaData();
            ResultSet rs = dmd.getTables(null, null, table, null);

            return rs.next();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
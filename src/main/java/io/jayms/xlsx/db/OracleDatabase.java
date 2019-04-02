package io.jayms.xlsx.db;

import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabase extends AbstractDatabase {

	public OracleDatabase(String serverName, String host, String port, String databaseName, String user,
			String pass) {
		super(serverName, host, port, databaseName, user, pass);
		
	}

	@Override
	protected void initConnection(String serverName, String host, String port, String databaseName, String user, String pass) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String connString = "jdbc:oracle:thin:@" + host + ":" + port + ":xe";
			System.out.println("connString: " + connString);
			System.out.println("user: " + user);
			System.out.println("pass: " + pass);
			connection = DriverManager.getConnection(connString, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}


package io.jayms.xlsx.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;

public abstract class AbstractDatabase implements Database {

	@Getter protected Connection connection;
	
	public AbstractDatabase(String serverName, String host, String port, String databaseName, String user, String pass) {
		try {
			initConnection(serverName, host, port, databaseName, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
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
	
	protected abstract void initConnection(String serverName, String host, String port, String databaseName, String user,
			String pass) throws SQLException, ClassNotFoundException;
}

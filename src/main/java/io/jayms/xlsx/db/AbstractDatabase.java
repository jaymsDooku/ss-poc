package io.jayms.xlsx.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;

/**
 * This class represents a database at an abstract level. It is to be extended by subclasses with specialized implementations of 
 */
public abstract class AbstractDatabase implements Database {

	/**
	 * Represents connection to DB.
	 */
	@Getter protected Connection connection;
	
	/**
	 * Instantiates this class.
	 * @param serverName - name of server DB is located on (if required).
	 * @param host - name of host DB is located on.
	 * @param port - port to connect to DB on.
	 * @param databaseName - name of DB (or schema if instance of OracleDatabase).
	 * @param user - username to authorize access to DB.
	 * @param pass - password to authorize access to DB.
	 */
	public AbstractDatabase(String serverName, String host, String port, String databaseName, String user, String pass) {
		try {
			initConnection(serverName, host, port, databaseName, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns true if a table exists in the database, otherwise false.
	 */
	@Override
	public boolean tableExists(String table) {
        try {
            DatabaseMetaData dmd = this.connection.getMetaData();
            ResultSet rs = dmd.getTables(null, null, table, null); // Grab tables with this tableName

            return rs.next(); // Check if record exists in result set (if table exists).
        } catch (Exception e) { 
            e.printStackTrace();
            return false; 
        }
    }
	
	@Override
	public void close() throws SQLException {
		connection.close();
	}
	
	/**
	 * Abstract method responsible for the initialization of the connection to the database.
	 */
	protected abstract void initConnection(String serverName, String host, String port, String databaseName, String user,
			String pass) throws SQLException, ClassNotFoundException;
}

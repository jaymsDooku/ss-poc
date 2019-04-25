package io.jayms.xlsx.db;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class DBTools {

	/**
	 * Extracts the DatabaseColumn / field data from the metadata of a ResultSet.
	 * @param meta - ResultSet metadata to extract from.
	 * @return - Returns an array of DatabaseColumn.
	 * @throws SQLException
	 */
	public static DatabaseColumn[] getDatabaseColumns(ResultSetMetaData meta) throws SQLException {
		int columnCount = meta.getColumnCount();
		DatabaseColumn[] columns = new DatabaseColumn[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			//Retrieve data from column at this index.
			String colName = meta.getColumnName(i);
			String colLabel = meta.getColumnLabel(i);
			int colType = meta.getColumnType(i);
			String typeName = meta.getColumnTypeName(i);
			String tableName = meta.getTableName(i);
			//debug
			System.out.println("Column " + i); 
			System.out.println("Column Name " + colName);
			System.out.println("Column Label " + colLabel);
			System.out.println("Column Type " + colType);
			System.out.println("Column Type Name " + typeName);
			System.out.println("Table Name " + meta.getTableName(i));
			//Instantiate database column and insert into array.
			columns[i-1] = new DatabaseColumn(i, colName, colLabel, colType, tableName);
		}
		return columns;
	}
}

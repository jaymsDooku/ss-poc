package io.jayms.xlsx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import io.jayms.xlsx.model.Workbook;
import io.jayms.xlsx.model.Worksheet;

public class DatabaseConverter {

	private Database db;
	
	public DatabaseConverter(Database db) {
		this.db = db;
	}
	
	public Worksheet toWorksheet(Workbook wb, String name, String query) {
		Worksheet ws = wb.createSheet(name);
		
		try {
			PreparedStatement ps = this.db.connection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			
			int columnCount = meta.getColumnCount();
			DatabaseColumn[] columns = new DatabaseColumn[columnCount];
			for (int i = 0; i < columnCount; i++) {
				String colName = meta.getColumnName(i);
				int type = meta.getColumnType(i);
				String typeName = meta.getColumnTypeName(i);
				System.out.println("Column " + i);
				System.out.println("Column Name " + colName);
				System.out.println("Column Type " + type);
				System.out.println("Column Type Name " + typeName);
				columns[i] = new DatabaseColumn(colName, type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ws;
	}
}

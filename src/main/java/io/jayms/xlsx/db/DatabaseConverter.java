package io.jayms.xlsx.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import io.jayms.xlsx.model.Row;
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
			
			System.out.println(meta);
			int columnCount = meta.getColumnCount();
			System.out.println(columnCount);
			Row headerRow = ws.row();
			DatabaseColumn[] columns = new DatabaseColumn[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				String colName = meta.getColumnName(i);
				String colLabel = meta.getColumnLabel(i);
				int colType = meta.getColumnType(i);
				String typeName = meta.getColumnTypeName(i);
				System.out.println("Column " + i);
				System.out.println("Column Name " + colName);
				System.out.println("Column Label " + colLabel);
				System.out.println("Column Type " + colType);
				System.out.println("Column Type Name " + typeName);
				columns[i-1] = new DatabaseColumn(colName, colLabel, colType);
				
				headerRow.string(colName);
			}
			
			while (rs.next()) {
				Row row = ws.row();
				for (int i = 0; i < columns.length; i++) {
					DatabaseColumn col = columns[i];
					String colName = col.name();
					String colLabel = col.label();
					int colType = col.type();
					String value;
					switch(colType) {
						case DatabaseColumnTypes.NVARCHAR:
							value = rs.getNString(colLabel);
							break;
						case DatabaseColumnTypes.UUID:
						case DatabaseColumnTypes.VARCHAR:
							value = rs.getString(colLabel);
							break;
						case DatabaseColumnTypes.INT:
							value = Integer.toString(rs.getInt(colLabel));
							break;
						case DatabaseColumnTypes.BOOL:
							value = Boolean.toString(rs.getBoolean(colLabel));
							break;
						case DatabaseColumnTypes.DATETIME:
							value = rs.getDate(colLabel).toString();
							break;
						default:
							value = "null";
							break;
					}
					row.string(value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ws;
	}
}

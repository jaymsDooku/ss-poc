package io.jayms.xlsx.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.Arrays;

import io.jayms.xlsx.model.Row;
import io.jayms.xlsx.model.Workbook;
import io.jayms.xlsx.model.Worksheet;

public class DatabaseConverter {

	private Database db;
	
	public DatabaseConverter(Database db) {
		this.db = db;
	}
	
	public Worksheet appendQuery(Worksheet ws, String query) {
		try {
			PreparedStatement ps = this.db.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			System.out.println(meta);
			int columnCount = meta.getColumnCount();
			System.out.println(columnCount);
			Row headerRow = ws.row();
			headerRow.setTitleRow(true);
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
				columns[i-1] = new DatabaseColumn(i, colName, colLabel, colType);
				
				headerRow.string(colName);
			}
			
			while (rs.next()) {
				Row row = ws.row();
				for (int i = 0; i < columns.length; i++) {
					DatabaseColumn col = columns[i];
					String colName = col.getName();
					String colLabel = col.getLabel();
					int colType = col.getType();
					String value;
					System.out.println("colType: " + colType);
					switch(colType) {
						case DatabaseColumnTypes.NVARCHAR:
							value = rs.getNString(colLabel);
							break;
						case DatabaseColumnTypes.VARCHAR:
							value = rs.getString(colLabel);
							break;
						case DatabaseColumnTypes.NUMBER:
							value = Double.toString(rs.getDouble(colLabel));
							break;
						case DatabaseColumnTypes.INT:
							value = Integer.toString(rs.getInt(colLabel));
							break;
						case DatabaseColumnTypes.BOOL:
							value = Boolean.toString(rs.getBoolean(colLabel));
							break;
						case DatabaseColumnTypes.DATE:
						case DatabaseColumnTypes.TIMESTAMP:
							value = rs.getDate(colLabel).toString();
							break;
						case DatabaseColumnTypes.INTERVAL_DS:
						case DatabaseColumnTypes.INTERVAL_YM:
							value = rs.getString(colLabel);
							break;
						case DatabaseColumnTypes.ROWID:
							System.out.println("stuck");
							value = new String(rs.getBytes(colLabel));
							System.out.println("passed stuck");
							break;
						case DatabaseColumnTypes.CLOB:
							value = rs.getClob(colLabel).toString();
							break;
						case DatabaseColumnTypes.RAW:
							value = rs.getBytes(colLabel).toString();
							break;
						case DatabaseColumnTypes.CHAR:
							value = rs.getString(colLabel);
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
	
	public Worksheet addQueryToWorksheet(Workbook wb, String worksheetName, String query) {
		Worksheet ws = wb.hasWorksheet(worksheetName) ? wb.getWorksheet(worksheetName) : wb.createSheet(worksheetName);
		appendQuery(ws, query);
		return ws;
	}
}

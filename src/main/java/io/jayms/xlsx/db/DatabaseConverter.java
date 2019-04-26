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
	
	public Worksheet appendQuery(Worksheet ws, String query) {
		try {
			PreparedStatement ps = this.db.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			Row headerRow = ws.row();
			headerRow.setTitleRow(true);
			DatabaseColumn[] columns = DBTools.getDatabaseColumns(meta);
			for (int i = 0; i < columns.length; i++) {
				String colName = columns[i].getName();
				headerRow.string(colName);
			}
			
			while (rs.next()) {
				Row row = ws.row();
				for (int i = 0; i < columns.length; i++) {
					DatabaseColumn col = columns[i];
					String colLabel = col.getLabel();
					int colType = col.getType();
					String value = "null";
					Number numVal = 0;
					//System.out.println("colType: " + colType);
					switch(colType) {
						case DatabaseColumnTypes.NVARCHAR:
							value = rs.getNString(colLabel);
							break;
						case DatabaseColumnTypes.VARCHAR:
							value = rs.getString(colLabel);
							break;
						case DatabaseColumnTypes.NUMBER:
							value = Double.toString(rs.getDouble(colLabel));
							numVal = rs.getDouble(colLabel);
							break;
						case DatabaseColumnTypes.INT:
							value = Integer.toString(rs.getInt(colLabel));
							numVal = rs.getInt(colLabel);
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
							value = new String(rs.getBytes(colLabel));
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
							break;
					}
					if (DatabaseColumnTypes.NUMBER_TYPES.contains(colType)) {
						row.number(numVal);
					} else {
						row.string(value);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ws;
	}
	
	public Worksheet addQueryToWorksheet(Workbook wb, String worksheetName, String query) {
		Worksheet ws = wb.hasWorksheet(worksheetName) ? wb.getWorksheet(worksheetName) : wb.createSheet(worksheetName);
		ws = appendQuery(ws, query);
		return ws;
	}
}

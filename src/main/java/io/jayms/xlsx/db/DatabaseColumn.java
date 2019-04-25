package io.jayms.xlsx.db;

import lombok.Getter;

public class DatabaseColumn {

	@Getter private int index;
	@Getter private String name;
	@Getter private String label;
	@Getter private int type;
	@Getter private String tableName;
	
	public DatabaseColumn(int index, String name, String label, int type, String tableName) {
		this.index = index;
		this.name = name;
		this.label = label;
		this.type = type;
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "index=" + index + "|name=" + name + "|label=" + label + "|type=" + type + "|tableName=" + tableName;
	}
}

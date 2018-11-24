package io.jayms.xlsx.db;

public class DatabaseColumn {

	private String name;
	private int type;
	
	public DatabaseColumn(String name, int type) {
		this.name = name;
		this.type = type;
	}
	
	public String name() {
		return name;
	}
	
	public int type() {
		return type;
	}
}

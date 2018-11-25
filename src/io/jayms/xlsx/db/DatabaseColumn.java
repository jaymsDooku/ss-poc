package io.jayms.xlsx.db;

public class DatabaseColumn {

	private String name;
	private String label;
	private int type;
	
	public DatabaseColumn(String name, String label, int type) {
		this.name = name;
		this.label = label;
		this.type = type;
	}
	
	public String name() {
		return name;
	}
	
	public String label() {
		return label;
	}
	
	public int type() {
		return type;
	}
}

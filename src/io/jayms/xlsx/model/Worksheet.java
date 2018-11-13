package io.jayms.xlsx.model;

import java.util.LinkedList;

public class Worksheet {
	
	private String name;
	private LinkedList<Row> rows;
	
	public Worksheet(String name) {
		this.name = name;
		rows = new LinkedList<>();
	}
	
	public Row row() {
		Row row = new Row(this);
		rows.add(row);
		return row;
	}

}

package io.jayms.xlsx.model;

import java.util.LinkedList;

public class Row {

	private Worksheet worksheet;
	private LinkedList<Cell> cells;
	
	public Row(Worksheet ws) {
		worksheet = ws;
		cells = new LinkedList<>();
	}
	
	public Cell<String> string(String value) {
		Cell<String> cell = new Cell<>(this, value);
		cells.add(cell);
		return cell;
	}
}

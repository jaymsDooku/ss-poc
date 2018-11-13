package io.jayms.xlsx.model;

public class Cell<T> {

	private Row row;
	private T value;
	
	public Cell(Row row, T value) {
		this.row = row;
		this.value = value;
	}
	
	public T value() {
		return value;
	}
	
}

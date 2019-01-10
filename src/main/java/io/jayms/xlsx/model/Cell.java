package io.jayms.xlsx.model;

import lombok.Getter;
import lombok.Setter;

public class Cell<T> {

	@Getter private final Row row;
	@Getter @Setter private T value;
	@Getter @Setter private int fillId = -1;
	
	public Cell(Row row, T value) {
		this.row = row;
		this.value = value;
	}
	
}

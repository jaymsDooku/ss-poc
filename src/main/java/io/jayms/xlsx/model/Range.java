package io.jayms.xlsx.model;

import lombok.Getter;

public class Range {
	
	@Getter private Row startRow;
	@Getter private Row stopRow;
	@Getter private int colIndex;
	
	public Range(Row startRow, Row stopRow, int colIndex) {
		this.startRow = startRow;
		this.stopRow = stopRow;
		this.colIndex = colIndex;
	}
}

package io.jayms.xlsx.model.cells;

import io.jayms.xlsx.model.Cell;
import io.jayms.xlsx.model.Row;

public class StringCell extends Cell<String> {

	public StringCell(Row row, String value) {
		super(row, value);
	}
	
}

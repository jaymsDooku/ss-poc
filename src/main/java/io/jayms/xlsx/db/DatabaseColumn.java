package io.jayms.xlsx.db;

import lombok.Getter;

public class DatabaseColumn {

	@Getter private int index;
	@Getter private String name;
	@Getter private String label;
	@Getter private int type;
	
	public DatabaseColumn(int index, String name, String label, int type) {
		this.index = index;
		this.name = name;
		this.label = label;
		this.type = type;
	}

}

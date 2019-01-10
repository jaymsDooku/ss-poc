package io.jayms.xlsx.db;

import lombok.Getter;

public class DatabaseColumn {

	@Getter private String name;
	@Getter private String label;
	@Getter private int type;
	
	public DatabaseColumn(String name, String label, int type) {
		this.name = name;
		this.label = label;
		this.type = type;
	}

}

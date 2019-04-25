package io.jayms.xlsx.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class WorksheetDescriptor {

	@Getter private String worksheetName;
	@Getter private Map<String, FieldConfiguration> fieldConfigs = new HashMap<>();
	
	public WorksheetDescriptor(String worksheetName, Map<String, FieldConfiguration> fieldConfigs) {
		this.worksheetName = worksheetName;
		this.fieldConfigs = fieldConfigs;
	}
	
}

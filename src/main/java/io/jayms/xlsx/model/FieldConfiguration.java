package io.jayms.xlsx.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.jayms.xlsx.db.DatabaseColumn;
import io.jayms.xlsx.model.cells.SubTotalFunction;
import lombok.Getter;
import lombok.Setter;

public class FieldConfiguration {

	@Getter @Setter private boolean inline;
	@Getter @Setter private boolean subTotalOnChange;
	@Getter @Setter private SubTotalFunction subTotalFunction;
	@Getter @Setter private boolean swapBandOnChange;
	@Getter @Setter private float columnWidth;
	
	public FieldConfiguration(boolean inline, boolean subTotalOnChange, SubTotalFunction subTotalFunction, boolean swapBandOnChange, float columnWidth) {
		this.inline = inline;
		this.subTotalOnChange = subTotalOnChange;
		this.subTotalFunction = subTotalFunction;
		this.swapBandOnChange = swapBandOnChange;
		this.columnWidth = columnWidth;
	}
	
	@Override
	public String toString() {
		return "inline=" + inline 
				+ "|subTotalOnChange=" + subTotalOnChange
				+ "|subTotalFunction=" + subTotalFunction
				+ "|swapBandOnChange=" + swapBandOnChange
				+ "|columnWidth=" + columnWidth;
	}
	
	public static FieldConfiguration getDefaultFieldConfig() {
		boolean inline = false;
		boolean subTotalOnChange = false;
		SubTotalFunction subTotalFunction = SubTotalFunction.SUM;
		boolean swapBandOnChange = false;
		float columnWidth = Worksheet.DEFAULT_COL_WIDTH;
		FieldConfiguration fieldConfig = new FieldConfiguration(inline, subTotalOnChange, subTotalFunction, swapBandOnChange, columnWidth);
		return fieldConfig;
	}
	
	public static Map<String, FieldConfiguration> getDefaultFieldConfigs(DatabaseColumn[] fields, Map<String, FieldConfiguration> curFieldConfigs) {
		Map<String, FieldConfiguration> fieldConfigs = curFieldConfigs == null ? new HashMap<>() : curFieldConfigs;
		Arrays.stream(fields).forEach(f -> {
			if (!fieldConfigs.containsKey(f.getName())) {
				fieldConfigs.put(f.getName(), FieldConfiguration.getDefaultFieldConfig());
			}
		});
		return fieldConfigs;
	}
	
}

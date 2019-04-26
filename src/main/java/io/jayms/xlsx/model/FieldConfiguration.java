package io.jayms.xlsx.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.jayms.xlsx.db.DatabaseColumn;
import io.jayms.xlsx.model.cells.SubTotalFunction;
import lombok.Getter;
import lombok.Setter;

/**
 * Describes how to save each field in a worksheet.
 */
public class FieldConfiguration {
	
	// should field save inline?
	@Getter @Setter private boolean inline;
	// should a sub total generate on the value change of this field?
	@Getter @Setter private boolean subTotalOnChange;
	// if a sub total should generate on the value change of this field, what will the sub total function be?
	@Getter @Setter private SubTotalFunction subTotalFunction;
	// should the style/colour band alternate on the value change of this field?
	@Getter @Setter private boolean swapBandOnChange;
	// how wide should the field be in the excel spreadsheet?
	@Getter @Setter private float columnWidth;
	
	public FieldConfiguration(boolean inline, boolean subTotalOnChange, SubTotalFunction subTotalFunction, boolean swapBandOnChange, float columnWidth) {
		this.inline = inline;
		this.subTotalOnChange = subTotalOnChange;
		this.subTotalFunction = subTotalFunction;
		this.swapBandOnChange = swapBandOnChange;
		this.columnWidth = columnWidth;
	}
	
	@Override
	public String toString() { // debug
		return "inline=" + inline 
				+ "|subTotalOnChange=" + subTotalOnChange
				+ "|subTotalFunction=" + subTotalFunction
				+ "|swapBandOnChange=" + swapBandOnChange
				+ "|columnWidth=" + columnWidth;
	}
	
	/**
	 * @return Field Configuration initialized with default values.
	 */
	public static FieldConfiguration getDefaultFieldConfig() {
		boolean inline = false;
		boolean subTotalOnChange = false;
		SubTotalFunction subTotalFunction = SubTotalFunction.SUM;
		boolean swapBandOnChange = false;
		float columnWidth = Worksheet.DEFAULT_COL_WIDTH;
		FieldConfiguration fieldConfig = new FieldConfiguration(inline, subTotalOnChange, subTotalFunction, swapBandOnChange, columnWidth);
		return fieldConfig;
	}
	
	/**
	 * Provide a map of default field configurations from an array of DatabaseColumn.
	 * 
	 * Iterates over the database columns in fields; if there isn't a field configuration for a field,
	 *  it will put a default field configuration into the map. 
	 * 
	 * @param fields - DatabaseColumn array, represent fields.
	 * @param curFieldConfigs - Existing field configuration map.
	 * @return Map of default field configurations.
	 */
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

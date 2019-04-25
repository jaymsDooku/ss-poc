package io.jayms.xlsx.model.cells;

import java.util.LinkedList;
import java.util.Set;

import io.jayms.xlsx.model.FieldConfiguration;
import io.jayms.xlsx.model.Range;
import io.jayms.xlsx.model.Row;
import io.jayms.xlsx.model.Save;
import io.jayms.xlsx.model.Worksheet;
import io.jayms.xlsx.model.WorksheetTransformer;

/**
 * 
 */
public class SubTotalFunctionTransformer extends WorksheetTransformer {

	public SubTotalFunctionTransformer(Worksheet worksheet, Save save) {
		super(worksheet, save);
	}

	private Range getRange(int cellIndex, LinkedList<CellBuf> buf) {
		CellBuf firstCB = buf.getFirst();
		CellBuf lastCB = buf.getLast();
		return new Range(firstCB.getRow(), lastCB.getRow(), cellIndex);
	}
	
	@Override
	public InsertRowData onFieldValueChange(int cellIndex, Row row, FieldConfiguration fieldConfig, LinkedList<CellBuf> buf) {
		if (!fieldConfig.isSubTotalOnChange()) {
			return null;
		}
		
		Range range = getRange(cellIndex, buf);
		return new InsertRowData(row, fieldConfig, range);
	}

	@Override
	public void postProcessing(Set<InsertRowData> toInsertRows) {
		System.out.println("toInsertRows: " + toInsertRows);
		System.out.println("toInsertRows length: " + toInsertRows.size());
		Worksheet ws = getWorksheet();
		for (InsertRowData toInsertData : toInsertRows) {
			Row adjacent = toInsertData.getAdjacent();
			FieldConfiguration fieldConfig = toInsertData.getFieldConfig();
			Range range = toInsertData.getRange();
			int insertIndex = ws.indexOf(adjacent);
			Row subTotalRow = ws.row(insertIndex+1);
			SubTotalFunction function = fieldConfig.getSubTotalFunction();
			subTotalRow.subTotal(function, range, "0");
		}
	}

}

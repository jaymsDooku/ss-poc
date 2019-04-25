package io.jayms.xlsx.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.jayms.xlsx.model.cells.StringCell;
import io.jayms.xlsx.util.AlphanumericSequence;
import lombok.Getter;

/**
 * Intends to make the large scale transformation of a worksheet easier by alleviating the work required to iterate over 
 * the worksheet and allow the injection of logic at certain points in the process.
 */
public abstract class WorksheetTransformer {

	/**
	 * Worksheet to transform.
	 */
	@Getter private Worksheet worksheet;
	/**
	 * Number of rows in worksheet.
	 */
	private int size;
	/**
	 * Buffer of cells before next change in value.
	 */
	private LinkedList<CellBuf> changeBuf = new LinkedList<>();
	/**
	 * Save data.
	 */
	private Save save;
	/**
	 * Index we're currently pointing to.
	 */
	private int index;
	/**
	 * A store for all of the rows to be passed into post-processing.
	 */
	private Set<InsertRowData> insertRowDatas = new HashSet<>();
	
	/**
	 * Instantiates WorksheetTransformer
	 * @param worksheet - worksheet to transform
	 * @param save - save data
	 */
	public WorksheetTransformer(Worksheet worksheet, Save save) {
		this.worksheet = worksheet;
		this.size = worksheet.getRows().size();
		this.save = save;
	}
	
	/**
	 * @return True if the index is below the initial number of rows in the worksheet.
	 */
	public boolean hasNextRow() {
		return index < size;
	}
	
	public void processNextRow() {
		if (!hasNextRow()) {
			return;
		}
		WorksheetDescriptor wsDesc = save.getWorksheetDescriptor(worksheet.getName());
		Row headerRow = worksheet.getHeaderRow();
		Row row = worksheet.getRows().get(index);
		if (row.isTitleRow()) {
			index++;
			return;
		}
		List<Cell> cells = row.getCells();
		for (int i = 0; i < cells.size(); i++) {
			AlphanumericSequence alphaSeq = new AlphanumericSequence();
			Cell headerCell = headerRow.getCells().get(i);
			if (!(headerCell instanceof StringCell)) {
				continue;
			}
			StringCell sHeaderCell = (StringCell) headerCell;
			String headerVal = sHeaderCell.getValue();
			FieldConfiguration fieldConfig = wsDesc.getFieldConfigs().get(headerVal);
			
			if (!fieldConfig.isSubTotalOnChange()) {
				continue;
			}
			
			Cell c = cells.get(i);
			Object val = c.getValue();
			
			changeBuf.push(new CellBuf(row, val));
			if (!changeBuf.isEmpty() && !changeBuf.peek().equals(val)) {
				System.out.println("changeBuf: " + changeBuf);
				InsertRowData ird = onFieldValueChange(i, row, fieldConfig, changeBuf);
				if (ird != null) insertRowDatas.add(ird);
				changeBuf.clear();
			}
		}
		index++;
	}
	
	public void process() {
		while (hasNextRow()) {
			processNextRow();
		}
		postProcessing(insertRowDatas);
	}
	
	public abstract InsertRowData onFieldValueChange(int cellIndex, Row adjacent, FieldConfiguration fieldConfig, LinkedList<CellBuf> buf);
	
	public abstract void postProcessing(Set<InsertRowData> insertRowData);
	
	public static class CellBuf {
		
		@Getter private Row row;
		@Getter private Object val;
		
		public CellBuf(Row row, Object val) {
			this.row = row;
			this.val = val;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(val);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CellBuf)) {
				return false;
			}
			CellBuf cb = (CellBuf) obj;
			return val.equals(cb.val);
		}
	}
	
	public static class InsertRowData {
		
		@Getter private Row adjacent;
		@Getter private FieldConfiguration fieldConfig;
		@Getter private Range range;
		
		public InsertRowData(Row adjacent, FieldConfiguration fieldConfig, Range range) {
			this.adjacent = adjacent;
			this.fieldConfig = fieldConfig;
			this.range = range;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(adjacent);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof InsertRowData)) {
				return false;
			}
			InsertRowData ird = (InsertRowData) obj;
			return adjacent.equals(ird.adjacent);
		}
	}
}

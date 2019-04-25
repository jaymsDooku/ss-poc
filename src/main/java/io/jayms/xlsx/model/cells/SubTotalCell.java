package io.jayms.xlsx.model.cells;

import io.jayms.xlsx.model.Range;
import io.jayms.xlsx.model.Row;
import io.jayms.xlsx.util.AlphanumericSequence;

/**
 * Represents a cell containing a sub total (a type of formula).
 */
public class SubTotalCell extends FormulaCell {
	
	/**
	 * The sub total function contained in the cell.
	 */
	private SubTotalFunction function;
	/**
	 * The range of cells the function is operating on.
	 */
	private Range range;
	
	/**
	 * Instantiates a SubTotalCell.
	 * @param row - row the cell belongs to.
	 * @param function - sub total function contained in cell.
	 * @param range - range function is operating within.
	 * @param value - existing value 
	 */
	public SubTotalCell(Row row, SubTotalFunction function, Range range, String value) {
		super(row, value);
		this.function = function;
		this.range = range;
	}

	/**
	 * Formulate the subtotal formula.
	 */
	@Override
	public String getFormula() {
		int colIndex = range.getColIndex(); // column index the range operates within.
		String fieldId = new AlphanumericSequence().get(colIndex); // the letter form of that index. i.e. column A, B, C,...
		Row start = range.getStartRow(); // get row at start of range
		Row stop = range.getStopRow(); // get row at end of range
		int startIndex = getRow().getWorksheet().indexOf(start)+1; // get indices of these rows.
		int stopIndex = getRow().getWorksheet().indexOf(stop)+1;
		return "=SUBTOTAL(" + function.getNum() + "," + fieldId + startIndex + ":" + fieldId + stopIndex + ")"; // concatenate together; function number is parameter 1, and the range is parameter 2.
	}
}

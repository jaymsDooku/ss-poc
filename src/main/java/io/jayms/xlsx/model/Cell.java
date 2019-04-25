package io.jayms.xlsx.model;

import io.jayms.xlsx.model.cells.StringCell;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an individual cell in a spreadsheet.
 *
 * @param <T> Type of the value stored in the cell.
 */
public abstract class Cell<T> {

	/**
	 * Row that the cell belongs to.
	 */
	@Getter private final Row row;
	/**
	 * Stored value.
	 */
	@Getter @Setter private T value;
	
	/**
	 * Instantiate Cell.
	 * @param row - row that the cell belongs to. 
	 * @param value - value stored in the cell.
	 */
	public Cell(Row row, T value) {
		this.row = row;
		this.value = value;
	}
	
	/**
	 * @return Textual representation of type of value stored in the cell; required for an attribute in XML tag.
	 */
	public abstract String getType();
	
	/**
	 * Save logic implemented by subclass of cell.
	 * @param save - save data
	 * @param inline - whether the cell should be stored inline (not in shared strings).
	 */
	public abstract void saveCell(Save save, boolean inline);
	
	/**
	 * Saves a cell.
	 * @param save - save data
	 * @param inline - whether the cell should be stored.
	 */
	public void save(Save save, boolean inline) {
		if (inline && !(this instanceof StringCell)) { // If we want to save it inline and it isn't already a string cell,
			StringCell sc = new StringCell(row, getValue().toString()); // Put it into a string cell
			sc.save(save, inline); // And save it as a string cell.
		} else {
			saveCell(save, inline); // Call implementation-specific save logic.
		}
	}
	
}

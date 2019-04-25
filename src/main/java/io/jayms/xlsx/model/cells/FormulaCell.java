package io.jayms.xlsx.model.cells;

import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Cell;
import io.jayms.xlsx.model.Row;
import io.jayms.xlsx.model.Save;

/**
 * Represents a cell with a formula.
 */
public abstract class FormulaCell extends Cell<String> {

	/**
	 * Instantiates a FormulaCell.
	 * 
	 * An abstract class but public-protected constructor to allow for anonymous usage.
	 * 
	 * @param row
	 * @param value
	 */
	public FormulaCell(Row row, String value) {
		super(row, value);
	}

	public abstract String getFormula();

	@Override
	public String getType() {
		return "str";
	}

	@Override
	public void saveCell(Save save, boolean inline) {
		XMLStreamWriter writer = save.getWriter();
		
		try {
			writer.writeStartElement("f"); // wrap formula in a <f> tag.
			writer.writeCharacters(getFormula());
			writer.writeEndElement();
			String val = getValue();
			writer.writeStartElement("v"); // write pre-calculated value into a <v> tag.
			writer.writeCharacters(val);
			writer.writeEndElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

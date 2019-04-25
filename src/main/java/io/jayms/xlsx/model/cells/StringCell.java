package io.jayms.xlsx.model.cells;

import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Cell;
import io.jayms.xlsx.model.Row;
import io.jayms.xlsx.model.Save;
import io.jayms.xlsx.model.SharedStrings;
import io.jayms.xlsx.model.Workbook;

/**
 * Represents a cell storing a string.
 */
public class StringCell extends Cell<String> {
	
	/**
	 * Instantiates StringCell.
	 * @param row
	 * @param value
	 */
	public StringCell(Row row, String value) {
		super(row, value);
	}
	
	@Override
	public String getType() {
		return "s";
	}
	
	@Override
	public void saveCell(Save save, boolean inline) {
		XMLStreamWriter writer = save.getWriter();
		
		Workbook wb = getRow().getWorksheet().getWorkbook();
		SharedStrings sharedStrings = wb.getSharedStrings();
		try {
			String scVal = getValue();
			writer.writeStartElement(inline ? "is" : "v"); // write into <is> if should write inline, otherwise write into <v>. (tertiary operation)
			if (inline) { // if we're inline
				writer.writeStartElement("t"); // writing inline so wrap the string value into a <t> tag.
				writer.writeCharacters(scVal);
				writer.writeEndElement();
			} else { 
				int v = sharedStrings.index(scVal); // not inline, so put this string value into the SharedStrings object and return where it is located. 
				writer.writeCharacters(Integer.toString(v)); // write its index/location into that <v> tag.
			}
			writer.writeEndElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

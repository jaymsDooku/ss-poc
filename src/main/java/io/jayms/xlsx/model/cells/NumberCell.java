package io.jayms.xlsx.model.cells;

import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Cell;
import io.jayms.xlsx.model.Row;
import io.jayms.xlsx.model.Save;

/**
 * Represents a cell with a number stored in it.
 */
public class NumberCell extends Cell<Number> {

	/**
	 * Instantiates a number cell.
	 * @param row - row it belongs to.
	 * @param value - numerical value.
	 */
	public NumberCell(Row row, Number value) {
		super(row, value);
	}

	@Override
	public void saveCell(Save save, boolean inline) {
		XMLStreamWriter writer = save.getWriter();
		
		try {
			Number nVal = getValue();
			writer.writeStartElement("v"); // wrap numerical value int a <v> tag.
			writer.writeCharacters(numberToString(nVal)); // must convert number into string to write it into xml tag first though.
			writer.writeEndElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handle the conversion from number (of any type) to string.
	 * @param number - number to convert.
	 * @return string form of number.
	 */
	private String numberToString(Number number) {
		// Check which kind of instance the number is, then use the according 'toString' method.
		if (number instanceof Byte) {
			return Byte.toString((Byte)number);
		} else if (number instanceof Short) {
			return Short.toString((Short)number);
		} else if (number instanceof Integer) {
			return Integer.toString((Integer)number);
		} else if (number instanceof Long) {
			return Long.toString((Long)number);
		} else if (number instanceof Float) {
			return Float.toString((Float)number);
		} else if (number instanceof Double) {
			return Double.toString((Double)number);
		} else {
			throw new IllegalArgumentException("Unsupported number instance.");
		}
	}

	@Override
	public String getType() {
		return "n";
	}
}

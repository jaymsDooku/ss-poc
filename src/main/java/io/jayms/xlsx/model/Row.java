package io.jayms.xlsx.model;

import java.util.LinkedList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.cells.StringCell;
import io.jayms.xlsx.util.AlphanumericSequence;
import lombok.Getter;
import lombok.Setter;

public class Row implements Part {

	@Getter private final Worksheet worksheet;
	private AlphanumericSequence alphaSeq;
	private LinkedList<Cell> cells;
	
	@Getter @Setter private boolean titleRow;
	@Getter @Setter private int bandAlternateColIndex = 0;
	
	public Row(Worksheet ws) {
		worksheet = ws;
		alphaSeq = new AlphanumericSequence();
		cells = new LinkedList<>();
	}
	
	public Cell<String> string(String value) {
		StringCell cell = new StringCell(this, value);
		cells.add(cell);
		return cell;
	}

	@Override
	public void save(Save save) {
		XMLStreamWriter writer = save.getWriter();
		
		int rowIndex = worksheet.indexOf(this) + 1;
		
		try {
			writer.writeStartElement("row");
			writer.writeAttribute("r", Integer.toString(rowIndex));
			writer.writeAttribute("outlineLevel", "0");
			writer.writeAttribute("hidden", "false");
			writer.writeAttribute("customHeight", "false");
			writer.writeAttribute("ht", "12.8");
			writer.writeAttribute("customFormat", "false");
			
			SharedStrings sharedStrings = worksheet.getWorkbook().getSharedStrings();
			
			int bandColour = save.getBandColour();
			boolean alternateColour = false;
			for (int i = 0; i < cells.size(); i++) {
				Cell c = cells.get(i);
				if (!(c instanceof StringCell)) {
					continue;
				}
				StringCell sc = (StringCell) c;
				int v = sharedStrings.index(sc.getValue());
				
				writer.writeStartElement("c");
				writer.writeAttribute("r", alphaSeq.get(i) + rowIndex);
				writer.writeAttribute("t", "s");
				
				String prevValue = save.getPrevValue();
				String val = sc.getValue();
				save.setPrevValue(val);
				
				if (i == this.bandAlternateColIndex) {
					alternateColour = prevValue != null && !prevValue.equals(val);
				}
				
				writer.writeAttribute("s", Integer.toString(this.isTitleRow() ? 3 : bandColour));
				writer.writeStartElement("v");
				writer.writeCharacters(Integer.toString(v));
				writer.writeEndElement();
				writer.writeEndElement();
			}
			
			writer.writeEndElement();
			
			if (alternateColour) {
				save.setBandColour(bandColour == 1 ? 2 : 1); //alternate band colours
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String toString() {
		String result = "[";
		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			result += c.toString();
			result += ",";
		}
		result += "]";
		return result;
	}
}

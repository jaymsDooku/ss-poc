package io.jayms.xlsx.model;

import java.util.LinkedList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.cells.StringCell;
import io.jayms.xlsx.util.AlphanumericSequence;

public class Row implements Part {

	private final Worksheet worksheet;
	private AlphanumericSequence alphaSeq;
	private LinkedList<Cell> cells;
	
	public Row(Worksheet ws) {
		worksheet = ws;
		alphaSeq = new AlphanumericSequence();
		cells = new LinkedList<>();
	}
	
	public Worksheet worksheet() {
		return worksheet;
	}
	
	public Cell<String> string(String value) {
		StringCell cell = new StringCell(this, value);
		cells.add(cell);
		return cell;
	}

	@Override
	public void save(Save save) {
		XMLStreamWriter writer = save.writer();
		
		int rowIndex = worksheet.indexOf(this) + 1;
		
		try {
			writer.writeStartElement("row");
			writer.writeAttribute("r", Integer.toString(rowIndex));
			writer.writeAttribute("outlineLevel", "0");
			writer.writeAttribute("hidden", "false");
			writer.writeAttribute("customHeight", "false");
			writer.writeAttribute("ht", "12.8");
			writer.writeAttribute("customFormat", "false");
			
			SharedStrings sharedStrings = worksheet.workbook().sharedStrings();
			
			for (int i = 0; i < cells.size(); i++) {
				Cell c = cells.get(i);
				if (!(c instanceof StringCell)) {
					continue;
				}
				StringCell sc = (StringCell) c;
				int v = sharedStrings.index(sc.value());
				
				writer.writeStartElement("c");
				writer.writeAttribute("r", alphaSeq.get(i) + rowIndex);
				writer.writeAttribute("t", "s");
				writer.writeAttribute("s", "0");
				writer.writeStartElement("v");
				writer.writeCharacters(Integer.toString(v));
				writer.writeEndElement();
				writer.writeEndElement();
			}
			
			writer.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}
}

package io.jayms.xlsx.model;

import java.util.LinkedList;

import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.cells.FormulaCell;
import io.jayms.xlsx.model.cells.NumberCell;
import io.jayms.xlsx.model.cells.StringCell;
import io.jayms.xlsx.model.cells.SubTotalCell;
import io.jayms.xlsx.model.cells.SubTotalFunction;
import io.jayms.xlsx.model.meta.Part;
import io.jayms.xlsx.util.AlphanumericSequence;
import lombok.Getter;
import lombok.Setter;

public class Row implements Part {

	@Getter private final Worksheet worksheet;
	@Getter private AlphanumericSequence alphaSeq;
	@Getter private LinkedList<Cell> cells;
	
	@Getter @Setter private boolean titleRow;
	@Getter @Setter private int bandAlternateColIndex = 0;
	
	public Row(Worksheet ws) {
		worksheet = ws;
		alphaSeq = new AlphanumericSequence();
		cells = new LinkedList<>();
	}
	
	public StringCell string(String value) {
		StringCell cell = new StringCell(this, value);
		cells.add(cell);
		return cell;
	}
	
	public NumberCell number(Number value) {
		NumberCell cell = new NumberCell(this, value);
		cells.add(cell);
		return cell;
	}
	
	public SubTotalCell subTotal(SubTotalFunction function, Range range, String value) {
		SubTotalCell cell = new SubTotalCell(this, function, range, value);
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
			
			WorksheetDescriptor wsDesc = save.getWorksheetDescriptor(this.getWorksheet().getName());
			Row headerRow = worksheet.getHeaderRow();
			
			System.out.println("Saving cells...");
			int bandColour = 3;
			boolean[] isInline = new boolean[cells.size()]; // for every field, is it inline?
			for (int i = 0; i < cells.size(); i++) {
				Cell headerCell = headerRow.getCells().get(i);
				if (!(headerCell instanceof StringCell)) { //only string cells in the header row
					continue;
				}
				StringCell sHeaderCell = (StringCell) headerCell;
				String headerVal = sHeaderCell.getValue();
				FieldConfiguration fieldConfig = wsDesc.getFieldConfigs().get(headerVal); // fetch the field config using the field name
				boolean inline = fieldConfig.isInline();
				isInline[i] = inline;
				
				Cell c = cells.get(i);
				Object val = c.getValue();
				if (!isTitleRow()) {
					bandColour = save.getBandColour(this.worksheet.getName(), headerVal, val);
				}
			}
			
			for (int i = 0; i < cells.size(); i++) {
				Cell c = cells.get(i);
				writer.writeStartElement("c");
				writer.writeAttribute("r", alphaSeq.get(i) + rowIndex);
				if (!isTitleRow()) {
					writer.writeAttribute("t", isInline[i] ? "inlineStr" : c.getType());
				} else {
					writer.writeAttribute("t", "inlineStr");
				}
				writer.writeAttribute("s", Integer.toString(bandColour));
				c.save(save, isTitleRow() ? true : isInline[i]);
				writer.writeEndElement();
			}
			writer.writeEndElement();
			System.out.println("Saved cells.");
		} catch (Exception e) {
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

package io.jayms.xlsx.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class Worksheet implements RelationshipPart {
	
	private final Workbook wb;
	private String name;
	private LinkedList<Row> rows;
	
	public Worksheet(Workbook wb, String name) {
		this.wb = wb;
		this.name = name;
		rows = new LinkedList<>();
	}
	
	public Workbook workbook() {
		return wb;
	}
	
	public String name() {
		return name;
	}
	
	public int indexOf(Row row) {
		return rows.indexOf(row);
	}
	
	public Row row() {
		Row row = new Row(this);
		rows.add(row);
		return row;
	}

	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.zos();
		XMLStreamWriter writer = save.writer();
		try {
			zos.putNextEntry(new ZipEntry("xl/" + target()));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("worksheet");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/officeDocument/2006/relationships", "r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships");
			
			writer.writeStartElement("dimension");
			writer.writeAttribute("ref", "A1:E1");
			writer.writeEndElement();
			
			writer.writeStartElement("sheetViews");
			writer.writeStartElement("sheetView");
			writer.writeAttribute("workbookViewId", "0");
			writer.writeAttribute("zoomScalePageLayoutView", "100");
			writer.writeAttribute("zoomScaleNormal", "100");
			writer.writeAttribute("zoomScale", "100");
			writer.writeAttribute("colorId", "64");
			writer.writeAttribute("topLeftCell", "A1");
			writer.writeAttribute("view", "normal");
			writer.writeAttribute("defaultGridColor", "true");
			writer.writeAttribute("tabSelected", "true");
			writer.writeAttribute("rightToLeft", "false");
			writer.writeAttribute("showZeros", "true");
			writer.writeAttribute("showRowColHeaders", "true");
			writer.writeAttribute("showGridLines", "true");
			writer.writeAttribute("showOutlineSymbols", "true");
			writer.writeAttribute("showFormulas", "false");
			writer.writeStartElement("selection");
			writer.writeAttribute("sqref", "E1");
			writer.writeAttribute("activeCellId", "0");
			writer.writeAttribute("activeCell", "E1");
			writer.writeAttribute("pane", "topLeft");
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("sheetFormatPr");
			writer.writeAttribute("outlineLevelCol", "0");
			writer.writeAttribute("outlineLevelRow", "0");
			writer.writeAttribute("zeroHeight", "false");
			writer.writeAttribute("defaultRowHeight", "12.8");
			writer.writeEndElement();
			
			writer.writeStartElement("cols");
			writer.writeStartElement("col");
			writer.writeAttribute("width", "11.52");
			writer.writeAttribute("style", "0");
			writer.writeAttribute("min", "1");
			writer.writeAttribute("max", "1025");
			writer.writeAttribute("outlineLevel", "0");
			writer.writeAttribute("hidden", "false");
			writer.writeAttribute("customWidth", "false");
			writer.writeAttribute("collapsed", "false");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("sheetData");
			for (int i = 0; i < rows.size(); i++) {
				Row row = rows.get(i);
				row.save(save);
			}
			writer.writeEndElement();
			
			writer.writeStartElement("printOptions");
			writer.writeAttribute("verticalCentered", "false");
			writer.writeAttribute("horizontalCentered", "false");
			writer.writeAttribute("gridLinesSet", "true");
			writer.writeAttribute("gridLines", "false");
			writer.writeAttribute("headings", "false");
			writer.writeEndElement();
			
			writer.writeStartElement("pageMargins");
			writer.writeAttribute("footer", "0.7875");
			writer.writeAttribute("header", "0.7875");
			writer.writeAttribute("bottom", "1.052777778");
			writer.writeAttribute("top", "1.052777778");
			writer.writeAttribute("right", "0.7875");
			writer.writeAttribute("left", "0.7875");
			writer.writeEndElement();
			
			writer.writeStartElement("pageSetup");
			writer.writeAttribute("copies", "1");
			writer.writeAttribute("verticalDpi", "300");
			writer.writeAttribute("useFirstPageNumber", "true");
			writer.writeAttribute("cellComments", "none");
			writer.writeAttribute("draft", "false");
			writer.writeAttribute("blackAndWhite", "false");
			writer.writeAttribute("orientation", "false");
			writer.writeAttribute("pageOrder", "downThenOver");
			writer.writeAttribute("fitToHeight", "1");
			writer.writeAttribute("fitToWidth", "1");
			writer.writeAttribute("firstPageNumber", "1");
			writer.writeAttribute("scale", "100");
			writer.writeAttribute("paperSize", "9");
			writer.writeEndElement();
			
			writer.writeStartElement("headerFooter");
			writer.writeAttribute("differentOddEven", "false");
			writer.writeAttribute("differentFirst", "false");
			writer.writeStartElement("oddHeader");
			writer.writeCharacters("&C&\"Times New Roman,Regular\"&12&A");
			writer.writeEndElement();
			writer.writeStartElement("oddFooter");
			writer.writeCharacters("&C&\"Times New Roman,Regular\"&12Page &P");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String target() {
		return "worksheets/" + name.toLowerCase() + ".xml";
	}

	@Override
	public String type() {
		return "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet";
	}
	
	@Override
	public String toString() {
		String result = "[";
		
		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.get(i);
			result += "{ROW" + i + " | " + row.toString();
			if (i < (rows.size() - 1)) {
				result += "}, ";
			}
		}
		
		result += "]";
		return result;
	}

}

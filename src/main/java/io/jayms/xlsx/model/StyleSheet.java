package io.jayms.xlsx.model;

import java.awt.Color;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class StyleSheet implements RelationshipPart {

	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		try {
			zos.putNextEntry(new ZipEntry("xl/styles.xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("styleSheet");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
			
			writer.writeStartElement("numFmts");
			writer.writeAttribute("count", "1");
			writer.writeStartElement("numFmt");
			writer.writeAttribute("formatCode", "General");
			writer.writeAttribute("numFmtId", "164");
			writer.writeEndElement();
			writer.writeEndElement();
			
			Style titleStyle = save.getWorkbook().getTitleStyle();
			DoubleBandFormat dbf = save.getWorkbook().getColourFormat();
			
			writer.writeStartElement("fonts");
			writer.writeAttribute("count", "3");
			
			writeFont(writer, dbf.getStyle1().getFont());
			writeFont(writer, dbf.getStyle2().getFont());
			writeFont(writer, titleStyle.getFont());
			
			writer.writeEndElement();
			
			writer.writeStartElement("fills");
			writer.writeAttribute("count", "4");
			
			writer.writeStartElement("fill");
			writer.writeStartElement("patternFill");
			writer.writeAttribute("patternType", "none");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("fill");
			writer.writeStartElement("patternFill");
			writer.writeAttribute("patternType", "gray125");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writeFill(writer, dbf.getStyle1().getFill());
			writeFill(writer, dbf.getStyle2().getFill());
			writeFill(writer, titleStyle.getFill());
			
			writer.writeEndElement();
			
			writer.writeStartElement("borders");
			writer.writeAttribute("count", "1");
			
			writer.writeStartElement("border");
			writer.writeAttribute("diagonalDown", "false");
			writer.writeAttribute("diagonalUp", "false");
			writer.writeStartElement("left");
			writer.writeEndElement();
			writer.writeStartElement("right");
			writer.writeEndElement();
			writer.writeStartElement("top");
			writer.writeEndElement();
			writer.writeStartElement("bottom");
			writer.writeEndElement();
			writer.writeStartElement("diagonal");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeStartElement("cellStyleXfs");
			writer.writeAttribute("count", "4");
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("applyProtection", "0");
			writer.writeAttribute("applyAlignment", "0");
			writer.writeAttribute("applyBorder", "0");
			writer.writeAttribute("applyFont", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "0");
			writer.writeAttribute("fontId", "0");
			writer.writeEndElement();
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("applyProtection", "0");
			writer.writeAttribute("applyAlignment", "0");
			writer.writeAttribute("applyBorder", "0");
			writer.writeAttribute("applyFont", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "3");
			writer.writeAttribute("fontId", "1");
			writer.writeEndElement();
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("applyProtection", "0");
			writer.writeAttribute("applyAlignment", "0");
			writer.writeAttribute("applyBorder", "0");
			writer.writeAttribute("applyFont", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "2");
			writer.writeAttribute("fontId", "0");
			writer.writeEndElement();
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("applyProtection", "0");
			writer.writeAttribute("applyAlignment", "0");
			writer.writeAttribute("applyBorder", "0");
			writer.writeAttribute("applyFont", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "4");
			writer.writeAttribute("fontId", "2");
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeStartElement("cellXfs");
			writer.writeAttribute("count", "4");
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "0");
			writer.writeAttribute("fontId", "0");
			writer.writeAttribute("xfId", "0");
			writer.writeEndElement();
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "3");
			writer.writeAttribute("fontId", "1");
			writer.writeAttribute("xfId", "1");
			writer.writeEndElement();
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "2");
			writer.writeAttribute("fontId", "0");
			writer.writeAttribute("xfId", "2");
			writer.writeEndElement();
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "4");
			writer.writeAttribute("fontId", "2");
			writer.writeAttribute("xfId", "3");
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeFill(XMLStreamWriter writer, Fill fill) throws XMLStreamException {
		writer.writeStartElement("fill");
		
		writer.writeStartElement("patternFill");
		writer.writeAttribute("patternType", "solid");
		
		writer.writeStartElement("fgColor");
		writer.writeAttribute("rgb", Fill.toHex(fill.getColor()));
		writer.writeEndElement();
		
		writer.writeEndElement();
		
		writer.writeEndElement();
	}
	
	private void writeFont(XMLStreamWriter writer, Font font) throws XMLStreamException {
		writer.writeStartElement("font");
		
		if (font.isBold()) {
			writer.writeStartElement("b");
			writer.writeEndElement();
		}
		writer.writeStartElement("sz");
		writer.writeAttribute("val", Integer.toString(font.getSize()));
		writer.writeEndElement();
		writer.writeStartElement("name");
		writer.writeAttribute("val", font.getName());
		writer.writeEndElement();
		writer.writeStartElement("family");
		writer.writeAttribute("val", Integer.toString(font.getFamily()));
		writer.writeEndElement();
		writer.writeStartElement("color");
		writer.writeAttribute("rgb", Fill.toHex(font.getColor()));
		writer.writeEndElement();
		
		writer.writeEndElement();
	}

	@Override
	public String target() {
		return "styles.xml";
	}

	@Override
	public String type() {
		return "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles";
	}

}

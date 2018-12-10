package io.jayms.xlsx.model;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class StyleSheet implements RelationshipPart {

	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.zos();
		XMLStreamWriter writer = save.writer();
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
			
			writer.writeStartElement("fonts");
			writer.writeAttribute("count", "1");
			
			writer.writeStartElement("font");
			writer.writeStartElement("sz");
			writer.writeAttribute("val", "10");
			writer.writeEndElement();
			writer.writeStartElement("name");
			writer.writeAttribute("val", "Arial");
			writer.writeEndElement();
			writer.writeStartElement("family");
			writer.writeAttribute("val", "2");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeStartElement("fills");
			writer.writeAttribute("count", "1");
			
			writer.writeStartElement("fill");
			writer.writeStartElement("patternFill");
			writer.writeAttribute("patternType", "none");
			writer.writeEndElement();
			writer.writeEndElement();
			
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
			writer.writeAttribute("count", "1");
			
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
			writer.writeEndElement();
			
			writer.writeStartElement("cellXfs");
			writer.writeAttribute("count", "1");
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "0");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "0");
			writer.writeAttribute("fontId", "0");
			writer.writeAttribute("xfId", "0");
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeStartElement("cellStyles");
			writer.writeAttribute("count", "1");
			
			writer.writeStartElement("cellStyle");
			writer.writeAttribute("xfId", "0");
			writer.writeAttribute("customBuiltin", "false");
			writer.writeAttribute("builtinId", "0");
			writer.writeAttribute("name", "Normal");
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
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

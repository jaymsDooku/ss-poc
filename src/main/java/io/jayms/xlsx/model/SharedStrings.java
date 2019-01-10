package io.jayms.xlsx.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class SharedStrings implements RelationshipPart {

	private ArrayList<String> sharedStrings = new ArrayList<>();
	
	private int stringCount = 0;
	
	public void clearStringCount() {
		stringCount = 0;
	}
	
	public int index(String s) {
		int ind = sharedStrings.indexOf(s);
		if (ind == -1) {
			sharedStrings.add(s);
			ind = sharedStrings.indexOf(s);
		}
		stringCount++;
		return ind;
	}
	
	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		try {
			zos.putNextEntry(new ZipEntry("xl/sharedStrings.xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("sst");
			writer.writeAttribute("uniqueCount", Integer.toString(sharedStrings.size()));
			writer.writeAttribute("count", Integer.toString(stringCount));
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
			
			for (int i = 0; i < sharedStrings.size(); i++) {
				writer.writeStartElement("si");
				writer.writeStartElement("t");
				writer.writeAttribute("xml", "http://schemas.openxmlformats.org/spreadsheetml/2006/main", "space", "preserve");
				writer.writeCharacters(sharedStrings.get(i));
				writer.writeEndElement();
				writer.writeEndElement();
			}
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String target() {
		return "sharedStrings.xml";
	}

	@Override
	public String type() {
		return "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings";
	}

}

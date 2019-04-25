package io.jayms.xlsx.model.meta;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Save;

public class AppProperties implements Part {

	private int totalTime = 0;
	
	public AppProperties() {
	}

	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		try {
			zos.putNextEntry(new ZipEntry("docProps/app.xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("Properties");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes", "vt", "http://schemas.openxmlformat.org/officeDocument/2006/docPropsVTypes");
			
			writer.writeStartElement("TotalTime");
			writer.writeCharacters("0");
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

package io.jayms.xlsx.model.meta;

import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Save;

public class CoreProperties implements Part {

	private String title;
	private String subject;
	private String description;
	private String creator;
	private Date created;
	private Date modified;
	
	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		try {
			zos.putNextEntry(new ZipEntry("docProps/core.xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("cp", "coreProperties", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties", "cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
			writer.writeAttribute("xmlns", "http://www.w3.org/2001/XMLSchema-instance", "xsi", "http://www.w3.org/2001/XMLSchema-instance");
			writer.writeAttribute("xmlns", "http://purl.org/dc/dcmitype/", "dcmitype", "http://purl.org/dc/dcmitype/");
			writer.writeAttribute("xmlns", "http://purl.org/dc/terms/", "dcterms", "http://purl.org/dc/terms/");
			writer.writeAttribute("xmlns", "http://purl.org/dc/elements/1.1/", "dc", "http://purl.org/dc/elements/1.1/");
			
			writer.writeStartElement("dcterms", "created", "http://purl.org/dc/terms/");
			writer.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");
			writer.writeCharacters("2018-11-06T18:23:13Z");
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "creator", "http://purl.org/dc/elements/1.1/");
			if (creator != null) {
				writer.writeCharacters(creator);
			}
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "description", "http://purl.org/dc/elements/1.1/");
			if (description != null) {
				writer.writeCharacters(description);
			}
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "language", "http://purl.org/dc/elements/1.1/");
			writer.writeCharacters("en-GB");
			writer.writeEndElement();
			
			writer.writeStartElement("cp", "lastModifiedBy", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
			if (creator != null) {
				writer.writeCharacters(creator);
			}
			writer.writeEndElement();
			
			writer.writeStartElement("dcterms", "modified", "http://purl.org/dc/terms/");
			writer.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");
			writer.writeCharacters("2018-11-06T18:23:58Z");
			writer.writeEndElement();
			
			writer.writeStartElement("cp", "revision", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
			writer.writeCharacters("1");
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "subject", "http://purl.org/dc/elements/1.1/");
			if (subject != null) {
				writer.writeCharacters(subject);
			}
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "title", "http://purl.org/dc/elements/1.1/");
			if (title != null) {
				writer.writeCharacters(title);
			}
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

}

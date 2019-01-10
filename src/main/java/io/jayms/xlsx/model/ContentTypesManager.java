package io.jayms.xlsx.model;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ContentTypesManager implements Part {

	private final Workbook workbook;
	
	public ContentTypesManager(Workbook workbook) {
		this.workbook = workbook;
	}
	
	@Override
	public void save(Save save) {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		try {
			
			zos.putNextEntry(new ZipEntry("[Content_Types].xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("Types");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/content-types");
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/_rels/.rels");
			writer.writeAttribute("ContentType", ContentTypes.RELATIONSHIPS);
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/docProps/core.xml");
			writer.writeAttribute("ContentType", ContentTypes.CORE);
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/docProps/app.xml");
			writer.writeAttribute("ContentType", ContentTypes.APP);
			writer.writeEndElement();
			
			Collection<Worksheet> worksheets = workbook.getWorksheets();
			for (Worksheet ws : worksheets) {
				writer.writeStartElement("Override");
				writer.writeAttribute("PartName", "/xl/" + ws.target());
				writer.writeAttribute("ContentType", ContentTypes.WORKSHEET);
				writer.writeEndElement();
			}
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/workbook.xml");
			writer.writeAttribute("ContentType", ContentTypes.WORKBOOK);
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/sharedStrings.xml");
			writer.writeAttribute("ContentType", ContentTypes.SHARED_STRINGS);
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/styles.xml");
			writer.writeAttribute("ContentType", ContentTypes.STYLES);
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/_rels/workbook.xml.rels");
			writer.writeAttribute("ContentType", ContentTypes.RELATIONSHIPS);
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

}

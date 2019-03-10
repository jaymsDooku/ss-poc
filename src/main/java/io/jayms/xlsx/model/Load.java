package io.jayms.xlsx.model;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import lombok.Getter;

public class Load {

	@Getter private ZipFile zipFile;
	@Getter private Workbook workbook;
	
	public Load(File file, Workbook workbook) {
		if (!file.exists()) {
			throw new IllegalArgumentException("Can't load from a file that doesn't exist.");
		}
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			zipFile = new ZipFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public XMLStreamReader getReader(String entryName) throws XMLStreamException, IOException {
		ZipEntry entry = zipFile.getEntry(entryName);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		return factory.createXMLStreamReader(zipFile.getInputStream(entry));
	}
	
	public void close() {
		try {
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

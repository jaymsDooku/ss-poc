package io.jayms.xlsx.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class Save {

	private ZipOutputStream zos;
	private XMLStreamWriter writer;
	
	public Save(File file) {
		if (!file.exists()) {
			if (file.getParentFile() != null) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
			}
			try {
				if (file.createNewFile()) {
					System.out.println("Created " + file.getName());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		try {
			zos = new ZipOutputStream(new FileOutputStream(file));
			writer = factory.createXMLStreamWriter(zos, "UTF-8");
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public ZipOutputStream zos() {
		return zos;
	}
	
	public XMLStreamWriter writer() {
		return writer;
	}
	
	public void close() {
		try {
			writer.close();
			zos.flush();
			zos.close();
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}
}

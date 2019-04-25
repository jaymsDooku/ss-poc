package io.jayms.xlsx.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import lombok.Getter;
import lombok.Setter;

public class Save {

	@Getter private ZipOutputStream zos;
	@Getter private XMLStreamWriter writer;
	@Getter @Setter private Workbook workbook;
	
	@Getter @Setter private Object prevValue;
	@Getter @Setter private int bandColour = 1;
	@Getter @Setter private Set<WorksheetDescriptor> worksheetDescriptors;
	
	public Save(File file, Workbook workbook,  Set<WorksheetDescriptor> worksheetDescriptors) {
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
		
		this.workbook = workbook;
		this.worksheetDescriptors = worksheetDescriptors;
	}
	
	public int getBandColour(String wsName, String colName, Object newVal) {
		WorksheetDescriptor wsDesc = getWorksheetDescriptor(wsName);
		if (wsDesc.getFieldConfigs().containsKey(colName)) {
			FieldConfiguration fieldConfig = wsDesc.getFieldConfigs().get(colName);
			//System.out.println("bandColour fieldConfig: " + fieldConfig);
			if (fieldConfig.isSwapBandOnChange()) {
				//System.out.println("prevValue: " + prevValue);
				//System.out.println("newValue: " + newVal);
				if (prevValue != null && !prevValue.equals(newVal)) {
					//System.out.println("swapped colours");
					setBandColour(bandColour == 1 ? 2 : 1);
				}
				setPrevValue(newVal);
			}
		}
		return bandColour;
	}
	
	public WorksheetDescriptor getWorksheetDescriptor(String wsName) {
		return worksheetDescriptors.stream().filter(wd -> wd.getWorksheetName().equals(wsName)).findFirst().orElse(null);
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

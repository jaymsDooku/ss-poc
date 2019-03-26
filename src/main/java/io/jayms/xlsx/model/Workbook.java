package io.jayms.xlsx.model;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import lombok.Getter;
import lombok.Setter;

public class Workbook implements Part {
	
	@Getter private String name;
	@Getter private RelationshipManager rsMan;
	@Getter private ContentTypesManager contentTypes;
	@Getter private CoreProperties coreProps;
	@Getter private AppProperties appProps;
	@Getter private StyleSheet styleSheet;
	@Getter private SharedStrings sharedStrings;
	@Getter private LinkedList<Worksheet> sheets;
	@Getter private DoubleBandFormat colourFormat;
	@Getter private Style titleStyle;
	@Getter private FontManager fontManager;
	@Getter private StyleTable styleTable;
	
	public Workbook(File file) {
		this(file.getName());
		String name = file.getName();
		System.out.println("File Name: " + name);
		try {
			load(file);
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public Workbook(String name) {
		this.name = name;
		contentTypes = new ContentTypesManager(this);
		styleSheet = new StyleSheet();
		sharedStrings = new SharedStrings();
		rsMan = new RelationshipManager(this);
		rsMan.styleSheet(styleSheet);
		rsMan.sharedStrings(sharedStrings);
		
		fontManager = new FontManager();
		styleTable = new StyleTable(this);
		
		sheets = new LinkedList<>();
		appProps = new AppProperties();
		coreProps = new CoreProperties();
		
		this.colourFormat = new DoubleBandFormat(styleTable.getStyle(7),
				styleTable.getStyle(21));
		
		Color tc = new Color(102, 153, 153, 255);
		Font tf = fontManager.getFont(fontManager.createFont("DengXian", 12, true, new Color(0, 0, 0, 255)));
		
		this.titleStyle = new Style(tf, new Fill(tc));
	}
	
	public Worksheet getWorksheet(String worksheetName) {
		return sheets.stream().filter(ws -> ws.getName().equals(worksheetName)).findFirst().orElse(null);
	}
	
	public boolean hasWorksheet(String worksheetName) {
		return sheets.stream().filter(ws -> ws.getName().equals(worksheetName)).findFirst().isPresent();
	}
	
	public Collection<Worksheet> getWorksheets() {
		return Collections.unmodifiableCollection(sheets);
	}
	
	public Worksheet createSheet(String name) {
		Worksheet sheet = new Worksheet(this, name);
		sheets.add(sheet);
		return sheet;
	}

	@Override
	public void save(Save save) {
		try {
			sharedStrings.clearStringCount();
			
			contentTypes.save(save);
			System.out.println("Saved content types");
			
			appProps.save(save);
			System.out.println("Saved app props");
			
			coreProps.save(save);
			System.out.println("Saved core types");
			
			saveWorkbookRelationships(save);
			System.out.println("Saved wb relationships");
			
			saveWorkbook(save);
			System.out.println("Saved wb");
			
			rsMan.save(save);
			System.out.println("Saved relationships");
			
			sharedStrings.save(save);
			System.out.println("Saved shared strings");
			
			styleSheet.save(save);
			System.out.println("Saved style sheet");
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(File file) {
		Save save = new Save(file, this);
		save(save);
		save.close();
	}
	
	private void saveWorkbook(Save save) throws IOException, XMLStreamException {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		zos.putNextEntry(new ZipEntry("xl/workbook.xml"));
		writer.writeStartDocument("UTF-8", "1.0");
		writer.writeStartElement("workbook");
		writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/officeDocument/2006/relationships", "r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships");
		writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
		
		writer.writeStartElement("fileVersion");
		writer.writeAttribute("appName", "Calc");
		writer.writeEndElement();
		
		writer.writeStartElement("workbookPr");
		writer.writeAttribute("date1904", "false");
		writer.writeAttribute("showObjects", "all");
		writer.writeAttribute("backupFile", "false");
		writer.writeEndElement();
		
		writer.writeStartElement("workbookProtection");
		writer.writeEndElement();
		
		writer.writeStartElement("bookViews");
		writer.writeStartElement("workbookView");
		writer.writeAttribute("activeTab", "0");
		writer.writeAttribute("firstSheet", "0");
		writer.writeAttribute("tabRatio", "500");
		writer.writeAttribute("windowHeight", "8192");
		writer.writeAttribute("windowWidth", "16384");
		writer.writeAttribute("yWindow", "0");
		writer.writeAttribute("xWindow", "0");
		writer.writeAttribute("showSheetTabs", "true");
		writer.writeAttribute("showVerticalScroll", "true");
		writer.writeAttribute("showHorizontalScroll", "0");
		writer.writeEndElement();
		writer.writeEndElement();
		
		writer.writeStartElement("sheets");
		for (int i = 0; i < sheets.size(); i++) {
			Worksheet ws = sheets.get(i);
			String name = ws.getName();
			String rId = rsMan.id(ws);
			String sheetId = Integer.toString((i+1));
			writer.writeStartElement("sheet");
			writer.writeAttribute("r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id", rId);
			writer.writeAttribute("sheetId", sheetId);
			writer.writeAttribute("name", name);
			writer.writeEndElement();
		}
		writer.writeEndElement();
		
		writer.writeStartElement("calcPr");
		writer.writeAttribute("iterateDelta", "0.001");
		writer.writeAttribute("iterate", "false");
		writer.writeAttribute("refMode", "A1");
		writer.writeAttribute("iterateCount", "100");
		writer.writeEndElement();
		
		writer.writeEndElement();
		writer.writeEndDocument();
		zos.closeEntry();
		
		for (Worksheet ws : sheets) {
			ws.save(save);
		}
	}
	
	private void saveWorkbookRelationships(Save save) throws XMLStreamException, IOException {
		ZipOutputStream zos = save.getZos();
		XMLStreamWriter writer = save.getWriter();
		zos.putNextEntry(new ZipEntry("_rels/.rels"));
		writer.writeStartDocument("UTF-8", "1.0");
		writer.writeStartElement("Relationships");
		writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");
		
		writer.writeStartElement("Relationship");
		writer.writeAttribute("Target", "xl/workbook.xml");
		writer.writeAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument");
		writer.writeAttribute("Id", "rId1");
		writer.writeEndElement();
		
		writer.writeStartElement("Relationship");
		writer.writeAttribute("Target", "docProps/core.xml");
		writer.writeAttribute("Type", "http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties");
		writer.writeAttribute("Id", "rId2");
		writer.writeEndElement();
		
		writer.writeStartElement("Relationship");
		writer.writeAttribute("Target", "docProps/app.xml");
		writer.writeAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties");
		writer.writeAttribute("Id", "rId3");
		writer.writeEndElement();
		
		writer.writeEndElement();
		writer.writeEndDocument();
		zos.closeEntry();
	}
	
	public void load(File file) throws XMLStreamException, IOException {
		Load load = new Load(file, this);
		load(load);
		load.close();
	}
	
	public void load(Load load) throws XMLStreamException, IOException {
		XMLStreamReader reader = load.getReader("xl/workbook.xml");
		while (reader.hasNext()) {
			int index = reader.next();
			QName qname = reader.getName();
			System.out.println("index: " + index);
			System.out.println("prefix: " + qname.getPrefix());
			System.out.println("localpart: " + qname.getLocalPart());
			System.out.println("namespace: " + qname.getNamespaceURI());
		}
	}
	
}

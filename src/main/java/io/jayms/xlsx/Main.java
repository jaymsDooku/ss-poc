package io.jayms.xlsx;

import java.io.File;

import io.jayms.xlsx.db.DatabaseConverter;
import io.jayms.xlsx.db.SQLiteDatabase;
import io.jayms.xlsx.model.Workbook;
import io.jayms.xlsx.model.Worksheet;

public class Main {

	/*public static void main(String[] args) {
		File file = new File("exampleXML.xlsx");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		
		/*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			
			String namespace = "http://schemas.openxmlformat.org/package/2006/metadata/core-properties";
			Element root = doc.createElementNS(namespace, "root");
			root.setAttributeNS(namespace, "revision", "1");
			
			System.out.println("doc: " + ((DOMImplementationLS)doc.getImplementation().getFeature("LS", "3.0")).createLSSerializer().writeToString(doc));
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty(OutputKeys.METHOD, "xml");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			t.transform(new DOMSource(doc), new StreamResult(Files.newOutputStream(file.toPath())));
		} catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException | IOException e) {
			e.printStackTrace();
		}*/
		
		/*XMLOutputFactory factory = XMLOutputFactory.newInstance();
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
			XMLStreamWriter writer = factory.createXMLStreamWriter(zos, "UTF-8");
			
			zos.putNextEntry(new ZipEntry("[Content_Types].xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("Types");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/content-types");
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/_rels/.rels");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-package.relationships+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/docProps/core.xml");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-package.core-properties+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/docProps/app.xml");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-officedocument.extended-properties+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/worksheets/sheet1.xml");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/workbook.xml");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/sharedStrings.xml");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/styles.xml");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml");
			writer.writeEndElement();
			
			writer.writeStartElement("Override");
			writer.writeAttribute("PartName", "/xl/_rels/workbook.xml.rels");
			writer.writeAttribute("ContentType", "application/vnd.openxmlformats-package.relationships+xml");
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
			
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
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "description", "http://purl.org/dc/elements/1.1/");
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "language", "http://purl.org/dc/elements/1.1/");
			writer.writeCharacters("en-GB");
			writer.writeEndElement();
			
			writer.writeStartElement("cp", "lastModifiedBy", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
			writer.writeEndElement();
			
			writer.writeStartElement("dcterms", "modified", "http://purl.org/dc/terms/");
			writer.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");
			writer.writeCharacters("2018-11-06T18:23:58Z");
			writer.writeEndElement();
			
			writer.writeStartElement("cp", "revision", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
			writer.writeCharacters("1");
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "subject", "http://purl.org/dc/elements/1.1/");
			writer.writeEndElement();
			
			writer.writeStartElement("dc", "title", "http://purl.org/dc/elements/1.1/");
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
			
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
			writer.writeStartElement("sheet");
			writer.writeAttribute("r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id", "rId2");
			writer.writeAttribute("sheetId", "1");
			writer.writeAttribute("name", "Sheet1");
			writer.writeEndElement();
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
			
			zos.putNextEntry(new ZipEntry("xl/sharedStrings.xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("sst");
			writer.writeAttribute("uniqueCount", "4");
			writer.writeAttribute("count", "5");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
			
			writer.writeStartElement("si");
			writer.writeStartElement("t");
			writer.writeAttribute("xml", "http://schemas.openxmlformats.org/spreadsheetml/2006/main", "space", "preserve");
			writer.writeCharacters("beep");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("si");
			writer.writeStartElement("t");
			writer.writeAttribute("xml", "http://schemas.openxmlformats.org/spreadsheetml/2006/main", "space", "preserve");
			writer.writeCharacters("im");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("si");
			writer.writeStartElement("t");
			writer.writeAttribute("xml", "http://schemas.openxmlformats.org/spreadsheetml/2006/main", "space", "preserve");
			writer.writeCharacters("a");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("si");
			writer.writeStartElement("t");
			writer.writeAttribute("xml", "http://schemas.openxmlformats.org/spreadsheetml/2006/main", "space", "preserve");
			writer.writeCharacters("jeep");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();*/
			
			/*zos.putNextEntry(new ZipEntry("xl/styles.xml"));
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
			writer.writeAttribute("count", "4");
			
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
			
			writer.writeStartElement("font");
			writer.writeStartElement("sz");
			writer.writeAttribute("val", "10");
			writer.writeEndElement();
			writer.writeStartElement("name");
			writer.writeAttribute("val", "Arial");
			writer.writeEndElement();
			writer.writeStartElement("family");
			writer.writeAttribute("val", "0");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("font");
			writer.writeStartElement("sz");
			writer.writeAttribute("val", "10");
			writer.writeEndElement();
			writer.writeStartElement("name");
			writer.writeAttribute("val", "Arial");
			writer.writeEndElement();
			writer.writeStartElement("family");
			writer.writeAttribute("val", "0");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("font");
			writer.writeStartElement("sz");
			writer.writeAttribute("val", "10");
			writer.writeEndElement();
			writer.writeStartElement("name");
			writer.writeAttribute("val", "Arial");
			writer.writeEndElement();
			writer.writeStartElement("family");
			writer.writeAttribute("val", "0");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeStartElement("fills");
			writer.writeAttribute("count", "2");
			
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
			writer.writeAttribute("count", "20");
			
			writer.writeStartElement("xf");
			writer.writeAttribute("numFmtId", "164");
			writer.writeAttribute("applyProtection", "true");
			writer.writeAttribute("applyAlignment", "true");
			writer.writeAttribute("applyBorder", "true");
			writer.writeAttribute("applyFont", "true");
			writer.writeAttribute("borderId", "0");
			writer.writeAttribute("fillId", "0");
			writer.writeAttribute("fontId", "0");
			writer.writeStartElement("alignment");
			writer.writeAttribute("shrinkToFit", "false");
			writer.writeAttribute("indent", "0");
			writer.writeAttribute("wrapText", "false");
			writer.writeAttribute("textRotation", "0");
			writer.writeAttribute("vertical", "bottom");
			writer.writeAttribute("horizontal", "false");
			writer.writeEndElement();
			writer.writeStartElement("protection");
			writer.writeAttribute("hidden", "false");
			writer.writeAttribute("locked", "true");
			writer.writeEndElement();
			writer.writeEndElement();
			
			xf(writer, "0", "false", "false", "false", "true", "0", "0", "1");
			xf(writer, "0", "false", "false", "false", "true", "0", "0", "1");
			xf(writer, "0", "false", "false", "false", "true", "0", "0", "2");
			xf(writer, "0", "false", "false", "false", "true", "0", "0", "2");
			for (int i = 0; i < 10; i++) {
				xf(writer, "0", "false", "false", "false", "true", "0", "0", "0");
			}
			xf(writer, "43", "false", "false", "false", "true", "0", "0", "1");
			xf(writer, "41", "false", "false", "false", "true", "0", "0", "1");
			xf(writer, "44", "false", "false", "false", "true", "0", "0", "1");
			xf(writer, "42", "false", "false", "false", "true", "0", "0", "1");
			xf(writer, "9", "false", "false", "false", "true", "0", "0", "1");
			
			writer.writeEndElement();
			
			writer.writeStartElement("cellXfs");
			writer.writeAttribute("count", "1");
			
			xf2(writer, "164", "false", "false", "false", "false", "0", "0", "0", "0");
			
			writer.writeEndElement();
			
			writer.writeStartElement("cellStyles");
			writer.writeAttribute("count", "6");
			
			cellStyle(writer, "0", "false", "0", "Normal");
			cellStyle(writer, "15", "false", "3", "Comma");
			cellStyle(writer, "16", "false", "6", "Comma [0]");
			cellStyle(writer, "17", "false", "4", "Currency");
			cellStyle(writer, "18", "false", "7", "Currency [0]");
			cellStyle(writer, "19", "false", "5", "Percent");
			
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();*/
			
			/*zos.putNextEntry(new ZipEntry("xl/styles.xml"));
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
			
			cellStyle(writer, "0", "false", "0", "Normal");
			
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
			
			zos.putNextEntry(new ZipEntry("xl/_rels/workbook.xml.rels"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("Relationships");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");
			
			writer.writeStartElement("Relationship");
			writer.writeAttribute("Target", "worksheets/sheet1.xml");
			writer.writeAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet");
			writer.writeAttribute("Id", "rId2");
			writer.writeEndElement();
			
			writer.writeStartElement("Relationship");
			writer.writeAttribute("Target", "sharedStrings.xml");
			writer.writeAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings");
			writer.writeAttribute("Id", "rId3");
			writer.writeEndElement();
			
			writer.writeStartElement("Relationship");
			writer.writeAttribute("Target", "styles.xml");
			writer.writeAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles");
			writer.writeAttribute("Id", "rId1");
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
			
			zos.putNextEntry(new ZipEntry("xl/worksheets/sheet1.xml"));
			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("worksheet");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/officeDocument/2006/relationships", "r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships");
			
			writer.writeStartElement("dimension");
			writer.writeAttribute("ref", "A1:E1");
			writer.writeEndElement();
			
			writer.writeStartElement("sheetViews");
			writer.writeStartElement("sheetView");
			writer.writeAttribute("workbookViewId", "0");
			writer.writeAttribute("zoomScalePageLayoutView", "100");
			writer.writeAttribute("zoomScaleNormal", "100");
			writer.writeAttribute("zoomScale", "100");
			writer.writeAttribute("colorId", "64");
			writer.writeAttribute("topLeftCell", "A1");
			writer.writeAttribute("view", "normal");
			writer.writeAttribute("defaultGridColor", "true");
			writer.writeAttribute("tabSelected", "true");
			writer.writeAttribute("rightToLeft", "false");
			writer.writeAttribute("showZeros", "true");
			writer.writeAttribute("showRowColHeaders", "true");
			writer.writeAttribute("showGridLines", "true");
			writer.writeAttribute("showOutlineSymbols", "true");
			writer.writeAttribute("showFormulas", "false");
			writer.writeStartElement("selection");
			writer.writeAttribute("sqref", "E1");
			writer.writeAttribute("activeCellId", "0");
			writer.writeAttribute("activeCell", "E1");
			writer.writeAttribute("pane", "topLeft");
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("sheetFormatPr");
			writer.writeAttribute("outlineLevelCol", "0");
			writer.writeAttribute("outlineLevelRow", "0");
			writer.writeAttribute("zeroHeight", "false");
			writer.writeAttribute("defaultRowHeight", "12.8");
			writer.writeEndElement();
			
			writer.writeStartElement("cols");
			writer.writeStartElement("col");
			writer.writeAttribute("width", "11.52");
			writer.writeAttribute("style", "0");
			writer.writeAttribute("min", "1");
			writer.writeAttribute("max", "1025");
			writer.writeAttribute("outlineLevel", "0");
			writer.writeAttribute("hidden", "false");
			writer.writeAttribute("customWidth", "false");
			writer.writeAttribute("collapsed", "false");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("sheetData");
			writer.writeStartElement("row");
			writer.writeAttribute("r", "1");
			writer.writeAttribute("outlineLevel", "0");
			writer.writeAttribute("hidden", "false");
			writer.writeAttribute("customHeight", "false");
			writer.writeAttribute("ht", "12.8");
			writer.writeAttribute("customFormat", "false");
			
			writer.writeStartElement("c");
			writer.writeAttribute("r", "A1");
			writer.writeAttribute("t", "s");
			writer.writeAttribute("s", "0");
			writer.writeStartElement("v");
			writer.writeCharacters("0");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("c");
			writer.writeAttribute("r", "B1");
			writer.writeAttribute("t", "s");
			writer.writeAttribute("s", "0");
			writer.writeStartElement("v");
			writer.writeCharacters("0");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("c");
			writer.writeAttribute("r", "C1");
			writer.writeAttribute("t", "s");
			writer.writeAttribute("s", "0");
			writer.writeStartElement("v");
			writer.writeCharacters("1");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("c");
			writer.writeAttribute("r", "D1");
			writer.writeAttribute("t", "s");
			writer.writeAttribute("s", "0");
			writer.writeStartElement("v");
			writer.writeCharacters("2");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeStartElement("c");
			writer.writeAttribute("r", "E1");
			writer.writeAttribute("t", "s");
			writer.writeAttribute("s", "0");
			writer.writeStartElement("v");
			writer.writeCharacters("3");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeStartElement("printOptions");
			writer.writeAttribute("verticalCentered", "false");
			writer.writeAttribute("horizontalCentered", "false");
			writer.writeAttribute("gridLinesSet", "true");
			writer.writeAttribute("gridLines", "false");
			writer.writeAttribute("headings", "false");
			writer.writeEndElement();
			
			writer.writeStartElement("pageMargins");
			writer.writeAttribute("footer", "0.7875");
			writer.writeAttribute("header", "0.7875");
			writer.writeAttribute("bottom", "1.052777778");
			writer.writeAttribute("top", "1.052777778");
			writer.writeAttribute("right", "0.7875");
			writer.writeAttribute("left", "0.7875");
			writer.writeEndElement();
			
			writer.writeStartElement("pageSetup");
			writer.writeAttribute("copies", "1");
			writer.writeAttribute("verticalDpi", "300");
			writer.writeAttribute("useFirstPageNumber", "true");
			writer.writeAttribute("cellComments", "none");
			writer.writeAttribute("draft", "false");
			writer.writeAttribute("blackAndWhite", "false");
			writer.writeAttribute("orientation", "false");
			writer.writeAttribute("pageOrder", "downThenOver");
			writer.writeAttribute("fitToHeight", "1");
			writer.writeAttribute("fitToWidth", "1");
			writer.writeAttribute("firstPageNumber", "1");
			writer.writeAttribute("scale", "100");
			writer.writeAttribute("paperSize", "9");
			writer.writeEndElement();
			
			writer.writeStartElement("headerFooter");
			writer.writeAttribute("differentOddEven", "false");
			writer.writeAttribute("differentFirst", "false");
			writer.writeStartElement("oddHeader");
			writer.writeCharacters("&C&\"Times New Roman,Regular\"&12&A");
			writer.writeEndElement();
			writer.writeStartElement("oddFooter");
			writer.writeCharacters("&C&\"Times New Roman,Regular\"&12Page &P");
			writer.writeEndElement();
			writer.writeEndElement();
			
			writer.writeEndElement();
			writer.writeEndDocument();
			zos.closeEntry();
			
			writer.close();
			
			zos.flush();
			zos.close();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void xf(XMLStreamWriter writer, String numFmtId,
			String applyProt, String applyAlign, String applyBorder, String applyFont,
			String borderId, String fillId, String fontId) throws XMLStreamException {
		writer.writeStartElement("xf");
		writer.writeAttribute("numFmtId", numFmtId);
		writer.writeAttribute("applyProtection", applyProt);
		writer.writeAttribute("applyAlignment", applyAlign);
		writer.writeAttribute("applyBorder", applyBorder);
		writer.writeAttribute("borderId", borderId);
		writer.writeAttribute("fillId", fillId);
		writer.writeAttribute("fontId", fontId);
		writer.writeEndElement();
	}
	
	private static void xf2(XMLStreamWriter writer, String numFmtId,
			String applyProt, String applyAlign, String applyBorder, String applyFont,
			String borderId, String fillId, String fontId, String xfId) throws XMLStreamException {
		writer.writeStartElement("xf");
		writer.writeAttribute("numFmtId", numFmtId);
		writer.writeAttribute("applyProtection", applyProt);
		writer.writeAttribute("applyAlignment", applyAlign);
		writer.writeAttribute("applyBorder", applyBorder);
		writer.writeAttribute("borderId", borderId);
		writer.writeAttribute("fillId", fillId);
		writer.writeAttribute("fontId", fontId);
		writer.writeAttribute("xfId", xfId);
		writer.writeEndElement();
	}
	
	private static void cellStyle(XMLStreamWriter writer, String xfId,
			String customBuiltin, String builtinId, String name) throws XMLStreamException {
		writer.writeStartElement("cellStyle");
		writer.writeAttribute("xfId", xfId);
		writer.writeAttribute("customBuiltin", customBuiltin);
		writer.writeAttribute("builtinId", builtinId);
		writer.writeAttribute("name", name);
		writer.writeEndElement();
	}*/
	
	public static void main(String[] args) {
		/*String name = "apiExample";
		File apiExample = new File(name + ".xlsx");
		Workbook workbook = new Workbook(name);
		
		Worksheet sheet1 = workbook.createSheet("Sheet1");
		Row row = sheet1.row();
		row.string("beep");
		row.string("beep");
		row.string("im");
		row.string("a");
		row.string("jeep");
		
		workbook.save(apiExample);*/
		String name = "test";
		File apiExample = new File(name + ".xlsx");
		Workbook workbook = new Workbook(name);
		
		SQLiteDatabase db = new SQLiteDatabase(new File("localDBs2.sqlite"));
		System.out.println("Connected to db.");
		DatabaseConverter converter = new DatabaseConverter(db);
		Worksheet ws = converter.toWorksheet(workbook, name, "select * from QUERIES");
		workbook.save(apiExample);
	}
}


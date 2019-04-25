package io.jayms.xlsx.model.meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Save;
import io.jayms.xlsx.model.SharedStrings;
import io.jayms.xlsx.model.StyleSheet;
import io.jayms.xlsx.model.Workbook;

public class RelationshipManager implements Part {

	private final Workbook wb;
    
    private int idCounter = 3;
    private Map<String, RelationshipPart> relationships = new HashMap<>();
    
    public RelationshipManager(Workbook wb) {
        this.wb = wb;
    }
    
    public void sharedStrings(SharedStrings sharedStrings) {
        relationships.put("rId1", sharedStrings);
    } 
    
    public void styleSheet(StyleSheet styleSheet) {
        relationships.put("rId2", styleSheet);
    }
    
    public String id(RelationshipPart rp) {
        String matchedId = matchId(rp);
        if (matchedId == null) {
            matchedId = genId();
            relationships.put(matchedId, rp);
        }
        return matchedId;
    }
    
    public RelationshipPart part(String id) {
        return relationships.get(id);
    }
    
    private String matchId(RelationshipPart rp) {
        for (String id : relationships.keySet()) {
            RelationshipPart relPart = relationships.get(id);
            if (relPart.type().equals(rp.type())
                && relPart.target().equals(rp.target())) {
                return id;        
            }
        }
        return null;
    }
    
    private String newId() {
        return "rId" + idCounter++;
    }
    
    private String genId() {
        String id = newId();
        if (relationships.containsKey(id)) {
            return genId();
        }
        return id;
    }
    
    @Override
    public void save(Save save) {
        ZipOutputStream zos = save.getZos();
        XMLStreamWriter writer = save.getWriter();
        
        try {
	        zos.putNextEntry(new ZipEntry("xl/_rels/workbook.xml.rels"));
	        
	        writer.writeStartDocument("UTF-8", "1.0");
	        writer.writeStartElement("Relationships");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");
			
			for (Map.Entry<String, RelationshipPart> relParts : relationships.entrySet()) {
			    String id = relParts.getKey();
	            RelationshipPart rp = relParts.getValue();
	            
	    	    writer.writeStartElement("Relationship");
	    	    writer.writeAttribute("Target", rp.target());
	    	    writer.writeAttribute("Type", rp.type());
	    	    writer.writeAttribute("Id", id);
	    	    writer.writeEndElement();            
			}
					
			writer.writeEndElement();
			writer.writeEndDocument();
		        
			zos.closeEntry();
        } catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
   }
}

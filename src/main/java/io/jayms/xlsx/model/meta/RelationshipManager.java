package io.jayms.xlsx.model.meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import io.jayms.xlsx.model.Save;
import io.jayms.xlsx.model.SharedStrings;
import io.jayms.xlsx.model.StyleSheet;

/**
 * Handles the management of RelationshipParts, and saves them into workbook.xml.rels zip entry.
 */
public class RelationshipManager implements Part {
    
    private int idCounter = 3; // id counter starts at 3 because other meta files take up those ids up to 3.
    private Map<String, RelationshipPart> relationships = new HashMap<>(); // relationship ids stored here.
    
    /**
     * Relate shared strings part.
     * @param sharedStrings - shared strings.
     */
    public void sharedStrings(SharedStrings sharedStrings) {
        relationships.put("rId1", sharedStrings);
    } 
    
    /**
     * Relate style sheet part.
     * @param styleSheet - style sheet.
     */
    public void styleSheet(StyleSheet styleSheet) {
        relationships.put("rId2", styleSheet);
    }
    
    /**
     * Allocate an integer id to a relationship part, or return existing id.
     * @param rp - relationship part.
     * @return Allocated id.
     */
    public String id(RelationshipPart rp) {
        String matchedId = matchId(rp); // does an id
        if (matchedId == null) {
            matchedId = genId();
            relationships.put(matchedId, rp);
        }
        return matchedId;
    }
    
    /**
     * Get a relationship part for id.
     * @param id - id of relationship part.
     * @return corresponding relationship part.
     */
    public RelationshipPart part(String id) {
        return relationships.get(id);
    }
    
    /**
     * Search for an existing id for a relationship part.
     * @param rp
     * @return
     */
    private String matchId(RelationshipPart rp) {
        for (String id : relationships.keySet()) { // Iterate over all the stored ids.
            RelationshipPart relPart = relationships.get(id); // Get their corresponding relationship part.
            if (relPart.type().equals(rp.type()) // Do the relationship parts match?
                && relPart.target().equals(rp.target())) {
                return id;        
            }
        }
        return null; // didn't find it :(
    }
    
    /**
     * @return New relationship part id.
     */
    private String newId() {
        return "rId" + idCounter++; // increment id for next id.
    }
    
    /**
     * Recursively generate an unused id.
     * @return unused id.
     */
    private String genId() {
        String id = newId(); // new id
        if (relationships.containsKey(id)) { // is that id already used?
            return genId(); // lets try again
        }
        return id;
    }
    
    @Override
    public void save(Save save) {
        ZipOutputStream zos = save.getZos();
        XMLStreamWriter writer = save.getWriter();
        
        try {
	        zos.putNextEntry(new ZipEntry("xl/_rels/workbook.xml.rels")); // we're writing in workbook.xml.rels.
	        
	        //meta
	        writer.writeStartDocument("UTF-8", "1.0");
	        writer.writeStartElement("Relationships");
			writer.writeAttribute("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");
			
			for (Map.Entry<String, RelationshipPart> relParts : relationships.entrySet()) { // iterate over relationship part map
			    String id = relParts.getKey();
	            RelationshipPart rp = relParts.getValue();
	            
	            // write a relationship tag for every relationship part in the relationship map.
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

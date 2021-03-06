package io.jayms.xlsx.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import io.jayms.xlsx.model.DoubleBandFormat;
import io.jayms.xlsx.model.FieldConfiguration;
import io.jayms.xlsx.model.Fill;
import io.jayms.xlsx.model.Font;
import io.jayms.xlsx.model.Style;
import io.jayms.xlsx.model.WorksheetDescriptor;
import io.jayms.xlsx.model.cells.SubTotalFunction;

public final class JSONTools {

	public static class ToJSON {
	
		public static JSONObject toJSON(DoubleBandFormat dbFormat) {
			JSONObject obj = new JSONObject();
			obj.put("style1", toJSON(dbFormat.getStyle1()));
			obj.put("style2", toJSON(dbFormat.getStyle2()));
			return obj;
		}
		
		public static JSONObject toJSON(Style style) {
			JSONObject obj = new JSONObject();
			obj.put("fill", toJSON(style.getFill()));
			obj.put("font", toJSON(style.getFont()));
			return obj;
		}
		
		public static JSONObject toJSON(Font font) {
			JSONObject obj = new JSONObject();
			obj.put("size", font.getSize());
			obj.put("family", font.getHandle());
			obj.put("name", font.getName());
			obj.put("color", Style.encodeRGB(font.getColor()));
			obj.put("bold", font.isBold());
			return obj;
		}
		
		public static JSONObject toJSON(Fill fill) {
			JSONObject obj = new JSONObject();
			obj.put("color", Style.encodeRGB(fill.getColor()));
			return obj;
		}

		public static JSONObject toJSON(WorksheetDescriptor wsDesc) {
			JSONObject obj = new JSONObject();
			obj.put("wsName", wsDesc.getWorksheetName());
			
			JSONObject fieldConfigsObj = toJSON(wsDesc.getFieldConfigs());
			obj.put("fieldConfigs", fieldConfigsObj);
			
			return obj;
		}
		
		public static JSONObject toJSON(Map<String, FieldConfiguration> fieldConfigs) {
			JSONObject fieldConfigsObj = new JSONObject();
			fieldConfigs.entrySet().forEach(e -> {
				fieldConfigsObj.put(e.getKey(), toJSON(e.getValue()));
			});
			return fieldConfigsObj;
		}
		
		public static JSONObject toJSON(FieldConfiguration fieldConfig) {
			JSONObject obj = new JSONObject();
			
			obj.put("inline", fieldConfig.isInline());
			obj.put("subTotalOnChange", fieldConfig.isSubTotalOnChange());
			obj.put("subTotalFunction", fieldConfig.getSubTotalFunction().getNum());
			obj.put("swapBandOnChange", fieldConfig.isSwapBandOnChange());
			obj.put("colWidth", fieldConfig.getColumnWidth());
			
			return obj;
		}
	}
	
	public static class FromJSON {
		
		public static DoubleBandFormat doubleBandFormat(JSONObject json) {
			System.out.println("json: " + json.toString());
			JSONObject style1Obj = json.getJSONObject("style1");
			JSONObject style2Obj = json.getJSONObject("style2");
			Style style1 = style(style1Obj);
			Style style2 = style(style2Obj);
			return new DoubleBandFormat(style1, style2);
		}
		
		public static Style style(JSONObject json) {
			JSONObject fontObj = json.getJSONObject("font");
			JSONObject fillObj = json.getJSONObject("fill");
			Font font = font(fontObj);
			Fill fill = fill(fillObj);
			return new Style(font, fill);
		}
		
		public static Font font(JSONObject json) {
			int size = json.getInt("size");
			int family = json.getInt("family");
			String name = json.getString("name");
			boolean bold = json.getBoolean("bold");
			int encodedColor = json.getInt("color");
			Color color = Style.decodeRGB(encodedColor);
			return new Font(size, name, family, bold, color);
		}
		
		public static Fill fill(JSONObject json) {
			int encodedColor = json.getInt("color");
			Color color = Style.decodeRGB(encodedColor);
			return new Fill(color);
		}
		
		public static Map<String, FieldConfiguration> fieldConfigs(JSONObject json) {
			Map<String, FieldConfiguration> fieldConfigs = new HashMap<>();
			for (String fieldName : json.keySet()) {
				fieldConfigs.put(fieldName, fieldConfig(((JSONObject) json.get(fieldName))));
			}
			return fieldConfigs;
		}
		
		public static FieldConfiguration fieldConfig(JSONObject json) {
			boolean inline = json.getBoolean("inline");
			boolean subTotalOnChange = json.getBoolean("subTotalOnChange");
			int subTotalFunctionNum = json.getInt("subTotalFunction");
			SubTotalFunction subTotalFunction = SubTotalFunction.valueOf(subTotalFunctionNum);
			boolean swapBandOnChange = json.getBoolean("swapBandOnChange");
			float colWidth = json.getFloat("colWidth");
			return new FieldConfiguration(inline, subTotalOnChange, subTotalFunction, swapBandOnChange, colWidth);
		}
	}
}

package io.jayms.xlsx.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class FontManager {

	private int handleCounter = 1;
	private Map<Integer, Font> fonts = new HashMap<>();
	
	public int createFont(String name, int size, boolean bold, Color color) {
		int handleToReturn = handleCounter;
		handleCounter++;
		fonts.put(handleToReturn, new Font(size, name, handleToReturn, bold, color));
		return handleToReturn;
	}
	
	public Font getFont(int handle) {
		return fonts.get(handle);
	}
	
}

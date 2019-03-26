package io.jayms.xlsx.model;

import java.awt.Color;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class Font {

	@Getter @Setter private int size;
	@Getter @Setter private String name;
	@Getter @Setter private boolean bold;
	@Getter @Setter private Color color;
	
	@Getter @Setter private int handle;
	
	public Font(int size, String name, int handle, boolean bold, Color color) {
		this.size = size;
		this.name = name;
		this.handle = handle;
		this.bold = bold;
		this.color = color;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(size, name, bold, color);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Font)) return false;
		
		Font font = (Font) obj;
		
		return size == font.size && name.equals(font.name) && bold == font.bold && color.equals(font.color);
	}
}

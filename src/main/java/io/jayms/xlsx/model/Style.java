package io.jayms.xlsx.model;

import java.awt.Color;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class Style {
	
	@Getter @Setter private Font font;
	@Getter @Setter private Fill fill;
	
	public Style(Font font, Fill fill) {
		this.font = font;
		this.fill = fill;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(font, fill);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Style)) return false;
		
		Style s = (Style) obj;
		return s.hashCode() == hashCode();
	}
	
	public static int encodeRGB(Color color) {
		return (((color.getRed() << 8) + color.getGreen() << 8) + color.getBlue());
	}
	
	public static Color decodeRGB(int encoded) {
		int red = (encoded >> 16) & 0xFF;
		int green = (encoded >> 8) & 0xFF;
		int blue = encoded & 0xFF;
		return new Color(red, green, blue);
	}
}

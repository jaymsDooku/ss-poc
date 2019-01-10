package io.jayms.xlsx.model;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

public class Fill {

	@Getter @Setter private Color color;
	
	public Fill(Color color) {
		this.color = color;
	}
	
	public static String toHex(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int a = color.getAlpha();
		
		String hr = Integer.toHexString(r) + (r == 0 ? "0" : "");
		String hg = Integer.toHexString(g) + (g == 0 ? "0" : "");
		String hb = Integer.toHexString(b) + (b == 0 ? "0" : "");
		String ha = Integer.toHexString(a) + (a == 0 ? "0" : "");
		
		return (ha + hr + hg + hb).toUpperCase();
	}
}

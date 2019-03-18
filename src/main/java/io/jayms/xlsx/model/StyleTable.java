package io.jayms.xlsx.model;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Stream;

import lombok.Getter;

public final class StyleTable {
	
	public static final StyleTable STYLE_TABLE = new StyleTable();

	@Getter private Style[] styles;
	
	private StyleTable() {
		this.styles = new Style[25];
		
		Color black = new Color(0, 0, 0, 255);
		Color white = new Color(255, 255, 255, 255);
		
		Font blackFt = new Font(12, "Arial", 2, false, black);
		Font whiteFt = new Font(12, "Arial", 2, false, white);
		
		styles[0] = new Style(blackFt, new Fill(white));
		styles[1] = new Style(whiteFt, new Fill(black));
		
		styles[2] = new Style(blackFt, new Fill(new Color(231, 230, 230, 255)));
		styles[3] = new Style(blackFt, new Fill(new Color(68, 84, 106, 255)));
		styles[4] = new Style(blackFt, new Fill(new Color(91, 155, 213, 255)));
		styles[5] = new Style(blackFt, new Fill(new Color(237, 125, 49, 255)));
		styles[6] = new Style(blackFt, new Fill(new Color(165, 165, 165, 255)));
		styles[7] = new Style(blackFt, new Fill(new Color(255, 192, 0, 255)));
		styles[8] = new Style(blackFt, new Fill(new Color(68, 144, 196, 255)));
		styles[9] = new Style(blackFt, new Fill(new Color(112, 173, 71, 255)));
		styles[10] = new Style(blackFt, new Fill(new Color(242, 242, 242, 255)));
		styles[11] = new Style(blackFt, new Fill(new Color(128, 128, 128, 255)));
		styles[12] = new Style(blackFt, new Fill(new Color(208, 206, 206, 255)));
		styles[13] = new Style(blackFt, new Fill(new Color(214, 220, 228, 255)));
		styles[14] = new Style(blackFt, new Fill(new Color(221, 235, 247, 255)));
		styles[15] = new Style(blackFt, new Fill(new Color(252, 228, 214, 255)));
		styles[16] = new Style(blackFt, new Fill(new Color(237, 237, 237, 255)));
		styles[17] = new Style(blackFt, new Fill(new Color(255, 242, 204, 255)));
		styles[18] = new Style(blackFt, new Fill(new Color(217, 225, 242, 255)));
		styles[19] = new Style(blackFt, new Fill(new Color(226, 239, 218, 255)));
		styles[20] = new Style(blackFt, new Fill(new Color(217, 217, 217, 255)));
		styles[21] = new Style(whiteFt, new Fill(new Color(89, 89, 89, 255)));
		styles[22] = new Style(blackFt, new Fill(new Color(174, 170, 170, 255)));
		styles[23] = new Style(whiteFt, new Fill(new Color(172, 185, 202, 255)));
		styles[24] = new Style(whiteFt, new Fill(new Color(189, 215, 238, 255)));
	}
	
	public Style getStyle(int index) {
		return styles[index];
	}
	
	public Style getStyle(Color color) {
		return stream().filter(s -> s.getFill().getColor().equals(color)).findFirst().orElse(null);
	}
	
	public Stream<Style> stream() {
		return Arrays.stream(styles);
	}
}

package io.jayms.xlsx.model;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;

public class StyleTable {

	public static final Color[] COLORS = {
			new Color(0, 0, 0, 255),
			new Color(255, 255, 255, 255),
			new Color(231, 230, 230, 255),
			new Color(68, 84, 106, 255),
			new Color(91, 155, 213, 255),
			new Color(237, 125, 49, 255),
			new Color(165, 165, 165, 255),
			new Color(255, 192, 0, 255),
			new Color(68, 144, 196, 255),
			new Color(112, 173, 71, 255),
			new Color(242, 242, 242, 255),
			new Color(128, 128, 128, 255),
			new Color(208, 206, 206, 255),
			new Color(214, 220, 228, 255),
			new Color(221, 235, 247, 255),
			new Color(252, 228, 214, 255),
			new Color(237, 237, 237, 255),
			new Color(255, 242, 204, 255),
			new Color(217, 225, 242, 255),
			new Color(226, 239, 218, 255),
			new Color(217, 217, 217, 255),
			new Color(89, 89, 89, 255),
			new Color(174, 170, 170, 255),
			new Color(172, 185, 202, 255),
			new Color(189, 215, 238, 255)
	};
	
	private static final List<Integer> WHITE_INDICES = Arrays.asList(1, 21, 23, 24);
	
	
	@Getter private Workbook workbook;
	@Getter private Style[] styles;
	
	public StyleTable(Workbook workbook) {
		this.workbook = workbook;
		this.styles = new Style[25];
		
		FontManager ftMan = workbook.getFontManager();
		
		for (int i = 0; i < COLORS.length; i++) {
			Color color = COLORS[i];
			Font font = ftMan.getFont(ftMan.createFont("Arial", 12, false, COLORS[0]));
			if (WHITE_INDICES.contains(i)) {
				font = ftMan.getFont(ftMan.createFont("Arial", 12, false, COLORS[1]));
			}
			styles[i] = new Style(font, new Fill(color));
		}
	}
	
	public Style getStyle(int index) {
		return styles[index];
	}
	
	public Style getStyle(Color color) {
		return stream().filter(s -> s.getFill().getColor().equals(color)).findFirst().orElse(null);
	}
	
	public int indexOf(Style style) {
		for (int i = 0; i < styles.length; i++) {
			Style s = styles[i];
			if (s.equals(style)) {
				return i;
			}
		}
		return -1;
	}
	
	public Stream<Style> stream() {
		return Arrays.stream(styles);
	}
}

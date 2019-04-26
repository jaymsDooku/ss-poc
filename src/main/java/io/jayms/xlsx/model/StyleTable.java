package io.jayms.xlsx.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;

public class StyleTable {

	/**
	 * all valid double band format colours
	 */
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
			new Color(189, 215, 238, 255), // 25
			new Color(244, 176, 132, 255), // 26
			new Color(201, 201, 201, 255), // 
			new Color(255, 217, 102, 255),
			new Color(142, 169, 219, 255),
			new Color(169, 208, 142, 255),
			new Color(191, 191, 191, 255),
			new Color(38, 38, 38, 255), // 42
			new Color(58, 56, 56, 255),
			new Color(51, 63, 79, 255),
			new Color(47, 117, 181, 255), // 45
			new Color(198, 89, 17, 255),
			new Color(123, 123, 123, 255),
			new Color(191, 143, 0, 255), // 48
			new Color(48, 84, 150, 255),
			new Color(84, 130, 53, 255), // 50
			new Color(128, 128, 128, 255),
			new Color(13, 13, 13, 255),
			new Color(22, 22, 22, 255),
			new Color(34, 43, 53, 255),
			new Color(31, 78, 120, 255),
			new Color(131, 60, 12, 255),
			new Color(82, 82, 82, 255),
			new Color(128, 96, 0, 255),
			new Color(32, 55, 100, 255), // 59
			new Color(55, 86, 35, 255),
			new Color(192, 0, 0, 255),
			new Color(255, 0, 0, 255),
			new Color(255, 192, 0, 255),
			new Color(255, 255, 0, 255),
			new Color(146, 208, 80, 255),
			new Color(0, 176, 240, 255),
			new Color(0, 112, 192, 255),
			new Color(0, 32, 96, 255),
			new Color(112, 48, 160) // 70
	};
	
	/**
	 * a list of the indices for the style table; indicating which colour prefers a white font.
	 */
	private static final List<Integer> WHITE_INDICES = new ArrayList<>();
	
	static {
		WHITE_INDICES.add(1);
		WHITE_INDICES.add(20);
		WHITE_INDICES.add(21);
		WHITE_INDICES.add(68);
		for (int i = 30; i < 50; i++) {
			WHITE_INDICES.add(i);
		}
		for (int i = 51; i < 60; i++) {
			WHITE_INDICES.add(i);
		}
	}
	
	
	@Getter private Workbook workbook;
	@Getter private Style[] styles;
	
	public StyleTable(Workbook workbook) {
		this.workbook = workbook;
		this.styles = new Style[COLORS.length];
		
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

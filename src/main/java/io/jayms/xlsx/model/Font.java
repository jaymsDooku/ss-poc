package io.jayms.xlsx.model;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

public class Font {

	@Getter @Setter private int size;
	@Getter @Setter private String name;
	@Getter @Setter private int family;
	@Getter @Setter private boolean bold;
	@Getter @Setter private Color color;
	
	public Font(int size, String name, int family, boolean bold, Color color) {
		this.size = size;
		this.name = name;
		this.family = family;
		this.bold = bold;
		this.color = color;
	}
}

package io.jayms.xlsx.model;

import lombok.Getter;
import lombok.Setter;

public class Style {
	
	@Getter @Setter private Font font;
	@Getter @Setter private Fill fill;
	
	public Style(Font font, Fill fill) {
		this.font = font;
		this.fill = fill;
	}
}

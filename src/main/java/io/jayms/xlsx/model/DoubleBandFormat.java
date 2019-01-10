package io.jayms.xlsx.model;

import lombok.Getter;
import lombok.Setter;

public class DoubleBandFormat {

	@Getter @Setter private Style style1;
	@Getter @Setter private Style style2;
	
	public DoubleBandFormat(Style style1, Style style2) {
		this.style1 = style1;
		this.style2 = style2;
	}
}

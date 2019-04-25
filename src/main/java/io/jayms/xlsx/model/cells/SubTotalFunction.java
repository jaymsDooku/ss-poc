package io.jayms.xlsx.model.cells;

import java.util.Arrays;

import lombok.Getter;

/**
 * 	Represents the function carried out by a sub total; all possible sub totals listed in the link below. 
 *	https://support.office.com/en-us/article/subtotal-function-7b027003-f060-4ade-9040-e478765b9939
 */
public enum SubTotalFunction {

	AVERAGE(1),
	COUNT(2),
	COUNTA(3),
	MAX(4),
	MIN(5),
	PRODUCT(6),
	STDEV(7),
	STDEVP(8),
	SUM(9),
	VAR(10),
	VARP(11);
	
	@Getter private int num;
	
	private SubTotalFunction(int num) {
		this.num = num;
	}
	
	/**
	 * @param num - sub total function id
	 * @return corresponding sub total function.
	 */
	public static SubTotalFunction valueOf(int num) {
		return Arrays.stream(values())
				.filter(v -> v.getNum() == num)
				.findFirst().orElse(null);
	}
}

package io.jayms.xlsx.util;

public class AlphanumericSequence {

	private char[] characters;
	
	public AlphanumericSequence() {
		 characters = new char[26];
		 int i = 0;
		 for (char c = 'A'; c <= 'Z'; c++) {
			 characters[i++] = c;
		 }
	}
	
	public String get(int index) {
		StringBuilder sb = new StringBuilder();
		
		int len = (int) (index / 26);
		System.out.println("len: " + len);
		
		int[] indices = new int[len+1];
		
		for (int i = 0; i < indices.length; i++) {
			int mod = index % 26;
			System.out.println("mod" + i + ": " + mod);
			indices[i] = mod;
			index -= 26 + mod;
		}
		
		for (int i = 0; i < indices.length; i++) {
			sb.append(characters[indices[i]]);
		}
		return sb.reverse().toString();
	}
	
	int counter = -1;
	
	public String count() {
		StringBuilder sb = new StringBuilder();
		counter++;
		
		if (counter < 26) {
			sb.append(characters[counter]);
			return sb.toString();
		}
		
		sb.append(characters[(counter / 26) - 1]);
		sb.append(characters[counter % 26]);
		
		return sb.toString();
	}
}

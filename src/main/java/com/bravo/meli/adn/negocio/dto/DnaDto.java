package com.bravo.meli.adn.negocio.dto;

import java.util.Arrays;

public class DnaDto {

	private String[] dna;

	public DnaDto() {
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	@Override
	public String toString() {
		return "DnaDto [dna=" + Arrays.toString(dna) + "]";
	}

}

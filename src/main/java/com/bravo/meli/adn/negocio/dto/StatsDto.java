package com.bravo.meli.adn.negocio.dto;

public class StatsDto {

	private Long count_mutant_dna;
	private Long count_human_dna;
	private Double ratio;

	public StatsDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(Long count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	public Long getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(Long count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

}

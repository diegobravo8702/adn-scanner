package com.bravo.meli.adn.negocio.algoritmos;

import com.bravo.meli.adn.negocio.interfaces.Analizable;
import com.bravo.meli.adn.negocio.interfaces.Validable;

import lombok.Data;

@Data
public abstract class Algoritmo implements Validable, Analizable {
	final static byte limiteDeSecuencia = (byte) 4;
	final static byte limiteDeHallazgos = (byte) 1;

	protected long hallazgos;

	public Algoritmo() {
		hallazgos = 0;
	}

}

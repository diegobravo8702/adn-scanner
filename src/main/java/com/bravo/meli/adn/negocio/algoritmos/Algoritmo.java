package com.bravo.meli.adn.negocio.algoritmos;

import com.bravo.meli.adn.negocio.interfaces.Analizable;
import com.bravo.meli.adn.negocio.interfaces.Validable;

public abstract class Algoritmo implements Validable, Analizable {
	final static byte limiteDeSecuencia = (byte) 4;
	final static byte limiteDeHallazgos = (byte) 1;

	protected long hallazgos;

	public Algoritmo() {
		hallazgos = 0;
	}

	public long getHallazgos() {
		return hallazgos;
	}

	public void setHallazgos(long hallazgos) {
		this.hallazgos = hallazgos;
	}

}

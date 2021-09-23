package com.bravo.meli.adn.negocio.algoritmos;

import java.util.regex.Matcher;

import com.bravo.meli.adn.negocio.Analizable;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

public class AlgoritmoRegex extends Algoritmo {

	@Override
	public boolean isMutant(String[] cadenas) throws DatosInvalidosException {
		validarCadenas(cadenas);

		Matcher matcher;
		for (String cadena : cadenas) {
			matcher = patronMutante.matcher(cadena);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	public static void main(String args[]) {
		// String cadenas[] = null;// = { "aaat", "", "", "" };
		String cadenas[] = { "aaat", "aaag", "aaag", "agaa" };
		Analizable analizador = new AlgoritmoRegex();
		try {
			System.out.println(analizador.isMutant(cadenas) ? "Mutante" : "Muggle");
		} catch (DatosInvalidosException e) {
			System.out.println(e);
		}
	}

}

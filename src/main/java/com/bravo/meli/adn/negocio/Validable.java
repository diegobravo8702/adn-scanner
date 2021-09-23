package com.bravo.meli.adn.negocio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;
import com.bravo.meli.adn.negocio.excepciones.ErrorCode;

public interface Validable {
	public static final Pattern patronBasesInvalidas = Pattern.compile("[^CAGT]", Pattern.CASE_INSENSITIVE);
	
	default boolean validarCadenas(String[] cadenas) throws DatosInvalidosException {

		// Se asegura que el arreglo de cadenas contenga información.
		if (cadenas == null || cadenas.length <= 0) {
			throw new DatosInvalidosException("Una o varias cadenas es nula.", ErrorCode.CADENAS_NULAS);
		}

		// Se asegura que cada cadena del arreglo no este vacio.
		for (String cadena : cadenas) {
			if (cadena == null || cadena.isBlank()) {
				throw new DatosInvalidosException("Una o varias cadenas son nulas o vacias.", ErrorCode.CADENAS_NULAS);
			}
		}

		// Establece el indice de la matriz. En este caso se opto por tomarlo a partir
		// de la cantidad de cadenas.
		int indiceMatriz = cadenas.length;

		// Se asegura de que todas las cadenas tengan la misma longitud que el indice de
		// la matriz.
		for (String cadena : cadenas) {
			if (indiceMatriz != cadena.length()) {
				throw new DatosInvalidosException("Las cadenas no tienen la misma longitud.",
						ErrorCode.CADENAS_TAMANOS_DISPARES);
			}
		}

		// Se asegura que la matriz sea cuadrada
		if (indiceMatriz != cadenas.length) {
			throw new DatosInvalidosException("Las cadenas deben formar una matriz cuadrada N x N.",
					ErrorCode.CADENA_DEBE_SER_CUADRADA);
		}

		// Se asegura que todos los valores de la cadena correspondan a las bases
		// nitrogenadas: ATGC
		Matcher matcher;
		for (String cadena : cadenas) {
			matcher = patronBasesInvalidas.matcher(cadena);
			if (matcher.find()) {
				throw new DatosInvalidosException("Datos inválidos. solo se acepta ATGC.",
						ErrorCode.BASE_NITROGENADA_INVALIDA);
			}
		}

		// Llegados a este punto no se produjo ninguna excepcion, se retorna true
		return true;
	}
}

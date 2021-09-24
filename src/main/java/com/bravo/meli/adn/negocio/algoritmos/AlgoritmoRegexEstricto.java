package com.bravo.meli.adn.negocio.algoritmos;

import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

/**
 * 
 * @author diego
 *
 */

public class AlgoritmoRegexEstricto extends Algoritmo {

	/**
	 * 
	 * @param cadenas
	 * @return
	 */
	@Override
	public boolean isMutant(String[] cadenas) throws DatosInvalidosException {
		validarCadenas(cadenas);
		return analizarEstricto(cadenas);
	}

	private long obtenerCantidadAnomalias(String cadena) {
		if (cadena.length() >= limiteDeSecuencia) {
			Matcher matcher = patronMutante.matcher(cadena);
			return matcher.results().count();
		}
		return 0l;
	}

	private boolean esMutanteActualizandoContadores(StringBuilder buffer, String log) {
		String cadena = buffer.toString();
		buffer.setLength(0);
		long hallazgosLocales = obtenerCantidadAnomalias(cadena);
		hallazgos = hallazgos + hallazgosLocales;
		// System.out.println(log + " cadena:" + cadena + " hallazgos locales: "+ hallazgosLocales + " hallazgos global: " + hallazgos);
		return (hallazgos) > limiteDeHallazgos;
	}

	/**
	 * Transforma el array de cadenas de ADN en un string que incluye todos los casos de evaluación: cadenas horizontales, verticales y diagonales
	 * 
	 * @param cadenas
	 * @return cadenaCondensada
	 */
	private boolean analizarEstricto(String[] cadenas) {
		final int indice = cadenas.length;

		StringBuilder bufferHorizontales = new StringBuilder();
		StringBuilder bufferVerticales = new StringBuilder();
		StringBuilder bufferDiagonalA = new StringBuilder();
		StringBuilder bufferDiagonalB = new StringBuilder();

		for (int fila = 0; fila < indice; fila++) {
			// se llena buffer horizontal, no requiere contador de columnas
			// System.out.println("fila: " + fila);
			bufferHorizontales.append(cadenas[fila]);

			// Esta se utiliza para indicar si en el recorrido por las diagonales nos encontramos evaluando la diagonal antes de salirnos del arreglo. Cuando esto ocurre se recalculan los indices para
			// continuar leyendo la matriz al otro lado de donde se excedió el limite.
			boolean parteSuperior = true;
			for (int columna = 0; columna < indice; columna++) {
				// System.out.println("colu: " + columna);
				// Se llena la cadena de verticales: se invierte filas por columnas
				bufferVerticales.append(cadenas[columna].charAt(fila));

				// Esto evalua si nos "salimos" por arriba de la matriz, de ser así modificamos
				// la bandera parteSuperior para indicar que a partir de ahora se utilizará los
				// indices de la matriz pero recalculados. Para continuar "por abajo" de la
				// matriz
				if (fila - columna >= 0) {
					// Llenando en la diagonal principal: inf izq -- sup der PARTE SUPERIOR
					bufferDiagonalA.append(cadenas[fila - columna].charAt(columna));
					// Llenando en la diagonal inversa: sup izq -- inf der PARTE SUPERIOR
					bufferDiagonalB.append(cadenas[(indice - 1) - Math.abs(fila - columna)].charAt(columna));
				} else {
					if (parteSuperior) {
						parteSuperior = false;
						// ya se ha llenado los buffer de la parte superior. Se evaluan.
						if (esMutanteActualizandoContadores(bufferDiagonalA, "A") || esMutanteActualizandoContadores(bufferDiagonalB, "B")) {
							return true;
						}
					}
					// Llenando en la diagonal principal: inf izq -- sup der PARTE INFERIOR
					bufferDiagonalA.append(cadenas[indice - Math.abs(fila - columna)].charAt(columna));
					// Llenando en la diagonal inversa: sup izq -- inf der PARTE INFERIOR
					bufferDiagonalB.append(cadenas[Math.abs(fila - columna) - 1].charAt(columna));
				}
			}
			// se evalua los bufferes
			if (esMutanteActualizandoContadores(bufferHorizontales, "H") || esMutanteActualizandoContadores(bufferVerticales, "V") || esMutanteActualizandoContadores(bufferDiagonalA, "A")
					|| esMutanteActualizandoContadores(bufferDiagonalB, "B")) {
				return true;
			}
		}

		return false;
	}
}

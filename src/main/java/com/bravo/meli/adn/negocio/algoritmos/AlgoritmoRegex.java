package com.bravo.meli.adn.negocio.algoritmos;

import java.util.regex.Matcher;

import com.bravo.meli.adn.negocio.Analizable;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

/**
 * 
 * @author diego
 *
 */
public class AlgoritmoRegex extends Algoritmo {

	public AlgoritmoRegex() {
		super();
	}

	/**
	 * 
	 * @param cadenas
	 * @return
	 */
	@Override
	public boolean isMutant(String[] cadenas) throws DatosInvalidosException {
		validarCadenas(cadenas);

		Matcher matcher;

		matcher = patronMutante.matcher(condensarCadenas(cadenas));
		if (matcher.find()) {
			return true;
		}

		return false;
	}

	/**
	 * Transforma el array de cadenas de ADN en un string que incluye todos los
	 * casos de evaluación: cadenas horizontales, verticales y diagonales
	 * 
	 * @param cadenas
	 * @return cadenaCondensada
	 */
	private String condensarCadenas(String[] cadenas) {
		final int indice = cadenas.length;
		final String separador = "_";

		StringBuilder cadenasCombinadas = new StringBuilder();
		StringBuilder cadenaHorizontales = new StringBuilder();
		StringBuilder cadenaVerticales = new StringBuilder();
		StringBuilder cadenaDiagonalA = new StringBuilder();
		StringBuilder cadenaDiagonalB = new StringBuilder();

		// con dos indices se recorre la totalidad de la matriz
		for (int fila = 0; fila < indice; fila++) {
			cadenaHorizontales.append(separador);
			cadenaVerticales.append(separador);
			cadenaDiagonalA.append(separador);
			cadenaDiagonalB.append(separador);

			// se llena la nueva cadena con horizontales. No requiere doble indice.
			cadenaHorizontales.append(cadenas[fila]);

			// Esta se utiliza para indicar si en el recorrido por las diagolanes nos
			// encontramos evaluando la diagonal antes de salirnos del arreglo. Cuando esto
			// ocurre se recalculan los indices para continuar leyendo la matriz al otro
			// lado de donde se excedió el limite.
			boolean parteSuperior = true;
			for (int columna = 0; columna < indice; columna++) {
				// Se llena la cadena de verticales: se invierte filas por columnas
				cadenaVerticales.append(cadenas[columna].charAt(fila));

				// Esto evalua si nos "salimos" por arriba de la matriz, de ser así modificamos
				// la bandera parteSuperior para indicar que a partir de ahora se utilizará los
				// indices de la matriz pero recalculados. Para continuar "por abajo" de la
				// matriz
				if (fila - columna >= 0) {
					// Llenando en la diagonal principal: inf izq -- sup der PARTE SUPERIOR
					cadenaDiagonalA.append(cadenas[fila - columna].charAt(columna));
					// Llenando en la diagonal inversa: sup izq -- inf der PARTE SUPERIOR
					cadenaDiagonalB.append(cadenas[(indice - 1) - Math.abs(fila - columna)].charAt(columna));
				} else {
					if (parteSuperior) {
						// Como se debe continuar con la evaluacion en la parte inferior se añade un
						// espacio para separar la cadena
						cadenaDiagonalA.append(separador);
						cadenaDiagonalB.append(separador);
						parteSuperior = false;
					}
					// Llenando en la diagonal principal: inf izq -- sup der PARTE INFERIOR
					cadenaDiagonalA.append(cadenas[indice - Math.abs(fila - columna)].charAt(columna));
					// Llenando en la diagonal inversa: sup izq -- inf der PARTE INFERIOR
					cadenaDiagonalB.append(cadenas[Math.abs(fila - columna)].charAt(columna));
				}
			}
		}

		cadenasCombinadas.append(cadenaHorizontales.toString());
		cadenasCombinadas.append(cadenaVerticales.toString());
		cadenasCombinadas.append(cadenaDiagonalA.toString());
		cadenasCombinadas.append(cadenaDiagonalB.toString());

		System.out.println();
		System.out.println(cadenasCombinadas.toString());

		return cadenasCombinadas.toString();
	}

	public static void main(String args[]) {
		// String cadenas[] = null;// = { "aaat", "", "", "" };
		//@formatter:off
		String cadenas[] = { 
				"acfjouCK",
				"beintBJR", 
				"dhmsAIQY", 
				"glrzHPXd", 
				"kqyGOVch", 
				"pxFNUbgk", 
				"vEMTafjm",
				"DLSZeiln", };
		//@formatter:on
		Analizable analizador = new AlgoritmoRegex();
		try {
			System.out.println();
			System.out.println(analizador.isMutant(cadenas) ? "Mutante" : "Muggle");
		} catch (DatosInvalidosException e) {
			System.out.println(e);
		}
	}

}

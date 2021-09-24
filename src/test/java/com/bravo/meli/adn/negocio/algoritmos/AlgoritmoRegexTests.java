package com.bravo.meli.adn.negocio.algoritmos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;
import com.bravo.meli.adn.negocio.excepciones.ErrorCode;

class AlgoritmoRegexTests {

	private String[] adnNulo = null;
	private String[] admContenidoInvalida = { "ATGC", "AWWC", "ATGC", "ATGC" };
	private String[] admLongitudInvalidaCaso1 = { "ATGC", "ATGC", "ATGC", "ATGCA" };
	private String[] admLongitudInvalidaCaso2 = { "ATGC", "ATGC", "ATGC" };
	private String[] admLongitudInvalidaCaso3 = { "ATGC", "A", "ATGC", "ATGC" };
	private String[] adnHumano = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
	private String[] adnMutante = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG", };

	@Test
	void testElAlgoritmoIndicaQueEsMutante() {

		AlgoritmoRegex algoritmo = new AlgoritmoRegex();
		try {
			Assert.isTrue(algoritmo.isMutant(adnMutante), "ESTO DEBE SER MUTANTE");
		} catch (DatosInvalidosException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testElAlgoritmoIndicaQueNoEsMutante() {

		AlgoritmoRegex algoritmo = new AlgoritmoRegex();
		try {
			Assert.isTrue(!algoritmo.isMutant(adnHumano), "ESTO DEBE SER NO MUTANTE");
		} catch (DatosInvalidosException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDatosAdnNulo() {
		DatosInvalidosException exception = assertThrows(DatosInvalidosException.class, () -> {
			AlgoritmoRegex algoritmo = new AlgoritmoRegex();
			algoritmo.isMutant(adnNulo);
		});

		assertEquals(ErrorCode.CADENAS_NULAS.getMessage(), exception.getCode().getMessage());
	}

	@Test
	void testDatosAdnInvalidos() {
		DatosInvalidosException exception = assertThrows(DatosInvalidosException.class, () -> {
			AlgoritmoRegex algoritmo = new AlgoritmoRegex();
			algoritmo.isMutant(admContenidoInvalida);
		});

		assertEquals(ErrorCode.BASE_NITROGENADA_INVALIDA.getMessage(), exception.getCode().getMessage());
	}

	@Test
	void testDatosAdnLongitudInvalidaCaso1() {
		DatosInvalidosException exception = assertThrows(DatosInvalidosException.class, () -> {
			AlgoritmoRegex algoritmo = new AlgoritmoRegex();
			algoritmo.isMutant(admLongitudInvalidaCaso1);
		});

		assertEquals(ErrorCode.CADENAS_TAMANOS_DISPARES.getMessage(), exception.getCode().getMessage());
	}

	@Test
	void testDatosAdnLongitudInvalidaCaso2() {
		DatosInvalidosException exception = assertThrows(DatosInvalidosException.class, () -> {
			AlgoritmoRegex algoritmo = new AlgoritmoRegex();
			algoritmo.isMutant(admLongitudInvalidaCaso2);
		});

		assertEquals(ErrorCode.CADENAS_TAMANOS_DISPARES.getMessage(), exception.getCode().getMessage());
	}

	@Test
	void testDatosAdnLongitudInvalidaCaso3() {
		DatosInvalidosException exception = assertThrows(DatosInvalidosException.class, () -> {
			AlgoritmoRegex algoritmo = new AlgoritmoRegex();
			algoritmo.isMutant(admLongitudInvalidaCaso3);
		});

		assertEquals(ErrorCode.CADENAS_TAMANOS_DISPARES.getMessage(), exception.getCode().getMessage());
	}

	public void compute() {

		Long l1 = 0L;
		Long l2 = 1L;
		Long l = 0L;
		for (int i = 0; i < 100000; i++) {
			l = l1 + l2;
			l1 = l2;
			l2 = l;
		}
		System.out.println(l);
	}
}

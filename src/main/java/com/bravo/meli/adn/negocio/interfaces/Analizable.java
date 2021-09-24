package com.bravo.meli.adn.negocio.interfaces;

import java.util.regex.Pattern;

import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

public interface Analizable {

	public static final Pattern patronMutante = Pattern.compile("a{4}|t{4}|g{4}|c{4}", Pattern.CASE_INSENSITIVE);

	boolean isMutant(String[] adn) throws DatosInvalidosException;
}

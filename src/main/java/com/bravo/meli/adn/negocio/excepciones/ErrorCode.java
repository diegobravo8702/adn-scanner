package com.bravo.meli.adn.negocio.excepciones;

public enum ErrorCode {
	CADENAS_NULAS((byte) 10, "Una o varias cadenas es nula."),

	CADENAS_VACIAS((byte) 20, "Una o varias cadenas vacias."),

	CADENAS_TAMANOS_DISPARES((byte) 30, "Las cadenas deben tener la misma longitud."),

	CADENA_DEBE_SER_CUADRADA((byte) 40, "Las cadenas deben formar una matriz cuadrada N x N."),

	BASE_NITROGENADA_INVALIDA((byte) 50, "Datos inválidos. solo se acepta ACGT.");

	private byte code;
	private String message;

	ErrorCode(byte code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
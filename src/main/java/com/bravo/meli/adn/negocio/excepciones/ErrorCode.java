package com.bravo.meli.adn.negocio.excepciones;

public enum ErrorCode {
	CADENAS_NULAS((byte) 10),

	CADENAS_VACIAS((byte) 20),

	CADENAS_TAMANOS_DISPARES((byte) 30),

	CADENA_DEBE_SER_CUADRADA((byte) 40),

	BASE_NITROGENADA_INVALIDA((byte) 50);

	private byte code;

	ErrorCode(byte code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
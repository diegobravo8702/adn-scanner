package com.bravo.meli.adn.negocio.excepciones;

public class DatosInvalidosException extends Exception {

	private static final long serialVersionUID = 3689943144527966263L;

	private final ErrorCode code;

	public DatosInvalidosException(ErrorCode code) {
		super();
		this.code = code;
	}

	public DatosInvalidosException(String message, Throwable cause, ErrorCode code) {
		super(message, cause);
		this.code = code;
	}

	public DatosInvalidosException(String message, ErrorCode code) {
		super(message);
		this.code = code;
	}

	public DatosInvalidosException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}

}

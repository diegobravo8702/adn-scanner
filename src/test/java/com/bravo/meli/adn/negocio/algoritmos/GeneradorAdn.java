package com.bravo.meli.adn.negocio.algoritmos;

import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import com.bravo.meli.adn.negocio.Analizable;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

public class GeneradorAdn {

	public static final Pattern patronMutante = Pattern.compile("a{4}|t{4}|g{4}|c{4}", Pattern.CASE_INSENSITIVE);

	public GeneradorAdn() {

	}

	public static void buscarAdnHumano(int indice) throws DatosInvalidosException {
		boolean humanoEncontrado = false;
		Analizable algoritmo = new AlgoritmoRegexEstricto();

		Random random = new Random(new Date().getTime());
		StringBuilder[] buffers = new StringBuilder[indice];
		String[] adnCandidato = null;

		for (int i = 0; i < indice; i++) {
			buffers[i] = new StringBuilder();
		}
		int intentos = 0;
		do {
			intentos++;
			System.out.println("intento " + intentos);
			adnCandidato = new String[indice];
			for (StringBuilder buffer : buffers) {
				boolean esMutante = true;
				while (esMutante) {
					buffer.setLength(0);
					for (int i = 0; i < indice; i++) {

						switch (random.nextInt(4)) {
						case 0:
							buffer.append("A");
							break;
						case 1:
							buffer.append("T");
							break;
						case 2:
							buffer.append("G");
							break;
						default:
							buffer.append("C");
						}
					}
					esMutante = patronMutante.matcher(buffer.toString()).find();
				}
			}

			int i = 0;
			for (StringBuilder buffer : buffers) {
				adnCandidato[i] = buffer.toString();
				System.out.println(buffer.toString());
				i++;
			}
			if (!algoritmo.isMutant(adnCandidato)) {
				humanoEncontrado = true;
				System.out.println("ENCONTRADO");
//				for (StringBuilder buffer : buffers) {
//					System.out.println(buffer.toString());
//					adnCandidato[i] = buffer.toString();
//				}
			}
		} while (!humanoEncontrado);
	}

	public static void main(String[] args) {
//		Random random = new Random(new Date().getTime());
//		for (int i = 0; i < 20; i++) {
//			System.out.println(random.nextInt(3));
//		}
		try {
			buscarAdnHumano(5);
		} catch (DatosInvalidosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

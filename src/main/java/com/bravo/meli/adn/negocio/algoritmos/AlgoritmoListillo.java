package com.bravo.meli.adn.negocio.algoritmos;

import java.util.ArrayList;
import java.util.List;

import com.bravo.meli.adn.negocio.algoritmos.AlgoritmoListillo.inclinacion;
import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

public class AlgoritmoListillo extends Algoritmo {

	private String[] adn;
	private int indice;
	
	private int lecturas = 0;

	
	public int getLecturas() {
		return lecturas;
	}

	public void setLecturas(int lecturas) {
		this.lecturas = lecturas;
	}

	public AlgoritmoListillo() {
	}

	// @formatter:off

	/*
	
	ideas: recorrer la matriz en 1 o 2 dimensiones.
	que sea matriz es una facilidad humana. los datos pueden estar desperdigados por toda la ram sin que sea para nada parecido  a una matriz
	si los datos estuvieran en un array de 1 dimension, y de hecho como que asi es que están, puedo hacer un calculo utilizando el indice de la matriz.
	que en este ejercicio la matriz sea cuadrada facilita el calculo.


         0       1       2       3       4      5                                            

     |-------|-------|-------|-------|-------|-------|
     |   A   |   T   |   G   |   C   |   A   |   T   |                                                     
0    |   0   |   1   |   2   |   3   |   4   |   5   |                                                        
     |-------|-------|-------|-------|-------|--------	                                                         |      
     |   A   |   T   |   G   |   C   |   A   |   T   |                                                      
1    |   6   |   7   |   8   |   9   |   10  |   11  |                                                      
     |-------|-------|-------|-------|-------|--------      
     |   A   |   T   |   G   |   C   |   A   |   T   |                                                    
2    |   12  |   13  |   14  |   15  |   16  |   17  |                                                      
     |-------|-------|-------|-------|-------|--------      
     |   A   |   T   |   G   |   C   |   A   |   T   |                                                    
3    |   18  |   19  |   20  |   21  |<<22>> |   23  |                                                      
     |-------|-------|-------|-------|-------|--------      
     |   A   |   T   |   G   |   C   |   A   |   T   |                                                     
4    |   24  |   25  |   26  |   27  |   28  |   29  |                                                       
     |-------|-------|-------|-------|-------|--------      
     |   A   |   T   |   G   |   C   |   A   |   T   |                                                     
5    |   30  |   31  |   32  |   33  |   34  |   35  |                                                      
     |-------|-------|-------|-------|-------| -------


	entonces en una matriz de indice 6, el valor en 1 dimension de la posicion 3,4 seria:  3 x 6 + 4 = 22 

 
	Ahora necesito crear una funcion que me retorne las coordenadas en 2 dimensiones a partir del indice en una
	La idea es que los calculos del algoritmo se mantengan en una dimension pero al leer las posiciones se acceda con dos.

*/
	// @formatter:on	

	private Coordenada getCoordenadas2D(int posicion1D, int indice) {
		return new Coordenada(posicion1D / indice, posicion1D % indice);
	}

	/**
	 * como la matriz se puede recorrer en distintas direcciones, se debe crear un algoritmo que me entregue la posicion 2D de una posicion cercana relativa por ejemplo: si recorro la matriz en
	 * direccion diagonal principal (sup izq -> inf derecha) la casilla siguiente a la celda 3,4 seria 4,5 mientras que la siguiente a la 5,2 seria la 6,4 pero esta no existe en la matriz, nos hemos
	 * salido. si fuera como el juego del pacman que se sale al otro lado la casilla seria la 0.3 pero en el ejercicio de mercadolibre no se contemplo este escenario. los bordes son el límite. aunque
	 * saber eso sirve en la logica de los dos algoritmos anteriores a este
	 * 
	 * nota para mas adelante: pedirle a esta funcion que entregue un dato y ese este fuera del borde puede servir de fin de algun proceso o tambien para indicar que hay un separador de palabras .. no
	 * se aun.
	 * 
	 */

	enum inclinacion {
		horizontal, vertical, diagonalPrincipal, diagonalInversa
	};

// se llama getSiguiente pero puede traer saltandose x espacios
	/**
	 * Retorna la siguiente coordenada válida o null si no tiene siguiente.
	 * 
	 * @param coordenadaActual
	 * @param inc
	 * @param objetivoEnPasos
	 * @return
	 */
	private Coordenada getSiguienteCoordenadaValida(Coordenada coordenadaActual, inclinacion inc, int objetivoEnPasos) {
		// Estas dos representan cuanto debe cambiar la coordenada actual para llegar hasta la nueva.
		// Otra forma de verlo es cuanto se debe sumarse o restarse para ser el nuevo valor.
		// la manera de ir hacia adelante (derecha) o hacia atras(izquierda) es con el signo de delta.

		int deltaX = 0;
		int deltaY = 0;

		int nuevaPosicionX = 0;
		int nuevaPosicionY = 0;

		switch (inc) {
		case horizontal:
			deltaY = 0; // como es horizontal pues no cambia.
			deltaX = objetivoEnPasos;
			break;
		case vertical:
			deltaY = objetivoEnPasos;
			deltaX = 0;
			break;
		case diagonalPrincipal:
			deltaY = objetivoEnPasos;
			deltaX = objetivoEnPasos;
			break;
		case diagonalInversa:
			deltaY = objetivoEnPasos * (-1);
			deltaX = objetivoEnPasos;
			break;
		}

		nuevaPosicionY = coordenadaActual.getFil() + deltaY;
		nuevaPosicionX = coordenadaActual.getCol() + deltaX;
		// ---- System.out.println(" getSiguienteValida(posicionActual: " + coordenadaActual.toString() + " , inc: " + inc + " , objetivoEnPasos: " + objetivoEnPasos + " ) -> "+ ((nuevaPosicionY < 0 || nuevaPosicionY >= indice || nuevaPosicionX >= indice) ? "null" : new Coordenada(nuevaPosicionY, nuevaPosicionX).toString()));
		return (nuevaPosicionY < 0 || nuevaPosicionY >= indice || nuevaPosicionX >= indice) ? null : new Coordenada(nuevaPosicionY, nuevaPosicionX);

	}

	/**
	 * 
	 */

	char getLetraDeCoordenadaSinValidacion(Coordenada coordenada) {
		// // ---- System.out.println( " getLetraDeCoordenadaSinValidacion(coordenada " + coordenada.toString() + ") -> " + adn[coordenada.getFil()].charAt(coordenada.getCol()));
		lecturas ++;
		return adn[coordenada.getFil()].charAt(coordenada.getCol());
	}

	/**
	 * unidad basica de analisis, puede determinar de la manera mas optima si un conjunto de letras es mutante o no. Se asume que el objeto matriz es global
	 * 
	 * la primera letra ya llega LEIDA por lo que no se accede a esa posicion sino a las siguientes.
	 */
	public static boolean CONTINUAR = true;
	public static boolean NOCONTINUAR = false;

// String[] adnMutante = {  "TTTTTG",
	// "TTGTGC",
	// "TTTTGT",
	// "TGATGG",
	// "CCCCTA",
	// "TCACTG", };
	// SiguienteAccion encontrarRangoMutante(inclinacion inc, Coordenada posicionInicial, char letraBuscada) {

	/**
	 * este metodo se iba a llamar preparar instrucciones pero pues ... si la preparo, estoy leyendo posiciones que no me interesan. porque estaria releyengo...
	 */
	boolean recorrerInstruccionesYNotificarHallazgoMutante() {
		char letraInicial;
		Coordenada coordenadaInicial = new Coordenada(0, 0);
		for (int i = 0; i < indice; i++) {
			if (hallazgos >= 2) {
				// // ---- System.out.println("SE DETUBO LA ITERACION DE INSTRUCCIONES PORQUE ENCONTRAMOS UN MUTANTE");
				return true;
			}
			// horizontales
			coordenadaInicial.setCol(0);
			coordenadaInicial.setFil(i);
			letraInicial = getLetraDeCoordenadaSinValidacion(coordenadaInicial);

			procesarLineaCompleta(new Instruccion(CONTINUAR, inclinacion.horizontal, letraInicial, coordenadaInicial, letraInicial));
		}
		for (int i = 0; i < indice; i++) {
			if (hallazgos >= 2) {
				// // ---- System.out.println("SE DETUBO LA ITERACION DE INSTRUCCIONES PORQUE ENCONTRAMOS UN MUTANTE");
				return true;
			}
			// verticales
			coordenadaInicial.setCol(i);
			coordenadaInicial.setFil(0);
			letraInicial = getLetraDeCoordenadaSinValidacion(coordenadaInicial);

			procesarLineaCompleta(new Instruccion(CONTINUAR, inclinacion.vertical, letraInicial, coordenadaInicial, letraInicial));

		}
//		// DIAGONAL PRINCIPAL SUPERIOR
		for (int i = 0; i < indice; i++) {
			if (hallazgos >= 2) {
				// // ---- System.out.println("SE DETUBO LA ITERACION DE INSTRUCCIONES PORQUE ENCONTRAMOS UN MUTANTE");
				return true;
			}
			if (indice - i >= limiteDeSecuencia) {
				coordenadaInicial.setCol(i);
				coordenadaInicial.setFil(0);
				letraInicial = getLetraDeCoordenadaSinValidacion(coordenadaInicial);

				procesarLineaCompleta(new Instruccion(CONTINUAR, inclinacion.diagonalPrincipal, letraInicial, coordenadaInicial, letraInicial));
			}

		}
		// DIAGONAL PRINCIPAL INFERIOR
		for (int i = 1; i < indice; i++) {
			if (hallazgos >= 2) {
				// // ---- System.out.println("SE DETUBO LA ITERACION DE INSTRUCCIONES PORQUE ENCONTRAMOS UN MUTANTE");
				return true;
			}
			if (indice - i >= limiteDeSecuencia) {
				coordenadaInicial.setCol(0);
				coordenadaInicial.setFil(i);
				letraInicial = getLetraDeCoordenadaSinValidacion(coordenadaInicial);

				procesarLineaCompleta(new Instruccion(CONTINUAR, inclinacion.diagonalPrincipal, letraInicial, coordenadaInicial, letraInicial));
			}

		}
		// DIAGONAL INVERSA SUPERIOR
		for (int i = 0; i < indice; i++) {
			if (hallazgos >= 2) {
				// // ---- System.out.println("SE DETUBO LA ITERACION DE INSTRUCCIONES PORQUE ENCONTRAMOS UN MUTANTE");
				return true;
			}
			if (indice - i >= limiteDeSecuencia) {
				coordenadaInicial.setCol(i);
				coordenadaInicial.setFil(indice - 1); // siempre la de abajo
				letraInicial = getLetraDeCoordenadaSinValidacion(coordenadaInicial);

				procesarLineaCompleta(new Instruccion(CONTINUAR, inclinacion.diagonalInversa, letraInicial, coordenadaInicial, letraInicial));
			}
		}
//
		// DIAGONAL INVERSA INFERIOR
		for (int i = 0; i < indice; i++) {
			if (hallazgos >= 2) {
				// // ---- System.out.println("SE DETUBO LA ITERACION DE INSTRUCCIONES PORQUE ENCONTRAMOS UN MUTANTE");
				return true;
			}
			if (indice - i >= limiteDeSecuencia) {
				coordenadaInicial.setCol(0); // siempre el borde de la izquierda
				coordenadaInicial.setFil((indice - 1) - i); // siempre la de abajo
				letraInicial = getLetraDeCoordenadaSinValidacion(coordenadaInicial);

				procesarLineaCompleta(new Instruccion(CONTINUAR, inclinacion.diagonalInversa, letraInicial, coordenadaInicial, letraInicial));
			}
		}
		return false;
	}

	Instruccion encontrarRangoMutante(Instruccion siguienteAccion) {
		// ---- System.out.println("encontrarRangoMutante()");
		if (siguienteAccion != null && siguienteAccion.isContinuar()) {
			// ---- System.out.println("encontrarRangoMutante(inclinacion: [" + siguienteAccion.getInc() + "] , posicionInicial: [" + siguienteAccion.getCoordenadaSiguiente().toString()+ "],letrasDePalabra :[" + Algoritmo.limiteDeSecuencia + "] )");

			// SiguienteAccion respuesta = new SiguienteAccion();
			// char letraBuscada = adn[posicionInicial.getFil()].charAt(posicionInicial.getCol());
			// respuesta.setLetraBuscada(letraBuscada);

			Coordenada posicionFinal = getSiguienteCoordenadaValida(siguienteAccion.getCoordenadaSiguiente(), siguienteAccion.getInc(), Algoritmo.limiteDeSecuencia - 1);

			// datos fuera del rango pero utiles si aun seguimos sin saber si habemus mutante o no.
			char letraSiguiente = 'X';
			char letraFinal = 'X';
			Coordenada coordenadaSiguiente = posicionFinal; // esto nos sirve mas adelante

			// comparamos las letras de la inicial y la final
			// // // ---- System.out.println("if (letraBuscada == adn[posicionFinal.getFil()].charAt(posicionFinal.getCol())) {");
			if (posicionFinal == null) {
				// ---- System.out.println("NO HAY MAS");
				return new Instruccion(NOCONTINUAR);
			} else {
				// ---- System.out.println(" " + siguienteAccion.getLetraBuscada() + " == " + adn[posicionFinal.getFil()].charAt(posicionFinal.getCol()) + "");
				letraSiguiente = adn[posicionFinal.getFil()].charAt(posicionFinal.getCol());
				letraFinal = letraSiguiente;
				if (siguienteAccion.getLetraBuscada() == letraSiguiente) {

					// ---- System.out.println(" - SI si la inicial y la final son iguales es indicio de posible mutante");
					// si la inicial y la final son iguales es indicio de posible mutante
					// recorro las posiciones intermerdias y si encuentro alguna letra diferente retorno null
					// ---- System.out.println(" pasos intermedios pendientes por recorrer_:letrasDePalabra{" + Algoritmo.limiteDeSecuencia + "} - 2 = " + (Algoritmo.limiteDeSecuencia - 2));
					for (int paso = 0; paso < Algoritmo.limiteDeSecuencia - 2; paso++) {
						// ---- System.out.println(" recorriendo intermedio " + (paso + 1) + "/" + (Algoritmo.limiteDeSecuencia - 2));
						// // ---- System.out.println(" if (letraBuscada != getLetraDeCoordenadaSinValidacion(getSiguienteValida(posicionInicial, inc, paso + 1))) {");
						// // ---- System.out.println(" " + siguienteAccion.getLetraBuscada() + " != " +
						// getLetraDeCoordenadaSinValidacion(getSiguienteCoordenadaValida(siguienteAccion.getCoordenadaSiguiente(), siguienteAccion.getInc(), paso + 1)));
						// Como estamos buscando entre coordenadas de extremos, se asume que en el intermedio habra coordenadas validas y con letra

						char letraIntermediaEncontrada = getLetraDeCoordenadaSinValidacion(getSiguienteCoordenadaValida(siguienteAccion.getCoordenadaSiguiente(), siguienteAccion.getInc(), paso + 1));
						if (siguienteAccion.getLetraBuscada() != letraIntermediaEncontrada) {
							// ---- System.out.println(" SI, DIFERENTE return Nueva accion de CONTINUAR");
							// Se leyo la letra de la coordenada final y esta no correspondio a la buscada
							// se trata de leer la siguiete posicion, para poder decidir la siguiente accion
							// boolean continuar, char letraBuscada, Coordenada coordenadaSiguiente, char letraSiguiente
							coordenadaSiguiente = getSiguienteCoordenadaValida(posicionFinal, siguienteAccion.getInc(), 1);

							if (coordenadaSiguiente == null) {
								// ---- System.out.println("           no hay coordenada, es el final de la frase");
								// letraSiguiente = getLetraDeCoordenadaSinValidacion(coordenadaSiguiente);
								return new Instruccion(NOCONTINUAR);
							} else {
								// SE DEBE CONTINUAR DESDE LA FINAL YA QUE PUEDE SER INICIO DE ALGO MUTANTE
								// letraSiguiente = getLetraDeCoordenadaSinValidacion(coordenadaSiguiente);
								// ---- System.out.println("           si hay coordenada, no es el final de la frase");
								return new Instruccion(CONTINUAR, siguienteAccion.getInc(), siguienteAccion.getLetraBuscada(), posicionFinal, letraFinal);
							}

						} else {
							// ---- System.out.println(" NO, IGUAL seguimos en el ciclo");

						}
					}
					hallazgos++;
					// ---- System.out.println("<<MUTANTE>> cant: " + hallazgos + " Si llegue hasta aqui es que todas las letras son iguales. pero ahora hay que seguir porcesando");
//				respuesta = new SiguienteAccion(new Rango(posicionInicial, posicionFinal));
//				respuesta.setLetraBuscada(letraBuscada);
					// Si llegue hasta aqui es que todas las letras son iguales. Exito en la busqueda. podriamos notificar del hallazgo pero hay algo extra por analizar:
					// El objetico de todo el metodo es hallar mas de 1 caso mutante, es decir 2 o mas. y por eficiencia solo nos interesa llegar a 2.
					// si este hallazgo, en el que estamos parados es el segundo, entonces solo debemos notificarlo y ya, FIN.
					// por otra parte si este es el primer hallazgo se debe continuar buscando. entonces como ya encontramos toda una cadena larga debemos descartar que esta se extienda mas

					// evaluamos la variable hallazgos que le pertenece a la superclase Algoritmo y por ser protegida se puele leer directamente desde aqui. privilegios de ser el hijo de papi.
					// // ---- System.out.println("Como estamos de hallazgos globales???");
					if (hallazgos <= limiteDeHallazgos) {

						// ---- System.out.println("hallazgos < 1 toca seguir ...");
						// encontramos cuantos pasos nos falta hasta el borde
						int pasosFaltantes = (this.indice - 1) - (siguienteAccion.getInc() == inclinacion.vertical ? posicionFinal.getFil() : posicionFinal.getCol());
						for (; pasosFaltantes > 0; pasosFaltantes--) {
							coordenadaSiguiente = getSiguienteCoordenadaValida(coordenadaSiguiente, siguienteAccion.getInc(), 1);

							// caso donde ya tenemos un rango mutante pero llegamos al final de la linea.
							if (coordenadaSiguiente == null) {
								// ---- System.out.println("ALERTAAAAAA ESTO NUUUUUUNCAAAA DEBEBEEEE LEERSEEEEEEE ALERTA!!!!!!!!!!!!!!!!!!!!!!!!!!");
								return null; // TOFIX: si estamos en este ciclo for, nunca deberia entrar a este if ya que previamente se averiguo cuantos pasos faltaban para el final y dado qque SI
												// faltaban pudimos entrar, asi que no tiene sentido que no haya una posicion siguiente valida
							} else {
								// sabenos que la siguiente posicion no es nula asi que leemos la letra
								letraSiguiente = getLetraDeCoordenadaSinValidacion(coordenadaSiguiente);
								// asignamos la posicion y la letra a la respuesta extra
//							respuesta.setLetraSiguiente(letraSiguiente);
//							respuesta.setCoordenadaSiguiente(coordenadaSiguiente);
								if (siguienteAccion.getLetraBuscada() != letraSiguiente) {
									// Aqui se detiene todo ya que encontramos una letra distinta a la buscada, por lo que se retorna.
									// notar que en la respuesta extra el valor de letraBuscada sera distinto a letra siguiene, esto le indica a la funcion externa que aun hay nuevas cosas por leer.

									return new Instruccion(CONTINUAR, siguienteAccion.getInc(), siguienteAccion.getLetraBuscada(), coordenadaSiguiente, letraSiguiente);
								}
								// else: en este punto sabemos que la letra actual es igual a lka buscada por lo que seguimos en el ciclo
							}
						}
						// si salimos del ciclo y llegamos hasta aqui es porque todas las letras hasta el final de la frase eran iguales
						// notar que en la respuesta extra el valor de letraBuscada sera igual a letra siguiente, esto le indica a la funcion externa que llegamos al final de la frase.
						return new Instruccion(NOCONTINUAR);
					} else {
						// ---- System.out.println("hallazgos > 1 toca AVISAR Y SALIR DE AQUI SIN HACER NADA MAS ... !!!");
						return new Instruccion(NOCONTINUAR);
					}
				} else {
					// // // ---- System.out.println("NOT !!!! hallazgos < 1 toca PARAR");
					// ---- System.out.println(" - NO si la inicial y la final son distintas se descarta ese rango. Sin embargo se consulta si hay algo mas a la ''derecha''");
					// si la inicial y la final son distintas se descarta ese rango. Se retorna false y fin del metodo.
					// return new SiguienteAccion(NOCONTINUAR, letraBuscada, null, letraSiguiente);

					coordenadaSiguiente = getSiguienteCoordenadaValida(posicionFinal, siguienteAccion.getInc(), 1);

					// caso donde ya tenemos un rango mutante pero llegamos al final de la linea.
					if (coordenadaSiguiente == null) {
						// ---- System.out.println("No hay nada mas a la derecha, se acabo la fila. Aqui no hubo mutante");
						return new Instruccion(NOCONTINUAR);
					} else {
						// sabenos que la siguiente posicion no es nula asi que leemos la letra
						letraSiguiente = getLetraDeCoordenadaSinValidacion(coordenadaSiguiente);
						// ---- System.out.println("si hay algo mas a la derecha, la siguiente posicion es " + coordenadaSiguiente.toString() + "y su letra es " + letraSiguiente);
						return new Instruccion(CONTINUAR, siguienteAccion.getInc(), siguienteAccion.getLetraBuscada(), coordenadaSiguiente, letraSiguiente);
					}

				}
			}
		} else {
			// ---- System.out.println("SE DIO LA ORDEN DE NO CONTINUAR");
			return new Instruccion(NOCONTINUAR);
		}
	}

	void analisisDeEncontrarRangoMutante(Instruccion info) {

		// // ---- System.out.println("");
		// // ---- System.out.println("");
		// // ---- System.out.println("ANALISIS");
		// // ---- System.out.println("");
		if (info == null) {
			// // ---- System.out.println("Aqui no hay mutantes");
		} else {
			if (info.getLetraBuscada() == info.getLetraSiguiente()) {
				// // ---- System.out.println("Aqui SI hay mutantes y toda la frase tiene adn mutante");
			} else {
				// // ---- System.out.println("Aqui SI hay mutantes y pero en la frase hay otro adn sin explorar");
				// // ---- System.out.println("ese adn sin explorar inicia en la pos " + info.getCoordenadaSiguiente().toString() + " y comienaz con la letra " + info.getLetraSiguiente());
			}
		}

	}

	// analisisDeEncontrarRangoMutante(encontrarRangoMutante(inclinacion.diagonalPrincipal, new Coordenada(0, 0), 4));
	void procesarLineaCompleta(Instruccion instruccionInicial) {
		// Coordenada coordenadaSiguiente = instruccion.getCoordenadaSiguiente();
		// char letraInicial = adn[instruccion.getCoordenadaSiguiente().getFil()].charAt(instruccion.getCoordenadaSiguiente().getCol());
		Instruccion siguienteAccion = instruccionInicial;
		do {
			// ---- System.out.println("SE EJECUTA EL INICIO DE UN CICLO DENTRO DE LA linea");
			siguienteAccion = encontrarRangoMutante(siguienteAccion);
		} while (siguienteAccion != null && siguienteAccion.isContinuar());
	}

	@Override
	public boolean isMutant(String[] adn) throws DatosInvalidosException {
		validarCadenas(adn);
		this.adn = adn;
		this.indice = adn.length;

		return recorrerInstruccionesYNotificarHallazgoMutante();
	}

	public static void main(String[] args) {
		AlgoritmoRegex app1 = new AlgoritmoRegex();
		AlgoritmoRegexEstricto app2 = new AlgoritmoRegexEstricto();
		AlgoritmoListillo app3 = new AlgoritmoListillo();
		// @formatter:off
		String[] adnHumano = { 
				"ATGCGA", 
				"CAGTGC", 
				"TTATTT", 
				"AGACGG", 
				"GCGTCA", 
				"TCACTG" }; // @formatter:on
		String[] adnMutante = { "TTTTTG", "TTGTGC", "TTTTGT", "TGATGG", "CCCCTA", "TCACTG", };

		try {
			boolean esMutante = false;
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++) {
				esMutante = app1.isMutant(adnHumano);
			}
			long stopTime = System.currentTimeMillis();

			if (esMutante) {
				// ---- System.out.println("MUTANTE");
			} else {
				// ---- System.out.println("HUMANO");
			}
			System.out.println("APP1 tiempo de ejecucion: " + (stopTime - startTime) + "ms Se realizaron [?] lecturas desde el archivo");
			// ---- System.out.println("tiempo de ejecucion: " + (stopTime - startTime) + "ms");
		} catch (DatosInvalidosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			boolean esMutante = false;
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++) {
				esMutante = app2.isMutant(adnHumano);
			}
			long stopTime = System.currentTimeMillis();

			if (esMutante) {
				// ---- System.out.println("MUTANTE");
			} else {
				// ---- System.out.println("HUMANO");
			}
			System.out.println("APP2 tiempo de ejecucion: " + (stopTime - startTime) + "ms Se realizaron [?] lecturas desde el archivo");
			// ---- System.out.println("tiempo de ejecucion: " + (stopTime - startTime) + "ms");
		} catch (DatosInvalidosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			boolean esMutante = false;
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++) {
				esMutante = app3.isMutant(adnHumano);
			}
			long stopTime = System.currentTimeMillis();

			if (esMutante) {
				// ---- System.out.println("MUTANTE");
			} else {
				// ---- System.out.println("HUMANO");
			}
			System.out.println("APP3 tiempo de ejecucion: " + (stopTime - startTime) + "ms Se realizaron [?] lecturas desde el archivo");
			// ---- System.out.println("tiempo de ejecucion: " + (stopTime - startTime) + "ms");
		} catch (DatosInvalidosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		// // // ---- System.out.println("hola " + AlgoritmoListillo.inclinacion.diagonalPrincipal);
//		// pruebas de obtener coordenadas en 1 y 2 dimensiones.
//		// for(int i=0 ; i<200 ; i++) {
//		// // // ---- System.out.println("" + i + "" + app.getCoordenadas2D(i, 6));
//		// }
//
//		// pruebas de obtener nuevas coordenadas en 2D
//		List<Coordenada> coordenadasDePrueba = new ArrayList<Coordenada>();
//		// en diagonal principal
//		coordenadasDePrueba.add(new Coordenada(0, 0));
//		coordenadasDePrueba.add(new Coordenada(1, 1));
//		coordenadasDePrueba.add(new Coordenada(2, 2));
//		coordenadasDePrueba.add(new Coordenada(3, 3));
//		// en bordes
//		coordenadasDePrueba.add(new Coordenada(0, 1));
//		coordenadasDePrueba.add(new Coordenada(0, 2));
//		coordenadasDePrueba.add(new Coordenada(0, 3));
//
//		coordenadasDePrueba.add(new Coordenada(0, 0));
//		coordenadasDePrueba.add(new Coordenada(1, 0));
//		coordenadasDePrueba.add(new Coordenada(2, 0));
//		coordenadasDePrueba.add(new Coordenada(3, 0));
//
//		for (Coordenada c : coordenadasDePrueba) {
//
//			// // ---- System.out.println("desde " + c.toString() + " [ - ] 1 pasos adelante" + app.getSiguienteValida(c, inclinacion.horizontal, 1));
//			// // ---- System.out.println("desde " + c.toString() + " [ - ] 2 pasos adelante" + app.getSiguienteValida(c, inclinacion.horizontal, 2));
//
//			// // ---- System.out.println("desde " + c.toString() + " [ | ] 1 pasos adelante" + app.getSiguienteValida(c, inclinacion.vertical, 1));
//			// // ---- System.out.println("desde " + c.toString() + " [ | ] 2 pasos adelante" + app.getSiguienteValida(c, inclinacion.vertical, 2));
//
//			// // ---- System.out.println("desde " + c.toString() + " [ \\ ] 1 pasos adelante" + app.getSiguienteValida(c, inclinacion.diagonalPrincipal, 1));
//			// // ---- System.out.println("desde " + c.toString() + " [ \\ ] 2 pasos adelante" + app.getSiguienteValida(c, inclinacion.diagonalPrincipal, 2));
//
//			// // ---- System.out.println("desde " + c.toString() + " [ / ] 1 pasos adelante" + app.getSiguienteValida(c, inclinacion.diagonalInversa, 1));
//			// // ---- System.out.println("desde " + c.toString() + " [ / ] 2 pasos adelante" + app.getSiguienteValida(c, inclinacion.diagonalInversa, 2));
//
//		}

	}

}

class Coordenada {
	int fil;
	int col;

	Coordenada(int fil, int col) {
		this.fil = fil;
		this.col = col;
	}

	public int getFil() {
		return fil;
	}

	public void setFil(int fil) {
		this.fil = fil;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String toString() {
		return "[" + this.fil + "," + this.col + "]";
	}
}

class Rango {
	private Coordenada min;
	private Coordenada max;

	public Rango(Coordenada min, Coordenada max) {
		super();
		this.min = min;
		this.max = max;
	}

	public Coordenada getMin() {
		return min;
	}

	public void setMin(Coordenada min) {
		this.min = min;
	}

	public Coordenada getMax() {
		return max;
	}

	public void setMax(Coordenada max) {
		this.max = max;
	}
}

class Instruccion {
	private inclinacion inc;
	private boolean continuar;
	private Coordenada coordenadaSiguiente;
	private char letraBuscada;
	private char letraSiguiente;

//	public SiguienteAccion() {
//		super();
//		continuar = false;
//	}

//	public SiguienteAccion(Coordenada coordenadaSiguiente, char letraSiguiente) {
//		super();
//		this.coordenadaSiguiente = coordenadaSiguiente;
//		this.letraSiguiente = letraSiguiente;
//	}

	public char getLetraBuscada() {
		return letraBuscada;
	}

	public Instruccion(boolean continuar) {
		super();
		this.continuar = continuar;
	}

	/**
	 * 
	 * @param continuar
	 * @param coordenadaSiguiente
	 * @param letraBuscada
	 * @param letraSiguiente
	 */
	public Instruccion(boolean continuar, inclinacion inc, char letraBuscada, Coordenada coordenadaSiguiente, char letraSiguiente) {
		super();
		this.continuar = continuar;
		this.coordenadaSiguiente = coordenadaSiguiente;
		this.letraBuscada = letraBuscada;
		this.letraSiguiente = letraSiguiente;
		this.inc = inc;
	}

	public void setLetraBuscada(char letraBuscada) {
		this.letraBuscada = letraBuscada;
	}

	public Coordenada getCoordenadaSiguiente() {
		return coordenadaSiguiente;
	}

	public void setCoordenadaSiguiente(Coordenada coordenadaSiguiente) {
		this.coordenadaSiguiente = coordenadaSiguiente;
	}

	public char getLetraSiguiente() {
		return letraSiguiente;
	}

	public void setLetraSiguiente(char letraSiguiente) {
		this.letraSiguiente = letraSiguiente;
	}

	public boolean isContinuar() {
		return continuar;
	}

	public void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}

	public inclinacion getInc() {
		return inc;
	}

	public void setInc(inclinacion inc) {
		this.inc = inc;
	}

}

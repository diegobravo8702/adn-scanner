package com.bravo.meli.adn.negocio.algoritmos;

import com.bravo.meli.adn.negocio.excepciones.DatosInvalidosException;

public class AlgoritmoListillo extends Algoritmo {

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
	 como la matriz se puede recorrer en distintas direcciones, se debe crear un algoritmo que me entregue la posicion 2D de una posicion cercana relativa
	 por ejemplo:
	 si recorro la matriz en direccion diagonal principal (sup izq -> inf derecha) la casilla siguiente a la celda 3,4 seria 4,5
	 mientras que la siguiente a la 5,2 seria la 6,4 pero esta no existe en la matriz, nos hemos salido. si fuera como el juego del pacman que se sale al otro lado
	 la casilla seria la 0.3 pero en el ejercicio de mercadolibre no se contemplo este escenario. los bordes son el límite. aunque saber eso sirve en la logica de los dos algoritmos anteriores a este

	 nota para mas adelante: pedirle a esta funcion que entregue un dato y ese este fuera del borde puede servir de fin de algun proceso o tambien para indicar que hay un separador de palabras .. no se aun.
	 
	*/

	enum direccion{
		horizontal,
		vertical,
		diagonalPrincipal,
		diagonalInversa
	};

// se llama getSiguiente pero puede traer saltandose x espacios
	private Coordenada getSiguiente(direccion dir, int saltarse){
		switch (dir){
			case horizontal:

			break;
			default:
		}
		return new Coordenada(0,0);

	}

	@Override
	public boolean isMutant(String[] adn) throws DatosInvalidosException {
		// La idea es ir dando saltos en el arreglo hasta llegar al sigueinte valor que deberia ser igual al prmero para considerarse un hallazgo
		// será un ciclo while que se concluirá cuando se active la bandera de barrido totalmente o de manera abrupta con el hallazgo de 2 casos positivos para mutante
		// aqui habra ciclos dentro de ciclos por lo que al darse el hallazgo se notifica con un return

		boolean debemosContinuar = true;
		int punteroPrincipal = 0;
		while(debemosContinuar){

		}

		return false;
	}

	public static void main(String [] args) {
		AlgoritmoListillo app = new AlgoritmoListillo();
		
		for(int i=0 ; i<20 ; i++) {
			System.out.println("" + i + "" + app.getCoordenadas2D(i, 6));
		}
		

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
		return "[" + this.fil + "," + this.col+"]";
	}

}

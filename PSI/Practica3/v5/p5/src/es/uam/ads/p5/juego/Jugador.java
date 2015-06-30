package es.uam.ads.p5.juego;

/**
 * Enumerado que representa cada uno de los bandos de las 3 en raya. 
 */
public enum Jugador {
	BLANCO('O'), NEGRO('X');
	
	/**
	 * S�mbolo que representa a cada jugador
	 */
	private char symbol; 
	
	/**
	 * Constructor privado del enum con el s�mbolo del jugador
	 * @param s
	 */
	private Jugador(char s) {
		this.symbol = s;
	}
		
	/**
	 * Devuelve el s�mbolo del jugador como un String
	 */
	@Override public String toString() {
		return String.valueOf(this.symbol);
	}
}	

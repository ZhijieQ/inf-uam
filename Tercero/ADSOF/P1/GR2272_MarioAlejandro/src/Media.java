/** 
 * @author Alejandro  y Mario
 * 
 * Esta aplicación muestra el mensaje "Hola mundo!" por pantalla 
 */ 


public class Media {
/**
* Punto de entrada a la aplicación.
*
* Este método imprime el logaritmo Neperiano del número que se le pasa como entrada
*
* @param args Los argumentos de la línea de comandos. Se espera un número como primer parámetro
*/
	public static void main(String[] args) {
		if (args.length<2) {
			System.out.println("Se esperan dos numero como parametro.");
			return;
		}
		Integer arg1 = Integer.parseInt(args[0]);
		Integer arg2 = Integer.parseInt(args[1]);
		System.out.println("Media:"+((arg1+arg2)/2.0)+"");
	}
}

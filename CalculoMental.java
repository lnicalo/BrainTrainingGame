
import java.util.Random;

/**
 * Clase CalculoMental que representa a un juego de cálculo mental.
 * Se encarga de gestionar las dificultades relacionadas con el desarrollo del juego como
 * pueden ser la generación de nuevas perguntas aleatoriamente, correcion y calculo de puntos
 * Además ofrece una serie de metodos para poder cambiar el nivel de los números escogidos y el
 * número de operaciones de cada cuenta.
 * Realiza las operaciones pero no se encarga de la interfaz.
 * @see CalculoPanel
 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */
public class CalculoMental {
	
	/**
	 * Crea un objeto CalculoMental con un nivel de numeros entre 1 y 20, 
	 * con 2 operaciones por cuenta y con una duracion total de un minuto
	 */
	public CalculoMental() {
		// Parametros por defecto
		nivel 	= 20;
		numOper = 2;
		puntos 	= 0;
		tiempoMax = 60;
	}
	
	/**
	 * Genera una nueva cuenta de manera aleatoria
	 * @return String con la cuenta generada almacenada
	 */
	public String genCuenta() {
		String cuenta = new String("");
		Random generador = new Random();
		int i;
		// Generamos los vectores donde se van almacenar los numeros
		// y las operaciones. El tamaño de esto depende del numero de
		// operaciones
		operandos = new int[numOper+1];
		operaciones = new char[numOper];
		
		// Generacion aleatoria de los numeros
		for (i=0;i < operandos.length;i++) {
			// Evitamos que se propongan cuentas donde uno de los operandos es 0
			while((operandos[i] = generador.nextInt(nivel)) == 0);
		}
		
		// Generacion aleatoria de la operaciones.
		for (i=0;i < operaciones.length;i++) {
			
			switch (generador.nextInt(3)) {
				case 0:
					operaciones[i] = '+';
					break;
				case 1:
					operaciones[i] = '-';
					break;
				case 2:
					operaciones[i] = '*';
					break;
			}
		}
		
		// Construimos el string que va a contener la cuenta
		for (i=0;i < operaciones.length;i++) {
			cuenta = cuenta + operandos[i] + operaciones[i];
		}
		cuenta = cuenta + operandos[operandos.length-1];
		return cuenta;
	}
	
	/**
	 * Corrige una repuesta a la cuenta generada anteriormente y suma los puntos correspondientes
	 * @param unaRespuesta la respuesta que se desea corregir
	 * @return boolean con true si es correcto false si no
	 */
	public boolean checkCuenta(int unaRespuesta, int tiempo) {
		int acc;
		int i,j;
		
		// Hacemos un primer recorrido para hacer primero las multiplicaciones
		i = 0;
		while ( i < operaciones.length ) {
			if (operaciones[i] == '*') {
				operandos[i] = operandos[i]*operandos[i+1];
				for (j=i;j < operaciones.length-1;j++) {
					// Desplazamos todos las operaciones y operandos
					// un indice mas abajo si se ha hecho alguna operacion
					// de multiplicacion
					operandos[j+1] = operandos[j+2];
					operaciones[j] = operaciones[j+1];
				}
				// Los operandos con valor 0 no se tendran en cuenta
				operandos[operandos.length-1] = 0;
				operaciones[operaciones.length-1] = ' ';
				i = 0; // Hay que reiniciar la busqueda de los asteriscos porque uno de ellos se
				// puede haber movido de la posicion i a la i-1.			
			}
			else {
				i++;
			}
		}
		// Operamos lo que queda: sumas y restas
		acc = operandos[0];
		for (i=0;i < operaciones.length && operandos[i+1] != 0 && operaciones[i]!= ' ';i++) {
			switch (operaciones[i]) {
				case '+':
					acc += operandos[i+1];
					break;
				case '-':
					acc -= operandos[i+1];
					break;
				default:
					System.out.println("Error en checkCuenta()");
			}
		}
		correcion = (acc == unaRespuesta);
		ContarPuntos(tiempo);
		return correcion;
	}
	
	/**
	 * Devuelve los puntos acumulados hasta el momento
	 * @return String con los puntos acumulados hasta el momento
	 */
	public String getPuntos() {
		String puntosStr = new String();
		puntosStr = String.valueOf(puntos);
		return puntosStr;
	}
	
	/**
	 * <p>Suma los puntos por una respuesta dada con los siguientes criterios:</p>
	 * <ul>
	 * 		<li>En la puntuacion a cada respuesta se tiene en cuenta el nivel y del numero de operaciones</li>
	 * 		<li>Por cada operacion que se realice correctamente se suman puntos</li>
	 *		<li>Por cada operacion que se realize incorrectamente se resta un cuarto de los puntos que se 
	 * 			podian haber obtenido si se hubiera hecho correctamente</li>
	 * 		<li>Ademas hay mayor bonificacion cuanto menor es el tiempo (cuanto mas tension hay) </li>
	 * </ul>
	 * <p>Sistema de puntuacion:</p>
	 * <ul>
	 *		<li>Da una mayor importancia a la dificultad de los numeros escogidos que 
	 *			al numero de operaciones porque se ha comprobado que afecta mas a la 
	 *			dificultad (esta elevedo a un mayor exponente).</li>
	 *		<li>Tambien se ha tenido en cuenta el tiempo maximo. Cuanto menor es mayor 
	 *			bonificacion hay por cada cuenta. Ademas cuando se acerca a los segundos
	 *			finales las cuentas empiezan a valer mucho mas (crecimiento exponencial)
	 *			para potenciar que el usuario se quede a jugar hasta el final y ademas
	 *			este crecimiento es mayor cuanto menor es el tiempo maximo escogido para
	 *			compensar el menor tiempo total disponible.
	 *			Por otro lado tambien no hay que perjudicar demasiado al que ha estado 
	 *			mucho mas tiempo haciendo cuentas. Se tiene que llegar a un compromiso</li>
	 *	
	 * <p>Expresion matematica del cálculo de la puntuacion:<br>
	 *		puntosFijos = (nivel^2)*(numOper);<br>
	 *		puntosTiempo = 'puntosFijos*exp(-0.0001*tiempo*tiempoMax)';<br>
	 *		puntuacion = puntosFijos + 15*puntosTiempo;<br>
	 * @param tiempo Segundo del juego en el que se produjo la respuesta
	 */
	 
	protected void ContarPuntos(int tiempo) {
		long puntosFijos = (long)Math.pow(nivel,2)*numOper;
		long puntosTiempo = (long) (puntosFijos*Math.exp(-0.0001*tiempo*tiempoMax));
		long puntuacion = puntosFijos + 10*puntosTiempo;
		if (correcion) {
			puntos += puntuacion;
		}
		else {
			puntos -= puntuacion/4;
		}
	}
	
	/**
	 * Establece el nivel del juego. Los numeros son elegidos aleatoriamente entre 1 y unNivel
	 * @param unNivel dificultad en los numeros escogidos
	 * @param unNumOper numero de operaciones en cada cuenta
	 */
	public void setNivel(int unNivel, int unNumOper) {
		nivel = unNivel;
		numOper = unNumOper;
	}
	
	/** Reinicia el juego */
	public void reset() {
		puntos = 0;
	}
	
	/**
	 * Establece el tiempo máximo para cada prueba
	 * @param unTiempoMax tiempo
	 */
	public void setTiempoMax(int unTiempoMax) {
		tiempoMax = unTiempoMax;
	}
	
	/**
	 * Obtiene el tiempo máximo de juego
	 * @return entero con el tiempo máximo
	 */
	public int getTiempoMax() {
		return tiempoMax;
	}
	
	/**	
	 * Vector con las operaciones. Estan en orden de izquierda a 
	 * derecha. El indice 0 corresponde con el que esta más a la izquierda 
	 */
	private	char[]	operaciones;
	/** 
	 * Vector con los numeros que hay que operar. El indice 0 corresponde con 
	 * el primer operando por la izquierda 
	 */ 
	private	int[]	operandos;
	/** 
	 * Los numeros enteros que forman las operaciones son una variable aleatoria uniforme
	 * entre 1 y nivel
	 */
	private int		nivel;
	/** Numero de operaciones que forma la cuenta */
	private int		numOper;
	/** Puntos acumulados hasta el momento */
	private int		puntos;
	/** Valoracion de la última respuesta, es decir, ei ha sido correcta o no */
	private boolean	correcion;	
	/** Duracion total del juego */
	private	int		tiempoMax;
}

import java.util.Random;
/**
 * Clase MasterMind que representa a un juego MasterMind.
 * Se encarga de gestionar las dificultades relacionadas con el desarrollo del juego como
 * pueden ser la generaci�n de nuevos c�digos secretos aleatoriamente, correci�n de estos 
 * y calculo de puntos. Adem�s ofrece una serie de m�todos para poder cambiar los n�meros
 * que formar�n el c�digo y la longitud del c�digo.
 * Realiza las operaciones pero no se encarga de la interfaz.
 * @see MasterPanel
 * @author  Daniel G�mez Frontela
 * @author	Luis Fernando Nicol�s Alonso
 */

public class MasterMind {
	/** Vector que contiene la secuencia de numeros, c�digo secreto*/
	private int[]	v;			
	
	/** Intentos que le quedan al jugador para resolver la secuencia*/
	private int		intentos;	
	
	/** Intentos m�ximos*/
	private int		intentosMax;
	
	/** N�mero de opciones que pueden formar el c�digo*/
	private int		nivel;
	
	/** Puntos acumulados hasta el momento*/
	private long	puntos;	
	
	/** Contiene vector con n�meros acertados en la anterior respuesta*/
	private int[]	correccion;
	
	/** Tiempo m�ximo de juego*/
	private int 	tiempoMax;	
	
	/**
	 * Crea un objeto MasterMind con un nivel por defecto (6 n�meros y 10 intentos)
	 */
	public MasterMind() {
		// Por defecto dejamos 6 numeros distintos y 10 intentos
		nivel 	= 6;
		puntos 	= 0;
		// El vector de correcion contiene:
		//		correccion[0] los que hay correctos
		//		correccion[1] los que estan en la secuencia pero no bien colocados
		correccion = new int[2];
		intentosMax = 10;
		v = new int[4];
		// Por defecto dejamos 10 minutos. Se puede cambiar.
		tiempoMax = 600;
	}
	
	/**
	 * Generamos secuencia de c�digo secreto a adivinar
	 */
	public  void genVector() {
		int num_aleat;
		// Reiniciamos el numero de intentos
		intentos = intentosMax;
		// Vector de enteros que van de 1 a nivel: 1,2,...,nivel
		int[] enteros = new int[nivel];
		for(int i=0;i<enteros.length;i++) {
			enteros[i] = i+1;
		}
		
		Random generador = new Random();
		// Selecionamos un numero aleatoriamente del vector de enteros, que contiene
		// los numeros que hay disponibles (para no repertir numeros en la secuencia),
		// una vez seleccionado el numero lo quitamos del vector de enteros y desplazaos
		// un indice menos a los que estan en indices superiores
		// Ejemplo:
		// Nivel: 6 enteros = {1,2,3,4,5,6}
		// num_aleat = 3 -> v[0]=4
		// Quitamos el 4 y desplazamos los numeros: enteros = {1,2,3,5,6,0}
		// Repetimos el procedimiento hasta completar la secuencia
		for (int i=0; i<4; i++) {
				num_aleat = generador.nextInt(nivel-i);
				v[i] = enteros[num_aleat];
				for (int j=num_aleat+1;j < enteros.length;j++) {
					enteros[j-1] = enteros[j];
				}
				enteros[enteros.length-1] = 0;
		}
	}
	
	
	
	 /**
	  * Corrige una repuesta al c�digo secreto
	  * y suma los puntos correspondientes si se acert�
	  * @param resp c�digo introducido por el usuario
	  * @return vector de correci�n
	  */
	public int[] checkVector(int[] resp, int tiempo) {
		// Reiniciamos el vector correcion
		correccion[0] = 0;
		correccion[1] = 0;
		if (intentos > 0) {
			// Se ha consumido un intento
			intentos--;
			
			// Hallamos el numero de enteros correctos en su posicion
			for (int i=0;i < v.length; i++ ) {
				if (v[i] == resp[i]) {
					correccion[0]++;
				}
			}
			
			// Si no se ha acertado completamente la secuencia
			if (correccion[0] != 4) {
				// hallamos los que estan en la secuencia pero no estan bien colocados
				for (int i=0;i<resp.length; i++ ) {
					for (int j=0;j< v.length; j++) {
						// No contamos los que estan bien colocados. Obviamente
						if (i==j)
							continue;
						// Si un numero de resp coincide con v sumamos a correccion 1
						else if (v[j] == resp[i]) {
							correccion[1]++;
						}
					}
				}
				
			}
			
			// Si se ha acertado la secuencia contamos los puntos
			else {
				ContarPuntos(tiempo);
			}
		}
		return correccion;
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
	 * 		<li>En la puntuacion a cada respuesta se tiene en cuenta el n�mero de opciones y el n�mero de intentos</li>
	 * 		<li>Por cada c�digo acertado se suman puntos</li>
	 *		<li>Por cada vez que no se acierte no se restan puntos ya que el juego tiene bastante dificultad y es
	 *			dif�cil acertar en los primeros intentos</li>
	 * 		<li>Ademas hay mayor bonificacion cuanto menor es el tiempo (cuanto mas tension hay) </li>
	 * </ul>
	 * <p>Sistema de puntuacion:</p>
	 * <ul>
	 *		<li>Da pr�cticamente la misma importancia a la cantidad de opciones del c�digo que al 
	 *			n�mero m�ximo de intetnos. Cuanto mayor es la cantidad y
	 *			menor el n�mero de intentos m�s puntos se sumar�n.</li>
	 *		<li>Tambien se ha tenido en cuenta el tiempo maximo. Cuanto menor es mayor 
	 *			bonificacion hay por cada respuesta correcta. Ademas cuando se acerca a los segundos
	 *			finales las cuentas empiezan a valer mucho mas (crecimiento exponencial)
	 *			para potenciar que el usuario se quede a jugar hasta el final y ademas
	 *			este crecimiento es mayor cuanto menor es el tiempo maximo escogido para
	 *			compensar el menor tiempo total disponible.
	 *			Por otro lado tambien no hay que perjudicar demasiado al que ha estado 
	 *			mucho mas tiempo jugando. Se tiene que llegar a un compromiso</li>
	 *	
	 * <p>Expresion matematica del c�lculo de la puntuacion:<br>
	 *		puntosFijos = nivel^(10-(intentosMax-intentos)) + 1000;<br>
	 *		puntosFijos = nivel^(10-(intentosMax-intentos)) + 1000;<br>
	 *		puntuacion = puntosFijos + 4*puntosTiempo;<br>
	 * @param tiempo Segundo del juego en el que se produjo la respuesta
	 */
	protected void ContarPuntos(int tiempo) {
		long puntosFijos = (int)Math.pow(nivel,10-(intentosMax-intentos)) + 1000;;
		long puntosTiempo = (long) (puntosFijos*Math.exp(-0.0001*tiempo*tiempoMax));
		long puntuacion = puntosFijos + 8*puntosTiempo;
		puntos += puntuacion;
	}
	
	/**
	 * Establece el nivel del juego. Las opciones van entre 1 y unNivel
	 * @param unNivel cantidad de n�meros del c�digo
	 */
	public void setNivel(int unNivel) {
		nivel = unNivel;
	}
	
	/**
	 * Establece el tiempo m�ximo de juego
	 * @param unTiempo tiempo
	 */
	public void setTiempoMax(int unTiempo) {
		tiempoMax = unTiempo;
	}
	
	/**
	 * Establece el n�mero m�ximo de intentos
	 * @param unNumIntentos n�mero m�ximo de intentos
	 */
	public void setIntentosMax(int unNumIntentos) {
		intentosMax = unNumIntentos;
	}
	
	/**
	 * Devuelve n�mero de opciones del c�digo
	 * @return n�mero de opciones del c�digo
	 */
	public int getNumOpciones() {
		return nivel;
	}
	
	/** Reinicia el juego */
	public void reset() {
		puntos = 0;
	}
	
	/**
	 * Devuelve n�mero m�ximo de intentos
	 * @return n�mero m�ximo de intentos
	 */
	public int getIntentosMax() {
		return intentosMax;
	}
	
	/**
	 * Devuelve tiempo m�ximo de juego
	 * @return tiempo m�ximo de juego
	 */
	public int getTiempoMax() {
		return tiempoMax;
	}
	
	/**
	 * Devuelve el n�mero de intento actual
	 * @return intento actual
	 */
	public int getIntento() {
		return (intentosMax-intentos);
	}
}

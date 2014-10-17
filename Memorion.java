import java.util.Random;
/**
 * Clase Memorion que representa a un juego de memorizar secuencias de n�meros.
 * Se encarga de gestionar las dificultades relacionadas con el desarrollo del juego como
 * pueden ser la generaci�n de nuevas secuencias aleatoriamente, correci�n y calculo de puntos
 * Adem�s ofrece una serie de metodos para poder cambiar el nivel y el
 * tiempo m�ximo para memorizar la secuncia.
 * Realiza las operaciones pero no se encarga de la interfaz.
 * @see MemoriaPanel
 * @author  Daniel G�mez Frontela
 * @author	Luis Fernando Nicol�s Alonso
 */
public class Memorion {
	/**  Vector que contiene la secuencia de numeros*/
	private int[]	v;		
	
	/** Cantidad de n�meros de la secuencia*/
	private int		nivel;
	
	/** Puntos acumulados hasta el momento*/
	private long		puntos;		
	
	/** Contiene si se ha acertado el �ltimo n�mero o no*/
	private int		correcion;	
	
	/** Tiempo m�ximo que se mostrar�n las secuencias para memorizarlas*/
	private	int		tiempoMax;
	
	/** Indica en que numero de la secuencia nos llegamos*/
	private int		n;		
	
	/** Racha de n�meros acertados seguidos. Se mantiene entre secuencias distintas*/
	private int 	correctoAcc; 
	
	/**
	 * Crea un objeto Memorion con un nivel por defecto
	 * @param unNivel nivel del juego
	 */
	public Memorion(int unNivel) {
		nivel 	= unNivel;
		puntos 	= 0;
		// Por defecto dejamos 5 segundos. Se puede cambiar.
		tiempoMax = 5;
		correctoAcc = 0;
	}
	
	/**
	 * Generamos secuencia de n�meros a memorizar
	 * @return vector que guarda secuencia de n�meros a memorizar
	 */
	public  int[] genVector() {
		n = 1; // Reiniciamos el numero de secuencia donde nos llegamos
		
		//Vector de 9 enteros
		v = new int[9];
		Random generador = new Random();
		int i, num_aleat;
		
		//Inicializamos v todo a 0
		for(i=0; i<9; i++) {
			v[i] = 0;
		}
		
		//Realizamos bucle para asignar n�meros del 1 al nivel dado a posiciones del vector v,
		//sin repetir para conseguir tener el rango que queremos desordenado en el vector v.
		//Si nivel<9 habr� posiciones de v que seguir�n conteniendo 0
		for (i=1; i<=nivel; i++) {
			do {
				//Generamos n�mero aleatorio entero entre 0 y 8 que ser� el �ndice de la
				//posici�n que llevar� el n�mero i dentro del vector v
				//Ejemplo: i=1; num_aleat=5; hace que v[5]=1; el 1 vaya en la posici�n 6 del vector
				num_aleat = generador.nextInt(9);
			} while (v[num_aleat]!=0); // Si en esa posici�n hay algo distinto de 0,
			//generaremos otro porque en esa posici�n ya habr� caido otro n�mero anterior del bucle 
			v[num_aleat]=i;
		}
		return v;
	}
	
	/**
	 * Corrige una repuesta al n�mero que nos llegamos dentro de la secuencia
	 * y suma los puntos correspondientes si se acert�
	 * @param unaRespuesta posici�n donde el usuario dice que est� el n�mero
	 * @return booelan con true si es correcto false si no
	 */
	public int checkVector(int unaRespuesta) {
		//Si el n�mero por el que nos llegamos (n) est� 
		//en la posici�n que ha decidido el usuario pues est� bien
		if( n == v[unaRespuesta]) {
			if (n == nivel) {
				correcion = 2;
				ContarPuntos();
				correctoAcc =+ 5;
			}
			else {
				correcion = 1;
				n++;
				// Tenemos que contar puntos antes de reiniciar el acumulado 
				ContarPuntos();
				correctoAcc++;
			}
		}
		else {
			n = 1; // Le obligamos a volver a empezar la secuencia si ha fallado el n�mero
			correcion = 0;
			// Tenemos que contar puntos antes de reiniciar el acumulado 
			ContarPuntos();
			correctoAcc = 0;
		}
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
	 * 		<li>En la puntuacion a cada respuesta se tiene en cuenta:</li>
	 * 		<li>el nivel(cantidad de n�meros de la secuencia) y el tiempo m�ximo que se muestra la secuencia</li>
	 * 		<li>Por cada n�mero acertado en orden se suman puntos</li>
	 *		<li>Por cada n�mero errado se resta la mitad de los puntos que se 
	 * 			podian haber obtenido si se hubiera hecho correctamente</li>
	 * 		<li>Ademas hay mayor bonificacion cuantos m�s n�meros correctos se vayan encadenando </li>
	 * </ul>
	 * <p>Sistema de puntuacion:</p>
	 * <ul>
	 *		<li>Da pr�cticamente la misma importancia a la cantidad de n�meros de la
	 *			secuencia que al tiempo m�ximo que se muestra la secuencia. Cuanto mayor es la cantidad y
	 *			menor el tiempo m�s puntos se sumar�n.</li>
	 *		<li>Tambien se han tenido en cuenta los aciertos encadenados, ya que el cansancio puede
	 *			causar peque�as distracciones que hacen m�s dif�cil mantener la concentraci�n durante
	 *			secuencias consecutivas a memorizar.</li>
	 *	
	 * <p>Expresion matematica del c�lculo de la puntuacion:<br>
	 *		puntos = nivel*(rachaDeAciertos +1 )*(16-tiempoMax);<br>
	 */
	protected void ContarPuntos() {
		if (correcion == 1 || correcion == 2) {
			puntos += nivel*(correctoAcc +1 )*(16-tiempoMax);
		}
		else {
			puntos -= nivel*(correctoAcc + 1)*(16-tiempoMax)/2;
		}
	}
	
	/**
	 * Establece el nivel del juego. La secuencia es generada entre 1 y unNivel
	 * @param unNivel cantidad de n�meros de la secuencia
	 */
	public void setNivel(int unNivel) {
		nivel = unNivel;
	}
	
	/** Reinicia el juego */
	public void reset() {
		puntos = 0;
	}
	
	/**
	 * Establece el tiempo m�ximo que se muestran las secuencias para memorizarlas
	 * @param unTiempoMax tiempo
	 */
	public void setTiempoMax(int unTiempoMax) {
		tiempoMax = unTiempoMax;
	}
	
	/**
	 * Obtiene el tiempo m�ximo que se muestran las secuencias para memorizarlas
	 * @return entero con el tiempo m�ximo
	 */
	public int getTiempoMax() {
		return tiempoMax;
	}
}


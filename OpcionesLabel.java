import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
/**
 * Clase OpcionesLabel guarda las posibles opciones de las que estar� formado el c�digo y gestiona el portapapeles
 * @author Daniel G�mez Frontela
 * @author Luis Fernando Nicol�s Alonso
 */

public class OpcionesLabel extends JLabel{
	/** Contenido del portapapeles*/
	private int portapapeles;
	
	/** Para habilitar escuchar el evento de hacer click sobre las etiquetas*/
	private boolean enable;
	
	/** �ndice de la posici�n en el vector de etiquetas*/
	private int	contenido;
	
	/**
	 * Crea un nuevo OpcionesLabel
	 * @param unPortapapeles contenido del portapapeles
	 * @param indice �ndice de la etiqueta
	 */
	public OpcionesLabel(int unPortapapeles, int indice) {
		portapapeles = unPortapapeles;
		contenido	= indice;
		addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent evt) {
        		if (enable) {
        			if (portapapeles == 0) {
        				portapapeles = contenido;
        				setEnable(false);
        			}
        		}
        	}
		});
	}
	public void setEnable(boolean unEnable) {
		enable = false;
		setVisible(false);
	}
}

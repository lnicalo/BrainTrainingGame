import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/** Clase MemoriaPanel representa un panel con todas funcionalidades 
 * de un juego de memmorización de secuencias
 * @author Daniel Gómez Frontela
 * @author Luis Fernando Nicolás Alonso
 */
public class MemoriaPanel extends JPanel{
	
	/** Crea un nuevo panel MemoriaPanel*/
	public MemoriaPanel() {
		// Dejamos user como null
		user = null;
		
		// Creacion de los componentes
		juego = new Memorion(5);
        iconoPanel = new JPanel();
        iconLabel = new JLabel();
        
        //Vector de etiquetas         
        label = new JLabel[9];
        for(i = 0; i < label.length; i++) {
            label[i] = new JLabel();
        }
        
        mainPanel = new JPanel();
        pregLabel = new JLabel();
        puntosLabel = new JLabel();
        relojLabel = new JLabel();
        userPanel = new JPanel();
        nivelButton = new JButton();
        comenzarButton = new JButton();
        pararButton = new JButton();
        bottomPanel = new JPanel();
        userLabel = new JLabel();
        
        // Layout
        setLayout(null);

        // Sin layout. La imagen creada tiene que ser exactamente del mismo tamaño
        // para que no sea cortada al mostrarla
        iconoPanel.setLayout(null);
        
        try {
        	iconLabel.setIcon(new ImageIcon(getClass().getResource("memoria.jpg")));
        }
        catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "No está disponible la imagen memoria.jpg\n" +
        			"Algunas de la imágenes incluidas no se mostrarán");
        }
        
        iconoPanel.add(iconLabel);
        iconLabel.setBounds(5, 5, 190, 490);

        add(iconoPanel);
        iconoPanel.setBounds(0, 0, 180, 490);

        mainPanel.setLayout(null);


        mainPanel.add(pregLabel);
        pregLabel.setBounds(70, 20, 370, 20);
        
        
        //Dibujamos cuadrícula donde irán los números de la secuencia
        i = 0;
        for (f=1; f<=3; f++) {
        	for (c=1; c<=3; c++) {
        		mainPanel.add(label[i]);
            	label[i].setBounds((110*c)-20, (110*f)-60, 80, 80);
            	
            	label[i].setHorizontalAlignment(JLabel.CENTER);
                
                label[i].setBackground(Color.blue);
                label[i].setOpaque(true);
                label[i].addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        labelsClickedPerformed(evt);
                    }
                });
                i++;
        	}
        }
        
        mainPanel.add(puntosLabel);
        puntosLabel.setBounds(70, 360, 370, 20);

        mainPanel.add(relojLabel);
        relojLabel.setBounds(70, 400, 370, 20);

        add(mainPanel);
        mainPanel.setBounds(200, 70, 480, 430);

        bottomPanel.setLayout(null);

        nivelButton.setText("Nivel");
        bottomPanel.add(nivelButton);
        nivelButton.setBounds(20, 20, 130, 23);

        comenzarButton.setText("Comenzar");
        bottomPanel.add(comenzarButton);
        comenzarButton.setBounds(180, 20, 130, 23);
        
        pararButton.setText("Parar");
        bottomPanel.add(pararButton);
        pararButton.setBounds(330, 20, 130, 23);
        
        add(bottomPanel);
        bottomPanel.setBounds(200, 500, 480, 60);

        userPanel.setLayout(null);

        userPanel.add(userLabel);
        userLabel.setBounds(70, 30, 370, 40);

        add(userPanel);
        userPanel.setBounds(200, 0, 480, 100);
        
        // Damos el contenido que tiene que haber en los componentes por defecto
        userLabel.setText("Usuario: Default user");
    
        // de principio el usuario no puede jugar tiene que dar al boton comenzar
        nivelButton.setEnabled(true);
    	comenzarButton.setEnabled(true);
    	pararButton.setEnabled(false);
        pregLabel.setText("Pulse el boton comenzar para jugar");
      
        puntosLabel.setText("Puntos: 0");
        relojLabel.setText("00:00");
        
        // Hacemos la fuente más grande porque queda un poco pequeño
		comenzarButton.setFont(new Font("Arial",Font.BOLD,17));
		iconLabel.setFont(new Font("Arial",Font.BOLD,17));
		nivelButton.setFont(new Font("Arial",Font.BOLD,17));
		pararButton.setFont(new Font("Arial",Font.BOLD,17));
		pregLabel.setFont(new Font("Arial",Font.BOLD,17));
		puntosLabel.setFont(new Font("Arial",Font.BOLD,17));
		relojLabel.setFont(new Font("Arial",Font.BOLD,17));
		userLabel.setFont(new Font("Arial",Font.BOLD,30));
       
        // Añadimos los eventos
        nivelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                nivelButtonActionPerformed(evento);
            }
        });
        
        comenzarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comenzarButtonActionPerformed(evt);
            }
        });
        
        pararButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pararButtonActionPerformed(evt);
            }
        });
        
        timer = new Timer (1000, new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
            	timerAction();
            }
        });
    }

    // Funciones manejadoras de eventos
        
	/**
	 * Manedor del evento clic sobre el boton nivel
     * Se mustra un dialogo para modificar el nivel
	 */
    protected void nivelButtonActionPerformed(ActionEvent evento) {
    	Window ventanaPadre = SwingUtilities.getWindowAncestor(
                (Component) evento.getSource());
		if (dialogNivel == null) {
            if (ventanaPadre instanceof JFrame)
            {
                dialogNivel = new NivelMemoria((JFrame) ventanaPadre);
                // Hacemos que el usuario no pueda cambiar el tamaño del dialogo para que no se
        		// descoloque el contenido y programos el tamano suficiente para que se vean todos
        		// los controles
                dialogNivel.setResizable(false);
            }
            else  {
            	JOptionPane.showMessageDialog(this, "MenoriaPanel.java: Error en la creacion del dialogo");
            	System.exit(0);
            }
		}
		// Centramos el dialogo
		Rectangle rc = ventanaPadre.getBounds();
        dialogNivel.setBounds(rc.x + (BrainTraining.anchura - NivelMemoria.anchura) / 2 ,
        		rc.y + (BrainTraining.altura - NivelMemoria.altura) / 2,
        		NivelMemoria.anchura, NivelMemoria.altura);
        
        // Obtenemos los valores por defecto de los datos del usuario de la base de datos
        Object campo = user.getCampo("memoriaCantidadNum");
		dialogNivel.setNivel(campo);
		campo = user.getCampo("memoriaTiempoMax");
		dialogNivel.setTiempoMax(campo);
		
		// Hacemos que sea visible el dialogo
		dialogNivel.setVisible(true); 
		// Hasta que el usuario no pulsa cancelar u OK para cerrar no se sigue ejecutando el codigo
		if (dialogNivel.getResult() == true) {
			//Guardamos valores elegidos por usuario en base de datos
			user.setCampo("memoriaTiempoMax", dialogNivel.getTiempoMax());
			user.setCampo("memoriaCantidadNum", dialogNivel.getNivel());
		}
    }
    
    /**
     * Manejador del evento cuando se hace clic en el boton recomenzar
     * Se inicia el juego
     * @param evt
     */
    protected void comenzarButtonActionPerformed(ActionEvent evt) {
        iniciarJuego();
    }
    
    /**
     * Manejador del evento cuando se hace clic en el boton parar
     * Se para el juego
     * @param evt
     */
    protected void pararButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pararJuego();
    }
    
    /**
     * Manejador del evento cuando se ahce click sobre las etiquetas
     * @param evt
     */
    protected void labelsClickedPerformed(MouseEvent evt) {
    	// Solo hacemos caso a clicks sobre las labels si no estamos mostrando los numeros
    	if (jugar) {
	    	i = 0;
	    	// Calculamos el índice con que se corresponde el Label seleccionado
	    	while (label[i] != evt.getSource() ) {
	        	i++;
	    	}
	    	switch (juego.checkVector(i)) {
	    	case 0:
	    		// Actualizamos el campo de los puntos
				puntosLabel.setText("Puntos: " + juego.getPuntos());
	    		vidas--;
	    		relojLabel.setText("Vidas: " + vidas);
	    		puntosLabel.setForeground(Color.red);
	    		if (vidas==0) {
	    			pararJuego();
	    			break;
	    		}
	            pregLabel.setText("Has fallado vuelve a memorizar");
				dibujarNum();
				break;
	    	case 1:
	    		// Actualizamos el campo de los puntos
				puntosLabel.setText("Puntos: " + juego.getPuntos());
	            pregLabel.setText("¡Correcto!");
				puntosLabel.setForeground(Color.green);
				label[i].setBackground(Color.green);
				label[i].setText(pregunta[i] + "");
				break;
	    	case 2:
	    		// Actualizamos el campo de los puntos
				puntosLabel.setText("Puntos: " + juego.getPuntos());
	            pregLabel.setText("¡Has acabado! Ahí va otra");
	    		pregunta = juego.genVector();
	    		dibujarNum();
	    		break;
			}
    	}
    }
    
    /**
     * Manejador del evento cuando vence el timer
     * Si los segundos que quedan son mayores que cero se reduce en una 
     * unidad los segundos, se actualiza el reloj y si los segundos son 
     * menores que cero se para el reloj 
     */
    protected void timerAction() {
    	timer.restart();
    	if (segundos > 0) {
    		segundos -= 1;
        	updateReloj();
    	}
    	else {
    		borrarNum();
    		timer.stop();
            pregLabel.setText("Haz click en los cuadrados en orden");
            relojLabel.setText("Vidas: " + vidas);
    	}
    }
    
    /**
     * Actualiza la etiqueta que muestra el reloj al valor de los segundos que quedan
     */
    protected void updateReloj() {
    	String min;
    	String seg;
    	
    	// Formatemamos la cadena para que siempre se muestre con dos digitos
    	if (segundos/60 > 9) 
    		min = "" + segundos/60; // Hay que hacer esta "trampa" porque da probrema al convertir el entero a cadena directamente
    	else 
    		min = "0" + segundos/60;
    	
    	if (segundos%60 > 9)
    		seg = ""  + segundos%60;
    	else 
    		seg = "0" + segundos%60;
    	
    	relojLabel.setText(  min + ":" + seg);
    }
    
    /**
     * Para el juego. 
     * Para el reloj y deshabilita los controles del juego
     * Habilita el boton de seleccion de nivel puesto que no
     * estamos permitiendo el cambio de nivel durante el juego
     * Al final se actualiza la puntuacion si el usuario superó
     * al finalizar la puntuación que tenía.
     */
    public void pararJuego() {
    	// Paramos el timer
    	timer.stop();
    	// Deshabilitamos las labels donde hay que hacer clic para responder
    	setEnabledLabels(false);
    	
    	//Retornamos valores de los botones a los de por defecto
    	nivelButton.setEnabled(true);
    	comenzarButton.setEnabled(true);
    	pararButton.setEnabled(false);
    	
    	//Borramos números de la cuadrícula
    	borrarNum();
    	
    	relojLabel.setText("00:00");
        pregLabel.setText("Pulse el boton comenzar para jugar");
        setPuntos();
    }
    
    /**
     * Inicia el juego
     * Reinicia los puntos, pone en funcinamiento el reloj,
     * habilita el campo de respuesta y deshabilita la posibilidad 
     * de cambiar de nivel durante el juego
     */
    public void iniciarJuego() {
    	// Solo permitimos iniciar el juego de memoria si se ha superado
    	// cierta puntuacion en el juego de calculo mental
    	if ((Long)user.getCampo("calculoPtos") > MemoriaPanel.minPtos) {
    		juego.reset();
    		vidas = 3;
    		relojLabel.setText("Vidas: " + vidas);

    		//Cargamos valores establecidos por el usuario de la base de datos

    		Object campo = user.getCampo("memoriaCantidadNum");
    		String valor = String.valueOf(campo);
    		int num = Integer.parseInt(valor);

    		juego.setNivel(num);

    		campo = user.getCampo("memoriaTiempoMax");
    		valor = String.valueOf(campo);
    		num = Integer.parseInt(valor);

    		juego.setTiempoMax(num);

    		pregunta = juego.genVector();
    		puntosLabel.setText("Puntos: 0");
    		nivelButton.setEnabled(false);
    		comenzarButton.setEnabled(false);
    		pararButton.setEnabled(true);
    		puntosLabel.setForeground(Color.black);
    		pregLabel.setText("Memoriza la secuencia");
    		dibujarNum();
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "Lo sentimos. Para poder jugar a este juego\n" +
    											"tienes que conseguir al menos " + MemoriaPanel.minPtos + " puntos\nen el juego de Calculo mental" );
    	}
    }
    /**
     * Dibujamos números en sus posiciones para que sea memorizada la secuencia.
     */
    private void dibujarNum() {
    	//Iniciamos contador para que desaparezca la secuencia
    	segundos = juego.getTiempoMax();
        updateReloj();
        timer.start();
        
       	setEnabledLabels(false);
    	for (i=0; i < label.length; i++) {
    		if (pregunta[i] != 0)
            	label[i].setText(pregunta[i] + "");
    		else
    			label[i].setText("");
    		label[i].setBackground(Color.blue);
    		label[i].setForeground(Color.white);
    		label[i].setFont(new Font("Arial",Font.BOLD,30));
    	}
    }
    
    /**
     * Borramos números de la cuadrícula
     */
    private void borrarNum() {
		setEnabledLabels(true);
    	for (i=0; i < label.length; i++) {
            	label[i].setText("");
            	label[i].setBackground(Color.blue);
    	}
    }
    
    /**
     * Habilitamos las etiquetas
     * @param bEnabled
     */
    private void setEnabledLabels(boolean bEnabled) {
    	jugar = bEnabled;
    }
    
    /**
     * Asocia un usuario con el panel de juego
     * @param UnUser usuario actualmente autenticado
     */
    public void setUser(User UnUser) {
    	user = UnUser;
    	userLabel.setText("Usuario: " + (String)user.getCampo("nombre"));
    }
    
    /**
     * Actualiza la puntuación del usuario si ésta supera a la que tenía
     */
    private void setPuntos() {
    	Long num1 = (Long)user.getCampo("memoriaPtos");
		long num2 = Long.parseLong(juego.getPuntos());
		if (num1 < num2){
			user.setCampo("memoriaPtos", num2);
			JOptionPane.showMessageDialog(this, "Has mejorado tu récord de puntos: " + num2);
		}
		else {
			JOptionPane.showMessageDialog(this, "Has finalizado con: " + num2 + " puntos");
		}
    }
    
    /** Panel inferior donde están situados los botones comenzarButton, pararButton, nivelButton */
    private JPanel bottomPanel;
    
    /** Botón comenzar el juego */
    private JButton comenzarButton;
    
    /** Label que contiene la imagen representativa del juego */
    private JLabel iconLabel;
    
    /** Panel situado a la izquierda que contiene a iconLabel */
    private JPanel iconoPanel;
    
    /** Panel donde están contenidos todos los controles relacionados con el juego */
    private JPanel mainPanel;
    
    /** Botón para configurar el nivel */
    private JButton nivelButton;
    
    /** Botón para parar el juego */
    private JButton pararButton;
    
    /** Label donde se muestra información para el usuario*/
    private JLabel pregLabel;
    
    /** Label que muestra lo puntos acumulados */
    private JLabel puntosLabel;
    
    /** Label que muestra el tiempo restante */
    private JLabel relojLabel;
    
    /** Label que muestra el nombre del usuario autenticado */
    private JLabel userLabel;
    
    /** Panel situado en la parte superior que contiene a userLabel */
    private JPanel userPanel;
    
    /** Vector de etiquetas donde irán las distintas posiciones de los números de la secuencia*/
    private JLabel[] label;
    
    /** Enteros utilizados para finalidades varias dentro del código*/
    private int i, f, c;
    
    /** Vidas que posee el usuario, si llegan a 0 se acaba la partida*/
    private int vidas;
  
    /** Objeto que se encarga de lo relacionado con el desarrollo del juego de memoria */
	private Memorion	juego; 
	
	/** Dialogo para configurar el nivel del juego */
	private NivelMemoria	dialogNivel; 
	
	/** Timer para controlar el tiempo */
	private Timer	timer;
	
	/** Segundos que quedan para dejar de mostrar la secuencia. Es imprescindible actualizar la etiqueta relojLabel
	 * después de cambiar el contenido de este miembro */
	private	int		segundos; 
	
	/** Secuencia de números a memorizar*/
	private int[]	pregunta;
	
	/** Indica si se puede jugar o no al juego*/
	private boolean	jugar;
	
	/** Usuario actualmente autenticado */
	private User user;
	
	/** El minimo de puntos necesario para poder jugar en este juego*/
	public static int minPtos = 30000;
}

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/** Clase CalculoPanel representa un panel con todas funcionalidades 
 * de un juego de cálculo mental
 * @author Daniel Gómez Frontela
 * @author Luis Fernando Nicolás Alonso
 */
public class CalculoPanel extends JPanel {

    /** Crea un nuevo panel CalculoPanel*/
    public CalculoPanel() {
    	// Dejamos user como null
    	user = null;
    	
    	// Creamos los componentes
    	juego = new CalculoMental();
        iconoPanel = new JPanel();
        iconLabel = new JLabel();
        mainPanel = new JPanel();
        respuestaField = new JTextField();
        pregestatLabel = new JLabel();
        pregLabel = new JLabel();
        puntosLabel = new JLabel();
        relojLabel = new JLabel();
        userPanel = new JPanel();
        nivelButton = new JButton();
        comenzarButton = new JButton();
        pararButton = new javax.swing.JButton();
        bottomPanel = new JPanel();
        userLabel = new JLabel();
        
        // Layout
        setLayout(null);
        
        // Sin layout. La imagen creada tiene que ser exactamente del mismo tamaño
        // para que no sea cortada al mostrarla
        iconoPanel.setLayout(null);

        try {
        	iconLabel.setIcon(new ImageIcon(getClass().getResource("/calculomental.gif")));
        }
        catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "No está disponible la imagen calculomental.gif\n" +
        			"Algunas de la imágenes incluidas no se mostrarán");
        }
        iconoPanel.add(iconLabel);
        iconLabel.setBounds(0, 0, 190, 490);

        add(iconoPanel);
        iconoPanel.setBounds(5, 5, 190, 490);

        mainPanel.setLayout(null);

        mainPanel.add(respuestaField);
        respuestaField.setBounds(230, 120, 210, 20);

        pregestatLabel.setText("Respuesta:");
        mainPanel.add(pregestatLabel);
        pregestatLabel.setBounds(70, 120, 100, 20);

        mainPanel.add(pregLabel);
        pregLabel.setBounds(70, 60, 370, 20);

        mainPanel.add(puntosLabel);
        puntosLabel.setBounds(70, 200, 370, 20);

        mainPanel.add(relojLabel);
        relojLabel.setBounds(70, 280, 370, 20);

        add(mainPanel);
        mainPanel.setBounds(200, 70, 480, 360);

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
        bottomPanel.setBounds(200, 440, 480, 60);

        userPanel.setLayout(null);

        userPanel.add(userLabel);
        userLabel.setBounds(70, 30, 370, 40);

        add(userPanel);
        userPanel.setBounds(200, 0, 480, 100);
        
        // Damos el contenido que tiene que haber en los componentes por defecto
        userLabel.setText("Usuario: Default user");
        respuestaField.setText("Su respuesta...");
        // de principio el usuario no puede jugar tiene que dar al boton comenzar
        respuestaField.setEnabled(false);
    	nivelButton.setEnabled(true);
    	comenzarButton.setEnabled(true);
    	pararButton.setEnabled(false);
        pregLabel.setText("Pulse el boton comenzar para empezar a jugar");
        puntosLabel.setText("Puntos: 0");
        relojLabel.setText("00:00");
        
        // Hacemos la fuente más grande porque queda un poco pequeno
		comenzarButton.setFont(new Font("Arial",Font.BOLD,17));
		iconLabel.setFont(new Font("Arial",Font.BOLD,17));
		nivelButton.setFont(new Font("Arial",Font.BOLD,17));
		pararButton.setFont(new Font("Arial",Font.BOLD,17));
		pregLabel.setFont(new Font("Arial",Font.BOLD,17));
		pregestatLabel.setFont(new Font("Arial",Font.BOLD,17));
		puntosLabel.setFont(new Font("Arial",Font.BOLD,17));
		relojLabel.setFont(new Font("Arial",Font.BOLD,17));
		respuestaField.setFont(new Font("Arial",Font.BOLD,17));
		userLabel.setFont(new Font("Arial",Font.BOLD,30));
       
        // Anadimos los eventos
		respuestaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	respuestaFieldActionPerformed(evt);
            }
        });
        
		respuestaField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evento) {
                respuestaFieldFocusGained(evento);
            }
        });
        

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
     * Manejador del evento cuando el campo de texto para respuesta
     * gana el foco 
     */
    protected void respuestaFieldFocusGained(FocusEvent evento) {
    	respuestaField.setText("");
    }
    
    /**
     * Manejador del evento cuando el usurario pulsa una tecla con el foco en el campo de respuestas
     * Se corrige la respuesta, se suman los puntos y se genera otra cuenta
     * @param evt
     */
    protected void respuestaFieldActionPerformed(ActionEvent evt) {
		try {
				String res = new String(respuestaField.getText());
				int resInt = Integer.parseInt(res.trim());
				// Cambiamos el color de los puntos para que el usuario
				// sepa si ha fallado la pregunta anterior o no sin tener que
				// saber si los puntos han bajado o no
				if (juego.checkCuenta(resInt, segundos)) {
					puntosLabel.setForeground(Color.green);
				}
				else {
					puntosLabel.setForeground(Color.red);
				}
				
				// Actualizamos el campo de los puntos
				puntosLabel.setText("Puntos: " + juego.getPuntos());
				// Generamos una nueva cuenta
				pregLabel.setText(juego.genCuenta());
				// Reiniciamos el contenido de la caja de respuesta
				respuestaField.setText("");
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "La respuesta debe ser un numero entero");
		}
    }
           
    /**
     * Manedor del evento clic sobre el boton nivel
     * Se mustra un dialogo para modificar el nivel
     * @param evento
     */
    protected void nivelButtonActionPerformed(ActionEvent evento) {
		Window ventanaPadre = SwingUtilities.getWindowAncestor(
                (Component) evento.getSource());
		if (dialogNivel == null) {
            if (ventanaPadre instanceof JFrame)
            {
                dialogNivel = new NivelCalculo((JFrame) ventanaPadre);
                // Hacemos que el usuario no pueda cambiar el tamaño del dialogo para que no se
        		// descoloque el contenido y que el tamano sea el suficiente para que se vean todos
        		// los controles
                dialogNivel.setResizable(false);
            }
            else  {
            	JOptionPane.showMessageDialog(this, "CalculoPanel.java: Error en la creacion del dialogo");
            	System.exit(0);
            }
		}
		
		// Centramos el dialogo dentro del frame pricipal
		Rectangle rc = ventanaPadre.getBounds();
        dialogNivel.setBounds(rc.x + (BrainTraining.anchura - NivelCalculo.anchura) / 2 ,
        		rc.y + (BrainTraining.altura - NivelCalculo.altura) / 2,
        		NivelCalculo.anchura, NivelCalculo.altura);
        
        // Obtenemos los valores de los datos del usuario de la base de datos
        Object campo = user.getCampo("calculoNumOper");
		dialogNivel.setNumOper(campo);
		campo = user.getCampo("calculoNivel");
		dialogNivel.setNivel(campo);
		campo = user.getCampo("calculoTiempoMax");
		dialogNivel.setTiempoMax(campo);
		
		dialogNivel.setVisible(true);
		if (dialogNivel.getResult() == true) {
			// Si el dialogo se ha aceptado guardamos valores elegidos por usuario en base de datos
			user.setCampo("calculoTiempoMax", dialogNivel.getTiempoMax());
			user.setCampo("calculoNumOper", dialogNivel.getNumOper());
			user.setCampo("calculoNivel", dialogNivel.getNivel());		
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
    		pararJuego();
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
    	// Paramos el contador
    	timer.stop();
    	// Deshabilitamos el componente donde se introducen las respuestas
    	respuestaField.setEnabled(false);
    	
    	respuestaField.setText("Su respuesta...");
    	
    	// Habilitamos la seleccion de niveles
    	nivelButton.setEnabled(true);
    	comenzarButton.setEnabled(true);
    	pararButton.setEnabled(false);
    	setPuntos();
    }
    
    /**
     * Inicia el juego
     * Reinicia los puntos, pone en funcinamiento el reloj,
     * habilita el campo de respuesta y deshabilita la posibilidad 
     * de cambiar de nivel durante el juego
     */
    public void iniciarJuego() {
    	juego.reset();
        timer.start();
        
        //Cargamos valores establecidos por el usuario de la base de datos
        Object campo = user.getCampo("calculoNivel");
        String valor = String.valueOf(campo);
		int num1 = Integer.parseInt(valor);
		
        campo = user.getCampo("calculoNumOper");
        valor = String.valueOf(campo);
		int num2 = Integer.parseInt(valor);
        
		// Actualizamos el nivel en el juego de acuerdo al perfil del usuario
        juego.setNivel(num1, num2);
        
        campo = user.getCampo("calculoTiempoMax");
        valor = String.valueOf(campo);
		num1 = Integer.parseInt(valor);
        
		// Actualizamos el tiempo de máximo de juego con el del perfil del usuario
		juego.setTiempoMax(num1);
		
        pregLabel.setText(juego.genCuenta());
        puntosLabel.setText("Puntos: 0");
        respuestaField.setEnabled(true);
        segundos = juego.getTiempoMax();
        updateReloj();
        nivelButton.setEnabled(false);
        comenzarButton.setEnabled(false);
    	pararButton.setEnabled(true);
    	puntosLabel.setForeground(Color.black);
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
    	Long num1 = (Long)user.getCampo("calculoPtos");
		long num2 = Long.parseLong(juego.getPuntos());
		if (num1 < num2){
			user.setCampo("calculoPtos", num2);
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
    /** Botón para configurar el nivel */
    private JButton nivelButton;
    /** Botón para parar el juego */
    private JButton pararButton;
    
    /** Panel situado a la izquierda que contiene a iconLabel */
    private JPanel iconoPanel;
    /** Label que contiene la imagen representativa del juego */
    private JLabel iconLabel;
   
    /** Panel donde están contenidos todos los controles relacionados con el juego */
    private JPanel mainPanel;
    /** Label donde se muestra la cuenta que el usuario tiene que resolver */
    private JLabel pregLabel;
    /** Label estática que indica donde el usuario debe escribir la respuesta */
    private JLabel pregestatLabel;
    /** Label que muestra lo puntos acumulados */
    private JLabel puntosLabel;
    /** Label que muestra el tiempo restante */
    private JLabel relojLabel;
    /** Campo de texto donde el usuario introduce la respuesta */
    private JTextField respuestaField;
    
    /** Panel situado en la parte superior que contiene a userLabel */
    private JPanel userPanel;
    /** Label que muestra el nombre del usuario autenticado */
    private JLabel userLabel;
    
    /** Objeto que se encarga de lo relacionado con el desarrollo del juego calculo mental */
	private CalculoMental juego;

	/** Dialogo para configurar el nivel del juego */
	private NivelCalculo dialogNivel;
	
	/** Timer para controlar el tiempo */
	private Timer timer;
	
	/** Segundos que quedan para que el juego acabe. Es imprescindible actualizar la etiqueta relojLabel
	 * después de cambiar el contenido de este miembre */
	private	int	segundos;
	
	/** Usuario actualmente autenticado */
	private User user;
}

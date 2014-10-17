import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/** Clase MasterPanel representa un panel con todas funcionalidades 
 * de un juego MasterMind
 * @author Daniel Gómez Frontela
 * @author Luis Fernando Nicolás Alonso
 */

public class MasterPanel extends JLayeredPane{
	
	/** Crea un nuevo panel MasterPanel*/
	public MasterPanel() {
		//Creamos panel de relleno debido a problemas que hemos tenido con panel heredado de JLayeredPane
		JPanel rellenoPanel = new JPanel();
		rellenoPanel.setBounds(1, 1, 700, 600);
		
		//Lo añadimos como la capa más inferior, la menos visible
		add(rellenoPanel, JLayeredPane.DEFAULT_LAYER);
		
		// Dejamos user como null
		user = null;
		
		//Guardamos MasterPanel(this) en una variable para poder trabajar con ello
		panel = this;
		
		// Creacion de los componentes
		juego = new MasterMind();
        iconoPanel = new JPanel();
        iconLabel = new JLabel();
        opcionesPanel = new JPanel();
        checkButton = new JButton();
		
        //Matriz de etiquetas
		intentos = juego.getIntentosMax();
        intentoLabel = new JLabel[intentos][4];
        for(int i = 0; i < intentoLabel.length; i++) {
        	for (int j=0;j<intentoLabel[i].length;j++) {
        		intentoLabel[i][j] = new JLabel();
        	}   
        }
        
        //Vector de etiquetas
        resuLabel = new JLabel[intentos];        
        for(int i = 0; i < resuLabel.length; i++) {
            resuLabel[i] = new JLabel();
        }
        
        //Vector de paneles
        intentoPanel = new JPanel[intentos];
        for(int i=0; i< intentoPanel.length; i++) {
        	intentoPanel[i]= new JPanel();
        }
        
        //Vector de etiquetas
        opcionesLabel = new JLabel[9];
        
        // Creacion del portapapeles
        porta = 0;
        portaLabel = new JLabel();
        portaLabel.setVisible(false);
        portaLabel.setOpaque(true);
        portaLabel.setBackground(Color.red);
        portaLabel.setBounds(0, 0, 30,30);
        portaLabel.setHorizontalAlignment(JLabel.CENTER);
        add(portaLabel,JLayeredPane.DRAG_LAYER);
        
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
        tableroPanel = new JPanel();
        
        // Layout
        setLayout(null);

        // Sin layout. La imagen creada tiene que ser exactamente del mismo tamaño
        // para que no sea cortada al mostrarla
        iconoPanel.setLayout(null);
        
        try {
        	iconLabel.setIcon(new ImageIcon(getClass().getResource("mastermind1.jpg")));
        }
        catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "No está disponible la imagen mastermind1.jpg\n" +
        			"Algunas de la imágenes incluidas no se mostrarán");
        }
        iconoPanel.add(iconLabel);
        iconLabel.setBounds(5, 5, 190, 490);

        add(iconoPanel, JLayeredPane.PALETTE_LAYER);
        iconoPanel.setBounds(0, 0, 180, 490);

        mainPanel.setLayout(null);

        mainPanel.add(pregLabel, JLayeredPane.PALETTE_LAYER);
        pregLabel.setBounds(70, 20, 370, 20);
        
        tableroPanel.setLayout(null);
        tableroPanel.setBounds(270, 120, 420, 300);
        
        //Dibujamos cuadrículas donde irán intentos realizados y aciertos
        int i = 0;
        for (int f=0; f<(intentos+1)/2; f++) {
        	for (int c=0; c<=1 && i<intentoLabel.length; c++) {
        		tableroPanel.add(intentoPanel[i]);
        		tableroPanel.setBackground(Color.red);
        		tableroPanel.setOpaque(false);
        		intentoPanel[i].setLayout(null);
            	intentoPanel[i].setBounds(210*c, 50*f, 210, 50);
            	for (int j=0;j<intentoLabel[i].length;j++) {
	            	intentoPanel[i].add(intentoLabel[i][j]);
	            	intentoLabel[i][j].setBounds(j*30, 0, 25, 30);
	            	resuLabel[i].setBounds(130, 0, 60, 30);
	            	
	            	intentoLabel[i][j].setHorizontalAlignment(JLabel.CENTER);
	                intentoLabel[i][j].setFont(new Font("Arial",Font.BOLD,17));
	                intentoLabel[i][j].setBackground(Color.blue);
	                intentoLabel[i][j].setForeground(Color.white);
	                intentoLabel[i][j].setOpaque(true);
	                intentoLabel[i][j].addMouseListener(new MouseAdapter() {
	                	public void mouseClicked(MouseEvent evt) {
	        				JLabel label = (JLabel)evt.getSource();
	        				if (label.isEnabled()) {
	        					if (porta != 0) {
	        						//Si el portapapeles tiene contenido, lo pasamos a la etiqueta clickeada
		        					if (label.getText().equals("")) {
		        						label.setBackground(Color.red);
			        					label.setText(porta + "");
			        					porta = 0;
			        					portaLabel.setVisible(false);
		        					}
		        					else {
		        						//Si la etiqueta tenía contenido hay que pasarlo al portapapeles
		        						int temp = porta;
		        						porta = Integer.valueOf(label.getText()).intValue();
		        						portaLabel.setText(porta + "");
		        						label.setText(temp + "");
		        					}
	        					}
	        					else {
	        						if (!label.getText().equals("")) {
	        							//Si el portapaeles no tiene nada y pulsamos sobre una etiqueta que sí,
	        							//pasamos el contenido de etiqueta al portapapeles
		        						label.setBackground(Color.green);
			        					porta = Integer.valueOf(label.getText()).intValue();
			        					label.setText("");
		        						portaLabel.setText(porta + "");
		        						portaLabel.setVisible(true);
	        						}
	        					}	        						
	        				}
	                	}
	                });
            	}
            	intentoPanel[i].add(resuLabel[i]);
                resuLabel[i].setBackground(Color.blue);
                resuLabel[i].setOpaque(true);
                i++;
        	}
        }
        tableroPanel.add(opcionesPanel);
        
        // Dibujo de opcionesPanel y su contenido
        opcionesPanel.setBounds(5, 250, 35*opcionesLabel.length+10, 40);
        opcionesPanel.setBackground(Color.cyan);
        opcionesPanel.setOpaque(true);
        opcionesPanel.setLayout(null);
        for (int j=0; j<opcionesLabel.length; j++) {
        	opcionesLabel[j] = new OpcionesLabel(porta,j);
    		opcionesLabel[j].setBackground(Color.red);
    		opcionesLabel[j].setOpaque(true);
        	opcionesLabel[j].setBounds(j*35+5, 5, 30, 30);
        	opcionesLabel[j].setText(""+(j+1));
        	opcionesPanel.add(opcionesLabel[j]);
          	opcionesLabel[j].setHorizontalAlignment(JLabel.CENTER);  
          	opcionesLabel[j].addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent evt) {
    				JLabel label = (JLabel)evt.getSource();
    				int conten = Integer.valueOf(label.getText()).intValue();
					if (label.isEnabled()) {
						// Caso en que este habilitada.
						if (porta != 0) {
							// Si en el portapapeles hay algo. Habilitamos la
							// opcion que estaba en él
							opcionesLabel[porta-1].setBackground(Color.red);
							opcionesLabel[porta-1].setEnabled(true);
						}
						// Deshabilitamos la etiqueta sobre la que se ha hecho clic
						label.setBackground(Color.gray);
						label.setEnabled(false);
						porta = conten;
						portaLabel.setVisible(true);
						portaLabel.setText("" + porta);
						// Colocamos el portapapeles
						Point point = panel.getMousePosition();
						portaLabel.setLocation(point);
					}
					else {
						// Caso en que este deshabilitada. Si el portapapeles
						// contiene la misma etiqueta se vuelve a colocar
						if (porta == conten) {
							label.setBackground(Color.red);
							label.setEnabled(true);
							portaLabel.setVisible(false);
							porta = 0;
						}
					}
    			}
    		});
    	}
        
        // Por defecto deshabilitamos las opciones y los intentos
        deshabilitarOpciones();
        deshabilitarIntentos();
        
        //El juego no está acabado por defecto
        acabado = false;
        
        setLayout(null);
        
        add(tableroPanel, JLayeredPane.PALETTE_LAYER);
        
        mainPanel.add(checkButton);
        checkButton.setBounds(260, 360, 100, 30);
        checkButton.setText("Check...");
        checkButton.setEnabled(false);
        mainPanel.add(puntosLabel);
        puntosLabel.setBounds(70, 360, 190, 20);

        mainPanel.add(relojLabel);
        relojLabel.setBounds(70, 400, 100, 20);

        mainPanel.setOpaque(true);
        add(mainPanel, JLayeredPane.PALETTE_LAYER);
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
        
        add(bottomPanel, JLayeredPane.PALETTE_LAYER);
        bottomPanel.setBounds(200, 500, 480, 60);

        userPanel.setLayout(null);

        userPanel.add(userLabel);
        userLabel.setBounds(70, 30, 370, 40);

        add(userPanel, JLayeredPane.PALETTE_LAYER);
        userPanel.setBounds(200, 0, 480, 100);
        
        // Damos el contenido que tiene que haber en los componentes por defecto
        userLabel.setText("Usuario: Default user");
    
        // de principio el usuario no puede jugar tiene que dar al boton comenzar
        nivelButton.setEnabled(true);
    	comenzarButton.setEnabled(true);
    	pararButton.setEnabled(false);
        pregLabel.setText("Pulse el boton comenzar para empezar a jugar");
   
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
		checkButton.setFont(new Font("Arial",Font.BOLD,10));
		userLabel.setFont(new Font("Arial",Font.BOLD,30));
       
        // Añadimos los eventos
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt) {
				if (porta == 0) {
					portaLabel.setVisible(false);
				}
				else {
					portaLabel.setVisible(true);
					portaLabel.setText(porta + "");
					Point point = getMousePosition();
					point.x += 5;
					point.y += 5;
					portaLabel.setLocation(point);
				}
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
        
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
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
                dialogNivel = new NivelMaster((JFrame) ventanaPadre);
                // Hacemos que el usuario no pueda cambiar el tamaño del dialogo para que no se
        		// descoloque el contenido y programos el tamano suficiente para que se vean todos
        		// los controles
                dialogNivel.setResizable(false);
            }
            else  {
            	JOptionPane.showMessageDialog(this, "MasterPanel.java: Error en la creacion del dialogo");
            	System.exit(0);
            }
		}
		
		// Centramos el dialogo
		Rectangle rc = ventanaPadre.getBounds();
        dialogNivel.setBounds(rc.x + (BrainTraining.anchura - NivelMaster.anchura) / 2 ,
        		rc.y + (BrainTraining.altura - NivelMaster.altura) / 2,
        		NivelMaster.anchura, NivelMaster.altura);
        
        // Obtenemos los valores por defecto de los datos del usuario de la base de datos
        Object campo = user.getCampo("masterIntentos");
		dialogNivel.setNumIntentos(campo);
		campo = user.getCampo("masterOpciones");
		dialogNivel.setNumOpciones(campo);
		campo = user.getCampo("masterTiempoMax");
		dialogNivel.setTiempoMax(campo);
		
		// Hacemos que sea visible el dialogo
		dialogNivel.setVisible(true); 
		// Hasta que el usuario no pulsa cancelar u OK para cerrar no se sigue ejecutando el codigo
		if (dialogNivel.getResult() == true) {
			//Guardamos valores elegidos por usuario en base de datos
			user.setCampo("masterTiempoMax", dialogNivel.getTiempoMax());
			user.setCampo("masterIntentos", dialogNivel.getIntentosMax());
			user.setCampo("masterOpciones", dialogNivel.getNivel());
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
    
 // 
   /**
    * Manejador del evento cuando se hace clic en el boton check
    * Se corrige la respuesta si el juego no ha acabado, si ha acabado generamos otro código secreto
    */
    protected void checkButtonActionPerformed(ActionEvent evt) {
    	int i = juego.getIntento();
    	if (acabado) {
    		//Si ha acabado generamos nuevo código a adivinar
    		pregLabel.setText("Intenta adivinar el código");
    		juego.genVector();
        	borrarNum();
        	acabado = false;
        	deshabilitarIntentos();
        	habilitarIntento();
        	inicializarOpciones();
        	checkButton.setText("Check...");
        	timer.start();
    	}
    	else {
	        int[] res = new int[intentoLabel.length];
	        // Comprobamos que la repuesta esta completa
	        for (int j=0; j < intentoLabel[i].length; j++) {
	        	if (intentoLabel[i][j].getText().equals("")) {
	        		JOptionPane.showMessageDialog(this, "Rellena todos los huecos del código antes de comprobar");
	        		return;
	        	}
	        	res[j] = Integer.valueOf(intentoLabel[i][j].getText()).intValue();
	        }
	        //Corregimos la respuesta
	        int[] infor = juego.checkVector(res, segundos);
	        resuLabel[i].setText("<html><p style=\"padding-bottom: 6; padding-left: 12; font-size: 27;\"><FONT COLOR='#00ff00' >" + infor[0] + "&nbsp;" + "</FONT>" + 
	        		"<FONT COLOR=red>" + infor[1] + "</FONT></p></html>");
	        if(infor[0] == intentoLabel[i].length) {
	        	//Si se ha acertado se suman los puntos y pausamos el juego hasta que el usuario desee continuar con un nuevo código
	        	pregLabel.setText("Has acertado el código");
	        	puntosLabel.setText("Puntos: " + juego.getPuntos());
	        	acabado = true;
	        	checkButton.setText("Continuar...");
	        	deshabilitarIntentos();
	        	deshabilitarOpciones();
	        	timer.stop();
	        }
	        else {
	        	if (i == (juego.getIntentosMax()-1)) {
	        		//Si se han acabado los intentos sin acertar el código pausamos el juego hasta que el usuario desee continuar con un nuevo código
	        		pregLabel.setText("Has acabado todos tus intentos");
	        		puntosLabel.setText("Puntos: " + juego.getPuntos());
	            	acabado = true;
	            	checkButton.setText("Continuar...");
	            	deshabilitarIntentos();
	            	deshabilitarOpciones();
	            	timer.stop();
	        	}
	        	else {
	        		//El usuario no ha acertado pero le quedan intentos, el juego sigue
	        		pregLabel.setText("¡Huuuy, casi...! Sigue probando...");
	        		deshabilitarIntentos();
	        		habilitarIntento();
	        		inicializarOpciones();
	        	}
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
    	//Paramos el timer
    	timer.stop();
    	
    	//Retornamos valores de los botones a los de por defecto
    	nivelButton.setEnabled(true);
    	comenzarButton.setEnabled(true);
    	pararButton.setEnabled(false);
    	checkButton.setEnabled(false);
    	borrarNum();
    	relojLabel.setText("00:00");
        pregLabel.setText("Pulse el boton comenzar para empezar a jugar");
        
        //Deshabilitamos opciones e intentos
        deshabilitarIntentos();
        deshabilitarOpciones();
        setPuntos();
    }
    
    /**
     * Inicia el juego
     * Reinicia los puntos, pone en funcinamiento el reloj,
     * habilita el campo de respuesta y deshabilita la posibilidad 
     * de cambiar de nivel durante el juego
     */
    public void iniciarJuego() {
    	if ((Long)user.getCampo("memoriaPtos") > MasterPanel.minPtos) {
    		timer.start();
    		juego.reset();

    		//Cargamos valores establecidos por el usuario de la base de datos

    		Object campo = user.getCampo("masterOpciones");
    		String valor = String.valueOf(campo);
    		int num = Integer.parseInt(valor);

    		juego.setNivel(num);

    		campo = user.getCampo("masterIntentos");
    		valor = String.valueOf(campo);
    		num = Integer.parseInt(valor);

    		juego.setIntentosMax(num);

    		campo = user.getCampo("masterTiempoMax");
    		valor = String.valueOf(campo);
    		num = Integer.parseInt(valor);

    		juego.setTiempoMax(num);

    		segundos = juego.getTiempoMax();
    		updateReloj();
    		
    		//Generamos código a adivinar
    		juego.genVector();
    		puntosLabel.setText("Puntos: 0");
    		
    		//Actualizamos estado de los botones
    		checkButton.setEnabled(true);
    		nivelButton.setEnabled(false);
    		comenzarButton.setEnabled(false);
    		pararButton.setEnabled(true);
    		checkButton.setEnabled(true);
    		pregLabel.setText("Intenta adivinar el código");
    		borrarNum();
    		
    		//Deshabilitaos primero todos los intentos y habilitamos luego el actual
    		deshabilitarIntentos();
    		habilitarIntento();
    		inicializarOpciones();
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "Lo sentimos. Para poder jugar a este juego\n" +
    				"tienes que conseguir al menos " + MasterPanel.minPtos + " puntos\nen el juego de Memoria" );
    	}
    }
    
    /**
     * Borramos los números de los intentos y de los resultados
     */
    private void borrarNum() {
    	for (int i=0; i < intentoLabel.length; i++) {
    		for(int j=0;j<intentoLabel[i].length;j++) {
    			intentoLabel[i][j].setText("");
        		intentoLabel[i][j].setBackground(Color.blue);
    		}
    		
    	}
    	for (int i=0; i < resuLabel.length; i++) {
    		resuLabel[i].setText("");
    		resuLabel[i].setBackground(Color.blue);
    	}
    }
   
    /**
     * Deshabilitamos todos los intentos
     */
    private void deshabilitarIntentos() {
    	for (int i=0; i < intentoLabel.length; i++) {
    		for(int j=0;j<intentoLabel[i].length;j++) {
    			intentoLabel[i][j].setEnabled(false);
    			if (i>=juego.getIntentosMax()) {
    				intentoLabel[i][j].setBackground(Color.gray);
    			}
    		}
    	}
    }
    
    /**
     * Habilitamos la etiqueta del intento actual
     */
    private void habilitarIntento() {
    	// Habilitamos el intento correspondiente
    	int i = juego.getIntento();
    	for(int j=0;j<intentoLabel[i].length;j++) {
			intentoLabel[i][j].setEnabled(true);
			intentoLabel[i][j].setBackground(Color.green);
		}
    }
    
    /**
     *  Inicializamos las opciones, números de los que puede estar formado el código 
     */
    private void inicializarOpciones() {
    	 for (int j=0; j<juego.getNumOpciones(); j++) {
         	opcionesLabel[j].setEnabled(true);
     		opcionesLabel[j].setBackground(Color.red);
    	 }
    }
    
    /**
     * Deshabilitamos las opciones
     */
    private void deshabilitarOpciones() {
    	for (int j=0; j<opcionesLabel.length; j++) {
         	opcionesLabel[j].setEnabled(false);
     		opcionesLabel[j].setBackground(Color.gray);
    	 }
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
    	Long num1 = (Long)user.getCampo("masterPtos");
		long num2 = Long.parseLong(juego.getPuntos());
		if (num1 < num2){
			user.setCampo("masterPtos", num2);
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
    
    /** Matriz de etiquetas donde en cada fila va un intento de averiguar el código*/
    private JLabel[][] intentoLabel; 
    
    /** Vector de etiquetas donde se muestran los aciertos realizados en cada intento*/
    private JLabel[]	resuLabel;
    
    /** Panel donde van las etiquetas de opciones*/
    private JPanel opcionesPanel;
    
    /** Número máximo de intentos*/
    private int intentos;
    
    /** Botón para chequear el códgio introducido por el usuario*/
    private JButton checkButton;
    
    /** Panel donde se colocan los paneles de intentopanel*/
    private JPanel tableroPanel; 
    
    /** Vector de paneles, cada panel guarda una etiqueta de intento y una de resultado*/
    private JPanel[] intentoPanel;
  
    /** Objeto que se encarga de lo relacionado con el desarrollo del juego MasterMind */
	private MasterMind	juego;
	
	/** Dialogo para configurar el nivel del juego */
	private NivelMaster	dialogNivel; 
	
	/** Timer para controlar el tiempo */
	private Timer	timer;
	
	/** Segundos que quedan para que el juego acabe. Es imprescindible actualizar la etiqueta relojLabel
	 * después de cambiar el contenido de este miembro */
	private	int		segundos; 
	
	/** Vector de etiquetas donde van los números de los que está formado el código*/
	private JLabel[] 	opcionesLabel;
	
	/** Contenido del portapapeles*/
	private int 			porta;
	
	/** Etiqueta del portapapeles*/
	private JLabel			portaLabel;
	
	/** Indica si hemos acabado o no el juego*/
	private boolean			acabado;
	 
	/** Panel actual */
	private MasterPanel			panel; // Guardar el puntero this en una variable miembro
	// nos va a permitir acceder a las funciones de MasterPanel
	// en lugares donde con el puntero this solo no se permite
	/** Usuario actualmente autenticado */
	private User user;
	
	/** El minimo de puntos necesario para poder jugar en este juego*/
	public static int minPtos = 500; // El minimo de puntos necesario para poder jugar en este juego
}



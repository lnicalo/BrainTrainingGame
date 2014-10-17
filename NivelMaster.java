
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
/**
 * Diálogo con el que se eligen los niveles y distintos parámetros del juego MasterMind
 * @author Daniel Gómez Frontela
 * @author Luis Fernando Nicolás Alonso
 */

public class NivelMaster extends JDialog implements ActionListener{
	/**
	 * Crea un nuevo diálogo
	 * @param propietario frame padre
	 */
	public NivelMaster(JFrame propietario) {
		super(propietario, "Seleccion de niveles", true);
        
		// Creamos los controles del diálogo
		mainPanel = new JPanel();
        diffNumPanel = new JPanel();
        diffNum = new JSlider(5,9);
        tiempoMaxPanel = new JPanel();
        tiempoMax = new JSpinner();
        tiempoMaxLabel = new JLabel();
        tiemposPanel = new JPanel();
        bottomPanel = new JPanel();
        cancel = new JButton();
        ok = new JButton();
        listaTiempos = new String[] {"Intensivo", "Medio", "Resistencia"};
        tiempos = new JList(listaTiempos);
        
        // Si se hace clic en X se cierra la ventana
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Layout
        getContentPane().setLayout(null);

        // Colocamos los componentes en el layout e
        // iniciamos sus propiedades
        mainPanel.setLayout(null);

        diffNumPanel.setLayout(null);
        diffNumPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
		"Cantidad de números del código:"));
        diffNum.setMajorTickSpacing(2);
        diffNum.setMaximum(9);
        diffNum.setMinorTickSpacing(1);
        diffNum.setPaintLabels(true);
        diffNum.setPaintTicks(true);
        diffNumPanel.add(diffNum);
        diffNum.setBounds(20, 25, 200, 45);

        mainPanel.add(diffNumPanel);
        diffNumPanel.setBounds(10, 20, 240, 90);
        
        tiemposPanel.setLayout(null);
        tiemposPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
        "Tiempo máximo de juego:"));
        mainPanel.add(tiemposPanel);
        tiemposPanel.setBounds(250, 20, 200, 90);
       
        tiemposPanel.add(tiempos);
        tiempos.setBounds(20, 20, 150, 60);
        
        tiempoMaxPanel.setLayout(null);
        tiempoMaxPanel.add(tiempoMax);
        tiempoMax.setBounds(160, 20, 50, 18);
        tiempoMax.setModel(new SpinnerNumberModel(8, 4, 10, 1));
        
        tiempoMaxLabel.setText("Número máximo de intentos");
        tiempoMaxPanel.add(tiempoMaxLabel);
        tiempoMaxLabel.setBounds(0, 20, 200, 20);

        mainPanel.add(tiempoMaxPanel);
        tiempoMaxPanel.setBounds(130, 110, 220, 60);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 470, 190);

        bottomPanel.setLayout(null);

        cancel.setText("Cancel");
        bottomPanel.add(cancel);
        cancel.setBounds(250, 10, 73, 23);

        ok.setText("OK");
        bottomPanel.add(ok);
        ok.setBounds(160, 10, 73, 23);

        getContentPane().add(bottomPanel);
        bottomPanel.setBounds(0, 210, 470, 40);

        pack();
        
        // Hacemos el botón ok predeterminado
        getRootPane().setDefaultButton(ok);
        
        // Tooltips
        tiempoMax.setToolTipText("Número máximo de intentos");
        diffNum.setToolTipText("Nivel en los números elegidos");

        // Anadimos eventos
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                okActionPerformed(evento);
            }
        });
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evento) {
                cancelActionPerformed(evento);
            }
        });
	}

	// Manejadores de eventos
	/**
	 * Maneja el evento clic sobre el botón ok
	 * @param evento
	 */
    protected void okActionPerformed(java.awt.event.ActionEvent evento) {
    	setVisible(false);
		estado = true;
    }
    
    /**
     * Maneja el evento clic sobre el botón cancel
     * @param evento
     */
    protected void cancelActionPerformed(java.awt.event.ActionEvent evento) {
    	setVisible(false);
		estado = false;
    }		
	
    /** 
	 * Devuelve si se ha pulsado o no el botón ok
	 * */
	public boolean getResult() {
		return estado;
	}
	
	/**
	 * Maneja eventos de acción
	 * @param evento
	 */
	public void actionPerformed(ActionEvent evento) {
		Object origen = evento.getSource();
		
		// Cuando el usuario pulsa OK hacemos invisible el dialog y marcamos la variable estado como
		// true para que se sepa que se acepto el dialogo
		if ( origen == ok) {
			setVisible(false);
			estado = true;
		}
		else if (origen == cancel) {
			setVisible(false);
			estado = false;
		}
				
	}
	
	/**
	 * Devuelve el nivel (cantidad de números del código) seleccionado
	 * @return nivel
	 */
	public int getNivel() {
		return diffNum.getValue();
	}
	
	/**
	 * Devuelve el tiempo máximo de juego seleccionado
	 * @return tiempo máximo
	 */
	public int getTiempoMax() {
		int i;
		// Buscamos el número del item seleccionado
		for (i=0; i < listaTiempos.length; i++) {
			if (((String)tiempos.getSelectedValue()).equals(listaTiempos[i])) {
				break;
			}
		}
		
		switch (i) {
		case 0:
			// Caso intensivo
			return 300;
		case 1:
			// Caso intermedio
			return 600;
		case 2:
			// Caso resistencia
			return 900;
		default:
			return 600;	
		} 
	}
	
	/**
	 * Devuelve los intentos máximos seleccionados
	 * @return intentos máximos
	 */
	public int getIntentosMax() {
		return (Integer)tiempoMax.getValue();
	}
	
	/**
	 * Establece el tiempo máximo de juego pasado
	 * @param unTiempoMax
	 */
	public void setTiempoMax (Object unTiempoMax)
	{
		String valor = String.valueOf(unTiempoMax);
		int num = Integer.parseInt(valor);
		int i;
		switch(num) {
		case 300:
			i = 0;
			break;
		case 600:
			i = 1;
			break;
		case 900:
			i = 2;
			break;
		default:
			i = 0;
			break;
		}
		tiempos.setSelectedIndex(i);
	}
	
	/**
	 * Establece el nivel (cantidad de números del código) pasado
	 * @param numOpciones
	 */
	public void setNumOpciones (Object numOpciones)
	{
		String valor = String.valueOf(numOpciones);
		int num = Integer.parseInt(valor);
		
		diffNum.setValue(num);
	}
	
	/**
	 * Establece el número de intentos pasado
	 * @param numIntentos
	 */
	public void setNumIntentos (Object numIntentos)
	{
		String valor = String.valueOf(numIntentos);
		int num = Integer.parseInt(valor);
		tiempoMax.setValue(num);
	}
	
	/** Anchura del cuadro de diálogo */
	public static final int anchura = 500;
	
	/** Altura del cuadro de diálogo */
	public static final int altura = 300;
	
	/** Indica si hemos pulsado o no el botón OK */
	private boolean			estado;
	
	/** Panel que contiene botones ok y cancel*/
	private JPanel bottomPanel;
	
	/** Botón de cancelar */
    private JButton cancel;
    
    /** Slider para elegir cantidad de números del código */
    private JSlider diffNum;
    
    /** Panel que contiene slider de cantidad de números*/
    private JPanel diffNumPanel;
    
    /** Etiqueta que acompaña spinner de número máximo de intentos */
    private JLabel tiempoMaxLabel;
    
    /** Panel principal */
    private JPanel mainPanel;
    
    /** Botón de aceptar*/
    private JButton ok;
    
    /** Spinner para elegir número máximo de intentos*/
    private JSpinner tiempoMax;
    
    /** Panel que contiene etiqueta y spinner de número máximo de intentos*/
    private JPanel tiempoMaxPanel;
    
    /** Lista para elegir tiempo máximo de juego*/
    private JList tiempos;
    
    /** Vector de cadenas de caracteres que guarda los niveles del tiempo máximo*/
    private String[] listaTiempos;
    
    /** Panel que contiene etiqueta y lista de tiempo máximo de juego*/
    private JPanel tiemposPanel;
}

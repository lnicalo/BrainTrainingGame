import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.WindowConstants;
/**
 * Di�logo con el que se eligen los niveles y distintos par�metros del juego de c�lculo mental
 * @author Daniel G�mez Frontela
 * @author Luis Fernando Nicol�s Alonso
 */

public class NivelCalculo extends JDialog implements ActionListener{
	
	/** Anchura del cuadro de di�logo */
	public static final int anchura = 550;
	
	/** Altura del cuadro de di�logo */
	public static final int altura = 250;

	/** Indica si hemos pulsado o no el bot�n OK */
	private boolean			estado;
	
	/** Grupo de radiobotones para elegir dificultad de operaciones */
	private ButtonGroup 	grupo;
	
	/** Bot�n de cancelar */
	private JButton 		cancel;
	
	/** Slider para elegir dificultad de los n�meros */
	private JSlider 		diffNum;
	
	/** Etiqueta que precede slider de dificultad de los n�meros */
	private JLabel 			diffNumLabel;
	
	/** Radiobot�n con la opci�n dif�cil */
	private JRadioButton 	dificil;
	
	/** Radiobot�n con la opci�n f�cil */
	private JRadioButton 	facil;
	
	/** Radiobot�n con la opci�n medio */
	private JRadioButton 	medio;
	
	/** Radiobot�n con la opci�n muy dif�cil */
	private JRadioButton 	muyDificil;
	
	/** Etiqueta que precede radiobotones de n�mero de operaciones */
	private JLabel 			numOperLabel;
	
	/** Bot�n de aceptar*/
	private JButton 		ok;
	
	/** Spinner para elegir tiempo m�ximo de juego*/
	private JSpinner 		tiempoMax;
	
	/** Etiqueta que precede spinner de tiempo m�ximo */
	private JLabel 			tiempoMaxLabel;
	
	/** Vector de cadenas de caracteres que guarda los niveles del tiempo m�ximo*/
	private String[]		niveles;
	
	/**
	 * Crea un nuevo di�logo
	 * @param propietario frame padre
	 */
	
	public NivelCalculo(JFrame propietario) {
		super(propietario, "Seleccion de niveles", true);
		GridBagConstraints gridBagConstraints;
		
		// Creamos los controles del di�logo
		niveles = new String[] {"Intensivo", "Medio", "Resistencia"};
        cancel = new JButton();
        ok = new JButton();
        tiempoMax = new JSpinner();
        tiempoMaxLabel = new JLabel();
        diffNum = new JSlider();
        muyDificil = new JRadioButton();
        facil = new JRadioButton();
        medio = new JRadioButton();
        dificil = new JRadioButton();
        numOperLabel = new JLabel();
        diffNumLabel = new JLabel();

        // Si se hace clic en X se cierra la ventana
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Layout
        getContentPane().setLayout(new GridBagLayout());

        // Colocamos los componentes en el GridBagLayout e
        // iniciamos sus propiedades
        cancel.setText("Cancel");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(20, 0, 20, 0);
        getContentPane().add(cancel, gridBagConstraints);

        ok.setText("OK");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.insets = new Insets(20, 0, 20, 0);
        getContentPane().add(ok, gridBagConstraints);

        tiempoMax.setModel(new SpinnerListModel(niveles));
        tiempoMax.setToolTipText("Tiempo maximo de juego");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.insets = new Insets(0, 0, 0, 20);
        getContentPane().add(tiempoMax, gridBagConstraints);

        tiempoMaxLabel.setText("Tiempo m�ximo");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        getContentPane().add(tiempoMaxLabel, gridBagConstraints);

        diffNum.setMajorTickSpacing(10);
        diffNum.setMaximum(50);
        diffNum.setMinimum(5);
        diffNum.setMinorTickSpacing(1);
        diffNum.setPaintLabels(true);
        diffNum.setPaintTicks(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.insets = new Insets(0, 0, 0, 20);
        getContentPane().add(diffNum, gridBagConstraints);
        
        grupo = new ButtonGroup();
        muyDificil.setText("Muy dif�cil (4 operaciones)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 12, 0, 0);
        grupo.add(muyDificil);
        getContentPane().add(muyDificil, gridBagConstraints);

        facil.setText("F�cil (1 operaci�n)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 12, 0, 0);
        grupo.add(facil);
        getContentPane().add(facil, gridBagConstraints);

        medio.setText("Medio (2 operaciones)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 12, 0, 0);
        grupo.add(medio);
        getContentPane().add(medio, gridBagConstraints);

        dificil.setText("Dif�cil (3 operaciones)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 12, 0, 0);
        grupo.add(dificil);
        getContentPane().add(dificil, gridBagConstraints);

        numOperLabel.setText("Numero de operaciones:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(10, 12, 10, 0);
        getContentPane().add(numOperLabel, gridBagConstraints);

        diffNumLabel.setText("Dificultad en los n�meros:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        getContentPane().add(diffNumLabel, gridBagConstraints);

        pack();
        
        // Hacemos el bot�n ok predeterminado
		getRootPane().setDefaultButton(ok);
        
        // Tooltips
        tiempoMax.setToolTipText("Tiempo maximo de juego");
        diffNum.setToolTipText("Nivel en los n�meros elegidos");

        // A�adimos eventos
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                okActionPerformed(evento);
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                cancelActionPerformed(evento);
            }
        });
	}

	// Manejadores de eventos
	/**
	 * Maneja el evento clic sobre el bot�n ok
	 * @param evento
	 */
    protected void okActionPerformed(ActionEvent evento) {
    	setVisible(false);
		estado = true;
    }
    
    /**
     * Maneja el evento clic sobre el bot�n cancel
     * @param evento
     */
    protected void cancelActionPerformed(ActionEvent evento) {
    	setVisible(false);
		estado = false;
    }
		
	/** 
	 * Devuelve si se ha pulsado o no el bot�n ok
	 * */
	public boolean getResult() {
		return estado;
	}
	
	/**
	 * Maneja eventos de acci�n
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
	 * Devuelve n�mero de operaciones seleccionado
	 * @return n�mero de operaciones
	 */
	public int getNumOper() {
		if (facil.isSelected()) {
				return 1;
		}
		else if (medio.isSelected()) {
				return 2;
		}
		else if (dificil.isSelected()) {
				return 3;
		}
		else if (muyDificil.isSelected()) {
				return 4;
		}
		return 0;
	}
	
	/**
	 * Devuelve el nivel (dificultad de los n�meros) seleccionado
	 * @return nivel
	 */
	public int getNivel() {
		return diffNum.getValue();
	}
	
	/**
	 * Devuelve el tiempo m�ximo de juego seleccionado
	 * @return tiempo m�ximo
	 */
	public int getTiempoMax() {
		int i;
		// Buscamos el n�mero del item seleccionado
		for (i=0; i < niveles.length; i++) {
			if (((String)tiempoMax.getValue()).equals(niveles[i])) {
				break;
			}
		}
		
		switch (i) {
		case 0:
			// Caso intensivo
			return 60;
		case 1:
			// Caso intermedio
			return 300;
		case 2:
			// Caso resistencia
			return 600;
		default:
			return 60;	
		} 
	}
	
	/**
	 * Establece n�mero de operaciones pasado
	 * @param unNumOper
	 */
	public void setNumOper(Object unNumOper) {
		String valor = String.valueOf(unNumOper);
		int num = Integer.parseInt(valor);
		switch (num) {
		case 1:
			facil.doClick();
			break;
		case 2:
			medio.doClick();
			break;
		case 3:
			dificil.doClick();
			break;
		case 4:
			muyDificil.doClick();
			break;	
		default:
			break;
		}
	}
	
	/**
	 * Establece el nivel (dificultad de los n�meros) pasado
	 * @param unDiffNum
	 */
	public void setNivel(Object unDiffNum)
	{
		String valor = String.valueOf(unDiffNum);
		int num = Integer.parseInt(valor);
		diffNum.setValue(num);
	}
	
	/**
	 * Establece el tiempo m�ximo de juego pasado
	 * @param unTiempoMax
	 */
	public void setTiempoMax (Object unTiempoMax)
	{
		String valor = String.valueOf(unTiempoMax);
		int num = Integer.parseInt(valor);
		int i;
		switch(num) {
		case 60:
			i = 0;
			break;
		case 300:
			i = 1;
			break;
		case 600:
			i = 2;
			break;
		default:
			i = 0;
			break;
		}
		tiempoMax.setValue(niveles[i]);
	}
	
}

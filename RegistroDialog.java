import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 * Dialogo de registro que se encarga de registrar a un nuevo usuario
 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */
public class RegistroDialog extends JDialog {
	/**
	 * Crea un nuevo dialogo de registro
	 * @param propietario frame padre
	 */
	public RegistroDialog(JFrame propietario) {
		super(propietario, "Registro de nuevos usuarios", true);
		
		GridBagConstraints gridBagConstraints;
		
		// Creamos los componentes
		tituloLabel = new JLabel();
        nombreLabel = new JLabel();
        nombreTextField = new JTextField();
        apellidosLabel = new JLabel();
        apellidosTextField = new JTextField();
        nacimientoLabel = new JLabel();
        nacSpinner = new JSpinner(new SpinnerDateModel());
        mailLabel = new JLabel();
        mailTextField = new JTextField();
        loginLabel = new JLabel();
        loginTextField = new JTextField();
        passLabel = new JLabel();
        passField = new JPasswordField();
        confirmPassLabel = new JLabel();
        confirmPassField = new JPasswordField();
        secuenciaLabel = new JLabel();
        numerosLabel = new JLabel();
        secuencia2Label = new JLabel();
        numerosTextField = new JTextField();
        cancelarButton = new JButton();
        registrarButton = new JButton();
             
        // Creamos el vector de string que almacena
        // todas las secuencias posibles
        secuencia = new String[10];
        secuencia[0]="HYW7";
        secuencia[1]="TKT2";
        secuencia[2]="SSM4";
        secuencia[3]="KZN4";
        secuencia[4]="PTU7";
        secuencia[5]="BEN3";
        secuencia[6]="EDR9";
        secuencia[7]="APN3";
        secuencia[8]="ZYR4";
        secuencia[9]="GFZ6";
        
        // Cerramos el dialogo si el usuario hace clic en el aspa
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Layout GridBagLayout
        getContentPane().setLayout(new GridBagLayout());

        // Colocamos los controles en el GridBagLayout y configuramos
        // sus propiedades
        tituloLabel.setFont(new Font("Tahoma", 0, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setText("Formulario de registro");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        getContentPane().add(tituloLabel, gridBagConstraints);

        nombreLabel.setText("Nombre:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(nombreLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(nombreTextField, gridBagConstraints);

        apellidosLabel.setText("Apellidos:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(apellidosLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(apellidosTextField, gridBagConstraints);

        nacimientoLabel.setText("Fecha de nacimiento:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(nacimientoLabel, gridBagConstraints);

        // Hacemos que no muestre solo muestre la fecha. El comportamiento por defecto
        // es mostrar además de la fecha la hora
        // Ver Cay. S. Horstmann/ Gary Cornell, Java 2 Fundamentos, Sun Microsystems
        String pattern = ((SimpleDateFormat)DateFormat.getDateInstance()).toPattern();
        nacSpinner.setEditor(new JSpinner.DateEditor(nacSpinner, pattern));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 45;
        getContentPane().add(nacSpinner, gridBagConstraints);

        mailLabel.setText("Correo electrónico:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(mailLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(mailTextField, gridBagConstraints);

        loginLabel.setText("Login:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(loginLabel, gridBagConstraints);
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(loginTextField, gridBagConstraints);

        passLabel.setText("Password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(passLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(passField, gridBagConstraints);

        confirmPassLabel.setText("Confirme el password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(confirmPassLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(confirmPassField, gridBagConstraints);

        secuenciaLabel.setText("Introduce la siguiente secuencia alfanumérica:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(secuenciaLabel, gridBagConstraints);
        
        Random generador = new Random();
        String imagen = new String();
        num = generador.nextInt(10);
        imagen = num + ".jpg";
        try {
        	numerosLabel.setIcon(new ImageIcon(getClass().getResource(imagen)));
        }
        catch(Exception e) {
        	JOptionPane.showMessageDialog(this, "No se ha encontrado la imagen" + imagen
        			+ "\nNo va a ser posible que se registre.");
        }
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(numerosLabel, gridBagConstraints);

        secuencia2Label.setText("Secuencia:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(secuencia2Label, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(numerosTextField, gridBagConstraints);

        cancelarButton.setText("Cancelar");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(cancelarButton, gridBagConstraints);

        registrarButton.setText("Registrarse");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(registrarButton, gridBagConstraints);

        pack();
        
        // Hacemos que si el usuario pulsa intro se accione
        // el botón de registrar
        getRootPane().setDefaultButton(registrarButton);
        
        // Anadimos eventos
        // Clic sobre el boton registrarse
        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	registrarButtonActionPerformed(evt);
            }
        });
        
        // Clic sobre el boton cancelar
        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	setVisible(false);
            }
        });
                
        // Modificaciones en el campo de texto del login
        loginTextField.getDocument().addDocumentListener(new DocumentListener(){
    	        public void insertUpdate(DocumentEvent evt) {
    	        	// Se ha insertado una letra
    	        	loginTextFieldCheck(evt);
    	        }
    	        public void changedUpdate(DocumentEvent evt) { 
    	        }
    	        public void removeUpdate(DocumentEvent evt) {
    	        	// Se ha borrado una letra 
    	        	loginTextFieldCheck(evt);
    	        }
        });
	}
	
	// Manejadores de eventos
	/**
	 * Cuando se pulsa el botón registrar
	 */
	protected void registrarButtonActionPerformed(ActionEvent evt) {
		// n: Número total de componentes en el dialogo
		int n = getContentPane().getComponentCount();
		Component comp;
		JTextField f;
		
		for (int i=0; i < n; i++) {
			// Analisis de los campos de texto (tambien se incluyen JPasswordField)
			if ((comp = getContentPane().getComponent(i)) instanceof JTextField) {
				f = (JTextField)comp;
				// Comprobamos que no esta vacia. Tamoco admitimos que haya solo espacios porque hemos
				// hecho un trim() previo que elimina los espacios de los extremos
				f.setText(f.getText().trim());
				if (f.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Rellene todos los campos del formulario");
					return;
				}
				// No admitimos campos demasiado largos
				if (f.getText().length() > textFieldLongMax) {
					JOptionPane.showMessageDialog(this, "La longitud de algún campo supera la permitida");
					return;
				}
				// No admitimos ' por seguridad ya que en las consultas va embedido lo que 
				// escribe el usuario.
				if (charEspecial(f.getText())) {
					JOptionPane.showMessageDialog(this, "No se puede usar '");
					return;
				}
			}
		}
		
		// Comprobacion del correo. Lo hace la funcion checkMail()
		if (checkMail(mailTextField.getText()) == false) {
			JOptionPane.showMessageDialog(this, "Correo electrónico incorrecto");
			return;
		}		
		// Comprobamos que los dos password son iguales
		String pass = new String (passField.getPassword());
		String pass2 = new String (confirmPassField.getPassword());
		if (!pass.equals(pass2)) {
			JOptionPane.showMessageDialog(this, "Los dos password no son iguales");
			return;
		}
		// No admitimos password demasiado cortos
		if (pass.length() < passFieldLongMin) {
			JOptionPane.showMessageDialog(this, "Password demasiado corto");
			return;
		}
		// El passsword no puede ser igual al login
		if (pass.equals(loginTextField.getText())) {
			JOptionPane.showMessageDialog(this, "El password no debe ser igual al login");
			return;
		}
		// Comprobamos que el login no exista ya
		if (existeLogin(loginTextField.getText() )) {
			JOptionPane.showMessageDialog(this, "El login ya existe");
			return;
		}
		// Comprobamos que introduce correctamente la secuencia
		if (!numerosTextField.getText().toUpperCase().equals(secuencia[num])){
			JOptionPane.showMessageDialog(this, "Introduce correctamente el código alfanumérico");
			return;
		}
		
		// Si se llega aqui es que los campos están bien y que el login introducido es unico
		// lo que nos asegura de que no se va a producir errores en la inserción del registro
		// (login es la clave y no puede repetirse)
		Date date = (Date)nacSpinner.getValue();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		String dateStr = "#" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1)
					+ "-" + cal.get(Calendar.DAY_OF_MONTH) + "#";
        DataBase db = new DataBase();
        String sql = new String("INSERT INTO usuarios" +
        		"(nombre, apellidos, nacimiento, correo, login, pass, " +
        		"calculoPtos, calculoTiempoMax, calculoNumOper, calculoNivel, " +
        		"memoriaPtos, memoriaTiempoMax, memoriaCantidadNum, " +
        		"masterPtos, masterTiempoMax, masterIntentos, masterOpciones)" + 
        		" VALUES('" + nombreTextField.getText() + "', '" +
        		apellidosTextField.getText() + "', " +
        		dateStr + ", '" +
        		mailTextField.getText() + "', '" +
        		loginTextField.getText() + "', '" +
        		pass + "'," +
        				" 0, 300, 1, 10," +
        				" 0, 5, 5, " +
        				" 0, 600, 10, 6)");
        db.executeUpdate(sql);
        db.liberaUpdate();
        JOptionPane.showMessageDialog(this, "Ha sido registrado con login:\n" + loginTextField.getText());
        setVisible(false);
    }
	
	/**
	 * Manejador del evento cuando se está escribiendo el login
	 */
	protected void loginTextFieldCheck(DocumentEvent evt) {	
		String str = loginTextField.getText();
		// Si ese login existe pintamos la letras de rojo
		if (!str.isEmpty() && !existeLogin(str)) {
			loginTextField.setForeground(Color.green);
		}
		else {
			loginTextField.setForeground(Color.red);
		}
	}
	// Fin de manejadoras de eventos
	
	/**
	 * Comprueba su un correo tiene formato válido
	 * @param mail Correo
	 * @return true el correo es correcto false si no lo es
	 */
	
	private boolean checkMail(String mail) {
		// Comprobar la longitud
	    if (mail.length() < 5) {
	        return false;
	    }
	   
	    // Buscar la arroba (@) y el punto (.)
	    int at_location = mail.indexOf("@");
	    int dot_location = mail.lastIndexOf(".");
	   
	    if (at_location == -1 || dot_location == -1 || at_location > dot_location) {
	        return false;
	    }

	    // ¿Hay, al menos, un carácter antes de la arroba?
	    if (at_location == 0) {
	        return false;
	    }
	   
	    // ¿Hay, al menos, un carácter entre la arroba y el punto?
	    if (dot_location - at_location <= 1 ) {
	        return false;
	    }

	    // ¿Hay, al menos, un carácter después del punto?
	    if (mail.length() - dot_location <= 1) {
	        return false;
	    }

	    // Si se llega a este punto, la dirección de correo electrónico es válida
	    // por lo que devolvemos true
	    return true;
	}
	
	/**
	 * Averigua si en una cadena hay comillas simples
	 * @param str String
	 * @return true si hay comillas simples y false si no las hay
	 */
	private boolean charEspecial(String str) {
		if (str.indexOf("'") == -1) {
			// Hay caracter especial
			return false;
		}
		else {
			// No hay caracter especial
			return true;
		}
	}
	
	/**
	 * Comprueba que el login no exista ya en la base de datos (Tiene que ser único)
	 * @param login Login
	 * @return true si ya existe, false si no
	 */
	private boolean existeLogin(String login) {
		DataBase db = new DataBase();
		ResultSet result = db.executeQuery("SELECT * FROM usuarios WHERE login ='" + 
				login + "'");
		try {
			if (result.next()) {
				db.liberaQuery();
				return true;
			}
			else {
				db.liberaQuery();
				return false;
			}
		}
		catch (SQLException ex) {
			System.out.println("Excepción SQL");
			while (ex != null) {
				ex.printStackTrace();
				ex.getNextException();
				System.out.println("");
			}
			System.exit(1);
		}
		catch ( Exception ex) {
			System.out.println("Excepción inesperada");
			ex.printStackTrace();
			System.exit(1);
		}
		// Nunca se llega aqui es para que el compilador no se queje
		return false;
	}
	
	/**
	 * Hace visible el dialogo o no
	 * @param visible true para hacer visible el dialogo, false para ocultarlo
	 */
	
	public void setVisible(boolean visible) {
		int n = getContentPane().getComponentCount();
		Component comp;
		JTextField f;
		// Vaciamos el contenido de cada campo de texto
		if (visible == true) {
			Random generador = new Random();
			String imagen;
			for (int i=0; i < n; i++) {
				if ((comp = getContentPane().getComponent(i)) instanceof JTextField) {
					f = (JTextField)comp;
					f.setText("");
				}
			}
			// Ponemos el foco en el primer campo
			nombreTextField.requestFocus();
			// Cambiamos la imagen
			num = generador.nextInt(10);
	        imagen = num + ".jpg";
	        try {
	        	numerosLabel.setIcon(new ImageIcon(getClass().getResource(imagen)));
	        }
	        catch(Exception e) {
	        	JOptionPane.showMessageDialog(this, "No se ha encontrado la imagen" + imagen
	        			+ ".\nNo va a ser posible que se registre.");
	        }
		}
		super.setVisible(visible);
	}
	
	/** Etiqueta estática que indica el título del formulario */
    private JLabel tituloLabel;
	/** Etiqueta estática que indica dónde hay que introducir el nombre */
    private JLabel nombreLabel;
    /** Campo de texto para el nombre */
    private JTextField nombreTextField;
	/** Etiqueta estática que indica dónde hay que introducir los apellidos */
    private JLabel apellidosLabel;
    /** Campo de texto para los apellidos */
    private JTextField apellidosTextField;
    /** Etiqueta estática que indica dónde hay que introducir la fecha de nacimiento */
    private JLabel nacimientoLabel;
    /** Spinner para introducir la fecha de nacimiento */
    private JSpinner nacSpinner;
    /** Etiqueta estática que indica dónde hay que introducir el correo */
    private JLabel mailLabel;
    /** Campo de texto para el correo */
    private JTextField mailTextField;
    /** Etiqueta estática que indica dónde hay que introducir el login */
    private JLabel loginLabel;
    /** Campo de texto para el login */
    private JTextField loginTextField;
    /** Etiqueta estática que indica dónde hay que introducir el password por primera vez */
    private JLabel passLabel;
    /** Campo de password para el password solicitado por primera vez */
    private JPasswordField passField;
    /** Campo password para confirmar el password */
    private JPasswordField confirmPassField;
    /** Etiqueta estática que indica dónde hay que confirmar el password */
    private JLabel confirmPassLabel;
     /** Etiqueta estática que indica cual es la secuencia de caracteres que hay que escribir (no es la que muestra la imagen) */
    private JLabel secuenciaLabel;
    /** Etiqueta estática que indica dónde se tiene que escribir la secuencia alfanumérica */
    private JLabel secuencia2Label;
    /** Etiqueta dónde se muestran las imágenes de las secuencias */
    private JLabel numerosLabel;
    /** Campo de texto para introducir los números de la secuencia */
    private JTextField numerosTextField;
    
    /** Botón registrar */
	private JButton registrarButton;
    /** Botón cancelar */
    private JButton cancelarButton;
    
    /** Secuencias posibles almacenadas como String */
    private String[] secuencia;
    /** Índice que indica cuales de todas las secuencias del miembro secuencia se está actualmente mostrando */
    private int num;
    
    
    /** Altura del diálogo de registro */
    public static final int altura = 450;
    /** Anchura del diálogo de registro */ 
    public static final int anchura = 450;
    
    /** Longitud maxima de los campos de texto */
    private static final int textFieldLongMax = 50;
    /** Longitud mínima del password */
    private static final int passFieldLongMin = 8;
}

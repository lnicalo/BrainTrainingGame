import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Dialogo de acceso que se encarga de autenticar a un usuario
 * El usuario podrá acceder si está registrado en la base de datos
 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */
public class AccesoDialog extends JDialog {
	/**
	 * Crea un nuevo dialogo de acceso
	 * @param parent frame padre
	 */
	public AccesoDialog(JFrame parent) {
		super(parent, "Acceso a Brain Training", true);
		GridBagConstraints gridBagConstraints;
		
		// Creamos el dialogo de registro (sin mostrarlo) 
		regDlg = new RegistroDialog((JFrame)getParent());
		
		// El usuario todavia no esta autenticado
		estado = false;
		
		// Creamos los controles del dialogo
        tituloLabel = new JLabel();
        loginLabel = new JLabel();
        loginTextField = new JTextField();
        passLabel = new JLabel();
        passTextField = new JPasswordField();
        avisoLabel = new JLabel();
        salirButton = new JButton();
        entrarButton = new JButton();
        registrarseButton = new JButton();

        // Si se hace clic en X se cierra la ventana
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        // Layout
        getContentPane().setLayout(new GridBagLayout());

        // Colocamos los componentes en el GridBagLayout y 
        // iniciamos sus propiedades
        tituloLabel.setFont(new Font("Tahoma", 1, 18));
        tituloLabel.setText("Acceso a Brain Training");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new Insets(11, 0, 11, 0);
        getContentPane().add(tituloLabel, gridBagConstraints);

        loginLabel.setText("login:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(loginLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(loginTextField, gridBagConstraints);

        passLabel.setText("password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(passLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(passTextField, gridBagConstraints);

        avisoLabel.setVisible(false);
        avisoLabel.setForeground(new Color(255, 0, 0));
        avisoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avisoLabel.setText("<html><p>Acceso denegado. En el caso de estar registrado<br>" +
        		"puede hacerlo si pulsa el botón Registrarse</p></html>");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(avisoLabel, gridBagConstraints);

        salirButton.setText("Salir");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(salirButton, gridBagConstraints);

        entrarButton.setText("Entrar");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.insets = new Insets(6, 8, 6, 8);
        getContentPane().add(entrarButton, gridBagConstraints);

        registrarseButton.setText("Registrarse");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        getContentPane().add(registrarseButton, gridBagConstraints);

        pack();

        // Hacemos el botón entrar predeterminado
        getRootPane().setDefaultButton(entrarButton);
        
        // Añadimos los eventos
        salirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evento) {
        		System.exit(0);
        	}
        });
        
        entrarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evento) {
        		entrarButtonActionPerformed(evento);
        	}
        });
        registrarseButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evento) {
        		registrarseButtonActionPerformed(evento);
        	}
        });
        passTextField.addFocusListener(new FocusAdapter() {
        	public void focusGained(FocusEvent env) {
        		avisoLabel.setVisible(false);
        	}
        });
        
        loginTextField.addFocusListener(new FocusAdapter() {
        	public void focusGained(FocusEvent env) {
        		avisoLabel.setVisible(false);
        	}
        });
	}
	
	/**
	 * Maneja el evento clic sobre el botón entrar
	 * @param evento
	 */
	protected void entrarButtonActionPerformed(ActionEvent evento) {
		String login = loginTextField.getText();
		String pass	 = new String(passTextField.getPassword());
		
		// Comprobamos que no se dejaron vacios los campos de texto
		if (login.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor introduzca el login y el password");
			return;
		}
		
		// No permitimos usar comillas simples por seguridad al hacer las consultas en la
		// base de datos.
		if (charEspecial(login) || charEspecial(pass)) {
			JOptionPane.showMessageDialog(this, "No se puede usar '");
			return;
		}
		
		// Si las entradas del usuario son válidas consultamos la base de datos
		// para realizar la autenticación
		DataBase db = new DataBase();
		ResultSet result = db.executeQuery("SELECT * FROM usuarios" +
				" WHERE login= '" + login + "' AND pass= '" +
				pass + "'");
		try {
			// Comprobamos que hay un registro. Si lo hay este es único ya que no hay
			// dos usuarios con el login igual.
			// Si hay un registro el usuario está registrado
			if (result.next()) {
				estado = true;
				setVisible(false);
			}
			else {
				// Hacemos visible el aviso password incorrecto
				avisoLabel.setVisible(true);
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
		// Despues de cada consulta Query hay que liberar los recursos
		db.liberaQuery();
	}
	
	/** 
	 * Maneja el evento clic sobre el botón resgistrarse
	 * @param evento
	 */
	protected void registrarseButtonActionPerformed(ActionEvent evento) {
		// Centramos el dialogo de registro en la pantalla
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int scHeight = screenSize.height;
		int scWidth = screenSize.width;
        regDlg.setBounds((scWidth - RegistroDialog.anchura) / 2 ,
        		(scHeight - RegistroDialog.altura) / 2,
        		RegistroDialog.anchura, RegistroDialog.altura);
        
        // No permitimos que se pueda modificar su tamaño para que los controles
        // no se descoloquen
        regDlg.setResizable(false);
		regDlg.setVisible(true);
	}
	
	/**
	 * Devuelve el login del usuario registrado si no hay ninguno
	 * registrado devuelve null
	 * @return "String"
	 */
	public String getLoginRegistrado() {
		if (estado) {
			return loginTextField.getText();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Busca comillas simples en una cadena
	 * @param str string donde queremos analizar si hay comillas simples
	 * @return "boolean"
	 * @see AccesoDialog#entrarButtonActionPerformed
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
	 * Reinicia el contenido del dialogo cuando el dialogo se hace visible
	 * Llama a setVisible de la clase padre
	 */
	public void setVisible(boolean visible) {
		if (visible == true) {
			// Limpiamos los campos y reiniciamos el estado
			loginTextField.setText("");
			passTextField.setText("");
			loginTextField.requestFocus();
			estado = false;
		}
		super.setVisible(visible);
	}    
	
	/** Anchura del dialogo*/
	public static final int anchura = 400;
	
	/** Altura del dialogo */
	public static final int altura = 220;
	
	/** Título del formulario */
    private JLabel tituloLabel;
    
	/** Etiqueta estática que indica dónde introducir el login */
    private JLabel loginLabel;
    /** Campo de texto para introducir el login */
    private JTextField loginTextField;
    /** Etiqueta estática que indica dónde introducir el password */
    private JLabel passLabel;
    /** Campo de texto para introducir el password */
    private JPasswordField passTextField;
    
    /** Botón para registrarse */
    private JButton registrarseButton;
    /** Botón para salir */
    private JButton salirButton;
    /** Botón para acceder */
	private JButton entrarButton;
	
    /** Label que se muestra cuando el login o el password son incorrectos */
    private JLabel avisoLabel;
    
    /** Para acceder al dialogo de registro */
    private RegistroDialog regDlg;
    
    /** Bandera que indica que el usuario ha sido autenticado  */
    private boolean estado;
}

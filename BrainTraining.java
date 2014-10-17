import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
/**
 * Clase principal. Es la ventana principal del juego braintraining
 *  Por ejemplo:
 * <pre>
 *    BrainTraining bt= new BrainTraining();
 * </pre>

 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */
public class BrainTraining extends JFrame {
	/** Crea un nuevo  frame BrainTraining */
	public BrainTraining() {
		initComponents();
		inicio();
	}

	/**
	 * Inicializa los componentes que hay dentro del frame braintraining
	 * y el dialogo de acceso asociado a él
     */
	private void initComponents() {
		// Construimos los paneles
		JTabbedPane jTabbedPanel = new JTabbedPane();
		calculoPanel = new CalculoPanel();
		memoriaPanel = new MemoriaPanel();
		masterPanel = new MasterPanel();
		
		// Construimos los diálogos relacionados
		accesoDlg = new AccesoDialog(this);


		// Creamos la barra de menu
		menu();

		// Cerramos la aplicacion si la ventana principal se cierra
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Layaout
		getContentPane().setLayout(null);

		// Añadimos los paneles
		jTabbedPanel.addTab("Cálculo mental", calculoPanel);
		jTabbedPanel.addTab("Memorión", memoriaPanel);
		jTabbedPanel.addTab("Master Mind", masterPanel);
		getContentPane().add(jTabbedPanel);
		
		// Tamaño y posición del control de pestaña en el frame- Dejamos márgenes
		jTabbedPanel.setBounds(5, 5, BrainTraining.anchura-30, BrainTraining.altura-70);
		
		// No permitimos al usuario que pueda modificar el tamaño. 
		setResizable(false);
		pack();

	}

	/**
	 * Muestra el dialogo de acceso y oculta y centra el frame pricipal.
	 * Es el estado inicial del programa
	 */
	public void inicio() {
		// Centra el frame en la pantalla
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int scHeight = screenSize.height;
		int scWidth = screenSize.width;
		setBounds((scWidth - BrainTraining.anchura) / 2 ,
				(scHeight - BrainTraining.altura) / 2,
				BrainTraining.anchura, BrainTraining.altura);
		setTitle("Brain Training");
		
		// Ocultamos el frame principal hasta que el usuario no se haya autenticado
		setVisible(false);

		// Mostramos centrado en la pantalla el dialogo de acceso
		accesoDlg.setBounds((scWidth - AccesoDialog.anchura) / 2 ,
				(scHeight - AccesoDialog.altura) / 2,
				AccesoDialog.anchura, AccesoDialog.altura);
		accesoDlg.setResizable(false);
		accesoDlg.setVisible(true);
		
		// Autenticacion
		String login = accesoDlg.getLoginRegistrado();
		if (login == null) {
			// Si se llega aqui es porque se cierro la ventana
			// sin que el usuario haya sido aceptado en el sistema
			// por tanto salimos
			System.exit(0);
		}
		
		// Mostramos el frame principal
		setVisible(true);
		
		// Creamos al usuario por el login, que es unico.
		// Y asignamos el usuario
		User user = new User(login);
		setUser(user);
	}
	
	/**
	 * Asigna un usuario a braintraining y a cada panel
	 * @param unUser usuario
	 */
	public void setUser(User unUser) {
		user = unUser;
		calculoPanel.setUser(unUser);
		memoriaPanel.setUser(unUser);
		masterPanel.setUser(unUser);
	}
	
	/**
	 * Crea la barra de menu del frame. No llamar directamente
	 */
	protected void menu() {
		// Creamos el menuBar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu Usuario
		JMenu userMenu = new JMenu("Usuario");
		menuBar.add(userMenu);
		//Usuario -> Eliminar perfil
		JMenuItem bajaItem = new JMenuItem("Eliminar perfil");
		userMenu.add(bajaItem);
		bajaItem.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent evt) {
				darBaja();
			}
		});
		
		// Usuario -> salir
		JMenuItem salirItem = new JMenuItem("Salir");
		userMenu.add(salirItem);
		salirItem.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent evt) {
				salir();
			}
		});

		// Menu juego
		JMenu juegoMenu = new JMenu("Juego");
		menuBar.add(juegoMenu);
		//Usuario -> Eliminar perfil
		JMenuItem recordItem = new JMenuItem("Mostrar puntuaciones máximas");
		juegoMenu.add(recordItem);
		recordItem.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent evt) {
				mostrarRecords();
			}
		});
	}

	/**
	 * Elimina un usuario
	 */
	public void darBaja() {
		if (JOptionPane.showConfirmDialog(this,
				"¿ Está seguro/a de que se quiere dar da baja ?") == 0) {
			user.darBaja();
			JOptionPane.showMessageDialog(this, "Eliminado el perfil");
			
			// Volvemos al principio
			inicio();
		}
	}
	
	/**
	 * Sale del juego y vuelve al principio
	 */
	public void salir() {
		if (JOptionPane.showConfirmDialog(this,
		"¿ Está seguro/a de que se quiere abandonar el juego ?") == 0) {
			inicio();
		}
	}
	
	/**
	 * Muestra las puntuaciones máximas obtenidas por los usuarios registrados
	 */
	public void mostrarRecords() {
		recordsDlg = new RecordsDlg(this);
		// Centramos el dialogo en la ventana principal
		Rectangle rc = getBounds();
		recordsDlg.setBounds(rc.x + (BrainTraining.anchura - RecordsDlg.anchura) / 2 ,
				rc.y + (BrainTraining.altura - RecordsDlg.altura) / 2,
				RecordsDlg.anchura, RecordsDlg.altura);
		recordsDlg.setVisible(true);
	}

	/**
	 * Funcion principal del programa BrainTraining
	 * @param args vector de argumentos de la linea de comandos (no tiene utilidad)
	 */
	public static void main(String args[]) {
		new BrainTraining();
	}
	
	 /** Altura del frame */
	public static final int altura = 670;
	/** Anchura del frame */
	public static final int anchura = 730;

	/** Para acceder al dialogo de acceso */
	private AccesoDialog accesoDlg;
	/** Para acceder al diálogo de puntuaciones máximas */
	private RecordsDlg recordsDlg;
	/** Para almacenar que ha accedido al juego */
	private User user;
	/** Menu */
	private JMenuBar menuBar;
	
	/** Panel que contiene el juego de calculo mental */
	private CalculoPanel calculoPanel;
	/** Panel que contiene el juego de memoria */
	private MemoriaPanel memoriaPanel;
	/** Panel que contiene el juego MasterMind */
	private MasterPanel masterPanel;
}

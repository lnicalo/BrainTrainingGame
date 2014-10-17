import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 * Dialogo que muestra las mejores puntuaciones de cada juego
 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */
public class RecordsDlg extends JDialog {
	/**
	 * Crea un nuevo dialogo RecordsDlg
	 * @param parent frame padre
	 */
	public RecordsDlg(JFrame parent) {
		super(parent, "Puntuaciones máximas", true);
		// Juegos posibles
		String juego[] = {"Calculo Mental", "Memorión", "MasterMind"};
		// Campos que hay que consultar para saber los puntos de cada usuario en cada juego
		String campo[] = {"calculoPtos", "memoriaPtos", "masterPtos"};
		Object datos[][];
		String[] columnas = {"Login", "Puntuación máxima"};
		
		// Layout BoxLayout
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Récords");
		label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		label.setFont(new Font("Arial",Font.BOLD,20));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		getContentPane().add(label);
		
		// Se consulta una vez y se dibuja una tabla por cada juego. En el caso de haber
		// empate en las puntuaciones máximas se muestran todos los jugadores empatados con esa puntuación 
		for (int i=0; i < 3; i ++) {
			DataBase db = new DataBase();
			ResultSet rs = db.executeQuery("SELECT login, " + campo[i] + " FROM usuarios WHERE " + campo[i] + "  = " +
					"(SELECT MAX(" + campo[i] + ") FROM usuarios)");
			try {
				// Sacamos el numeros de filas
				rs.last();
				int filas = rs.getRow();
				// Hay que volver al principio
				rs.beforeFirst();
				datos = new Object[filas][2];
				for (int j=0; rs.next();j++){
					 datos[j][0] = new String(rs.getString("login"));
					 datos[j][1] = new String(rs.getInt(campo[i]) + "");
				}
				JTable t = new JTable(datos, columnas);
				t.setPreferredScrollableViewportSize(new Dimension(400, 10));
				t.setAlignmentX(Component.CENTER_ALIGNMENT);
				t.setFillsViewportHeight(true); 
				JScrollPane sp = new JScrollPane(t);
				
				JPanel listPane = new JPanel();
		        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		        label = new JLabel(juego[i]);
		        label.setLabelFor(t);
		        listPane.add(label);
		        listPane.add(Box.createRigidArea(new Dimension(0,5)));
		        listPane.add(sp);
		        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		        
		      
		        //Put everything together, using the content pane's BorderLayout.
		        getContentPane().add(listPane, BorderLayout.CENTER);
		        
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
			db.liberaQuery();
		}
		JPanel buttonPane = new JPanel();
        JButton aceptarButton = new JButton("Aceptar");
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(aceptarButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        getContentPane().add(buttonPane, BorderLayout.PAGE_END);
        getRootPane().setDefaultButton(aceptarButton);
		pack();
		
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
	}
	
	/** Altura del diálogo */
	public static final int altura = 400;
	/** Anchura del diálogo */
    public static final int anchura = 300;
}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Clase envoltorio de las operaciones de acceso a la base de datos
 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */

public class DataBase {	
	/**
	 * Crea DataBase
	 */
	public DataBase() {	
	}
	
	/**
	 * Realiza una consulta a la base de datos de tipo Query 
	 * @see Statement#executeQuery(String)
	 * @param sql sentencia SQL para hacer la consulta
	 * @return el objeto ResultSet contiene los datos obtenidos al hacer la consulta
	 */
	public ResultSet executeQuery(String sql) {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(urlID);
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = state.executeQuery(sql);
			return result;			
		}
		catch (SQLException ex) {
			System.out.println("Excepción SQL");
			while (ex != null) {
				ex.printStackTrace();
				ex.getNextException();
				System.out.println("");
				System.exit(1);
			}
		}
		catch ( Exception ex) {
			System.out.println("Excepción inesperada");
			ex.printStackTrace();
			System.exit(1);
		}
		// Para que no de error de compilacion
		return null;
	}
	
	/**
	 * Realiza una consulta a la base de datos de tipo Query 
	 * @see Statement#executeQuery(String)
	 * @param sql sentencia SQL para hacer la consulta
	 * @return número de filas afectadas por la consulta
	 */
	public int executeUpdate(String sql) {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(urlID, "Administrador", "barcelona");
			state = con.createStatement();
			int filas = state.executeUpdate(sql);
			return filas;			
		}
		catch (SQLException ex) {
			System.out.println("Excepción SQL");
			while (ex != null) {
				ex.printStackTrace();
				ex.getNextException();
				System.out.println("");
				System.exit(1);
			}
		}
		catch ( Exception ex) {
			System.out.println("Excepción inesperada");
			ex.printStackTrace();
			System.exit(1);
		}
		// Para que no de error de compilacion. No va a llegar nunca aqui
		return -1;
	}
	
	/**
	 * Libera los recursos después de hacer una consulta tipo Query 
	 * @see DataBase#executeQuery(String)
	 */
	public void liberaQuery() {
		try {
			result.close();
			state.close();
			con.close();
		}
		
		catch (SQLException ex) {
			System.out.println("Excepción SQL");
			while (ex != null) {
				ex.printStackTrace();
				ex.getNextException();
				System.out.println("");
				System.exit(1);
			}
		}
		catch ( Exception ex) {
			System.out.println("Excepción inesperada");
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Libera los recursos después de hacer una consulta tipo Update 
	 * @see DataBase#executeUpdate(String)
	 */
	public void liberaUpdate() {
		try {
			state.close();
			con.close();
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
	}
	
	/** urlID */
	final static private String	urlID = "jdbc:odbc:JDBC_BRAINTRAINING";
	/** driver */
	final static private String	driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	/** Conexión a la base de datos */
	private Connection con;
	/** Statement para hacer las consultas */
	private Statement state;
	/** ResultSet obtenido después de una consulta Query */
	private ResultSet result;
}

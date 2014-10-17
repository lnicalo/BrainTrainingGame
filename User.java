import java.sql.ResultSet;
import java.sql.SQLException;
/** 
 * Clase que almacena todos los datos relativos al usuario que está jugando 
 * @author  Daniel Gómez Frontela
 * @author	Luis Fernando Nicolás Alonso
 */
public class User {
	
	/**
	 * Crea una nueva clase User
	 * @param unLogin login del usuario cuyos datos se quieren guardar en la clase
	 */
	public User(String unLogin) {
		login = unLogin;
		DataBase db = new DataBase();
		ResultSet result = db.executeQuery("SELECT * FROM usuarios WHERE login= '" + login + "'");
		// Cargamos el perfil dado por login
		try {
			result.next();
			pass = result.getString("pass");
			nombre = result.getString("nombre");
			apellidos = result.getString("apellidos");
			correo = result.getString("correo");
			nacimiento = result.getDate("nacimiento").toString();
			
			// Perfil para el juego cuentas
			calculoPtos = result.getInt("calculoPtos");
			calculoTiempoMax = result.getInt("calculoTiempoMax");
			calculoNumOper = result.getInt("calculoNumOper");
			calculoNivel = result.getInt("calculoNivel");
			
			// Perfil para el juego memoria
			memoriaPtos = result.getInt("memoriaPtos");
			memoriaTiempoMax = result.getInt("memoriaTiempoMax");
			memoriaCantidadNum = result.getInt("memoriaCantidadNum");
			
			// Perfil para el juego MasterMind
			masterPtos = result.getInt("masterPtos");
			masterTiempoMax = result.getInt("masterTiempoMax");
			masterIntentos = result.getInt("masterIntentos");
			masterOpciones = result.getInt("masterOpciones");
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
		// Liberamos recursos después de la consulta.
		// Esto hay que hacerlo despues de obtener los datos de ResultSet
		db.liberaQuery();
	}
	
	/**
	 * Obtiene el contenido de cada campo en funcion del nombre 
	 * @param campo Nombre del campo cuyo valor queremos saber
	 * @return el objeto Object contiene el valor del campo
	 */
	public Object getCampo(String campo) {
		if (campo.equals("login"))
			return login;
		if (campo.equals("pass"))
			return pass;
		if (campo.equals("nombre"))
			return nombre;
		if (campo.equals("apellidos"))
			return apellidos;
		else if (campo.equals("correo"))
			return correo;
		else if (campo.equals("nacimiento"))
			return nacimiento;
		else if (campo.equals("calculoPtos"))
			return calculoPtos;
		else if (campo.equals("calculoTiempoMax"))
			return calculoTiempoMax;
		else if (campo.equals("calculoNumOper"))
			return calculoNumOper;
		else if (campo.equals("calculoNivel"))
			return calculoNivel;
		else if (campo.equals("memoriaPtos"))
			return memoriaPtos;
		else if (campo.equals("memoriaTiempoMax"))
			return memoriaTiempoMax;
		else if (campo.equals("memoriaCantidadNum"))
			return memoriaCantidadNum;
		else if (campo.equals("masterPtos"))
			return masterPtos;
		else if (campo.equals("masterTiempoMax"))
			return masterTiempoMax;
		else if (campo.equals("masterIntentos"))
			return masterIntentos;
		else if (campo.equals("masterOpciones"))
			return masterOpciones;
		// Si no existe ese campo
		return null;
	}
	
	/**
	 * Modifica el valor de un campo long o int de la clase usuario.
	 * @param campo Nombre del campo long que queremos modificar
	 * @param valorCampo long con el valor que queremos introducir
	 */
	public void setCampo(String campo, long valorCampo) {
		boolean flag = false;
		if (campo.equals("calculoPtos")) {
			calculoPtos=valorCampo;
			flag=true;
		}
		else if (campo.equals("calculoTiempoMax")) {
			calculoTiempoMax=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("calculoNumOper")) {
			calculoNumOper=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("calculoNivel")) {
			calculoNivel=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("memoriaPtos")) {
			memoriaPtos=valorCampo;
			flag=true;
		}
		else if (campo.equals("memoriaTiempoMax")) {
			memoriaTiempoMax=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("memoriaCantidadNum")) {
			memoriaCantidadNum=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("masterPtos")) {
			masterPtos=valorCampo;
			flag=true;
		}
		else if (campo.equals("masterTiempoMax")) {
			masterTiempoMax=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("masterIntentos")) {
			masterIntentos=(int)valorCampo;
			flag=true;
		}
		else if (campo.equals("masterOpciones")) {
			masterOpciones=(int)valorCampo;
			flag=true;
		}
		
		// Si el campo no es correcto no hacemos nada
		if (flag==true) {
			DataBase db = new DataBase();
			db.executeUpdate("UPDATE Usuarios SET " + campo +  " = " + valorCampo + " WHERE login= '" + login + "'");
			db.liberaUpdate();
		}	
	}
	
	/**
	 * Modifica el valor de un campo long o int de la clase usuario. El login del usuario no se puede modificar.
	 * @param campo Nombre del campo String que queremos modificar
	 * @param valorCampo String con el valor que queremos introducir
	 */
	public void setCampo(String campo, String valorCampo) {
		boolean flag = false;
		if (campo.equals("pass")) {
			pass=valorCampo;
			flag=true;
		}
		if (campo.equals("nombre")) {
			nombre=valorCampo;
			flag=true;
		}
		if (campo.equals("apellidos")) {
			apellidos=valorCampo;
			flag=true;
		}
		else if (campo.equals("correo")) {
			correo=valorCampo;
			flag=true;
		}
		else if (campo.equals("nacimiento")) {
			nacimiento=valorCampo;
			DataBase db = new DataBase();
			db.executeUpdate("UPDATE Usuarios SET nacimiento = #" + valorCampo + "# WHERE login= '" + login + "'");
			db.liberaUpdate();
		}
		
		// Si el campo no es correcto o el campo es nacimiento no hacemos nada aquí
		if (flag==true) {
			DataBase db = new DataBase();
			db.executeUpdate("UPDATE Usuarios SET " + campo +  " = " + valorCampo + " WHERE login= '" + login + "'");
			db.liberaUpdate();
		}
	}
	
	/**
	 * Elimina al usuario de la base de datos
	 */
	public void darBaja() {
		DataBase db = new DataBase();
		db.executeUpdate("DELETE FROM usuarios WHERE login = '" + login + "'");
		db.liberaUpdate();
		
	}
	
	/** Login */
	private String  login;
	/** Password */
	private String pass;
	/** Nombre */
	private String	nombre;
	/** Apellidos */
	private String	apellidos;
	/** Correo */
	private String	correo;
	/** Cadena con la fecha de nacimiento */
	private String	nacimiento;
	
	// Perfil para el juego cuentas
	/** Puntos en cálculo mental */
	private	long	calculoPtos;
	/** Tiempo máximo por prueba en cálculo mental */
	private int		calculoTiempoMax;
	/** Numero de operaciones por cuenta en cálculo mental */
	private int		calculoNumOper;
	/** Dificultad de los números escogidos en cálculo mental */
	private int		calculoNivel;
	
	// Perfil para el juego memoria
	/** Puntos en memoria */
	private long	memoriaPtos;
	/** Tiempo máximo para memorizar las secuencias en el juego memoria */
	private int		memoriaTiempoMax;
	/** Cantidad de números a memorizar por secuencia en el juego memoria */
	private int		memoriaCantidadNum;
	
	// Perfil para el juego MasterMind
	/** Puntos en mastermind */
	private long	masterPtos;
	/** Tiempo máximo por prueba en mastermind */
	private int		masterTiempoMax;
	/** Número de intentos máximo por secuencia a adivinar en el juego mastermind */
	private	int		masterIntentos;
	/** Cantidad de números diferentes por secuencia en el juego mastermind */
	private int		masterOpciones;
	
}


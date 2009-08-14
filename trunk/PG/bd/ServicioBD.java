package bd;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;




public class ServicioBD {

	public static Connection conexion;
	public static boolean crearConexion(String servidor, String puerto, String usuario, char[] clave, String nombreBD)
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			conexion=(Connection) DriverManager.getConnection("jdbc:mysql://"+servidor+":"+puerto+"/"+nombreBD, usuario, new String(clave));
		//System.out.println("Conexion exitosa");
		return true;
		}
		catch(SQLException ex)
		{
			//System.out.println("Error en la conexión");
			return false;
		} catch (ClassNotFoundException e) {
			//System.out.println(e);
			return false;
		}
	}
}

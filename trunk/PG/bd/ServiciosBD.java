package bd;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ServiciosBD {

	public static Connection conexion;

	/**
	 * Se abre una conexión a la base de datos MySQL.
	 * 
	 * @param servidor
	 *            - Host en donde se encuentra alojada la base de datos. (puede
	 *            ser una ip, o una dirección)
	 * @param puerto
	 *            - Puerto a utilizar por la base de datos. (Por defecto debería
	 *            ser 3306).
	 * @param usuario
	 *            - Nombre de usuario con el cual te conectarás a la base de
	 *            datos. (Por defecto es root).
	 * @param clave
	 *            - Clave de acceso correspondiente a dicho nombre de usuario.
	 *            (Generalmente no tiene o es 123456).
	 * @param nombreBD
	 *            - El nombre de la base de datos en la cual se encuentra el
	 *            grafo a dibujar.
	 * @return True si la conexión se pudo llevar a cabo correctamente. False en
	 *         caso contrario.
	 */
	public static boolean crearConexion(String servidor, String puerto,
			String usuario, char[] clave, String nombreBD) {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			conexion = (Connection) DriverManager.getConnection("jdbc:mysql://"
					+ servidor + ":" + puerto + "/" + nombreBD, usuario,
					new String(clave));
			// System.out.println("Conexion exitosa");
			return true;
		} catch (SQLException ex) {
			// System.out.println("Error en la conexión");
			return false;
		} catch (ClassNotFoundException e) {
			// System.out.println(e);
			return false;
		}
	}

	/**
	 * @return Retorna todos los códigos de todos los nodos que se encuentran en
	 *         la base de datos.
	 */
	public static int[] getCodigosNodos() {
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT codigo FROM nodos ORDER BY codigo");
			ResultSet res = query.executeQuery();
			res.last();
			int[] codigos = new int[res.getRow()];
			res.first();
			for (int c = 0; c < codigos.length; c++, res.next()) {
				codigos[c] = res.getInt("codigo");
			}
			query.close();
			res.close();
			return codigos;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Consulta el tipo de nodo de un nodo en específico.
	 * 
	 * @param codigo
	 *            del nodo al cual se le desea consultar el tipo.
	 * @return el tipo de nodo al cual pertenece.
	 */
	public static String getTipoNodo(int codigo) {
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT nt.nombre FROM nodos n, nodos_tipo nt WHERE n.codigo_tipo = nt.codigo AND n.codigo = "
							+ codigo);
			ResultSet res = query.executeQuery();
			if (res.next()) {
				String tipo = res.getString("nombre");
				query.close();
				res.close();
				return tipo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ESte método se encarga de consultar todas las propiedades de un nodo
	 * determinado.
	 * 
	 * @param codigo
	 *            del nodo al cual se desea consultar sus correspondientes
	 *            propiedades.
	 * @return Retorna una colección Clave-Valor de todos los atributos de dicho
	 *         nodo.
	 */
	public static HashMap<String, Object> getAtributosNodo(int codigo) {
		HashMap<String, Object> nodos = new HashMap<String, Object>();
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT pt.nombre, pn.valor FROM propiedades_nodos pn, propiedades_tipo pt WHERE pn.codigo_propiedad = pt.codigo AND pn.codigo_nodo = "
							+ codigo);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				nodos.put(res.getString("nombre"), (Object) res
						.getString("valor"));
			}
			query.close();
			res.close();
			return nodos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HashMap<String, Object> getAtributosArista(int codigo) {
		HashMap<String, Object> aristas = new HashMap<String, Object>();
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT pt.nombre, pa.valor FROM propiedades_aristas pa, propiedades_tipo pt WHERE pa.codigo_propiedad = pt.codigo AND pa.codigo_arista = "
							+ codigo);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				aristas.put(res.getString("nombre"), (Object) res
						.getString("valor"));
			}
			query.close();
			res.close();
			return aristas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int[] getCodigosAristas() {
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT codigo FROM aristas ORDER BY codigo");
			ResultSet res = query.executeQuery();
			res.last();
			int[] aristas = new int[res.getRow()];
			res.first();
			for (int c = 0; c < aristas.length; c++, res.next()) {
				aristas[c] = res.getInt("codigo");
			}
			query.close();
			res.close();
			return aristas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTipoArista(int codigo) {
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT at.nombre FROM aristas a, aristas_tipo at WHERE a.codigo_tipo = at.codigo AND a.codigo = "
							+ codigo);
			ResultSet res = query.executeQuery();
			if (res.next()) {
				String tipo = res.getString("nombre");
				query.close();
				res.close();
				return tipo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int[] getNodosArista(int codigo) {
		int[] nodos = new int[2];
		PreparedStatement query;
		try {
			query = conexion
					.prepareStatement("SELECT nodo_origen, nodo_destino FROM aristas WHERE codigo = "
							+ codigo);
			ResultSet res = query.executeQuery();
			if (res.next()) {
				nodos[0] = res.getInt("nodo_origen");
				nodos[1] = res.getInt("nodo_destino");
				query.close();
				res.close();
				return nodos;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getTiposAristas() {
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT DISTINCT codigo, nombre FROM aristas_tipo");
			ResultSet res = query.executeQuery();
			res.last();
			String[] tipos = new String[res.getRow()];
			res.first();
			for (int c = 0; c < tipos.length; c++, res.next()) {
				tipos[c] = res.getString("codigo") + " - "
						+ res.getString("nombre");
			}
			query.close();
			res.close();
			return tipos;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static String[] getTiposNodos() {
		try {
			PreparedStatement query = conexion
					.prepareStatement("SELECT DISTINCT codigo, nombre FROM nodos_tipo");
			ResultSet res = query.executeQuery();
			res.last();
			String[] tipos = new String[res.getRow()];
			res.first();
			for (int c = 0; c < tipos.length; c++, res.next()) {
				tipos[c] = res.getString("codigo") + " - "
						+ res.getString("nombre");
			}
			query.close();
			res.close();
			return tipos;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

}

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

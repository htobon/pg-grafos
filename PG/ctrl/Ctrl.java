package ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.jme.renderer.ColorRGBA;

import bd.ServiciosBD;

import modelo.Arista;
import modelo.Grafo;
import modelo.Nodo;

public class Ctrl {

	private static Grafo grafo = null;

	/**
	 * Este m�todo crea una conexi�n a la base de datos MySQL.
	 * 
	 * @param servidor
	 *            direcci�n ip.
	 * @param puerto
	 *            de escucha de la base de datos MySQL.
	 * @param usuario
	 *            MySQL
	 * @param clave
	 * @param nombreBD
	 *            nombre de la base de datos.
	 * @return true si la conexion con la base de datos ha sido efectiva. Falso
	 *         en caso contrario.
	 */
	public static boolean probarConexion(String servidor, String puerto,
			String usuario, char[] clave, String nombreBD) {
		return ServiciosBD.crearConexion(servidor, puerto, usuario, clave,
				nombreBD);
	}

	/**
	 * Busca cada uno de los identificadores de los nodos que tiene el grafo.
	 * 
	 * @return Retorna un arreglo de enteros correspondiente a los c�digos de
	 *         identificaci�n de cada uno de los nodos.
	 */
	public static int[] getCodigosNodos() {
		int[] codigos = new int[grafo.getNodos().size()];
		int cont = 0;
		for (Iterator<Nodo> i = grafo.getNodos().iterator(); i.hasNext(); cont++) {
			codigos[cont] = i.next().getCodigo();
		}
		return codigos;
	}

	/**
	 * Busca cada uno de los identificadores de las aristas que tiene el grafo.
	 * 
	 * @return Retorna un arreglo de enteros correspondiente a los c�digos de
	 *         identificaci�n de cada una de las aristas.
	 */
	public static int[] getCodigosAristas() {
		int[] codigos = new int[grafo.getAristas().size()];
		int cont = 0;
		for (Iterator<Arista> i = grafo.getAristas().iterator(); i.hasNext(); cont++) {
			codigos[cont] = i.next().getCodigo();
		}
		return codigos;
	}

	/**
	 * 
	 * @return Retorna el objeto grafo utilizado en la aplicaci�n.
	 */
	public static Grafo getGrafo() {
		return grafo;
	}

	/**
	 * Este m�todo es el encargado de crear un objeto grafo en el modelo de la
	 * aplicaci�n.
	 * 
	 * @return true si se cre� efectivamente el grafo. false en caso contrario.
	 */
	public static boolean crearGrafo() {
		grafo = new Grafo();
		extraerNodos();
		extraerAristas();
		llenarMapaColores();
		llenarFiguras();
		return true;
	}

	/**
	 * Este m�todo se encarga de devolver un arreglo de 2 posiciones de los
	 * nodos que componen a una ar�sta espec�fica.
	 * 
	 * @param codigoArista
	 *            correspondiente al id de la arista.
	 * @return un arreglo de 2 posiciones de enteros correspondiente a los ids
	 *         de los nodos que componen dicha arista. int[0]=NodoDesde
	 *         int[1]=NodoHacia
	 */
	public static int[] getNodosDeArista(int codigoArista) {
		for (Arista arista : grafo.getAristas()) {
			if (arista.getCodigo() == codigoArista) {
				return new int[] { arista.getOrigen().getCodigo(),
						arista.getDestino().getCodigo() };
			}
		}
		return null;
	}

	private static void extraerAristas() {
		HashSet<Arista> aristas = new HashSet<Arista>();

		// Creando Aristas
		int[] codigosAristas = ServiciosBD.getCodigosAristas();
		for (int codigo : codigosAristas) {
			String tipo = ServiciosBD.getTipoArista(codigo);
			// nodos = {origen, destino}
			int[] nodos = ServiciosBD.getNodosArista(codigo);
			aristas.add(new Arista(codigo, tipo, grafo.getNodo(nodos[0]), grafo
					.getNodo(nodos[1])));
		}

		// Asignando aristas al grafo.
		grafo.setAristas(aristas);

		// Asignando atributos a las aristas.
		for (int codigo : codigosAristas) {
			// Pido las propiedades de cada arista se los asigno a sus
			// atributos.
			grafo.getArista(codigo).setAtributos(
					ServiciosBD.getAtributosArista(codigo));
		}
	}

	private static void extraerNodos() {
		HashSet<Nodo> nodos = new HashSet<Nodo>();

		// Creando Nodos.
		int[] codigosNodos = ServiciosBD.getCodigosNodos(); // pidiendo codigos

		for (int codigo : codigosNodos) {
			String tipo = ServiciosBD.getTipoNodo(codigo);
			nodos.add(new Nodo(codigo, tipo));
		}

		// Asignando nodos al grafo
		grafo.setNodos(nodos);

		// Asignando atributos a todos los nodos.
		for (int codigo : codigosNodos) {
			// Pido propiedades de este nodo y se los asigno a sus atributos.
			grafo.getNodo(codigo).setAtributos(
					ServiciosBD.getAtributosNodo(codigo));
		}
	}

	public static boolean agregarNodo(int codigo, String tipo) {
		return grafo.agregarNodo(new Nodo(codigo, tipo));
	}

	public static boolean agregarArista(int codigo, String tipo, Nodo origen,
			Nodo destino) {
		return grafo.agregarArista(new Arista(codigo, tipo, origen, destino));
	}

	public static HashSet<Nodo> getNodos() {
		return grafo.getNodos();
	}

	public static HashSet<Arista> getAristas() {
		return grafo.getAristas();
	}

	public static void llenarMapaColores() {
		HashMap<String, ColorRGBA> coloresNodos = new HashMap<String, ColorRGBA>();
		HashMap<String, ColorRGBA> coloresAristas = new HashMap<String, ColorRGBA>();
		HashSet<Nodo> nodos = grafo.getNodos();
		HashSet<Arista> aristas = grafo.getAristas();

		for (Nodo nodo : nodos) {
			if (!(coloresNodos.containsKey(nodo.getTipo()))) {
				coloresNodos.put(nodo.getTipo(), ColorRGBA.randomColor());
			}
		}
		grafo.setColoresNodos(coloresNodos);
		asignarColoresNodos();

		for (Arista arista : aristas) {
			if (!(coloresAristas.containsKey(arista.getTipo()))) {
				coloresAristas.put(arista.getTipo(), ColorRGBA.randomColor());
			}
		}
		grafo.setColoresAristas(coloresAristas);
		asignarColoresAristas();
	}

	public static ColorRGBA getColorNodo(int codNodo) {
		for (Nodo nodo : grafo.getNodos()) {
			if (nodo.getCodigo() == codNodo) {
				return nodo.getColor();
			}
		}
		return null;
	}

	public static ColorRGBA getColorArista(int codArista) {
		for (Arista arista : grafo.getAristas()) {
			if (arista.getCodigo() == codArista) {
				return arista.getColor();
			}
		}
		return null;
	}

	public static HashMap<String, Object> getAtributosNodo(int codNodo) {
		HashSet<Nodo> nodos = grafo.getNodos();
		for (Nodo nodo : nodos) {
			if (nodo.getCodigo() == codNodo) {
				return nodo.getAtributos();
			}
		}
		return null;
	}

	public static HashMap<String, Object> getAtributosArista(int codArista) {
		HashSet<Arista> aristas = grafo.getAristas();
		for (Arista arista : aristas) {
			if (arista.getCodigo() == codArista) {
				return arista.getAtributos();
			}
		}
		return null;
	}

	public static String[] getTiposNodos() {
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		String[] llaves = new String[colores.size()];
		return (String[]) colores.keySet().toArray(llaves);
	}

	public static String[] getTiposAristas() {
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		String[] llaves = new String[colores.size()];
		return (String[]) colores.keySet().toArray(llaves);
	}

	public static ColorRGBA getColorTipoNodo(String codigoYtipo) {
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		String codigo = codigoYtipo.split(" ")[0];
		return colores.get(codigo);
	}

	public static ColorRGBA getColorTipoArista(String codigoYtipo) {
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		String codigo = codigoYtipo.split(" ")[0];
		return colores.get(codigo);
	}

	public static String getFiguraNodo(String codigoYtipo) {
		HashSet<Nodo> nodos = grafo.getNodos();
		String tipo = codigoYtipo.split(" ")[0];
		for (Nodo nodo : nodos) {
			if (nodo.getTipo().equalsIgnoreCase(tipo)) {
				return nodo.getFigura();
			}
		}
		return null;
	}

	public static void setFiguraNodos(String codigoYtipo, String figura) {
		HashSet<Nodo> nodos = grafo.getNodos();
		String tipo = codigoYtipo.split(" ")[0];
		for (Nodo nodo : nodos) {
			if (nodo.getTipo().equalsIgnoreCase(tipo)) {
				nodo.setFigura(figura);
			}
		}
	}

	public static void setColoresNodos(String codigoYtipo, ColorRGBA color) {
		String tipo = codigoYtipo.split(" ")[0];
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		colores.put(tipo, color);
		asignarColoresNodos();
	}

	private static void asignarColoresNodos() {
		HashSet<Nodo> nodos = grafo.getNodos();
		HashMap<String, ColorRGBA> coloresNodos = grafo.getColoresNodos();
		for (Nodo nodo : nodos) {
			nodo.setColor(coloresNodos.get(nodo.getTipo()));
		}
	}

	public static void setColoresAristas(String codigoYtipo, ColorRGBA color) {
		String tipo = codigoYtipo.split(" ")[0];
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		colores.put(tipo, color);
		asignarColoresAristas();
	}

	private static void asignarColoresAristas() {
		HashSet<Arista> aristas = grafo.getAristas();
		HashMap<String, ColorRGBA> coloresAristas = grafo.getColoresAristas();
		for (Arista arista : aristas) {
			arista.setColor(coloresAristas.get(arista.getTipo()));
		}
	}

	public static void llenarFiguras() {
		HashSet<Nodo> nodos = grafo.getNodos();
		for (Nodo nodo : nodos) {
			nodo.setFigura("Esfera");
		}
	}

	public static int guardarGrafo(File archivo) {

		try {
			if (archivo.exists()) {
				ObjectOutputStream salida = new ObjectOutputStream(
						new FileOutputStream(archivo));
				salida.writeObject(grafo);
				salida.close();
				return 1;
			} else {
				File file = new File(archivo.getAbsoluteFile() + ".grafo");
				ObjectOutputStream salida = new ObjectOutputStream(
						new FileOutputStream(file));
				salida.writeObject(grafo);
				salida.close();
			}

		} catch (IOException ex) {
			System.out.println(ex);
		}

		return 0;
	}

	public static boolean abrirGrafo(File archivo) {
		try {

			ObjectInputStream entrada = new ObjectInputStream(
					new FileInputStream(archivo));
			grafo = (Grafo) entrada.readObject();
			entrada.close();
			return true;
		} catch (IOException ex) {
			System.out.println(ex);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		return false;
	}
}

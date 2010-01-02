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
import java.util.Random;

import com.jme.renderer.ColorRGBA;

import bd.ServiciosBD;

import modelo.Arista;
import modelo.Grafo;
import modelo.Nodo;

public class Ctrl {
 
	private static Grafo grafo = null;
	private static Random random = new Random();

	/**
	 * Este método crea una conexión a la base de datos MySQL.
	 * 
	 * @param servidor
	 *            dirección ip.
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
	 * @return Retorna un arreglo de enteros correspondiente a los códigos de
	 *         identificación de cada uno de los nodos.
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
	 * @return Retorna un arreglo de enteros correspondiente a los códigos de
	 *         identificación de cada una de las aristas.
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
	 * @return Retorna el objeto grafo utilizado en la aplicación.
	 */
	public static Grafo getGrafo() {
		return grafo;
	}

	/**
	 * Este método es el encargado de crear un objeto grafo en el modelo de la
	 * aplicación.
	 * 
	 * @return true si se creó efectivamente el grafo. false en caso contrario.
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
	 * Este método se encarga de devolver un arreglo de 2 posiciones de los
	 * nodos que componen a una arísta específica.
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

	/**
	 * Este método se encarga de crear las aristas a partir de la información de la base de datos
	 * y luego asignarle la colección de aristas al grafo
	 * 
	 */
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

	/**
	 * Este método se encarga de crear los nodos a partir de la información de la base de datos
	 * y luego asignarle la colección de nodos al grafo
	 * 
	 */
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

	/**
	 * Este método agrega un nuevo nodo al grafo
	 * 
	 * @param codigo
	 * 		  		Código del nodo
	 * @param tipo
	 * 				El tipo del nodo
	 * @return true si el nodo se pudo agregar, false si el nodo no se agregó
	 */
	public static boolean agregarNodo(int codigo, String tipo) {
		return grafo.agregarNodo(new Nodo(codigo, tipo));
	}

	/**
	 * Este método agrega una nueva arista al grafo
	 * 
	 * @param codigo
	 * 		  		Código de la arista
	 * @param tipo
	 * 				El tipo de la arista
	 * @param origen
	 * 				Nodo origen
	 * @param destino
	 * 				Nodo destino
	 * @return true si el nodo se pudo agregar, false si el nodo no se agregó
	 */
	public static boolean agregarArista(int codigo, String tipo, Nodo origen,
			Nodo destino) {
		return grafo.agregarArista(new Arista(codigo, tipo, origen, destino));
	}

	/**
	 * Este método permite obtener los nodos del grafo
	 * @return HashSet<Nodo> con los nodos del grafo
	 */
	public static HashSet<Nodo> getNodos() {
		return grafo.getNodos();
	}

	/**
	 * Este método permite obtener las aristas del grafo
	 * @return HashSet<Nodo> con las aristas del grafo 
	 */
	public static HashSet<Arista> getAristas() {
		return grafo.getAristas();
	}

	/**
	 * Este método permite definir el color de cada tipo de aristas y nodos
	 */
	public static void llenarMapaColores() {
		HashMap<String, ColorRGBA> coloresNodos = new HashMap<String, ColorRGBA>();
		HashMap<String, ColorRGBA> coloresAristas = new HashMap<String, ColorRGBA>();
		HashSet<Nodo> nodos = grafo.getNodos();
		HashSet<Arista> aristas = grafo.getAristas();

		for (Nodo nodo : nodos) {
			if (!(coloresNodos.containsKey(nodo.getTipo()))) {
				//coloresNodos.put(nodo.getTipo(), ColorRGBA.randomColor());
				coloresNodos.put(nodo.getTipo(), new ColorRGBA(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1));
				//coloresNodos.put(nodo.getTipo(), new ColorRGBA((float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
				
			}
		}
		grafo.setColoresNodos(coloresNodos);
		asignarColoresNodos();

		for (Arista arista : aristas) {
			if (!(coloresAristas.containsKey(arista.getTipo()))) {
				//coloresAristas.put(arista.getTipo(), ColorRGBA.randomColor());
				coloresAristas.put(arista.getTipo(), new ColorRGBA(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1));
				//coloresAristas.put(arista.getTipo(), new ColorRGBA((float)Math.random(), (float)Math.random(), (float)Math.random(), 1));
			}
		}
		grafo.setColoresAristas(coloresAristas);
		asignarColoresAristas();
	}

	/**
	 * Este método permite obtener el color que tiene definido un nodo a través de su código
	 * 
	 * @param codNodo
	 * 				Codigo del nodo
	 * @return ColorRGBA con el color del nodo
	 */
	public static ColorRGBA getColorNodo(int codNodo) {
		for (Nodo nodo : grafo.getNodos()) {
			if (nodo.getCodigo() == codNodo) {
				return nodo.getColor();
			}
		}
		return null;
	}

	/**
	 * Este método permite obtener el color que tiene definido una arista a través de su código
	 * 
	 * @param codArista
	 * 				Codigo de la arista
	 * @return ColorRGBA con el color de la arista
	 */
	public static ColorRGBA getColorArista(int codArista) {
		for (Arista arista : grafo.getAristas()) {
			if (arista.getCodigo() == codArista) {
				return arista.getColor();
			}
		}
		return null;
	}

	/**
	 * Este método permite obtener una colección con los atributos que posee un nodo determinado. 
	 * La información se obtiene a través del código del nodo
	 * 
	 * @param codNodo
	 * 				Código del nodo
	 * @return HashMap<String, Object> con los atributos del nodo
	 */
	public static HashMap<String, Object> getAtributosNodo(int codNodo) {
		HashSet<Nodo> nodos = grafo.getNodos();
		for (Nodo nodo : nodos) {
			if (nodo.getCodigo() == codNodo) {
				return nodo.getAtributos();
			}
		}
		return null;
	}

	/**
	 * Este método permite obtener una colección con los atributos que posee una arista determinada. 
	 * La información se obtiene a través del código de la arista
	 * 
	 * @param codArista
	 * 				Código de la arista
	 * @return HashMap<String, Object> con los atributos de la arista 
	 */
	public static HashMap<String, Object> getAtributosArista(int codArista) {
		HashSet<Arista> aristas = grafo.getAristas();
		for (Arista arista : aristas) {
			if (arista.getCodigo() == codArista) {
				return arista.getAtributos();
			}
		}
		return null;
	}

	/**
	 * Este método permite obtener un arreglo con los nombres de los tipos de nodos que hay en el grafo
	 *  
	 * @return String[] con el los tipos de nodos que existen en el grafo
	 */
	public static String[] getTiposNodos() {
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		String[] llaves = new String[colores.size()];
		return (String[]) colores.keySet().toArray(llaves);
	}

	/**
	 * Este método permite obtener un arreglo con los nombres de los tipos de aristas que hay en el grafo
	 *  
	 * @return String[] con el los tipos de aristas que existen en el grafo
	 */
	public static String[] getTiposAristas() {
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		String[] llaves = new String[colores.size()];
		return (String[]) colores.keySet().toArray(llaves);
	}

	/**
	 * Este método permite obtener el color del tipo de un nodo de acuerdo a su código
	 * 
	 * @param codigoYtipo
	 * 					String con el código y el tipo del nodo
	 * @return ColorRGBA del tipo de nodo
	 */
	public static ColorRGBA getColorTipoNodo(String codigoYtipo) {
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		String codigo = codigoYtipo.split(" ")[0];
		return colores.get(codigo);
	}

	/**
	 * Este método permite obtener el color del tipo de una arista de acuerdo a su código
	 * 
	 * @param codigoYtipo
	 * 					String con el código y el tipo de la arista
	 * @return ColorRGBA del tipo de arista 
	 */
	public static ColorRGBA getColorTipoArista(String codigoYtipo) {
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		String codigo = codigoYtipo.split(" ")[0];
		return colores.get(codigo);
	}

	/**
	 * Este método permite obtener la figura del tipo de un nodo de acuerdo a su código
	 * 
	 * @param codigoYtipo
	 * 					String con el código y el tipo del nodo
	 * @return String con el nombre de la figura del nodo 
	 */
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

	/**
	 * Este método define una nueva figura a los nodos de un mismo tipo al que se desea cambiar
	 * 
	 * @param codigoYtipo
	 * 					String con el código y el tipo del nodo
	 * @param figura
	 * 				Nombre de la nueva figura 
	 */
	public static void setFiguraNodos(String codigoYtipo, String figura) {
		HashSet<Nodo> nodos = grafo.getNodos();
		String tipo = codigoYtipo.split(" ")[0];
		for (Nodo nodo : nodos) {
			if (nodo.getTipo().equalsIgnoreCase(tipo)) {
				nodo.setFigura(figura);
			}
		}
	}

	/**
	 * Este método define un nuevo color a los nodos de un mismo tipo al que se desea cambiar
	 * 
	 * @param codigoYtipo
	 * 					String con el código y el tipo del nodo
	 * @param color
	 * 				Nuevo color
	 */
	public static void setColoresNodos(String codigoYtipo, ColorRGBA color) {
		String tipo = codigoYtipo.split(" ")[0];
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		colores.put(tipo, color);
		asignarColoresNodos();
	}

	/**
	 * Este método permite asignarle el color a todos los nodos de acuerdo a su propio tipo 
	 */
	private static void asignarColoresNodos() {
		HashSet<Nodo> nodos = grafo.getNodos();
		HashMap<String, ColorRGBA> coloresNodos = grafo.getColoresNodos();
		for (Nodo nodo : nodos) {
			nodo.setColor(coloresNodos.get(nodo.getTipo()));
		}
	}

	/**
	 * Este método define un nuevo color a las aristas de un mismo tipo al que se desea cambiar
	 * 
	 * @param codigoYtipo
	 * 					String con el código y el tipo de la arista
	 * @param color
	 * 				Nuevo color 
	 */
	public static void setColoresAristas(String codigoYtipo, ColorRGBA color) {
		String tipo = codigoYtipo.split(" ")[0];
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		colores.put(tipo, color);
		asignarColoresAristas();
	}

	/**
	 * Este método permite asignarle el color a todas las aristas de acuerdo a su propio tipo
	 */
	private static void asignarColoresAristas() {
		HashSet<Arista> aristas = grafo.getAristas();
		HashMap<String, ColorRGBA> coloresAristas = grafo.getColoresAristas();
		for (Arista arista : aristas) {
			arista.setColor(coloresAristas.get(arista.getTipo()));
		}
	}

	/**
	 * Este método permite definir una figura inicial a todos los nodos. Por default se definen como esferas
	 */
	public static void llenarFiguras() {
		HashSet<Nodo> nodos = grafo.getNodos();
		for (Nodo nodo : nodos) {
			nodo.setFigura("Esfera");
		}
	}

	/**
	 * Este método permite guardar la información del grafo en un archivo, para poder ser reutilizado y para
	 * dar persistencia a los cambio realizados en el grafo
	 * 
	 * @param archivo
	 * 				ruta donde se guardará el archivo
	 * @return 1 si se guardó con éxito, 0 si se produjo algún error 
	 */
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
				return 1;
			}

		} catch (IOException ex) {
			System.out.println(ex);
		}

		return 0;
	}

	/**
	 * Este método permite abrir y leer un grafo que este almacenado
	 * 
	 * @param archivo
	 * 				ruta del archivo que se desea abrir
	 * @return true si se logró leer el archivo, false si se presentó algún error
	 */
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

package ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
		/*
		 * int[] codigos = new int[grafo.getNodos().size()]; int cont = 0; for
		 * (Iterator<Nodo> i = grafo.getNodos().iterator(); i.hasNext(); cont++)
		 * { codigos[cont] = i.next().getCodigo(); } return codigos;
		 */

		int[] codigos = new int[grafo.getNodos().size()];
		ArrayList<Nodo> nodos = reordenarNodos();

		for (int i = 0; i < nodos.size(); i++) {
			codigos[i] = nodos.get(i).getCodigo();
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

	/**
	 * Este m�todo se encarga de crear las aristas a partir de la informaci�n de
	 * la base de datos y luego asignarle la colecci�n de aristas al grafo
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
	 * Este m�todo se encarga de crear los nodos a partir de la informaci�n de
	 * la base de datos y luego asignarle la colecci�n de nodos al grafo
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
	 * Este m�todo agrega un nuevo nodo al grafo
	 * 
	 * @param codigo
	 *            C�digo del nodo
	 * @param tipo
	 *            El tipo del nodo
	 * @return true si el nodo se pudo agregar, false si el nodo no se agreg�
	 */
	public static boolean agregarNodo(int codigo, String tipo) {
		return grafo.agregarNodo(new Nodo(codigo, tipo));
	}

	/**
	 * Este m�todo agrega una nueva arista al grafo
	 * 
	 * @param codigo
	 *            C�digo de la arista
	 * @param tipo
	 *            El tipo de la arista
	 * @param origen
	 *            Nodo origen
	 * @param destino
	 *            Nodo destino
	 * @return true si el nodo se pudo agregar, false si el nodo no se agreg�
	 */
	public static boolean agregarArista(int codigo, String tipo, Nodo origen,
			Nodo destino) {
		return grafo.agregarArista(new Arista(codigo, tipo, origen, destino));
	}

	/**
	 * Este m�todo permite obtener los nodos del grafo
	 * 
	 * @return HashSet<Nodo> con los nodos del grafo
	 */
	public static HashSet<Nodo> getNodos() {
		return grafo.getNodos();
	}

	/**
	 * Este m�todo permite obtener las aristas del grafo
	 * 
	 * @return HashSet<Nodo> con las aristas del grafo
	 */
	public static HashSet<Arista> getAristas() {
		return grafo.getAristas();
	}

	/**
	 * Este m�todo permite definir el color de cada tipo de aristas y nodos
	 */
	public static void llenarMapaColores() {
		HashMap<String, ColorRGBA> coloresNodos = new HashMap<String, ColorRGBA>();
		HashMap<String, ColorRGBA> coloresAristas = new HashMap<String, ColorRGBA>();
		HashSet<Nodo> nodos = grafo.getNodos();
		HashSet<Arista> aristas = grafo.getAristas();

		for (Nodo nodo : nodos) {
			if (!(coloresNodos.containsKey(nodo.getTipo()))) {
				// coloresNodos.put(nodo.getTipo(), ColorRGBA.randomColor());
				coloresNodos.put(nodo.getTipo(),
						new ColorRGBA(random.nextFloat(), random.nextFloat(),
								random.nextFloat(), 1));
				// coloresNodos.put(nodo.getTipo(), new
				// ColorRGBA((float)Math.random(), (float)Math.random(),
				// (float)Math.random(), 1));

			}
		}
		grafo.setColoresNodos(coloresNodos);
		asignarColoresNodos();

		for (Arista arista : aristas) {
			if (!(coloresAristas.containsKey(arista.getTipo()))) {
				// coloresAristas.put(arista.getTipo(),
				// ColorRGBA.randomColor());
				coloresAristas.put(arista.getTipo(),
						new ColorRGBA(random.nextFloat(), random.nextFloat(),
								random.nextFloat(), 1));
				// coloresAristas.put(arista.getTipo(), new
				// ColorRGBA((float)Math.random(), (float)Math.random(),
				// (float)Math.random(), 1));
			}
		}
		grafo.setColoresAristas(coloresAristas);
		asignarColoresAristas();
	}

	/**
	 * Este m�todo permite obtener el color que tiene definido un nodo a trav�s
	 * de su c�digo
	 * 
	 * @param codNodo
	 *            Codigo del nodo
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
	 * Este m�todo permite obtener el color que tiene definido una arista a
	 * trav�s de su c�digo
	 * 
	 * @param codArista
	 *            Codigo de la arista
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
	 * Este m�todo permite obtener una colecci�n con los atributos que posee un
	 * nodo determinado. La informaci�n se obtiene a trav�s del c�digo del nodo
	 * 
	 * @param codNodo
	 *            C�digo del nodo
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
	 * Este m�todo permite obtener una colecci�n con los atributos que posee una
	 * arista determinada. La informaci�n se obtiene a trav�s del c�digo de la
	 * arista
	 * 
	 * @param codArista
	 *            C�digo de la arista
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
	 * Este m�todo permite obtener un arreglo con los nombres de los tipos de
	 * nodos que hay en el grafo
	 * 
	 * @return String[] con el los tipos de nodos que existen en el grafo
	 */
	public static String[] getTiposNodos() {
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		String[] llaves = new String[colores.size()];
		return (String[]) colores.keySet().toArray(llaves);
	}

	/**
	 * Este m�todo permite obtener un el tipo de una aristas determinada
	 * 
	 * @param codigo
	 *            : C�digo de la arista
	 * @return String
	 */
	public static String getTipoArista(int codigo) {
		String tipo= ServiciosBD.getTipoArista(codigo);
		if(tipo == null){
			for(Arista arista: grafo.getAristas()){
				if(arista.getCodigo()==codigo){
					return arista.getTipo();
				}
			}
		}
		return tipo;
		// return ServiciosBD.getTipoArista(codigo);
	}

	/**
	 * Este m�todo permite obtener un el tipo de un nodo determinado
	 * 
	 * @param codigo
	 *            : C�digo del nodo
	 * @return String
	 */
	public static String getTipoNodo(int codigo) {
		String tipo= ServiciosBD.getTipoNodo(codigo);
		if(tipo == null){
			for(Nodo nodo: grafo.getNodos()){
				if(nodo.getCodigo()==codigo){
					return nodo.getTipo();
				}
			}
		}
		return tipo;
		//return ServiciosBD.getTipoNodo(codigo);
	}

	/**
	 * Este m�todo permite obtener un arreglo con los nombres de los tipos de
	 * aristas que hay en el grafo
	 * 
	 * @return String[] con el los tipos de aristas que existen en el grafo
	 */
	public static String[] getTiposAristas() {
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		String[] llaves = new String[colores.size()];
		return (String[]) colores.keySet().toArray(llaves);
	}

	/**
	 * Este m�todo permite obtener el color del tipo de un nodo de acuerdo a su
	 * c�digo
	 * 
	 * @param codigoYtipo
	 *            String con el c�digo y el tipo del nodo
	 * @return ColorRGBA del tipo de nodo
	 */
	public static ColorRGBA getColorTipoNodo(String codigoYtipo) {
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		String codigo = codigoYtipo.split(" ")[0];
		return colores.get(codigo);
	}

	/**
	 * Este m�todo permite obtener el color del tipo de una arista de acuerdo a
	 * su c�digo
	 * 
	 * @param codigoYtipo
	 *            String con el c�digo y el tipo de la arista
	 * @return ColorRGBA del tipo de arista
	 */
	public static ColorRGBA getColorTipoArista(String codigoYtipo) {
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		String codigo = codigoYtipo.split(" ")[0];
		return colores.get(codigo);
	}

	/**
	 * Este m�todo permite obtener la figura del tipo de un nodo de acuerdo a su
	 * c�digo
	 * 
	 * @param codigoYtipo
	 *            String con el c�digo y el tipo del nodo
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
	 * Este m�todo define una nueva figura a los nodos de un mismo tipo al que
	 * se desea cambiar
	 * 
	 * @param codigoYtipo
	 *            String con el c�digo y el tipo del nodo
	 * @param figura
	 *            Nombre de la nueva figura
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
	 * Este m�todo define un nuevo color a los nodos de un mismo tipo al que se
	 * desea cambiar
	 * 
	 * @param codigoYtipo
	 *            String con el c�digo y el tipo del nodo
	 * @param color
	 *            Nuevo color
	 */
	public static void setColoresNodos(String codigoYtipo, ColorRGBA color) {
		String tipo = codigoYtipo.split(" ")[0];
		HashMap<String, ColorRGBA> colores = grafo.getColoresNodos();
		colores.put(tipo, color);
		asignarColoresNodos();
	}

	/**
	 * Este m�todo permite asignarle el color a todos los nodos de acuerdo a su
	 * propio tipo
	 */
	private static void asignarColoresNodos() {
		HashSet<Nodo> nodos = grafo.getNodos();
		HashMap<String, ColorRGBA> coloresNodos = grafo.getColoresNodos();
		for (Nodo nodo : nodos) {
			nodo.setColor(coloresNodos.get(nodo.getTipo()));
		}
	}

	/**
	 * Este m�todo define un nuevo color a las aristas de un mismo tipo al que
	 * se desea cambiar
	 * 
	 * @param codigoYtipo
	 *            String con el c�digo y el tipo de la arista
	 * @param color
	 *            Nuevo color
	 */
	public static void setColoresAristas(String codigoYtipo, ColorRGBA color) {
		String tipo = codigoYtipo.split(" ")[0];
		HashMap<String, ColorRGBA> colores = grafo.getColoresAristas();
		colores.put(tipo, color);
		asignarColoresAristas();
	}

	/**
	 * Este m�todo permite asignarle el color a todas las aristas de acuerdo a
	 * su propio tipo
	 */
	private static void asignarColoresAristas() {
		HashSet<Arista> aristas = grafo.getAristas();
		HashMap<String, ColorRGBA> coloresAristas = grafo.getColoresAristas();
		for (Arista arista : aristas) {
			arista.setColor(coloresAristas.get(arista.getTipo()));
		}
	}

	/**
	 * Este m�todo permite definir una figura inicial a todos los nodos. Por
	 * default se definen como esferas
	 */
	public static void llenarFiguras() {
		HashSet<Nodo> nodos = grafo.getNodos();
		for (Nodo nodo : nodos) {
			nodo.setFigura("Esfera");
		}
	}

	/**
	 * Este m�todo permite guardar la informaci�n del grafo en un archivo, para
	 * poder ser reutilizado y para dar persistencia a los cambio realizados en
	 * el grafo
	 * 
	 * @param archivo
	 *            ruta donde se guardar� el archivo
	 * @return 1 si se guard� con �xito, 0 si se produjo alg�n error
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
	 * Este m�todo permite abrir y leer un grafo que este almacenado
	 * 
	 * @param archivo
	 *            ruta del archivo que se desea abrir
	 * @return true si se logr� leer el archivo, false si se present� alg�n
	 *         error
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

	/**
	 * Este m�todo permite reordenar los nodos para que los nodos que tienen
	 * conexiones entre s� queden m�s cerca en el momento de ser graficadas
	 * 
	 * @return ArrayList<nodo> con los nodos organizados
	 */
	private static ArrayList<Nodo> reordenarNodos() {
		ArrayList<Nodo> nodosRet = new ArrayList<Nodo>(grafo.getNodos().size());
		for (Arista aristaA : grafo.getAristas()) {
			if (!nodosRet.contains(aristaA.getOrigen())) {
				nodosRet.add(aristaA.getOrigen());
			}
			if (!nodosRet.contains(aristaA.getDestino())) {
				nodosRet.add(aristaA.getDestino());
			}
			for (Arista aristaB : grafo.getAristas()) {
				if (aristaA.getOrigen() == aristaB.getDestino()
						&& !nodosRet.contains(aristaB.getOrigen())) {
					nodosRet.add(aristaB.getOrigen());
				}
				if (aristaA.getOrigen() == aristaB.getOrigen()
						&& !nodosRet.contains(aristaB.getDestino())) {
					nodosRet.add(aristaB.getDestino());
				}
			}
		}
		return nodosRet;
	}

}

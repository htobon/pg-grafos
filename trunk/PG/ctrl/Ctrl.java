package ctrl;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import bd.ServiciosBD;

import modelo.Arista;
import modelo.Grafo;
import modelo.Nodo;

public class Ctrl {

	private static Grafo grafo = new Grafo();

	public static boolean probarConexion(String servidor, String puerto,
			String usuario, char[] clave, String nombreBD) {
		return ServiciosBD.crearConexion(servidor, puerto, usuario, clave,
				nombreBD);
	}
	
	public static int[] getCodigosNodos() {
		int[] codigos = new int[grafo.getNodos().size()];
		int cont=0;
		for(Iterator<Nodo> i = grafo.getNodos().iterator(); i.hasNext(); cont++) {
			codigos[cont] = i.next().getCodigo();
		}
		return codigos;
	}

	public static Grafo getGrafo() {
		return grafo;
	}

	public static boolean crearGrafo() {
		grafo = new Grafo();
		extraerNodos();
		extraerAristas();
		return true;
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
		for(int codigo : codigosAristas) {
			// Pido las propiedades de cada arista se los asigno a sus atributos.
			grafo.getArista(codigo).setAtributos(ServiciosBD.getAtributosArista(codigo));
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
			grafo.getNodo(codigo)
					.setAtributos(ServiciosBD.getAtributosNodo(codigo));
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
	
	public static void llenarMapaColores(){
		HashMap<String,Color> coloresNodos= new HashMap<String, Color>();	
		HashMap<String,Color> coloresAristas= new HashMap<String, Color>();
		HashSet<Nodo> nodos= grafo.getNodos();
		HashSet<Arista> aristas= grafo.getAristas();
		Random r =  new Random();
		
		for (Nodo nodo : nodos) {
			if(!(coloresNodos.containsKey(nodo.getTipo()))){
				coloresNodos.put(nodo.getTipo(), new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			}
		}
		grafo.setColoresNodos(coloresNodos);
		
		for (Arista arista : aristas) {
			if(!(coloresAristas.containsKey(arista.getTipo()))){
				coloresAristas.put(arista.getTipo(), new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			}
		}
		grafo.setColoresAristas(coloresAristas);
	}
	
	public void asignarColoresNodos(){
		HashSet<Nodo> nodos= grafo.getNodos();
		HashMap<String,Color> coloresNodos= new HashMap<String, Color>();	
		for (Nodo nodo : nodos) {
			nodo.setColor(coloresNodos.get(nodo.getTipo()));
		}
	}
	
	public void asignarColoresAristas(){
		HashSet<Arista> aristas= grafo.getAristas();
		HashMap<String,Color> coloresAristas= new HashMap<String, Color>();	
		for (Arista arista : aristas) {
			arista.setColor(coloresAristas.get(arista.getTipo()));
		}
	}
}

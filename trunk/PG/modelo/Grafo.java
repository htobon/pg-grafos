package modelo;

   
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import com.jme.renderer.ColorRGBA;
 
public class Grafo implements Serializable{

	private HashSet<Nodo> nodos;
	private HashSet<Arista> aristas;
	private HashMap<String,ColorRGBA> coloresNodos;
	private HashMap<String,ColorRGBA> coloresAristas;
	
	/**
	 * Método para obtener el color de los nodos
	 * @return  HashMap<String, ColorRGBA> con el tipo del nodo como clave y el color como valor
	 */
	public HashMap<String, ColorRGBA> getColoresNodos() {
		return coloresNodos;
	}

	/**
	 * Método para asignar la colección con los colores de los nodos a la propiedad del grafo
	 * @param coloresNodos
	 * 					Colección con los colores de los nodos
	 */
	public void setColoresNodos(HashMap<String, ColorRGBA> coloresNodos) {
		this.coloresNodos = coloresNodos;
	}

	/**
	 * * Método para obtener el color de las aristas
	 * @return  HashMap<String, ColorRGBA> con el tipo de arista como clave y el color como valor 
	 */
	public HashMap<String, ColorRGBA> getColoresAristas() {
		return coloresAristas;
	}

	/**
	* Método para asignar la colección con los colores de las aristas a la propiedad del grafo
	 * @param coloresAristas
	 * 					Colección con los colores de las aristas 
	 */
	public void setColoresAristas(HashMap<String, ColorRGBA> coloresAristas) {
		this.coloresAristas = coloresAristas;
	}

	/**
	 * Constructor de la clase Grafo para crear un grafo con nodos y aristas 
	 */
	public Grafo() {
		nodos = new HashSet<Nodo>();
		aristas = new HashSet<Arista>();
	}

	/**
	 * Método que permite agregar una arista a la colección de aristas
	 * @param arista
	 * 				Arista a agregar
	 * @return  true si se agregó la arista, false si hubo algún error
	 */
	public boolean agregarArista(Arista arista) {
		return aristas.add(arista);
	}

	/**
	 * Método que permite agregar un nodo a la colección de nodos
	 * @param nodo
	 * 				Nodo a agregar
	 * @return  true si se agregó el nodo, false si hubo algún error
	 */
	public boolean agregarNodo(Nodo nodo) {
		return nodos.add(nodo);
	}

	/**
	 * Método para obtener la colección de nodos del grafo
	 * @return HashSet<Nodo> colección con los nodos del grafo 
	 */
	public HashSet<Nodo> getNodos() {
		return nodos;
	}

	/**
	 * Método para obtener la colección de aristas del grafo
	 * @return HashSet<Arista> colección con las aristas del grafo  
	 */
	public HashSet<Arista> getAristas() {
		return aristas;
	}

	/**
	 * Método para retornar un Nodo dado un código
	 * @param codigo
	 * 				Código del nodo
	 * @return  El nodo correspondiente al código por parámetro
	 */
	public Nodo getNodo(int codigo) {
		for (Nodo nodo : nodos) {
			if (nodo.getCodigo() == codigo)
				return nodo;
		}
		return null;
	}

	/**
	 * Método para asignar la colección de nodos 
	 * @param  nodos
	 */
	public void setNodos(HashSet<Nodo> nodos) {
		this.nodos = nodos;

	}

	/**
	 * Método para asignar la colección de aristas 
	 * @param  aristas
	 */
	public void setAristas(HashSet<Arista> aristas) {
		this.aristas = aristas;

	}

	/**
	 * Método para obtener una arista dado un código
	 *  @param codigo
	 * 				Código de la arista
	 * @return  La arista correspondiente al código por parámetro
	 */
	public Arista getArista(int codigo) {
		for (Arista arista : aristas) {
			if (arista.getCodigo() == codigo)
				return arista;
		}
		return null;
	}

}

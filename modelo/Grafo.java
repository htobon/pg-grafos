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
	
	public HashMap<String, ColorRGBA> getColoresNodos() {
		return coloresNodos;
	}

	public void setColoresNodos(HashMap<String, ColorRGBA> coloresNodos) {
		this.coloresNodos = coloresNodos;
	}

	public HashMap<String, ColorRGBA> getColoresAristas() {
		return coloresAristas;
	}

	public void setColoresAristas(HashMap<String, ColorRGBA> coloresAristas) {
		this.coloresAristas = coloresAristas;
	}

	public Grafo() {
		nodos = new HashSet<Nodo>();
		aristas = new HashSet<Arista>();
	}

	public boolean agregarArista(Arista arista) {
		return aristas.add(arista);
	}

	public boolean agregarNodo(Nodo nodo) {
		return nodos.add(nodo);
	}

	public HashSet<Nodo> getNodos() {
		return nodos;
	}

	public HashSet<Arista> getAristas() {
		return aristas;
	}

	public Nodo getNodo(int codigo) {
		for (Nodo nodo : nodos) {
			if (nodo.getCodigo() == codigo)
				return nodo;
		}
		return null;
	}

	public void setNodos(HashSet<Nodo> nodos) {
		this.nodos = nodos;

	}

	public void setAristas(HashSet<Arista> aristas) {
		this.aristas = aristas;

	}

	public Arista getArista(int codigo) {
		for (Arista arista : aristas) {
			if (arista.getCodigo() == codigo)
				return arista;
		}
		return null;
	}

}

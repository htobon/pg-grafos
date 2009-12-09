package modelo;

import java.util.HashSet;

public class Grafo {

	private HashSet<Nodo> nodos;
	private HashSet<Arista> aristas;

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

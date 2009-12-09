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
	
}

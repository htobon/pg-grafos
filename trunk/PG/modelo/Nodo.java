package modelo;

import java.util.*;

public class Nodo {
	private Map<String, Object> atributos;
	private String nombre;
	
	public Nodo(String nombre) {
		this.nombre = nombre;
		atributos = new HashMap<String, Object>();
	}
}

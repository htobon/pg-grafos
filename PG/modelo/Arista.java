package modelo;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Arista {
	private int codigo;
	private String tipo;
	private Nodo origen, destino;
	private Color color;
	private HashMap<String, Object> atributos;

	public Arista(int codigo, String tipo, Nodo origen, Nodo destino) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
		atributos = new HashMap<String, Object>();
	}

	public String getTipo() {
		return tipo;
	}

	public int getCodigo() {
		return codigo;
	}

	public Nodo getOrigen() {
		return origen;
	}

	public Nodo getDestino() {
		return destino;
	}

	public HashMap<String, Object> getAtributos() {
		return atributos;
	}

	public void agregarAtributo(String nombreAtributo, Object valor) {
		atributos.put(nombreAtributo, valor);
	}

	public void setAtributos(HashMap<String, Object> atributos) {
		this.atributos = atributos;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
}

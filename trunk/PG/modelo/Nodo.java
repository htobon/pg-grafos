package modelo;

import java.awt.Color;
import java.util.*;

public class Nodo {
	private Map<String, Object> atributos;
	private String tipo;
	private int codigo;
	private Color color;
	
	public Nodo(int codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
		atributos = new HashMap<String, Object>();
	}
	
	public void setAtributo(HashMap<String, Object> atributos) {
		this.atributos = atributos;
	}
	
	public void agregarAtributo(String nombreAtributo, Object valor) {
		atributos.put(nombreAtributo, valor);
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public Map<String, Object> getAtributos() {
		return atributos;
	}

	public void setAtributos(HashMap<String, Object> atributos) {
		this.atributos = atributos;
		
	}
	
	public void setColor(Color color){
		this.color=color;
	}
	
	public Color getColor(){
		return color;
	}
	
	
}

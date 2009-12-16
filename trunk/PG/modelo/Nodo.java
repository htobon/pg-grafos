package modelo;


import java.io.Serializable;
import java.util.*;

import com.jme.renderer.ColorRGBA;

public class Nodo implements Serializable {
	private HashMap<String, Object> atributos;
	private String tipo;
	private int codigo;
	private ColorRGBA color;
	private String figura;
	
	public String getFigura() {
		return figura;
	}

	public void setFigura(String figura) {
		this.figura = figura;
	}

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
	
	public HashMap<String, Object> getAtributos() {
		return atributos;
	}

	public void setAtributos(HashMap<String, Object> atributos) {
		this.atributos = atributos;
		
	}
	
	public void setColor(ColorRGBA color){
		this.color=color;
	}
	
	public ColorRGBA getColor(){
		return color;
	}
	
	
}

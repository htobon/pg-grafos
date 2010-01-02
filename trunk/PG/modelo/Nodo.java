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
	
	/**
	 * Método para obtener la figura del nodo
	 * @return  String con la figura del nodo  
	 */
	public String getFigura() {
		return figura;
	}

	/**
	 * Métodos para definir la figura del nodo
	 * @param figura
	 */
	public void setFigura(String figura) {
		this.figura = figura;
	}

	/**
	 * Método constructor de la clase Nodo. Permite crear un objeto nodo
	 * @param codigo
	 * 				código del nodo
	 * @param tipo
	 * 				tipo de nodo
	 */
	public Nodo(int codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
		atributos = new HashMap<String, Object>();
	}
	
	/**
	 * Métodos para definir la colección de atributos del nodo
	 * @param atributos
	 */
	public void setAtributo(HashMap<String, Object> atributos) {
		this.atributos = atributos;
	}
	
	/**
	 * Método para agregar un atributo a la colección de atributos del nodo
	 * @param nombreAtributo
	 * @param valor
	 */
	public void agregarAtributo(String nombreAtributo, Object valor) {
		atributos.put(nombreAtributo, valor);
	}
	
	/**
	 * Método para obtener el codigo del nodo
	 * @return  int con el codigo del nodo  
	 */
	public int getCodigo() {
		return codigo;
	}
	
	/**
	 * Método para obtener el tipo del nodo
	 * @return  String con el tipo del nodo
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Método para obtener los atributos del nodo
	 * @return  HashMap<String, Object> con los atributos del nodo
	 */
	public HashMap<String, Object> getAtributos() {
		return atributos;
	}

	/**
	 * Métodos para definir la colección de atributos del nodo
	 * @param atributos
	 */
	public void setAtributos(HashMap<String, Object> atributos) {
		this.atributos = atributos;
		
	}
	
	/**
	 * Métodos para definir el color del nodo
	 * @param color
	 */
	public void setColor(ColorRGBA color){
		this.color=color;
	}
	
	/**
	 * Método para obtener el color del nodo
	 * @return el color definido para el tipo de nodo
	 */
	public ColorRGBA getColor(){
		return color;
	}
	
	
}

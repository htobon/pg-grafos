package modelo;


import java.io.Serializable;
import java.util.HashMap;


import com.jme.renderer.ColorRGBA;
 
public class Arista implements Serializable {
	private int codigo;
	private String tipo;
	private Nodo origen, destino;
	private ColorRGBA color;
	private HashMap<String, Object> atributos;

	/**
	 * Método constructor de la clase Arista. Permite crear un objeto arista
	 * @param codigo
	 * 				código de la arista
	 * @param tipo
	 * 				tipo de arista
	 * @param origen
	 * 				nodo origen
	 * @param destino
	 * 				nodo destino
	 */
	public Arista(int codigo, String tipo, Nodo origen, Nodo destino) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
		atributos = new HashMap<String, Object>();
	}

	/**
	 * Método para obtener el tipo de la arista
	 * @return  String con el tipo de arista
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para obtener el codigo de la arista
	 * @return  int con el codigo de la arista  
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Método para obtener el nodo origen de la arista
	 * @return  Nodo origen de la arista 
	 */
	public Nodo getOrigen() {
		return origen;
	}

	/**
	 * Método para obtener el nodo destino de la arista
	 * @return  Nodo destino de la arista  
	 */
	public Nodo getDestino() {
		return destino;
	}

	/**
	 * Método para obtener los atributos de la arista
	 * @return  HashMap<String, Object> con los atributos de la arista
	 */
	public HashMap<String, Object> getAtributos() {
		return atributos;
	}

	/**
	 * Método para agregar un atributo a la colección de atributos de la arista
	 * @param nombreAtributo
	 * @param valor
	 */
	public void agregarAtributo(String nombreAtributo, Object valor) {
		atributos.put(nombreAtributo, valor);
	}

	/**
	 * Métodos para definir la colección de atributos de la arista
	 * @param atributos
	 */
	public void setAtributos(HashMap<String, Object> atributos) {
		this.atributos = atributos;
	}

	/**
	 * Métodos para definir el color de la arista
	 * @param color
	 */
	public void setColor(ColorRGBA color) {
		this.color = color;
	}
	
	/**
	 * Método para obtener el color de la arista
	 * @return el color definido para el tipo de arista
	 */
	public ColorRGBA getColor(){
		return color;
	}

}

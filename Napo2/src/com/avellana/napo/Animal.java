package com.avellana.napo;

/**
 * Clase modelo que almacena nombre del animal y la ID del recurso de la foto en
 * el sistema.
 * 
 * @author ProyectoSimio
 * 
 */
public class Animal {
	private String nombre;
	private int drawableImageID;

	public Animal(String nombre, int drawableImageID) {
		this.nombre = nombre;
		this.drawableImageID = drawableImageID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDrawableImageID() {
		return drawableImageID;
	}

	public void setDrawableImageID(int drawableImageID) {
		this.drawableImageID = drawableImageID;
	}

}

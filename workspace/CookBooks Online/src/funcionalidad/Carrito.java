package funcionalidad;

import java.util.LinkedList;
import java.util.Random;

public class Carrito {
	// TODO super mock
	private LinkedList<Libro> libros;

	public Carrito() {
		super();
		libros = new LinkedList<Libro>();
	}

	public LinkedList<Libro> getLibros() {
		return libros;
	}
	
	public void agregar(Libro unLibro){
		libros.add(unLibro);
	}

	public void eliminar(Libro unLibro){
		libros.remove(unLibro);
	}
	
	public Boolean contiene(Libro unLibro){
		return libros.contains(unLibro);
	}
	
	public Integer getCosto(){
		return 0;
	}
}

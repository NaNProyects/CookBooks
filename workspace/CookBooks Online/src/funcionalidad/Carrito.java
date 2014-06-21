package funcionalidad;

import java.util.LinkedList;

public class Carrito {
	private LinkedList<Libro> libros;

	public Carrito() {
		libros = new LinkedList<Libro>();
	}

	public LinkedList<Libro> getLibros() {
		return libros;
	}

	public boolean agregar(Libro unLibro) {
		if (!this.contiene(unLibro)) {
			libros.add(unLibro);
			return true;
		} else {
			return false;
		}
	}

	public void eliminar(Libro unLibro) {
		libros.remove(unLibro);
	}

	public Boolean contiene(Libro unLibro) {
		return libros.contains(unLibro);
	}

	public Double getCosto() {
		double total = 0;
		for (Libro l : libros) {
			total += l.getPrecio();
		}
		return total;
	}
}

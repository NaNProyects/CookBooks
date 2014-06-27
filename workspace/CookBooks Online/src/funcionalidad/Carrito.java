package funcionalidad;

import java.sql.SQLException;
import java.util.LinkedList;

import utilsSQL.Conector;

public class Carrito {
	private LinkedList<Libro> libros;

	public Carrito() {
		libros = new LinkedList<Libro>();
	}

	public LinkedList<Libro> getLibros() {
		return libros;
	}

	public void agregar(Libro unLibro) {
			libros.add(unLibro);
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
		return redondeo(total);
	}
	
	private Double redondeo(double num) {
		return ((double) Math.round(num*100)) / 100;
	}

	public void vaciar() {
		libros = new LinkedList<Libro>();
	}
	
	public Pedido guardarEn (Conector base, Usuario user) throws SQLException {
		Pedido result = new Pedido(libros, user);
		result.guardarEn(base);
		this.vaciar();
		return result;
	}
}

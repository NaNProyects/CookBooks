package funcionalidad;

import java.sql.SQLException;
import java.util.Date;
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

	boolean agregar(Libro unLibro) {
//		if (!this.contiene(unLibro)) { FIXME carrito agregar
			libros.add(unLibro);
			return true;
//		} else {
//			return false;
//		}
	}

	void eliminar(Libro unLibro) {
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
	
	void vaciar() {
		libros = new LinkedList<Libro>();
	}
	
	Pedido guardarEn (Conector base, Usuario user) throws SQLException {
		Pedido result = new Pedido(new Date(), libros, user);
		result.guardarEn(base);
		this.vaciar();
		return result;
	}
}

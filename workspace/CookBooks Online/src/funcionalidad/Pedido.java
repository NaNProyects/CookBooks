package funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaSelect;

public class Pedido implements Cargable{
	private Integer nro;
	private Date fecha;
	private Boolean estado;
	private Double total;
	private LinkedList<Libro> libros = new LinkedList<Libro>();
	/**
	 * para carga parcial en {@link Pedido.cargarCon(unIterador)}
	 * 
	 */
	private LinkedList<Integer> idLibros;
	private Usuario usuario;
	
	public Pedido(Integer nro, Date fecha, Boolean estado, Double total,
			List<Libro> libros,
			Usuario usuario) {
		super();
		this.nro = nro;
		this.fecha = fecha;
		this.estado = estado;
		this.total = total;
		this.libros.addAll(libros);
//		this.idLibros.addAll(idLibros); TODO rever esto jaja
		this.usuario = usuario;
	}

	public Pedido() {
		super();
	}
	
	public Pedido(Date fecha, LinkedList<Libro> libros, Usuario usuario) {
		this.nro = -1;
		this.fecha = fecha;
		this.estado = false;
		this.libros = libros;
		this.usuario = usuario;
		this.total = 0.0;
		for (Libro libro : libros) {
			total+=libro.getPrecio();
		}
	}
	
	public Integer getNro() {
		return nro;
	}

	public Date getFecha() {
		return fecha;
	}

	public Boolean getEstado() {
		return estado;
	}

	public Double getTotal() {
		return total;
	}

	public LinkedList<Libro> getLibros() {
		return libros;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public LinkedList<Libro> libros() {
		return null;
	
	}
	
	public Integer nro() {
		return nro;
	
	}
	
	public Double total() {
		return total;
	
	}
	
	public Date fecha() {
		return fecha;
	
	}
	
	public boolean estado() {
		return estado;
	
	}
	
	public void enviar() {
		estado = true;	
	}

	public void cargarCon(ResultSet iterador) {
		// TODO Auto-generated method stub
		
	}

	public void guardarEn(Conector base) {
		// TODO Auto-generated method stub
		
	}

	public void borrarDe(Conector base) {
		// TODO Auto-generated method stub
		
	}

	public boolean existeEn(Conector base) {
		return estado;
		// TODO Auto-generated method stub
		
	}

	public void terminarCarga() {
		// TODO aca cambia los id por libros posta?
		idLibros = new LinkedList<Integer>();
		for (Integer i : idLibros) {
			System.out.println("cargo un libro "+i);
		}
	}

	public ConsultaSelect getBuscador() {
		return null;
	}

	public boolean esBorrableDe(Conector base) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}

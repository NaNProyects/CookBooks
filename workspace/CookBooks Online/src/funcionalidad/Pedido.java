package funcionalidad;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import utilsSQL.Cargable;
import utilsSQL.Conector;

public class Pedido implements Cargable{
	public Pedido() {
		super();
	}

	private int nro;
	private Date fecha;
	private boolean estado;
	private double total;
	public Pedido(Date fecha, ArrayList<Libro> libros) {
	
	}
	
	public ArrayList<Libro> libros() {
		return null;
	
	}
	
	public int nro() {
		return nro;
	
	}
	
	public double total() {
		return total;
	
	}
	
	public Date fecha() {
		return fecha;
	
	}
	
	public boolean estado() {
		return estado;
	
	}
	
	public Usuario usuario() {
		return null;
	
	}
	
	public void enviar() {
	
	}

	public void cargarCon(ResultSet iterador) {
		// TODO Auto-generated method stub
		
	}

	public void guardarEn(Conector base) {
		// TODO Auto-generated method stub
		
	}
}

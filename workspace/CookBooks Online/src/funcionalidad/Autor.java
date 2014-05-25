package funcionalidad;

import java.sql.ResultSet;

import utilsSQL.Cargable;
import utilsSQL.Conector;

public class Autor implements Cargable {
	public Autor() {
		super();
	}

	private int id;
	private String nombre;
	public Autor(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public int id() {
		return id;
	
	}
	
	public void nombre(String unNombre) {
		this.nombre = unNombre;
	}
	
	public String nombre() {
		return nombre;
	
	}

	public void cargarCon(ResultSet iterador) {
		// TODO Auto-generated method stub
		
	}

	public void guardarEn(Conector base) {
		// TODO Auto-generated method stub
		
	}
}

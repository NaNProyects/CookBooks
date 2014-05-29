package funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaABM;
import utilsSQL.ConsultaInsert;
import utilsSQL.ConsultaSelect;
import utilsSQL.ConsultaUpdate;

public class Libro implements Cargable {
	private Long isbn;
	private String titulo;
	private String autor; // pasar a objeto autor ?
	private String genero;
	private String editorial;
	private String idioma;
	private String reseña;
	private String vistaso;
	private Double precio;

	public Libro() {
	}

	public Libro(Long i, String titulo, String autor, String genero,
			String editorial, String idioma, String reseña, String vistaso,
			Double precio) {
		super();
		this.isbn = i;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.editorial = editorial;
		this.idioma = idioma;
		this.reseña = reseña;
		this.vistaso = vistaso;
		this.precio = precio.doubleValue();
	}

	public void guardarEn(Conector base) throws SQLException {
		ConsultaABM cons;
		String subconsultaAutor = "(select apNom from autor where apNom = '"
				+ autor + "')";
		ArrayList<String> atributos = new ArrayList<String>();
		atributos.add("isbn");
		atributos.add("titulo");
		atributos.add("autor");
		atributos.add("genero");
		atributos.add("editorial");
		atributos.add("idioma");
		atributos.add("reseña");
		atributos.add("vistazo");
		atributos.add("precio");
		ArrayList<String> valores = new ArrayList<String>();
		valores.add(isbn.toString());
		valores.add("'" + titulo + "'");
		valores.add(subconsultaAutor);
		valores.add("'" + genero + "'");
		valores.add("'" + editorial + "'");
		valores.add("'" + idioma + "'");
		valores.add("'" + reseña + "'");
		valores.add("'" + vistaso + "'");
		valores.add(precio.toString());
		if (isbn >= 0) {
			cons = new ConsultaInsert("libro", atributos, valores);
		} else { //si es negativo viene modificado
			cons = new ConsultaUpdate("libro", atributos, valores,
					new ConsultaSelect("*", "("
							+ new ConsultaSelect("apNom", "autor", "isbn = "
									+ isbn) + ") as tmp")
							+ ")");
		}
		System.out.println(cons);
			base.ejecutar(cons);
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getReseña() {
		return reseña;
	}

	public void setReseña(String reseña) {
		this.reseña = reseña;
	}

	public String getVistaso() {
		return vistaso;
	}

	public void setVistaso(String vistaso) {
		this.vistaso = vistaso;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public void cargarCon(ResultSet iterador) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void borrarDe(Conector base) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean existeEn(Conector base) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}



}

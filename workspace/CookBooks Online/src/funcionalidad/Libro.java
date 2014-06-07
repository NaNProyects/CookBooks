package funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaABM;
import utilsSQL.ConsultaDelete;
import utilsSQL.ConsultaInsert;
import utilsSQL.ConsultaSelect;
import utilsSQL.ConsultaUpdate;

public class Libro implements Cargable {
	private String isbn;
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

	public Libro(String isbn, String titulo, String autor, String genero,
			String editorial, String idioma, String reseña, String vistaso,
			Double precio) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.editorial = editorial;
		this.idioma = idioma;
		this.reseña = reseña;
		this.vistaso = vistaso;
		this.precio = precio.doubleValue();
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
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

	public Double getPrecio() {
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

	public ConsultaSelect getBuscador() {
		return new ConsultaSelect("*",
				"libro inner join autor on autor = idAutor", "isbn = '"
						+ this.getIsbn()+"'");
	}

	public void guardarEn(Conector base) throws SQLException {
		try {
			ConsultaABM cons;
			String subconsultaAutor = "(select idAutor from autor where apNom = '"
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
			valores.add("'" + isbn + "'");
			valores.add("'" + titulo + "'");
			valores.add(subconsultaAutor);
			valores.add("'" + genero + "'");
			valores.add("'" + editorial + "'");
			valores.add("'" + idioma + "'");
			valores.add("'" + reseña + "'");
			valores.add("'" + vistaso + "'");
			valores.add(precio.toString());
			if (!isbn.endsWith("!")) {
				cons = new ConsultaInsert("libro", atributos, valores);
			} else { // si termina en ! viene modificado
				isbn = isbn.replace("!", "");
				valores.set(0, "'" + isbn +"'"); 
				cons = new ConsultaUpdate("libro", atributos, valores,
						"isbn IN ("
								+ new ConsultaSelect("*", "("
										+ new ConsultaSelect("isbn", "libro",
												"isbn = '" + isbn + "'") + ")"
										+ " as tmp") + ")");
			}
			base.ejecutar(cons);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new SQLException(
						"Ya existe un libro con ese isbn");
			}
			throw e;
		}
	}

	public void cargarCon(ResultSet iterador) throws SQLException {
		try {
			isbn = iterador.getString("isbn");
			titulo = iterador.getString("titulo");
			autor = iterador.getString("apNom");
			genero = iterador.getString("genero");
			editorial = iterador.getString("editorial");
			idioma = iterador.getString("idioma");
			reseña = iterador.getString("reseña");
			vistaso = iterador.getString("vistazo");
			precio = iterador.getDouble("precio");
		} catch (SQLException e) {
			if (e.getMessage().startsWith("Column")) {
				throw new SQLException("Posiblemente falte el inner join");
			} else
				throw e;
		}
	}

	public void borrarDe(Conector base) throws SQLException {
		ConsultaSelect sel1 = new ConsultaSelect("isbn", "libro", "isbn = '"
				+ isbn + "'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("libro", "isbn IN (" + sel2
				+ ")");
		if (this.existeEn(base)) {
			base.ejecutar(del);
		} else {
			throw new SQLException("El elemento no existe");
		}
		if (this.existeEn(base)) {
			throw new SQLException("El elemento no se pudo borrar");
		}
	}

	public boolean existeEn(Conector base) throws SQLException {
		ConsultaSelect select = new ConsultaSelect("count(*)", "libro",
				"(isbn = '" + isbn + "' and titulo='" + titulo + "')");
		base.ejecutar(select);
		return (base.getFirstInt() != 0);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Libro) {
			Libro libro = (Libro) obj;
			return (libro.getIsbn().equals(libro.getIsbn()));
		} else
			return false;
	}

	public void terminar() {
		// por ahora no es necesario
	};

}

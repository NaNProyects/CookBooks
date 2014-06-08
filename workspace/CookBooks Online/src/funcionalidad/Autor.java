package funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utilsSQL.*;

public class Autor implements Cargable {

	private Integer id;
	private String nombre;
	private String apellido = "";

	public Autor() {
		super();
	}

	public Autor(Integer id, String nombre, String apellido) { // TODO JOSE
																// CAMBIA EL
																// CONSTRUCTOR
																// CUANDO EN LA
																// BASE Y ACA
																// MANEJES EL
																// APELLIDO,
		// POR AHORA LO MAJENO COMO Q SE INICIE VACIO XD
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Integer id() {
		return id;

	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void nombre(String unNombre) {
		this.nombre = unNombre;
	}

	public String nombre() {
		return nombre;

	}

	public void cargarCon(ResultSet iterador) throws SQLException {
		this.id = iterador.getInt("idAutor");
		this.nombre = iterador.getString("nombre");
		this.apellido = iterador.getString("apellido");
	}

	public void guardarEn(Conector base) throws SQLException {

		try {
			ConsultaABM cons;
			ArrayList<String> atr = new ArrayList<String>();
			atr.add("nombre");
			atr.add("apellido");
			ArrayList<String> vals = new ArrayList<String>();
			vals.add("'" + nombre + "'");
			vals.add("'" + apellido + "'");
			if (id == -1) { // sin nro asignado
				cons = new ConsultaInsert("autor", atr, vals);
				base.ejecutar(cons);
				base.ejecutar(new ConsultaSelect("idAutor", "autor",
						"nombre = '" + nombre + "' and apellido = '" + apellido + "'"));
				this.id = base.getFirstInt();
			} else {
				cons = new ConsultaUpdate("autor", atr, vals,
						"idAutor IN ("
								+ new ConsultaSelect("*", "("
										+ new ConsultaSelect("idAutor",
												"autor", "idAutor = " + id)
										+ ") as tmp") + ")");
				base.ejecutar(cons);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new SQLException("Ya existe un autor con ese nombre");
			}
			throw e;
		}
	}

	public void borrarDe(Conector base) throws SQLException {
		ConsultaSelect sel1 = new ConsultaSelect("idAutor", "autor",
				"nombre = '"+nombre+"' and apellido = '"+apellido+"'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("autor", "idAutor IN (" + sel2
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
		ConsultaSelect select = new ConsultaSelect("count(*)", "autor",
				"nombre = '"+nombre+"' and apellido = '"+apellido+"'");
		base.ejecutar(select);
		return (base.getFirstInt() != 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Autor) {
			Autor autor = (Autor) obj;
			return (this.nombre.equals(autor.nombre));
		} else {
			return false;
		}
	}

	public void terminar() {
		// por ahora no es necesario
	}

	public ConsultaSelect getBuscador() {
		return new ConsultaSelect("*", "autor", "idAutor =" + id);
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}

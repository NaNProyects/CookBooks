package funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;

import utilsSQL.*;

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

	public void cargarCon(ResultSet iterador) throws SQLException {
		this.id = iterador.getInt("idAutor");
		this.nombre = iterador.getString("apNom");
	}

	public void guardarEn(Conector base) throws SQLException {

		try {
			ConsultaABM cons;
			if (id == -1) { // sin nro asignado
				cons = new ConsultaInsert("autor", "apNom", "'" + nombre + "'");
				base.ejecutar(cons);
				base.ejecutar(new ConsultaSelect("idAutor", "autor",
						"apNom = '" + nombre + "'"));
				this.id = base.getFirstInt();
			} else {
				cons = new ConsultaUpdate("autor", "apNom", "'" + nombre + "'",
						"idAutor IN ("
								+ new ConsultaSelect("*", "("
										+ new ConsultaSelect("idAutor", "autor",
												"idAutor = "+ id)
										+ ") as tmp") + ")");
				base.ejecutar(cons);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new SQLException(
						"ERROR: Ya existe un autor con ese nombre");
			}
			throw e;
		}
	}

	public void borrarDe(Conector base) throws SQLException {
		ConsultaSelect sel1 = new ConsultaSelect("apNom", "autor", "apNom = '"
				+ nombre + "'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("autor", "apNom IN (" + sel2
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
		ConsultaSelect select = new ConsultaSelect("count(apNom)", "autor",
				"apNom = '" + nombre + "'");
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
}

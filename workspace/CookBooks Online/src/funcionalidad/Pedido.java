package funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaSelect;

public class Pedido implements Cargable {
	private Integer nro;
	private Date fecha;
	private Boolean estado;
	private Double total;
	private LinkedList<Libro> libros;
	private Usuario usuario;

	public Pedido(Integer nro, Date fecha, Boolean estado, Double total,
			List<Libro> libros, Usuario usuario) {
		super();
		this.nro = nro;
		this.fecha = fecha;
		this.estado = estado;
		this.total = total;
		this.libros = new LinkedList<Libro>(libros);
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
			total += libro.getPrecio();
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

	public void cargarCon(ResultSet iterador) throws SQLException {
		try {
			this.nro = iterador.getInt("idPedido");
			this.fecha = iterador.getDate("fecha");
			this.estado = iterador.getBoolean("estado");
			this.total = iterador.getDouble("total");
			this.usuario = new Usuario();
			this.usuario.cargarCon(iterador);
		} catch (SQLException e) {
			if (e.getMessage().startsWith("Column")) {
				throw new SQLException("Posiblemente falte el inner join");
			} else
				throw e;
		}
	}

	public void guardarEn(Conector base) throws SQLException {
		// TODO Pedido.#guardarEn

	}

	public void borrarDe(Conector base) throws SQLException {
		// TODO Pedido.#borrarDe

	}

	public boolean existeEn(Conector base) throws SQLException {
		// TODO Pedido.#existeEn
		return false;
	}

	@SuppressWarnings("unchecked")
	public void terminarCargaDe(Conector base) throws Exception {
		ConsultaSelect sel = new ConsultaSelect(
				"*",
				"libro inner join libroPedido on libro = isbn inner join autor on autor = idAutor",
				"pedido = " + nro);
		base.ejecutar(sel);
		libros = new LinkedList<Libro>(
				(Collection<? extends Libro>) base.iterarUn(Libro.class));
	}

	public static ConsultaSelect getBuscadorTodos() {
		return new ConsultaSelect("*",
				"pedido inner join usuario on usuario = DNI");
	}

	public ConsultaSelect getBuscador() {
		return new ConsultaSelect("*",
				"pedido inner join usuario on usuario = DNI", "idPedido = "
						+ this.nro);
	}

	public boolean esBorrableDe(Conector base) throws SQLException {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pedido) {
			Pedido pedido = (Pedido) obj;
			return (this.nro.equals(pedido.nro));
		} else {
			return false;
		}
	}
}

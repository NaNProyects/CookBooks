package funcionalidad;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaABM;
import utilsSQL.ConsultaInsert;
import utilsSQL.ConsultaSelect;
import utilsSQL.ConsultaUpdate;

public class Pedido implements Cargable {
	private Integer nro;
	private Date fecha;
	private Boolean estado;
	private Double total;
	private LinkedList<Libro> libros;
	private Usuario usuario;

	public Pedido() {
		super();
	}

	public Pedido(LinkedList<Libro> libros, Usuario usuario) {
		this.nro = -1;
		java.util.Date d = new java.util.Date();
		this.fecha = new Date(d.getTime());
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
		//ESTADO
		
		
		//FECHA
		//NRO
		//TOTAL
		//USUARIO (link con usuario) OK
		//LIBROS (link con libropedido)
		try {
			ConsultaABM cons;
			if (!estado) { //no enviado FIXME REFACTORIZAR POR ENVIADO DIOS
				ArrayList<String> atributos = new ArrayList<String>();
				atributos.add("fecha");
				atributos.add("total");
				atributos.add("usuario");
				atributos.add("estado");
				ArrayList<String> valores = new ArrayList<String>();
				valores.add("'" + fecha.toString() + "'");
				valores.add("'" + total.toString() + "'");
				valores.add(usuario.getDni().toString());
				valores.add(estado.toString());
				cons = new ConsultaInsert("pedido", atributos, valores);
				base.ejecutar(cons);
				base.ejecutar(new ConsultaSelect("LAST_INSERT_ID()"));
				this.nro = base.getFirstInt();
				this.guardarLibros(base);
			} else { //solo puede cambiar de estado
				cons = new ConsultaUpdate("pedido", "estado", "true",
						"nro IN ("
								+ new ConsultaSelect("*", "("
										+ new ConsultaSelect("idPedido", "pedido",
												"nro = " + nro ) + ")"
										+ " as tmp") + ")");
				base.ejecutar(cons);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new SQLException("Ya existe un pedido con ese nro (?)");
			}
			throw e;
		}
		

	}

	private void guardarLibros(Conector base) throws SQLException {
		for (Libro l : libros) {
			ArrayList<String> atr = new ArrayList<String>();
			atr.add("pedido");
			atr.add("libro");
			ArrayList<String> vals = new ArrayList<String>();
			vals.add(nro.toString());
			vals.add(l.getIsbn());
			ConsultaInsert ins = new ConsultaInsert("libroPedido", atr, vals);
			base.ejecutar(ins);
			}
	}

	public void borrarDe(Conector base) throws SQLException {
		// no se puede borrar un pedido

	}

	public boolean existeEn(Conector base) throws SQLException {
		ConsultaSelect select = new ConsultaSelect("count(*)", "pedido",
				"(idPedido = " + nro + ")");
		base.ejecutar(select);
		return (base.getFirstInt() != 0);
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

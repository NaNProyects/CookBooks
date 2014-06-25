package funcionalidad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilsSQL.*;

public class Usuario implements Cargable {

	private Integer dni;
	private String telefono;
	private String tarjeta;
	private Date fechaRegistro;
	private String direccion;
	// NOTA = EN TODOS LOS INSERTS DEMO LA PASS ES SU "qwerty"
	private String hashPass;

	private String email;
	private String nombre;
	private String apellido;
	private Integer id;
	private String pin;

	public static Usuario anonimo() {
		Usuario result = new Usuario();
		result.setId(-1);
		result.setApellido("");
		result.setDireccion("");
		result.setDni(0);
		result.setEmail("");
		result.setNombre("");
		result.setPin("");
		result.setTarjeta("");
		result.setTelefono("");
		return result;
	}

	public Usuario() {
		super();
	}

	public Usuario(Integer dni, String telefono, String tarjeta, String pin,
			String direccion, String hashPass, String email, String nombre,
			String apellido) {
		super();
		this.dni = dni;
		this.telefono = telefono;
		this.tarjeta = tarjeta;
		this.pin = pin;
		java.util.Date d = new java.util.Date();
		this.fechaRegistro = new Date(d.getTime());
		this.direccion = direccion;
		this.hashPass = hashPass;
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Integer getDni() {
		return dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getHashPass() {
		return hashPass;
	}

	public String getEmail() {
		return email;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setHashPass(String hashPass) {
		this.hashPass = hashPass;
	}

	public void cargarCon(ResultSet iterador) throws SQLException {
		this.id = iterador.getInt("idUsuario");
		this.dni = iterador.getInt("dni");
		this.telefono = iterador.getString("telefono");
		this.tarjeta = iterador.getString("tarjeta");
		this.pin = iterador.getString("pin");
		this.fechaRegistro = iterador.getDate("fechaRegistro");
		this.direccion = iterador.getString("direccion");
		this.hashPass = iterador.getString("contraseña");
		this.email = iterador.getString("email");
		this.nombre = iterador.getString("nombre");
		this.apellido = iterador.getString("apellido");
	}

	public void guardarEn(Conector base) throws SQLException {
		try {
			ConsultaABM cons;
			ArrayList<String> atributos = new ArrayList<String>();
			atributos.add("dni");
			atributos.add("telefono");
			atributos.add("tarjeta");
			atributos.add("pin");
			atributos.add("fechaRegistro");
			atributos.add("direccion");
			atributos.add("contraseña");
			atributos.add("email");
			atributos.add("nombre");
			atributos.add("apellido");
			ArrayList<String> valores = new ArrayList<String>();
			valores.add(dni.toString());
			valores.add(telefono.toString());
			valores.add("'" + tarjeta + "'");
			valores.add("'" + pin + "'");
			valores.add("'" + fechaRegistro.toString() + "'");
			valores.add("'" + direccion + "'");
			valores.add("'" + hashPass + "'");
			valores.add("'" + email + "'");
			valores.add("'" + nombre + "'");
			valores.add("'" + apellido + "'");
			if (dni >= 0) {
				cons = new ConsultaInsert("usuario", atributos, valores);
				base.ejecutar(cons);
				base.ejecutar(new ConsultaSelect("LAST_INSERT_ID()"));
				this.id = base.getFirstInt();
			} else { // si es negativo viene modificado
				dni = -1 * dni;
				valores.set(0, dni.toString());
				atributos.add("idUsuario"); // como ya existia ya sabemos el id
				valores.add(id.toString()); //
				cons = new ConsultaUpdate("usuario", atributos, valores,
						"dni IN ("
								+ new ConsultaSelect("*", "("
										+ new ConsultaSelect("dni", "usuario",
												"dni = " + dni) + ")"
										+ " as tmp") + ")");
				base.ejecutar(cons);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new SQLException("Ya existe un usuario con ese mail/dni");
			}
			throw e;
		}
	}

	public void borrarDe(Conector base) throws SQLException {
		ConsultaSelect sel1 = new ConsultaSelect("dni", "usuario", "dni = '"
				+ dni + "'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("usuario", "dni IN (" + sel2
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
		ConsultaSelect select = new ConsultaSelect("count(*)", "usuario",
				"(dni = " + dni + " or email='" + email + "')");
		base.ejecutar(select);
		return (base.getFirstInt() != 0);
	}

	public void terminarCargaDe(Conector base) {
		// por ahora no es necesario
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			Usuario usuario = (Usuario) obj;
			return (this.dni.equals(usuario.dni) & this.email
					.equals(usuario.email));
		} else
			return false;
	}

	public static ConsultaSelect getBuscadorTodos() {
		return new ConsultaSelect("*", "usuario");
	}

	public ConsultaSelect getBuscador() {
		return new ConsultaSelect("*", "usuario", "dni = " + dni);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer i) {
		id = i;
	}

	/*
	 * (non-Javadoc) Un usuario no se puede borrar si está en un pedido
	 * 
	 * @see utilsSQL.Cargable#esBorrableDe(utilsSQL.Conector)
	 */
	public boolean esBorrableDe(Conector base) throws SQLException {
		ConsultaSelect select = new ConsultaSelect("count(*)",
				"usuario inner join Pedido on DNI = usuario", "id = " + id);
		base.ejecutar(select);
		return (base.getFirstInt() == 0);
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	};

	@SuppressWarnings("unchecked")
	public LinkedList<Pedido> getHistorial(Conector base) {
		ConsultaSelect sel = new ConsultaSelect("*", "pedido inner join usuario on usuario = DNI", "usuario = "
				+ this.dni);
		try {
			base.ejecutar(sel);
			return new LinkedList<Pedido>(
					(Collection<? extends Pedido>) base.iterarUn(Pedido.class));
		} catch (Exception e) {
			e.printStackTrace(); // TODO alindar cuando ande
			return new LinkedList<Pedido>();
		}
	}
}

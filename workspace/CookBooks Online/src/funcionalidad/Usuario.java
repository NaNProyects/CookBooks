package funcionalidad;

import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaABM;
import utilsSQL.ConsultaDelete;
import utilsSQL.ConsultaInsert;
import utilsSQL.ConsultaSelect;
import utilsSQL.ConsultaUpdate;

public class Usuario implements Cargable {

	private Integer dni;
	private Integer telefono;
	private String tarjeta;
	private Date fechaRegistro;
	private String direccion;
	// NOTA = EN TODOS LOS INSERTS DEMO LA PASS ES SU "Nombre"
	private String hashPass;
	private String email;
	private String nombre;
	private String apellido;
	private Integer id; //TODO para permisos
	
	public static Usuario anonimo() {
		Usuario result = new Usuario();
		result.setId(-1);
		return result;
	}

	public Usuario() {
		super();
	}

	public Usuario(Integer dni, Integer telefono, String tarjeta,
			Date fechaRegistro, String direccion, String hashPass,
			String email, String nombre, String apellido) {
		super();
		this.dni = dni;
		this.telefono = telefono;
		this.tarjeta = tarjeta;
		this.fechaRegistro = fechaRegistro;
		this.direccion = direccion;
		this.hashPass = hashPass;
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Integer getDni() {
		return dni;
	}

	public Integer getTelefono() {
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

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void cargarCon(ResultSet iterador) throws SQLException {
		this.id = iterador.getInt("idUsuario");
		this.dni = iterador.getInt("dni");
		this.telefono = iterador.getInt("telefono");
		this.tarjeta = iterador.getString("tarjeta");
		this.fechaRegistro = iterador.getDate("fechaRegistro");
		this.direccion = iterador.getString("direccion");
		this.hashPass = iterador.getString("contraseņa");
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
			atributos.add("fechaRegistro");
			atributos.add("direccion");
			atributos.add("contraseņa");
			atributos.add("email");
			atributos.add("nombre");
			atributos.add("apellido");
			ArrayList<String> valores = new ArrayList<String>();
			valores.add(dni.toString());
			valores.add(telefono.toString());
			valores.add("'" + tarjeta + "'");
			valores.add("'" + fechaRegistro.toString() + "'");
			valores.add("'" + direccion + "'");
			valores.add("'" + hashPass + "'");
			valores.add("'" + email + "'");
			valores.add("'" + nombre + "'");
			valores.add("'" + apellido + "'");
			if (dni >= 0) {
				cons = new ConsultaInsert("usuario", atributos, valores);
				base.ejecutar(cons);
				base.ejecutar(new ConsultaSelect("idUsuario", "usuario",
						"email = '" + email + "'"));
				this.id = base.getFirstInt();
			} else { // si es negativo viene modificado
				dni = -1 * dni;
				valores.set(0, dni.toString());
				atributos.add("idUsuario"); //como ya existia ya sabemos el id
				valores.add(id.toString()); //
				cons = new ConsultaUpdate("usuario", atributos, valores,
						"dni IN ("
								+ new ConsultaSelect("*", "(" + new ConsultaSelect("dni", "usuario", "dni = "+dni) + ")"
										+ " as tmp") + ")");
				base.ejecutar(cons);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new SQLException(
						"Ya existe un usuario con ese mail/dni");
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
				"(dni = " + dni + " and email='" + email + "')");
		base.ejecutar(select);
		return (base.getFirstInt() != 0);
	}

	public void terminar() {
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

	public ConsultaSelect getBuscador() {
		return new ConsultaSelect("*", "usuario", "dni = "+dni);
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer i) {
		id = i;
	}
}

package funcionalidad;

import java.util.*;
import java.security.*;
import java.sql.Date;
import java.sql.SQLException;
import java.math.BigInteger;

import com.mysql.jdbc.exceptions.MySQLNonTransientException;

import utilsSQL.Conector;
import utilsSQL.ConsultaSelect;

public class CookBooks {

	private Conector base;
	private Usuario usuario = Usuario.anonimo();
	private Carrito carrito = new Carrito();

	/**
	 * Está feito que esté acá la pass peeeero MySQLNonTransientException Por
	 * compatibilidad no tira excepción... todavía
	 * 
	 * @throws Exception
	 *             si no conseguimos conectar con la base. Expirará?
	 * 
	 */
	public CookBooks() throws Exception {
		try {
			base = new Conector("cookbooksbase", "root", "qwerty");
		} catch (SQLException e) {
			throw new Exception("No se pudo contactar con la base");
		}
	}

	/**
	 * Por ahora es un eliminador drástico. <br>
	 * Ya se verá con borrado lógico. Chequea e informa si tenía libros.
	 * 
	 * @param unAutor
	 * @return si se pudo
	 * @throws Exception
	 *             si paso algo raro o no se pudo reconectar
	 */
	public boolean eliminar(Autor unAutor) throws Exception {
		try {
			unAutor.borrarDe(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			if (e.getMessage().startsWith("El autor posee libros"))
				return false;
			else
				throw e;
		}

	}

	/**
	 * NO se puede actualizar un autor que viene con -1 (nuevo). <br>
	 * Ud se compromete a mandarme un id con sentido.
	 * 
	 * @param unAutor
	 * @return si se pudo o no
	 * @throws Exception
	 *             si hubo un error de reconexion
	 */
	public boolean modificar(Autor unAutor) throws Exception {
		if (unAutor.id() < 0) {
			return false;
		}
		try {
			unAutor.guardarEn(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Guarda el autor en la base. Chequea repetidos. Ignora id.
	 * 
	 * @param unNombreAutor
	 * @return objeto autor con id
	 * @throws Exception
	 */
	public Autor agregar(Autor autor) throws Exception {
		try {
			autor.setId(-1);
			autor.guardarEn(base);
			return autor;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return null;
		} catch (SQLException e) {
			if (e.getMessage().startsWith("Ya existe")) {
				throw new Exception(e.getMessage());
			} else {
				throw new Exception("Ocurrió un error");
			}

		}

	}

	/**
	 * @return la lista de todos los autores en la base
	 * @throws Exception
	 *             si falla la reconexion
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Autor> listarAutores() throws Exception {
		try {
			base.ejecutar(Autor.getBuscadorTodos());
			return new LinkedList<Autor>(
					(Collection<? extends Autor>) base.iterarUn(Autor.class));
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return new LinkedList<Autor>();
		} catch (Exception e) {
			return new LinkedList<Autor>();
		}
	}

	/**
	 * @return la lista de todos los pedidos en la base
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Pedido> listarPedidos() throws Exception {
		try {
			base.ejecutar(Pedido.getBuscadorTodos());
			return new LinkedList<Pedido>(
					(Collection<? extends Pedido>) base.iterarUn(Pedido.class));
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return new LinkedList<Pedido>();
		} catch (Exception e) {
			return new LinkedList<Pedido>();
		}
	}

	/**
	 * Modifica en la base el estado de un pedido pendiente a enviado
	 * 
	 * @param unPedido
	 * @return si se pudo o no (falla si no tenia numero)
	 * @throws Exception
	 *             si paso algo raro
	 */
	public boolean enviar(Pedido unPedido) throws Exception {
		if (unPedido.nro() < 0) {
			return false; // no puedo enviar un pedido sin numero
		}
		try {
			unPedido.enviar();
			unPedido.guardarEn(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			throw new Exception("Ocurrió un error");
		}
	}

	/**
	 * @return la lista de todos los libros en la base
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Libro> listarLibros() {
		try {
			base.ejecutar(Libro.getBuscadorTodos());
			return new LinkedList<Libro>(
					(Collection<? extends Libro>) base.iterarUn(Libro.class));
		} catch (Exception e) {
			return new LinkedList<Libro>();
		}
	}

	/**
	 * Agrega un libro ya creado. Falla si el isbn ya estaba.
	 * 
	 * @param unLibro
	 * @return si se pudo o no
	 * @throws Exception
	 */
	public boolean agregar(Libro unLibro) throws Exception {
		try {
			unLibro.guardarEn(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 * Guarda los datos nuevos del libro en la fila con el mismo isbn
	 * 
	 * @param unLibro
	 * @return si se pudo o no
	 * @throws Exception
	 */
	public boolean modificar(Libro unLibro) throws Exception {
		unLibro.setIsbn(unLibro.getIsbn() + "!");
		try {
			unLibro.guardarEn(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 * Elimina un libro. Si está en algún pedido no se puede.
	 * 
	 * @param unLibro
	 * @return si se pudo
	 * @throws Exception
	 */
	public boolean eliminar(Libro unLibro) throws Exception {
		try {
			unLibro.borrarDe(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			if (e.getMessage().startsWith("El libro tiene pedidos"))
				return false;
			else
				throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Libro> buscarLibro(String terminoDeBusqueda) {
		if (terminoDeBusqueda.equals("")) {
			return new LinkedList<Libro>();
		} else {
			try {
				base.ejecutar(this.getBuscador(terminoDeBusqueda));
				return new LinkedList<Libro>(
						(Collection<? extends Libro>) base
								.iterarUn(Libro.class));
			} catch (Exception e) {
				return new LinkedList<Libro>();
			}
		}
	}

	private ConsultaSelect getBuscador(String cadena) {
		ArrayList<String> atributos = new ArrayList<String>();
		atributos.add("isbn");
		atributos.add("titulo");
		atributos.add("nombre");
		atributos.add("apellido");
		atributos.add("genero");
		atributos.add("editorial");
		atributos.add("idioma");
		String from = "libro inner join autor on autor = idAutor";
		String[] palabras = cadena.split("( |,)+");
		ArrayList<ConsultaSelect> selects = new ArrayList<ConsultaSelect>();
		for (String pal : palabras) {
			String where = "";
			for (int i = 0; i < atributos.size() - 1; i++) {
				where += "(" + atributos.get(i) + " LIKE " + "'%" + pal
						+ "%') OR ";
			}
			where += "(" + atributos.get(atributos.size() - 1) + " LIKE "
					+ "'%" + pal + "%')";
			ConsultaSelect sel = new ConsultaSelect("*", from, where);
			selects.add(sel);
		}
		String fromUnion = "(";
		for (int i = 0; i < selects.size() - 1; i++) {
			fromUnion += "(" + selects.get(i) + ") UNION ";
		}
		fromUnion += "(" + selects.get(selects.size() - 1) + ")) as tmp";
		return new ConsultaSelect("*", fromUnion);
	}

	public boolean estaEnElCarrito(Libro unLibro) {
		return carrito.contiene(unLibro);
	}

	public boolean agregarAlCarrito(Libro unLibro) {
		if (!carrito.contiene(unLibro)) {
			carrito.agregar(unLibro);
			return true;
		} else {
			return false;
		}
	}

	public LinkedList<Libro> getLibrosCarrito() {
		return carrito.getLibros();
	}

	public Double getCostoCarrito() {
		return carrito.getCosto();
	}

	public Pedido confirmarCarrito() throws SQLException {
		Pedido result = carrito.guardarEn(base, usuario);
		carrito.vaciar();
		return result;
	}

	public void eliminarDelCarrito(Libro unLibro) {
		carrito.eliminar(unLibro);
	}

	public void vaciarCarrito() {
		carrito.vaciar();
	}

	/**
	 * @param mail
	 *            el mail de alguien
	 * @param pass
	 *            como string sin hashear
	 * @return true si los datos coinciden
	 * @throws Exception
	 * @throws SQLException
	 *             si paso algo que espero que no
	 */
	@SuppressWarnings("unchecked")
	public boolean autenticar(String mail, String pass) throws Exception {
		try {
			ConsultaSelect sel = new ConsultaSelect("*", "usuario", "email = '"
					+ mail + "'");
			base.ejecutar(sel);
			LinkedList<Usuario> lis = new LinkedList<Usuario>(
					(Collection<? extends Usuario>) base
							.iterarUn(Usuario.class));
			if (lis.size() == 0) {
				return false; // el mail no existe
			} else {
				if (chequearContraseña(lis.element(), pass)) {
					usuario = lis.element();
					return true;
				}
				return false;
			}
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (Exception e) {
			return false; // no se pudo por algun error
		}
	}

	public Usuario usuarioActual() {
		return usuario;
	}

	/**
	 * Agrega un usuario ya creado, sin hashPass ni fecha de ingreso. <br>
	 * Falla si el DNI o mail ya estaba.
	 * 
	 * @param unUsuario
	 * @param pass
	 * @return si se pudo o no
	 * @throws Exception
	 */
	public boolean agregar(Usuario unUsuario, String pass) throws Exception {
		try {
			unUsuario.setHashPass(getMD5(pass));
			java.util.Date d = new java.util.Date();
			unUsuario.setFechaRegistro(new Date(d.getTime()));
			unUsuario.guardarEn(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			return false;
		}

	}

	public boolean chequearContraseña(Usuario unUsuario, String pass) {
		return unUsuario.getHashPass().equals(getMD5(pass));

	}

	public LinkedList<Pedido> historialDe(Usuario unUsuario) {
		try {
			return unUsuario.getHistorial(base);
		} catch (Exception e) {
			e.printStackTrace();
			return new LinkedList<Pedido>();
		}

	}

	/**
	 * Elimina un usuario. Si está en algún pedido no se puede.
	 * 
	 * @param unUsuario
	 * @return si se pudo
	 * @throws Exception
	 */
	public boolean eliminar(Usuario unUsuario) throws Exception {
		try {
			unUsuario.borrarDe(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			if (e.getMessage().startsWith("El libro tiene pedidos"))
				return false;
			else
				throw e;
		}
	}

	/**
	 * Guarda los datos nuevos del usuario en la fila con el mismo dni
	 * 
	 * @param unUsuario
	 * @return si se pudo o no
	 * @throws Exception
	 */
	public boolean modificar(Usuario unUsuario) throws Exception {
		if (unUsuario.getDni() < 0) {
			return false;
		}
		try {
			unUsuario.guardarEn(base);
			return true;
		} catch (MySQLNonTransientException e) {
			this.reconectar();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public void recuperarContraseña(String mail, int dni) {

	}

	public void cerrarSesion() {
		this.usuario = Usuario.anonimo();
		carrito.vaciar();
	}

	public Libro libroMasVendido() {
		return null;

	}

	public LinkedList<Usuario> usuariosResgistradosEntre(Date inicio, Date fin) {
		return null;

	}

	/**
	 * Usar. No preguntar.
	 * 
	 * @param cadena
	 * @return hash
	 */
	public static String getMD5(String cadena) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(cadena.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	public boolean existeDNI(String dni) throws Exception {
		ConsultaSelect select = new ConsultaSelect("count(*)", "usuario",
				"dni = " + dni);
		try {
			base.ejecutar(select);
		} catch (SQLException e) {
			throw new Exception("No se pudo completar la operacion");
		}
		return (base.getFirstInt() != 0);
	}

	public boolean existeMail(String mail) throws Exception {
		ConsultaSelect select = new ConsultaSelect("count(*)", "usuario",
				"email = '" + mail + "'");
		try {
			base.ejecutar(select);
		} catch (SQLException e) {
			throw new Exception("No se pudo completar la operacion");
		}
		return (base.getFirstInt() != 0);
	}

	/**
	 * Úsese a discreción en caso de emergencia.
	 * 
	 * @throws Exception
	 * 
	 * @throws SQLException
	 */
	public void reconectar() throws Exception {
		try {
			base.reconectar();
		} catch (SQLException e) {
			throw new Exception("No se pudo reconectar");
		}
	}
}

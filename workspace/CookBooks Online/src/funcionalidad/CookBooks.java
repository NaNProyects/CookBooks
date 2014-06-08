package funcionalidad;

import java.util.*;
import java.security.*;
import java.sql.SQLException;
import java.math.BigInteger;

import utilsSQL.Conector;
import utilsSQL.ConsultaSelect;

public class CookBooks {

	// mocks
	private static LinkedList<Autor> autores = new LinkedList<Autor>();
	private static LinkedList<Libro> lista = new LinkedList<Libro>();

	// mocks fin

	private Conector base;
	private Usuario usuario;

	@SuppressWarnings("unused")
	private static void IniciarAutores() {
		autores.add(new Autor(0, "pepe", "a"));
		autores.add(new Autor(1, "sssss", "a"));
		autores.add(new Autor(2, "sssss2", "a"));

	}

	@SuppressWarnings("unused")
	private static void IniciarLibros() {
		lista.add(new Libro("11111", "aaaaa", "sssss", "ddddd",
				"fffff", "ggggg", "wwwwwww", "eeeeeeee", new Double(12.30)));
		lista.add(new Libro("11112", "aaaaa2", "sssss2", "ddddd2",
				"fffff2", "ggggg2", "wwwwwww2", "qqqqqqqqq", new Double(13.30)));
	}

	/**
	 * Está feito que esté acá la pass peeeero MySQLNonTransientException 
	 * Por compatibilidad no tira excepción... todavía
	 * 
	 * @throws Exception
	 *             si no conseguimos conectar con la base. Expirará?
	 * 
	 */
	public CookBooks() {
		try {
			base = new Conector("cookbooksbase", "root", "qwerty");
		} catch (SQLException e) {
			// throw new Exception("No se pudo contactar con la base");
		}
	}

	/**
	 * Por ahora es un eliminador drástico. <br>
	 * Ya se verá con borrado lógico
	 * 
	 * @param unAutor
	 * @return si se pudo
	 */
	public boolean eliminar(Autor unAutor) {
		/*
		 * // Mock para Mock XD if (lista.size() == 0) { IniciarLibros(); } //
		 * Mock temporal for (Iterator<Libro> iterator = lista.iterator();
		 * iterator.hasNext();) { Libro lib = (Libro) iterator.next(); if
		 * (lib.getAutor().equals(unAutor.nombre())) { return false; } }
		 * autores.remove(unAutor); return true;
		 */
		try {
			unAutor.borrarDe(base);
			return true;
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 * NO se puede actualizar un autor que viene con -1 (nuevo). <br>
	 * Ud se compromete a mandarme un id con sentido.
	 * 
	 * @param unAutor
	 * @return si se pudo o no
	 */
	public boolean actualizar(Autor unAutor) {
		/*
		 * // Mock temporal for (Iterator<Autor> iterator = autores.iterator();
		 * iterator.hasNext();) { Autor aut = (Autor) iterator.next(); if
		 * ((aut.nombre().equals(unAutor.nombre())) && (aut.id() !=
		 * unAutor.id())) { return false; } } return true;
		 */
		if (unAutor.id() < 0) { // i told you TODO que? ->no puedo actualizar si no viene con id cargado, eso nomas xd
			return false;
		}
		try {
			unAutor.guardarEn(base);
			return true;
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
	public Autor agregar(Autor autor) throws Exception { //TODO listo
		/*
		 * // Mock temporal Autor aut; for (Iterator<Autor> iterator =
		 * autores.iterator(); iterator.hasNext();) { aut = (Autor)
		 * iterator.next(); if (aut.nombre().equals(unNombreAutor)) { throw new
		 * Exception("El Autor ya existe"); } } aut = new Autor((((Autor)
		 * autores.get(autores.size() - 1)).id()) + 1, unNombreAutor);
		 * autores.add(aut); return aut;
		 */

		try {
			autor.setId(-1);
			autor.guardarEn(base);
			return autor;
		} catch (SQLException e) {
			if (e.getMessage().startsWith("Ya existe")) {
				throw new Exception(e.getMessage());
			} else {
				throw new Exception("Ocurrió un error");
			}

		}

	}

	@SuppressWarnings("unchecked")
	public LinkedList<Autor> autores() {
		/*
		 * // Mock temporal
		 * 
		 * if (autores.size() == 0) { IniciarAutores(); } return
		 * (LinkedList<Autor>) autores.clone();
		 */
		try {
			ConsultaSelect sel = new ConsultaSelect("*", "autor");
			base.ejecutar(sel);
			return new LinkedList<Autor>(
					(Collection<? extends Autor>) base.iterarUn(Autor.class));
		} catch (Exception e) {
			return new LinkedList<Autor>();
		}
	}

	public LinkedList<Pedido> pedidos() {
		return new LinkedList<Pedido>();

	}

	public void enviar(Pedido unPedido) {

	}

	@SuppressWarnings("unchecked")
	public LinkedList<Libro> libros() {
		ConsultaSelect sel = new ConsultaSelect("*",
				"libro inner join autor on idAutor = autor");
		try {
			base.ejecutar(sel);
			return new LinkedList<Libro>(
					(Collection<? extends Libro>) base.iterarUn(Libro.class));
		} catch (Exception e) {
			return new LinkedList<Libro>();
		}
		// mock temporal
		/*
		 * if (lista.size() == 0) { IniciarLibros(); } return
		 * (LinkedList<Libro>) lista.clone();
		 */
	}

	/**
	 * Agrega un libro ya creado. Falla si el isbn ya estaba.
	 * 
	 * @param unLibro
	 * @return si se pudo o no
	 */
	public boolean agregar(Libro unLibro) {
		/*
		 * // Mock lista.add(unLibro); return true;
		 */
		try {
			unLibro.guardarEn(base);
			return true;
		} catch (SQLException e) {
			return false;
		}

	}

	// el tercero en discordia, apenas se pueda renombrar a
	// public boolean actualizar(Libro unLibro)
	/**
	 * Guarda los datos nuevos del libro en la fila con el mismo isbn
	 * 
	 * @param unLibro
	 * @return si se pudo o no
	 */
	public boolean modificar(Libro unLibro) {
		/*
		 * lista.remove(unLibro); lista.add(unLibro); return true;
		 */
		unLibro.setIsbn(unLibro.getIsbn()+"!"); // invertido = modificado
		try {
			unLibro.guardarEn(base);
			return true;
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 * Por ahora es un eliminador drástico. <br>
	 * Ya se verá con borrado lógico
	 * 
	 * @param unLibro
	 * @return si se pudo
	 */
	public boolean eliminar(Libro unLibro) { // TODO aca fijate si tiene pedido si podes asi al darle eliminar me tira false si tiene gracias
		/*
		 * // Mock lista.remove(unLibro); return true;
		 */
		try {
			unLibro.borrarDe(base);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public LinkedList<Libro> buscar(String unNombreLibro) {
		return null;

	}

	public void agregarAlCarrito(Libro unLibro) {

	}

	public Carrito carrito() {
		return null;

	}

	public Pedido confirmarCarrito() {
		return null;

	}

	public void eliminarDelCarrito(Libro unLibro) {

	}

	public void cancelarCarrito() {

	}

	public boolean autenticar(String mail, String pass) {
		return false;

	}

	public Usuario usuarioActual() {
		return usuario;
	}

	public boolean agregar(Usuario unUsuario, String pass) {
		return false;

	}

	public boolean chequearContraseña(Usuario unUsuario, String pass) {
		return false;

	}

	public LinkedList<Usuario> historialDe(Usuario unUsuario) {
		return null;

	}

	public void eliminar(Usuario unUsuario) {

	}

	public void actualizar(Usuario unUsuario) {

	}

	public void recuperarContraseña(String mail, int dni) {

	}

	public void cerrarSesion() {
		this.usuario = Usuario.anonimo();
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

	/**
	 * Úsese a discreción en caso de emergencia.
	 * 
	 * @throws SQLException
	 */
	public void reconectar() throws SQLException {
		base.reconectar();
	}
}

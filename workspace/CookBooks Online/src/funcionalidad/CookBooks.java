package funcionalidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class CookBooks {
	
	//mocks
	private static ArrayList autores= new ArrayList();
	private static LinkedList lista = new LinkedList();
	private static void IniciarAutores(){
		autores.add(new Autor(0, "pepe"));
		autores.add(new Autor(1,"sssss"));
		autores.add(new Autor(2,"sssss2"));
	}	
	
	
	private static void IniciarLibros(){
		lista.add(new Libro(new Long(11111), "aaaaa", "sssss", "ddddd", "fffff", "ggggg", "wwwwwww" , "eeeeeeee",new Double(12.30)));
		lista.add(new Libro(new Long(11112), "aaaaa2", "sssss2", "ddddd2", "fffff2", "ggggg2", "wwwwwww2" , "qqqqqqqqq", new Double(13.30)));	
	}	
	
	
	//mocks fin
	
	
	//private Conexion base;
//	private Usuario usuario;
	public boolean eliminar(Autor unAutor) {
		return false;
	
	}
	
	public boolean actualizar(Autor unAutor) {
		return false;
	
	}
	
	public Autor agregar(String unNombreAutor) {
		return null;

	}
	
	public ArrayList autores() {
		
		// Mock temporal

			if(autores.size() == 0){
				IniciarAutores();
			}
			return autores;
	
	
	}
	
	public ArrayList pedidos() {
		return null;
	
	}
	
	public void enviar(Pedido unPedido) {
	
	}
	
	public LinkedList libros() {
		
		//mock temporal
	
		if(lista.size() == 0){
			IniciarLibros();
		}
		return lista;

	
	}
	
	public boolean agregar(Libro unLibro) {
		// Mock
			lista.add(unLibro);				
			return true;
	
	
	}
	
	public boolean modificar(Libro unLibro) {
		lista.remove(unLibro);
		lista.add(unLibro);				
		return true;
	
	}
	
	public boolean eliminar(Libro unLibro) {
		// Mock
		lista.remove(unLibro);				
		return true;
	
	}
	
	public ArrayList buscar(String unNombreLibro) {
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
		return null;
	
	}
	
	public boolean agregar(Usuario unUsuario, String pass) {
		return false;
	
	}
	
	public boolean chequearContraseña(Usuario unUsuario, String pass) {
		return false;
	
	}
	
	public ArrayList historialDe(Usuario unUsuario) {
		return null;
	
	}
	
	public void eliminar(Usuario unUsuario) {
	
	}
	
	public void actualizar(Usuario unUsuario) {
	
	}
	
	public void recuperarContraseña(String mail, int dni) {
	
	}
	
	public void cerrarSesion() {
	
	}
	
	public Libro libroMasVendido() {
		return null;
	
	}
	
	public ArrayList usuariosResgistradosEntre(Date inicio, Date fin) {
		return null;
	
	}
}

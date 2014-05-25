package funcionalidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public class CookBooks {
	
	//mocks
	private static LinkedList<Autor> autores= new LinkedList<Autor>();
	private static LinkedList<Libro> lista = new LinkedList<Libro>();
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
		
		// Mock para Mock XD
				if(lista.size() == 0){
					IniciarLibros();
				}
		// Mock temporal
		for (Iterator<Libro> iterator = lista.iterator(); iterator.hasNext();) {
			Libro lib = (Libro) iterator.next();
			if(lib.getAutor().equals(unAutor.nombre())){
				return false;
			}			
		}
		autores.remove(unAutor);
		return true;	
	}
	
	public boolean actualizar(Autor unAutor) {
		
		// Mock temporal
		for (Iterator<Autor> iterator = autores.iterator(); iterator.hasNext();) {
			Autor aut = (Autor) iterator.next();
			if((aut.nombre().equals(unAutor.nombre()))&&(aut.id() != unAutor.id())){
				return false;
			}			
		}
		return true;
	
	}
	
	public Autor agregar(String unNombreAutor) throws Exception {
		
		// Mock temporal
		Autor aut;
		for (Iterator<Autor> iterator = autores.iterator(); iterator.hasNext();) {
			aut = (Autor) iterator.next();
			if(aut.nombre().equals(unNombreAutor)){
				throw new Exception("El Autor ya existe");
			}			
		}
		aut=new Autor((((Autor) autores.get(autores.size()-1)).id())+1, unNombreAutor);
		autores.add(aut);
		return aut;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Autor> autores() {
		
		// Mock temporal

			if(autores.size() == 0){
				IniciarAutores();
			}
			return (LinkedList<Autor>) autores.clone();
	
	
	}
	
	public ArrayList<Pedido> pedidos() {
		return null;
	
	}
	
	public void enviar(Pedido unPedido) {
	
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Libro> libros() {
		
		//mock temporal
	
		if(lista.size() == 0){
			IniciarLibros();
		}
		return (LinkedList<Libro>) lista.clone();

	
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
	
	public ArrayList<Libro> buscar(String unNombreLibro) {
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
	
	public ArrayList<Usuario> historialDe(Usuario unUsuario) {
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
	
	public ArrayList<Usuario> usuariosResgistradosEntre(Date inicio, Date fin) {
		return null;
	
	}
}

package funcionalidad;

import java.util.Iterator;
import java.util.LinkedList;

public class Contexto {

	private static LinkedList lista = new LinkedList();
	private static LinkedList autores= new LinkedList();
	public static LinkedList ListaDeLibros(String condicion){
		if(lista.size() == 0){
			IniciarLibros();
		}
		return lista;
		
	}
	private static void IniciarLibros(){
		lista.add(new Libro(new Long(11111), "aaaaa", "sssss", "ddddd", "fffff", "ggggg", "wwwwwww" , "eeeeeeee",new Double(12.30)));
		lista.add(new Libro(new Long(11112), "aaaaa2", "sssss2", "ddddd2", "fffff2", "ggggg2", "wwwwwww2" , "qqqqqqqqq", new Double(13.30)));	
	}
	public static LinkedList ListaDeAutores(String Condicion){
		if(autores.size() == 0){
			IniciarAutores();
		}
		return autores;
	}
	public static Libro getLibro(int isbn){
		Iterator iterador = lista.iterator();
		Libro libro = (Libro) iterador.next();
		while((iterador.hasNext()) && (libro.getIsbn() != isbn)){	
			libro = (Libro) iterador.next();}	
		return libro;
	}
	private static void IniciarAutores(){
		autores.add(new String("pepe"));
		autores.add(new String("sssss"));
		autores.add(new String("sssss2"));
	}
	public static boolean AgragarLibro(Libro libro){
		lista.add(libro);				
		return true;
	}
	public static boolean EliminarLibro(Libro libro){
		lista.remove(libro);				
		return true;
	}
}

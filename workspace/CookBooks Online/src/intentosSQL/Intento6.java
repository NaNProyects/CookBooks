package intentosSQL;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import utilsSQL.*;

public class Intento6 {
	public static void main(String[] args) {
		try {
			Conector objetoConexion = new Conector("sakila", "root", "qwerty");
			ConsultaSelect cons = new ConsultaSelect("*", "actor", "first_name like '%christian%'");
			objetoConexion.ejecutar(cons);
			LinkedList<Cargable> lista = objetoConexion.iterarUn(MockActor.class);
			System.out.println(lista);
			MockActor m = (MockActor) lista.getFirst();
			Date d = Date.valueOf("2014-05-25");
			m = new MockActor(201, "John", "Egbert", d);
			//O se activa esta linea (cargar)
			m.guardarEn(objetoConexion);		
			ConsultaSelect sel1 = new ConsultaSelect("first_name", "actor", "(first_name = 'John') and (last_name = 'Egbert')");
			ConsultaSelect sel2 = new ConsultaSelect("*", "("+sel1+") as tmp");
			ConsultaDelete c = new ConsultaDelete("actor", "apNom IN ("+sel2.toString()+")");
			//O se activa esta otra (borrar)
			objetoConexion.ejecutar(c);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

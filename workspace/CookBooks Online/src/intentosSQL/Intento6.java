package intentosSQL;

import java.sql.*;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

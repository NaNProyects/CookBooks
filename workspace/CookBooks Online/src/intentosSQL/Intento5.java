package intentosSQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import utilsSQL.*;

/**
 * La idea es probar el objeto conexion primero con sakila y despues con CB,
 * usando los objetos consulta
 * 
 * @author Mokocchi
 * 
 */
public class Intento5 {
	public static void main(String[] args) {
		LinkedList<ConsultaSQL> listaCons = pruebaObjetosConsulta();
		System.out.println(ConsultaSQL.concatenarConsultas(listaCons));

		try {
			// abro conexion
			Conector sakila = new Conector("sakila", "root", "qwerty");

			// corro consultas - notar que el resultado se pierde
			for (ConsultaSQL consultaSQL : listaCons) {
				consultaSQL.ejecutame(sakila);
			}

			// cierro conexion
			sakila.desconectar();
		} catch (SQLException ex) {
			Conector.informeErrorSQL(ex);
		}
	}

	private static LinkedList<ConsultaSQL> pruebaObjetosConsulta() {
		LinkedList<ConsultaSQL> listaCons = new LinkedList<ConsultaSQL>();
		ConsultaSQL cons;
		ArrayList<String> att = new ArrayList<String>();
		ArrayList<String> vals = new ArrayList<String>();
		// test ConsultaInsert
		// insert into actor (first_name, last_name, last_update)
		// values('Johny', 'Depp', '2007-09-23 00:00:00.0')
		att.add("first_name");
		att.add("last_name");
		att.add("last_update");
		vals.add("'Johny'");
		vals.add("'Depp'");
		vals.add("'2007-09-23 00:00:00.0'");
		cons = new ConsultaInsert("actor", att, vals);
		System.out.println(cons);
		listaCons.add(cons);

		// insert into film_text (film, description) values ('HelloWorld', 'Una
		// peli copada')
		// insert into film_text (film, description) values ('HelloMoon', 'Otra
		// peli copada')
		// insert into film_text (film, description) values ('HelloMars', 'Esta
		// peli no es copada')
		att.clear();
		att.add("film_id");
		att.add("title");
		att.add("description");
		vals.clear();
		vals.add("1001");
		vals.add("'HelloWorld'");
		vals.add("'Una peli copada'");
		cons = new ConsultaInsert("film_text", att, vals);
		listaCons.add(cons);
		vals.clear();
		vals.add("1002");
		vals.add("'HelloMoon'");
		vals.add("'Otra peli copada'");
		cons = new ConsultaInsert("film_text", att, vals);
		listaCons.add(cons);
		vals.clear();
		vals.add("1003");
		vals.add("'HelloMars'");
		vals.add("'Esta peli no es copada'");
		cons = new ConsultaInsert("film_text", att, vals);
		listaCons.add(cons);

		// test ConsultaSelect
		// select last_name, ',' , first_name from actor where last_name like
		// '%OLT%'
		att.clear();
		att.add("last_name");
		att.add("','");
		att.add("first_name");
		cons = new ConsultaSelect(att, "actor", "last_name like '%OLT%'");
		listaCons.add(cons);

		// test ConsultaUpdate - ojo, 2 subconsultas
		// update film_text
		// set description=concat('XXX',description)
		// where film_id IN
		// (SELECT (2)
		// tmp.film_id
		// FROM (select film_id (1)
		// from film_text
		// where description like '%copada%') as tmp
		att.clear();
		att.add("film_id");
		ConsultaSelect subcons1 = new ConsultaSelect(att, "film_text",
				"description like '%copada%'");

		att.clear();
		att.add("tmp.film_id");
		ConsultaSelect subcons2 = new ConsultaSelect(att, "(" + subcons1 + ")"
				+ "as tmp");
		att.clear();
		att.add("description");
		vals.clear();
		vals.add("concat('XXX',description)");
		cons = new ConsultaUpdate("film_text", att, vals, "film_id IN ("
				+ subcons2 + ")");
		listaCons.add(cons);

		// test ConsultaDelete
		// delete from actor where actor_id>200
		// delete from film_text where film_id
		cons = new ConsultaDelete("actor", "actor_id>200");
		listaCons.add(cons);
		cons = new ConsultaDelete("film_text", "film_id>1000");
		listaCons.add(cons);
		return listaCons;
	}
}
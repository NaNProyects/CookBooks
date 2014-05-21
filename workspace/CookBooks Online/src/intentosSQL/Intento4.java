package intentosSQL;

//import java.sql.*;
import utilsSQL.*;

/**
 * A ver si me acuerdo como se hace esto.
 * <p>
 * <b>Objetivo</b> <br>
 * <li>Crear un statement <br>
 * <li>Crear un select asi: <code>select * from actor</code> <br>
 * <li>Crear un update asi:
 * <code>update actor set last_name='pepito' where id='1'</code>
 * 
 * EDIT = ya no funciona por abstracciones varias
 * @author Mokocchi
 * 
 */
@SuppressWarnings("unused")
@Deprecated
public class Intento4 {
	public static void main(String[] args) {
//		try {
//			Statement mySQLStat = Conector.conectame(Conector.urlDesde("sakila"), Conector.llaveCon("root", "qwerty"));
//			ConsultaSQL sel1 = new ConsultaSelect(new String[] {"*"}, "actor");
//			ConsultaUpdate upd1 = new ConsultaUpdate("actor", new String[]{"last_name"},new String[]{"'pepito'"}, "id='1'");
//			// TODO Por que hay dos enters?
//			
//			System.out.println(sel1.toString());
//			System.out.println();
//			System.out.println(upd1.toString());
//		} catch (SQLException ex) {
//			Conector.informeErrorSQL(ex);
//		}
	}
}

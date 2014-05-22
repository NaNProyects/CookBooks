package utilsSQL;

import java.sql.ResultSet;

/**
 * @author Mokocchi
 * Define a los objetos que me voy a traer de la base
 */
public interface Cargable {
	
	public void cargarCon(ResultSet iterador);

}

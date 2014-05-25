package utilsSQL;

import java.sql.ResultSet;

/**
 * @author Mokocchi
 * Define a los objetos que me voy a traer de la base.
 * Ademas deben definir el constructor vacio.
 */
public interface Cargable {
	
	public void cargarCon(ResultSet iterador);
	
	public void guardarEn(Conector base);

}

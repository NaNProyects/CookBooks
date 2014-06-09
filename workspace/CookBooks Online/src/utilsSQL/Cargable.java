package utilsSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Define a los objetos que me voy a traer de la base.
 * Ademas deben definir:
 * <li> el constructor vacio.
 * <li> {@link #equals(Object)}
 * @author Mokocchi
 */
public interface Cargable {
	
	/**
	 * Arma el objeto sacando las columnas del iterador
	 * @param iterador
	 * @throws SQLException
	 */
	public void cargarCon(ResultSet iterador) throws SQLException;
	
	/**
	 * Guarda el objeto en la base indicada.
	 * <br>Deberia usar una {@link ConsultaABM}.
	 * @param base en la que guardo
	 * @throws SQLException 
	 */
	public void guardarEn(Conector base) throws SQLException;
	
	/**
	 * Borra el objeto de la base. Debería preguntar por {@link #esBorrableDe(Conector)}.
	 * @param base
	 * @throws SQLException
	 */
	public void borrarDe (Conector base) throws SQLException;
	
	/**
	 * @return si las limitaciones de foreign keys van a permitir la eliminacion
	 */ 
	public boolean esBorrableDe (Conector base) throws SQLException;

	
	/**
	 * 
	 * @param base
	 * @return si el objeto esta en la base o no
	 * @throws SQLException
	 */
	public boolean existeEn(Conector base) throws SQLException;

	/**
	 * Le da los retoques que no se puede en una pasada (xej listas)
	 * <p> Lo termina la base en el método {@link Conector.#iterarUn(Class)}
	 * <p> No me hago cargo de ninguna excepcion por ahora porque es interno
	 * @param base 
	 * @throws Exception 
	 */
	public void terminarCargaDe(Conector base) throws Exception;

	/**
	 * @return {@link ConsultaSelect} para buscar el propio objeto en la base.
	 * También se recomienda un static que devuelva todos.
	 */
	public ConsultaSelect getBuscador();
}

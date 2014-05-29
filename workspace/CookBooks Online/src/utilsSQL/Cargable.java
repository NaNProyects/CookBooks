package utilsSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mokocchi
 * Define a los objetos que me voy a traer de la base.
 * Ademas deben definir el constructor vacio.
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
	 * Borra el objeto de la base
	 * @param base
	 * @throws SQLException
	 */
	public void borrarDe (Conector base) throws SQLException;
	
	/**
	 * 
	 * @param base
	 * @return si el objeto esta en la base o no
	 * @throws SQLException
	 */
	public boolean existeEn(Conector base) throws SQLException;

}

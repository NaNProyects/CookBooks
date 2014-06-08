package utilsSQL;

import java.sql.SQLException;

/**
 * Representa una consulta SQL que posiblemente modificará una tabla
 * @author Mokocchi
 */
public class ConsultaABM extends ConsultaSQL {

	public ConsultaABM(String tabla) {
		super(tabla);
	}
	
	public void ejecutame(Conector consultor) throws SQLException{
		consultor.ejecutar(this);
	}
}

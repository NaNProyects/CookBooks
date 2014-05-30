package utilsSQL;

import java.sql.SQLException;

public class ConsultaABM extends ConsultaSQL {

	public ConsultaABM(String tabla) {
		super(tabla);
	}
	
	public void ejecutame(Conector consultor) throws SQLException{
		consultor.ejecutar(this);
	}
}

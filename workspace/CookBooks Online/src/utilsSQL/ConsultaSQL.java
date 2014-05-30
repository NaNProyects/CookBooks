package utilsSQL;

import java.sql.SQLException;
import java.util.List;

/**
 * Esta clase abstrae los parametros de una consulta SQL. Imprime con ToString.
 * 
 * @author Mokocchi
 * 
 */
public abstract class ConsultaSQL {
	protected String tabla;
	protected String condicion;

	public ConsultaSQL(String tabla) {
		this.tabla = tabla;
	}

	public String listarSeparandoPor(List<String> lista, String separador) {
		String result = "";
		for (int i = 0; i < lista.size() - 1; i++) {
			result += lista.get(i) + separador;
		}
		result += lista.get(lista.size() - 1);
		return result;
	}

	public static String concatenarConsultas(Iterable<ConsultaSQL> lista) {
		String result = "";
		for (ConsultaSQL consultaSQL : lista) {
			result+=consultaSQL.toString();
			result+=";\n\n";
		}
		return result;
	}
	
	public abstract void ejecutame(Conector consultor) throws SQLException;
}

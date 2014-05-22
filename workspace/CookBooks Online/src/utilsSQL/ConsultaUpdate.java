package utilsSQL;
import java.util.*;

public class ConsultaUpdate extends ConsultaAtributosValores {
	
	/**
	 * Chequea que #atributos = #valores
	 * @throws IllegalArgumentException
	 */
	public ConsultaUpdate(String tabla, String atributo, String valor, String condicion) throws IllegalArgumentException{
		super(tabla, atributo, valor);
	}
	
	public ConsultaUpdate(String tabla, List<String> atributos, List<String> valores, String condicion)
			throws IllegalArgumentException{
		super(tabla,atributos,valores);
		this.condicion = condicion;
	}
	
	@Override
	public String toString() {		
		// update TABLA SET attr=val, attr2=val2 WHERE
		return "update " + tabla + "\n" +
				"set " + getValores() + "\n" +
				(condicion!=""?"where "+condicion:"");
	}

	/**
	 * @return string con lista de pares (atributo=valor) para linea <code>SET</code>
	 */
	private String getValores() {
		String result = "";
		for (int i = 0; i < atributos.size(); i++) {
			result+=atributos.get(i)+"="+valores.get(i)+" ";
		}
		return result;
	}
}

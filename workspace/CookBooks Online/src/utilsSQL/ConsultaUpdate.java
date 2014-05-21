package utilsSQL;
import java.util.*;

public class ConsultaUpdate extends ConsultaABM {

	private ArrayList<String> valores;
	private ArrayList<String> atributos;
	
	/**
	 * Chequea que #atributos = #valores
	 * @throws IllegalArgumentException
	 */
	public ConsultaUpdate(String tabla, List<String> atributos, List<String> valores, String condicion) throws IllegalArgumentException{
		super(tabla);
		if (atributos.size()!=valores.size()){
			throw new IllegalArgumentException("Debe haber la misma cantidad de atributos que de valores.");
		}
		this.atributos = new ArrayList<String>(atributos);
		this.valores = new ArrayList<String>(valores);
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

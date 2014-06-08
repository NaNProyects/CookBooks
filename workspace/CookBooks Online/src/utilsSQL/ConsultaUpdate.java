package utilsSQL;
import java.util.*;

/**
 * Representa una consulta SQL de tipo update.
 * @author Mokocchi
 */
public class ConsultaUpdate extends ConsultaABM {
	
	private ArrayList<String> valores = new ArrayList<String>();
	private ArrayList<String> atributos = new ArrayList<String>();

	/**
	 * Chequea que #atributos = #valores
	 * @throws IllegalArgumentException
	 */
	public ConsultaUpdate(String tabla, String atributo, String valor, String condicion) throws IllegalArgumentException{
		super(tabla);
		this.atributos.add(atributo);
		this.valores.add(valor);
		this.condicion = condicion;
	}
	
	public ConsultaUpdate(String tabla, List<String> atributos, List<String> valores, String condicion)
			throws IllegalArgumentException{
		super(tabla);
		if ((atributos.size() != valores.size())) {
			throw new IllegalArgumentException(
					"Debe haber la misma cantidad de atributos que de valores.");
		}
		this.atributos.addAll(atributos);
		this.valores.addAll(valores);
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
		for (int i = 0; i < atributos.size() - 1; i++) {
			result+=atributos.get(i)+"="+valores.get(i)+", ";
		}
		return result+atributos.get(atributos.size()-1)+"="+valores.get(atributos.size()-1);
	}
}

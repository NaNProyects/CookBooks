package utilsSQL;

import java.util.*;

/**
 * Representa una consulta SQL de tipo insert.
 * @author Mokocchi
 */
public class ConsultaInsert extends ConsultaABM {

	private ArrayList<String> valores = new ArrayList<String>();
	private ArrayList<String> atributos = new ArrayList<String>();

	/**
	 * Insert sin el campo autoincremental
	 * 
	 * @param tabla
	 * @param atributos
	 * @param valores
	 * @throws IllegalArgumentException
	 */
	public ConsultaInsert(String tabla, List<String> atributos,
			List<String> valores) throws IllegalArgumentException {
		super(tabla);
		if (atributos != null) {
			if ((atributos.size() != valores.size())) {
				throw new IllegalArgumentException(
						"Debe haber la misma cantidad de atributos que de valores.");
			}
			this.atributos.addAll(atributos);
		}
		this.valores.addAll(valores);
	}

	/**
	 * Insert sin autoincremental para tablas de dos columnas
	 *  
	 * 
	 * @param tabla
	 * @param atributo
	 * @param valor
	 */
	public ConsultaInsert(String tabla, String atributo, String valor) {
		super(tabla);
		this.atributos.add(atributo);
		this.valores.add(valor);
	}

	/**
	 * Consulta que usa todos los campos (estandar)
	 * @param tabla
	 * @param valores
	 */
	public ConsultaInsert(String tabla, List<String> valores) {
		super(tabla);
		this.valores.addAll(valores);
	}

	@Override
	public String toString() {
		// insert into TABLA (atributos) value/s ({valores})
		return "insert into " + tabla + " "
				+ (atributos.size() != 0 ? "(" + getAtributos() + ")" : "")
				+ "\n" + "value" + (atributos.size() == 1 ? "" : "s") + "("
				+ getValores() + ")";
	}

	private String getValores() {
		return listarSeparandoPor(valores, ", ");
	}

	private String getAtributos() {
		return listarSeparandoPor(atributos, ", ");
	}

}

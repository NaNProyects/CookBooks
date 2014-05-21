package utilsSQL;

import java.util.*;

public class ConsultaInsert extends ConsultaABM {

	private ArrayList<String> valores;
	protected ArrayList<String> atributos;

	/**
	 * Chequea que #atributos = #valores
	 * 
	 * @throws IllegalArgumentException
	 */
	public ConsultaInsert(String tabla, List<String> atributos, List<String> valores)
			throws IllegalArgumentException {
		super(tabla);
		if (atributos.size() != valores.size()) {
			throw new IllegalArgumentException(
					"Debe haber la misma cantidad de atributos que de valores.");
		}
		this.atributos = new ArrayList<String>(atributos);
		this.valores = new ArrayList<String>(valores);
	}

	@Override
	public String toString() {
		// insert into TABLA (atributos) value/s ({valores})
		return "insert into " + tabla + " " + "(" + getAtributos() + ") \n"
				+ "value" + (atributos.size() == 1 ? "" : "s") + "("
				+ getValores() + ")";
	}

	private String getValores() {
		return listarSeparandoPor(valores, ", ");
	}

	private String getAtributos() {
		return listarSeparandoPor(atributos, ", ");
	}

}

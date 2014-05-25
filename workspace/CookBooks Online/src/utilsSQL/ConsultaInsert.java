package utilsSQL;

import java.util.*;

public class ConsultaInsert extends ConsultaABM {

	private ArrayList<String> valores = new ArrayList<String>();
	private ArrayList<String> atributos = new ArrayList<String>();

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

	public ConsultaInsert(String tabla, String atributo, String valor,
			String condicion) {
		super(tabla);
		this.atributos.add(atributo);
		this.valores.add(valor);
	}

	@Override
	public String toString() {
		// insert into TABLA (atributos) value/s ({valores})
		return "insert into " + tabla + " "
				+ (atributos.size() != 0 ? "(" + getAtributos() + ")" : "") + "\n"
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

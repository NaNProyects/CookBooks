package utilsSQL;

import java.util.*;

public class ConsultaSelect extends ConsultaSQL {

	private ArrayList<String> atributos;

	public ConsultaSelect(List<String> atributos, String tabla, String condicion) {
		super(tabla);
		this.condicion = condicion;
		this.atributos = new ArrayList<String>(atributos);
	}

	public ConsultaSelect(List<String> atributos, String tabla) {
		this(atributos, tabla, "");
	}

	@Override
	public String toString() {
		return "select " + atributos() + "\n" + "from " + tabla + "\n"
				+ (condicion != "" ? "where " + condicion : "");
	}

	/**
	 * @return lista de atributos separados por espacios
	 */
	protected String atributos() {
		return listarSeparandoPor(atributos, ", ");
	}
	
	public void ejecutame(Conector consultor){
		consultor.ejecutar(this);
	}
}

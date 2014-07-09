package utilsSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una consulta SQL de tipo select, con condicion opcional
 * @author Mokocchi
 */
public class ConsultaSelect extends ConsultaSQL {

	private ArrayList<String> atributos = new ArrayList<String>();

	public ConsultaSelect(List<String> atributos, String tabla, String condicion) {
		super(tabla);
		this.condicion = condicion;
		this.atributos.addAll(atributos);
	}

	public ConsultaSelect(String atributo, String tabla, String condicion) {
		super(tabla);
		this.condicion = condicion;
		this.atributos.add(atributo);
	}

	public ConsultaSelect(List<String> atributos, String tabla) {
		this(atributos, tabla, "");
	}

	public ConsultaSelect(String atributo, String tabla) {
		this(atributo, tabla, "");
	}
	
	public ConsultaSelect(String atributo) {
		this(atributo, "");
	}

	@Override
	public String toString() {
		return "select " + atributos() + "\n" + (tabla.equals("")? "" : "from " + tabla + "\n"
				+ (condicion != "" ? "where " + condicion : ""));
	}

	/**
	 * @return lista de atributos separados por espacios
	 */
	protected String atributos() {
		return listarSeparandoPor(atributos, ", ");
	}
}

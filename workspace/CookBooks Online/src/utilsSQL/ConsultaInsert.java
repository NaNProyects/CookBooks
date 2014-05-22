package utilsSQL;

import java.util.*;

public class ConsultaInsert extends ConsultaAtributosValores {

	public ConsultaInsert(String tabla, List<String> atributos,
			List<String> valores) throws IllegalArgumentException {
		super(tabla, atributos, valores);
	}
	
	public ConsultaInsert(String tabla, String atributo, String valor, String condicion) throws IllegalArgumentException{
		super(tabla, atributo, valor);
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

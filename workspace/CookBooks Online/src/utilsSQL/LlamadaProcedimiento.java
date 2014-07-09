package utilsSQL;

import java.util.*;

public class LlamadaProcedimiento {
	private String nombre;
	private List<String> parametros;

	public LlamadaProcedimiento(String nombre) {
		this(nombre, new LinkedList<String>());
	}

	public LlamadaProcedimiento(String nombre, String parametro) {
		this.nombre = nombre;
		this.parametros = new LinkedList<String>();
		this.parametros.add(parametro);
	}

	public LlamadaProcedimiento(String nombre, List<String> parametros) {
		this.nombre = nombre;
		this.parametros = parametros;
	}

	@Override
	public String toString() {
		String result = "CALL " + nombre;
		if (parametros.size() == 0) {
			return result;
		} else {
			result += "(" + parametros.get(0);
			for (int i = 1; i < parametros.size(); i++) {
				result += ", " + parametros.get(i);
			}
			return result + ")";
		}
	}

}

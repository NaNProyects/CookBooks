package utilsSQL;

import java.util.ArrayList;
import java.util.List;

public class ConsultaAtributosValores extends ConsultaABM {

	protected ArrayList<String> valores = new ArrayList<String>();
	protected ArrayList<String> atributos = new ArrayList<String>();

	/**
	 * Chequea que #atributos = #valores
	 * 
	 * @throws IllegalArgumentException
	 */
	public ConsultaAtributosValores(String tabla, List<String> atributos, List<String> valores)
			throws IllegalArgumentException {
				super(tabla);
				if (atributos.size() != valores.size()) {
					throw new IllegalArgumentException(
							"Debe haber la misma cantidad de atributos que de valores.");
				}
				this.atributos.addAll(atributos);
				this.valores.addAll(valores);
			}

	public ConsultaAtributosValores(String tabla, String atributo, String valor) {
		super(tabla);
		this.atributos.add(atributo);
		this.valores.add(valor);
	}

}
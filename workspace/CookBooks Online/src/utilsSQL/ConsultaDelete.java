/**
 * 
 */
package utilsSQL;

/**
 * @author Mokocchi
 *
 */
public class ConsultaDelete extends ConsultaABM {

	public ConsultaDelete(String tabla, String condicion) {
		super(tabla);
		this.condicion = condicion;
	}

	@Override
	public String toString() {
		return "delete from " + tabla + "\n" +
				(condicion!=""?"where "+condicion:"");
	}

}

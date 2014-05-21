package utilsSQL;

public class ConsultaABM extends ConsultaSQL {

	public ConsultaABM(String tabla) {
		super(tabla);
	}
	
	public void ejecutame(Conector consultor){
		consultor.ejecutar(this);
	}
}

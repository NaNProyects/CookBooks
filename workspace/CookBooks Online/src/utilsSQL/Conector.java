package utilsSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Esta clase abstrae las cosas de conexion a SQL y ANDA. Asi que usemos esto. <br>
 * Representa una conexion con la base, tiene un <code>consultor</code> que
 * ejecuta las consultas.
 * 
 * @author Mokocchi
 */
public class Conector {

	private Connection objetoConexion;
	private Statement consultor;
	private ResultSet iterador;

	/**
	 * @param baseAConectar
	 *            - Nombre String de la base
	 * @param llave
	 *            - Creada con
	 *            {@link utilsSQL.Conector#llaveCon(String, String)
	 *            llaveCon(String, String)}
	 * @throws SQLException
	 *             por errores varios xD
	 */
	public Conector(String baseAConectar, String user, String pass)
			throws SQLException {
		objetoConexion = DriverManager.getConnection(Conector
				.urlDesde(baseAConectar) + Conector.llaveCon(user, pass));
		consultor = objetoConexion.createStatement();
	}

	public static void informeErrorSQL(SQLException e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("VendorError: " + e.getErrorCode());
	}

	public void desconectar() {
		try {
			objetoConexion.close();
		} catch (SQLException e) {
			Conector.informeErrorSQL(e);
		}
	}

	/**
	 * @return url completa para acceder a <code>base</code> en <b>localhost</b>
	 */
	private static String urlDesde(String base) {
		return Conector.urlDesde("localhost", base);
	}

	/**
	 * @return url completa para acceder a <b>base</b> en <b>host</b>
	 */
	private static String urlDesde(String host, String base) {
		return "jdbc:mysql://" + host + "/" + base + "?";
	}

	/**
	 * @param user
	 * @param pass
	 * @return string de acceso para la base
	 */
	private static String llaveCon(String user, String pass) {
		return "user=" + user + "&password=" + pass;
	}

	/**
	 * @param consulta
	 * @return
	 */
	public void ejecutar(ConsultaSelect consulta) {
		try {
			iterador = consultor.executeQuery(consulta.toString());
		} catch (SQLException e) {
			Conector.informeErrorSQL(e);
		}
	}

	/**
	 * @param consulta
	 */
	public void ejecutar(ConsultaABM consulta) {
		try {
			consultor.execute(consulta.toString());
		} catch (SQLException e) {
			Conector.informeErrorSQL(e);
		}
	}

	public LinkedList<Cargable> iterarUn(Class<? extends Cargable> c) {
		LinkedList<Cargable> result = new LinkedList<Cargable>();
		try {
			while (iterador.next()) //itera por filas
			{
				Cargable item = c.newInstance();
				item.cargarCon(iterador);
				result.add(item);
			}
		} catch (SQLException e) {
			Conector.informeErrorSQL(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}

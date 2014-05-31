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
	public String ultimoError;
	private String url; //para reconectar


	/**
	 * @param baseAConectar
	 * @param user
	 * @param pass
	 * @throws SQLException
	 */
	public Conector(String baseAConectar, String user, String pass)
			throws SQLException {
		url = Conector
				.urlDesde(baseAConectar) + Conector.llaveCon(user, pass);
		objetoConexion = DriverManager.getConnection(url);
		consultor = objetoConexion.createStatement();
	}

	public static void informeErrorSQL(SQLException e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("VendorError: " + e.getErrorCode());
	}

	public void desconectar() throws SQLException {
		try {
			objetoConexion.close();
		} catch (SQLException e) {
			this.ultimoError = e.getMessage();
			throw e;
		}
	}
	
	public void reconectar() throws SQLException {
		objetoConexion = DriverManager.getConnection(url);
		consultor = objetoConexion.createStatement();
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
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void ejecutar(ConsultaSelect consulta) throws SQLException {
		try {
			iterador = consultor.executeQuery(consulta.toString());
		} catch (SQLException e) {
			this.ultimoError = e.getMessage();
			throw e;
		}
	}

	/**
	 * @param consulta
	 * @throws SQLException 
	 */
	public void ejecutar(ConsultaABM consulta) throws SQLException {
		try {
			consultor.execute(consulta.toString());
		} catch (SQLException e) {
			this.ultimoError = e.getMessage();
			throw e;
		}
	}

	public LinkedList<Cargable> iterarUn(Class<? extends Cargable> c) throws Exception {
		LinkedList<Cargable> result = new LinkedList<Cargable>();
		try {
			while (iterador.next()) //itera por filas
			{
				Cargable item = c.newInstance();
				item.cargarCon(iterador);
				result.add(item);
			}
		} catch (SQLException e) {
			this.ultimoError = e.getMessage();
			throw e;
		} catch (InstantiationException e) {
			throw new Exception("No me mandaste una clase compatible");
		} catch (IllegalAccessException e) {
			throw new Exception("No me mandaste una clase compatible");
		}
		for (Cargable cargable : result) {
			cargable.terminar();
		}
		return result;
	}
	
	public Integer getFirstInt() throws SQLException {
		if(iterador.first()) {
			return iterador.getInt(1);
		} else {
			return null;
		}
	}
	
	public Long getFirstLong() throws SQLException {
		if(iterador.first()) {
			return iterador.getLong(1);
		} else {
			return null;
		}
	}
	
	public String getFirstString() throws SQLException {
		if(iterador.first()) {
			return iterador.getString(1);
		} else {
			return null;
		}
	} 
	
}

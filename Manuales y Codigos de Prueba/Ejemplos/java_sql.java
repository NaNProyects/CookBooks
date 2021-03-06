package TestSQL;

import java.sql.*;
public class Test1 {

   	private static Connection objetoConexion;
   
	private static String baseAConectar="sakila";
	
	private static String host="localhost"; //puede ser IP
	
	private static String server="jdbc:mysql://"+host+"/"+baseAConectar; //ruta

	public static void main(String[] args) {
		
//		Timestamp lastM = Timestamp.valueOf("2007-09-23 00:00:00");

		//conectar
	        try {
	            Class.forName("com.mysql.jdbc.Driver"); //si existe este coso (?????)

	            objetoConexion = DriverManager.getConnection(server,"root","qwerty"); //que feo el harcode

	            System.out.println("Conexión a base de datos "+server+" ... OK");
	 
	        } catch (ClassNotFoundException ex) {
	            System.out.println("Error cargando el Driver MySQL JDBC ... FAIL");
	        } catch (SQLException ex) {
	            System.out.println("Imposible realizar conexion con "+server+" ... FAIL");
	        }
	 
	        //realizar consulta
	        try {
	            // Preparamos la consulta
	            Statement s = objetoConexion.createStatement(); //mi conexion escupe una sentencia
	            String sql = "insert into actor values (1, 'Johny', 'Depp', '2007-09-23 00:00:00.0')"; //comillaaaas
	            s.executeUpdate(sql);
	            sql = "select * from actor";
	            ResultSet rs = s.executeQuery (sql); //guarda en un "set" iterable
	 
	            // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla.
	            while (rs.next()) //itera
	            {
	                System.out.println( //indices o nombres
	                        "ID: " +rs.getInt (1) +
	                        "tNombre: " + rs.getString (2)+
	                        "tApellido: " + rs.getString("last_name") +
	                        "tFecha: " + rs.getTimestamp(4)
	                        );
	            }
	        } catch (SQLException ex) {
	            System.out.println("Imposible realizar consulta ... FAIL");
	        }
	 
	        //desconectar
	        try {
	            objetoConexion.close();
	            System.out.println("Cerrar conexion con "+server+" ... OK");
	        } catch (SQLException ex) {
	            System.out.println("Imposible cerrar conexion ... FAIL");
	        }
	    }
}
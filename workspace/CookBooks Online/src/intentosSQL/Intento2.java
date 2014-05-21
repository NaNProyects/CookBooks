package intentosSQL;
import java.sql.*;

/*test limpio*/
// SET PASSWORD FOR root@localhost=PASSWORD('qwerty');
// <3
public class Intento2 {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/sakila?";
		String claves = "user=root&password=qwerty";
		try {
		    Connection conn = DriverManager.getConnection(url + claves);
		    // Do something with the Connection
		    Statement s = conn.createStatement(); //mi conexion escupe una sentencia
		    String sql;
//		    sql = "insert into actor (first_name, last_name, last_update) values ('Johny', 'Depp', '2007-09-23 00:00:00.0')"; //comillaaaas
//            s.executeUpdate(sql);
            
            sql = "select * from actor";
            ResultSet rs = s.executeQuery (sql); //guarda en un "set" iterable
 
            // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla.
            while (rs.next()) //itera
            {
                System.out.println( //indices o nombres
                        "ID: " +rs.getInt (1) + " " +
                        "tNombre: " + rs.getString (2)+ " " +
                        "tApellido: " + rs.getString("last_name") + " " +
                        "tFecha: " + rs.getTimestamp(4)
                        );
            }
            System.out.println("TODO OK");
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}

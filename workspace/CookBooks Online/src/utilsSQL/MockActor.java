package utilsSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MockActor implements Cargable {
	private int nro;
	private String name;
	private String surname;
	private Date updatedAt;

	public int getNro() {
		return nro;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	/* (non-Javadoc)
	 * @see utilsSQL.Cargable#cargarCon(java.sql.ResultSet)
	 */
	public void cargarCon(ResultSet iterador) {
		try {
			this.nro = iterador.getInt ("actor_id");
			this.name = iterador.getString ("first_name");
			this.surname = iterador.getString("last_name");
			this.updatedAt = iterador.getDate("last_update");
		} catch (SQLException e) {
			Conector.informeErrorSQL(e);
		}
	}
	
	@Override
	public String toString() {
		return "#"+nro+":"+" "+name+" "+surname+"//"+updatedAt;
	}

}

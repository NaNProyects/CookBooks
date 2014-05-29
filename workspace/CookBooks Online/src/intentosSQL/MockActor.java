package intentosSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import utilsSQL.Cargable;
import utilsSQL.Conector;
import utilsSQL.ConsultaInsert;

public class MockActor implements Cargable {

	public MockActor() {
		super();
	}

	public MockActor(int nro, String name, String surname, Date updatedAt) {
		this.nro = nro;
		this.name = name;
		this.surname = surname;
		this.updatedAt = updatedAt;
	}

	private Integer nro;
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
	
	

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public void guardarEn(Conector base) throws SQLException {
		ArrayList<String> val = new ArrayList<String>();
		val.add(nro.toString());
		val.add("'"+name+"'");
		val.add("'"+surname+"'");
		val.add("'"+updatedAt.toString()+"'");
		ConsultaInsert ins = new ConsultaInsert("actor",null, val);
		System.out.println(ins);
		base.ejecutar(ins);
	}

	public void borrarDe(Conector base) {
		// TODO Auto-generated method stub
		
	}

	public boolean existeEn(Conector base) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}

package funcionalidad;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilsSQL.Conector;
import utilsSQL.ConsultaDelete;
import utilsSQL.ConsultaSelect;

public class UsuarioTest {
	private Usuario usuario1;
	private Usuario usuario2;
	private LinkedList<Usuario> lista1 = new LinkedList<Usuario>();
	private LinkedList<Usuario> lista2 = new LinkedList<Usuario>();
	private static Conector cookbooks;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cookbooks = new Conector("cookbooksbase", "root", "qwerty");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		cookbooks.desconectar();
	}

	@Before
	public void setUp() throws Exception {
		ConsultaSelect sel1 = new ConsultaSelect("dni", "usuario", "dni = 1");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("usuario", "dni IN (" + sel2
				+ ")");
		cookbooks.ejecutar(del);
		usuario1 = new Usuario(1, "4241111", "1234567887654321","0000",
				Date.valueOf("2000-1-1"), "Demo nº000",
				"76b5e9c04b3c45253bfcde488623a01b",
				"nombredemoapellidodemo@cookbooks.com", "NombreDemo",
				"ApellidoDemo");
	}

	@After
	public void tearDown() throws Exception {
		ConsultaSelect sel1 = new ConsultaSelect("dni", "usuario", "dni = 1");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("usuario", "dni IN (" + sel2
				+ ")");
		cookbooks.ejecutar(del);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCargarCon() {
		/*
		 * depende de que N.G. este en la base try { ConsultaSelect sel = new
		 * ConsultaSelect("*", "usuario", "dni = 2968741");
		 * cookbooks.ejecutar(sel); lista2 = new LinkedList<Usuario>(
		 * (Collection<? extends Usuario>) cookbooks .iterarUn(Usuario.class));
		 * usuario1 = lista2.element(); assertTrue("El dni no coincide",
		 * usuario1.getDni().equals(2968741));
		 * assertTrue("El telefono no coincide", usuario1.getTelefono()
		 * .equals(4708741)); assertTrue("La tarjeta no coincide",
		 * usuario1.getTarjeta().equals("2968123456788741"));
		 * assertTrue("La fecha no coincide", usuario1.getFechaRegistro()
		 * .equals(Date.valueOf("2006-06-06")));
		 * assertTrue("La direccion no coincide", usuario1.getDireccion()
		 * .equals("29 nº741")); assertTrue("La contraseña no coincide",
		 * usuario1.getHashPass() .equals("d9fd932e114c21309e61c08496bdc78e"));
		 * assertTrue("El email no coincide",
		 * usuario1.getEmail().equals("nicolasgaldamez@cookbooks.com"));
		 * assertTrue("El nombre no coincide",
		 * usuario1.getNombre().equals("Nicolas"));
		 * assertTrue("El apellido no coincide", usuario1.getApellido()
		 * .equals("Galdamez")); } catch (Exception e) { fail("auch. " +
		 * e.getMessage()); }
		 */

		try { // setup
			usuario1 = new Usuario(1, "4241111", "1234567887654321","0000",
					Date.valueOf("2000-1-1"), "Demo nº000",
					"76b5e9c04b3c45253bfcde488623a01b",
					"nombredemoapellidodemo1@cookbooks.com", "NombreDemo1",
					"ApellidoDemo1");
			lista1.add(usuario1);
			usuario1.guardarEn(cookbooks);
			usuario1 = new Usuario(2, "4241111", "1234567887654321","0000",
					Date.valueOf("2000-1-1"), "Demo nº000",
					"76b5e9c04b3c45253bfcde488623a01b",
					"nombredemoapellidodemo2@cookbooks.com", "NombreDemo2",
					"ApellidoDemo2");
			lista1.add(usuario1);
			usuario1.guardarEn(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		// pruebas

		try {
			ConsultaSelect sel = new ConsultaSelect("*", "usuario",
					"nombre LIKE 'NombreDemo%'");
			cookbooks.ejecutar(sel);
			lista2 = new LinkedList<Usuario>(
					(Collection<? extends Usuario>) cookbooks
							.iterarUn(Usuario.class));
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		assertTrue(
				"es diferente la cantidad de usuarios que entraron que los que salieron",
				lista1.size() == lista2.size());

		for (int i = 0; i < lista1.size(); i++) {
			assertTrue("El dni no coincide",
					lista1.get(i).getDni().equals(lista2.get(i).getDni()));
			assertTrue("El telefono no coincide", lista1.get(i).getTelefono()
					.equals(lista2.get(i).getTelefono()));
			assertTrue("La tarjeta no coincide",
					lista1.get(i).getTarjeta().equals(lista2.get(i).getTarjeta()));
			assertTrue("El pin no coincide",
					lista1.get(i).getPin().equals(lista2.get(i).getPin()));
			assertTrue("La fecha no coincide", lista1.get(i).getFechaRegistro()
					.equals(lista2.get(i).getFechaRegistro()));
			assertTrue("La direccion no coincide", lista1.get(i).getDireccion()
					.equals(lista2.get(i).getDireccion()));
			assertTrue("La contraseña no coincide", lista1.get(i).getHashPass()
					.equals(lista2.get(i).getHashPass()));
			assertTrue("El email no coincide",
					lista1.get(i).getEmail().equals(lista2.get(i).getEmail()));
			assertTrue("El nombre no coincide",
					lista1.get(i).getNombre().equals(lista2.get(i).getNombre()));
			assertTrue("El apellido no coincide", lista1.get(i).getApellido()
					.equals(lista2.get(i).getApellido()));
		}
		for (Usuario u : lista1) {
			try {
				u.borrarDe(cookbooks);
			} catch (SQLException e) {
				fail("auch. " + e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGuardarEn() {
		/*
		 * a) no exista antes, lo guardo y que este despues
		 */
		try {
			assertFalse("No deberia estar en la base",
					usuario1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			usuario1.guardarEn(cookbooks);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				fail("el usuario ya estaba");
			} else {
				fail("auch. " + e.getMessage());
			}
		}

		try {
			assertTrue("Debería haberse cargado", usuario1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			cookbooks.ejecutar(usuario1.getBuscador());
			lista1 = new LinkedList<Usuario>(
					(Collection<? extends Usuario>) cookbooks
							.iterarUn(Usuario.class));
			usuario2 = lista1.element();
			assertTrue("El dni no coincide",
					usuario1.getDni().equals(usuario2.getDni()));
			assertTrue("El telefono no coincide", usuario1.getTelefono()
					.equals(usuario2.getTelefono()));
			assertTrue("La tarjeta no coincide",
					usuario1.getTarjeta().equals(usuario2.getTarjeta()));
			assertTrue("El pin no coincide",
					usuario1.getPin().equals(usuario1.getPin()));
			assertTrue("La fecha no coincide", usuario1.getFechaRegistro()
					.equals(usuario2.getFechaRegistro()));
			assertTrue("La direccion no coincide", usuario1.getDireccion()
					.equals(usuario2.getDireccion()));
			assertTrue("La contraseña no coincide", usuario1.getHashPass()
					.equals(usuario2.getHashPass()));
			assertTrue("El email no coincide",
					usuario1.getEmail().equals(usuario2.getEmail()));
			assertTrue("El nombre no coincide",
					usuario1.getNombre().equals(usuario2.getNombre()));
			assertTrue("El apellido no coincide", usuario1.getApellido()
					.equals(usuario2.getApellido()));

		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		/*
		 * b) si ya esta al guardar de vuelta que tire exception
		 */

		try {
			usuario2 = new Usuario(1, "4241111", "1234567887654321","0000",
					Date.valueOf("2000-1-1"), "Demo nº000",
					"76b5e9c04b3c45253bfcde488623a01b",
					"nombredemoapellidodemo@cookbooks.com", "NombreDemo",
					"ApellidoDemo");
			usuario2.guardarEn(cookbooks);
			fail("el usuario no se puede guardar dos veces");
		} catch (SQLException e) {
			// OK
		}

		/*
		 * c) si ya esta invierto el numero y updatea
		 */
		try {
			usuario1.setNombre("NombreDemoC");
			usuario1.setDni(usuario1.getDni() * -1); // invierto porque modifico
			usuario1.guardarEn(cookbooks);
			assertTrue(usuario1.existeEn(cookbooks));
			cookbooks.ejecutar(usuario1.getBuscador());

			lista1 = new LinkedList<Usuario>(
					(Collection<? extends Usuario>) cookbooks
							.iterarUn(Usuario.class));
			usuario2 = lista1.element();
			assertTrue("El dni no coincide",
					usuario1.getDni().equals(usuario2.getDni()));
			assertTrue("El telefono no coincide", usuario1.getTelefono()
					.equals(usuario2.getTelefono()));
			assertTrue("La tarjeta no coincide",
					usuario1.getTarjeta().equals(usuario2.getTarjeta()));
			assertTrue("El pin no coincide",
					usuario1.getPin().equals(usuario1.getPin()));
			assertTrue("La fecha no coincide", usuario1.getFechaRegistro()
					.equals(usuario2.getFechaRegistro()));
			assertTrue("La direccion no coincide", usuario1.getDireccion()
					.equals(usuario2.getDireccion()));
			assertTrue("La contraseña no coincide", usuario1.getHashPass()
					.equals(usuario2.getHashPass()));
			assertTrue("El email no coincide",
					usuario1.getEmail().equals(usuario2.getEmail()));
			assertTrue("El nombre no coincide",
					usuario1.getNombre().equals(usuario2.getNombre()));
			assertTrue("El apellido no coincide", usuario1.getApellido()
					.equals(usuario2.getApellido()));

		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		// limpio
		try {
			usuario1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBorrarDe() {
		try {
			usuario1.borrarDe(cookbooks);
			fail("no se puede borrar algo que no está");
		} catch (SQLException e) {
			// OK
		}

		try {
			usuario1.guardarEn(cookbooks);
			assertTrue("El usuario tiene que estar antes",
					usuario1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			usuario1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			assertFalse("El usuario ya no tiene que estar",
					usuario1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}
		
		try { // TODO reemplazar por un pedido temporal
			ConsultaSelect sel = new ConsultaSelect("*",
					"usuario inner join Pedido", "idUsuario = 3");
			cookbooks.ejecutar(sel);
			lista1 = new LinkedList<Usuario>(
					(Collection<? extends Usuario>) cookbooks
							.iterarUn(Usuario.class));
			lista1.element().borrarDe(cookbooks);
			fail("Si esta en un pedido no se puede borrar");
		} catch (Exception e) {
			// OK
		}
	}

	@Test
	public void testExisteEn() {
		try {
			assertFalse("no tiene que estar", usuario1.existeEn(cookbooks));
			usuario1.guardarEn(cookbooks);
			assertTrue("tiene que estar en la base",
					usuario1.existeEn(cookbooks));
			usuario1.borrarDe(cookbooks);
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}
	}

}

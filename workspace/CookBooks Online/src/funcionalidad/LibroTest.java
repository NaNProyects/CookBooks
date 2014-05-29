package funcionalidad;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilsSQL.Conector;
import utilsSQL.ConsultaDelete;
import utilsSQL.ConsultaSelect;

public class LibroTest {
	private Libro libro1;
	private Autor autor1;
	private Libro libro2;
	private LinkedList<Libro> lista;
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
		ConsultaSelect sel1 = new ConsultaSelect("titulo", "libro",
				"titulo = 'LIBRO Demo'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("libro", "titulo IN (" + sel2
				+ ")");
		cookbooks.ejecutar(del);
		if (autor1.existeEn(cookbooks))
			autor1.borrarDe(cookbooks);
		autor1 = new Autor(-1, "AUTOR Demo");
		autor1.guardarEn(cookbooks);
		libro1 = new Libro((long) -1, "LIBRO Demo", "AUTOR Demo", "generoDemo",
				"editorialDemo", "idiomaDemo", "reseñaDemo", "vistazoDemo", 0.0);
	}

	@After
	public void tearDown() throws Exception {
		ConsultaSelect sel1 = new ConsultaSelect("titulo", "libro",
				"titulo = 'LIBRO Demo'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("libro", "titulo IN (" + sel2
				+ ")");
		try {
			cookbooks.ejecutar(del);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGuardarEn() {
		try {
			assertFalse("No deberia estar en la base",
					libro1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			libro1.guardarEn(cookbooks);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				fail("el libro ya estaba");
			} else {
				fail("auch. " + e.getMessage());
			}
		}

		try {
			assertTrue("Debería haberse cargado", libro1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		/*
		 * b) si ya esta al guardar de vuelta que tire exception (todavia no se
		 * cual por eso hago esto)
		 */

		try {
			libro2 = new Libro((long) -1, "LIBRO Demo", "AUTOR Demo", "generoDemo",
					"editorialDemo", "idiomaDemo", "reseñaDemo", "vistazoDemo", 0.0);
			libro2.guardarEn(cookbooks);
			fail("el libro no se puede guardar dos veces");
		} catch (SQLException e) {
			// OK
		}
		
		
		
		
	}

	@Test
	public void testCargarCon() {
		fail("Not yet implemented");
	}

	@Test
	public void testBorrar() {
		try {
			libro1.borrarDe(cookbooks);
			fail("no se puede borrar algo que no está");
		} catch (SQLException e) {
			// OK
		}

		try {
			autor1.guardarEn(cookbooks);
			assertTrue("El autor tiene que estar antes",
					autor1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			autor1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			assertFalse("El autor ya no tiene que estar",
					autor1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}
	}

}

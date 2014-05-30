package funcionalidad;

import static org.junit.Assert.*;

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

public class LibroTest {
	private Libro libro1;
	private Autor autor1;
	private Libro libro2;
	private LinkedList<Libro> lista1 = new LinkedList<Libro>();
	private LinkedList<Libro> lista2 = new LinkedList<Libro>();
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
		autor1 = new Autor(-1, "AUTOR Demo");
		if (autor1.existeEn(cookbooks))
			autor1.borrarDe(cookbooks);
		autor1.guardarEn(cookbooks);
		libro1 = new Libro((long) 1, "LIBRO Demo", "AUTOR Demo", "generoDemo",
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
			autor1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCargarCon() {
		try { // setup
			libro1 = new Libro((long) 2, "LIBRO Demo2", "AUTOR Demo",
					"generoDemo", "editorialDemo", "idiomaDemo", "reseñaDemo",
					"vistazoDemo", 0.0);
			lista1.add(libro1);
			libro1.guardarEn(cookbooks);
			libro1 = new Libro((long) 3, "LIBRO Demo3", "AUTOR Demo",
					"generoDemo", "editorialDemo", "idiomaDemo", "reseñaDemo",
					"vistazoDemo", 0.0);
			lista1.add(libro1);
			libro1.guardarEn(cookbooks);
			ConsultaSelect sel = new ConsultaSelect("*", "libro inner join autor on autor = idAutor",
					"titulo LIKE 'LIBRO Demo%'");
			cookbooks.ejecutar(sel);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		// pruebas

		try {
			lista2.addAll((Collection<? extends Libro>) cookbooks
					.iterarUn(Libro.class));
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		assertTrue(
				"es diferente la cantidad de libros que entraron que los que salieron",
				lista1.size() == lista2.size());

		for (int i = 0; i < lista1.size(); i++) {
			assertTrue("hay un libro que no coincide",
					lista2.contains(lista1.get(i)));
		}

		// limpiando
		for (Libro l : lista1) {
			try {
				l.borrarDe(cookbooks);
			} catch (SQLException e) {
				fail("auch. " + e.getMessage());
			}
		}
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
			libro1.guardarEn(cookbooks);
			assertTrue("El libro tiene que estar antes",
					libro1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			libro1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			assertFalse("El libro ya no tiene que estar",
					libro1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}
	}

	@Test
	public void testExisteEn() {
		try {
			assertFalse("no tiene que estar", libro1.existeEn(cookbooks));
			libro1.guardarEn(cookbooks);
			assertTrue("tiene que estar en la base", libro1.existeEn(cookbooks));
			libro1.borrarDe(cookbooks);
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}
	}

	@Test
	public void testGuardarEn() {
		/*
		 * a) no exista antes, lo guardo y que este despues
		 */
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
		 * b) si ya esta al guardar de vuelta que tire exception
		 */

		try {
			libro2 = new Libro((long) 1, "LIBRO Demo", "AUTOR Demo",
					"generoDemo", "editorialDemo", "idiomaDemo", "reseñaDemo",
					"vistazoDemo", 0.0);
			libro2.guardarEn(cookbooks);
			fail("el libro no se puede guardar dos veces");
		} catch (SQLException e) {
			// OK
		}

		/*
		 * c) si ya esta pero tengo el numero updatea
		 */
		try {
			libro1.setTitulo("Libro DemoC");
			libro1.setIsbn(libro1.getIsbn() * -1); // invierto porque modifico
			libro1.guardarEn(cookbooks);
			assertTrue(libro1.existeEn(cookbooks));
			assertFalse(libro2.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		// limpio
		try {
			libro1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}
	}

}

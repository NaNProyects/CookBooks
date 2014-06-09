package funcionalidad;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilsSQL.*;

public class AutorTest {
	private Autor autor1;
	private Autor autor2;
	private LinkedList<Autor> lista1 = new LinkedList<Autor>();
	private LinkedList<Autor> lista2 = new LinkedList<Autor>();
	private Libro libro1;
	private static Conector cookbooks;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cookbooks = new Conector("cookbooksbase", "root", "qwerty");
	}

	@Before
	public void setUp() throws Exception {
		ConsultaSelect sel1 = new ConsultaSelect("idAutor", "autor",
				"nombre = 'AUTOR' and apellido = 'Demo'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("autor", "idAutor IN (" + sel2
				+ ")");
		cookbooks.ejecutar(del);
		autor1 = new Autor(-1, "AUTOR", "Demo");
		libro1 = new Libro("000000000001", "LIBRO Demo", autor1, "generoDemo",
				"editorialDemo", "idiomaDemo", "reseñaDemo", "vistazoDemo", 0.0);
	}

	@After
	public void tearDown() throws Exception {
		if (libro1.existeEn(cookbooks))
			libro1.borrarDe(cookbooks);
		ConsultaSelect sel1 = new ConsultaSelect("idAutor", "autor",
				"nombre = 'AUTOR' and apellido = 'Demo'");
		ConsultaSelect sel2 = new ConsultaSelect("*", "(" + sel1 + ") as tmp");
		ConsultaDelete del = new ConsultaDelete("autor", "idAutor IN (" + sel2
				+ ")");
		try {
			cookbooks.ejecutar(del);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		cookbooks.desconectar();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCargarCon() {
		try { // setup
			autor1 = new Autor(-1, "AUTOR", "Demo2");
			lista1.add(autor1);
			autor1.guardarEn(cookbooks);
			autor1 = new Autor(-1, "AUTOR", "Demo3");
			lista1.add(autor1);
			autor1.guardarEn(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		// pruebas

		try {
			ConsultaSelect sel = new ConsultaSelect("*", "autor",
					"apellido LIKE 'Demo%'");
			cookbooks.ejecutar(sel);

			lista2.addAll((Collection<? extends Autor>) cookbooks
					.iterarUn(Autor.class));
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		assertTrue(
				"es diferente la cantidad de autores que entraron que los que salieron",
				lista1.size() == lista2.size());

		for (int i = 0; i < lista1.size(); i++) {
			assertTrue("hay un autor que no coincide",
					lista2.contains(lista1.get(i)));
		}

		// limpiando
		for (Autor a : lista1) {
			try {
				a.borrarDe(cookbooks);
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
					autor1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			autor1.guardarEn(cookbooks);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				fail("el autor ya estaba");
			} else {
				fail("auch. " + e.getMessage());
			}
		}

		try {
			assertTrue("Debería haberse cargado", autor1.existeEn(cookbooks));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

		try {
			cookbooks.ejecutar(autor1.getBuscador());
			lista1 = new LinkedList<Autor>(
					(Collection<? extends Autor>) cookbooks
							.iterarUn(Autor.class));
			autor2 = lista1.element();
			assertTrue("El id no coincide", autor1.id().equals(autor2.id()));
			assertTrue("El nobmre no coincide",
					autor1.getNombre().equals(autor2.getNombre()));
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		/*
		 * b) si ya esta al guardar de vuelta que tire exception
		 */

		try {
			autor2 = new Autor(-1, "AUTOR", "Demo");
			autor2.guardarEn(cookbooks);
			fail("el autor no se puede guardar dos veces");
		} catch (SQLException e) {
			// OK
		}

		/*
		 * c) si ya esta pero tengo el numero updatea
		 */
		try {
			autor1.setNombre("AUTOR DemoC");
			autor1.guardarEn(cookbooks);
			assertTrue("El autor no se cargo", autor1.existeEn(cookbooks));
			cookbooks.ejecutar(autor1.getBuscador());
			lista1 = new LinkedList<Autor>(
					(Collection<? extends Autor>) cookbooks
							.iterarUn(Autor.class));
			autor2 = lista1.element();
			assertTrue("El id no coincide", autor1.id().equals(autor2.id()));
			assertTrue("El nobmre no coincide",
					autor1.getNombre().equals(autor2.getNombre()));
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}

		// limpio
		try {
			autor1.borrarDe(cookbooks);
		} catch (SQLException e) {
			fail("auch. " + e.getMessage());
		}

	}

	@Test
	public void testBorrar() {
		try {
			autor1.borrarDe(cookbooks);
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

		try {
			autor1 = new Autor(-1, "AUTOR", "Demo");
			autor1.guardarEn(cookbooks);
			libro1.guardarEn(cookbooks); // tiene a este autor
			autor1.borrarDe(cookbooks);
			fail("Si tiene libros no se puede borrar");
		} catch (Exception e) {
			// OK
		}
	}

	@Test
	public void testExisteEn() {
		try {
			assertFalse("no tiene que estar", autor1.existeEn(cookbooks));
			autor1.guardarEn(cookbooks);
			assertTrue("tiene que estar en la base", autor1.existeEn(cookbooks));
			// limpio
			autor1.borrarDe(cookbooks);
		} catch (Exception e) {
			fail("auch. " + e.getMessage());
		}
	}

}

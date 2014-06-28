package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;
import funcionalidad.Libro;

@SuppressWarnings("serial")
public class MedioEdicionDeLibro extends MedioPanel {
	private String tituloPanel = "Nuevo Libro";
	private Libro libro;
	private TextField tituloLibro;
	private JComboBox<String> autorLibro;
	private TextField generoLibro;
	private TextField editorialLibro;
	private TextField idiomaLibro;
	private JFormattedTextField precioLibro;
	private Interface inside;
	private TextField isbnLibro_1;
	private JTextPane reseñaLibro;
	private JTextPane vistasoLibro;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private MedioPanel anterior;
	private JTextPane errorISBN;
	private JTextPane errorTitulo;
	private JTextPane errorIdioma;
	private JTextPane errorGenero;
	private JTextPane errorEditorial;
	private JTextPane errorAutor;
	private JTextPane errorPrecio;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioEdicionDeLibro(Interface inside2, MedioPanel ant) {
		this(inside2, new Libro("0", "", null, "", "",
				"", "", "", new Double(0)),ant); 
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MedioEdicionDeLibro(Interface inside2, Libro libro2 , MedioPanel ant) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		libro = libro2;
		anterior = ant;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 523, 333, 50);
		add(labelErrores);

		isbnLibro_1 = new TextField();
		isbnLibro_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)|| isbnLibro_1.getText().length() == 45) {
					e.consume();
				}
			}
		});
		;
		isbnLibro_1.setText(libro.getIsbn().toString());
		isbnLibro_1.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadISBN();
			}
		});

		isbnLibro_1.setText(libro.getIsbn().toString());
		isbnLibro_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		isbnLibro_1.setBounds(108, 57, 184, 20);
		if (libro.getIsbn() != "0") {
			isbnLibro_1.setEditable(false);
		}
		add(isbnLibro_1);

		tituloLibro = new TextField();
		tituloLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadTitulo();
			}
		});
		tituloLibro.setText(libro.getTitulo());

		if (!isbnLibro_1.isEditable()) {
			tituloPanel = "Editar Libro";
		}
		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				tituloPanel);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		tituloLibro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tituloLibro.getText().length() == 45) {
					e.consume();
				}
			}
		});
		
		tituloLibro.setBounds(108, 109, 184, 20);
		add(tituloLibro);

		autorLibro = new JComboBox<String>();
		autorLibro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ValidadAutor();
			}
		});
		try {
			autorLibro.setModel(new DefaultComboBoxModel(
					NombresAutores(inside.contexto.listarAutores())));
		} catch (Exception e2) {
			printError(e2.getMessage().concat(" /n"),labelErrores, true);
		}
		autorLibro.setBounds(108, 327, 184, 20);
		autorLibro.setSelectedItem(libro.getAutor());
		add(autorLibro);

		generoLibro = new TextField();
		generoLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadGenero();
			}
		});
		generoLibro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isLetter(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE || Character.isWhitespace(car))|| generoLibro.getText().length() == 45) {
					e.consume();
				}
			}
		});
		generoLibro.setText(libro.getGenero());

		generoLibro.setBounds(108, 213, 184, 20);
		add(generoLibro);

		editorialLibro = new TextField();
		editorialLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadEditorial();
			}
		});
		editorialLibro.setText(libro.getEditorial());
		editorialLibro.setBounds(108, 270, 184, 20);
		editorialLibro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isLetter(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE || Character.isWhitespace(car))|| editorialLibro.getText().length() == 45) {
					e.consume();
				}
			}
		});
		add(editorialLibro);
		

		idiomaLibro = new TextField();
		idiomaLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadIdioma();
			}
		});
		idiomaLibro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isLetter(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE || Character.isWhitespace(car))|| idiomaLibro.getText().length() == 45) {
					e.consume();
				}
			}
		});
		idiomaLibro.setText(libro.getIdioma());
		idiomaLibro.setBounds(108, 161, 184, 20);
		add(idiomaLibro);

		precioLibro = new JFormattedTextField(new DecimalFormat("0.00"));
		if (!isbnLibro_1.isEditable()) {
			precioLibro.setValue(libro.getPrecio());
		}
		precioLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					precioLibro.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				ValidadPrecio();

			}
		});
		precioLibro.setBounds(108, 389, 184, 20);
		add(precioLibro);

		reseñaLibro = new JTextPane();
		reseñaLibro.setText(libro.getReseña());
		reseñaLibro.setBounds(10, 339, 503, 191);
		add(reseñaLibro);

		vistasoLibro = new JTextPane();
		vistasoLibro.setText(libro.getVistaso());
		vistasoLibro.setBounds(375, 307, 503, 266);
		add(vistasoLibro);

		JLabel isbnLabel = new JLabel("ISBN*");
		isbnLabel.setLabelFor(isbnLibro_1);
		isbnLabel.setBounds(52, 60, 46, 14);
		add(isbnLabel);

		JLabel tituloLabel = new JLabel("Título*");
		tituloLabel.setLabelFor(tituloLibro);
		tituloLabel.setBounds(52, 112, 46, 14);
		add(tituloLabel);

		JLabel autorLabel = new JLabel("Autor*");
		autorLabel.setLabelFor(autorLibro);
		autorLabel.setBounds(52, 330, 46, 14);
		add(autorLabel);

		JLabel generoLabel = new JLabel("Género*");
		generoLabel.setLabelFor(generoLibro);
		generoLabel.setBounds(52, 216, 46, 14);
		add(generoLabel);

		JLabel editorialLabel = new JLabel("Editorial*");
		editorialLabel.setLabelFor(editorialLibro);
		editorialLabel.setBounds(52, 273, 88, 14);
		add(editorialLabel);

		JLabel idiomaLabel = new JLabel("Idioma*");
		idiomaLabel.setLabelFor(idiomaLibro);
		idiomaLabel.setBounds(52, 164, 46, 14);
		add(idiomaLabel);

		JLabel precioLabel = new JLabel("Precio*");
		precioLabel.setLabelFor(precioLibro);
		precioLabel.setBounds(52, 392, 46, 14);
		add(precioLabel);

		JLabel reseñaLabel = new JLabel("Rese\u00F1a");
		reseñaLabel.setLabelFor(reseñaLibro);
		reseñaLabel.setBounds(375, 46, 46, 14);
		add(reseñaLabel);

		JLabel vistasoLabel = new JLabel("Vistazo");
		vistasoLabel.setLabelFor(vistasoLibro);
		vistasoLabel.setBounds(375, 282, 46, 14);
		add(vistasoLabel);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean error = null;
				if (ValidarLibro()) {
					libro.setIsbn((isbnLibro_1.getText().replaceAll("-", "")));
					libro.setTitulo((String) tituloLibro.getText());
					libro.setAutor(selectAutor());
					libro.setGenero((String) generoLibro.getText());
					libro.setEditorial((String) editorialLibro.getText());
					libro.setIdioma((String) idiomaLibro.getText());
					try {
						libro.setReseña(reseñaLibro.getDocument().getText(0,
								reseñaLibro.getDocument().getLength()));
						libro.setVistaso(vistasoLibro.getDocument().getText(0,
								vistasoLibro.getDocument().getLength()));
					} catch (BadLocationException e1) {

						e1.printStackTrace();
					}
					try {
						libro.setVistaso(vistasoLibro.getDocument().getText(0,
								vistasoLibro.getDocument().getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					libro.setPrecio((new Double(precioLibro.getValue().toString())
							.doubleValue()));
					try {
					if (isbnLibro_1.isEditable()) {
						
							error = inside.contexto.agregar(libro);
						
					} else {
						error = inside.contexto.modificar(libro);
					}
					} catch (Exception e1) {

						printError(e1.getMessage().concat(" /n"),labelErrores, true);
					}
					if (error) {
						printError("El ISBN pertenece a un libro existente /n",labelErrores, false);
						inside.centro(anterior);
					} else {
						printError("El ISBN pertenece a un libro existente /n",labelErrores, true);
					}
				}
			}
		});

		confirmar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		confirmar.setBounds(22, 464, 144, 31);
		add(confirmar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
			}
		});
		cancelar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(176, 464, 144, 31);
		add(cancelar);

		JScrollPane scrollReseña = new JScrollPane(reseñaLibro);
		scrollReseña.setBounds(375, 72, 503, 191);
		add(scrollReseña);

		JScrollPane scrollVistaso = new JScrollPane(vistasoLibro);
		scrollVistaso.setBounds(375, 307, 503, 266);
		add(scrollVistaso);

		JLabel lblcamposObligatorios = new JLabel("*Campos obligatorios.");
		lblcamposObligatorios.setBounds(22, 498, 298, 14);
		add(lblcamposObligatorios);
		
		 errorISBN = new JTextPane();
		 errorISBN.setBorder(null);
		 errorISBN.setEditable(false);
		 errorISBN.setBackground(new Color(255, 204, 255));
		 errorISBN.setForeground(Color.RED);
		errorISBN.setBounds(52, 83, 303, 20);
		add(errorISBN);
		
		 errorTitulo = new JTextPane();
		 errorTitulo.setBorder(null);
		 errorTitulo.setEditable(false);
		 errorTitulo.setBackground(new Color(255, 204, 255));
		 errorTitulo.setForeground(Color.RED);
		errorTitulo.setBounds(52, 135, 303, 20);
		add(errorTitulo);
		
		 errorIdioma = new JTextPane();
		 errorIdioma.setBorder(null);
		 errorIdioma.setEditable(false);
		 errorIdioma.setBackground(new Color(255, 204, 255));
		 errorIdioma.setForeground(Color.RED);
		errorIdioma.setBounds(52, 187, 303, 20);
		add(errorIdioma);
		
		 errorGenero = new JTextPane();
		 errorGenero.setBorder(null);
		 errorGenero.setEditable(false);
		 errorGenero.setBackground(new Color(255, 204, 255));
		 errorGenero.setForeground(Color.RED);
		errorGenero.setBounds(52, 239, 303, 20);
		add(errorGenero);
		
		 errorEditorial = new JTextPane();
		 errorEditorial.setBorder(null);
		 errorEditorial.setEditable(false);
		 errorEditorial.setBackground(new Color(255, 204, 255));
		 errorEditorial.setForeground(Color.RED);
		errorEditorial.setBounds(52, 296, 303, 20);
		add(errorEditorial);
		
		 errorAutor = new JTextPane();
		 errorAutor.setBorder(null);
		 errorAutor.setEditable(false);
		 errorAutor.setBackground(new Color(255, 204, 255));
		 errorAutor.setForeground(Color.RED);
		errorAutor.setBounds(52, 358, 303, 20);
		add(errorAutor);
		
		 errorPrecio = new JTextPane();
		 errorPrecio.setBorder(null);
		 errorPrecio.setEditable(false);
		 errorPrecio.setBackground(new Color(255, 204, 255));
		 errorPrecio.setForeground(Color.RED);
		errorPrecio.setBounds(52, 420, 303, 20);
		add(errorPrecio);

		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				isbnLibro_1, tituloLibro, idiomaLibro, generoLibro,
				editorialLibro, autorLibro, precioLibro, confirmar, cancelar,
				scrollReseña, reseñaLibro, scrollVistaso, vistasoLibro }));

	}

	private boolean ValidarLibro() {

		return (((ValidadISBN() & ValidadTitulo() & ValidadGenero()
				& ValidadEditorial() & ValidadIdioma() & ValidadPrecio() & ValidadAutor())));

	}

	private Autor selectAutor() {		
		autorLibro.getSelectedItem();
		try {
			inside.contexto.listarAutores();
		for (Autor iterable_element : inside.contexto.listarAutores()) {
			if(iterable_element.toString().compareTo((String) autorLibro.getSelectedItem()) == 0){
				return iterable_element;				
			}
		}
	} catch (Exception e) {
		printError(e.getMessage().concat(" /n"),labelErrores, true);
	}
		return null;
	}	
	
	private Object[] NombresAutores(LinkedList<Autor> autores) {
		ArrayList<String> nombres = new ArrayList<String>();
		for (Autor element : autores) {
			nombres.add(element.toString());
		}
		return nombres.toArray();
	}

	private Boolean ValidadISBN() {
		if ((isbnLibro_1.getText().toString().length() <= 13)
				&& (isbnLibro_1.getText().toString().length() > 9)) {
			printError("El ISBN debe tener entre 10 y 13 digitos /n",errorISBN, false);
			return true;

		} else {
			printError("El ISBN debe tener entre 10 y 13 digitos /n",errorISBN, true);
			return false;
		}
	}

	private Boolean ValidadTitulo() {
		if ((tituloLibro.getText().length() < 46)
				&& (tituloLibro.getText().length() > 0)) {
			printError("El Título debe tener entre 0 y 45 caracteres /n",errorTitulo, false);
			return true;

		} else {
			printError("El Título debe tener entre 0 y 45 caracteres /n",errorTitulo, true);
			return false;
		}

	}

	private Boolean ValidadGenero() {
		if ((generoLibro.getText().length() < 46)
				&& (generoLibro.getText().length() > 0)) {
			printError("El Género debe tener entre 0 y 45 caracteres /n",errorGenero, false);
			return true;

		} else {
			printError("El Género debe tener entre 0 y 45 caracteres /n",errorGenero, true);
			return false;
		}
	}

	private Boolean ValidadEditorial() {
		if ((editorialLibro.getText().length() < 46)
				&& (editorialLibro.getText().length() > 0)) {
			printError("La Editorial debe tener entre 0 y 45 caracteres /n",errorEditorial,
					false);
			return true;

		} else {
			printError("La Editorial debe tener entre 0 y 45 caracteres /n",errorEditorial,
					true);
			return false;
		}

	}

	private Boolean ValidadIdioma() {
		if ((idiomaLibro.getText().length() < 46)
				&& (idiomaLibro.getText().length() > 0)) {
			printError("El idioma debe tener entre 0 y 45 caracteres /n",errorIdioma, false);

			return true;

		} else {
			printError("El idioma debe tener entre 0 y 45 caracteres /n",errorIdioma, true);
			return false;
		}

	}

	private Boolean ValidadAutor() {
		if (autorLibro.getSelectedIndex() != -1) {
			printError("Debe selecionar un Autor /n",errorAutor, false);
			return true;

		} else {
			printError("Debe selecionar un Autor /n",errorAutor, true);
			return false;
		}
	}

	private Boolean ValidadPrecio() {
		try{
		if ((new Double(precioLibro.getValue().toString()).compareTo(new Double("0")) > 0)) {
			printError("El Precio debe ser un Número mayor a 0 /n",errorPrecio, false);
			return true;

		} else {
			printError("El Precio debe ser un Número mayor a 0 /n",errorPrecio, true);
			return false;
		}
		}
		catch(Exception e){
			printError("El Precio debe ser un Número mayor a 0 /n",errorPrecio, true);
			return true;
		}
	}



	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}

}

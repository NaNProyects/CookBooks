package fachade;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;
import funcionalidad.Libro;

import java.awt.Font;
import java.awt.Color;
import java.awt.TextField;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JScrollPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.BadLocationException;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class MedioEdicionDeLibro extends JPanel {
	private String tituloPanel = "Nuevo Libro";
	private Libro libro;
	private JTextField tituloLibro;
	private JComboBox<String> autorLibro;
	private JTextField generoLibro;
	private JTextField editorialLibro;
	private JTextField idiomaLibro;
	private JFormattedTextField precioLibro;
	private Interface inside;
	private TextField isbnLibro_1;
	private JTextPane reseñaLibro;
	private JTextPane vistasoLibro;
	private JLabel lblTitulo;
	private JTextPane labelErrores;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioEdicionDeLibro(Interface inside2) {
		this(inside2, new Libro("0", "Titulo", null, "genero", "editorial",
				"idioma", "reseña", "vistaso", new Double(-1)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MedioEdicionDeLibro(Interface inside2, Libro libro2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		libro = libro2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 333, 333, 240);
		labelErrores.setVisible(false);
		add(labelErrores);

		isbnLibro_1 = new TextField();
		isbnLibro_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});
		;
		isbnLibro_1.setText(libro.getIsbn().toString());
		isbnLibro_1.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {// REVISAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				labelErrores.setText(labelErrores.getText().replaceAll(
						"El ISBN debe tener entre 10 y 13 digitos /n"
								.replaceAll("/n",
										System.getProperty("line.separator")),
						""));
				if ((isbnLibro_1.getText().toString().length() > 13)
						|| (isbnLibro_1.getText().toString().length() <= 9)) {
					labelErrores.setText(labelErrores
							.getText()
							.concat("El ISBN debe tener entre 10 y 13 digitos /n")
							.replaceAll("/n",
									System.getProperty("line.separator")));
					labelErrores.setVisible(true);
				}
			}
		});

		isbnLibro_1.setText(libro.getIsbn().toString());
		isbnLibro_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		isbnLibro_1.setBounds(108, 57, 184, 20);
		if (libro.getIsbn() != "0") {
			isbnLibro_1.setEditable(false);
		}
		add(isbnLibro_1);

		tituloLibro = new JTextField();
		tituloLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				labelErrores.setText(labelErrores.getText().replaceAll(
						"El Titulo debe tener entre 0 y 45 caracteres /n"
								.replaceAll("/n",
										System.getProperty("line.separator")),
						""));
				if ((tituloLibro.getText().length() >= 45)
						|| (tituloLibro.getText().length() <= 0)) {

					labelErrores.setText(labelErrores
							.getText()
							.concat("El Titulo debe tener entre 0 y 45 caracteres /n")
							.replaceAll("/n",
									System.getProperty("line.separator")));
					labelErrores.setVisible(true);
				}
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

		tituloLibro.setBounds(108, 88, 184, 20);
		add(tituloLibro);

		autorLibro = new JComboBox<String>();
		autorLibro.setModel(new DefaultComboBoxModel(
				NombresAutores(inside.contexto.autores())));
		autorLibro.setBounds(108, 119, 184, 20);
		autorLibro.setSelectedItem(libro.getAutor());
		add(autorLibro);

		generoLibro = new JTextField();
		generoLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				labelErrores.setText(labelErrores.getText().replaceAll(
						"El Genero debe tener entre 0 y 45 caracteres /n"
								.replaceAll("/n",
										System.getProperty("line.separator")),
						""));
				if ((generoLibro.getText().length() >= 45)
						|| (generoLibro.getText().length() <= 0)) {

					labelErrores.setText(labelErrores
							.getText()
							.concat("El Genero debe tener entre 0 y 45 caracteres /n")
							.replaceAll("/n",
									System.getProperty("line.separator")));
					labelErrores.setVisible(true);
				}
			}
		});
		generoLibro.setText(libro.getGenero());

		generoLibro.setBounds(108, 150, 184, 20);
		add(generoLibro);

		editorialLibro = new JTextField();
		editorialLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				labelErrores.setText(labelErrores.getText().replaceAll(
						"La Editorial debe tener entre 0 y 45 caracteres /n"
								.replaceAll("/n",
										System.getProperty("line.separator")),
						""));
				if ((editorialLibro.getText().length() >= 45)
						|| (editorialLibro.getText().length() <= 0)) {

					labelErrores.setText(labelErrores
							.getText()
							.concat("La Editorial debe tener entre 0 y 45 caracteres /n")
							.replaceAll("/n",
									System.getProperty("line.separator")));
					labelErrores.setVisible(true);
				}
			}
		});
		editorialLibro.setText(libro.getEditorial());
		editorialLibro.setBounds(108, 181, 184, 20);
		add(editorialLibro);

		idiomaLibro = new JTextField();
		idiomaLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				labelErrores.setText(labelErrores.getText().replaceAll(
						"El idioma debe tener entre 0 y 45 caracteres /n"
								.replaceAll("/n",
										System.getProperty("line.separator")),
						""));
				if ((idiomaLibro.getText().length() >= 45)
						|| (idiomaLibro.getText().length() <= 0)) {

					labelErrores.setText(labelErrores
							.getText()
							.concat("El idioma debe tener entre 0 y 45 caracteres /n")
							.replaceAll("/n",
									System.getProperty("line.separator")));
					labelErrores.setVisible(true);
				}
			}
		});
		idiomaLibro.setText(libro.getIdioma());
		idiomaLibro.setBounds(108, 212, 184, 20);
		add(idiomaLibro);

		precioLibro = new JFormattedTextField(new Double(libro.getPrecio()));
		precioLibro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					precioLibro.commitEdit();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				labelErrores.setText(labelErrores.getText().replaceAll(
						"El Precio debe ser mayor a 0 /n".replaceAll("/n",
								System.getProperty("line.separator")), ""));
				if (((Double) precioLibro.getValue())
						.compareTo(new Double("0")) < 0) {

					labelErrores.setText(labelErrores
							.getText()
							.concat("El Precio debe ser mayor a 0 /n")
							.replaceAll("/n",
									System.getProperty("line.separator")));
					labelErrores.setVisible(true);
				}

			}
		});
		precioLibro.setBounds(108, 243, 184, 20);
		add(precioLibro);

		reseñaLibro = new JTextPane();
		reseñaLibro.setText(libro.getReseña());
		reseñaLibro.setBounds(10, 339, 503, 191);
		add(reseñaLibro);

		vistasoLibro = new JTextPane();
		vistasoLibro.setText("vistazo");
		vistasoLibro.setBounds(375, 307, 503, 266);
		add(vistasoLibro);

		JLabel isbnLabel = new JLabel("ISBN*");
		isbnLabel.setLabelFor(isbnLibro_1);
		isbnLabel.setBounds(52, 60, 46, 14);
		add(isbnLabel);

		JLabel tituloLabel = new JLabel("Titulo*");
		tituloLabel.setLabelFor(tituloLibro);
		tituloLabel.setBounds(52, 91, 46, 14);
		add(tituloLabel);

		JLabel autorLabel = new JLabel("Autor*");
		autorLabel.setLabelFor(autorLibro);
		autorLabel.setBounds(52, 122, 46, 14);
		add(autorLabel);

		JLabel generoLabel = new JLabel("Genero*");
		generoLabel.setLabelFor(generoLibro);
		generoLabel.setBounds(52, 153, 46, 14);
		add(generoLabel);

		JLabel editorialLabel = new JLabel("Editorial*");
		editorialLabel.setLabelFor(editorialLibro);
		editorialLabel.setBounds(52, 184, 88, 14);
		add(editorialLabel);

		JLabel idiomaLabel = new JLabel("Idioma*");
		idiomaLabel.setLabelFor(idiomaLibro);
		idiomaLabel.setBounds(52, 215, 46, 14);
		add(idiomaLabel);

		JLabel precioLabel = new JLabel("Precio*");
		precioLabel.setLabelFor(precioLibro);
		precioLabel.setBounds(52, 246, 46, 14);
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
		confirmar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Boolean error;
				if (ValidarCampos()) {
					libro.setIsbn((isbnLibro_1.getText().replaceAll("-", "")));
					libro.setTitulo((String) tituloLibro.getText());
					libro.setAutor((String) autorLibro.getSelectedItem());
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
					libro.setPrecio(((Double) precioLibro.getValue())
							.doubleValue());
					if (isbnLibro_1.isEditable()) {
						error = inside.contexto.agregar(libro);
					} else {
						error = inside.contexto.modificar(libro);
					}
					if (error) {
						inside.centro(new MedioListaDeLibros(inside));
					} else {
						labelErrores.setText(labelErrores
								.getText()
								.concat("El ISBN pertenece a un libro existente")
								.replaceAll("/n",
										System.getProperty("line.separator")));
						labelErrores.setVisible(true);
					}
				}
			}
		});

		confirmar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		confirmar.setBounds(22, 274, 144, 31);
		add(confirmar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioListaDeLibros(inside));
			}
		});
		cancelar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(176, 274, 144, 31);
		add(cancelar);

		JScrollPane scrollReseña = new JScrollPane(reseñaLibro);
		scrollReseña.setBounds(375, 72, 503, 191);
		add(scrollReseña);

		JScrollPane scrollVistaso = new JScrollPane(vistasoLibro);
		scrollVistaso.setBounds(375, 307, 503, 266);
		add(scrollVistaso);

		JLabel lblcamposObligatorios = new JLabel("*Campos obligatorios.");
		lblcamposObligatorios.setBounds(22, 308, 298, 14);
		add(lblcamposObligatorios);

		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				isbnLibro_1, tituloLibro, autorLibro, generoLibro,
				editorialLibro, idiomaLibro, precioLibro, confirmar, cancelar,
				scrollReseña, reseñaLibro, scrollVistaso, vistasoLibro }));

	}

	private boolean ValidarCampos() {
		return (((isbnLibro_1.getText().toString().length() <= 13) && (isbnLibro_1
				.getText().toString().length() > 9))
				&& ((tituloLibro.getText().length() < 45) && (tituloLibro
						.getText().length() > 0))
				&& ((generoLibro.getText().length() < 45) && (generoLibro
						.getText().length() > 0))
				&& ((editorialLibro.getText().length() < 45) && (editorialLibro
						.getText().length() > 0))
				&& ((idiomaLibro.getText().length() < 45) && (idiomaLibro
						.getText().length() > 0))
				&& (((Double) precioLibro.getValue())
						.compareTo(new Double("0")) >= 0) && (autorLibro
					.getSelectedIndex() != -1));
	}

	private Object[] NombresAutores(LinkedList<Autor> autores) {
		ArrayList<String> nombres = new ArrayList<String>();
		for (Autor element : autores) {
			nombres.add(element.nombre());
		}
		return nombres.toArray();
	}
}

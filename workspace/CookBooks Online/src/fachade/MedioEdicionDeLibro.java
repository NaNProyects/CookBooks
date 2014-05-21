package fachade;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;
import funcionalidad.Libro;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JScrollPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.DefaultComboBoxModel;

public class MedioEdicionDeLibro extends JPanel {
	private String tituloPanel = "Nuevo Libro";
	private Libro libro;
	private JFormattedTextField tituloLibro;
	private JComboBox autorLibro;
	private JFormattedTextField generoLibro;
	private JFormattedTextField editorialLibro;
	private JFormattedTextField idiomaLibro;
	private JFormattedTextField precioLibro;
	private Interface inside;
	private JFormattedTextField isbnLibro_1;
	private JTextPane reseñaLibro;
	private JTextPane vistasoLibro;

	/**
	 * Create the panel.
	 */
	public MedioEdicionDeLibro(Interface inside2) {
		this(inside2, new Libro(new Long(-1), "Titulo", null, "genero",
				"editorial", "idioma", "reseña", "vistaso", new Double(-1)));
	}

	public MedioEdicionDeLibro(Interface inside2, Libro libro2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		libro = libro2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		JLabel lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				tituloPanel);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		isbnLibro_1 = new JFormattedTextField(new Long(libro.getIsbn()));
		isbnLibro_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		isbnLibro_1.setBounds(108, 57, 184, 20);
		if (((Long) isbnLibro_1.getValue()).intValue() != -1) {
			isbnLibro_1.setEditable(false);
		}
		add(isbnLibro_1);

		try {
			tituloLibro = new JFormattedTextField(new MaskFormatter(
					"*********************************************"));
			tituloLibro.setText(libro.getTitulo());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tituloLibro.setBounds(108, 88, 184, 20);
		add(tituloLibro);

		autorLibro = new JComboBox();
		autorLibro.setModel(new DefaultComboBoxModel(
				NombresAutores(inside.contexto.autores())));
		autorLibro.setBounds(108, 119, 184, 20);
		autorLibro.setSelectedItem(libro.getAutor());
		add(autorLibro);

		try {
			generoLibro = new JFormattedTextField(new MaskFormatter(
					"*********************************************"));
			generoLibro.setText(libro.getGenero());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		generoLibro.setBounds(108, 150, 184, 20);
		add(generoLibro);

		try {
			editorialLibro = new JFormattedTextField(new MaskFormatter(
					"*********************************************"));
			editorialLibro.setText(libro.getEditorial());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		editorialLibro.setBounds(108, 181, 184, 20);
		add(editorialLibro);

		try {
			idiomaLibro = new JFormattedTextField(new MaskFormatter(
					"*********************************************"));
			idiomaLibro.setText(libro.getIdioma());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		idiomaLibro.setBounds(108, 212, 184, 20);
		add(idiomaLibro);

		precioLibro = new JFormattedTextField(new Double(libro.getPrecio()));
		precioLibro.setBounds(108, 243, 184, 20);
		add(precioLibro);

		reseñaLibro = new JTextPane();
		reseñaLibro.setText(libro.getReseña());
		reseñaLibro.setBounds(375, 72, 503, 191);
		add(reseñaLibro);

		vistasoLibro = new JTextPane();
		vistasoLibro.setText(libro.getVistaso());
		vistasoLibro.setBounds(375, 307, 503, 266);
		add(vistasoLibro);

		JLabel isbnLabel = new JLabel("ISBN");
		isbnLabel.setLabelFor(isbnLibro_1);
		isbnLabel.setBounds(52, 60, 46, 14);
		add(isbnLabel);

		JLabel tituloLabel = new JLabel("Titulo");
		tituloLabel.setLabelFor(tituloLibro);
		tituloLabel.setBounds(52, 91, 46, 14);
		add(tituloLabel);

		JLabel autorLabel = new JLabel("Autor");
		autorLabel.setLabelFor(autorLibro);
		autorLabel.setBounds(52, 122, 46, 14);
		add(autorLabel);

		JLabel generoLabel = new JLabel("Genero");
		generoLabel.setLabelFor(generoLibro);
		generoLabel.setBounds(52, 153, 46, 14);
		add(generoLabel);

		JLabel editorialLabel = new JLabel("Editorial");
		editorialLabel.setLabelFor(editorialLibro);
		editorialLabel.setBounds(52, 184, 46, 14);
		add(editorialLabel);

		JLabel idiomaLabel = new JLabel("Idioma");
		idiomaLabel.setLabelFor(idiomaLibro);
		idiomaLabel.setBounds(52, 215, 46, 14);
		add(idiomaLabel);

		JLabel precioLabel = new JLabel("Precio");
		precioLabel.setLabelFor(precioLibro);
		precioLabel.setBounds(52, 246, 46, 14);
		add(precioLabel);

		JLabel reseñaLabel = new JLabel("Rese\u00F1a");
		reseñaLabel.setLabelFor(reseñaLibro);
		reseñaLabel.setBounds(375, 46, 46, 14);
		add(reseñaLabel);

		JLabel vistasoLabel = new JLabel("Vistaso");
		vistasoLabel.setLabelFor(vistasoLibro);
		vistasoLabel.setBounds(375, 282, 46, 14);
		add(vistasoLabel);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (ValidarCampos()) {
					libro.setIsbn(((Long) isbnLibro_1.getValue()).intValue());
					libro.setTitulo((String) tituloLibro.getValue());
					libro.setAutor((String) autorLibro.getSelectedItem());
					libro.setGenero((String) generoLibro.getValue());
					libro.setEditorial((String) editorialLibro.getValue());
					libro.setIdioma((String) idiomaLibro.getValue());
					libro.setReseña(reseñaLibro.getDocument().toString());
					libro.setVistaso(vistasoLibro.getDocument().toString());
					libro.setPrecio(((Double) precioLibro.getValue())
							.doubleValue());
					if (isbnLibro_1.isEditable()) {
						inside.contexto.agregar(libro);
					} else {
						inside.contexto.modificar(libro);
					}
					inside.centro(new MedioListaDeLibros(inside));
				}
			}
		});

		confirmar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		confirmar.setBounds(22, 326, 144, 31);
		add(confirmar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioListaDeLibros(inside));
			}
		});
		cancelar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(176, 326, 144, 31);
		add(cancelar);

		JScrollPane scrollReseña = new JScrollPane(reseñaLibro);
		scrollReseña.setBounds(375, 72, 503, 191);
		add(scrollReseña);

		JScrollPane scrollVistaso = new JScrollPane(vistasoLibro);
		scrollVistaso.setBounds(375, 307, 503, 266);
		add(scrollVistaso);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				isbnLibro_1, tituloLibro, autorLibro, generoLibro,
				editorialLibro, idiomaLibro, precioLibro, confirmar, cancelar,
				scrollReseña, reseñaLibro, scrollVistaso, vistasoLibro }));

	}

	private boolean ValidarCampos() {

		return true;
	}

	private Object[] NombresAutores(ArrayList autores) {
		ArrayList nombres = new ArrayList();
		for (Iterator iterator = autores.iterator(); iterator.hasNext();) {
			nombres.add(((Autor) iterator.next()).nombre());
		}
		return nombres.toArray();
	}
}

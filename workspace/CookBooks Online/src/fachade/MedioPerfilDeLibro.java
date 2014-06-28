package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Libro;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MedioPerfilDeLibro extends MedioPanel {
	@SuppressWarnings("unused")
	private String tituloPanel;
	private Libro libro;
	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JLabel isbnLabel;
	private JLabel tituloLabel;
	private JLabel autorLabel;
	private JLabel generoLabel;
	private JLabel editorialLabel;
	private JLabel idiomaLabel;
	private JLabel precioLabel;
	private JLabel reseñaLabel;
	private JButton Comprar;
	private JButton Vistazo;
	private JScrollPane scrollPane;
	private JTextPane textReseña;
	private MedioPanel anterior;
	private JButton atras;
	private JButton btnModificar;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */

	public MedioPerfilDeLibro(Interface inside2, Libro libro2, MedioPanel ant) {
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
		labelErrores.setBounds(22, 529, 326, 63);
		add(labelErrores);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Perfil de Libro");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		isbnLabel = new JLabel("ISBN: ".concat(libro.getIsbn()));
		isbnLabel.setBounds(62, 96, 200, 14);
		add(isbnLabel);

		tituloLabel = new JLabel("Título: ".concat(libro.getTitulo()));
		tituloLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		tituloLabel.setBounds(52, 58, 658, 24);
		add(tituloLabel);

		autorLabel = new JLabel("Autor: ".concat(libro.getAutor().toString()));
		autorLabel.setBounds(62, 121, 200, 14);
		add(autorLabel);

		generoLabel = new JLabel("Género: ".concat(libro.getGenero()));
		generoLabel.setBounds(62, 146, 200, 14);
		add(generoLabel);

		editorialLabel = new JLabel("Editorial: ".concat(libro.getEditorial()));
		editorialLabel.setBounds(482, 96, 271, 14);
		add(editorialLabel);

		idiomaLabel = new JLabel("Idioma: ".concat(libro.getIdioma()));
		idiomaLabel.setBounds(482, 121, 271, 14);
		add(idiomaLabel);

		precioLabel = new JLabel(
				"Precio: ".concat(libro.getPrecio().toString()));
		precioLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		precioLabel.setBounds(62, 171, 186, 14);
		add(precioLabel);

		reseñaLabel = new JLabel("Rese\u00F1a: ");
		reseñaLabel.setBounds(62, 247, 130, 14);
		add(reseñaLabel);

		Comprar = new JButton("Agregar al Carrito");
		Comprar.setIcon(new ImageIcon(MedioPerfilDeLibro.class.getResource("/fachade/Image/Add Green Button.png")));
		Comprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.contexto.agregarAlCarrito(libro);
				Comprar.setEnabled((inside.contexto.usuarioActual().getId() >1) && (!inside.contexto.estaEnElCarrito(libro)));
			}
		});
		Comprar.setBounds(358, 542, 171, 31);

		Comprar.setEnabled((inside.contexto.usuarioActual().getId() >1) && (!inside.contexto.estaEnElCarrito(libro)) ); 
		Comprar.setVisible(!(inside.contexto.usuarioActual().getId() == 1));
		add(Comprar);

		Vistazo = new JButton("Dar un Vistazo");
		Vistazo.setIcon(new ImageIcon(MedioPerfilDeLibro.class.getResource("/fachade/Image/Export To Document.png")));
		Vistazo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioPerfilDeLibroVistazo(inside, libro, inside.center));
				
			}
		});
		Vistazo.setBounds(539, 542, 171, 31);
		add(Vistazo);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 278, 648, 240);
		add(scrollPane);

		textReseña = new JTextPane();
		textReseña.setText(libro.getReseña());
		textReseña.setEditable(false);
		textReseña.setSelectionColor(Color.WHITE);
		scrollPane.setViewportView(textReseña);
		
		atras = new JButton("Atrás");
		atras.setIcon(new ImageIcon(MedioPerfilDeLibro.class.getResource("/fachade/Image/Import Document.png")));
		atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
				inside.center.refresh();
			}
		});
		atras.setBounds(720, 542, 171, 31);
		add(atras);
		
		btnModificar = new JButton("Editar");
		btnModificar.setIcon(new ImageIcon(MedioPerfilDeLibro.class.getResource("/fachade/Image/Write Document.png")));
		btnModificar.setBounds(358, 542, 171, 31);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					inside.centro(new MedioEdicionDeLibro(inside, libro, inside.center));
			}
		});
		btnModificar.setVisible(inside.contexto.usuarioActual().getId() == 1);
		btnModificar.setEnabled(inside.contexto.usuarioActual().getId() == 1);
		add(btnModificar);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				Comprar, Vistazo }));

	}

	@SuppressWarnings("unused")
	private void printError(String texto, Boolean condicion) {
		labelErrores.setText(labelErrores.getText().replaceAll(
				texto.replaceAll("/n", System.getProperty("line.separator")),
				""));
		if (condicion) {
			labelErrores.setText(labelErrores.getText().concat(texto)
					.replaceAll("/n", System.getProperty("line.separator")));
			labelErrores.setVisible(true);
		}
	}

	protected void refresh() {
		if (inside.contexto.usuarioActual().getId() < 1) {
			inside.centro(new MedioHome(inside));
		} else {
			Comprar.setEnabled((inside.contexto.usuarioActual().getId() > 1)
					&& (!inside.contexto.estaEnElCarrito(libro)));
			Comprar.setVisible(!(inside.contexto.usuarioActual().getId() == 1));
			btnModificar
					.setVisible(inside.contexto.usuarioActual().getId() == 1);
			btnModificar
					.setEnabled(inside.contexto.usuarioActual().getId() == 1);
			repaint();
		}
	}
}

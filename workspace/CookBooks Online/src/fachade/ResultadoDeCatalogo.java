package fachade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Libro;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ResultadoDeCatalogo extends MedioPanel {
	private Libro libro;
	private Interface inside;
	private JLabel lblTitulo;
	private JLabel lblAutor;
	private JLabel lblGenero;
	private JLabel lblIdioma;
	private JLabel lblEditorial;
	private JButton botonComprar;
	private JButton botonVerPerfil;
	private JLabel lblPrecio;
	private JButton btnModificar;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public ResultadoDeCatalogo(Interface inside2) {
		this(inside2, new Libro("0", "", null, "", "",
				"", "", "", new Double(0))); 
	}

	public ResultadoDeCatalogo(Interface inside2, Libro libro2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		this.libro = libro2;
		
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setPreferredSize(new Dimension(901, 95));
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"T�tulo: ".concat(libro2.getTitulo()));
		lblTitulo.setBounds(22, 0, 529, 31);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblTitulo);
		
		lblAutor = DefaultComponentFactory.getInstance().createTitle("Autor: ".concat(libro2.getAutor().toString()));
		lblAutor.setBounds(32, 42, 283, 17);
		lblAutor.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblAutor);
		
		lblGenero = DefaultComponentFactory.getInstance().createTitle("G�nero: ".concat(libro2.getGenero()));
		lblGenero.setBounds(32, 67, 283, 17);
		lblGenero.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblGenero);
		
		lblIdioma = DefaultComponentFactory.getInstance().createTitle("Idioma: ".concat(libro2.getIdioma()));
		lblIdioma.setBounds(325, 42, 298, 17);
		lblIdioma.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblIdioma);
		
		lblEditorial = DefaultComponentFactory.getInstance().createTitle("Editorial: ".concat(libro2.getEditorial()));
		lblEditorial.setBounds(325, 67, 298, 17);
		lblEditorial.setFont(new Font("Tahoma", Font.BOLD, 13));
		add(lblEditorial);
		
		botonComprar = new JButton("Agregar al Carrito");
		botonComprar.setIcon(new ImageIcon(ResultadoDeCatalogo.class.getResource("/fachade/Image/Add Green Button.png")));
		botonComprar.setBounds(720, 8, 171, 33);
		botonComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.contexto.agregarAlCarrito(libro);

				botonComprar.setEnabled((inside.contexto.usuarioActual().getId() >1) && (!inside.contexto.estaEnElCarrito(libro)));
			}
		});
		botonComprar.setEnabled((inside.contexto.usuarioActual().getId() >1) && (!inside.contexto.estaEnElCarrito(libro)) ); 
		botonComprar.setVisible(!(inside.contexto.usuarioActual().getId() == 1));
		add(botonComprar);
		
		btnModificar = new JButton("Editar");
		btnModificar.setSize(171, 33);
		btnModificar.setLocation(720, 8);
		btnModificar.setIcon(new ImageIcon(MedioPerfilDeLibro.class.getResource("/fachade/Image/Write Document.png")));
		botonComprar.setBounds(720, 8, 171, 33);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					inside.centro(new MedioEdicionDeLibro(inside, libro, inside.center));
			}
		});
		btnModificar.setVisible(inside.contexto.usuarioActual().getId() == 1);
		btnModificar.setEnabled(inside.contexto.usuarioActual().getId() == 1);
		add(btnModificar);
		
		botonVerPerfil = new JButton("Ver Perfil");
		botonVerPerfil.setIcon(new ImageIcon(ResultadoDeCatalogo.class.getResource("/fachade/Image/Export To Document.png")));
		botonVerPerfil.setBounds(720, 51, 171, 33);
		botonVerPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioPerfilDeLibro(inside, libro, inside.center));
			}
		});
		add(botonVerPerfil);
		
		lblPrecio = DefaultComponentFactory.getInstance().createTitle("Precio : $".concat(libro2.getPrecio().toString()));
		lblPrecio.setBounds(567, 9, 157, 22);
		lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblPrecio);
		


	}

	protected void refresh() {
		botonComprar.setEnabled((inside.contexto.usuarioActual().getId() >1) && (!inside.contexto.estaEnElCarrito(libro)) ); 
		botonComprar.setVisible(!(inside.contexto.usuarioActual().getId() == 1));
		btnModificar.setVisible(inside.contexto.usuarioActual().getId() == 1);
		btnModificar.setEnabled(inside.contexto.usuarioActual().getId() == 1);
		repaint();
	}

}

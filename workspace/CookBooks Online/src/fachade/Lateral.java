package fachade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Lateral extends JPanel {
	
	private Interface inside;
	private JButton botonLibros;
	private JButton botonAutores;
	private JButton botonPedidos;
	private JButton carrito;
	private JButton inicio;
	private JButton historial;
	private JButton topTres;
	private JButton botonUsuarios;
	private JButton contacto;
	private JButton ayuda;
	/**
	 * Create the panel.
	 */
	public Lateral(Interface inside2) {
		inside = inside2;
		
		setBackground(new Color(255, 204, 255));

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));	
		// NO USUARIOS
		inicio = new JButton("Inicio");
		inicio.setPreferredSize(new Dimension(96, 23));
		inicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioHome(inside));
			}
		});
		add(inicio);
		
		topTres = new JButton("Top 3");
		topTres.setPreferredSize(new Dimension(96, 23));
		topTres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioTopDeLibros(inside,3));
			}
		});
		add(topTres);		
		
		contacto = new JButton("Contacto");
		contacto.setPreferredSize(new Dimension(96, 23));
		contacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioContacto(inside));
			}
		});
		add(contacto);	
		
		ayuda = new JButton("Ayuda");
		ayuda.setPreferredSize(new Dimension(96, 23));
		ayuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioIndiceAyuda(inside));
			}
		});
		add(ayuda);			
		
		// ADMINISTRADORES
		 botonLibros = new JButton("Libros");
		botonLibros.setPreferredSize(new Dimension(96, 23));
		botonLibros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioListaDeLibros(inside));
			}
		});

		add(botonLibros);
		
		 botonAutores = new JButton("Autores");
		botonAutores.setPreferredSize(new Dimension(96, 23));
		botonAutores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioListaDeAutores(inside));
				
			}
		});
		add(botonAutores);
		
		 botonPedidos = new JButton("Pedidos");
		botonPedidos.setPreferredSize(new Dimension(96, 23));
		botonPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioPedidos(inside));
			}
		});
		
		add(botonPedidos);
		
		 botonUsuarios = new JButton("Usuarios");
		 botonUsuarios.setPreferredSize(new Dimension(96, 23));
		 botonUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioUsuariosDeUnPeriodo(inside));
			}
		});
		
		add(botonUsuarios);
		
		// USUARIOS
		
		 carrito = new JButton("Carrito");
		 carrito.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		inside.centro(new MedioCarrito(inside));
		 	}
		 });
		carrito.setPreferredSize(new Dimension(96, 23));
		add(carrito);
		
		historial = new JButton("Historial");
		historial.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		inside.centro(new MedioHistorialDeUsuario(inside));
		 	}
		 });
		historial.setPreferredSize(new Dimension(96, 23));
		add(historial);
		

		// NO USUARIOS
;
		

	}
	public void permisos(Integer permisos){
		// ADMINISTRADORES
		botonAutores.setVisible(permisos == 1);
		botonLibros.setVisible(permisos == 1);
		botonPedidos.setVisible(permisos == 1);
		botonUsuarios.setVisible(permisos == 1);
		
		// USUARIOS
		carrito.setVisible(permisos > 1);
		historial.setVisible(permisos > 1);
		
		
		// NO USUARIOS
		inicio.setVisible(true);
		topTres.setVisible(true);	
		contacto.setVisible(true);
		ayuda.setVisible(true);
		repaint();
	}

}
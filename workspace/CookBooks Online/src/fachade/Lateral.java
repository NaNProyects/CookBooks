package fachade;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Lateral extends JPanel {
	
	private Interface inside;
	private JButton botonLibros;
	private JButton botonAutores;
	private JButton botonPedidos;
	private JButton carrito;
	private JButton inicio;
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
		inicio.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioHome(inside));
			}
		});
		add(inicio);
		
		
		// ADMINISTRADORES
		 botonLibros = new JButton("Libros");
		botonLibros.setPreferredSize(new Dimension(96, 23));
		botonLibros.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioListaDeLibros(inside));
			}
		});

		add(botonLibros);
		
		 botonAutores = new JButton("Autores");
		botonAutores.setPreferredSize(new Dimension(96, 23));
		botonAutores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioListaDeAutores(inside));
				
			}
		});
		add(botonAutores);
		
		 botonPedidos = new JButton("Pedidos");
		botonPedidos.setPreferredSize(new Dimension(96, 23));
		botonPedidos		.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioPedidos(inside));
			}
		});
		
		add(botonPedidos);
		

		
		// USUARIOS
		
		 carrito = new JButton("Carrito");
		 carrito.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		inside.centro(new MedioCarrito(inside));
		 	}
		 });
		carrito.setPreferredSize(new Dimension(96, 23));
		add(carrito);
		

		// NO USUARIOS
;
		

	}
	public void permisos(Integer permisos){
		// ADMINISTRADORES
		botonAutores.setVisible(permisos == 1);
		botonLibros.setVisible(permisos == 1);
		botonPedidos.setVisible(permisos == 1);
		
		// USUARIOS
		carrito.setVisible(permisos > 1);
		
		// NO USUARIOS
		inicio.setVisible(true);
		
		repaint();
	}

}
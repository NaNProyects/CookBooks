package fachade;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class Lateral extends JPanel {
	
	private Interface inside;
	private JButton botonLibros;
	private JButton botonAutores;
	private JButton botonPedidos;
	private JButton button_2;
	private JButton inicio;
	/**
	 * Create the panel.
	 */
	public Lateral(Interface inside2) {
		inside = inside2;
		
		setBackground(new Color(255, 204, 255));

		
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
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
		
		 button_2 = new JButton("New button");
		button_2.setPreferredSize(new Dimension(96, 23));
		add(button_2);
		

		// NO USUARIOS
;
		

	}
	public void permisos(Integer permisos){
		// ADMINISTRADORES
		botonAutores.setVisible(permisos == 1);
		botonLibros.setVisible(permisos == 1);
		botonPedidos.setVisible(permisos == 1);
		
		// USUARIOS
		button_2.setVisible(permisos > 1);
		
		// NO USUARIOS
		inicio.setVisible(true);
		
		repaint();
	}

}
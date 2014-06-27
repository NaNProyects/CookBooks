package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Libro;

@SuppressWarnings("serial")
public class MedioHome extends MedioPanel {

	private Interface inside;
	private JPanel panelResultado;
	private LinkedList<Libro> libros;
	private JLabel lblTitulo;
	private JScrollPane scrollPane;
	private JTextPane labelErrores;

	public MedioHome(Interface inside2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		libros = inside.contexto.listarLibros();
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		panelResultado = new JPanel();
		panelResultado.setPreferredSize(new Dimension(901, libros.size()*105));
		panelResultado.setBounds(0, 71, 901, 533);
		
		 panelResultado.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		 panelResultado.setBackground(new Color(255, 204, 255));
		for (Libro libro : libros) {
			panelResultado.add(new ResultadoDeCatalogo(inside, libro));
		}
		add(panelResultado);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Catálogo");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);


		
		 scrollPane = new JScrollPane(panelResultado);


		 scrollPane.setBorder(null);
		 scrollPane.setBounds(0, 71, 921, 533);

		 add(scrollPane);

		 
		labelErrores = new JTextPane();
		labelErrores.setBounds(22, 50, 867, 14);
		add(labelErrores);
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.BLACK);				
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

	@Override
	void refresh() {
		for (Component componente : panelResultado.getComponents()) {
			((MedioPanel) componente).refresh();
		}
		
	}
}

package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Libro;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MedioTopDeLibros extends MedioPanel {

	private Interface inside;
	private JPanel panelResultado;
	private LinkedList<Libro> libros = new LinkedList<Libro>();
	private JLabel lblTitulo;
	private JScrollPane scrollPane;
	private JTextPane labelErrores;
	private JLabel lblHasta;
	private JSpinner hasta;
	private JLabel lblDesde;
	private JSpinner desde;
	private JButton btnListar;
	private Integer cantidad;

	public MedioTopDeLibros(Interface inside2, Integer cant,LinkedList<Libro> lib, JSpinner hast,JSpinner desd){
		this(inside2,cant);
		libros = lib;
		hasta.setValue(hast.getValue());

		desde.setValue(desd.getValue());

	}	

	public MedioTopDeLibros(Interface inside2, Integer cant) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		cantidad = cant;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		panelResultado = new JPanel();
		panelResultado.setPreferredSize(new Dimension(901, cantidad*105));
		panelResultado.setBounds(0, 71, 901, 533);
		
		 panelResultado.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		 panelResultado.setBackground(new Color(255, 204, 255));
		add(panelResultado);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Libros mas vendidos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 226, 50);
		add(lblTitulo);


		
		 scrollPane = new JScrollPane(panelResultado);


		 scrollPane.setBorder(null);
		 scrollPane.setBounds(0, 99, 921, 505);

		 add(scrollPane);

		 
		labelErrores = new JTextPane();
		labelErrores.setBounds(22, 50, 867, 14);
		add(labelErrores);
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.BLACK);
		
		 lblDesde = new JLabel("Entre:");
		lblDesde.setBounds(54, 74, 62, 14);
		add(lblDesde);
		
		 desde = new JSpinner();
		desde.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.WEEK_OF_YEAR));
		desde.setEditor(new JSpinner.DateEditor(desde, "dd/MM/yyyy"));
		desde.setBounds(92, 71, 107, 20);
		add(desde);
		
		lblHasta = new JLabel("Y:");
		lblHasta.setBounds(229, 74, 62, 14);
		add(lblHasta);
		
		hasta = new JSpinner();
		hasta.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.WEEK_OF_YEAR));
		hasta.setEditor(new JSpinner.DateEditor(hasta, "dd/MM/yyyy"));
		hasta.setBounds(265, 71, 107, 20);
		add(hasta);
		
		btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		btnListar.setBounds(405, 70, 89, 23);
		add(btnListar);
		
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

	private void listar(){
		inside.centro(new MedioTopDeLibros(inside, cantidad, inside.contexto.librosMasVendidosEntre((Date)desde.getValue(),(Date)hasta.getValue(),cantidad),hasta,desde));
//		libros = inside.contexto.librosMasVendidosEntre((Date)desde.getValue(),(Date)hasta.getValue(),cantidad); 
//		Cargar();
//		inside.center.refresh();
	}
	
	protected void Cargar() { 
		panelResultado.removeAll();
		for (Libro libro : libros) {
			panelResultado.add(new ResultadoDeCatalogo(inside, libro));
		}
	}
	
	
	@Override
	void refresh() {
		for (Component componente : panelResultado.getComponents()) {
			((MedioPanel) componente).refresh();
		}
	}
}

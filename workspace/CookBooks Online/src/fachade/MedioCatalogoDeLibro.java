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
public class MedioCatalogoDeLibro extends MedioPanel {
	private LinkedList<Libro> resultados;
	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JScrollPane scrollPane;
	private JPanel panelResultado;
	private String busqueda;
	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioCatalogoDeLibro(Interface inside2, String busco) {
		this(inside2, new LinkedList<Libro>(), busco);
	}

	public MedioCatalogoDeLibro(Interface inside2, LinkedList<Libro> resultado, String busco) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		resultados = resultado;
		busqueda = busco;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		panelResultado = new JPanel();
		panelResultado.setPreferredSize(new Dimension(901, resultados.size()*105));
		panelResultado.setBounds(0, 71, 901, 533);
		
		 panelResultado.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		 panelResultado.setBackground(new Color(255, 204, 255));
		for (Libro libro : resultados) {
			panelResultado.add(new ResultadoDeCatalogo(inside, libro));
		}
		add(panelResultado);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Resultados");
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

		printError("Resultados para \"".concat(busqueda).concat("\" encontrados: ").concat(new Integer(resultados
				.size()).toString()),labelErrores, true);
		
		

	}
	
	
	
	
	@Override
	protected void Cargar() { //XXX parcheeeeeee, gracias a dios q deje esta funcion suelta
		resultados = inside.contexto.buscarLibro(busqueda); 
		panelResultado.removeAll();
		for (Libro libro : resultados) {
			panelResultado.add(new ResultadoDeCatalogo(inside, libro));
		}
	}

	@Override
	void refresh() {
		if(inside.contexto.usuarioActual().getId() <1){
			inside.centro(new MedioHome(inside));
			inside.top.resetBusqueda();
		}
		for (Component componente : panelResultado.getComponents()) {
			((MedioPanel) componente).refresh();
		}
		
	}
}

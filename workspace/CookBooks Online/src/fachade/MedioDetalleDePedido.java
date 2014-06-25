package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Libro;
import funcionalidad.Pedido;

@SuppressWarnings("serial")
public class MedioDetalleDePedido extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Libro> libros;
	private JScrollPane scrollPane;
	private JButton enviarButton;
	private Pedido pedido;
	private JButton btnAtras;
	private JTextPane labelErrores;
	private JLabel labelTitulo;
	private MedioPanel anterior;

	/**
	 * Create the panel.
	 */
	public MedioDetalleDePedido(Interface inside2, Pedido unPedido, MedioPanel ant) { //TODO CAMBIAR FORMATO AL DEFINIDIO
		inside = inside2;
		libros = unPedido.getLibros();
		pedido = unPedido;
		anterior = ant;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Listado de Libros");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);

		
		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 523, 333, 50);
		add(labelErrores);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"ISBN", "Título", "Autor", "Género", "Idioma", "Editorial",
				"Precio" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(80, 49, 755, 471);
		add(table);

		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					inside.contexto.enviar(pedido);
				} catch (Exception e1) {
					printError(e1.getMessage().concat(" /n"), true);
				}
				enviarButton.setEnabled(false);
				enviarButton.repaint();
			}
		});
		enviarButton.setHorizontalAlignment(SwingConstants.LEFT);
		enviarButton.setToolTipText("Marca como \"Enviado\" el pedido seleccionado.");
		enviarButton.setIcon(new ImageIcon(MedioDetalleDePedido.class.getResource("/fachade/Image/Clear Green Button.png")));
		enviarButton.setBounds(598, 529, 120, 47);
		enviarButton.setEnabled(!pedido.fueEnviado());

		add(enviarButton);

		 btnAtras = new JButton("Atras");
		 btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {//TODO COMO NO HAY BAJADA DE PEDIDO VERIFICAR Q SE CAMBIE EN PEDIDOS COMO ENVIADO SIA APRETO
				anterior.Cargar();
				inside.centro(anterior);
			}
		});
		 btnAtras.setIcon(new ImageIcon(MedioDetalleDePedido.class.getResource("/fachade/Image/Import Document.png")));
		 btnAtras.setHorizontalAlignment(SwingConstants.LEFT);
		 btnAtras.setBounds(716, 529, 120, 47);
		add(btnAtras);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 78, 812, 442);
		add(scrollPane);

		Cargar();
	}

	protected void Cargar() {
		Iterator<Libro> iterador = libros.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ISBN", "Título", "Autor", "Género", "Idioma",
						"Editorial", "Precio" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		while (iterador.hasNext()) {
			Libro libro = iterador.next();
			model.addRow(new String[] {
					libro.getIsbn().toString(), libro.getTitulo(),
					libro.getAutor().toString(), libro.getGenero(), libro.getIdioma(),
					libro.getEditorial(),
					new Double(libro.getPrecio()).toString() });

		}
		table.setModel(model);
		table.repaint();
	}

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
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


}

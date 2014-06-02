package fachade;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import funcionalidad.Libro;
import funcionalidad.Pedido;

import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class MedioDetalleDePedido extends JPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Libro> libros;
	private JScrollPane scrollPane;
	private JButton enviarButton;
	private Pedido pedido;
	private JButton btnAtras;

	/**
	 * Create the panel.
	 */
	public MedioDetalleDePedido(Interface inside2, Pedido unPedido) {
		inside = inside2;
		libros = unPedido.getLibros();
		pedido = unPedido;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"ISBN", "Titulo", "Autor", "Genero", "Idioma", "Editorial",
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
		enviarButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.contexto.enviar(pedido);
				enviarButton.setEnabled(false);
				enviarButton.repaint();
			}
		});
		enviarButton.setHorizontalAlignment(SwingConstants.LEFT);
		enviarButton.setToolTipText("Marca como \"Enviado\" el pedido seleccionado.");
		enviarButton.setIcon(new ImageIcon(MedioDetalleDePedido.class.getResource("/fachade/Image/Clear Green Button.png")));
		enviarButton.setBounds(598, 529, 120, 47);
		enviarButton.setEnabled(!pedido.estado());

		add(enviarButton);

		 btnAtras = new JButton("Atras");
		 btnAtras.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioPedidos(inside));
			}
		});
		 btnAtras.setIcon(new ImageIcon(MedioDetalleDePedido.class.getResource("/fachade/Image/Import Document.png")));
		 btnAtras.setToolTipText("Muestra los libros encargados en el pedido.");
		 btnAtras.setHorizontalAlignment(SwingConstants.LEFT);
		 btnAtras.setBounds(716, 529, 120, 47);
		add(btnAtras);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 19, 812, 501);
		add(scrollPane);

		Cargar();
	}

	private void Cargar() {
		Iterator<Libro> iterador = libros.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ISBN", "Titulo", "Autor", "Genero", "Idioma",
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
					libro.getAutor(), libro.getGenero(), libro.getIdioma(),
					libro.getEditorial(),
					new Double(libro.getPrecio()).toString() });

		}
		table.setModel(model);
		table.repaint();
	}
	
	// averiguar como
	public void OrdenarPorISBN() {}
	public void OrdenarPorTitulo() {}
	public void OrdenarPorAutor() {}
	public void OrdenarPorGenero() {}
	public void OrdenarPorIdioma() {}
	public void OrdenarPorEditorial() {}
	public void OrdenarPorPrecio() {}
}

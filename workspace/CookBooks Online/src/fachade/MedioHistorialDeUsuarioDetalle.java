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
public class MedioHistorialDeUsuarioDetalle extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Libro> libros;
	private JButton btnAtras;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;
	private Pedido pedido;
	private MedioPanel anterior;

	/**
	 * Create the panel.
	 */
	public MedioHistorialDeUsuarioDetalle(Interface inside2, Pedido ped, MedioPanel ant) {
		inside = inside2;
		pedido = ped;
		anterior = ant;

		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Listado de Libros");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);

		table = new JTable();
		table.setAutoCreateRowSorter(true);
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

		btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
			}
		});
		btnAtras.setIcon(new ImageIcon(MedioHistorialDeUsuarioDetalle.class.getResource("/fachade/Image/Import Document.png")));
		btnAtras.setToolTipText("");
		btnAtras.setHorizontalAlignment(SwingConstants.LEFT);
		btnAtras.setBounds(714, 531, 120, 47);
		add(btnAtras);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 78, 812, 442);
		add(scrollPane);
		
		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(61, 531, 333, 63);
		add(labelErrores);
		

		Cargar();
	}

	protected void Cargar() {
		libros = pedido.getLibros();
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
}
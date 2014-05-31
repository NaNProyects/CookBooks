package fachade;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter; //TODO JOSE

import funcionalidad.Autor;
import funcionalidad.Libro;

import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MedioListaDeLibros extends JPanel {
	private JTable table;
	private Interface inside;
	private LinkedList libros;

	/**
	 * Create the panel.
	 */
	public MedioListaDeLibros(Interface inside2) {
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"ISBN", "Titulo", "Autor", "Genero", "Idioma", "Editorial",
				"Precio" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class };

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

		JButton btnNewButton = new JButton("Nuevo");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioEdicionDeLibro(inside));
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setToolTipText("Agrega un nuevo libro");
		btnNewButton.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/New Document.png")));
		btnNewButton.setBounds(478, 531, 120, 47);
		add(btnNewButton);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioEdicionDeLibro(inside, (Libro) libros
						.get(table.getSelectedRow())));
			}
		});
		btnEditar.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/Write Document.png")));
		btnEditar.setToolTipText("Edite datos del libro seleccionado");
		btnEditar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEditar.setBounds(596, 531, 120, 47);
		add(btnEditar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.contexto.eliminar((Libro) libros.get(table
						.getSelectedRow()));
				table.repaint();
				Cargar();
			}
		});
		btnEliminar.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/Remove Document.png")));
		btnEliminar.setToolTipText("Elimina libro seleccionado");
		btnEliminar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEliminar.setBounds(714, 531, 120, 47);
		add(btnEliminar);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 19, 812, 501);
		add(scrollPane);

		Cargar();
	}

	private void Cargar() {
		libros = inside.contexto.libros();
		Iterator iterador = libros.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ISBN", "Titulo", "Autor", "Genero", "Idioma",
						"Editorial", "Precio" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class };

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
			Libro libro = (Libro) iterador.next();
			model.addRow(new String[] {
					libro.getIsbn().toString(), libro.getTitulo(),
					libro.getAutor(), libro.getGenero(), libro.getIdioma(),
					libro.getEditorial(),
					new Double(libro.getPrecio()).toString() });

		}
		table.setModel(model);
		
		 TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model); //TODO JOSE
		 table.setRowSorter(sorter); //TODO JOSE

		table.repaint();
	}
}

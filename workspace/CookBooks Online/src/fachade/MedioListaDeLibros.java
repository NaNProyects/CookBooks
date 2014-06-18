package fachade;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import funcionalidad.Libro;

import javax.swing.JScrollPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class MedioListaDeLibros extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Libro> libros;
	private JButton btnNewButton;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;

	/**
	 * Create the panel.
	 */
	public MedioListaDeLibros(Interface inside2) {
		inside = inside2;

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

		btnNewButton = new JButton("Nuevo");
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

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					inside.centro(new MedioEdicionDeLibro(inside, selected()));
				} catch (Exception e1) {
					if (e1.getMessage().equalsIgnoreCase(
							"Debe selecionar el Libro a")){
						labelErrores.setForeground(Color.RED);
						printError(e1.getMessage().concat(" eliminar /n"), false);
						printError(e1.getMessage().concat(" modificar /n"), true);
					}
				}
			}
		});
		btnEditar.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/Write Document.png")));
		btnEditar.setToolTipText("Edite datos del libro seleccionado");
		btnEditar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEditar.setBounds(596, 531, 120, 47);
		add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try{
				if (inside.contexto.eliminar(selected())) {
					libros.remove(selected());
					table.repaint();
					Cargar();
					printError("No se puede eliminar un libro que figure en pedidos /n", false);
					labelErrores.setForeground(Color.GREEN);
					printError(
							"Eliminacion exitosa /n",
							true);
				} else {
					printError(
							"Eliminacion exitosa /n",
							false);
					labelErrores.setForeground(Color.RED);
					printError("No se puede eliminar un libro que figure en pedidos /n", true);
				}
				}
				catch(Exception e1){
					if(e1.getMessage().equalsIgnoreCase("Debe selecionar el Libro a")){
						labelErrores.setForeground(Color.RED);
						printError(e1.getMessage().concat(" modificar /n"), false);
						printError(e1.getMessage().concat(" eliminar /n"), true);
					}
				}
			}
		});
		btnEliminar.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/Remove Document.png")));
		btnEliminar.setToolTipText("Elimina libro seleccionado");
		btnEliminar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEliminar.setBounds(714, 531, 120, 47);
		add(btnEliminar);

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

	private void Cargar() {
		libros = inside.contexto.libros();
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
					libro.getAutor().toString(), libro.getGenero(), libro.getIdioma(),
					libro.getEditorial(),
					new Double(libro.getPrecio()).toString() });

		}
		table.setModel(model);
		table.repaint();
	}
	
	private Libro selected() throws Exception{
		try {
		for (Iterator<Libro> iterator = libros.iterator(); iterator.hasNext();) {
			Libro iterable_element = iterator.next();
			if (iterable_element.getIsbn() == table.getValueAt(table.getSelectedRow(), 0) ){

				return iterable_element;
			}
		}		
		return null;
		}
		catch(Exception e1){
			throw new Exception("Debe selecionar el Libro a");
			
		}
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

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}
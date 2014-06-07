package fachade;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import funcionalidad.Autor;

import javax.swing.JScrollPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MedioListaDeAutores extends JPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Autor> autores;
	private JButton btnNewButton;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;
	private JTextField textField;
	private JLabel lblBuscarAutor;
	private JTextField txtBuscarAutor;
	private JButton botonBuscar;

	/**
	 * Create the panel.
	 */
	public MedioListaDeAutores(Interface inside2) {
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Listado de Autores");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);

		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Nombre", "Apellido" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(80, 49, 755, 471);
		add(table);

		btnNewButton = new JButton("Nuevo");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioEdicionDeAutor(inside,
						(MedioListaDeAutores) inside.center));
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setToolTipText("Agrega un nuevo Autor");
		btnNewButton.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/New Document.png")));
		btnNewButton.setBounds(478, 531, 120, 47);
		add(btnNewButton);

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					inside.centro(new MedioEdicionDeAutor(inside,
							(MedioListaDeAutores) inside.center, selected()));
				} catch (Exception e1) {
					if(e1.getMessage().equalsIgnoreCase("Debe selecionar el Autor a")){
						labelErrores.setForeground(Color.RED);
						printError(e1.getMessage().concat(" eliminar /n"), false);
						printError(e1.getMessage().concat(" modificar /n"), true);
					}
				}
			}
		});
		btnEditar.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/Write Document.png")));
		btnEditar.setToolTipText("Edite datos del Autor seleccionado");
		btnEditar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEditar.setBounds(596, 531, 120, 47);
		add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					if (inside.contexto.eliminar(selected())) {
						autores.remove(selected());
						table.repaint();
						Cargar();
						printError(
								"No se puede eliminar un autor que posea libros /n",
								false);
						labelErrores.setForeground(Color.GREEN);
						printError("Eliminacion exitosa /n", true);
					} else {
						printError("Eliminacion exitosa /n", false);
						labelErrores.setForeground(Color.RED);
						printError(
								"No se puede eliminar un autor que posea libros /n",
								true);
					}
				} catch (Exception e1) {
					if(e1.getMessage().equalsIgnoreCase("Debe selecionar el Autor a")){
						labelErrores.setForeground(Color.RED);
						printError(e1.getMessage().concat(" modificar /n"), false);
						printError(e1.getMessage().concat(" eliminar /n"), true);
					}
				}
			}
		});
		btnEliminar.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/Remove Document.png")));
		btnEliminar.setToolTipText("Elimina Autor seleccionado");
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
		labelErrores.setBounds(22, 531, 200, 63);
		add(labelErrores);

		lblBuscarAutor = new JLabel("Buscar Autor");
		lblBuscarAutor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBuscarAutor.setBounds(598, 13, 83, 14);
		add(lblBuscarAutor);

		txtBuscarAutor = new JTextField();
		lblBuscarAutor.setLabelFor(txtBuscarAutor);
		txtBuscarAutor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Cargar();
				}

			}
		});
		txtBuscarAutor.setText("");
		txtBuscarAutor.setBounds(598, 37, 190, 23);
		add(txtBuscarAutor);
		txtBuscarAutor.setColumns(10);

		botonBuscar = new JButton("");
		botonBuscar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Cargar();
			}
		});
		botonBuscar.setIcon(new ImageIcon(Superior.class
				.getResource("/fachade/Image/lupa-icono-3813-16.png")));
		botonBuscar.setBounds(798, 37, 36, 23);
		add(botonBuscar);

		Cargar();
	}

	private void Cargar() {
		autores = filtrar();
		inside.contexto.autores();
		Iterator<Autor> iterador = autores.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Nombre", "Apellido" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		while (iterador.hasNext()) {
			Autor autor = iterador.next();
			model.addRow(new String[] { autor.nombre(), autor.getApellido() });

		}
		table.setModel(model);
		table.repaint();
	}

	private LinkedList<Autor> filtrar() {
		LinkedList<Autor> retorno = new LinkedList<Autor>();

		for (Autor autor : inside.contexto.autores()) {
			if ((autor.nombre().toLowerCase().contains(txtBuscarAutor.getText()
					.toLowerCase()))
					|| (autor.getApellido().toLowerCase()
							.contains(txtBuscarAutor.getText().toLowerCase()))) {
				retorno.add(autor);
			}
		}

		return retorno;
	}

	private Autor selected() throws Exception {
		try {
			for (Iterator<Autor> iterator = autores.iterator(); iterator
					.hasNext();) {
				Autor iterable_element = iterator.next();
				if ((iterable_element.nombre() == table.getValueAt(
						table.getSelectedRow(), 0))
						&& (iterable_element.getApellido() == table.getValueAt(
								table.getSelectedRow(), 1))) {
					return iterable_element;
				}
			}
			return null;
		} catch (Exception e1) {
			throw new Exception("Debe selecionar el Autor a");

		}
	}

	/**
	 * Retorna si existe el autor pero con diferente isbn
	 * 
	 * @param Autor
	 */
	public Boolean existAutor(Autor unAutor) {
		for (Autor autor : autores) {
			if ((autor.id().compareTo(unAutor.id()) != 0)
					&& (autor.nombre().compareToIgnoreCase(unAutor.nombre()) == 0)
					&& (autor.getApellido().compareToIgnoreCase(
							unAutor.getApellido()) == 0)) {
				return true;
			}
		}
		return false;

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
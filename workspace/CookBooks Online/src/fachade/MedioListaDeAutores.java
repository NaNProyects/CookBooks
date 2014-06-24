package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;

@SuppressWarnings("serial")
public class MedioListaDeAutores extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Autor> autores;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;
	private JLabel lblBuscarAutor;
	private JTextField txtBuscarAutor;
	private JButton botonBuscar;
	private JPanel panel;
	private JPanel panelSombra;

	/**
	 * Create the panel.
	 */
	public MedioListaDeAutores(Interface inside2) {
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		//--------------------------------------------------- panel?
				panel = new JPanel();
				panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				panel.setBackground(new Color(255, 204, 255));
				panel.setBounds(168, 152, 499, 193);
				panel.setVisible(false);
				add(panel);
				panel.setLayout(null);
				
				panelSombra = new JPanel();
				panelSombra.setBackground(new Color(255, 204, 255,125));
				panelSombra.setBounds(0, 0, 901, 601);
				panelSombra.setVisible(false);
				add(panelSombra);
				//--------------------------------------------------- panel?

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

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioEdicionDeAutor(inside,
						(MedioListaDeAutores) inside.center));
			}
		});
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setToolTipText("Agrega un nuevo Autor");
		btnNuevo.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/New Document.png")));
		btnNuevo.setBounds(478, 531, 120, 47);
		add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					inside.centro(new MedioEdicionDeAutor(inside,
							(MedioListaDeAutores) inside.center, selected()));
				} catch (Exception e1) {
					if(e1.getMessage().equalsIgnoreCase("Debe seleccionar el Autor a")){
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
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					validarEliminado(selected());
				} catch (Exception e1) {
					if(e1.getMessage().equalsIgnoreCase("Debe seleccionar el Autor a")){
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
		botonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cargar();
			}
		});
		botonBuscar.setIcon(new ImageIcon(Superior.class
				.getResource("/fachade/Image/lupa-icono-3813-16.png")));
		botonBuscar.setBounds(798, 37, 36, 23);
		add(botonBuscar);

		Cargar();
	}

	protected void Cargar() {
		autores = filtrar();
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
			model.addRow(new String[] { autor.getNombre(), autor.getApellido() });

		}
		table.setModel(model);
		table.repaint();
	}

	private LinkedList<Autor> filtrar() {
		LinkedList<Autor> retorno = new LinkedList<Autor>();

		try {
			for (Autor autor : inside.contexto.listarAutores()) {
				if ((autor.getNombre().toLowerCase().contains(txtBuscarAutor.getText()
						.toLowerCase()))
						|| (autor.getApellido().toLowerCase()
								.contains(txtBuscarAutor.getText().toLowerCase()))) {
					retorno.add(autor);
				}
			}
		} catch (Exception e) {
			printError(e.getMessage().concat(" /n"), true);
		}

		return retorno;
	}

	private Autor selected() throws Exception {
		try {
			for (Iterator<Autor> iterator = autores.iterator(); iterator
					.hasNext();) {
				Autor iterable_element = iterator.next();
				if ((iterable_element.getNombre() == table.getValueAt(
						table.getSelectedRow(), 0))
						&& (iterable_element.getApellido() == table.getValueAt(
								table.getSelectedRow(), 1))) {
					return iterable_element;
				}
			}
			return null;
		} catch (Exception e1) {

			throw new Exception("Debe seleccionar el Autor a");


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
					&& (autor.getNombre().compareToIgnoreCase(unAutor.getNombre()) == 0)
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
	
	private void validarEliminado(Autor unAutor){
		final Autor autor = unAutor; // TODO aca puede haber un error
		panel.setVisible(true);
		
		JPanel panelConfirmacion = new JPanel();
		panelConfirmacion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelConfirmacion.setBackground(new Color(255, 204, 255));
		panelConfirmacion.setBounds(0, 0, 499, 193);
		panelConfirmacion.setVisible(true);
		
		panel.add(panelConfirmacion);
		panelConfirmacion.setLayout(null);
		
		JTextPane tituloFlotante =	new JTextPane();
		tituloFlotante.setText("¿Seguro que desea eliminar el autor seleccionado?");
		tituloFlotante.setBorder(null);
		tituloFlotante.setEditable(false);
		tituloFlotante.setBackground(new Color(255, 204, 255));
		tituloFlotante.setFont(new Font("Tahoma", Font.BOLD, 20));
		tituloFlotante.setBounds(10, 11, 479, 67);
		panelConfirmacion.add(tituloFlotante);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if (inside.contexto.eliminar(autor)) {
							autores.remove(autor);
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
						printError(
								"Eliminacion exitosa /n",
								false);
					}
					finally{
						panel.removeAll();
						panel.setVisible(false);
						swichBoton();
					}
			}
		});
		confirmar.setIcon(new ImageIcon(MedioEdicionDeUsuario.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		confirmar.setBounds(78, 151, 144, 31);
		panelConfirmacion.add(confirmar);
		
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.setVisible(false);
				swichBoton();
			}
		});
		cancelar.setIcon(new ImageIcon(MedioEdicionDeUsuario.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(301, 151, 144, 31);
		
		swichBoton();
		panelConfirmacion.add(cancelar);
		panel.repaint();
		panelConfirmacion.repaint();
		
		
		
		
	}

	private void swichBoton(){
		btnNuevo.setEnabled(!btnNuevo.isEnabled());
		btnEliminar.setEnabled(!btnEliminar.isEnabled());
		btnEditar.setEnabled(!btnEditar.isEnabled());
//		panelSombra.setVisible(!panelSombra.isVisible());
	}

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}
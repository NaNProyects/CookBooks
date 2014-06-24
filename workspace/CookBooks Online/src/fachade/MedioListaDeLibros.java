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

import funcionalidad.Libro;

@SuppressWarnings("serial")
public class MedioListaDeLibros extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Libro> libros;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;
	private JLabel lblBuscarLibros;
	private JTextField txtBuscarLibros;
	private JButton botonBuscar;
	private JPanel panel;
	private JPanel panelSombra;

	/**
	 * Create the panel.
	 */
	public MedioListaDeLibros(Interface inside2) {
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
				"Listado de Libros");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);

		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"ISBN", "Titulo", "Autor", "Género", "Idioma", "Editorial",
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

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioEdicionDeLibro(inside,inside.center));
			}
		});
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setToolTipText("Agrega un nuevo libro");
		btnNuevo.setIcon(new ImageIcon(MedioListaDeLibros.class
				.getResource("/fachade/Image/New Document.png")));
		btnNuevo.setBounds(478, 531, 120, 47);
		add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					inside.centro(new MedioEdicionDeLibro(inside, selected(),inside.center));
				} catch (Exception e1) {
					if (e1.getMessage().equalsIgnoreCase(
							"Debe seleccionar el Libro a")){
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
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					validarEliminado(selected());
				}
				catch(Exception e1){
					if(e1.getMessage().equalsIgnoreCase("Debe seleccionar el Libro a")){
						labelErrores.setForeground(Color.RED);
						printError(e1.getMessage().concat(" modificar /n"), false);
						printError(e1.getMessage().concat(" eliminar /n"), true);
					}
					else{
						printError(
								"Eliminacion exitosa /n",
								false);
//						labelErrores.setForeground(Color.RED);
//						printError("No se puede eliminar un libro que figure en pedidos /n", true);
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
		
		lblBuscarLibros = new JLabel("Buscar Libro");
		lblBuscarLibros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBuscarLibros.setBounds(598, 13, 83, 14);
		add(lblBuscarLibros);

		txtBuscarLibros = new JTextField();
		lblBuscarLibros.setLabelFor(txtBuscarLibros);
		txtBuscarLibros.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Cargar();
				}

			}
		});
		txtBuscarLibros.setText("");
		txtBuscarLibros.setBounds(598, 37, 190, 23);
		add(txtBuscarLibros);
		txtBuscarLibros.setColumns(10);

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

	private LinkedList<Libro> filtrar() {
		LinkedList<Libro> retorno = new LinkedList<Libro>();

		try {
			for (Libro libro : inside.contexto.listarLibros()) {
				if (
						(libro.getAutor().getApellido().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))
						|| 
						(libro.getAutor().getNombre().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))
						|| 
						(libro.getEditorial().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))
						|| 
						(libro.getGenero().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))
						|| 
						(libro.getIdioma().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))
						|| 
						(libro.getTitulo().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))
						|| 
						(libro.getIsbn().toLowerCase().contains(txtBuscarLibros.getText().toLowerCase()))	
						) {
					retorno.add(libro);
				}
			}
		} catch (Exception e) {
			printError(e.getMessage().concat(" /n"), true);
		}

		return retorno;
	}
	
	
	protected void Cargar() {
		libros = filtrar();
		Iterator<Libro> iterador = libros.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ISBN", "Titulo", "Autor", "Género", "Idioma",
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
			throw new Exception("Debe seleccionar el Libro a");
			
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
	
	private void validarEliminado(Libro unLibro){
		final Libro libro = unLibro; // TODO aca puede haber un error
		panel.setVisible(true);
		
		JPanel panelConfirmacion = new JPanel();
		panelConfirmacion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelConfirmacion.setBackground(new Color(255, 204, 255));
		panelConfirmacion.setBounds(0, 0, 499, 193);
		panelConfirmacion.setVisible(true);
		
		panel.add(panelConfirmacion);
		panelConfirmacion.setLayout(null);
		
		JTextPane tituloFlotante =	new JTextPane();
		tituloFlotante.setText("¿Seguro que desea eliminar el libro seleccionado?");
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
						if (inside.contexto.eliminar(libro)) {
							libros.remove(libro);
							table.repaint();
							Cargar();
							printError("No se puede eliminar un libro que figure en pedidos /n", false);
							labelErrores.setForeground(Color.GREEN);
							printError(
									"Eliminacion exitosa /n",
									true);
						} 
						else {
							printError(
									"Eliminacion exitosa /n",
									false);
							labelErrores.setForeground(Color.RED);
							printError("No se puede eliminar un libro que figure en pedidos /n", true);
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
		//panelSombra.setVisible(!panelSombra.isVisible());
	}
	
	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}
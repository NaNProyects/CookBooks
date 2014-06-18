package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class MedioCarrito extends MedioPanel {//TODO agregar imagen a los botones
	private JTable table;
	private Interface inside;
	private LinkedList<Libro> libros;
	private JButton btnConfirmar;
	private JButton btnEliminar;
	private JButton btnVaciar;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;
	private JPanel panelConfirmacion;
	private JLabel consinga;
	private JLabel tarjeta;
	private JLabel lblPin;
	private JTextField pin;
	private JLabel lblPrecio;
	private JTextField valor;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public MedioCarrito(Interface inside2) {
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
		//--------------------------------------------------- panel?
		
		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Carrito");
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

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				validarTarjeta();
				
			}
		});
		btnConfirmar.setHorizontalAlignment(SwingConstants.LEFT);
		btnConfirmar.setToolTipText("Confirma el carrito para su compra");
		btnConfirmar.setBounds(480, 540, 120, 47);
		add(btnConfirmar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
				validarEliminado(selected());
				} catch (Exception e1) {
					if (e1.getMessage().equalsIgnoreCase(
							"Debe selecionar el Libro a")){
						labelErrores.setForeground(Color.RED);
						printError(e1.getMessage().concat(" eliminar /n"), true);
					}
				}
			}
		});
		
		btnEliminar.setToolTipText("Elimina libro seleccionado");
		btnEliminar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEliminar.setBounds(598, 540, 120, 47);
		add(btnEliminar);

		btnVaciar = new JButton("Vaciar");
		btnVaciar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				validarVaciado();
			}
		});
		btnVaciar.setToolTipText("Vacia el carrito");
		btnVaciar.setHorizontalAlignment(SwingConstants.LEFT);
		btnVaciar.setBounds(716, 540, 120, 47);
		add(btnVaciar);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 78, 812, 442);
		add(scrollPane);
		
		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 531, 312, 63);
		add(labelErrores);
		
		lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(345, 526, 46, 14);
		add(lblPrecio);
		
		valor = new JTextField();
		valor.setBounds(344, 540, 119, 38);
		valor.setText(inside.contexto.carrito().getCosto().toString());
		valor.setEditable(false);
		valor.setSelectionColor(Color.WHITE);
		add(valor);
		valor.setColumns(10);
		

		Cargar();
	}

	private void Cargar() {
		libros = inside.contexto.carrito().getLibros();
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
	private void validarTarjeta(){
		panel.setVisible(true);
		
		panelConfirmacion = new JPanel();
		panelConfirmacion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelConfirmacion.setBackground(new Color(255, 204, 255));
		panelConfirmacion.setBounds(0, 0, 499, 193);
		panelConfirmacion.setVisible(true);
		
		panel.add(panelConfirmacion);
		panelConfirmacion.setLayout(null);
		
		JLabel tituloFlotante = DefaultComponentFactory.getInstance().createLabel("Validacion de Tarjeta");
		tituloFlotante.setFont(new Font("Tahoma", Font.BOLD, 20));
		tituloFlotante.setBounds(10, 11, 324, 25);
		panelConfirmacion.add(tituloFlotante);
		
		consinga = new JLabel("Ingrese el PIN de  la siguiente Tarjeta.");
		consinga.setBounds(20, 41, 312, 14);
		panelConfirmacion.add(consinga);
		
		tarjeta = new JLabel("Tarjeta: ".concat(inside.contexto.usuarioActual().getTarjeta()));
		tarjeta.setBounds(30, 66, 284, 14);
		panelConfirmacion.add(tarjeta);
		
		lblPin = new JLabel("PIN:");
		lblPin.setBounds(30, 103, 66, 14);
		panelConfirmacion.add(lblPin);
		
		pin = new JTextField();
		pin.setBounds(68, 100, 66, 20);
		pin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});

		panelConfirmacion.add(pin);
		pin.setColumns(10);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pin.getText().compareTo(inside.contexto.usuarioActual().getPin())==0){ // TODO Probar con pin andando
					inside.contexto.confirmarCarrito();
					Cargar();
					labelErrores.setForeground(Color.GREEN);
					printError("Carrito confirmado por favor revise su historial para informarse del envio", true);
					panel.removeAll();
					panel.setVisible(false);
					swichBoton();
				}
				else{
					labelErrores.setForeground(Color.RED);
					printError("Pin invalido", true);
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
	private void validarVaciado(){
		panel.setVisible(true);
		
		panelConfirmacion = new JPanel();
		panelConfirmacion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelConfirmacion.setBackground(new Color(255, 204, 255));
		panelConfirmacion.setBounds(0, 0, 499, 193);
		panelConfirmacion.setVisible(true);
		
		panel.add(panelConfirmacion);
		panelConfirmacion.setLayout(null);
		
		JLabel tituloFlotante = DefaultComponentFactory.getInstance().createLabel("¿Seguro que desea vaciar el carrito?");
		tituloFlotante.setFont(new Font("Tahoma", Font.BOLD, 20));
		tituloFlotante.setBounds(10, 11, 324, 25);
		panelConfirmacion.add(tituloFlotante);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.contexto.cancelarCarrito();
				valor.setText(inside.contexto.carrito().getCosto().toString());
				valor.repaint();
				Cargar();
				labelErrores.setForeground(Color.GREEN);
				printError("Carrito vaciado", true);
				panel.removeAll();
				panel.setVisible(false);
				swichBoton();
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
	
	private void validarEliminado(Libro unLibro){
		final Libro libro = unLibro; // TODO aca puede haber un error
		panel.setVisible(true);
		
		panelConfirmacion = new JPanel();
		panelConfirmacion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelConfirmacion.setBackground(new Color(255, 204, 255));
		panelConfirmacion.setBounds(0, 0, 499, 193);
		panelConfirmacion.setVisible(true);
		
		panel.add(panelConfirmacion);
		panelConfirmacion.setLayout(null);
		
		JLabel tituloFlotante = DefaultComponentFactory.getInstance().createLabel("¿Seguro que desea eliminar el libro seleccionado?");
		tituloFlotante.setFont(new Font("Tahoma", Font.BOLD, 20));
		tituloFlotante.setBounds(10, 11, 324, 25);
		panelConfirmacion.add(tituloFlotante);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.contexto.carrito().eliminar(libro);
				Cargar();
				valor.setText(inside.contexto.carrito().getCosto().toString());
				valor.repaint();
				labelErrores.setForeground(Color.GREEN);
				printError("Libro eliminado", true);
				panel.removeAll();
				panel.setVisible(false);
				swichBoton();
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
		btnConfirmar.setEnabled(!btnConfirmar.isEnabled());
		btnEliminar.setEnabled(!btnEliminar.isEnabled());
		btnVaciar.setEnabled(!btnVaciar.isEnabled());
		
	}
	
	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}
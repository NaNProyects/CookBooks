package fachade;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import funcionalidad.Pedido;

import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

@SuppressWarnings("serial")
public class MedioPedidos extends JPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Pedido> pedidos;
	private JLabel botonEnviar;
	private JLabel botonDetalles;
	private JButton enviarButton;
	private JButton btnDetalles;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	/**
	 * Create the panel.
	 */
	public MedioPedidos(Interface inside2) {
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Listado de Pedidos");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);
		
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Numero", "Fecha", "Monto", "Usuario", "Libros", "Estado"}) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, String.class, String.class, Object.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false };

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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(80, 49, 755, 471);
		add(table);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 78, 812, 442);
		add(scrollPane);
		
		botonEnviar = new JLabel("Enviar");
		botonEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inside.contexto.enviar(selected());
				table.repaint();
				Cargar();
			}
		});
		botonEnviar.setForeground(Color.BLUE);
		botonEnviar.setBounds(846, 52, 46, 14);
		add(botonEnviar);

		
		botonDetalles = new JLabel("Detalles");
		botonDetalles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioDetalleDePedido(inside, selected()));
			}
		});
		
		botonDetalles.setForeground(Color.BLUE);
		botonDetalles.setBounds(846, 67, 68, 14);
		add(botonDetalles);
		
		enviarButton = new JButton("Enviar");
		enviarButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.contexto.enviar(selected());
				table.repaint();
				Cargar();
			}
		});
		enviarButton.setHorizontalAlignment(SwingConstants.LEFT);
		enviarButton.setToolTipText("Marca como \"Enviado\" el pedido seleccionado.");
		enviarButton.setIcon(new ImageIcon(MedioPedidos.class.getResource("/fachade/Image/Clear Green Button.png")));
		enviarButton.setBounds(598, 529, 120, 47);
		add(enviarButton);

		btnDetalles = new JButton("Detalles");
		btnDetalles.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioDetalleDePedido(inside, selected()));
			}
		});
		 btnDetalles.setIcon(new ImageIcon(MedioPedidos.class.getResource("/fachade/Image/Export To Document.png")));
		 btnDetalles.setToolTipText("Muestra los libros encargados en el pedido.");
		 btnDetalles.setHorizontalAlignment(SwingConstants.LEFT);
		 btnDetalles.setBounds(716, 529, 120, 47);
		add(btnDetalles);
		
		Cargar();
	}

	private void Cargar() {
		pedidos = inside.contexto.pedidos();
		Iterator<Pedido> iterador = pedidos.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] {
						"Numero", "Fecha", "Monto", "Usuario", "Libros", "Estado"}) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, String.class, String.class, Object.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		while (iterador.hasNext()) {
			Pedido pedido = iterador.next();
			model.addRow(new Object[] {					
					pedido.nro(), 
					DateFormat.getDateInstance(DateFormat.SHORT).format(pedido.fecha()),
					pedido.total().toString(), pedido.getUsuario().getNombre(), botonDetalles,
					estado(pedido.estado())});
		}
		
		table.setModel(model);
		table.repaint();
	}
	
	// averiguar como
	private Object estado(Boolean a){
		if(a){
			return "enviado";
		}
		else
		{
			return botonEnviar;
		}
	}
	
	private Pedido selected(){
		for (Pedido pedido : pedidos) {
			if (pedido.nro().compareTo((Integer) table.getValueAt(table.getSelectedRow(), 0)) == 0 ) {
				return pedido;
			}
		}	
		return null;
		
	}
}

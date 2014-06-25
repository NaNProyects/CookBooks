package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
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

import funcionalidad.Pedido;

@SuppressWarnings("serial")
public class MedioHistorialDeUsuario extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Pedido> pedidos;
	private JButton btnDetalles;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;
	/**
	 * Create the panel.
	 */
	public MedioHistorialDeUsuario(Interface inside2) {//TODO EDITAR FORMATO
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 523, 333, 70);
		add(labelErrores);

		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Historial de Compras");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 370, 50);
		add(labelTitulo);
		
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Numero", "Fecha", "Monto", "Cantidad de Libros", "Estado"}) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, String.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(80, 49, 755, 471);
		add(table);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 78, 812, 442);
		add(scrollPane);

		btnDetalles = new JButton("Detalles");
		btnDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {try {
				inside.centro(new MedioHistorialDeUsuarioDetalle(inside, selected(), inside.center));
				printError("Debe selecionar un pedido /n", false);
			} catch (Exception e1) {
				labelErrores.setForeground(Color.RED);
				printError("Debe selecionar un pedido /n", true);

			}

			}
		});
		 btnDetalles.setIcon(new ImageIcon(MedioHistorialDeUsuario.class.getResource("/fachade/Image/Export To Document.png")));
		 btnDetalles.setToolTipText("Muestra los libros encargados en el pedido.");
		 btnDetalles.setHorizontalAlignment(SwingConstants.LEFT);
		 btnDetalles.setBounds(716, 529, 120, 47);
		add(btnDetalles);
		
		Cargar();
	}

	protected void Cargar() {
		try {
			pedidos = inside.contexto.historialDe(inside.contexto.usuarioActual());
		} catch (Exception e) {
			printError(e.getMessage().concat(" /n"), true);
		}
		Iterator<Pedido> iterador = pedidos.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] {
						"Numero", "Fecha", "Monto", "Cantidad de Libros", "Estado"}) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, String.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		while (iterador.hasNext()) {
			Pedido pedido = iterador.next();
			model.addRow(new Object[] {					
					pedido.nro(), 
					DateFormat.getDateInstance(DateFormat.SHORT).format(pedido.fecha()),
					pedido.total().toString(), pedido.getLibros().size(),
					estado(pedido.fueEnviado())});
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
			return "pendiente";
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

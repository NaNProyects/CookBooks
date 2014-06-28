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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class MedioPedidos extends MedioPanel {
	private JTable table;
	private Interface inside;
	private LinkedList<Pedido> pedidos;
	private JButton enviarButton;
	private JButton btnDetalles;
	private JScrollPane scrollPane;
	private JLabel labelTitulo;
	private JTextPane labelErrores;

	/**
	 * Create the panel.
	 */
	public MedioPedidos(Interface inside2) {
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
				"Listado de Pedidos");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					enviarButton.setEnabled(!selected().getEstado());
				} catch (Exception e1) {
					enviarButton.setEnabled(false);
				}
			}
		});
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Número", "Fecha", "Monto", "DNI del Usuario", "Usuario",
				"Cantidad de Libros", "Estado" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
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

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(24, 78, 812, 442);
		add(scrollPane);

		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					inside.contexto.enviar(selected());
					labelErrores.setForeground(new Color(0, 128, 0));
					printError("Pedido enviado /n", true);
					printError("Debe selecionar un pedido /n", false);
					printError("Ocurrió un error /n", false);
				} catch (Exception e1) {
					if(e1.getMessage().contains("ocurrio")){
						printError("Ocurrió un error /n", true);
						printError("Debe selecionar un pedido /n", false);
					}
					else{
						printError("Debe selecionar un pedido /n", true);
					}
					labelErrores.setForeground(Color.RED);
					printError("Pedido enviado /n", false);
				}
				table.repaint();
				Cargar();
			}
		});
		enviarButton.setHorizontalAlignment(SwingConstants.LEFT);
		enviarButton
				.setToolTipText("Marca como \"Enviado\" el pedido seleccionado.");
		enviarButton.setIcon(new ImageIcon(MedioPedidos.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		enviarButton.setBounds(598, 529, 120, 47);
		enviarButton.setEnabled(false);
		add(enviarButton);

		btnDetalles = new JButton("Detalles");
		btnDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					inside.centro(new MedioDetalleDePedido(inside, selected(),
							inside.center));
				} catch (Exception e1) {
					printError("Debe selecionar un pedido /n", true);
				}
			}
		});
		btnDetalles.setIcon(new ImageIcon(MedioPedidos.class
				.getResource("/fachade/Image/Export To Document.png")));
		btnDetalles
				.setToolTipText("Muestra los libros encargados en el pedido.");
		btnDetalles.setHorizontalAlignment(SwingConstants.LEFT);
		btnDetalles.setBounds(716, 529, 120, 47);
		add(btnDetalles);

		Cargar();
	}

	protected void Cargar() {
		try {
			pedidos = inside.contexto.listarPedidos();
		} catch (Exception e) {
			printError(e.getMessage().concat(" /n"), true);
		}
		Iterator<Pedido> iterador = pedidos.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Número", "Fecha", "Monto", "DNI del Usuario",
						"Usuario", "Cantidad de Libros", "Estado" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class,
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
			Pedido pedido = iterador.next();
			model.addRow(new Object[] {
					pedido.nro(),
					DateFormat.getDateInstance(DateFormat.SHORT).format(
							pedido.fecha()),
					pedido.total().toString(),
					pedido.getUsuario().getDni(),
					pedido.getUsuario().getNombre().concat(" ")
							.concat(pedido.getUsuario().getApellido()),
					pedido.getLibros().size(), estado(pedido.fueEnviado()) });
		}

		table.setModel(model);
		table.repaint();
	}

	// averiguar como
	private Object estado(Boolean a) {
		if (a) {
			return "enviado";
		} else {
			return "pendiente";
		}
	}

	private Pedido selected() {
		for (Pedido pedido : pedidos) {
			if (pedido.nro().compareTo(
					(Integer) table.getValueAt(table.getSelectedRow(), 0)) == 0) {
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

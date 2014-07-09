package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Usuario;

@SuppressWarnings("serial")
public class MedioUsuariosDeUnPeriodo extends MedioPanel {

	private Interface inside;
	private LinkedList<Usuario> usuarios = new LinkedList<Usuario>();
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JLabel lblHasta;
	private JSpinner hasta;
	private JLabel lblDesde;
	private JSpinner desde;
	private JButton btnListar;
	private JTable table;
	private JScrollPane scrollPane;

	public MedioUsuariosDeUnPeriodo(Interface inside2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Usuarios registrado en un periodo");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 380, 50);
		add(lblTitulo);

		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"DNI", "E-Mail", "Nombre", "Apellido", "Direccion", "Tel\u00E9fono", "Tarjeta"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
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
		table.setBounds(54, 109, 755, 471);
		add(table);

		labelErrores = new JTextPane();
		labelErrores.setBounds(22, 50, 867, 14);
		add(labelErrores);
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.BLACK);

		lblDesde = new JLabel("Entre:");
		lblDesde.setBounds(54, 74, 62, 14);
		add(lblDesde);

		desde = new JSpinner();
		desde.setModel(new SpinnerDateModel(new Date(1404702000000L), null,
				null, Calendar.WEEK_OF_YEAR));
		desde.setEditor(new JSpinner.DateEditor(desde, "dd/MM/yyyy"));
		desde.setBounds(93, 71, 107, 20);
		add(desde);

		lblHasta = new JLabel("Y:");
		lblHasta.setBounds(229, 74, 62, 14);
		add(lblHasta);

		hasta = new JSpinner();
		hasta.setModel(new SpinnerDateModel(new Date(1404702000000L), null,
				null, Calendar.WEEK_OF_YEAR));
		hasta.setEditor(new JSpinner.DateEditor(hasta, "dd/MM/yyyy"));
		hasta.setBounds(265, 71, 107, 20);
		add(hasta);

		System.out.println(hasta.getValue().getClass().toString());

		btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		btnListar.setBounds(405, 70, 89, 23);
		add(btnListar);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(54, 109, 755, 471);
		add(scrollPane);

	}

	@SuppressWarnings("unused")
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

	private void listar() {
		 usuarios= inside.contexto.usuariosResgistradosEntre((Date)desde.getValue(),(Date)hasta.getValue()); 
		Cargar();
	}

	protected void Cargar() {
		Iterator<Usuario> iterador = usuarios.iterator();
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "E-Mail", "Nombre", "Apellido",
						"Direccion", "Teléfono", "Tarjeta" }) {
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
			Usuario usuario = iterador.next();
			model.addRow(new String[] { usuario.getDni().toString(),
					usuario.getEmail(), usuario.getNombre(),
					usuario.getApellido(), usuario.getDireccion(),
					usuario.getTelefono(), usuario.getTarjeta() });

		}
		table.setModel(model);
		table.repaint();
	}

	@Override
	void refresh() {
		inside.centro(new MedioHome(inside));

	}
}

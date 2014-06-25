package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Libro;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MedioPerfilDeLibroVistazo extends MedioPanel {//TODO agregar imagen a los botones
	@SuppressWarnings("unused")
	private String tituloPanel;
	private Libro libro;
	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JLabel tituloLabel;
	private JScrollPane scrollPane;
	private JTextPane textVistazo;
	private MedioPanel anterior;
	private JButton atras;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */

	public MedioPerfilDeLibroVistazo(Interface inside2, Libro libro2, MedioPanel ant) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		libro = libro2;
		anterior = ant;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 529, 333, 63);
		add(labelErrores);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Vistazo de Libro");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		tituloLabel = new JLabel("Título: ".concat(libro.getTitulo()));
		tituloLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		tituloLabel.setBounds(52, 58, 658, 24);
		add(tituloLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 93, 798, 425);
		add(scrollPane);

		textVistazo = new JTextPane();
		textVistazo.setText(libro.getVistaso());
		textVistazo.setEditable(false);
		textVistazo.setSelectionColor(Color.WHITE);
		
		scrollPane.setViewportView(textVistazo);
		
		atras = new JButton("Atras");
		atras.setIcon(new ImageIcon(MedioPerfilDeLibroVistazo.class.getResource("/fachade/Image/Import Document.png")));
		atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
				inside.center.refresh();
			}
		});
		atras.setBounds(747, 542, 144, 31);
		add(atras);

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

	protected void refresh() {
	}
}

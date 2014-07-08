package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

@SuppressWarnings("serial")
public class MedioAyuda extends MedioPanel {

	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JScrollPane scrollPane;
	private MedioPanel anterior;
	private LinkedList<String> imagenes;
	private JButton atras;
	private JPanel panelImagen;


	public MedioAyuda(Interface inside2, MedioPanel ant, LinkedList<String> imag, String titulo) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		anterior = ant;
		imagenes = imag;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				titulo);
		lblTitulo.setBounds(22, 10, 200, 50);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblTitulo);

		 
		labelErrores = new JTextPane();
		labelErrores.setBounds(22, 50, 867, 14);
		add(labelErrores);
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.BLACK);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 71, 833, 466);
		add(scrollPane);
		
		panelImagen = new JPanel();
		scrollPane.setViewportView(panelImagen);
		for (String string : imagenes) {
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(MedioAyuda.class.getResource(string)));
			panelImagen.add(lblNewLabel);			
		}

		
		atras = new JButton("Atras");
		atras.setIcon(new ImageIcon(MedioAyuda.class.getResource("/fachade/Image/Import Document.png")));
		atras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);			
			}
		});
		atras.setBounds(718, 562, 171, 31);
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

	protected void Cargar() { 

	}
	
	
	@Override
	void refresh() {
		inside.centro(new MedioHome(inside));
		
	}
}

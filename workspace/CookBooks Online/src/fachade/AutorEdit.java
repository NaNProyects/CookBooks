package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.MaskFormatter;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AutorEdit extends JPanel {

	private JLabel labelTituloPanel;
	private JTextField campoNombre;
	private JLabel labelNombreAutor;
	private JButton botonConfirmar;
	private MedioAutores inside;
	private Autor selectedAutor;
	private JButton botonCancelar;
	private JTextField labelError;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public AutorEdit(MedioAutores inside2) {
		this(inside2, new Autor(-1, ""));

	}

	public AutorEdit(MedioAutores inside2, Autor selectedAutor2) {
		inside = inside2;
		selectedAutor = selectedAutor2;
		setBorder(null);
		setBackground(new Color(255, 204, 255));
		setBounds(0, 0, 368, 192);
		setLayout(null);

		labelTituloPanel = DefaultComponentFactory.getInstance().createTitle(
				"Autor:");
		labelTituloPanel.setFont(new Font("Tahoma", Font.BOLD, 18));
		labelTituloPanel.setBounds(10, 0, 161, 50);
		add(labelTituloPanel);

		campoNombre = new JTextField();
		campoNombre.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					confirmar();
				}
			}
		});
		campoNombre.setText(selectedAutor.nombre());
		campoNombre.setBounds(45, 85, 280, 20);
		add(campoNombre);

		labelNombreAutor = new JLabel("Nombre y Apellido:");
		labelNombreAutor.setBounds(20, 60, 202, 14);
		add(labelNombreAutor);

		botonConfirmar = new JButton("Confirmar");
		botonConfirmar.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				confirmar();
			}
		});
		botonConfirmar.setHorizontalAlignment(SwingConstants.LEFT);
		botonConfirmar.setIcon(new ImageIcon(MedioAutores.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		botonConfirmar.setBounds(45, 116, 130, 31);
		add(botonConfirmar);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				selectedAutor = new Autor(000, "");
				campoNombre.setText("");
				labelError.setVisible(false);
			}
		});
		botonCancelar.setHorizontalAlignment(SwingConstants.LEFT);
		botonCancelar.setIcon(new ImageIcon(MedioAutores.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		botonCancelar.setBounds(195, 116, 130, 31);
		add(botonCancelar);

		labelError = new JTextField("");
		labelError.setBorder(null);
		labelError.setEditable(false);
		labelError.setBackground(new Color(255, 204, 255));
		labelError.setForeground(Color.RED);
		labelError.setBounds(45, 155, 280, 14);
		labelError.setVisible(false);
		add(labelError);

	}

	private void confirmar() {
		if (campoNombre.getText().length() <= 80) {
			if (selectedAutor.id() == -1) {
				selectedAutor.nombre(campoNombre.getText());
				try {
					inside.arryAutores.add(inside.inside.contexto
							.agregar(selectedAutor.nombre()));
					inside.Cargar();
					inside.panelEdicion.removeAll();
					inside.panelEdicion.repaint();
				} catch (Exception e1) {
					labelError.setText(e1.getMessage());
					labelError.setVisible(true);
				}

			} else {
				try {
					if (inside.inside.contexto.actualizar(new Autor(
							selectedAutor.id(), campoNombre.getText()))) {
						selectedAutor.nombre(campoNombre.getText());
						inside.Cargar();
						inside.panelEdicion.removeAll();
						inside.panelEdicion.repaint();
					} else {
						labelError.setText("El Autor ya existe");
						labelError.setVisible(true);
					}
				} catch (Exception e1) {
					labelError.setText(e1.getMessage());
					labelError.setVisible(true);
				}

			}
		} else {
			labelError.setText("El Autor debe contener menos de 80 caracteres");
			labelError.setVisible(true);
		}
	}
}

package fachade;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextField;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.ComponentOrientation;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class MedioEdicionDeAutor extends JPanel {
	private String tituloPanel = "Nuevo Autor";
	private Autor autor;
	private JTextField apellidoAutor;
	private Interface inside;
	private TextField nombreAutor;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private MedioListaDeAutores listaDeAutores;

	/**
	 * Create the panel.
	 * @param listaDeAutores 
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioEdicionDeAutor(Interface inside2, MedioListaDeAutores listaDeAutores) {
		this(inside2,listaDeAutores, new Autor(0, "")); //TODO ACA FALTA EL APELLIDO CUANDO LO PONGAS
	}


	public MedioEdicionDeAutor(Interface inside2, MedioListaDeAutores listaDeAutores, Autor autor2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		this.listaDeAutores = listaDeAutores;
		autor = autor2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(22, 144, 333, 63);
		labelErrores.setVisible(false);
		add(labelErrores);

		nombreAutor = new TextField();
		nombreAutor.setText(autor.nombre());
		nombreAutor.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				validarNombre();
			}
		});

		nombreAutor.setText(autor.nombre());
		nombreAutor.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		nombreAutor.setBounds(108, 57, 184, 20);
		add(nombreAutor);

		apellidoAutor = new JTextField();
		apellidoAutor.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				validarApellido();
			}
		});
		apellidoAutor.setText(autor.getApellido());

		if (!(autor.id() == 0)) {
			tituloPanel = "Editar Autor";
		}
		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				tituloPanel);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		apellidoAutor.setBounds(108, 88, 184, 20);
		add(apellidoAutor);

		JLabel nombreLabel = new JLabel("Nombre*");
		nombreLabel.setLabelFor(nombreAutor);
		nombreLabel.setBounds(52, 60, 46, 14);
		add(nombreLabel);

		JLabel apellidoLabel = new JLabel("Apellido*");
		apellidoLabel.setLabelFor(apellidoAutor);
		apellidoLabel.setBounds(52, 91, 46, 14);
		add(apellidoLabel);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				autor.nombre(nombreAutor.getText());
				autor.setApellido(apellidoAutor.getText());
				if (ValidarAutor()) {	
					if (autor.id()==0) {
						try {
							inside.contexto.agregar(autor.nombre());
						} catch (Exception e1) {
							printError(e1.getMessage().concat(" /n"), true);
						}
					} else {
						inside.contexto.actualizar(autor);
					}
				inside.centro(new MedioListaDeAutores(inside));					
				}
			}
		});

		confirmar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		confirmar.setBounds(22, 218, 144, 31);
		add(confirmar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioListaDeAutores(inside));
			}
		});
		cancelar.setIcon(new ImageIcon(MedioEdicionDeLibro.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(176, 218, 144, 31);
		add(cancelar);

		JLabel lblcamposObligatorios = new JLabel("*Campos obligatorios.");
		lblcamposObligatorios.setBounds(22, 119, 298, 14);
		add(lblcamposObligatorios);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{nombreAutor, apellidoAutor, confirmar, cancelar}));

	}

	private boolean ValidarAutor() {

		return (validarNombre() & validarApellido() & validarExistencia());

	}


	private Boolean validarNombre() {
		if ((nombreAutor.getText().length() <= 45)
				&& (nombreAutor.getText().length() > 0)) {
			printError("El nombre del autor debe contener entre 1 y 45 caracteres /n", false);
			return true;

		} else {
			printError("El nombre del autor debe contener entre 1 y 45 caracteres /n", true);
			return false;
		}

	}

	private Boolean validarApellido() {
		if ((apellidoAutor.getText().length() < 45)
				&& (apellidoAutor.getText().length() > 0)) {
			printError("El apellido del autor debe contener entre 1 y 45 caracteres /n", false);
			return true;

		} else {
			printError("El apellido del autor debe contener entre 1 y 45 caracteres /n", true);
			return false;
		}
	}

	private Boolean validarExistencia() {
		if (!listaDeAutores.existAutor(autor)) {
			printError("El Autor ya existe /n", false);
			return true;

		} else {
			printError("El Autor ya existe /n", true);
			return false;
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

}

package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Document;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.CookBooks;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

@SuppressWarnings("serial")
public class MedioContacto extends MedioPanel {

	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private TextField mailUsuario;
	private JLabel lblSuEmail;
	private JTextPane errorMail;
	private TextField nombreUsuario;
	private JTextPane errorNombre;
	private JLabel lblSuMail;
	private JScrollPane scrollPane;
	private JEditorPane contenido;
	private JLabel lblMaximo;
	private JTextField caracteres;
	private JTextPane errorContenido;
	private JButton Botonconfirmar;
	private JButton cancelar;

	public MedioContacto(Interface inside2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Contacto");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		mailUsuario = new TextField();
		mailUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadEmail();
			}

			@Override
			public void focusGained(FocusEvent e) {
				printError("Mensaje Enviado /n", labelErrores, false);
			}
		});
		mailUsuario.setText(inside.contexto.usuarioActual().getEmail());
		mailUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (mailUsuario.getText().length() == 44) {
					if (!(e.isActionKey() || e.isControlDown()
							|| e.getKeyCode() == KeyEvent.VK_DELETE || e
							.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
						e.consume();
					}
				}
			}
		});

		mailUsuario.setBounds(155, 66, 184, 20);
		add(mailUsuario);

		errorMail = new JTextPane();
		errorMail.setBorder(null);
		errorMail.setEditable(false);
		errorMail.setBackground(new Color(255, 204, 255));
		errorMail.setForeground(Color.RED);
		errorMail.setBounds(345, 66, 448, 20);
		add(errorMail);

		nombreUsuario = new TextField();
		nombreUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadNombre();
			}

			@Override
			public void focusGained(FocusEvent e) {
				printError("Mensaje Enviado /n", labelErrores, false);
			}
		});

		nombreUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isLetter(car) || Character.isWhitespace(car))
						|| nombreUsuario.getText().length() == 90) {
					if (!(e.isActionKey() || e.isControlDown()
							|| e.getKeyCode() == KeyEvent.VK_DELETE || e
							.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
						e.consume();
					}
				}
			}
		});
		nombreUsuario.setText(inside.contexto.usuarioActual().toString());

		nombreUsuario.setBounds(155, 92, 184, 20);
		add(nombreUsuario);

		errorNombre = new JTextPane();
		errorNombre.setBorder(null);
		errorNombre.setEditable(false);
		errorNombre.setBackground(new Color(255, 204, 255));
		errorNombre.setForeground(Color.RED);
		errorNombre.setBounds(345, 97, 448, 20);
		add(errorNombre);

		labelErrores = new JTextPane();
		labelErrores.setBounds(22, 50, 867, 14);
		add(labelErrores);
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.BLACK);

		lblSuEmail = new JLabel("Su E-Mail:");
		lblSuEmail.setBounds(69, 71, 80, 14);
		add(lblSuEmail);

		lblSuMail = new JLabel("Su Nombre:");
		lblSuMail.setBounds(69, 92, 80, 14);
		add(lblSuMail);

		JLabel lblContenido = new JLabel("Contenido:");
		lblContenido.setBounds(69, 117, 80, 14);
		add(lblContenido);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(79, 146, 444, 286);
		add(scrollPane);

		contenido = new JEditorPane();
		contenido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				contador();
			}
		});

		contenido.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				printError("Mensaje Enviado /n", labelErrores, false);
			}
		});
		scrollPane.setViewportView(contenido);

		errorContenido = new JTextPane();
		errorContenido.setBorder(null);
		errorContenido.setEditable(false);
		errorContenido.setBackground(new Color(255, 204, 255));
		errorContenido.setForeground(Color.RED);
		errorContenido.setBounds(79, 469, 476, 20);
		add(errorContenido);

		lblMaximo = new JLabel("Caracteres Restantes:");
		lblMaximo.setBounds(89, 443, 133, 14);
		add(lblMaximo);

		caracteres = new JTextField();
		caracteres.setBackground(Color.WHITE);
		caracteres.setDisabledTextColor(Color.WHITE);
		caracteres.setSelectedTextColor(Color.BLACK);
		caracteres.setSelectionColor(Color.WHITE);
		caracteres.setEditable(false);
		caracteres.setText("140");
		caracteres.setBounds(219, 438, 45, 20);
		add(caracteres);
		caracteres.setColumns(10);

		Botonconfirmar = new JButton("Confirmar");
		Botonconfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Validar()) {
					labelErrores.setForeground(new Color(0, 128, 0));
					printError("Mensaje Enviado /n", labelErrores, true);
					mailUsuario.setText("");
					nombreUsuario.setText("");
					contenido.setDocument(contenido.getEditorKit().createDefaultDocument());
					caracteres.setText("140");
				}
			}
		});

		Botonconfirmar.setIcon(new ImageIcon(MedioRegistroDeUsuario.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		Botonconfirmar.setBounds(79, 500, 144, 31);
		add(Botonconfirmar);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioHome(inside));
			}
		});
		cancelar.setIcon(new ImageIcon(MedioRegistroDeUsuario.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(233, 500, 144, 31);
		add(cancelar);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{mailUsuario, nombreUsuario, contenido, Botonconfirmar, cancelar}));

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

	private Boolean Validar() {
		return (ValidadEmail() & ValidadNombre() & ValidadContenido());

	}

	private Boolean ValidadEmail() {
		return (ValidarLongitudMail() & ValidarFormatoMail());

	}

	private Boolean ValidarLongitudMail() {
		if ((mailUsuario.getText().length() < 45)
				&& (mailUsuario.getText().length() > 0)) {
			printError("El E-Mail debe tener entre 1 y 44 caracteres /n",
					errorMail, false);
			return true;
		} else {
			printError("El E-Mail debe tener entre 1 y 44 caracteres /n",
					errorMail, true);
			return false;
		}
	}

	private Boolean ValidarFormatoMail() {
		if (CookBooks.esUnMail(mailUsuario.getText().toString())) {
			printError("El formato del E-Mail es invalido /n", errorMail, false);
			return true;
		} else {
			printError("El formato del E-Mail es invalido /n", errorMail, true);
			return false;
		}
	}

	private Boolean ValidadNombre() {
		if ((nombreUsuario.getText().length() < 91)
				&& (nombreUsuario.getText().length() > 0)) {
			printError("El nombre debe tener entre 1 y 90 caracteres /n",
					errorNombre, false);
			return true;

		} else {
			printError("El nombre debe tener entre 1 y 90 caracteres /n",
					errorNombre, true);
			return false;
		}
	}

	private Boolean ValidadContenido() {
		if (contenido.getText().length() > 0) {
			printError("El mensaje debe tener contenido /n", errorContenido,
					false);
			return true;

		} else {
			printError("El mensaje debe tener contenido /n", errorContenido,
					true);
			return false;
		}
	}

	public void contador() {
		try {
			caracteres.setText(new Integer(140 - contenido.getText().length())
					.toString());
			try {
				String temp = contenido.getText().substring(0, 140);
				if(new Integer(140 - contenido.getText().length())
				.compareTo(0)< 0){
					Object doc = contenido.getEditorKit().createDefaultDocument();
					((Document) doc).insertString(0, temp, null);
					contenido.setDocument((Document) doc);
					contenido.setCaretPosition(140);
					contador();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}


	}

	protected void Cargar() {

	}

	@Override
	void refresh() {
		inside.centro(new MedioHome(inside));

	}
}

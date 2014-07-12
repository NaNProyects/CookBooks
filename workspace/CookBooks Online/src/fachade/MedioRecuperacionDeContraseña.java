package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.CookBooks;
import funcionalidad.Usuario;

@SuppressWarnings("serial")
public class MedioRecuperacionDeContraseña extends MedioPanel {
	private Usuario user;
	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private TextField campoMail;
	private TextField campoDNI;
	private MedioPanel anterior;
	private JButton Botonconfirmar;
	private JButton cancelar;
	private JLabel lblConfirmarContrasea;
	private JLabel mailLabel;
	private JTextPane errorMail;
	private JTextPane errorDNI;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioRecuperacionDeContraseña(Interface inside2, MedioPanel ant) {
		this(inside2, Usuario.anonimo(), ant);
	}

	public MedioRecuperacionDeContraseña(Interface inside2, Usuario usuario,
			MedioPanel ant) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		user = usuario;
		anterior = ant;

		setBackground(new Color(255, 204, 255));
		setLayout(null);

		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(52, 177, 333, 165);
		add(labelErrores);
		;

		if (user.getId() != -1) {
		}
		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Recuperar Contrase\u00F1a");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 313, 36);
		add(lblTitulo);

		campoMail = new TextField();
		campoMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Botonconfirmar.doClick();
				}
				if (campoMail.getText().length() == 44) {
					if (!(e.isActionKey() || e.isControlDown()
							|| e.getKeyCode() == KeyEvent.VK_DELETE || e
							.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
						e.consume();
					}
				}
			}
		});
		campoMail.setBounds(183, 83, 184, 20);
		add(campoMail);

		campoDNI = new TextField();
		campoDNI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Botonconfirmar.doClick();
				}
				if (!(Character.isDigit(car))
						|| campoDNI.getText().length() == 8) {
					if (!(e.isActionKey() || e.isControlDown()
							|| e.getKeyCode() == KeyEvent.VK_DELETE || e
							.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
						e.consume();
					}
				}
			}
		});
		campoDNI.setBounds(183, 113, 184, 20);
		add(campoDNI);

		mailLabel = new JLabel("Mail*");
		mailLabel.setBounds(52, 91, 88, 14);
		add(mailLabel);

		Botonconfirmar = new JButton("Confirmar");
		Botonconfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Validar()) {
					try {
						inside.centro(new MedioCambioDeContraseña(
								inside,
								inside.contexto.recuperarContraseña(
										campoMail.getText(), campoDNI.getText()),
								anterior));
						printError("Datos Incorrectos /n", labelErrores, false);
						printError("Ocurrio un error /n", labelErrores, false);
					} catch (Exception e2) {
						if (e2.getMessage().contains("no existe")) {
							printError("Datos Incorrectos /n", labelErrores,
									true);
						} else {
							printError("Ocurrio un error /n", labelErrores,
									true);
						}
					}
				}
			}
		});

		Botonconfirmar.setIcon(new ImageIcon(
				MedioRecuperacionDeContraseña.class
						.getResource("/fachade/Image/Clear Green Button.png")));
		Botonconfirmar.setBounds(593, 562, 144, 31);
		add(Botonconfirmar);

		JLabel lblcamposObligatorios = new JLabel("*Campos obligatorios.");
		lblcamposObligatorios.setBounds(22, 152, 298, 14);
		add(lblcamposObligatorios);

		lblConfirmarContrasea = new JLabel("DNI*");
		lblConfirmarContrasea.setBounds(52, 120, 133, 14);
		add(lblConfirmarContrasea);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
			}
		});
		cancelar.setIcon(new ImageIcon(MedioRecuperacionDeContraseña.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(747, 562, 144, 31);
		add(cancelar);

		errorMail = new JTextPane();
		errorMail.setBorder(null);
		errorMail.setEditable(false);
		errorMail.setBackground(new Color(255, 204, 255));
		errorMail.setForeground(Color.RED);
		errorMail.setBounds(373, 83, 480, 20);
		add(errorMail);

		errorDNI = new JTextPane();
		errorDNI.setBorder(null);
		errorDNI.setEditable(false);
		errorDNI.setBackground(new Color(255, 204, 255));
		errorDNI.setForeground(Color.RED);
		errorDNI.setBounds(373, 113, 490, 20);
		add(errorDNI);

		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				campoMail, campoDNI, Botonconfirmar, cancelar }));

	}

	private Boolean ValidarLongitudDNI() {
		if ((campoDNI.getText().toString().length() < 9)
				&& (campoDNI.getText().toString().length() > 6)) {
			printError("El DNI debe tener entre 7 y 8 dígitos /n", errorDNI,
					false);
			return true;
		} else {
			printError("El DNI debe tener entre 7 y 8 dígitos /n", errorDNI,
					true);
			return false;
		}
	}

	private Boolean Validar() {
		return (ValidarLongitudMail() & ValidarFormatoMail() & ValidarLongitudDNI());

	}

	private Boolean ValidarLongitudMail() {
		if ((campoMail.getText().length() < 45)
				&& (campoMail.getText().length() > 0)) {
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
		if (CookBooks.esUnMail(campoMail.getText().toString())) {
			printError("El formato del E-Mail es invalido /n", errorMail, false);
			return true;
		} else {
			printError("El formato del E-Mail es invalido /n", errorMail, true);
			return false;
		}
	}

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}
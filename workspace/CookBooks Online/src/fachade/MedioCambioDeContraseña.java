package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Usuario;

@SuppressWarnings("serial")
public class MedioCambioDeContraseña extends MedioPanel {
	private Usuario user;
	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private MedioPanel anterior;
	private JTextPane errorPass;
	private JTextPane errorPass2;
	private JButton Botonconfirmar;
	private JButton cancelar;
	private JLabel lblConfirmarContrasea;
	private JLabel contraseñaLabel;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioCambioDeContraseña(Interface inside2, MedioPanel ant) {
		this(inside2, Usuario.anonimo(), ant);
	}

	public MedioCambioDeContraseña(Interface inside2, Usuario usuario,
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
				"Nueva Contrase\u00F1a");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 219, 36);
		add(lblTitulo);

		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ValidadPass();
			}
		});
		passwordField.setBounds(183, 83, 184, 20);
		add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ValidadPass();
			}
		});
		passwordField_1.setBounds(183, 113, 184, 20);
		add(passwordField_1);

		contraseñaLabel = new JLabel("Contrase\u00F1a*");
		contraseñaLabel.setBounds(52, 91, 88, 14);
		add(contraseñaLabel);

		Botonconfirmar = new JButton("Confirmar");
		Botonconfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ValidadPass()) {
					try {
						inside.contexto.cambiarPass(user, new String(
								passwordField.getPassword()));
						inside.centro(new MedioHome(inside));
					} catch (Exception e1) {
						e1.printStackTrace();
						printError(e1.getMessage().concat(" /n"), labelErrores,
								true);
					}

				}
			}
		});

		Botonconfirmar.setIcon(new ImageIcon(MedioCambioDeContraseña.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		Botonconfirmar.setBounds(593, 562, 144, 31);
		add(Botonconfirmar);

		JLabel lblcamposObligatorios = new JLabel("*Campos obligatorios.");
		lblcamposObligatorios.setBounds(22, 152, 298, 14);
		add(lblcamposObligatorios);

		lblConfirmarContrasea = new JLabel("Confirmar Contrase\u00F1a*");
		lblConfirmarContrasea.setBounds(52, 120, 133, 14);
		add(lblConfirmarContrasea);

		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
			}
		});
		cancelar.setIcon(new ImageIcon(MedioCambioDeContraseña.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(747, 562, 144, 31);
		add(cancelar);

		errorPass = new JTextPane();
		errorPass.setBorder(null);
		errorPass.setEditable(false);
		errorPass.setBackground(new Color(255, 204, 255));
		errorPass.setForeground(Color.RED);
		errorPass.setBounds(377, 83, 476, 20);
		add(errorPass);

		errorPass2 = new JTextPane();
		errorPass2.setBorder(null);
		errorPass2.setEditable(false);
		errorPass2.setBackground(new Color(255, 204, 255));
		errorPass2.setForeground(Color.RED);
		errorPass2.setBounds(377, 113, 476, 20);
		add(errorPass2);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				passwordField, passwordField_1, Botonconfirmar, cancelar }));

	}

	private Boolean ValidadPass() {
		if (new String(passwordField.getPassword()).compareTo(new String(
				passwordField_1.getPassword())) != 0) {
			printError("Las contraseñas no coinciden /n", errorPass2, true);
			return false;
		} else {
			printError("Las contraseñas no coinciden /n", errorPass2, false);
			if ((new String(passwordField.getPassword()).length() < 21)
					&& (new String(passwordField.getPassword()).length() > 3)) {
				printError(
						"La contraseña debe tener entre 4 y 20 caracteres /n",
						errorPass, false);

				return true;

			} else {
				printError(
						"La contraseña debe tener entre 4 y 20 caracteres /n",
						errorPass, true);
				return false;
			}
		}
	}

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}
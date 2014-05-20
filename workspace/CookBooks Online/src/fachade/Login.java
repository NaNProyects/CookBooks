package fachade;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Login extends JPanel {
	private JTextField txtMailexamplecom;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private JButton btnEntrar;

	/**
	 * Create the panel.
	 */
	public Login(JPanel inside) {
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		txtMailexamplecom = new JTextField();
		txtMailexamplecom.setText("mail@example.com");
		txtMailexamplecom.setBounds(104, 21, 150, 20);
		add(txtMailexamplecom);
		txtMailexamplecom.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(104, 44, 150, 20);
		add(passwordField);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setLabelFor(txtMailexamplecom);
		lblMail.setToolTipText("");
		lblMail.setBounds(71, 24, 46, 14);
		add(lblMail);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setLabelFor(passwordField);
		lblContrasea.setToolTipText("");
		lblContrasea.setBounds(29, 47, 108, 14);
		add(lblContrasea);
		
		lblNewLabel = new JLabel("Registrarse");
		lblNewLabel.setForeground(new Color(0, 51, 255));
		lblNewLabel.setBounds(10, 87, 79, 14);
		add(lblNewLabel);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(147, 110, 89, 23);
		add(btnEntrar);
		
		JLabel lblRecuperarClave = new JLabel("Recuperar Clave");
		lblRecuperarClave.setForeground(new Color(0, 51, 255));
		lblRecuperarClave.setBounds(10, 112, 127, 19);
		add(lblRecuperarClave);
		
		JLabel lblDatosDeSecion = new JLabel("Datos de sesion incorrectos");
		lblDatosDeSecion.setForeground(Color.RED);
		lblDatosDeSecion.setBounds(71, 65, 183, 19);
		add(lblDatosDeSecion);

	}
}

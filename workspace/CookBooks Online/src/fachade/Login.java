package fachade;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class Login extends JPanel {
	private JTextField campoMail;
	private JPasswordField passwordField;
	private JLabel lblRegistro;
	private JButton btnEntrar;
	private JLabel lblMail;
	private JLabel lblContrasea;
	private JLabel lblRecuperarClave;
	private Superior inside;
	private JTextPane labelErrores;

	/**
	 * Create the panel.
	 */
	public Login(Superior inside2) {
		inside = inside2;
		
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		setBounds(0, 0, 264, 144);
		
		
		labelErrores = new JTextPane();
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.RED);
		labelErrores.setBounds(10, 64, 244, 20);
		add(labelErrores);

		campoMail = new JTextField();
		campoMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					entrar();
				   }
			}
		});
		campoMail.setText("mail@example.com");
		campoMail.setBounds(104, 21, 150, 20);
		add(campoMail);
		campoMail.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					entrar();
				   }
			}
		});
		passwordField.setBounds(104, 44, 150, 20);
		add(passwordField);
		
		lblMail = new JLabel("Mail:");
		lblMail.setLabelFor(campoMail);
		lblMail.setToolTipText("");
		lblMail.setBounds(71, 24, 46, 14);
		add(lblMail);
		
		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setLabelFor(passwordField);
		lblContrasea.setToolTipText("");
		lblContrasea.setBounds(29, 47, 108, 14);
		add(lblContrasea);
		
		lblRegistro = new JLabel("Registrarse");
		lblRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inside.getInside().centro(new MedioEdicionDeUsuario(inside.getInside(),inside.getInside().center));
			}
		});
		lblRegistro.setForeground(new Color(0, 51, 255));
		lblRegistro.setBounds(10, 87, 79, 14);
		add(lblRegistro);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entrar();
			}
		});
		btnEntrar.setBounds(147, 110, 89, 23);
		add(btnEntrar);
		
		lblRecuperarClave = new JLabel("Recuperar Clave"); //TODO esconder para demo 2
		lblRecuperarClave.setForeground(new Color(0, 51, 255));
		lblRecuperarClave.setBounds(10, 112, 127, 19);
		add(lblRecuperarClave);
		


	}
	public void entrar(){
		try {
			if(inside.getInside().contexto.autenticar(campoMail.getText(),new String(passwordField.getPassword()))){
				inside.getInside().center.refresh();
				inside.setPanelLog(new Loged(inside));
			}
			else{
				printError("Datos de Sesión incorrectos".concat(" /n"), true);
			}
		} catch (Exception e) {
			printError(e.getMessage().concat(" /n"), true);
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

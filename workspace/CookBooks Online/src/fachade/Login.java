package fachade;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class Login extends JPanel {
	private JTextField campoMail;
	private JPasswordField passwordField;
	private JLabel lblRegistro;
	private JButton btnEntrar;
	private JLabel lblMail;
	private JLabel lblContrasea;
	private JLabel lblRecuperarClave;
	private JLabel lblError;
	private Superior inside;

	/**
	 * Create the panel.
	 */
	public Login(Superior inside2) {
		inside = inside2;
		
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		setBounds(0, 0, 264, 144);
		
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
		lblRegistro.setForeground(new Color(0, 51, 255));
		lblRegistro.setBounds(10, 87, 79, 14);
		add(lblRegistro);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				entrar();
			}
		});
		btnEntrar.setBounds(147, 110, 89, 23);
		add(btnEntrar);
		
		lblRecuperarClave = new JLabel("Recuperar Clave");
		lblRecuperarClave.setForeground(new Color(0, 51, 255));
		lblRecuperarClave.setBounds(10, 112, 127, 19);
		add(lblRecuperarClave);
		
		lblError = new JLabel("Datos de sesion incorrectos");
		lblError.setForeground(Color.RED);
		lblError.setBounds(71, 65, 183, 19);
		lblError.setVisible(false);
		add(lblError);

	}
	public void entrar(){
		try {
			if(inside.getInside().contexto.autenticar(campoMail.getText(),new String(passwordField.getPassword()))){
				inside.setPanelLog(new Loged(inside));
			}
			else{
				lblError.setVisible(true);
				lblError.repaint();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

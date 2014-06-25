package fachade;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.CookBooks;
import funcionalidad.Usuario;

@SuppressWarnings("serial")
public class MedioEdicionDeUsuario extends MedioPanel {
	private String tituloPanel = "Nuevo Usuario";
	private Usuario user;
	private JTextField mailUsuario;
	private JTextField nombreUsuario;
	private JTextField apellidoUsuario;
	private JTextField direcUsuario;
	private TextField targetaUsuario;
	private Interface inside;
	private TextField dniUsuario;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private TextField pinTextField;
	private TextField telefonoUsuario;
	private MedioPanel anterior;

	/**
	 * Create the panel.
	 * 
	 * @wbp.parser.constructor
	 */
	public MedioEdicionDeUsuario(Interface inside2, MedioPanel ant) {
		this(inside2, Usuario.anonimo(),ant);
	}

	public MedioEdicionDeUsuario(Interface inside2, Usuario usuario, MedioPanel ant) {
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
		labelErrores.setBounds(22, 350, 333, 213);
		add(labelErrores);

		dniUsuario = new TextField();
		dniUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});
		;
		dniUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				printError("El DNI no puede comenzar con 0 /n", false);
				try {
					if (dniUsuario.getText().length() != new Integer(dniUsuario
							.getText()).toString().length()) {
						dniUsuario.setText(new Integer(dniUsuario.getText())
								.toString());
						throw new Exception();
					}
				} catch (Exception e1) {
					printError("El DNI no puede comenzar con 0 /n", true);
				}
				ValidadDNI();
			}
		});
		dniUsuario.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		dniUsuario.setBounds(183, 147, 184, 20);
		// if (user.getId().compareTo(new Integer(-1)) == 0) {
		// dniUsuario.setEditable(false);
		// }
		add(dniUsuario);

		mailUsuario = new JTextField();
		mailUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadEmail();
			}
		});
		mailUsuario.setText(user.getEmail());

		if (user.getId() != -1) {
			tituloPanel = "Editar Usuario";
		}
		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				tituloPanel);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		mailUsuario.setBounds(183, 52, 184, 20);
		add(mailUsuario);

		nombreUsuario = new JTextField();
		nombreUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadNombre();
			}
		});
		nombreUsuario.setText(user.getNombre());

		nombreUsuario.setBounds(183, 173, 184, 20);
		add(nombreUsuario);

		apellidoUsuario = new JTextField();
		apellidoUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadApellido();
			}
		});
		apellidoUsuario.setText(user.getApellido());
		apellidoUsuario.setBounds(183, 204, 184, 20);
		add(apellidoUsuario);

		direcUsuario = new JTextField();
		direcUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadDireccion();
			}
		});
		direcUsuario.setText(user.getDireccion());
		direcUsuario.setBounds(183, 235, 184, 20);
		add(direcUsuario);

		targetaUsuario = new TextField();
		targetaUsuario.setText(user.getTarjeta());
		targetaUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});
		targetaUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadTargeta();

			}
		});
		targetaUsuario.setBounds(183, 297, 184, 20);
		add(targetaUsuario);

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

		pinTextField = new TextField();
		pinTextField.setBounds(409, 297, 65, 20);
		pinTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});
		pinTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadPin();

			}
		});
		add(pinTextField);

		telefonoUsuario = new TextField();
		telefonoUsuario.setBounds(183, 266, 184, 20);
		telefonoUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});
		telefonoUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadTelefono();

			}
		});
		add(telefonoUsuario);

		JLabel dniLabel = new JLabel("DNI*");
		dniLabel.setLabelFor(dniUsuario);
		dniLabel.setBounds(52, 153, 46, 14);
		add(dniLabel);

		JLabel mailLabel = new JLabel("E-Mail*");
		mailLabel.setLabelFor(mailUsuario);
		mailLabel.setBounds(52, 58, 46, 14);
		add(mailLabel);

		JLabel contraseñaLabel = new JLabel("Contrase\u00F1a*");
		contraseñaLabel.setBounds(52, 91, 88, 14);
		add(contraseñaLabel);

		JLabel nombreLabel = new JLabel("Nombre*");
		nombreLabel.setLabelFor(nombreUsuario);
		nombreLabel.setBounds(52, 179, 88, 14);
		add(nombreLabel);

		JLabel apellidoLabel = new JLabel("Apellido*");
		apellidoLabel.setLabelFor(apellidoUsuario);
		apellidoLabel.setBounds(52, 210, 88, 14);
		add(apellidoLabel);

		JLabel direccionLabel = new JLabel("Dirección*");
		direccionLabel.setLabelFor(direcUsuario);
		direccionLabel.setBounds(52, 241, 74, 14);
		add(direccionLabel);

		JLabel targetaLabel = new JLabel("Tarjeta*");
		targetaLabel.setLabelFor(targetaUsuario);
		targetaLabel.setBounds(52, 303, 46, 14);
		add(targetaLabel);

		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean error = null;
				if (ValidarUsuario()) {
					user.setDni(new Integer(dniUsuario.getText()));
					user.setEmail(mailUsuario.getText());
					user.setHashPass(CookBooks.getMD5(new String(passwordField
							.getPassword())));
					user.setNombre(nombreUsuario.getText());
					user.setApellido(apellidoUsuario.getText());
					user.setDireccion(direcUsuario.getText());
					user.setTelefono(telefonoUsuario.getText()
							.toString());
					user.setTarjeta(targetaUsuario.getText().toString());
					user.setPin(pinTextField.getText().toString()); 

					try {
						if (user.getId() == -1) {

							error = inside.contexto.agregar(user, new String(
									passwordField.getPassword()));
							    inside.contexto.autenticar(mailUsuario.getText(),new String(passwordField.getPassword()));
								inside.center.refresh();
								inside.top.setPanelLog(new Loged(inside.top));
						} else {
							error = inside.contexto.modificar(user);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						printError(e1.getMessage().concat(" /n"), true);
					}
					if (error) {
						printError(
								"El Mail/DNI pertenece a un Usuario existente /n",
								false);
						inside.centro(anterior);
					} else {
						printError(
								"El Mail/DNI pertenece a un Usuario existente /n",
								true);
					}
				}
			}
		});

		confirmar.setIcon(new ImageIcon(MedioEdicionDeUsuario.class
				.getResource("/fachade/Image/Clear Green Button.png")));
		confirmar.setBounds(593, 562, 144, 31);
		add(confirmar);
		
		JLabel lblcamposObligatorios = new JLabel("*Campos obligatorios.");
		lblcamposObligatorios.setBounds(22, 329, 298, 14);
		add(lblcamposObligatorios);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar Contrase\u00F1a*");
		lblConfirmarContrasea.setBounds(52, 120, 133, 14);
		add(lblConfirmarContrasea);

		JLabel lblPin = new JLabel("Pin*");
		lblPin.setBounds(377, 297, 149, 14);
		add(lblPin);

		JLabel cambiarPass = new JLabel("Cambiar Contrase\u00F1a");
		cambiarPass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO aca va a ir el cambio de contraseña en edicion
			}
		});
		cambiarPass.setForeground(Color.BLUE);
		cambiarPass.setBounds(210, 86, 121, 14);
		add(cambiarPass);

		JLabel lblTelefono = new JLabel("Teléfono*");
		lblTelefono.setBounds(52, 272, 74, 14);
		add(lblTelefono);



		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(anterior);
			}
		});
		cancelar.setIcon(new ImageIcon(MedioEdicionDeUsuario.class
				.getResource("/fachade/Image/Cancel Red Button.png")));
		cancelar.setBounds(747, 562, 144, 31);
		add(cancelar);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				mailUsuario,passwordField,passwordField_1,dniUsuario, nombreUsuario, apellidoUsuario,
				direcUsuario,telefonoUsuario, targetaUsuario, pinTextField, confirmar, cancelar }));

		lblPin.setLabelFor(pinTextField);
		lblTelefono.setLabelFor(telefonoUsuario);

	}

	private boolean ValidarUsuario() {

		return (((ValidadDNI() & ValidadEmail() & ValidadNombre()
				& ValidadApellido() & ValidadDireccion() & ValidadTelefono()
				& ValidadTargeta() & ValidadPin() & ValidadPass())));

	}

	private Boolean ValidadDNI() {//TODO validar con existe
		if ((dniUsuario.getText().toString().length() < 9) && (dniUsuario.getText().toString().length() > 6)) {
			printError("El Dni debe tener entre 7 y 8 digitos /n", false);
			try {
				if(!inside.contexto.existeDNI(dniUsuario.getText().toString())){
					printError("El Dni pertenece a un usuario existente /n", false);
					printError("Ocurrio un error /n", false);
					return true;
				}
				else{
					printError("El Dni pertenece a un usuario existente /n", true);
					printError("Ocurrio un error /n", false);
					return false;
				}
			} catch (Exception e) {
				printError("Ocurrio un error /n", true);
				return false;
			}


		} else {
			printError("El Dni debe tener entre 7 y 8 digitos /n", true);
			return false;
		}
	}

	private Boolean ValidadEmail() {
		if ((mailUsuario.getText().length() < 45)
				&& (mailUsuario.getText().length() > 0)) {
			printError("El E-Mail debe tener entre 0 y 45 caracteres /n", false);
			
			try {
				if(!inside.contexto.existeMail(mailUsuario.getText().toString())){
					printError("El E-Mail pertenece a un usuario existente /n", false);
					printError("Ocurrio un error /n", false);
					return true;
				}
				else{
					printError("El E-Mail pertenece a un usuario existente /n", true);
					printError("Ocurrio un error /n", false);
					return false;
				}
			} catch (Exception e) {
				printError("Ocurrio un error /n", true);
				return false;
			}
		} else {
			printError("El E-Mail debe tener entre 0 y 45 caracteres /n", true);
			return false;
		}

	}

	private Boolean ValidadNombre() {
		if ((nombreUsuario.getText().length() < 45)
				&& (nombreUsuario.getText().length() > 0)) {
			printError("El Nombre debe tener entre 0 y 45 caracteres /n", false);
			return true;

		} else {
			printError("El Nombre debe tener entre 0 y 45 caracteres /n", true);
			return false;
		}
	}

	private Boolean ValidadApellido() {
		if ((apellidoUsuario.getText().length() < 45)
				&& (apellidoUsuario.getText().length() > 0)) {
			printError("La Apellido debe tener entre 0 y 45 caracteres /n",
					false);
			return true;

		} else {
			printError("La Apellido debe tener entre 0 y 45 caracteres /n",
					true);
			return false;
		}

	}

	private Boolean ValidadDireccion() {
		if ((direcUsuario.getText().length() < 50)
				&& (direcUsuario.getText().length() > 0)) {
			printError("La Dirección debe tener entre 0 y 50 caracteres /n",
					false);

			return true;

		} else {
			printError("La Dirección debe tener entre 0 y 50 caracteres /n",
					true);
			return false;
		}

	}

	private Boolean ValidadPin() {
		if ((pinTextField.getText().length() < 5)
				&& (pinTextField.getText().length() > 2)) {
			printError(
					"El pin de la tarjeta debe tener entre 3 y 4 digitos /n",
					false);

			return true;

		} else {
			printError(
					"El pin de la tarjeta debe tener entre 3 y 4 digitos /n",
					true);
			return false;
		}
	}

	private Boolean ValidadTelefono() {
		if ((telefonoUsuario.getText().length() < 14)
				&& (telefonoUsuario.getText().length() > 6)) {
			printError(
					"El Número de Teléfono debe tener entre 7 y 13 digitos /n",
					false);

			return true;

		} else {
			printError(
					"El Número de Teléfono debe tener entre 7 y 13 digitos /n",
					true);
			return false;
		}
	}

	private Boolean ValidadTargeta() {
		if ((targetaUsuario.getText().length() < 19)
				&& (targetaUsuario.getText().length() > 15)) {
			printError(
					"El Número de tarjeta debe tener entre 16 y 18 digitos /n",
					false);

			return true;

		} else {
			printError(
					"El Número de tarjeta debe tener entre 16 y 18 digitos /n",
					true);
			return false;
		}
	}

	private Boolean ValidadPass() {
		if (user.getId() > 0) {
			return true;
		} else {
			if (new String(passwordField.getPassword()).compareTo(new String(
					passwordField_1.getPassword())) != 0) {
				printError("Las contraseñas no coinsiden /n", true);
				return false;
			} else {
				printError("Las contraseñas no coinsiden /n", false);
				if ((new String(passwordField.getPassword()).length() < 21)
						&& (new String(passwordField.getPassword()).length() > 3)) {
					printError(
							"La contraseña debe tener entre 4 y 20 digitos /n",
							false);

					return true;

				} else {
					printError(
							"La contraseña debe tener entre 4 y 20 digitos /n",
							true);
					return false;
				}
			}
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

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}

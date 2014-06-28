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
import javax.swing.JTextPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.CookBooks;
import funcionalidad.Usuario;

@SuppressWarnings("serial")
public class MedioEdicionDeUsuario extends MedioPanel {
	private String tituloPanel = "Nuevo Usuario";
	private Usuario user;
	private TextField mailUsuario;
	private TextField nombreUsuario;
	private TextField apellidoUsuario;
	private TextField direcUsuario;
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
	private JTextPane errorMail;
	private JTextPane errorPass;
	private JTextPane errorPass2;
	private JTextPane errorDNI;
	private JTextPane errorNombre;
	private JTextPane errorApellido;
	private JTextPane errorDir;
	private JTextPane errorTel;
	private JTextPane errorTarjeta;

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
		labelErrores.setBounds(22, 384, 333, 179);
		add(labelErrores);

		dniUsuario = new TextField();
		dniUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isDigit(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)|| dniUsuario.getText().length() == 9) {
					e.consume();
				}
			}
		});
		;
		dniUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				printError("El DNI no puede comenzar con 0 /n",errorDNI, false);
				try {
					if (dniUsuario.getText().length() != new Integer(dniUsuario
							.getText()).toString().length()) {
						dniUsuario.setText(new Integer(dniUsuario.getText())
								.toString());
						throw new Exception();
					}
				} catch (Exception e1) {
					if(dniUsuario.getText().length() !=0)
					printError("El DNI no puede comenzar con 0 /n",errorDNI, true);
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

		mailUsuario = new TextField();
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
		lblTitulo.setBounds(22, 10, 200, 36);
		add(lblTitulo);

		
		mailUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (mailUsuario.getText().length() == 45) {
					e.consume();
				}
			}
		});
		
		mailUsuario.setBounds(183, 52, 184, 20);
		add(mailUsuario);

		nombreUsuario = new TextField();
		nombreUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadNombre();
			}
		});
	
		nombreUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isLetter(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE || Character.isWhitespace(car))|| nombreUsuario.getText().length() == 45) {
					e.consume();
				}
			}
		});
		nombreUsuario.setText(user.getNombre());

		nombreUsuario.setBounds(183, 173, 184, 20);
		add(nombreUsuario);

		apellidoUsuario = new TextField();
		apellidoUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadApellido();
			}
		});
		apellidoUsuario.setText(user.getApellido());
		apellidoUsuario.setBounds(183, 204, 184, 20);
		apellidoUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Character car = e.getKeyChar();
				if (!(Character.isLetter(car) || e.isActionKey()
						|| e.isControlDown()
						|| e.getKeyCode() == KeyEvent.VK_DELETE || e
						.getKeyCode() == KeyEvent.VK_BACK_SPACE || Character.isWhitespace(car))|| apellidoUsuario.getText().length() == 45) {
					e.consume();
				}
			}
		});
		add(apellidoUsuario);

		direcUsuario = new TextField();
		direcUsuario.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				ValidadDireccion();
			}
		});
		direcUsuario.setText(user.getDireccion());
		direcUsuario.setBounds(183, 235, 184, 20);
		direcUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (direcUsuario.getText().length() == 45) {
					e.consume();
				}
			}
		});
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
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)|| targetaUsuario.getText().length() == 45) {
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
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)|| pinTextField.getText().length() == 45) {
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
						.getKeyCode() == KeyEvent.VK_BACK_SPACE)|| telefonoUsuario.getText().length() == 45) {
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
						printError(e1.getMessage().concat(" /n"),labelErrores, true);
					}
					if (error) {
						printError(
								"El Mail/DNI pertenece a un Usuario existente /n",labelErrores,
								false);
						inside.centro(anterior);
					} else {
						printError(
								"El Mail/DNI pertenece a un Usuario existente /n",labelErrores,
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
		
		 errorMail = new JTextPane();
		 errorMail.setBorder(null);
		 errorMail.setEditable(false);
		 errorMail.setBackground(new Color(255, 204, 255));
		 errorMail.setForeground(Color.RED);
		errorMail.setBounds(377, 52, 476, 20);
		add(errorMail);
		
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
		
		 errorDNI = new JTextPane();
		 errorDNI.setBorder(null);
		 errorDNI.setEditable(false);
		 errorDNI.setBackground(new Color(255, 204, 255));
		 errorDNI.setForeground(Color.RED);
		errorDNI.setBounds(377, 147, 476, 20);
		add(errorDNI);
		
		 errorNombre = new JTextPane();
		 errorNombre.setBorder(null);
		 errorNombre.setEditable(false);
		 errorNombre.setBackground(new Color(255, 204, 255));
		 errorNombre.setForeground(Color.RED);
		errorNombre.setBounds(377, 173, 476, 20);
		add(errorNombre);
		
		 errorApellido = new JTextPane();
		 errorApellido.setBorder(null);
		 errorApellido.setEditable(false);
		 errorApellido.setBackground(new Color(255, 204, 255));
		 errorApellido.setForeground(Color.RED);
		errorApellido.setBounds(377, 204, 476, 20);
		add(errorApellido);
		
		 errorDir = new JTextPane();
		 errorDir.setBorder(null);
		 errorDir.setEditable(false);
		 errorDir.setBackground(new Color(255, 204, 255));
		 errorDir.setForeground(Color.RED);
		errorDir.setBounds(377, 235, 476, 20);
		add(errorDir);
		
		 errorTel = new JTextPane();
		 errorTel.setBorder(null);
		 errorTel.setEditable(false);
		 errorTel.setBackground(new Color(255, 204, 255));
		 errorTel.setForeground(Color.RED);
		errorTel.setBounds(377, 266, 476, 20);
		add(errorTel);
		
		 errorTarjeta = new JTextPane();
		 errorTarjeta.setBorder(null);
		 errorTarjeta.setEditable(false);
		 errorTarjeta.setBackground(new Color(255, 204, 255));
		 errorTarjeta.setForeground(Color.RED);
		errorTarjeta.setBounds(183, 323, 476, 50);
		add(errorTarjeta);

	}

	private boolean ValidarUsuario() {

		return (((ValidadDNI() & ValidadEmail() & ValidadNombre()
				& ValidadApellido() & ValidadDireccion() & ValidadTelefono()
				& ValidadTargeta() & ValidadPin() & ValidadPass())));

	}

	private Boolean ValidadDNI() {
		return (ValidarLongitudDNI() && ValidarExistenciaDNI());
	}
	private Boolean ValidarLongitudDNI(){
		if ((dniUsuario.getText().toString().length() < 9) && (dniUsuario.getText().toString().length() > 6)) {
			printError("El DNI debe tener entre 7 y 8 dígitos /n",errorDNI, false);
			return true;
		} else {
			printError("El DNI debe tener entre 7 y 8 dígitos /n",errorDNI, true);
			return false;
		}
	}
	private Boolean ValidarExistenciaDNI(){
		try {
			if(!inside.contexto.existeDNI(dniUsuario.getText().toString())){
				printError("El DNI pertenece a un usuario existente /n",errorDNI, false);
				printError("Ocurrió un error /n",labelErrores, false);
				return true;
			}
			else{
				printError("El DNI pertenece a un usuario existente /n",errorDNI, true);
				printError("Ocurrió un error /n",labelErrores, false);
				return false;
			}
		} catch (Exception e) {
			printError("Ocurrió un error /n",labelErrores, true);
			return false;
		}
	}
	private Boolean ValidadEmail() {
			return (ValidarLongitudMail() & ValidarFormatoMail() & ValidarExistenciaMail());

	}
	private Boolean ValidarLongitudMail(){
		if ((mailUsuario.getText().length() < 45)
				&& (mailUsuario.getText().length() > 0)) {
			printError("El E-Mail debe tener entre 1 y 44 caracteres /n",errorMail, false);
			return true;
		} else {
			printError("El E-Mail debe tener entre 1 y 44 caracteres /n",errorMail, true);
			return false;
		}
	}
	private Boolean ValidarExistenciaMail(){
		try {
			if(!inside.contexto.existeMail(mailUsuario.getText().toString())){
				printError("El E-Mail pertenece a un usuario existente /n",errorMail, false);
				printError("Ocurrió un error /n",labelErrores, false);
				return true;
			}
			else{
				printError("El E-Mail pertenece a un usuario existente /n",errorMail, true);
				printError("Ocurrió un error /n",labelErrores, false);
				return false;
			}
		} catch (Exception e) {
			printError("Ocurrió un error /n",labelErrores, true);
			return false;
		}
	}	
	
	private Boolean ValidarFormatoMail(){
		if(CookBooks.esUnMail(mailUsuario.getText().toString())){
			printError("El formato del E-Mail es invalido /n",errorMail, false);
			return true;
		}
		else{
			printError("El formato del E-Mail es invalido /n",errorMail, true);
			return false;
		}
	}

	private Boolean ValidadNombre() {
		if ((nombreUsuario.getText().length() < 45)
				&& (nombreUsuario.getText().length() > 0)) {
			printError("El nombre debe tener entre 1 y 44 caracteres /n",errorNombre, false);
			return true;

		} else {
			printError("El nombre debe tener entre 1 y 44 caracteres /n",errorNombre, true);
			return false;
		}
	}

	private Boolean ValidadApellido() {
		if ((apellidoUsuario.getText().length() < 45)
				&& (apellidoUsuario.getText().length() > 0)) {
			printError("El apellido debe tener entre 1 y 44 caracteres /n",errorApellido,
					false);
			return true;

		} else {
			printError("El apellido debe tener entre 1 y 44 caracteres /n",errorApellido,
					true);
			return false;
		}

	}

	private Boolean ValidadDireccion() {
		if ((direcUsuario.getText().length() < 50)
				&& (direcUsuario.getText().length() > 0)) {
			printError("La dirección debe tener entre 1 y 50 caracteres /n",errorDir,
					false);

			return true;

		} else {
			printError("La dirección debe tener entre 1 y 50 caracteres /n",errorDir,
					true);
			return false;
		}

	}

	private Boolean ValidadPin() {
		if ((pinTextField.getText().length() < 5)
				&& (pinTextField.getText().length() > 2)) {
			printError(
					"El pin de la tarjeta debe tener entre 3 y 4 dígitos /n",errorTarjeta,
					false);

			return true;

		} else {
			printError(
					"El pin de la tarjeta debe tener entre 3 y 4 dígitos /n",errorTarjeta,
					true);
			return false;
		}
	}

	private Boolean ValidadTelefono() {
		if ((telefonoUsuario.getText().length() < 14)
				&& (telefonoUsuario.getText().length() > 6)) {
			printError(
					"El número de Teléfono debe tener entre 7 y 13 dígitos /n",errorTel,
					false);

			return true;

		} else {
			printError(
					"El número de Teléfono debe tener entre 7 y 13 dígitos /n",errorTel,
					true);
			return false;
		}
	}

	private Boolean ValidadTargeta() {
		if ((targetaUsuario.getText().length() < 19)
				&& (targetaUsuario.getText().length() > 15)) {
			printError(
					"El número de tarjeta debe tener entre 16 y 18 dígitos /n",errorTarjeta,
					false);

			return true;

		} else {
			printError(
					"El número de tarjeta debe tener entre 16 y 18 dígitos /n",errorTarjeta,
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
				printError("Las contraseñas no coinciden /n",errorPass2, true);
				return false;
			} else {
				printError("Las contraseñas no coinciden /n",errorPass2, false);
				if ((new String(passwordField.getPassword()).length() < 21)
						&& (new String(passwordField.getPassword()).length() > 3)) {
					printError(
							"La contraseña debe tener entre 4 y 20 caracteres /n",errorPass,
							false);

					return true;

				} else {
					printError(
							"La contraseña debe tener entre 4 y 20 caracteres /n",errorPass,
							true);
					return false;
				}
			}
		}
	}

	

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}
}

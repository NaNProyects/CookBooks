package fachade;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Loged extends JPanel {

	private Superior inside;
	private JLabel labelNombre;
	private JButton btnCierre;
	private JButton btnEdicion;

	/**
	 * Create the panel.
	 */
	public Loged(Superior inside2) {
		inside = inside2;

		setBackground(new Color(255, 204, 255));
		setLayout(null);
		setBounds(0, 0, 264, 144);
		inside.getInside().left.permisos(inside.getInside().contexto
				.usuarioActual().getId());

		labelNombre = new JLabel(inside.getInside().contexto
				.usuarioActual()
				.getNombre()
				.concat(" ")
				.concat(inside.getInside().contexto.usuarioActual()
						.getApellido()));
		
		labelNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelNombre.setIcon(new ImageIcon(Loged.class
				.getResource("/fachade/Image/User.png")));
		labelNombre.setBounds(10, 31, 244, 32);
		add(labelNombre);

		btnCierre = new JButton("Cerrar Sesion");
		btnCierre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inside.setPanelLog(new Login(inside));
				inside.getInside().centro(new MedioHome(inside.getInside()));
				inside.getInside().contexto.cerrarSesion();
				inside.getInside().left.permisos(inside.getInside().contexto
						.usuarioActual().getId());
			}
		});
		btnCierre.setBounds(123, 110, 131, 23);
		add(btnCierre);

		btnEdicion = new JButton("Mis\n Datos".replaceAll("/n",
				System.getProperty("line.separator")));
		btnEdicion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnEdicion.setIcon(new ImageIcon(Loged.class
				.getResource("/fachade/Image/Write Document.png")));
		btnEdicion.setBounds(123, 74, 131, 32);
		add(btnEdicion);

	}

}
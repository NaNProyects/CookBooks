package fachade;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Superior extends JPanel {
	private JTextField txtBusque;

	/**
	 * Create the panel.
	 */
	public Superior(Interface inside) {
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		JPanel panel = new Login(this);
		panel.setBounds(752, 0, 264, 144);
		add(panel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Superior.class.getResource("/fachade/Image/Logo.png")));
		label.setBounds(0, -40, 350, 218);
		add(label);
		
		txtBusque = new JTextField();
		txtBusque.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtBusque.setText("hola");
				   }

			}
		});
		txtBusque.setText("Busque");
		txtBusque.setBounds(292, 113, 377, 20);
		add(txtBusque);
		txtBusque.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
					txtBusque.setText("hola2");
			}
		});
		btnNewButton.setIcon(new ImageIcon(Superior.class.getResource("/fachade/Image/lupa-icono-3813-16.png")));
		btnNewButton.setBounds(677, 113, 36, 23);
		add(btnNewButton);


	}
}

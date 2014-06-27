package fachade;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class Superior extends JPanel {
	private JTextField txtBusque;
	private Interface inside;
	private JPanel panelLog;
	private JButton botonBuscar;

	/**
	 * Create the panel.
	 */
	public Superior(Interface inside2) {
		setInside(inside2);
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		panelLog = new JPanel();		
		panelLog.setBackground(new Color(255, 204, 255));
		panelLog.setBounds(752, 0, 264, 144);
		add(panelLog);
		panelLog.setLayout(null);
		
		JPanel panel = new Login(this);
		
		panelLog.add(panel);

		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Superior.class.getResource("/fachade/Image/Logo.png")));
		label.setBounds(0, -40, 269, 218);
		add(label);
		
		txtBusque = new JTextField();
		txtBusque.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtBusque.getText().contentEquals("Busque")){
				txtBusque.setText("");
				}
			}
		});
		txtBusque.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					inside.centro(new MedioCatalogoDeLibro(inside, inside.contexto.buscarLibro(txtBusque.getText()),txtBusque.getText()));
				   }

			}
		});
		txtBusque.setText("Busque");
		txtBusque.setBounds(292, 113, 377, 20);
		add(txtBusque);
		txtBusque.setColumns(10);
		
		botonBuscar = new JButton("");
		botonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioCatalogoDeLibro(inside, inside.contexto.buscarLibro(txtBusque.getText()),txtBusque.getText()));
			}
		});
		botonBuscar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtBusque.getText().contentEquals("Busque")){
				txtBusque.setText("");
				}
			}
		});
		botonBuscar.setIcon(new ImageIcon(Superior.class.getResource("/fachade/Image/lupa-icono-3813-16.png")));
		botonBuscar.setBounds(677, 113, 36, 23);
		add(botonBuscar);


	}

	public void setPanelLog(JPanel panelLog) {
		this.panelLog.removeAll();
		this.panelLog.add(panelLog);
		this.panelLog.repaint();
		repaint();
	}

	public Interface getInside() {
		return inside;
	}

	public void setInside(Interface inside) {
		this.inside = inside;
	}
}
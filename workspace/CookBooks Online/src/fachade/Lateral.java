package fachade;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.GridBagLayout;

import javax.swing.SpringLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lateral extends JPanel {
	
	private Interface inside;
	/**
	 * Create the panel.
	 */
	public Lateral(Interface inside2) {
		inside = inside2;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		JButton btnNewButton = new JButton("Libros");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioListaDeLibros(inside));
			}
		});
		btnNewButton.setBounds(0, 11, 96, 23);
		add(btnNewButton);
		
		JButton button = new JButton("New button");
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inside.centro(new MedioHome(inside));
			}
		});
		button.setBounds(0, 45, 96, 23);
		add(button);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(0, 79, 96, 23);
		add(button_1);
		
		JButton button_2 = new JButton("New button");
		button_2.setBounds(0, 113, 96, 23);
		add(button_2);
		
		JButton button_3 = new JButton("New button");
		button_3.setBounds(0, 147, 96, 23);
		add(button_3);
		
		JButton button_4 = new JButton("New button");
		button_4.setBounds(0, 181, 96, 23);
		add(button_4);
		
		JButton button_5 = new JButton("New button");
		button_5.setBounds(0, 215, 96, 23);
		add(button_5);
		
		JButton button_6 = new JButton("New button");
		button_6.setBounds(0, 249, 96, 23);
		add(button_6);
		
		JButton button_7 = new JButton("New button");
		button_7.setBounds(0, 283, 96, 23);
		add(button_7);

	}

}

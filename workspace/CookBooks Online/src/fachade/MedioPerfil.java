package fachade;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.Font;
import java.awt.Color;

public class MedioPerfil extends JPanel {

	/**
	 * Create the panel.
	 */
	public MedioPerfil(Interface inside) {
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Perde");
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.PLAIN, 62));
		lblNewJgoodiesTitle.setBounds(213, 120, 512, 372);
		add(lblNewJgoodiesTitle);

	}

}

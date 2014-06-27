package fachade;

import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class MedioPerfil extends MedioPanel {

	private Interface inside;

	/**
	 * Create the panel.
	 */
	public MedioPerfil(Interface inside2) {
		inside = inside2;
		setBackground(new Color(255, 204, 255));
		setLayout(null);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Perde");
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.PLAIN, 62));
		lblNewJgoodiesTitle.setBounds(213, 120, 512, 372);
		add(lblNewJgoodiesTitle);

	}

	protected void refresh() {
		inside.centro(new MedioHome(inside));
	}

}

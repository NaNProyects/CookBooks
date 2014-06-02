package fachade;

import javax.swing.JPanel;

import java.awt.Color;

@SuppressWarnings("serial")
public class MedioRegistro extends JPanel {

	private Interface inside;

	/**
	 * Create the panel.
	 */
	public MedioRegistro(Interface inside2) {
		inside = inside2;
		
		setBackground(new Color(255, 204, 255));
		setLayout(null);

	}

}

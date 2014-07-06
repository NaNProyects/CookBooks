package fachade;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public abstract class MedioPanel extends JPanel {

	public MedioPanel() {
		super();
	}

	public MedioPanel(LayoutManager layout) {
		super(layout);
	}

	public MedioPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public MedioPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	protected void printError(String texto, JTextPane label , Boolean condicion ) {
		label.setText(label.getText().replaceAll(
				texto.replaceAll("/n", System.getProperty("line.separator")),
				""));
		if (condicion) {
			label.setText(label.getText().concat(texto)
					.replaceAll("/n", System.getProperty("line.separator")));
			label.setVisible(true);
		}
	}
	
	abstract void refresh();
	
	protected void Cargar(){}

}
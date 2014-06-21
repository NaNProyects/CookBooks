package fachade;

import java.awt.LayoutManager;

import javax.swing.JPanel;

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

	abstract void refresh();
	
	protected void Cargar(){}

}
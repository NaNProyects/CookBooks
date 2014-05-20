package fachade;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class CopyOfInterface {

	private JFrame frmCookbooksOnline;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CopyOfInterface window = new CopyOfInterface();
					window.frmCookbooksOnline.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CopyOfInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCookbooksOnline = new JFrame();
		frmCookbooksOnline.setTitle("CookBooks Online");
		frmCookbooksOnline.setResizable(false);
		frmCookbooksOnline.setBounds(100, 100, 1024, 768);
		frmCookbooksOnline.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

package fachade;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.Dimension;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import funcionalidad.CookBooks;

import java.awt.Component;
import java.awt.Toolkit;

public class Interface {
	public CookBooks contexto = new CookBooks();		
	private JPanel center;
	private JPanel top;
	private JPanel left;
	private JFrame frmCookbooksOnline;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
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
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCookbooksOnline = new JFrame();
		frmCookbooksOnline.setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/fachade/Image/LogoChico.png")));
		frmCookbooksOnline.setTitle("CookBooks Online");
		frmCookbooksOnline.setResizable(false);
		frmCookbooksOnline.setBounds(100, 100, 1024, 768);
		frmCookbooksOnline.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCookbooksOnline.getContentPane().setLayout(new BorderLayout(0, 0));
		
		center = new MedioHome(this);
		center.setPreferredSize(new Dimension(904, 601));
		frmCookbooksOnline.getContentPane().add(center, BorderLayout.CENTER);
		
		left = new Lateral(this);
		left.setPreferredSize(new Dimension(96, 10));
		frmCookbooksOnline.getContentPane().add(left, BorderLayout.WEST);
		
		top = new Superior(this);
		top.setPreferredSize(new Dimension(0, 145));
		frmCookbooksOnline.getContentPane().add(top, BorderLayout.NORTH);	
		

		frmCookbooksOnline.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{left, top, center}));
	}
	public void centro(JPanel Centro){
		frmCookbooksOnline.getContentPane().remove(center);
		center = Centro;
		center.setPreferredSize(new Dimension(904, 601));
		frmCookbooksOnline.getContentPane().add(center, BorderLayout.CENTER);
		center.revalidate();
		center.repaint();
	}
}

package fachade;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import funcionalidad.CookBooks;

public class Interface {
	public CookBooks contexto;		
	protected MedioPanel center;
	protected Superior top;
	protected Lateral left;
	protected JFrame frmCookbooksOnline;

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
		
		
		
		top = new Superior(this);
		top.setPreferredSize(new Dimension(0, 145));
		frmCookbooksOnline.getContentPane().add(top, BorderLayout.NORTH);	
		

		frmCookbooksOnline.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{left, top, center}));
		
		try {
			contexto = new CookBooks();
		} catch (Exception e) {
			// TODO poner advertencia si pasa lo q sea q pase
			e.printStackTrace();
		}
		
		left = new Lateral(this);
		left.setPreferredSize(new Dimension(96, 10));
		left.permisos(contexto.usuarioActual().getId());
		
		
		
		frmCookbooksOnline.getContentPane().add(left, BorderLayout.WEST);
	}
	public void centro(MedioPanel Centro){
		frmCookbooksOnline.getContentPane().remove(center);
		center = Centro;
		center.setPreferredSize(new Dimension(904, 601));
		frmCookbooksOnline.getContentPane().add(center, BorderLayout.CENTER);
		center.revalidate();
		center.repaint();
		center.Cargar();
	}
}


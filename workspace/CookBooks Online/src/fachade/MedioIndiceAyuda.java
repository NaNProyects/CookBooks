package fachade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

@SuppressWarnings("serial")
public class MedioIndiceAyuda extends MedioPanel {

	private Interface inside;
	private JLabel lblTitulo;
	private JTextPane labelErrores;
	private JScrollPane scrollPane;
	private LinkedList<String> manuales = new LinkedList<String>();
	private LinkedList<LinkedList<String>> imagenes = new LinkedList<LinkedList<String>>();
	private JList<String> list;
	private JButton ver;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MedioIndiceAyuda(Interface inside2) {
		setFocusTraversalPolicyProvider(true);
		inside = inside2;
		
		//TODO Mock
		manuales.add("1. Vistazo general de la aplicación");
		manuales.add("2. Cómo busco un libro");
		manuales.add("3. Cómo me registro");
		manuales.add("4. Cómo incio mi sesión");
		manuales.add("5. Cómo cierro mi sesión");
		manuales.add("6. Cómo cambio mis datos");
		manuales.add("7. Manejo avanzado de cuenta");
		manuales.add("8. Estadísticas");
		manuales.add("9. Cómo agrego un libro a mi carrito");
		manuales.add("10. Cómo uso el carrito");
		manuales.add("11. Cómo veo mis compras");
		manuales.add("12. Cómo agrego un libro (o autor) al sistema");
		manuales.add("13. Cómo administro libros (y autores)");
		manuales.add("14. Cómo gestiono pedidos");
		LinkedList<String> lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Vistazo_general_de_la_aplicacion.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_busco_un_libro.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_me_registro.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_incio_mi_sesion.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_cierro_mi_sesion.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_cambio_mis_datos.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Manejo_avanzado_de_cuenta.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Estadisticas.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_agrego_un_libro_a_mi_carrito.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_uso_el_carrito.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_veo_mis_compras.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_agrego_un_libro_o_autor_al_sistema.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_administro_libros_y_autores.png");
		imagenes.add((LinkedList<String>) lista.clone());
		lista = new LinkedList<String>();
		lista.add("/fachade/Image/Manual/Como_gestiono_pedidos.png");
		imagenes.add((LinkedList<String>) lista.clone());
		//--------------
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		lblTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Índice de Ayuda");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitulo.setBounds(22, 10, 200, 50);
		add(lblTitulo);

		 
		labelErrores = new JTextPane();
		labelErrores.setBounds(22, 50, 867, 14);
		add(labelErrores);
		labelErrores.setBorder(null);
		labelErrores.setEditable(false);
		labelErrores.setBackground(new Color(255, 204, 255));
		labelErrores.setForeground(Color.BLACK);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 71, 524, 506);
		add(scrollPane);
		

		list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(new AbstractListModel() {
			String[] values = manuales.toArray(new String[0]) ;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ver = new JButton("Ver Sección");
		ver.setIcon(new ImageIcon(MedioPerfilDeLibro.class.getResource("/fachade/Image/Export To Document.png")));
		ver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inside.centro(new MedioAyuda(inside, inside.center, imagenes.get(list.getSelectedIndex()),list.getSelectedValue()));			
			}
		});
		ver.setBounds(718, 562, 171, 31);
		add(ver);
		
	}
	@SuppressWarnings("unused")
	private void printError(String texto, Boolean condicion) {
		labelErrores.setText(labelErrores.getText().replaceAll(
				texto.replaceAll("/n", System.getProperty("line.separator")),
				""));
		if (condicion) {
			labelErrores.setText(labelErrores.getText().concat(texto)
					.replaceAll("/n", System.getProperty("line.separator")));
			labelErrores.setVisible(true);
		}
	}
	
	protected void Cargar() { 
		
	}
	
	
	@Override
	void refresh() {
		inside.centro(new MedioHome(inside));
		
	}
}

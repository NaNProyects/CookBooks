package fachade;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import funcionalidad.Autor;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class MedioAutores extends JPanel {

	private JLabel labelTitulo;
	private JList<String> ListaAutores;
	private JButton botonAgregar;
	private JButton botonModificar;
	private JButton botonQuitar;
	protected JPanel panelEdicion;
	protected Interface inside;
	protected LinkedList<Autor> arryAutores;
	protected Autor selectedAutor = new Autor(000, "");
	private JLabel labelEliminacionError;
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public MedioAutores(Interface inside2) {
		inside = inside2;
		arryAutores = inside.contexto.autores();
		setBackground(new Color(255, 204, 255));
		setLayout(null);

		ListaAutores = new JList<String>();
		ListaAutores.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				labelEliminacionError.setVisible(false);
			}
		});
		Cargar();
		ListaAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListaAutores.setBounds(68, 120, 159, 280);
		add(ListaAutores);
		labelTitulo = DefaultComponentFactory.getInstance().createTitle(
				"Autores");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelTitulo.setBounds(22, 10, 200, 50);
		add(labelTitulo);

		scrollPane = new JScrollPane(ListaAutores);
		scrollPane.setBounds(61, 85, 287, 459);
		add(scrollPane);

		botonAgregar = new JButton("Agregar");
		botonAgregar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panelEdicion.removeAll();
				panelEdicion.add(new AutorEdit((MedioAutores) inside.center));
				panelEdicion.repaint();
			}
		});
		botonAgregar.setHorizontalAlignment(SwingConstants.LEFT);
		botonAgregar.setIcon(new ImageIcon(MedioAutores.class
				.getResource("/fachade/Image/Add Green Button.png")));
		botonAgregar.setBounds(364, 83, 125, 30);
		add(botonAgregar);

		botonModificar = new JButton("Modificar");
		botonModificar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectedAutor = (Autor) arryAutores.get(ListaAutores
						.getSelectedIndex());
				panelEdicion.removeAll();
				panelEdicion.add(new AutorEdit((MedioAutores) inside.center,
						selectedAutor));
				panelEdicion.repaint();
			}
		});
		botonModificar.setHorizontalAlignment(SwingConstants.LEFT);
		botonModificar.setIcon(new ImageIcon(MedioAutores.class
				.getResource("/fachade/Image/Gear Alt.png")));
		botonModificar.setBounds(364, 116, 125, 28);
		add(botonModificar);

		botonQuitar = new JButton("Quitar");
		botonQuitar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectedAutor = (Autor) arryAutores.get(ListaAutores
						.getSelectedIndex());
				if (inside.contexto.eliminar(selectedAutor)) {
					arryAutores.remove(selectedAutor);
					Cargar();
					ListaAutores.repaint();
				} else {
					labelEliminacionError.setVisible(true);
				}
			}
		});
		botonQuitar.setHorizontalAlignment(SwingConstants.LEFT);
		botonQuitar.setIcon(new ImageIcon(MedioAutores.class
				.getResource("/fachade/Image/Minus Red Button.png")));
		botonQuitar.setBounds(364, 150, 125, 28);
		add(botonQuitar);
		
		labelEliminacionError = new JLabel("No se puede eliminar un autor que posea libros");
		labelEliminacionError.setForeground(Color.RED);
		labelEliminacionError.setBounds(61, 555, 315, 14);
		labelEliminacionError.setVisible(false);
		add(labelEliminacionError);
		
		panelEdicion = new JPanel();
		panelEdicion.setBounds(508, 85, 368, 192);
		panelEdicion.setBackground(new Color(255, 204, 255));
		panelEdicion.setLayout(null);
		add(panelEdicion);


	}

	private Object[] NombresAutores(LinkedList<Autor> autores) {
		ArrayList<String> nombres = new ArrayList<String>();
		for (Autor element : autores) {
			nombres.add(element.nombre());
		}
		return nombres.toArray();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void Cargar() {
		ListaAutores.setModel(new AbstractListModel() {
			Object[] values = NombresAutores(arryAutores);

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		ListaAutores.repaint();
	}
}

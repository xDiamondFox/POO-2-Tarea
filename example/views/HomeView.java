package views;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controllers.HomeController;
import core.Model;
import core.View;

// Vista principal: configura la ventana y crea las 5 pestañas
@SuppressWarnings("serial")
public class HomeView extends JPanel implements View
{
	private HomeController controlador;
	private JFrame         ventana;

	private static final int ANCHO = 680;
	private static final int ALTO  = 450;
	private static final int POS_X = 100;
	private static final int POS_Y = 100;


	public HomeView(HomeController controlador, JFrame ventana)
	{
		this.controlador = controlador;
		this.ventana     = ventana;
		configurarVentana();
		crearPestanias();
	}

	@Override
	public void update(Model modelo, Object dato) {}

	private void configurarVentana()
	{
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(POS_X, POS_Y, ANCHO, ALTO);
		ventana.setMinimumSize(new Dimension(ANCHO, ALTO));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
	}

	private void crearPestanias()
	{
		JTabbedPane pestanias = new JTabbedPane(JTabbedPane.TOP);
		pestanias.addTab("New event",          controlador.getNewEventView());
		pestanias.addTab("Events",             controlador.getEventListView());
		pestanias.addTab("Remove Event",       controlador.getRemoveEventView());
		pestanias.addTab("Registrar invitado", controlador.getGuestView());

		// Al entrar a "Remove Event" (índice 2) recarga la tabla con los eventos actuales
		pestanias.addChangeListener(e -> {
			if (pestanias.getSelectedIndex() == 2)
				controlador.refrescarVistaEliminar();
		});

		add(pestanias, BorderLayout.CENTER);
	}
}

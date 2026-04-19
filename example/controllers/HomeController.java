package controllers;

import core.Controller;
import views.EventListView;
import views.HomeView;
import views.NewEventView;
import views.RemoveEventView;

// Controlador principal: inicializa todos los demás controladores y muestra la ventana
public class HomeController extends Controller
{
	private HomeView vistaInicio;

	private EventListController   controladorLista       = new EventListController();
	private NewEventController    controladorNuevoEvento = new NewEventController(controladorLista);
	private RemoveEventController controladorEliminar    = new RemoveEventController(controladorLista);


	@Override
	public void run()
	{
		controladorLista.run();
		controladorNuevoEvento.run();
		controladorEliminar.run();
		vistaInicio = new HomeView(this, mainFrame);
		addView("HomeView", vistaInicio);
		mainFrame.setVisible(true);
	}

	// Recarga la tabla de "Remove Event" al entrar a esa pestaña
	public void refrescarVistaEliminar()
	{
		controladorEliminar.refrescar();
	}

	public NewEventView    getNewEventView()    { return controladorNuevoEvento.getView(); }
	public EventListView   getEventListView()   { return controladorLista.getView(); }
	public RemoveEventView getRemoveEventView() { return controladorEliminar.getView(); }
}

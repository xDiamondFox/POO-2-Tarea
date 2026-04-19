package controllers;

import core.Controller;
import views.SearchEventView;


/**
 * Controlador de la pestaña "Buscar evento".
 */
public class SearchEventController extends Controller
{
	private SearchEventView searchEventView;

	@Override
	public void run()
	{
		searchEventView = new SearchEventView();
	}

	public SearchEventView getView()
	{
		return searchEventView;
	}
}

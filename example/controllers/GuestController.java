package controllers;

import javax.swing.JOptionPane;

import core.Controller;
import models.Guest;
import models.GuestIO;
import views.GuestView;

// Controlador de "Registrar invitado": recibe los datos del formulario y los guarda
public class GuestController extends Controller
{
	private GuestView vistaInvitado;

	@Override
	public void run()
	{
		vistaInvitado = new GuestView(this);
	}

	public void guardarInvitado(Guest invitado)
	{
		try {
			GuestIO modelo = new GuestIO();
			modelo.attach(vistaInvitado);
			modelo.guardarInvitado(invitado);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar el invitado.");
		}
	}

	public GuestView getView() { return vistaInvitado; }
}

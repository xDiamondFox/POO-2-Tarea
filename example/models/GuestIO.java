package models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import core.Model;
import core.View;

// Guarda invitados en guests.txt e implementa Observer para notificar a la vista
public class GuestIO implements Model
{
	private static final String DIRECTORIO = ".";
	private static final String ARCHIVO    = "guests.txt";
	private List<View> vistas = new ArrayList<>();
	private String mensaje;

	@Override
	public void attach(View vista) { vistas.add(vista); }

	@Override
	public void detach(View vista) { vistas.remove(vista); }

	@Override
	public void notifyViews()
	{
		for (View v : vistas) v.update(this, mensaje);
	}

	// "true" en FileWriter = agrega al final del archivo sin borrar lo anterior
	public void guardarInvitado(Guest invitado) throws Exception
	{
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(new File(DIRECTORIO, ARCHIVO), true));
			escritor.write(invitado.toString());
			escritor.newLine();
			escritor.close();
			mensaje = "Invitado registrado correctamente";
			notifyViews();
		} catch (FileNotFoundException e) {
			mensaje = "No se encontró el archivo";
			notifyViews();
		} catch (Exception e) {
			mensaje = "Error al guardar el invitado";
			notifyViews();
		}
	}
}

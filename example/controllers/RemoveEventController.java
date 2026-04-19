package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import core.Controller;
import models.SchedulerIO;
import views.RemoveEventView;

// Controlador de "Remove Event": maneja la selección y eliminación de eventos
public class RemoveEventController extends Controller
{
	private RemoveEventView       vistaEliminar;
	private EventListController   controladorLista; // para recargar "Events" tras eliminar


	public RemoveEventController(EventListController controladorLista)
	{
		this.controladorLista = controladorLista;
	}

	@Override
	public void run()
	{
		vistaEliminar = new RemoveEventView(this, cargarEventos());
	}

	// Marca todos los checkboxes (columna 5) como seleccionados
	public void seleccionarTodos(DefaultTableModel modeloTabla)
	{
		for (int i = 0; i < modeloTabla.getRowCount(); i++)
			modeloTabla.setValueAt(Boolean.TRUE, i, 5);
	}

	// Desmarca todos los checkboxes
	public void cancelarSeleccion(DefaultTableModel modeloTabla)
	{
		for (int i = 0; i < modeloTabla.getRowCount(); i++)
			modeloTabla.setValueAt(Boolean.FALSE, i, 5);
	}

	public void eliminarSeleccionados(DefaultTableModel modeloTabla)
	{
		List<Integer> filasAEliminar = new ArrayList<>();
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 5)))
				filasAEliminar.add(i);
		}

		if (filasAEliminar.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay eventos seleccionados.");
			return;
		}

		try {
			SchedulerIO modelo = new SchedulerIO();
			List<String> todasLasLineas   = modelo.getEventLines();
			List<String> lineasQueQuedan  = new ArrayList<>();

			for (int i = 0; i < todasLasLineas.size(); i++) {
				if (!filasAEliminar.contains(i))
					lineasQueQuedan.add(todasLasLineas.get(i));
			}
			modelo.rewriteEvents(lineasQueQuedan);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar los eventos.");
			return;
		}

		// Recorro al revés para no descuadrar los índices al borrar filas
		for (int i = filasAEliminar.size() - 1; i >= 0; i--)
			modeloTabla.removeRow(filasAEliminar.get(i));

		controladorLista.reload();
		JOptionPane.showMessageDialog(null, "Evento(s) eliminado(s) correctamente.");
	}

	// Se llama cada vez que el usuario entra a esta pestaña
	public void refrescar()
	{
		vistaEliminar.recargarTabla(cargarEventos());
	}

	public Vector<Vector<Object>> cargarEventos()
	{
		try {
			return new SchedulerIO().getEvents();
		} catch (Exception e) {
			return new Vector<>();
		}
	}

	public RemoveEventView getView() { return vistaEliminar; }
}

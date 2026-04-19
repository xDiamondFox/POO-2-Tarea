package controllers;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.Controller;
import models.SchedulerIO;
import views.EventListView;


/**
 * Responsible for {@link EventListView} behavior.
 */
public class EventListController extends Controller 
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private EventListView eventListView;
	private JTable table;
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run() 
	{
		table = new JTable(getDataColumns(), getNameColumns());
		eventListView = new EventListView(this, table);
	}
	
	/**
	 * Adds a new row in a {@link JTable} with the values informed.
	 *
	 * @param values Values to be add in {@link JTable}
	 */
	public void addNewRow(Object[] values)
	{
		((DefaultTableModel) table.getModel()).addRow(values);
	}

	/**
	 * Limpia la tabla de eventos y la recarga desde el archivo events.txt.
	 * Se llama después de eliminar eventos en RemoveEventController para que
	 * la pestaña "Events" quede sincronizada con el archivo actualizado.
	 */
	public void reload()
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Borra todas las filas actuales de la tabla
		try {
			SchedulerIO schedulerIO = new SchedulerIO();
			Vector<Vector<Object>> data = schedulerIO.getEvents(); // Lee el archivo actualizado
			if (data != null) {
				for (Vector<Object> row : data) {
					model.addRow(row); // Agrega cada evento como fila en la tabla
				}
			}
		} catch (Exception ex) {} // Si el archivo no existe aún, no pasa nada
	}
	
	
	//-----------------------------------------------------------------------
	//		Getters
	//-----------------------------------------------------------------------
	/**
	 * Gets the {@link EventListView view associated with this controller}.
	 * 
	 * @return View associated with this controller
	 */
	public EventListView getView()
	{
		return eventListView;
	}
	
	/**
	 * Returns the names of the columns of the events list.
	 * 
	 * @return Table metadata in a list
	 */
	public Vector<String> getNameColumns() 
	{
		Vector<String> nameColumns = new Vector<String>();
		
		nameColumns.add("Date");
		nameColumns.add("Description");
		nameColumns.add("Frequency");
		nameColumns.add("E-mail");
		nameColumns.add("Alarm");
		
		return nameColumns;
	}
	
	/**
	 * Returns events list data.
	 * 
	 * @return Table data in a list of lists (matrix)
	 */
	public Vector<Vector<Object>> getDataColumns() 
	{
		Vector<Vector<Object>> dataColumns = null;

		try {
			SchedulerIO schedulerIO = new SchedulerIO();
			schedulerIO.attach(eventListView);
			dataColumns = schedulerIO.getEvents();
		} catch (Exception ex) { }

		return dataColumns;
	}
}

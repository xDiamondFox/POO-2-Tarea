package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import core.Model;
import core.View;


/**
 * Responsible for reading / writing events saved.
 */
public class SchedulerIO implements Model
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private static final String DIRECTORY = ".";
	private static final String FILE = "events.txt";
	private List<View> views = new ArrayList<>();
	private String notice;

	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void attach(View view) 
	{
		views.add(view);
	}

	@Override
	public void detach(View view) 
	{
		views.remove(view);
	}

	@Override
	public void notifyViews() 
	{
		for (View v : views) {
			v.update(this, notice);
		}
	}
	
	/**
	 * Saves a {@link SchedulerEvent} in disk in {@link #DIRECTORY}.{@link #FILE}.
	 * 
	 * @param event {@link SchedulerEvent Event} to be saved
	 * @throws Exception If it can't save the event
	 */
	public void saveEvent(SchedulerEvent event) throws Exception 
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DIRECTORY, FILE), true));
			writer.write(event.toString(), 0, event.toString().length());
			writer.newLine();
			writer.close();
		} catch (FileNotFoundException fnfe) {
			notice = "File not found"; 
			notifyViews();
		} catch (Exception ex) {
			notice = "Error while writing the file";
			notifyViews();
		}
	}

	/**
	 * Lee el archivo events.txt y devuelve cada línea como texto plano (sin parsear).
	 * Esto se usa en RemoveEventController para saber exactamente qué líneas borrar.
	 * Las líneas vacías se ignoran para evitar problemas al reescribir el archivo.
	 */
	public List<String> getEventLines() throws Exception
	{
		List<String> lines = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(DIRECTORY, FILE)));
			String line = reader.readLine(); // Lee la primera línea
			while (line != null) {
				if (!line.trim().isEmpty()) lines.add(line); // Ignora líneas vacías
				line = reader.readLine(); // Avanza a la siguiente línea
			}
			reader.close();
		} catch (FileNotFoundException fnfe) {
			notice = "File not found";
			notifyViews();
		} catch (Exception ex) {
			notice = "Error reading the file";
			notifyViews();
		}
		return lines;
	}

	/**
	 * Reescribe el archivo events.txt con solo las líneas indicadas.
	 * A diferencia de saveEvent() que usa append=true (agrega al final),
	 * aquí se usa append=false para REEMPLAZAR todo el contenido del archivo.
	 * Esto permite eliminar eventos específicos del archivo.
	 */
	public void rewriteEvents(List<String> lines) throws Exception
	{
		try {
			// false = sobreescribe el archivo desde cero (no agrega al final)
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DIRECTORY, FILE), false));
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
		} catch (Exception ex) {
			notice = "Error while writing the file";
			notifyViews();
		}
	}

	/**
	 * Reads a {@link SchedulerEvent} saved in disk with name {@link #FILE}.
	 * @return List of lists (matrix) of the events
	 * @throws Exception If it can't read event file
	 */
	public Vector<Vector<Object>> getEvents() throws Exception 
	{
		Vector<Vector<Object>> response = new Vector<Vector<Object>>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(DIRECTORY, FILE)));
			String line = reader.readLine();
			
			while (line != null) {
				Vector<Object> eventInfo = new Vector<Object>();
				String[] tokens = line.split(";");

				eventInfo.add(tokens[0]);
				eventInfo.add(tokens[1]);
				eventInfo.add(Frequency.valueOf(tokens[2]));
				eventInfo.add(tokens[3]);
				eventInfo.add(tokens[4].equals("1") ? "ON" : "OFF");

				response.add(eventInfo);
				line = reader.readLine();
			}

			reader.close();
		} catch (FileNotFoundException fnfe) {
			notice = "File not found";
			notifyViews();
		} catch (Exception ex) {
			notice = "There was a problem reading the event file";
			notifyViews();
		}
		
		return response;
	}
}
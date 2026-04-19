package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controllers.RemoveEventController;
import core.Model;
import core.View;

// Vista de "Remove Event": tabla con checkboxes para seleccionar eventos a eliminar
@SuppressWarnings("serial")
public class RemoveEventView extends JPanel implements View
{
	private RemoveEventController controlador;
	private DefaultTableModel     modeloTabla;


	public RemoveEventView(RemoveEventController controlador, Vector<Vector<Object>> datos)
	{
		this.controlador = controlador;
		crearTabla(datos);
		crearBotones();
	}

	@Override
	public void update(Model modelo, Object dato) {}

	// Vacía y recarga la tabla con datos frescos del archivo
	public void recargarTabla(Vector<Vector<Object>> datos)
	{
		modeloTabla.setRowCount(0);
		if (datos != null) {
			for (Vector<Object> fila : datos) {
				Vector<Object> filaConCheck = new Vector<>(fila);
				filaConCheck.add(Boolean.FALSE);
				modeloTabla.addRow(filaConCheck);
			}
		}
	}

	private void crearTabla(Vector<Vector<Object>> datos)
	{
		setLayout(new BorderLayout());

		Vector<String> columnas = new Vector<>();
		columnas.add("Date");
		columnas.add("Description");
		columnas.add("Frequency");
		columnas.add("E-mail");
		columnas.add("Alarm");
		columnas.add("Boolean");

		// Modelo personalizado: columna 5 es Boolean (se muestra como checkbox)
		// y es la única editable por el usuario
		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public Class<?> getColumnClass(int col) {
				return col == 5 ? Boolean.class : String.class;
			}
			@Override
			public boolean isCellEditable(int fila, int col) {
				return col == 5;
			}
		};

		if (datos != null) {
			for (Vector<Object> fila : datos) {
				Vector<Object> filaConCheck = new Vector<>(fila);
				filaConCheck.add(Boolean.FALSE);
				modeloTabla.addRow(filaConCheck);
			}
		}

		JTable tabla = new JTable(modeloTabla);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(60);
		add(new JScrollPane(tabla), BorderLayout.CENTER);
	}

	private void crearBotones()
	{
		JPanel panelSur = new JPanel(new BorderLayout());

		JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnSeleccionarTodos = new JButton("Seleccionar Todos");
		btnSeleccionarTodos.addActionListener(e -> controlador.seleccionarTodos(modeloTabla));
		panelDerecho.add(btnSeleccionarTodos);
		panelSur.add(panelDerecho, BorderLayout.EAST);

		JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btnCancelar = new JButton("Cancel");
		btnCancelar.addActionListener(e -> controlador.cancelarSeleccion(modeloTabla));
		panelCentro.add(btnCancelar);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(e -> controlador.eliminarSeleccionados(modeloTabla));
		panelCentro.add(btnRemover);

		panelSur.add(panelCentro, BorderLayout.CENTER);
		add(panelSur, BorderLayout.SOUTH);
	}
}

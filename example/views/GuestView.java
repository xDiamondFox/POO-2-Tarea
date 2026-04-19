package views;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controllers.GuestController;
import core.Model;
import core.View;
import models.Guest;

// Vista del formulario "Registrar invitado"
@SuppressWarnings("serial")
public class GuestView extends JPanel implements View
{
	private GuestController controlador;
	private JTextField   campo_nombre;
	private JTextField   campo_celular;
	private JTextField   campo_direccion;
	private JRadioButton radio_masculino;
	private JRadioButton radio_femenino;
	private JComboBox<Integer> combo_dia;
	private JComboBox<String>  combo_mes;
	private JComboBox<Integer> combo_anio;
	private JCheckBox    check_terminos;


	public GuestView(GuestController controlador)
	{
		this.controlador = controlador;
		setLayout(null);
		crearCampoNombre();
		crearCampoCelular();
		crearCampoGenero();
		crearCampoFechaNacimiento();
		crearCampoDireccion();
		crearCheckTerminos();
		crearBotonRegistrar();
		crearBotonLimpiar();
	}

	@Override
	public void update(Model modelo, Object dato)
	{
		if (dato != null) JOptionPane.showMessageDialog(this, (String) dato);
	}

	private void limpiarCampos()
	{
		campo_nombre.setText("");
		campo_celular.setText("");
		campo_direccion.setText("");
		radio_masculino.setSelected(true);
		combo_dia.setSelectedIndex(0);
		combo_mes.setSelectedIndex(0);
		combo_anio.setSelectedIndex(0);
		check_terminos.setSelected(false);
	}

	private void crearCampoNombre()
	{
		JLabel etiqueta = new JLabel("Ingrese Nombre:");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 11));
		etiqueta.setBounds(29, 30, 140, 14);
		add(etiqueta);

		campo_nombre = new JTextField();
		campo_nombre.setBounds(180, 27, 196, 20);
		add(campo_nombre);
	}

	private void crearCampoCelular()
	{
		JLabel etiqueta = new JLabel("Ingrese número celular:");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 11));
		etiqueta.setBounds(29, 70, 160, 14);
		add(etiqueta);

		campo_celular = new JTextField();
		campo_celular.setBounds(200, 67, 176, 20);
		add(campo_celular);
	}

	// ButtonGroup hace que solo un radio button pueda estar marcado a la vez
	private void crearCampoGenero()
	{
		JLabel etiqueta = new JLabel("Género:");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 11));
		etiqueta.setBounds(29, 110, 60, 14);
		add(etiqueta);

		ButtonGroup grupo = new ButtonGroup();

		radio_masculino = new JRadioButton("Masculino");
		radio_masculino.setSelected(true);
		radio_masculino.setBounds(180, 106, 90, 23);
		grupo.add(radio_masculino);
		add(radio_masculino);

		radio_femenino = new JRadioButton("Femenino");
		radio_femenino.setBounds(280, 106, 90, 23);
		grupo.add(radio_femenino);
		add(radio_femenino);
	}

	private void crearCampoFechaNacimiento()
	{
		JLabel etiqueta = new JLabel("Fecha de Nacimiento:");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 11));
		etiqueta.setBounds(29, 150, 160, 14);
		add(etiqueta);

		Integer[] dias = new Integer[31];
		for (int i = 0; i < 31; i++) dias[i] = i + 1;
		combo_dia = new JComboBox<>(dias);
		combo_dia.setBounds(200, 147, 50, 22);
		add(combo_dia);

		String[] meses = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		combo_mes = new JComboBox<>(meses);
		combo_mes.setBounds(258, 147, 60, 22);
		add(combo_mes);

		Integer[] anios = new Integer[76];
		for (int i = 0; i < 76; i++) anios[i] = 1950 + i;
		combo_anio = new JComboBox<>(anios);
		combo_anio.setSelectedItem(1995);
		combo_anio.setBounds(326, 147, 70, 22);
		add(combo_anio);
	}

	private void crearCampoDireccion()
	{
		JLabel etiqueta = new JLabel("Dirección:");
		etiqueta.setFont(new Font("Tahoma", Font.BOLD, 11));
		etiqueta.setBounds(29, 190, 80, 14);
		add(etiqueta);

		campo_direccion = new JTextField();
		campo_direccion.setBounds(200, 187, 176, 20);
		add(campo_direccion);
	}

	private void crearCheckTerminos()
	{
		check_terminos = new JCheckBox("Acepta Términos y Condiciones");
		check_terminos.setBounds(29, 230, 240, 23);
		add(check_terminos);
	}

	private void crearBotonRegistrar()
	{
		JButton boton = new JButton("Registrar");
		boton.setBounds(290, 225, 100, 23);
		add(boton);

		boton.addActionListener(e -> {
			if (!check_terminos.isSelected()) {
				JOptionPane.showMessageDialog(this, "Debe aceptar los Términos y Condiciones.");
				return;
			}
			Guest invitado = new Guest();
			invitado.setNombre(campo_nombre.getText());
			invitado.setCelular(campo_celular.getText());
			invitado.setGenero(radio_masculino.isSelected() ? "Masculino" : "Femenino");
			invitado.setFechaNacimiento(combo_dia.getSelectedItem() + "-" + combo_mes.getSelectedItem() + "-" + combo_anio.getSelectedItem());
			invitado.setDireccion(campo_direccion.getText());
			invitado.setAceptoTerminos(true);
			controlador.guardarInvitado(invitado);
			limpiarCampos();
		});
	}

	private void crearBotonLimpiar()
	{
		JButton boton = new JButton("Limpiar");
		boton.setBounds(400, 225, 90, 23);
		add(boton);
		boton.addActionListener(e -> limpiarCampos());
	}
}

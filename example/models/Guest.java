package models;

// Datos personales de un invitado registrado en la aplicación
public class Guest
{
	private String nombre;
	private String celular;
	private String genero;
	private String fechaNacimiento;
	private String direccion;
	private boolean aceptoTerminos;

	// Convierte el objeto a texto separado por ";" para guardarlo en el archivo
	@Override
	public String toString()
	{
		return nombre + ";" + celular + ";" + genero + ";" + fechaNacimiento + ";" + direccion + ";" + (aceptoTerminos ? "1" : "0");
	}

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getCelular() { return celular; }
	public void setCelular(String celular) { this.celular = celular; }

	public String getGenero() { return genero; }
	public void setGenero(String genero) { this.genero = genero; }

	public String getFechaNacimiento() { return fechaNacimiento; }
	public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

	public String getDireccion() { return direccion; }
	public void setDireccion(String direccion) { this.direccion = direccion; }

	public boolean isAceptoTerminos() { return aceptoTerminos; }
	public void setAceptoTerminos(boolean aceptoTerminos) { this.aceptoTerminos = aceptoTerminos; }
}

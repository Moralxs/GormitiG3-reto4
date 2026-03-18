/**     //ANDONI y XABI
 * 
 */
package Clases;

/**
 * Clase Admin: Hereda de la clase Persona
 */
public class Admin extends Persona {
	private boolean permisos;

	/**
	 * @param nombre
	 * @param apellido
	 * @param edad
	 * @param genero
	 * @param mail
	 * @param telefono
	 * @param permisos
	 */
	public Admin(String nombre, String apellido, int edad, Genero genero, String mail, String telefono,
			boolean permisos) {
		super(nombre, apellido, edad, genero, mail, telefono);
		this.permisos = permisos;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public boolean isPermisos() {
		return permisos;
	}

	public void setPermisos(boolean permisos) {
		this.permisos = permisos;
	}


	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return super.getNombre();
	}

	@Override
	public void setNombre(String nombre) {
		// TODO Auto-generated method stub
		super.setNombre(nombre);
	}

	@Override
	public String getApellido() {
		// TODO Auto-generated method stub
		return super.getApellido();
	}

	@Override
	public void setApellido(String apellido) {
		// TODO Auto-generated method stub
		super.setApellido(apellido);
	}

	@Override
	public int getEdad() {
		// TODO Auto-generated method stub
		return super.getEdad();
	}

	@Override
	public void setEdad(int edad) {
		// TODO Auto-generated method stub
		super.setEdad(edad);
	}

	@Override
	public Genero getGenero() {
		// TODO Auto-generated method stub
		return super.getGenero();
	}

	@Override
	public void setGenero(Genero genero) {
		// TODO Auto-generated method stub
		super.setGenero(genero);
	}

	@Override
	public String getMail() {
		// TODO Auto-generated method stub
		return super.getMail();
	}

	@Override
	public void setMail(String mail) {
		// TODO Auto-generated method stub
		super.setMail(mail);
	}

	@Override
	public String getTelefono() {
		// TODO Auto-generated method stub
		return super.getTelefono();
	}

	@Override
	public void setTelefono(String telefono) {
		// TODO Auto-generated method stub
		super.setTelefono(telefono);
	}

}

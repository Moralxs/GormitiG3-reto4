package Clases;

/**
 * Clase Persona: la clase padre para las clases Usuario y Admin. Sirve de
 * plantilla para la implementacion de atributos y metodos en las otras clases.
 * 
 * @author Gormiti
 * 
 * @hidden LEYENDA: Getter -> Un getter es el metodo con el cual podemos conocer
 *         el valor del atributo de la clase que referencie el metodo.
 * @hidden LEYENDA: Setter-> Un setter es el metodo con el cual podemos dale un
 *         valor a un atributo de la calse, sobreescribiendola en caso de tener
 *         un valor anteriormente.
 * 
 */

public class Persona {

	private String nombre;
	private String apellido;
	private int edad;
	private Genero genero;
	private String mail;
	private String telefono;

	/**
	 * Constructor por parametros de la clase Persona
	 *
	 * @param id
	 * @param nombre
	 * @param apellido
	 * @param edad
	 * @param genero
	 * @param mail
	 * @param telefono
	 */
	public Persona( String nombre, String apellido, int edad, Genero genero, String mail, String telefono) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.genero = genero;
		this.mail = mail;
		this.telefono = telefono;
	}

	
	/**
	 * Constructor vacio de la clase Persona
	 */
	public Persona() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Getter NOMBRE
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter NOMBRE
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Getter APELLIDO
	 * 
	 * @return apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Setter APPELIDO
	 * 
	 * @param apellido
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * Getter EDAD
	 * 
	 * @return edad
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * Setter EDAD
	 * 
	 * @param edad
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * Getter GENERO
	 * 
	 * @return genero
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * Setter GENERO
	 * 
	 * @param genero
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	/**
	 * Getter MAIL
	 * 
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setter MAIL
	 * 
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Getter TELEFONO
	 * 
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Setter TELEFONO
	 * 
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}

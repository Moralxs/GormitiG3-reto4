package Clases;

/**
 * Clase base que representa a una persona física en el sistema de gestión
 * del intercambiador.
 *
 * <p>Actúa como superclase para {@link Usuario}, proporcionando los atributos
 * e identificadores comunes a cualquier persona registrada: nombre, apellido,
 * edad, género, correo electrónico y teléfono.
 *
 * <p>Las subclases deben extender esta clase para añadir los atributos y
 * comportamientos específicos de cada tipo de usuario del sistema.
 *
 * <p><b>Leyenda de convenciones:</b>
 * <ul>
 *   <li><b>Getter</b>: método que devuelve el valor actual de un atributo
 *       de la clase.</li>
 *   <li><b>Setter</b>: método que asigna o sobreescribe el valor de un
 *       atributo de la clase.</li>
 * </ul>
 *
 * @author  Gormiti
 * @version 1.0
 * @see     Usuario
 * @see     Genero
 */
public class Persona {

    /** Nombre de pila de la persona. */
    private String nombre;

    /** Primer apellido de la persona. */
    private String apellido;

    /** Edad de la persona en años. */
    private int edad;

    /**
     * Género de la persona, representado mediante el enum {@link Genero}.
     * Permite valores tipados y evita cadenas de texto arbitrarias.
     */
    private Genero genero;

    /** Dirección de correo electrónico de contacto. */
    private String mail;

    /** Número de teléfono de contacto. */
    private String telefono;

    // ══════════════════════════════════════════════
    // CONSTRUCTORES
    // ══════════════════════════════════════════════

    /**
     * Constructor con parámetros que inicializa todos los atributos
     * de la persona.
     *
     * @param nombre    nombre de pila de la persona
     * @param apellido  primer apellido de la persona
     * @param edad      edad en años
     * @param genero    género según el enum {@link Genero}
     * @param mail      dirección de correo electrónico
     * @param telefono  número de teléfono de contacto
     */
    public Persona(String nombre, String apellido, int edad, Genero genero, String mail, String telefono) {
        super();
        this.nombre   = nombre;
        this.apellido = apellido;
        this.edad     = edad;
        this.genero   = genero;
        this.mail     = mail;
        this.telefono = telefono;
    }

    /**
     * Constructor vacío que crea una instancia de {@code Persona} sin valores
     * iniciales. Los atributos deben asignarse posteriormente mediante
     * los setters correspondientes.
     */
    public Persona() {
    }

    // ══════════════════════════════════════════════
    // GETTERS Y SETTERS
    // ══════════════════════════════════════════════

    /**
     * Getter NOMBRE — devuelve el nombre de pila de la persona.
     *
     * @return nombre de pila
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter NOMBRE — establece o sobreescribe el nombre de pila de la persona.
     *
     * @param nombre nuevo nombre de pila
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter APELLIDO — devuelve el primer apellido de la persona.
     *
     * @return primer apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Setter APELLIDO — establece o sobreescribe el primer apellido de la persona.
     *
     * @param apellido nuevo apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Getter EDAD — devuelve la edad de la persona en años.
     *
     * @return edad en años
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Setter EDAD — establece o sobreescribe la edad de la persona.
     *
     * @param edad nueva edad en años
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Getter GENERO — devuelve el género de la persona.
     *
     * @return género según el enum {@link Genero}
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Setter GENERO — establece o sobreescribe el género de la persona.
     *
     * @param genero nuevo género según el enum {@link Genero}
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Getter MAIL — devuelve la dirección de correo electrónico de la persona.
     *
     * @return dirección de correo electrónico
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter MAIL — establece o sobreescribe la dirección de correo
     * electrónico de la persona.
     *
     * @param mail nueva dirección de correo electrónico
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter TELEFONO — devuelve el número de teléfono de contacto de la persona.
     *
     * @return número de teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Setter TELEFONO — establece o sobreescribe el número de teléfono
     * de contacto de la persona.
     *
     * @param telefono nuevo número de teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
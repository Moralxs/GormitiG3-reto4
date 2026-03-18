package Clases;      //MIKEL Y ANDONI 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Representa un usuario registrado en el sistema de gestión del intercambiador.
 *
 * <p>Extiende {@link Persona} añadiendo los atributos propios de una cuenta
 * de usuario: número de movimientos disponibles, experiencia acumulada,
 * credenciales de acceso (nickname y contraseña) y rol en el sistema.
 *
 * <p>Proporciona además los métodos de persistencia necesarios para
 * insertar un usuario completo en la base de datos en dos pasos:
 * <ol>
 *   <li>{@link #insertarP(Usuario)} — inserta los datos de persona y
 *       devuelve el {@code id_persona} autogenerado.</li>
 *   <li>{@link #insertarU(int, String, String, String)} — inserta los datos
 *       de usuario vinculados al {@code id_persona} obtenido.</li>
 * </ol>
 *
 * @author  Gormiti
 * @version 1.0
 * @see     Persona
 * @see     ConexionMySQL
 */
public class Usuario extends Persona {

    /**
     * Número de movimientos disponibles para el usuario.
     * Se decrementa con cada llamada a {@link #Movimiento()}.
     */
    private int numMovimientos;

    /**
     * Puntos de experiencia acumulados por el usuario
     * a lo largo de su actividad en el sistema.
     */
    private int experiencia;

    /**
     * Contraseña del usuario almacenada en formato codificado
     * (Base64 mediante {@link PasswordBase64Encoder}).
     */
    private String contrasena;

    /**
     * Nombre de usuario único que identifica la cuenta en el sistema.
     * Se usa como credencial de acceso junto con {@link #contrasena}.
     */
    private String nickname;

    /**
     * Rol del usuario en el sistema (p. ej. {@code "admin"}, {@code "user"}).
     * Controla los permisos de acceso a las distintas funcionalidades.
     */
    private String rol;

    // ══════════════════════════════════════════════
    // CONSTRUCTORES
    // ══════════════════════════════════════════════

    /**
     * Constructor con parámetros que inicializa todos los atributos del usuario,
     * incluyendo los heredados de {@link Persona}.
     *
     * @param nombre         nombre de pila de la persona
     * @param apellido       apellido de la persona
     * @param edad           edad en años
     * @param genero         género, según el enum {@link Genero}
     * @param mail           dirección de correo electrónico
     * @param telefono       número de teléfono de contacto
     * @param numMovimientos movimientos iniciales disponibles
     * @param experiencia    puntos de experiencia iniciales
     * @param contrasena     contraseña codificada del usuario
     * @param nickname       nombre de usuario único
     * @param rol            rol del usuario en el sistema
     */
    public Usuario(String nombre, String apellido, int edad, Genero genero, String mail, String telefono,
            int numMovimientos, int experiencia, String contrasena, String nickname, String rol) {
        super(nombre, apellido, edad, genero, mail, telefono);
        this.numMovimientos = numMovimientos;
        this.experiencia    = experiencia;
        this.contrasena     = contrasena;
        this.nickname       = nickname;
        this.rol            = rol;
    }

    /**
     * Constructor vacío requerido para crear instancias de {@code Usuario}
     * sin datos iniciales, rellenando sus atributos posteriormente
     * mediante los setters correspondientes.
     *
     * <p>Utilizado en {@link Visual.VentanaLog} para construir el objeto
     * antes de rellenarlo con los datos del formulario.
     */
    public Usuario() {
    }

    // ══════════════════════════════════════════════
    // MÉTODOS DE NEGOCIO
    // ══════════════════════════════════════════════

    /**
     * Decrementa en uno el número de movimientos disponibles del usuario.
     *
     * <p>Cuando {@link #numMovimientos} llega a {@code 0} se imprime un
     * aviso por consola. En una versión futura podría lanzar una excepción
     * o notificar a la interfaz gráfica.
     */
    public void Movimiento() {
        numMovimientos -= 1;
        if (numMovimientos == 0) {
            System.out.println("Te has quedado sin movimientos");
        }
    }

    // ══════════════════════════════════════════════
    // PERSISTENCIA EN BD
    // ══════════════════════════════════════════════

    /**
     * Inserta los datos personales del usuario en la tabla {@code persona}
     * y devuelve el identificador autogenerado por la base de datos.
     *
     * <p>Utiliza {@link PreparedStatement#RETURN_GENERATED_KEYS} para
     * recuperar el {@code id_persona} asignado por el motor MySQL.
     * Este ID es necesario para la llamada posterior a
     * {@link #insertarU(int, String, String, String)}.
     *
     * <p>La conexión y el {@link PreparedStatement} se cierran siempre
     * en el bloque {@code finally} mediante
     * {@link ConexionMySQL#cerrarConexion(Connection, PreparedStatement, ResultSet)}.
     *
     * @param  u objeto {@link Usuario} del que se extraen los datos personales
     *           (nombre, apellido, edad, género, mail, teléfono)
     * @return el {@code id_persona} autogenerado si la inserción fue exitosa,
     *         o {@code -1} si ocurrió algún error
     */
    public int insertarP(Usuario u) {
        String sql = "INSERT INTO persona (nombre, apellido, edad, genero, mail, telefono) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionMySQL.getConexion();
            ps   = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setInt   (3, u.getEdad());
            ps.setString(4, u.getGenero().name());
            ps.setString(5, u.getMail());
            ps.setString(6, u.getTelefono());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // ID autogenerado por MySQL
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar persona: " + e.getMessage());
        } finally {
            ConexionMySQL.cerrarConexion(conn, ps, null);
        }
        return -1; // indica fallo en la inserción
    }

    /**
     * Inserta los datos de acceso del usuario en la tabla {@code usuario},
     * vinculándolos a una persona existente mediante {@code id_persona}.
     *
     * <p>Los campos {@code numMovimientos} y {@code experiencia} se
     * inicializan a {@code 5} por defecto en el momento del alta.
     *
     * <p>La conexión y el {@link PreparedStatement} se cierran siempre
     * en el bloque {@code finally} mediante
     * {@link ConexionMySQL#cerrarConexion(Connection, PreparedStatement, ResultSet)}.
     *
     * @param  idPersona  clave foránea que relaciona el usuario con su persona
     *                    (obtenida previamente con {@link #insertarP(Usuario)})
     * @param  contrasena contraseña ya codificada (Base64) del usuario
     * @param  rol        rol asignado al usuario (p. ej. {@code "admin"})
     * @param  nickname   nombre de usuario único en el sistema
     * @return {@code true} si la inserción afectó al menos una fila,
     *         {@code false} en caso de error
     */
    public boolean insertarU(int idPersona, String contrasena, String rol, String nickname) {
        String sql = "INSERT INTO usuario (id_persona, numMovimientos, experiencia, contrasena, rol, nickname) "
                   + "VALUES (?, 5, 5, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionMySQL.getConexion();
            ps   = conn.prepareStatement(sql);

            ps.setInt   (1, idPersona);
            ps.setString(2, contrasena);
            ps.setString(3, rol);
            ps.setString(4, nickname);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        } finally {
            ConexionMySQL.cerrarConexion(conn, ps, null);
        }
    }

    // ══════════════════════════════════════════════
    // GETTERS Y SETTERS — PERSONA (delegados a superclase)
    // ══════════════════════════════════════════════

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#getNombre()}.
     */
    @Override
    public String getNombre() {
        return super.getNombre();
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#setNombre(String)}.
     *
     * @param nombre nuevo nombre de pila
     */
    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#getApellido()}.
     */
    @Override
    public String getApellido() {
        return super.getApellido();
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#setApellido(String)}.
     *
     * @param apellido nuevo apellido
     */
    @Override
    public void setApellido(String apellido) {
        super.setApellido(apellido);
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#getEdad()}.
     */
    @Override
    public int getEdad() {
        return super.getEdad();
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#setEdad(int)}.
     *
     * @param edad nueva edad en años
     */
    @Override
    public void setEdad(int edad) {
        super.setEdad(edad);
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#getGenero()}.
     */
    @Override
    public Genero getGenero() {
        return super.getGenero();
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#setGenero(Genero)}.
     *
     * @param genero nuevo género según el enum {@link Genero}
     */
    @Override
    public void setGenero(Genero genero) {
        super.setGenero(genero);
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#getMail()}.
     */
    @Override
    public String getMail() {
        return super.getMail();
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#setMail(String)}.
     *
     * @param mail nueva dirección de correo electrónico
     */
    @Override
    public void setMail(String mail) {
        super.setMail(mail);
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#getTelefono()}.
     */
    @Override
    public String getTelefono() {
        return super.getTelefono();
    }

    /**
     * {@inheritDoc}
     * Delegado a {@link Persona#setTelefono(String)}.
     *
     * @param telefono nuevo número de teléfono de contacto
     */
    @Override
    public void setTelefono(String telefono) {
        super.setTelefono(telefono);
    }

    // ══════════════════════════════════════════════
    // GETTERS Y SETTERS — USUARIO
    // ══════════════════════════════════════════════

    /**
     * Devuelve el número de movimientos disponibles del usuario.
     *
     * @return movimientos restantes
     */
    public int getNumMovimientos() {
        return numMovimientos;
    }

    /**
     * Establece el número de movimientos disponibles del usuario.
     *
     * @param numMovimientos nuevo valor de movimientos
     */
    public void setNumMovimientos(int numMovimientos) {
        this.numMovimientos = numMovimientos;
    }

    /**
     * Devuelve los puntos de experiencia acumulados por el usuario.
     *
     * @return experiencia actual
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * Establece los puntos de experiencia del usuario.
     *
     * @param experiencia nuevo valor de experiencia
     */
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Devuelve la contraseña codificada del usuario.
     *
     * @return contraseña en formato Base64
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña codificada del usuario.
     *
     * @param contrasena nueva contraseña en formato Base64
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Devuelve el nickname del usuario.
     *
     * @return nombre de usuario único
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Establece el nickname del usuario.
     *
     * @param nickname nuevo nombre de usuario
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Devuelve el rol del usuario en el sistema.
     *
     * @return rol (p. ej. {@code "admin"}, {@code "user"})
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario en el sistema.
     *
     * @param rol nuevo rol a asignar
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
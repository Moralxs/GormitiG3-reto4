package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase utilitaria que centraliza la gestión de conexiones a la base de datos
 * MySQL del sistema de gestión del intercambiador.
 *
 * <p>Proporciona dos métodos estáticos principales:
 * <ul>
 *   <li>{@link #getConexion()} — abre y devuelve una nueva conexión JDBC.</li>
 *   <li>{@link #cerrarConexion(Connection, PreparedStatement, ResultSet)} —
 *       cierra ordenadamente los recursos JDBC abiertos.</li>
 * </ul>
 *
 * <p>Al ser todos sus métodos estáticos, no es necesario instanciar esta clase.
 * Cualquier clase del proyecto puede obtener una conexión llamando directamente
 * a {@code ConexionMySQL.getConexion()}.
 *
 * <p><b>Nota de seguridad:</b> las credenciales están definidas como constantes
 * en el código fuente. En un entorno de producción se recomienda externalizarlas
 * a un fichero de propiedades o a variables de entorno.
 *
 * @author  Gormiti
 * @version 1.0
 */
public class ConexionMySQL {

    /**
     * URL de conexión JDBC a la base de datos MySQL.
     *
     * <p>Parámetros incluidos:
     * <ul>
     *   <li>{@code useSSL=false} — deshabilita SSL para entornos locales de desarrollo.</li>
     *   <li>{@code serverTimezone=UTC} — evita conflictos de zona horaria con el driver.</li>
     * </ul>
     */
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sistema_intercambio?useSSL=false&serverTimezone=UTC";

    /** Nombre de usuario con acceso a la base de datos MySQL. */
    private static final String USUARIO = "root";

    /** Contraseña del usuario de la base de datos MySQL. */
    private static final String PASSWORD = "root";

    // ══════════════════════════════════════════════
    // MÉTODOS ESTÁTICOS
    // ══════════════════════════════════════════════

    /**
     * Abre y devuelve una nueva conexión JDBC a la base de datos MySQL.
     *
     * <p>Proceso interno:
     * <ol>
     *   <li>Carga el driver {@code com.mysql.cj.jdbc.Driver} mediante
     *       {@link Class#forName(String)}.</li>
     *   <li>Establece la conexión con {@link DriverManager#getConnection(String, String, String)}
     *       usando {@link #URL}, {@link #USUARIO} y {@link #PASSWORD}.</li>
     * </ol>
     *
     * <p>En caso de error imprime el mensaje por {@code System.err} y devuelve
     * {@code null}. El llamante debe comprobar que el resultado no sea {@code null}
     * antes de usar la conexión.
     *
     * <p>La conexión devuelta debe cerrarse siempre mediante
     * {@link #cerrarConexion(Connection, PreparedStatement, ResultSet)}
     * o en un bloque {@code try-with-resources}.
     *
     * @return una {@link Connection} abierta y lista para usar,
     *         o {@code null} si no fue posible establecer la conexión
     */
    public static Connection getConexion() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la BD: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Cierra ordenadamente los recursos JDBC proporcionados, en el orden
     * correcto: primero {@link ResultSet}, después {@link PreparedStatement}
     * y finalmente {@link Connection}.
     *
     * <p>Cualquiera de los tres parámetros puede ser {@code null}; en ese
     * caso simplemente se omite el cierre de ese recurso, evitando
     * {@link NullPointerException}.
     *
     * <p>Si el cierre es exitoso imprime confirmación por {@code System.out}.
     * Si ocurre un {@link SQLException} durante el cierre, imprime el error
     * por {@code System.err} sin relanzar la excepción.
     *
     * @param conn conexión JDBC a cerrar, o {@code null} para omitir
     * @param ps   sentencia preparada a cerrar, o {@code null} para omitir
     * @param rs   conjunto de resultados a cerrar, o {@code null} para omitir
     */
    public static void cerrarConexion(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs   != null) rs.close();
            if (ps   != null) ps.close();
            if (conn != null) conn.close();
            System.out.println("Conexión cerrada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    /**
     * Método de prueba que verifica que la conexión a la base de datos
     * se puede establecer correctamente.
     *
     * <p>Llama a {@link #getConexion()} e imprime por consola si la
     * conexión fue exitosa o no. La conexión se cierra inmediatamente
     * tras la comprobación.
     *
     * <p><b>Uso:</b> ejecutar esta clase directamente durante el desarrollo
     * para validar que los parámetros de conexión ({@link #URL},
     * {@link #USUARIO}, {@link #PASSWORD}) son correctos.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Connection conn = getConexion();
        if (conn != null) {
            System.out.println("¡Conexión exitosa!");
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        } else {
            System.out.println("No se pudo establecer la conexión.");
        }
    }
}
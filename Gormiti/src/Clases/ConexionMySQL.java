package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String URL      = "jdbc:mysql://127.0.0.1:3306/sistema_intercambio?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "root";

    // Método estático para obtener la conexión desde cualquier clase
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
 // Método para cerrar la conexión
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

    // Main para probar la conexión
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
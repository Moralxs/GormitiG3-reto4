package Visual;   //XABI

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

import Clases.PasswordBase64Encoder;

import java.awt.event.ActionEvent;

/**
 * Ventana de inicio de sesión del sistema de gestión del intercambiador.
 *
 * <p>Permite al usuario autenticarse introduciendo su {@code nickname} y
 * {@code contraseña}. La contraseña se codifica mediante
 * {@link PasswordBase64Encoder#encrypt(String)} antes de compararla con
 * el valor almacenado en la base de datos MySQL.
 *
 * <p>Si las credenciales son correctas se abre la ventana principal
 * {@link GestionIntercambiador}; en caso contrario se muestra un mensaje
 * de error en el propio formulario.
 *
 * <p>Resolución de diseño: 450×320 px.
 *
 * @author  Gormiti
 * @version 1.0
 * @see     GestionIntercambiador
 * @see     PasswordBase64Encoder
 */
public class InicioSesion {

    /** Ventana principal del formulario de inicio de sesión. */
    private JFrame frame;

    /** Campo de texto donde el usuario introduce su nickname. */
    private JTextField textFieldNick;

    /** Campo de contraseña donde el usuario introduce su clave de acceso. */
    private JPasswordField passwordField;

    /** Etiqueta de retroalimentación que muestra mensajes de éxito o error al usuario. */
    private JLabel lblMensaje;

    // ── Configuración de la BD ──────────────────────────────────────────

    /** URL de conexión JDBC a la base de datos MySQL del sistema de intercambio. */
    private static final String DB_URL  = "jdbc:mysql://127.0.0.1:3306/sistema_intercambio";

    /** Usuario de la base de datos MySQL. */
    private static final String DB_USER = "root";

    /** Contraseña del usuario de la base de datos MySQL. */
    private static final String DB_PASS = "root";

    // ══════════════════════════════════════════════
    // PUNTO DE ENTRADA
    // ══════════════════════════════════════════════

    /**
     * Punto de entrada de la aplicación.
     *
     * <p>Lanza la ventana de inicio de sesión en el hilo de despacho de eventos
     * de Swing ({@link EventQueue#invokeLater}) para garantizar la seguridad
     * en el acceso a la interfaz gráfica.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InicioSesion window = new InicioSesion();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // ══════════════════════════════════════════════
    // CONSTRUCTOR  //XABI Y MIKEL 
    // ══════════════════════════════════════════════

    /**
     * Crea e inicializa la ventana de inicio de sesión.
     * Delega la construcción de los componentes gráficos en {@link #initialize()}.
     */
    public InicioSesion() {
        initialize();
    }

    // ══════════════════════════════════════════════
    // INICIALIZACIÓN DE LA INTERFAZ   //XABI y PERU
    // ══════════════════════════════════════════════

    /**
     * Construye y configura todos los componentes de la interfaz gráfica.
     *
     * <p>Componentes creados:
     * <ul>
     *   <li>Título "Bienvenido".</li>
     *   <li>Etiqueta y campo de texto para el {@code nickname}.</li>
     *   <li>Etiqueta y campo de contraseña para la {@code contraseña}.</li>
     *   <li>Etiqueta de mensaje de retroalimentación ({@link #lblMensaje}).</li>
     *   <li>Botón <em>Iniciar Sesión</em> → llama a {@link #validarLogin()}.</li>
     *   <li>Botón <em>Limpiar</em> → llama a {@link #limpiarCampos()}.</li>
     * </ul>
     */
    private void initialize() {
        frame = new JFrame("Inicio de Sesión");
        frame.setBounds(100, 100, 450, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        // Título
        JLabel lblTitulo = new JLabel("Bienvenido");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(160, 20, 150, 30);
        frame.getContentPane().add(lblTitulo);

        // Etiqueta NickName
        JLabel lblNick = new JLabel("NickName:");
        lblNick.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNick.setBounds(67, 80, 90, 23);
        frame.getContentPane().add(lblNick);

        // Campo NickName — entrada de texto libre, máx. 10 columnas visibles
        textFieldNick = new JTextField();
        textFieldNick.setBounds(165, 82, 140, 22);
        textFieldNick.setColumns(10);
        frame.getContentPane().add(textFieldNick);

        // Etiqueta Contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPass.setBounds(67, 125, 90, 23);
        frame.getContentPane().add(lblPass);

        // Campo Contraseña — oculta los caracteres introducidos
        passwordField = new JPasswordField();
        passwordField.setBounds(165, 127, 140, 22);
        frame.getContentPane().add(passwordField);

        // Etiqueta de retroalimentación — muestra mensajes de éxito/error en cursiva
        lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.ITALIC, 12));
        lblMensaje.setBounds(67, 170, 300, 20);
        frame.getContentPane().add(lblMensaje);

        // Botón Iniciar Sesión — dispara la validación de credenciales contra la BD
        JButton btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnIniciar.setBounds(100, 210, 130, 28);
        btnIniciar.addActionListener((ActionEvent e) -> validarLogin());
        frame.getContentPane().add(btnIniciar);

        // Botón Limpiar — vacía los campos y devuelve el foco al campo nickname
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnLimpiar.setBounds(250, 210, 90, 28);
        btnLimpiar.addActionListener((ActionEvent e) -> limpiarCampos());
        frame.getContentPane().add(btnLimpiar);
    }

    // ══════════════════════════════════════════════
    // LÓGICA DE AUTENTICACIÓN    //XABI Y ANDONI 
    // ══════════════════════════════════════════════

    /**
     * Valida las credenciales introducidas por el usuario contra la base de datos.
     *
     * <p>Flujo de ejecución:
     * <ol>
     *   <li>Comprueba que ninguno de los campos esté vacío.</li>
     *   <li>Codifica la contraseña usando {@link PasswordBase64Encoder#encrypt(String)}.</li>
     *   <li>Abre una conexión JDBC y ejecuta:
     *       {@code SELECT COUNT(*) FROM usuario WHERE nickname=? AND contrasena=?}.</li>
     *   <li>Si {@code COUNT > 0}: muestra mensaje de éxito y abre {@link GestionIntercambiador}.</li>
     *   <li>Si {@code COUNT = 0}: muestra mensaje de credenciales incorrectas.</li>
     * </ol>
     *
     * <p>Todos los mensajes de retroalimentación se muestran a través de
     * {@link #mostrarMensaje(String, Color)}.
     */
    private void validarLogin() {
        String nick = textFieldNick.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (nick.isEmpty() || pass.isEmpty()) {
            mostrarMensaje("Por favor, rellena todos los campos.", Color.ORANGE);
            return;
        }

        PasswordBase64Encoder pass1 = new PasswordBase64Encoder();
        String hashPass = pass1.encrypt(pass);

        if (hashPass == null) {
            mostrarMensaje("Error al procesar la contraseña.", Color.RED);
            return;
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String sql = "SELECT COUNT(*) FROM usuario WHERE nickname = ? AND contrasena = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nick);
                ps.setString(2, hashPass);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        mostrarMensaje("✔ Sesión iniciada correctamente.", new Color(0, 150, 0));
                        new GestionIntercambiador();
                    } else {
                        mostrarMensaje("✘ NickName o contraseña incorrectos.", Color.RED);
                    }
                }
            }

        } catch (SQLException ex) {
            mostrarMensaje("Error de conexión con la BD.", Color.RED);
            ex.printStackTrace();
        }
    }

    // ══════════════════════════════════════════════
    // MÉTODOS AUXILIARES    //XABI Y PERU 
    // ══════════════════════════════════════════════

    /**
     * Calcula el hash SHA-256 de una cadena de texto y lo devuelve
     * como cadena hexadecimal en minúsculas (p. ej. {@code "a94a8fe5..."}).
     *
     * <p><b>Nota:</b> Este método no se usa actualmente en el flujo de login
     * (se emplea {@link PasswordBase64Encoder}), pero se conserva como
     * alternativa de cifrado.
     *
     * @param  texto cadena a hashear, codificada en UTF-8
     * @return hash SHA-256 en hexadecimal minúscula, o {@code null} si ocurre
     *         un error con el algoritmo o la codificación
     */
    private String hashSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Muestra un mensaje de retroalimentación en {@link #lblMensaje}
     * con el color indicado.
     *
     * <p>Convenciones de color habituales:
     * <ul>
     *   <li>{@link Color#ORANGE} — campos vacíos o advertencias.</li>
     *   <li>{@link Color#RED} — error de credenciales o de conexión.</li>
     *   <li>{@code new Color(0, 150, 0)} — inicio de sesión correcto.</li>
     * </ul>
     *
     * @param texto  mensaje a mostrar al usuario
     * @param color  color del texto del mensaje
     */
    private void mostrarMensaje(String texto, Color color) {
        lblMensaje.setForeground(color);
        lblMensaje.setText(texto);
    }

    /**
     * Limpia el contenido de los campos {@link #textFieldNick} y
     * {@link #passwordField}, borra el mensaje de retroalimentación
     * y devuelve el foco al campo de nickname para facilitar
     * un nuevo intento de inicio de sesión.
     */
    private void limpiarCampos() {
        textFieldNick.setText("");
        passwordField.setText("");
        lblMensaje.setText("");
        textFieldNick.requestFocus();
    }
}
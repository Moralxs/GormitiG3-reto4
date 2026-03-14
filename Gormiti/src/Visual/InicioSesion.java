package Visual;

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

public class InicioSesion {

    private JFrame frame;
    private JTextField textFieldNick;
    private JPasswordField passwordField;
    private JLabel lblMensaje;

    // ── Configuración de la BD ──────────────────────────────────────────
    private static final String DB_URL  = "jdbc:mysql://127.0.0.1:3306/sistema_intercambio";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    // ───────────────────────────────────────────────────────────────────

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

    public InicioSesion() {
        initialize();
    }

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

        // Label NickName
        JLabel lblNick = new JLabel("NickName:");
        lblNick.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNick.setBounds(67, 80, 90, 23);
        frame.getContentPane().add(lblNick);

        // Campo NickName
        textFieldNick = new JTextField();
        textFieldNick.setBounds(165, 82, 140, 22);
        textFieldNick.setColumns(10);
        frame.getContentPane().add(textFieldNick);

        // Label Contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPass.setBounds(67, 125, 90, 23);
        frame.getContentPane().add(lblPass);

        // Campo Contraseña
        passwordField = new JPasswordField();
        passwordField.setBounds(165, 127, 140, 22);
        frame.getContentPane().add(passwordField);

        // Label mensaje feedback
        lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Tahoma", Font.ITALIC, 12));
        lblMensaje.setBounds(67, 170, 300, 20);
        frame.getContentPane().add(lblMensaje);

        // Botón Iniciar Sesión
        JButton btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnIniciar.setBounds(100, 210, 130, 28);
        btnIniciar.addActionListener((ActionEvent e) -> validarLogin());
        frame.getContentPane().add(btnIniciar);

        // Botón Limpiar
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnLimpiar.setBounds(250, 210, 90, 28);
        btnLimpiar.addActionListener((ActionEvent e) -> limpiarCampos());
        frame.getContentPane().add(btnLimpiar);
    }

    // ── Validación contra MySQL ─────────────────────────────────────────
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

    // ── SHA-256 ─────────────────────────────────────────────────────────
    private String hashSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // devuelve hex en minúsculas, ej: "a94a8fe5..."
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ── Helpers ─────────────────────────────────────────────────────────
    private void mostrarMensaje(String texto, Color color) {
        lblMensaje.setForeground(color);
        lblMensaje.setText(texto);
    }

    private void limpiarCampos() {
        textFieldNick.setText("");
        passwordField.setText("");
        lblMensaje.setText("");
        textFieldNick.requestFocus();
    }
}
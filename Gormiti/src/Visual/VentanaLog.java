package Visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import Clases.ConexionMySQL;
import Clases.Genero;
import Clases.PasswordBase64Encoder;
import Clases.Usuario;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Label;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ventana de registro de nuevos usuarios en el sistema de gestión
 * del intercambiador.
 *
 * <p>Permite introducir los datos personales y de acceso necesarios para
 * crear una cuenta nueva:
 * <ul>
 *   <li>Datos de persona: nombre, apellido, edad, género, mail y teléfono.</li>
 *   <li>Datos de usuario: nickname y contraseña (codificada con
 *       {@link PasswordBase64Encoder}).</li>
 * </ul>
 *
 * <p>Flujo de registro:
 * <ol>
 *   <li>Se inserta primero la persona en la BD mediante {@link Usuario#insertarP(Usuario)}
 *       y se obtiene el {@code id_persona} generado.</li>
 *   <li>Con ese ID se inserta el usuario mediante {@link Usuario#insertarU(int, String, String, String)}
 *       con rol {@code "admin"} por defecto.</li>
 * </ol>
 *
 * <p>También ofrece un botón para navegar a {@link InicioSesion}
 * si el usuario ya dispone de una cuenta.
 *
 * <p>Resolución de diseño: 1920×1080 px.
 *
 * @author  Gormiti
 * @version 1.0
 * @see     InicioSesion
 * @see     Usuario
 * @see     PasswordBase64Encoder
 * @see     Genero
 */
public class VentanaLog extends JFrame {

    /** Ventana principal del formulario de registro. */
    private JFrame frame;

    /** Campo de texto para introducir el nombre de la persona. */
    private JTextField Nombretxt;

    /** Campo de texto para introducir el apellido de la persona. */
    private JTextField Apellidotxt;

    /** Campo de texto para introducir el correo electrónico de la persona. */
    private JTextField Mailtxt;

    /** Campo de texto para introducir el número de teléfono de la persona. */
    private JTextField Telefonotxt;

    /** Campo de texto para introducir el nickname que identificará al usuario en el sistema. */
    private JTextField nick;

    /**
     * Campo de texto para introducir la contraseña del usuario.
     *
     * <p><b>Nota:</b> Se recomienda sustituir por {@link javax.swing.JPasswordField}
     * para ocultar los caracteres al escribir.
     */
    private JTextField contraseña;

    // ══════════════════════════════════════════════
    // PUNTO DE ENTRADA
    // ══════════════════════════════════════════════

    /**
     * Punto de entrada de la aplicación.
     *
     * <p>Lanza la ventana de registro en el hilo de despacho de eventos
     * de Swing ({@link EventQueue#invokeLater}) para garantizar la seguridad
     * en el acceso a la interfaz gráfica.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaLog window = new VentanaLog();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // ══════════════════════════════════════════════
    // CONSTRUCTOR
    // ══════════════════════════════════════════════

    /**
     * Crea e inicializa la ventana de registro de usuarios.
     * Delega la construcción de los componentes gráficos en {@link #initialize()}.
     */
    public VentanaLog() {
        initialize();
    }

    // ══════════════════════════════════════════════
    // INICIALIZACIÓN DE LA INTERFAZ
    // ══════════════════════════════════════════════

    /**
     * Construye y configura todos los componentes de la interfaz gráfica.
     *
     * <p>Secciones del formulario:
     * <ul>
     *   <li><b>Datos personales</b> (encima del separador):
     *       nombre, apellido, edad ({@link JSpinner}), género ({@link JComboBox}),
     *       mail y teléfono.</li>
     *   <li><b>Datos de acceso</b> (debajo del separador):
     *       nickname y contraseña.</li>
     *   <li><b>Botones de acción</b>:
     *       <ul>
     *         <li><em>Crear Usuario</em>: recoge todos los campos, construye un
     *             {@link Usuario} y lo persiste en la BD en dos pasos.</li>
     *         <li><em>Ya Tengo Un Usuario</em>: abre {@link InicioSesion}.</li>
     *       </ul>
     *   </li>
     * </ul>
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Título de la ventana
        JLabel lblNewLabel = new JLabel("Log In:");
        lblNewLabel.setBounds(10, 10, 73, 31);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
        frame.getContentPane().add(lblNewLabel);

        // ── Datos personales ───────────────────────────────────────────

        // Etiqueta y campo Nombre
        JLabel lblNewLabel_1 = new JLabel("Nombre");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(309, 162, 100, 31);
        frame.getContentPane().add(lblNewLabel_1);

        // Etiqueta y campo Apellido
        JLabel lblNewLabel_2 = new JLabel("Apellido");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_2.setBounds(309, 280, 100, 31);
        frame.getContentPane().add(lblNewLabel_2);

        Nombretxt = new JTextField();
        Nombretxt.setBounds(450, 162, 205, 31);
        frame.getContentPane().add(Nombretxt);
        Nombretxt.setColumns(10);

        Apellidotxt = new JTextField();
        Apellidotxt.setBounds(450, 280, 205, 31);
        frame.getContentPane().add(Apellidotxt);
        Apellidotxt.setColumns(10);

        // Etiqueta y spinner Edad — permite seleccionar un entero
        JLabel lblNewLabel_3 = new JLabel("Edad");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_3.setBounds(850, 162, 100, 31);
        frame.getContentPane().add(lblNewLabel_3);

        JSpinner Edadspinner = new JSpinner();
        Edadspinner.setBounds(991, 162, 205, 31);
        frame.getContentPane().add(Edadspinner);

        // Etiqueta y combo Género — poblado con los valores del enum {@link Genero}
        JLabel lblNewLabel_4 = new JLabel("Genero");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_4.setBounds(850, 280, 100, 31);
        frame.getContentPane().add(lblNewLabel_4);

        JComboBox<Genero> GeneroBox = new JComboBox<>(Genero.values());
        GeneroBox.setBounds(991, 280, 205, 31);
        GeneroBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.getContentPane().add(GeneroBox);

        // Separador visual entre datos personales y datos de acceso
        JSeparator separator = new JSeparator();
        separator.setBounds(200, 540, 1080, 2);
        frame.getContentPane().add(separator);

        // Etiqueta y campo Mail
        JLabel lblNewLabel_5 = new JLabel("Mail");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_5.setBounds(309, 390, 100, 31);
        frame.getContentPane().add(lblNewLabel_5);

        Mailtxt = new JTextField();
        Mailtxt.setBounds(450, 390, 205, 31);
        frame.getContentPane().add(Mailtxt);
        Mailtxt.setColumns(10);

        // Etiqueta y campo Teléfono
        JLabel lblNewLabel_6 = new JLabel("Telefono");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_6.setBounds(850, 390, 100, 31);
        frame.getContentPane().add(lblNewLabel_6);

        Telefonotxt = new JTextField();
        Telefonotxt.setBounds(991, 390, 205, 31);
        frame.getContentPane().add(Telefonotxt);
        Telefonotxt.setColumns(10);

        // ── Datos de acceso ────────────────────────────────────────────

        // Etiqueta y campo NickName
        JLabel lblNewLabel_7 = new JLabel("NickName:");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_7.setBounds(309, 492, 100, 31);
        frame.getContentPane().add(lblNewLabel_7);

        nick = new JTextField();
        nick.setColumns(10);
        nick.setBounds(450, 492, 205, 31);
        frame.getContentPane().add(nick);

        // Etiqueta y campo Contraseña (texto plano — ver nota en el campo)
        JLabel lblNewLabel_6_1 = new JLabel("Contraseña");
        lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_6_1.setBounds(850, 492, 100, 31);
        frame.getContentPane().add(lblNewLabel_6_1);

        contraseña = new JTextField();
        contraseña.setColumns(10);
        contraseña.setBounds(991, 492, 205, 31);
        frame.getContentPane().add(contraseña);

        // ── Botones de acción ──────────────────────────────────────────

        /**
         * Botón "Ya Tengo Un Usuario".
         * Al hacer clic abre la ventana {@link InicioSesion} para que el
         * usuario ya registrado pueda autenticarse.
         */
        JButton btnNewButton = new JButton("Ya Tengo Un Usuario");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InicioSesion ini = new InicioSesion();
            }
        });
        btnNewButton.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
        btnNewButton.setBounds(850, 671, 205, 51);
        frame.getContentPane().add(btnNewButton);

        /**
         * Botón "Crear Usuario".
         *
         * <p>Recoge todos los campos del formulario y ejecuta el siguiente flujo:
         * <ol>
         *   <li>Construye un objeto {@link Usuario} con los datos personales.</li>
         *   <li>Codifica la contraseña con {@link PasswordBase64Encoder#encrypt(String)}.</li>
         *   <li>Llama a {@link Usuario#insertarP(Usuario)} para insertar la persona
         *       en la BD y obtener su {@code id_persona}.</li>
         *   <li>Si el ID es válido, llama a {@link Usuario#insertarU(int, String, String, String)}
         *       con rol {@code "admin"} para crear la cuenta de usuario.</li>
         *   <li>Muestra un {@link JOptionPane} informando del resultado.</li>
         * </ol>
         */
        JButton CrearUsuariobtn = new JButton("Crear Usuario");
        CrearUsuariobtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PasswordBase64Encoder pass = new PasswordBase64Encoder();
                Usuario u = new Usuario();
                u.setNombre  (Nombretxt.getText().trim());
                u.setApellido(Apellidotxt.getText().trim());
                u.setEdad    ((int) Edadspinner.getValue());
                u.setGenero  (Genero.valueOf(GeneroBox.getSelectedItem().toString()));
                u.setMail    (Mailtxt.getText().trim());
                u.setTelefono(Telefonotxt.getText().trim());

                // Cifrar contraseña y asignar rol por defecto
                @SuppressWarnings("static-access")
                String contrasena = pass.encrypt(contraseña.getText().trim());
                String rol        = "admin";
                String nickname   = nick.getText().trim();

                // Paso 1: insertar persona y obtener su ID autogenerado
                int idPersona = u.insertarP(u);

                // Paso 2: insertar usuario vinculado a la persona recién creada
                if (idPersona != -1) {
                    boolean ok = u.insertarU(idPersona, contrasena, rol, nickname);
                    if (ok) {
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al crear el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar la persona.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        CrearUsuariobtn.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
        CrearUsuariobtn.setBounds(450, 671, 205, 51);
        frame.getContentPane().add(CrearUsuariobtn);
    }

    // ══════════════════════════════════════════════
    // MÉTODOS HEREDADOS
    // ══════════════════════════════════════════════

    /**
     * Devuelve la representación en cadena de esta ventana.
     *
     * <p>Delega en la implementación de {@link JFrame#toString()}.
     * Puede sobrescribirse en el futuro para incluir información
     * sobre el estado del formulario.
     *
     * @return cadena de texto generada por la superclase
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
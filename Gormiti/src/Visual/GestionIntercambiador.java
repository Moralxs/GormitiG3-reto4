package Visual;  //XABI

import Clases.ConexionMySQL;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana principal de gestión del intercambiador de calor.
 *
 * <p>Muestra dos tablas conectadas a la base de datos MySQL:
 * <ul>
 *   <li><b>REGISTROS</b> (panel izquierdo): tabla {@code registro} con los registros realizados.</li>
 *   <li><b>USUARIOS</b> (panel derecho): tabla {@code usuario} con los datos de los usuarios del sistema.</li>
 * </ul>
 *
 * <p>Cada tabla dispone de cuatro botones de acción:
 * Actualizar, Editar, Borrar y Añadir.
 *
 * <p>Resolución de diseño: 1920×1080 px.
 *
 * @author  Gormiti
 * @version 1.0
 */
public class GestionIntercambiador extends JFrame {

    /** Identificador de serialización requerido por {@link JFrame}. */
    private static final long serialVersionUID = 1L;

    /** Ventana principal de la aplicación. */
    private JFrame frame;

    /** Modelo de datos de la tabla REGISTROS. Gestiona filas y columnas dinámicamente. */
    private DefaultTableModel modeloRegistros;

    /** Modelo de datos de la tabla USUARIOS. Gestiona filas y columnas dinámicamente. */
    private DefaultTableModel modeloUsuarios;

    /** Componente visual que muestra los registros de la BD en forma de tabla. */
    private JTable tablaRegistros;

    /** Componente visual que muestra los usuarios de la BD en forma de tabla. */
    private JTable tablaUsuarios;

    // ══════════════════════════════════════════════
    // CONSTRUCTOR
    // ══════════════════════════════════════════════

    /**
     * Crea e inicializa la ventana de gestión del intercambiador.
     * Llama a {@link #initialize()} para construir todos los componentes visuales.
     */
    public GestionIntercambiador() {
        initialize();
    }

    // ══════════════════════════════════════════════
    // INICIALIZACIÓN DE LA INTERFAZ
    // ══════════════════════════════════════════════

    /**   //XABI Y MIKEL
     * Construye y configura todos los componentes de la interfaz gráfica.
     *
     * <p>Operaciones realizadas:
     * <ol>
     *   <li>Crea el {@link JFrame} principal con layout absoluto (null layout).</li>
     *   <li>Carga y escala la imagen de la gráfica de temperatura.</li>
     *   <li>Carga los iconos de los botones desde {@code /recursos/}.</li>
     *   <li>Construye el panel izquierdo: etiqueta, separador, botones y tabla REGISTROS.</li>
     *   <li>Construye el panel derecho: etiqueta, separador, botones y tabla USUARIOS.</li>
     *   <li>Carga los datos iniciales desde la BD llamando a {@link #cargarRegistros()}
     *       y {@link #cargarUsuarios()}.</li>
     *   <li>Registra los listeners de los botones de acción de ambas tablas.</li>
     * </ol>
     */
    @SuppressWarnings({ "serial", "serial", "serial", "serial" })
    private void initialize() {
        frame = new JFrame("Gestión Intercambiador");
        frame.setBounds(0, 0, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // ─────────────────────────────────────────
        // ZONA IZQUIERDA  x: 20  ancho: 860
        // ZONA DERECHA    x: 920 ancho: 960
        // ─────────────────────────────────────────

        // IMAGEN GRÁFICA TEMPERATURA
        ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/grafica_temperatura.png"));
        Image imagenEscalada = icono.getImage().getScaledInstance(500, 260, Image.SCALE_SMOOTH);
        JLabel lblGrafica = new JLabel(new ImageIcon(imagenEscalada));
        lblGrafica.setBounds(180, 20, 500, 260);
        frame.getContentPane().add(lblGrafica);

        // ICONOS BOTONES — cargados desde /recursos/ y escalados a 110×35 px
        ImageIcon icoActualizar = escalarIcono("/recursos/boton_actualizar.png", 110, 35);
        ImageIcon icoBorrar     = escalarIcono("/recursos/boton_borrar.png",     110, 35);
        ImageIcon icoAnadir     = escalarIcono("/recursos/boton_anadir.png",     110, 35);
        ImageIcon icoEditar     = escalarIcono("/recursos/boton_editar.png",     110, 35);

        // ══════════════════════════════════════════
        // PANEL IZQUIERDO — REGISTROS  (x=20, ancho=860)
        // ══════════════════════════════════════════

        JLabel lblRegistros = new JLabel("REGISTROS");
        lblRegistros.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblRegistros.setBounds(20, 300, 200, 35);
        frame.getContentPane().add(lblRegistros);

        /** Línea separadora visual bajo el título REGISTROS. */
        JSeparator sepReg = new JSeparator();
        sepReg.setBounds(20, 337, 860, 2);
        frame.getContentPane().add(sepReg);

        /** Botón que recarga los datos de la tabla REGISTROS desde la BD. */
        JButton btnActualizarReg = new JButton(icoActualizar);
        btnActualizarReg.setBounds(20, 345, 110, 35);
        frame.getContentPane().add(btnActualizarReg);

        /** Botón que abre el diálogo de edición para la fila seleccionada en REGISTROS. */
        JButton btnEditarReg = new JButton(icoEditar);
        btnEditarReg.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditarReg.setBounds(145, 345, 110, 35);
        frame.getContentPane().add(btnEditarReg);

        /** Botón que elimina la fila seleccionada de la tabla REGISTROS en la BD. */
        JButton btnBorrarReg = new JButton(icoBorrar);
        btnBorrarReg.setBounds(270, 345, 110, 35);
        frame.getContentPane().add(btnBorrarReg);

        /** Botón que abre el diálogo para insertar un nuevo registro en la BD. */
        JButton btnAnadirReg = new JButton(icoAnadir);
        btnAnadirReg.setBounds(395, 345, 110, 35);
        frame.getContentPane().add(btnAnadirReg);

        // Tabla REGISTROS — columnas: id_centro, id_usuario, id_intercambiador, fecha_registro
        // Altura: desde y=390 hasta y=990 → 600 px
        modeloRegistros = new DefaultTableModel(
                new String[]{"id_centro", "id_usuario", "id_intercambiador", "fecha_registro"}, 0) {
            private static final long serialVersionUID = 1L;

            /**
             * Impide la edición directa de celdas; los cambios se realizan
             * exclusivamente mediante los botones Editar y Añadir.
             *
             * @param r índice de fila
             * @param c índice de columna
             * @return {@code false} siempre
             */
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaRegistros = new JTable(modeloRegistros);
        tablaRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRegistros.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tablaRegistros.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tablaRegistros.setRowHeight(26);
        tablaRegistros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollRegistros = new JScrollPane(tablaRegistros);
        scrollRegistros.setBounds(20, 390, 860, 600);
        frame.getContentPane().add(scrollRegistros);

        // ══════════════════════════════════════════
        // PANEL DERECHO — USUARIOS  (x=920, ancho=960)    //XABI PERU
        // ══════════════════════════════════════════

        JLabel lblUsuarios = new JLabel("USUARIOS");
        lblUsuarios.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblUsuarios.setBounds(920, 20, 200, 35);
        frame.getContentPane().add(lblUsuarios);

        /** Línea separadora visual bajo el título USUARIOS. */
        JSeparator sepUsu = new JSeparator();
        sepUsu.setBounds(920, 57, 960, 2);
        frame.getContentPane().add(sepUsu);

        /** Botón que recarga los datos de la tabla USUARIOS desde la BD. */
        JButton btnActualizarUsu = new JButton(icoActualizar);
        btnActualizarUsu.setBounds(920, 65, 110, 35);
        frame.getContentPane().add(btnActualizarUsu);

        /** Botón que abre el diálogo de edición para el usuario seleccionado. */
        JButton btnEditarUsu = new JButton(icoEditar);
        btnEditarUsu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditarUsu.setBounds(1045, 65, 110, 35);
        frame.getContentPane().add(btnEditarUsu);

        /** Botón que elimina el usuario seleccionado de la BD. */
        JButton btnBorrarUsu = new JButton(icoBorrar);
        btnBorrarUsu.setBounds(1170, 65, 110, 35);
        frame.getContentPane().add(btnBorrarUsu);

        /** Botón que abre el diálogo para insertar un nuevo usuario en la BD. */
        JButton btnAnadirUsu = new JButton(icoAnadir);
        btnAnadirUsu.setBounds(1295, 65, 110, 35);
        frame.getContentPane().add(btnAnadirUsu);

        // Tabla USUARIOS — columnas: id_persona, numMovimientos, experiencia,
        //                            contrasena, rol, nickname
        // Altura: desde y=110 hasta y=990 → 880 px
        modeloUsuarios = new DefaultTableModel(
                new String[]{"id_persona", "numMovimientos", "experiencia", "contrasena", "rol", "nickname"}, 0) {
            /**
             * Impide la edición directa de celdas; los cambios se realizan
             * exclusivamente mediante los botones Editar y Añadir.
             *
             * @param r índice de fila
             * @param c índice de columna
             * @return {@code false} siempre
             */
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tablaUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tablaUsuarios.setRowHeight(26);
        tablaUsuarios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setBounds(920, 110, 960, 880);
        frame.getContentPane().add(scrollUsuarios);

        // ─────────────────────────────────────────
        // CARGA INICIAL DE DATOS
        // ─────────────────────────────────────────
        cargarRegistros();
        cargarUsuarios();

        // ─────────────────────────────────────────
        // LISTENERS — REGISTROS
        // ─────────────────────────────────────────

        /** Recarga todos los registros de la BD en la tabla. */
        btnActualizarReg.addActionListener(e -> cargarRegistros());

        /**
         * Solicita confirmación y elimina el registro seleccionado.
         * Muestra aviso si no hay ninguna fila seleccionada.
         */
        btnBorrarReg.addActionListener(e -> {
            int fila = tablaRegistros.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(frame, "Selecciona un registro para borrar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "¿Seguro que quieres borrar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int idCentro         = (int) modeloRegistros.getValueAt(fila, 0);
                int idUsuario        = (int) modeloRegistros.getValueAt(fila, 1);
                int idIntercambiador = (int) modeloRegistros.getValueAt(fila, 2);
                borrarRegistro(idCentro, idUsuario, idIntercambiador);
                cargarRegistros();
            }
        });

        /** Abre el diálogo en modo inserción para añadir un nuevo registro. */
        btnAnadirReg.addActionListener(e -> abrirDialogoRegistro(false, -1));

        /**
         * Abre el diálogo en modo edición con los datos de la fila seleccionada.
         * Muestra aviso si no hay ninguna fila seleccionada.
         */
        btnEditarReg.addActionListener(e -> {
            int fila = tablaRegistros.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(frame, "Selecciona un registro para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            abrirDialogoRegistro(true, fila);
        });

        // ─────────────────────────────────────────
        // LISTENERS — USUARIOS     //XABI Y ANDONI
        // ─────────────────────────────────────────

        /** Recarga todos los usuarios de la BD en la tabla. */
        btnActualizarUsu.addActionListener(e -> cargarUsuarios());

        /**
         * Solicita confirmación y elimina el usuario seleccionado por {@code id_persona}.
         * Muestra aviso si no hay ninguna fila seleccionada.
         */
        btnBorrarUsu.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(frame, "Selecciona un usuario para borrar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "¿Seguro que quieres borrar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int idPersona = (int) modeloUsuarios.getValueAt(fila, 0);
                borrarUsuario(idPersona);
                cargarUsuarios();
            }
        });

        /** Abre el diálogo en modo inserción para añadir un nuevo usuario. */
        btnAnadirUsu.addActionListener(e -> abrirDialogoUsuario(false, -1));

        /**
         * Abre el diálogo en modo edición con los datos del usuario seleccionado.
         * Muestra aviso si no hay ninguna fila seleccionada.
         */
        btnEditarUsu.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(frame, "Selecciona un usuario para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            abrirDialogoUsuario(true, fila);
        });

        frame.setVisible(true);
    }

    // ══════════════════════════════════════════════
    // CRUD — REGISTROS     //XABI
    // ══════════════════════════════════════════════

    /**
     * Consulta la tabla {@code registro} en la BD y carga todas las filas
     * en {@link #modeloRegistros}, sustituyendo el contenido previo.
     *
     * <p>Columnas recuperadas:
     * {@code id_centro}, {@code id_usuario}, {@code id_intercambiador}, {@code fecha_registro}.
     *
     * <p>En caso de error SQL muestra un {@link JOptionPane} de error.
     */
    private void cargarRegistros() {
        modeloRegistros.setRowCount(0);
        String sql = "SELECT id_centro, id_usuario, id_intercambiador, fecha_registro FROM registro";
        try (Connection conn = ConexionMySQL.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                modeloRegistros.addRow(new Object[]{
                    rs.getInt("id_centro"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_intercambiador"),
                    rs.getDate("fecha_registro")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar registros:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina un registro de la tabla {@code registro} identificado
     * por la clave compuesta {@code (id_centro, id_usuario, id_intercambiador)}.
     *
     * <p>En caso de error SQL muestra un {@link JOptionPane} de error.
     *
     * @param idCentro         identificador del centro
     * @param idUsuario        identificador del usuario
     * @param idIntercambiador identificador del intercambiador
     */
    private void borrarRegistro(int idCentro, int idUsuario, int idIntercambiador) {
        String sql = "DELETE FROM registro WHERE id_centro=? AND id_usuario=? AND id_intercambiador=?";
        try (Connection conn = ConexionMySQL.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCentro);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idIntercambiador);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al borrar registro:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre un {@link JDialog} modal para insertar o editar un registro.
     *
     * <p>En modo edición ({@code esEdicion=true}) se precargan los valores de la fila
     * indicada. Al guardar, el registro antiguo se elimina con {@link #borrarRegistro}
     * y se inserta uno nuevo, dado que la tabla usa clave compuesta sin autoincremento.
     *
     * <p>Validaciones aplicadas:
     * <ul>
     *   <li>Los campos de ID deben ser números enteros.</li>
     *   <li>La fecha debe tener formato {@code YYYY-MM-DD}.</li>
     * </ul>
     *
     * @param esEdicion {@code true} para editar la fila existente,
     *                  {@code false} para insertar una nueva
     * @param fila      índice de fila en {@link #modeloRegistros} a editar;
     *                  ignorado cuando {@code esEdicion} es {@code false}
     */
    private void abrirDialogoRegistro(boolean esEdicion, int fila) {
        JDialog dialogo = new JDialog(frame, esEdicion ? "Editar Registro" : "Añadir Registro", true);
        dialogo.setSize(380, 300);
        dialogo.setLocationRelativeTo(frame);
        dialogo.setLayout(new GridLayout(6, 2, 8, 8));

        JTextField txtCentro         = new JTextField();
        JTextField txtUsuario        = new JTextField();
        JTextField txtIntercambiador = new JTextField();
        JTextField txtFecha          = new JTextField("YYYY-MM-DD");

        if (esEdicion) {
            txtCentro.setText(String.valueOf(modeloRegistros.getValueAt(fila, 0)));
            txtUsuario.setText(String.valueOf(modeloRegistros.getValueAt(fila, 1)));
            txtIntercambiador.setText(String.valueOf(modeloRegistros.getValueAt(fila, 2)));
            txtFecha.setText(String.valueOf(modeloRegistros.getValueAt(fila, 3)));
        }

        dialogo.add(new JLabel("  id_centro:"));         dialogo.add(txtCentro);
        dialogo.add(new JLabel("  id_usuario:"));        dialogo.add(txtUsuario);
        dialogo.add(new JLabel("  id_intercambiador:")); dialogo.add(txtIntercambiador);
        dialogo.add(new JLabel("  fecha_registro:"));    dialogo.add(txtFecha);
        dialogo.add(new JButton("Guardar") {{
            addActionListener(e -> {
                try {
                    int  idCentro         = Integer.parseInt(txtCentro.getText().trim());
                    int  idUsuario        = Integer.parseInt(txtUsuario.getText().trim());
                    int  idIntercambiador = Integer.parseInt(txtIntercambiador.getText().trim());
                    Date fecha            = Date.valueOf(txtFecha.getText().trim());

                    if (esEdicion) {
                        borrarRegistro(
                            (int) modeloRegistros.getValueAt(fila, 0),
                            (int) modeloRegistros.getValueAt(fila, 1),
                            (int) modeloRegistros.getValueAt(fila, 2)
                        );
                    }
                    String sql = "INSERT INTO registro (id_centro, id_usuario, id_intercambiador, fecha_registro) VALUES (?,?,?,?)";
                    try (Connection conn = ConexionMySQL.getConexion();
                         PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setInt(1, idCentro); ps.setInt(2, idUsuario);
                        ps.setInt(3, idIntercambiador); ps.setDate(4, fecha);
                        ps.executeUpdate();
                    }
                    cargarRegistros();
                    dialogo.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "Los IDs deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialogo, "Formato de fecha incorrecto. Usa YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error SQL:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }});
        dialogo.add(new JButton("Cancelar") {{ addActionListener(e -> dialogo.dispose()); }});

        dialogo.setVisible(true);
    }

    // ══════════════════════════════════════════════
    // CRUD — USUARIOS    // XABI Y PERU
    // ══════════════════════════════════════════════

    /**
     * Consulta la tabla {@code usuario} en la BD y carga todas las filas
     * en {@link #modeloUsuarios}, sustituyendo el contenido previo.
     *
     * <p>Columnas recuperadas:
     * {@code id_persona}, {@code numMovimientos}, {@code experiencia},
     * {@code contrasena}, {@code rol}, {@code nickname}.
     *
     * <p>En caso de error SQL muestra un {@link JOptionPane} de error.
     */
    private void cargarUsuarios() {
        modeloUsuarios.setRowCount(0);
        String sql = "SELECT id_persona, numMovimientos, experiencia, contrasena, rol, nickname FROM usuario";
        try (Connection conn = ConexionMySQL.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                modeloUsuarios.addRow(new Object[]{
                    rs.getInt("id_persona"),
                    rs.getInt("numMovimientos"),
                    rs.getInt("experiencia"),
                    rs.getString("contrasena"),
                    rs.getString("rol"),
                    rs.getString("nickname")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar usuarios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina un usuario de la tabla {@code usuario} identificado por su clave primaria.
     *
     * <p>En caso de error SQL muestra un {@link JOptionPane} de error.
     *
     * @param idPersona clave primaria del usuario a eliminar
     */
    private void borrarUsuario(int idPersona) {
        String sql = "DELETE FROM usuario WHERE id_persona=?";
        try (Connection conn = ConexionMySQL.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error al borrar usuario:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre un {@link JDialog} modal para insertar o editar un usuario.
     *
     * <p>En modo edición ({@code esEdicion=true}) se precargan los valores de la fila
     * indicada y el campo {@code id_persona} queda deshabilitado para evitar
     * modificar la clave primaria. La operación ejecuta un {@code UPDATE} directo.
     *
     * <p>En modo inserción ({@code esEdicion=false}) ejecuta un {@code INSERT} con
     * todos los campos introducidos por el usuario.
     *
     * <p>Validaciones aplicadas:
     * <ul>
     *   <li>{@code id_persona}, {@code numMovimientos} y {@code experiencia}
     *       deben ser números enteros.</li>
     * </ul>
     *
     * @param esEdicion {@code true} para editar el usuario existente,
     *                  {@code false} para insertar uno nuevo
     * @param fila      índice de fila en {@link #modeloUsuarios} a editar;
     *                  ignorado cuando {@code esEdicion} es {@code false}
     */
    private void abrirDialogoUsuario(boolean esEdicion, int fila) {
        JDialog dialogo = new JDialog(frame, esEdicion ? "Editar Usuario" : "Añadir Usuario", true);
        dialogo.setSize(400, 380);
        dialogo.setLocationRelativeTo(frame);
        dialogo.setLayout(new GridLayout(8, 2, 8, 8));

        JTextField txtId          = new JTextField();
        JTextField txtMovimientos = new JTextField();
        JTextField txtExperiencia = new JTextField();
        JTextField txtContrasena  = new JTextField();
        JTextField txtRol         = new JTextField();
        JTextField txtNickname    = new JTextField();

        if (esEdicion) {
            txtId.setText(String.valueOf(modeloUsuarios.getValueAt(fila, 0)));
            txtId.setEditable(false); // La PK no se puede modificar
            txtMovimientos.setText(String.valueOf(modeloUsuarios.getValueAt(fila, 1)));
            txtExperiencia.setText(String.valueOf(modeloUsuarios.getValueAt(fila, 2)));
            txtContrasena.setText(String.valueOf(modeloUsuarios.getValueAt(fila, 3)));
            txtRol.setText(String.valueOf(modeloUsuarios.getValueAt(fila, 4)));
            txtNickname.setText(String.valueOf(modeloUsuarios.getValueAt(fila, 5)));
        }

        dialogo.add(new JLabel("  id_persona:"));    dialogo.add(txtId);
        dialogo.add(new JLabel("  numMovimientos:")); dialogo.add(txtMovimientos);
        dialogo.add(new JLabel("  experiencia:"));    dialogo.add(txtExperiencia);
        dialogo.add(new JLabel("  contrasena:"));     dialogo.add(txtContrasena);
        dialogo.add(new JLabel("  rol:"));            dialogo.add(txtRol);
        dialogo.add(new JLabel("  nickname:"));       dialogo.add(txtNickname);

        dialogo.add(new JButton("Guardar") {{
            addActionListener(e -> {
                try {
                    int    idPersona      = Integer.parseInt(txtId.getText().trim());
                    int    numMovimientos = Integer.parseInt(txtMovimientos.getText().trim());
                    int    experiencia    = Integer.parseInt(txtExperiencia.getText().trim());
                    String contrasena     = txtContrasena.getText().trim();
                    String rol            = txtRol.getText().trim();
                    String nickname       = txtNickname.getText().trim();

                    String sql = esEdicion
                        ? "UPDATE usuario SET numMovimientos=?, experiencia=?, contrasena=?, rol=?, nickname=? WHERE id_persona=?"
                        : "INSERT INTO usuario (id_persona, numMovimientos, experiencia, contrasena, rol, nickname) VALUES (?,?,?,?,?,?)";

                    try (Connection conn = ConexionMySQL.getConexion();
                         PreparedStatement ps = conn.prepareStatement(sql)) {
                        if (esEdicion) {
                            ps.setInt(1, numMovimientos); ps.setInt(2, experiencia);
                            ps.setString(3, contrasena);  ps.setString(4, rol);
                            ps.setString(5, nickname);    ps.setInt(6, idPersona);
                        } else {
                            ps.setInt(1, idPersona);      ps.setInt(2, numMovimientos);
                            ps.setInt(3, experiencia);    ps.setString(4, contrasena);
                            ps.setString(5, rol);         ps.setString(6, nickname);
                        }
                        ps.executeUpdate();
                    }
                    cargarUsuarios();
                    dialogo.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "Los campos numéricos deben ser enteros.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error SQL:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }});
        dialogo.add(new JButton("Cancelar") {{ addActionListener(e -> dialogo.dispose()); }});

        dialogo.setVisible(true);
    }

    // ══════════════════════════════════════════════
    // MÉTODOS UTILITARIOS   //MIKEL Y XABI 
    // ══════════════════════════════════════════════

    /**
     * Carga un icono desde el classpath y lo escala a las dimensiones indicadas
     * usando interpolación suavizada ({@link Image#SCALE_SMOOTH}).
     *
     * @param ruta ruta del recurso dentro del classpath (p. ej. {@code "/recursos/boton_editar.png"})
     * @param w    ancho deseado en píxeles
     * @param h    alto deseado en píxeles
     * @return {@link ImageIcon} escalado listo para asignar a un {@link JButton} o {@link JLabel}
     */
    private ImageIcon escalarIcono(String ruta, int w, int h) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        return new ImageIcon(icono.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    // ══════════════════════════════════════════════
    // GETTERS / SETTERS   //XABI Y ANDONI 
    // ══════════════════════════════════════════════

    /**
     * Devuelve la instancia del {@link JFrame} principal de esta ventana.
     *
     * @return el {@link JFrame} de la aplicación
     */
    public JFrame getFrame() { return frame; }

    /**
     * Reemplaza el {@link JFrame} principal de esta ventana.
     *
     * @param frame nuevo {@link JFrame} a asignar
     */
    public void setFrame(JFrame frame) { this.frame = frame; }
}
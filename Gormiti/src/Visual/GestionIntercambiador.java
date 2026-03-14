package Visual;

import Clases.ConexionMySQL;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestionIntercambiador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame frame;

    private DefaultTableModel modeloRegistros;
    private DefaultTableModel modeloUsuarios;
    private JTable tablaRegistros;
    private JTable tablaUsuarios;

    public GestionIntercambiador() {
        initialize();
    }

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

        // ICONOS BOTONES
        ImageIcon icoActualizar = escalarIcono("/recursos/boton_actualizar.png", 110, 35);
        ImageIcon icoBorrar     = escalarIcono("/recursos/boton_borrar.png",     110, 35);
        ImageIcon icoAnadir     = escalarIcono("/recursos/boton_anadir.png",     110, 35);
        ImageIcon icoEditar     = escalarIcono("/recursos/boton_editar.png",     110, 35);

        // ══════════════════════════════════════════
        // IZQUIERDA — REGISTROS  (x=20, ancho=860)
        // ══════════════════════════════════════════
        JLabel lblRegistros = new JLabel("REGISTROS");
        lblRegistros.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblRegistros.setBounds(20, 300, 200, 35);
        frame.getContentPane().add(lblRegistros);

        JSeparator sepReg = new JSeparator();
        sepReg.setBounds(20, 337, 860, 2);
        frame.getContentPane().add(sepReg);

        JButton btnActualizarReg = new JButton(icoActualizar);
        btnActualizarReg.setBounds(20, 345, 110, 35);
        frame.getContentPane().add(btnActualizarReg);

        JButton btnEditarReg = new JButton(icoEditar);
        btnEditarReg.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditarReg.setBounds(145, 345, 110, 35);
        frame.getContentPane().add(btnEditarReg);

        JButton btnBorrarReg = new JButton(icoBorrar);
        btnBorrarReg.setBounds(270, 345, 110, 35);
        frame.getContentPane().add(btnBorrarReg);

        JButton btnAnadirReg = new JButton(icoAnadir);
        btnAnadirReg.setBounds(395, 345, 110, 35);
        frame.getContentPane().add(btnAnadirReg);

        // Tabla registros: desde y=390 hasta y=990 → alto=600
        modeloRegistros = new DefaultTableModel(
                new String[]{"id_centro", "id_usuario", "id_intercambiador", "fecha_registro"}, 0) {
            /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			@Override public boolean isCellEditable(int r, int c) { return false; }
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

     
        JLabel lblUsuarios = new JLabel("USUARIOS");
        lblUsuarios.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblUsuarios.setBounds(920, 20, 200, 35);
        frame.getContentPane().add(lblUsuarios);

        JSeparator sepUsu = new JSeparator();
        sepUsu.setBounds(920, 57, 960, 2);
        frame.getContentPane().add(sepUsu);

        JButton btnActualizarUsu = new JButton(icoActualizar);
        btnActualizarUsu.setBounds(920, 65, 110, 35);
        frame.getContentPane().add(btnActualizarUsu);

        JButton btnEditarUsu = new JButton(icoEditar);
        btnEditarUsu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditarUsu.setBounds(1045, 65, 110, 35);
        frame.getContentPane().add(btnEditarUsu);

        JButton btnBorrarUsu = new JButton(icoBorrar);
        btnBorrarUsu.setBounds(1170, 65, 110, 35);
        frame.getContentPane().add(btnBorrarUsu);

        JButton btnAnadirUsu = new JButton(icoAnadir);
        btnAnadirUsu.setBounds(1295, 65, 110, 35);
        frame.getContentPane().add(btnAnadirUsu);

        // Tabla usuarios: desde y=110 hasta y=990 → alto=880
        modeloUsuarios = new DefaultTableModel(
                new String[]{"id_persona", "numMovimientos", "experiencia", "contrasena", "rol", "nickname"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
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
        // CARGAR DATOS INICIALES
        // ─────────────────────────────────────────
        cargarRegistros();
        cargarUsuarios();

        // ─────────────────────────────────────────
        // ACCIONES — REGISTROS
        // ─────────────────────────────────────────
        btnActualizarReg.addActionListener(e -> cargarRegistros());

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

        btnAnadirReg.addActionListener(e -> abrirDialogoRegistro(false, -1));

        btnEditarReg.addActionListener(e -> {
            int fila = tablaRegistros.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(frame, "Selecciona un registro para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            abrirDialogoRegistro(true, fila);
        });

        // ─────────────────────────────────────────
        // ACCIONES — USUARIOS
        // ─────────────────────────────────────────
        btnActualizarUsu.addActionListener(e -> cargarUsuarios());

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

        btnAnadirUsu.addActionListener(e -> abrirDialogoUsuario(false, -1));

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
    // CRUD — REGISTROS
    // ══════════════════════════════════════════════

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
    // CRUD — USUARIOS
    // ══════════════════════════════════════════════

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
            txtId.setEditable(false);
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
    // UTILIDAD
    // ══════════════════════════════════════════════

    private ImageIcon escalarIcono(String ruta, int w, int h) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        return new ImageIcon(icono.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    public JFrame getFrame() { return frame; }
    public void setFrame(JFrame frame) { this.frame = frame; }
}
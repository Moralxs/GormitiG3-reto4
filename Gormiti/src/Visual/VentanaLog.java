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

public class VentanaLog extends JFrame {
	
	

	private JFrame frame;
	private JTextField Nombretxt;
	private JTextField Apellidotxt;
	private JTextField Mailtxt;
	private JTextField Telefonotxt;
	private JTextField nick;
	private JTextField contraseña;

	/**
	 * Launch the application.
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

	/**
	 * Create the application.
	 */
	public VentanaLog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Log In:");
		lblNewLabel.setBounds(10, 10, 73, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(309, 162, 100, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
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
		
		JLabel lblNewLabel_3 = new JLabel("Edad");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(850, 162, 100, 31);
		frame.getContentPane().add(lblNewLabel_3);
		
		JSpinner Edadspinner = new JSpinner();
		Edadspinner.setBounds(991, 162, 205, 31);
		frame.getContentPane().add(Edadspinner);
		
		JLabel lblNewLabel_4 = new JLabel("Genero");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(850, 280, 100, 31);
		frame.getContentPane().add(lblNewLabel_4);
		
	
		JComboBox<Genero> GeneroBox = new JComboBox<>(Genero.values());
		GeneroBox.setBounds(991, 280, 205, 31);
		GeneroBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(GeneroBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(200, 540, 1080, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel_5 = new JLabel("Mail\r\n");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(309, 390, 100, 31);
		frame.getContentPane().add(lblNewLabel_5);
		
		Mailtxt = new JTextField();
		Mailtxt.setBounds(450, 390, 205, 31);
		frame.getContentPane().add(Mailtxt);
		Mailtxt.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Telefono\r\n");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(850, 390, 100, 31);
		frame.getContentPane().add(lblNewLabel_6);
		
		Telefonotxt = new JTextField();
		Telefonotxt.setBounds(991, 390, 205, 31);
		frame.getContentPane().add(Telefonotxt);
		Telefonotxt.setColumns(10);
		
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
		
		JButton CrearUsuariobtn = new JButton("Crear Usuario");
		CrearUsuariobtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PasswordBase64Encoder pass = new PasswordBase64Encoder();
				 Usuario u = new Usuario();
				    u.setNombre  (Nombretxt.getText().trim());
				    u.setApellido(Apellidotxt.getText().trim());
				    u.setEdad((int) Edadspinner.getValue());
				    u.setGenero  (Genero.valueOf(GeneroBox.getSelectedItem().toString()));
				    u.setMail    (Mailtxt.getText().trim());
				    u.setTelefono(Telefonotxt.getText().trim());

				    // Datos de usuario
				    @SuppressWarnings("static-access")
					String contrasena = pass.encrypt(contraseña.getText().trim());
				    String rol        = "admin";
				    String nickname   = nick.getText().trim();

				    

				    // 1. Insertar persona y obtener ID
				    int idPersona = u.insertarP(u);

				    // 2. Insertar usuario con ese ID
				    if (idPersona != -1) {
				        boolean ok = u.insertarU(idPersona, contrasena, rol, nickname);
				        if (ok) {
				            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
				        } else {
				            JOptionPane.showMessageDialog(null, "Error al crear el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
				        }
				    } else {
				        JOptionPane.showMessageDialog(null, "Error al guardar la persona.", "Error", JOptionPane.ERROR_MESSAGE);
				    }}
		});
		CrearUsuariobtn.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		CrearUsuariobtn.setBounds(450, 671, 205, 51);
		frame.getContentPane().add(CrearUsuariobtn);
		
		JLabel lblNewLabel_7 = new JLabel("NickName:\r\n");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_7.setBounds(309, 492, 100, 31);
		frame.getContentPane().add(lblNewLabel_7);
		
		nick = new JTextField();
		nick.setColumns(10);
		nick.setBounds(450, 492, 205, 31);
		frame.getContentPane().add(nick);
		
		JLabel lblNewLabel_6_1 = new JLabel("Contraseña\r\n");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6_1.setBounds(850, 492, 100, 31);
		frame.getContentPane().add(lblNewLabel_6_1);
		
		contraseña = new JTextField();
		contraseña.setColumns(10);
		contraseña.setBounds(991, 492, 205, 31);
		frame.getContentPane().add(contraseña);
		
		
	
		
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}

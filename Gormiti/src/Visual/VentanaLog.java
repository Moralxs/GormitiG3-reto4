package Visual;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import Clases.Genero;
import Clases.Usuario;

import javax.swing.JMenuBar;
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

public class VentanaLog extends JFrame {
	
	

	private JFrame frame;
	private JTextField Nombretxt;
	private JTextField Apellidotxt;
	private JTextField Mailtxt;
	private JTextField Telefonotxt;

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
				GestionIntercambiador ventana44 = new GestionIntercambiador();
			}
		});
		btnNewButton.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		btnNewButton.setBounds(850, 671, 205, 51);
		frame.getContentPane().add(btnNewButton);
		
		JLabel labelPrueba = new JLabel("Prueba\r\n");
		labelPrueba.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelPrueba.setBounds(48, 481, 900, 31);
		frame.getContentPane().add(labelPrueba);
		
		JButton CrearUsuariobtn = new JButton("Crear Usuario");
		CrearUsuariobtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Usuario u1 = new Usuario();
				u1.setNombre(Nombretxt.getText());
				u1.setApellido(Apellidotxt.getText());
				u1.setEdad((int) Edadspinner.getValue());
				//Es necesario castear el objeto como un enumerador. Surgio un problema que se pudo detectar haciendo debug en esta función.
				u1.setGenero((Genero) GeneroBox.getSelectedItem());
				u1.setMail(Mailtxt.getText());
				u1.setTelefono(Telefonotxt.getText());
				
				labelPrueba.setText("Has generado un usuario con los atributos: " + u1.getNombre() + ", " + u1.getApellido() + ", " + u1.getEdad() + ", " + u1.getGenero()+ ", " + u1.getMail() + ", " + u1.getTelefono());
				
				
				
				
				
			System.out.println("Has generado un usuario con los atributos: " + u1.getNombre() + "," + u1.getApellido() + "," + u1.getEdad() + "," + u1.getGenero()+ "," + u1.getMail() + "," + u1.getTelefono());
				
			}
		});
		CrearUsuariobtn.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		CrearUsuariobtn.setBounds(450, 671, 205, 51);
		frame.getContentPane().add(CrearUsuariobtn);
		
		
	
		
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}

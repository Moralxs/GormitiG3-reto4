package Visual;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;

public class GestionIntercambiador extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	
	

	/**
	 * Create the application.
	 */
	public GestionIntercambiador() {
		System.out.print("constructor");
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
		frame.setVisible(true);
		
		
		ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/grafica_temperatura.png"));
		Image imagenEscalada = icono.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		
		JLabel lblNewLabel = new JLabel(iconoEscalado);
		lblNewLabel.setBounds(190, 21, 400, 306);
		getFrame().getContentPane().add(lblNewLabel);
		
		ImageIcon icono2 = new ImageIcon(getClass().getResource("/recursos/boton_actualizar.png"));
		Image imagenEscalada2 = icono2.getImage().getScaledInstance(100, 35, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado2 = new ImageIcon(imagenEscalada2);
        
		JButton actualizar = new JButton(iconoEscalado2);
		actualizar.setBounds(62, 389, 85, 21);
		getFrame().getContentPane().add(actualizar);
		
		ImageIcon icono3 = new ImageIcon(getClass().getResource("/recursos/boton_borrar.png"));
		Image imagenEscalada3 = icono3.getImage().getScaledInstance(100, 35, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado3 = new ImageIcon(imagenEscalada3);
        
		JButton borrar = new JButton(iconoEscalado3);
		borrar.setBounds(349, 389, 85, 21);
		getFrame().getContentPane().add(borrar);
		
		ImageIcon icono4 = new ImageIcon(getClass().getResource("/recursos/boton_anadir.png"));
		Image imagenEscalada4 = icono4.getImage().getScaledInstance(100, 35, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado4 = new ImageIcon(imagenEscalada4);
		
		JButton añadir = new JButton(iconoEscalado4);
		añadir.setBounds(593, 389, 85, 21);
		getFrame().getContentPane().add(añadir);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(62, 430, 756, 306);
		getFrame().getContentPane().add(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(988, 145, 500, 590);
		frame.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton(iconoEscalado2);
		btnNewButton.setBounds(988, 98, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(iconoEscalado3);
		btnNewButton_1.setBounds(1190, 98, 85, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(iconoEscalado4);
		btnNewButton_2.setBounds(1403, 98, 85, 21);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("REGISTROS");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(72, 757, 200, 40);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("USUARIOS");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_2.setBackground(new Color(240, 240, 240));
		lblNewLabel_2.setBounds(1000, 775, 200, 40);
		frame.getContentPane().add(lblNewLabel_2);
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}

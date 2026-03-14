/**
 * 
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase Usuario: Hereda de la clase Persona
 */
public class Usuario extends Persona {
	private int numMovimientos;
	private int experiencia;
	private String contrasena;
	private String nickname;
	private String rol;

	/**
	 * Constructor por parametros de la clase Usuario
	 * 
	 * @param nombre
	 * @param apellido
	 * @param edad
	 * @param genero
	 * @param mail
	 * @param telefono
	 * @param numMovimientos
	 * @param experiencia
	 * @param contrasena
	 * @param nickname
	 */
	public Usuario(String nombre, String apellido, int edad, Genero genero, String mail, String telefono,
			int numMovimientos, int experiencia, String contrasena, String nickname, String rol) {
		super(nombre, apellido, edad, genero, mail, telefono);
		this.numMovimientos = numMovimientos;
		this.experiencia = experiencia;
		this.contrasena = contrasena;
		this.nickname = nickname;
		this.rol = rol;

		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor vacio de la clase Usuario
	 */
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Este metodo resta en uno el numero de movimientos posible del usuario. Cuando
	 * llega a 0, salta un mensaje.
	 */
	public void Movimiento() {
		numMovimientos -= 1;
		if (numMovimientos == 0) {
			System.out.println("Te has quedado sin movimientos");
		}
	}

	

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return super.getNombre();
	}

	@Override
	public void setNombre(String nombre) {
		// TODO Auto-generated method stub
		super.setNombre(nombre);
	}

	@Override
	public String getApellido() {
		// TODO Auto-generated method stub
		return super.getApellido();
	}

	@Override
	public void setApellido(String apellido) {
		// TODO Auto-generated method stub
		super.setApellido(apellido);
	}

	@Override
	public int getEdad() {
		// TODO Auto-generated method stub
		return super.getEdad();
	}

	@Override
	public void setEdad(int edad) {
		// TODO Auto-generated method stub
		super.setEdad(edad);
	}

	@Override
	public Genero getGenero() {
		// TODO Auto-generated method stub
		return super.getGenero();
	}

	@Override
	public void setGenero(Genero genero) {
		// TODO Auto-generated method stub
		super.setGenero(genero);
	}

	@Override
	public String getMail() {
		// TODO Auto-generated method stub
		return super.getMail();
	}

	@Override
	public void setMail(String mail) {
		// TODO Auto-generated method stub
		super.setMail(mail);
	}

	@Override
	public String getTelefono() {
		// TODO Auto-generated method stub
		return super.getTelefono();
	}

	@Override
	public void setTelefono(String telefono) {
		// TODO Auto-generated method stub
		super.setTelefono(telefono);
	}

	public int getNumMovimientos() {
		return numMovimientos;
	}

	public void setNumMovimientos(int numMovimientos) {
		this.numMovimientos = numMovimientos;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int insertarP(Usuario u) {
	    String sql = "INSERT INTO persona (nombre, apellido, edad, genero, mail, telefono) "
	               + "VALUES (?, ?, ?, ?, ?, ?)";

	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = ConexionMySQL.getConexion();
	        ps   = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // ← línea clave

	        ps.setString(1, u.getNombre());
	        ps.setString(2, u.getApellido());
	        ps.setInt   (3, u.getEdad());
	        ps.setString(4, u.getGenero().name());
	        ps.setString(5, u.getMail());
	        ps.setString(6, u.getTelefono());

	        int filas = ps.executeUpdate();

	        if (filas > 0) {
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getInt(1); // ← devuelve el ID generado
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al insertar persona: " + e.getMessage());
	    } finally {
	        ConexionMySQL.cerrarConexion(conn, ps, null);
	    }
	    return -1; // falló
	}
	
	
    

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	public boolean insertarU(int idPersona, String contrasena, String rol, String nickname) {
	    String sql = "INSERT INTO usuario (id_persona, numMovimientos, experiencia, contrasena, rol, nickname) "
	               + "VALUES (?, 5, 5, ?, ?, ?)";
	    //                       ↑  ↑
	    //            numMovimientos y experiencia empiezan en 0 por defecto

	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = ConexionMySQL.getConexion();
	        ps   = conn.prepareStatement(sql);

	        ps.setInt   (1, idPersona);
	        ps.setString(2, contrasena);
	        ps.setString(3, rol);
	        ps.setString(4, nickname);

	        int filas = ps.executeUpdate();
	        return filas > 0;

	    } catch (SQLException e) {
	        System.err.println("Error al insertar usuario: " + e.getMessage());
	        return false;
	    } finally {
	        ConexionMySQL.cerrarConexion(conn, ps, null);
	    }
	}
}





	



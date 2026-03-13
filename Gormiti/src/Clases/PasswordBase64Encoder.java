package Clases;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordBase64Encoder {

    /**
     * Hashea un String usando SHA-256 y lo codifica en Base64
     * @param password String a encriptar
     * @return String en Base64
     */
    public static String encrypt(String password) {
        try {
            // Crear instancia de SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplicar hash
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Codificar en Base64
            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    // Método de prueba
    public static void main(String[] args) {
        String password = "MiPassword123";
        String encrypted = encrypt(password);

        System.out.println("Password original: " + password);
        System.out.println("Password encriptado (Base64): " + encrypted);
    }
}

package Clases;

/**
 * Enumeración que representa el género de una persona en el sistema.
 *
 * <p>Define los dos valores posibles utilizados en la clase {@link Persona}
 * y sus subclases, así como en el formulario de registro {@link Visual.VentanaLog}
 * mediante un {@link javax.swing.JComboBox}.
 *
 * <p>Valores disponibles:
 * <ul>
 *   <li>{@link #H} — Hombre.</li>
 *   <li>{@link #M} — Mujer.</li>
 * </ul>
 *
 * @author  Gormiti
 * @version 1.0
 * @see     Persona
 * @see     Usuario
 */
public enum Genero {

    /**
     * Representa el género masculino (Hombre).
     * Almacenado en la base de datos como la cadena {@code "H"}.
     */
    H,

    /**
     * Representa el género femenino (Mujer).
     * Almacenado en la base de datos como la cadena {@code "M"}.
     */
    M;

    // ══════════════════════════════════════════════
    // MÉTODOS HEREDADOS
    // ══════════════════════════════════════════════

    /**
     * Devuelve el nombre de la constante enum como cadena de texto.
     *
     * <p>Delega en {@link Enum#toString()}, por lo que devuelve
     * {@code "H"} o {@code "M"} según la constante.
     * Puede sobrescribirse en el futuro para devolver etiquetas
     * más descriptivas (p. ej. {@code "Hombre"} / {@code "Mujer"}).
     *
     * @return nombre de la constante: {@code "H"} o {@code "M"}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
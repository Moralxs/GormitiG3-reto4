package Clases;

/**
 * Representa un intercambiador de calor del sistema.
 *
 * <p>Almacena los parámetros operativos del dispositivo:
 * temperaturas de entrada y salida del fluido, fuente de almacenamiento
 * energético asociada, consumo eléctrico y tiempos de operación.
 *
 * <p>Esta clase actúa como modelo de datos (POJO) y puede usarse tanto
 * para reflejar el estado actual del intercambiador como para
 * persistir sus lecturas en la base de datos.
 *
 * @author  Gormiti
 * @version 1.0
 */
public class Intercambiador {

    /**
     * Temperatura del fluido en la entrada del intercambiador, en grados Celsius.
     * Representa la temperatura antes del proceso de intercambio térmico.
     */
    private float temperaturaEntrada;

    /**
     * Temperatura del fluido en la salida del intercambiador, en grados Celsius.
     * Representa la temperatura después del proceso de intercambio térmico.
     */
    private float temperaturaSalida;

    /**
     * Identificador de la fuente o depósito de almacenamiento energético
     * asociado al intercambiador (clave foránea hacia la tabla correspondiente).
     */
    private int fuenteAlmacenamiento;

    /**
     * Consumo eléctrico del intercambiador durante su operación, en kilovatios (kW).
     */
    private float consumo;

    /**
     * Tiempo que lleva encendido el intercambiador en el ciclo actual, en segundos.
     */
    private int tiempoEncendido;

    /**
     * Tiempo de refrigeración necesario tras un ciclo de operación, en segundos.
     */
    private int tiempoRefrigeracion;

    // ══════════════════════════════════════════════
    // CONSTRUCTORES
    // ══════════════════════════════════════════════

    /**
     * Constructor con parámetros que inicializa todos los atributos
     * del intercambiador.
     *
     * @param temperaturaEntrada   temperatura de entrada del fluido (°C)
     * @param temperaturaSalida    temperatura de salida del fluido (°C)
     * @param fuenteAlmacenamiento identificador de la fuente de almacenamiento
     * @param consumo              consumo eléctrico del dispositivo (kW)
     * @param tiempoEncendido      tiempo de operación en el ciclo actual (s)
     * @param tiempoRefrigeracion  tiempo de refrigeración tras el ciclo (s)
     */
    public Intercambiador(float temperaturaEntrada, float temperaturaSalida, int fuenteAlmacenamiento, float consumo,
            int tiempoEncendido, int tiempoRefrigeracion) {
        super();
        this.temperaturaEntrada   = temperaturaEntrada;
        this.temperaturaSalida    = temperaturaSalida;
        this.fuenteAlmacenamiento = fuenteAlmacenamiento;
        this.consumo              = consumo;
        this.tiempoEncendido      = tiempoEncendido;
        this.tiempoRefrigeracion  = tiempoRefrigeracion;
    }

    /**
     * Constructor vacío que crea un intercambiador sin valores iniciales.
     * Los atributos deben establecerse posteriormente mediante los setters.
     */
    public Intercambiador() {
    }

    // ══════════════════════════════════════════════
    // GETTERS Y SETTERS
    // ══════════════════════════════════════════════

    /**
     * Devuelve la temperatura de entrada del fluido al intercambiador.
     *
     * @return temperatura de entrada en grados Celsius
     */
    public float getTemperaturaEntrada() {
        return temperaturaEntrada;
    }

    /**
     * Establece la temperatura de entrada del fluido al intercambiador.
     *
     * @param temperaturaEntrada nueva temperatura de entrada en grados Celsius
     */
    public void setTemperaturaEntrada(float temperaturaEntrada) {
        this.temperaturaEntrada = temperaturaEntrada;
    }

    /**
     * Devuelve la temperatura de salida del fluido tras el intercambio térmico.
     *
     * @return temperatura de salida en grados Celsius
     */
    public float getTemperaturaSalida() {
        return temperaturaSalida;
    }

    /**
     * Establece la temperatura de salida del fluido tras el intercambio térmico.
     *
     * @param temperaturaSalida nueva temperatura de salida en grados Celsius
     */
    public void setTemperaturaSalida(float temperaturaSalida) {
        this.temperaturaSalida = temperaturaSalida;
    }

    /**
     * Devuelve el identificador de la fuente de almacenamiento energético
     * asociada a este intercambiador.
     *
     * @return identificador de la fuente de almacenamiento
     */
    public int getFuenteAlmacenamiento() {
        return fuenteAlmacenamiento;
    }

    /**
     * Establece el identificador de la fuente de almacenamiento energético
     * asociada a este intercambiador.
     *
     * @param fuenteAlmacenamiento nuevo identificador de la fuente
     */
    public void setFuenteAlmacenamiento(int fuenteAlmacenamiento) {
        this.fuenteAlmacenamiento = fuenteAlmacenamiento;
    }

    /**
     * Devuelve el consumo eléctrico del intercambiador.
     *
     * @return consumo en kilovatios (kW)
     */
    public float getConsumo() {
        return consumo;
    }

    /**
     * Establece el consumo eléctrico del intercambiador.
     *
     * @param consumo nuevo valor de consumo en kilovatios (kW)
     */
    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    /**
     * Devuelve el tiempo que lleva encendido el intercambiador
     * en el ciclo de operación actual.
     *
     * @return tiempo de encendido en segundos
     */
    public int getTiempoEncendido() {
        return tiempoEncendido;
    }

    /**
     * Establece el tiempo que lleva encendido el intercambiador
     * en el ciclo de operación actual.
     *
     * @param tiempoEncendido nuevo tiempo de encendido en segundos
     */
    public void setTiempoEncendido(int tiempoEncendido) {
        this.tiempoEncendido = tiempoEncendido;
    }

    /**
     * Devuelve el tiempo de refrigeración necesario tras un ciclo de operación.
     *
     * @return tiempo de refrigeración en segundos
     */
    public int getTiempoRefrigeracion() {
        return tiempoRefrigeracion;
    }

    /**
     * Establece el tiempo de refrigeración necesario tras un ciclo de operación.
     *
     * @param tiempoRefrigeracion nuevo tiempo de refrigeración en segundos
     */
    public void setTiempoRefrigeracion(int tiempoRefrigeracion) {
        this.tiempoRefrigeracion = tiempoRefrigeracion;
    }
}
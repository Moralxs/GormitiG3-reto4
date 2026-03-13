package Clases;



public class Intercambiador {

	

	

	private float temperaturaEntrada;

	private float temperaturaSalida;

	private int fuenteAlmacenamiento;

	private float consumo;

	private int tiempoEncendido;

	private int tiempoRefrigeracion;		

	

	public Intercambiador(float temperaturaEntrada, float temperaturaSalida, int fuenteAlmacenamiento, float consumo,

			int tiempoEncendido, int tiempoRefrigeracion) {

		super();

		this.temperaturaEntrada = temperaturaEntrada;

		this.temperaturaSalida = temperaturaSalida;

		this.fuenteAlmacenamiento = fuenteAlmacenamiento;

		this.consumo = consumo;

		this.tiempoEncendido = tiempoEncendido;

		this.tiempoRefrigeracion = tiempoRefrigeracion;

	}





	public Intercambiador() {

		// TODO Auto-generated constructor stub

		

	}





	public float getTemperaturaEntrada() {

		return temperaturaEntrada;

	}





	public void setTemperaturaEntrada(float temperaturaEntrada) {

		this.temperaturaEntrada = temperaturaEntrada;

	}





	public float getTemperaturaSalida() {

		return temperaturaSalida;

	}





	public void setTemperaturaSalida(float temperaturaSalida) {

		this.temperaturaSalida = temperaturaSalida;

	}





	public int getFuenteAlmacenamiento() {

		return fuenteAlmacenamiento;

	}





	public void setFuenteAlmacenamiento(int fuenteAlmacenamiento) {

		this.fuenteAlmacenamiento = fuenteAlmacenamiento;

	}





	public float getConsumo() {

		return consumo;

	}





	public void setConsumo(float consumo) {

		this.consumo = consumo;

	}





	public int getTiempoEncendido() {

		return tiempoEncendido;

	}





	public void setTiempoEncendido(int tiempoEncendido) {

		this.tiempoEncendido = tiempoEncendido;

	}





	public int getTiempoRefrigeracion() {

		return tiempoRefrigeracion;

	}





	public void setTiempoRefrigeracion(int tiempoRefrigeracion) {

		this.tiempoRefrigeracion = tiempoRefrigeracion;

	}



}


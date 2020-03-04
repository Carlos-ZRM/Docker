package com.castellana.ecommerce.dto.pago;

public class PaqueteEstafetaDto {

	private int botellas;
	private double peso;
	private double largo;
	private double alto;
	private double ancho;

	public int getBotellas() {
		return botellas;
	}

	public void setBotellas(int botellas) {
		this.botellas = botellas;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	@Override
	public String toString() {
		return "PaqueteEstafetaDto [botellas=" + botellas + ", peso=" + peso + ", largo=" + largo + ", alto=" + alto
				+ ", ancho=" + ancho + "]";
	}

}

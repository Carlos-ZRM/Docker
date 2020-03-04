package com.castellana.ecommerce.dto;

import java.io.Serializable;

public class TipoEnvioDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean esPaquete;
	private double largo;
	private double peso;
	private double alto;
	private double ancho;

	public boolean isEsPaquete() {
		return esPaquete;
	}

	public void setEsPaquete(boolean esPaquete) {
		this.esPaquete = esPaquete;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
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
		return "TipoEnvioDto [esPaquete=" + esPaquete + ", largo=" + largo + ", peso=" + peso + ", alto=" + alto
				+ ", ancho=" + ancho + "]";
	}

}

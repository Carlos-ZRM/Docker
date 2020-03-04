package com.castellana.ecommerce.dto;

public class CostosDto {

	private int codigoPostal;
	private int zona;
	private int numBotellas;
	private int reexpedicion;
	private double peso;
	private double costoUnKilo;
	private double costoKiloAdicional;
	private double costoCincoKilos;
	private double costoKiloAdicionalMasDeCinco;
	private double costoCombustible;
	private double costoReexpedicion;

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public int getZona() {
		return zona;
	}

	public void setZona(int zona) {
		this.zona = zona;
	}

	public int getNumBotellas() {
		return numBotellas;
	}

	public void setNumBotellas(int numBotellas) {
		this.numBotellas = numBotellas;
	}

	public int getReexpedicion() {
		return reexpedicion;
	}

	public void setReexpedicion(int reexpedicion) {
		this.reexpedicion = reexpedicion;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getCostoUnKilo() {
		return costoUnKilo;
	}

	public void setCostoUnKilo(double costoUnKilo) {
		this.costoUnKilo = costoUnKilo;
	}

	public double getCostoKiloAdicional() {
		return costoKiloAdicional;
	}

	public void setCostoKiloAdicional(double costoKiloAdicional) {
		this.costoKiloAdicional = costoKiloAdicional;
	}

	public double getCostoCincoKilos() {
		return costoCincoKilos;
	}

	public void setCostoCincoKilos(double costoCincoKilos) {
		this.costoCincoKilos = costoCincoKilos;
	}

	public double getCostoKiloAdicionalMasDeCinco() {
		return costoKiloAdicionalMasDeCinco;
	}

	public void setCostoKiloAdicionalMasDeCinco(double costoKiloAdicionalMasDeCinco) {
		this.costoKiloAdicionalMasDeCinco = costoKiloAdicionalMasDeCinco;
	}

	public double getCostoCombustible() {
		return costoCombustible;
	}

	public void setCostoCombustible(double costoCombustible) {
		this.costoCombustible = costoCombustible;
	}

	public double getCostoReexpedicion() {
		return costoReexpedicion;
	}

	public void setCostoReexpedicion(double costoReexpedicion) {
		this.costoReexpedicion = costoReexpedicion;
	}

	@Override
	public String toString() {
		return "CostosDto [codigoPostal=" + codigoPostal + ", zona=" + zona + ", numBotellas=" + numBotellas
				+ ", reexpedicion=" + reexpedicion + ", peso=" + peso + ", costoUnKilo=" + costoUnKilo
				+ ", costoKiloAdicional=" + costoKiloAdicional + ", costoCincoKilos=" + costoCincoKilos
				+ ", costoKiloAdicionalMasDeCinco=" + costoKiloAdicionalMasDeCinco + ", costoCombustible="
				+ costoCombustible + ", costoReexpedicion=" + costoReexpedicion + "]";
	}

}

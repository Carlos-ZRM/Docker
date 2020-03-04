package com.castellana.ecommerce.dto;

import java.util.ArrayList;

public class Response {
	
	private String cp;
	private ArrayList<String> asentamiento;
	private String tipo_asentamiento;
	private String municipio;
	private String estado;
	private String ciudad;
	private String pais;
	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}
	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	/**
	 * @return the asentamiento
	 */
	public ArrayList<String> getAsentamiento() {
		return asentamiento;
	}
	/**
	 * @param asentamiento the asentamiento to set
	 */
	public void setAsentamiento(ArrayList<String> asentamiento) {
		this.asentamiento = asentamiento;
	}
	/**
	 * @return the tipo_asentamiento
	 */
	public String getTipo_asentamiento() {
		return tipo_asentamiento;
	}
	/**
	 * @param tipo_asentamiento the tipo_asentamiento to set
	 */
	public void setTipo_asentamiento(String tipo_asentamiento) {
		this.tipo_asentamiento = tipo_asentamiento;
	}
	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

}

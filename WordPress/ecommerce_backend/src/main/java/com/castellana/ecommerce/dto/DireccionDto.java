package com.castellana.ecommerce.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class DireccionDto implements Serializable {


	private static final long serialVersionUID = 1L;
	private String codigo_postal;
	private String municipio;
	private String estado;
	private ArrayList<String> colonias = new ArrayList<String>();

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ArrayList<String> getColonias() {
		return colonias;
	}

	public void setColonias(ArrayList<String> colonias) {
		this.colonias = colonias;
	}
}

package com.castellana.ecommerce.dto;

import java.util.List;

public class DireccionComponentReturn {
	
	private List<DireccionDTO> direcciones;

	/**
	 * @return the direcciones
	 */
	public List<DireccionDTO> getDirecciones() {
		return direcciones;
	}

	/**
	 * @param direcciones the direcciones to set
	 */
	public void setDirecciones(List<DireccionDTO> direcciones) {
		this.direcciones = direcciones;
	}
	
}

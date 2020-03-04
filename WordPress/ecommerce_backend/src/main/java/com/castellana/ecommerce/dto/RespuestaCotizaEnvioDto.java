package com.castellana.ecommerce.dto;

import java.util.Arrays;

public class RespuestaCotizaEnvioDto {
	
	private TipoEnvioDto tipoEnvio;
    private TipoServicioDto tipoServicio[];
	public TipoEnvioDto getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(TipoEnvioDto tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public TipoServicioDto[] getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(TipoServicioDto[] tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	@Override
	public String toString() {
		return "RespuestaCotizaEnvioDto [tipoEnvio=" + tipoEnvio + ", tipoServicio=" + Arrays.toString(tipoServicio)
				+ "]";
	}

	
}

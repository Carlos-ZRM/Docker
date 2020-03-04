package com.castellana.ecommerce.dto;

import java.io.Serializable;

public class TipoServicioDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String descripcionServicio;
	private int tipoEnvioRes;
	private String aplicaCotizacion;
	private double tarifaBase;
	private double CCTarifaBase;
	private double cargosExtra;
	private double sobrePeso;
	private double CCSobrePeso;
	private double costoTotal;
	private double peso;
	private String aplicaServicio;

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public int getTipoEnvioRes() {
		return tipoEnvioRes;
	}

	public void setTipoEnvioRes(int tipoEnvioRes) {
		this.tipoEnvioRes = tipoEnvioRes;
	}

	public String getAplicaCotizacion() {
		return aplicaCotizacion;
	}

	public void setAplicaCotizacion(String aplicaCotizacion) {
		this.aplicaCotizacion = aplicaCotizacion;
	}

	public double getTarifaBase() {
		return tarifaBase;
	}

	public void setTarifaBase(double tarifaBase) {
		this.tarifaBase = tarifaBase;
	}

	public double getCCTarifaBase() {
		return CCTarifaBase;
	}

	public void setCCTarifaBase(double cCTarifaBase) {
		CCTarifaBase = cCTarifaBase;
	}

	public double getCargosExtra() {
		return cargosExtra;
	}

	public void setCargosExtra(double cargosExtra) {
		this.cargosExtra = cargosExtra;
	}

	public double getSobrePeso() {
		return sobrePeso;
	}

	public void setSobrePeso(double sobrePeso) {
		this.sobrePeso = sobrePeso;
	}

	public double getCCSobrePeso() {
		return CCSobrePeso;
	}

	public void setCCSobrePeso(double cCSobrePeso) {
		CCSobrePeso = cCSobrePeso;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getAplicaServicio() {
		return aplicaServicio;
	}

	public void setAplicaServicio(String aplicaServicio) {
		this.aplicaServicio = aplicaServicio;
	}

	@Override
	public String toString() {
		return "TipoServicioDto [descripcionServicio=" + descripcionServicio + ", tipoEnvioRes=" + tipoEnvioRes
				+ ", aplicaCotizacion=" + aplicaCotizacion + ", tarifaBase=" + tarifaBase + ", CCTarifaBase="
				+ CCTarifaBase + ", cargosExtra=" + cargosExtra + ", sobrePeso=" + sobrePeso + ", CCSobrePeso="
				+ CCSobrePeso + ", costoTotal=" + costoTotal + ", peso=" + peso + ", aplicaServicio=" + aplicaServicio
				+ "]";
	}
}

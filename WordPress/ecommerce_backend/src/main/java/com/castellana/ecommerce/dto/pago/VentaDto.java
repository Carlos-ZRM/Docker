package com.castellana.ecommerce.dto.pago;

import java.math.BigDecimal;

public class VentaDto {

	private int idVenta;
	private int idUsuario;
	private String usuario;
	private int idDireccion;
	private String fechaCreacion;
	private int idEstatusPortal;
	private String estatusPortal;
	private BigDecimal costoEnvio;
	private long idMercadoPago;
	private String estatusPago;
	private String estatusPagoDetalle;

	private String metodoPago;
	private BigDecimal subtotal;
	private BigDecimal total;

	private int servicioEnvio;
	private BigDecimal pesoEnvio;

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(int idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getIdEstatusPortal() {
		return idEstatusPortal;
	}

	public void setIdEstatusPortal(int idEstatusPortal) {
		this.idEstatusPortal = idEstatusPortal;
	}

	public String getEstatusPortal() {
		return estatusPortal;
	}

	public void setEstatusPortal(String estatusPortal) {
		this.estatusPortal = estatusPortal;
	}

	public BigDecimal getCostoEnvio() {
		return costoEnvio;
	}

	public void setCostoEnvio(BigDecimal costoEnvio) {
		this.costoEnvio = costoEnvio;
	}

	public long getIdMercadoPago() {
		return idMercadoPago;
	}

	public void setIdMercadoPago(long idMercadoPago) {
		this.idMercadoPago = idMercadoPago;
	}

	public String getEstatusPago() {
		return estatusPago;
	}

	public void setEstatusPago(String estatusPago) {
		this.estatusPago = estatusPago;
	}

	public String getEstatusPagoDetalle() {
		return estatusPagoDetalle;
	}

	public void setEstatusPagoDetalle(String estatusPagoDetalle) {
		this.estatusPagoDetalle = estatusPagoDetalle;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getServicioEnvio() {
		return servicioEnvio;
	}

	public void setServicioEnvio(int servicioEnvio) {
		this.servicioEnvio = servicioEnvio;
	}

	public BigDecimal getPesoEnvio() {
		return pesoEnvio;
	}

	public void setPesoEnvio(BigDecimal pesoEnvio) {
		this.pesoEnvio = pesoEnvio;
	}

	@Override
	public String toString() {
		return "VentaDto [idVenta=" + idVenta + ", idUsuario=" + idUsuario + ", usuario=" + usuario + ", idDireccion="
				+ idDireccion + ", fechaCreacion=" + fechaCreacion + ", idEstatusPortal=" + idEstatusPortal
				+ ", estatusPortal=" + estatusPortal + ", costoEnvio=" + costoEnvio + ", idMercadoPago=" + idMercadoPago
				+ ", estatusPago=" + estatusPago + ", estatusPagoDetalle=" + estatusPagoDetalle + ", metodoPago="
				+ metodoPago + ", subtotal=" + subtotal + ", total=" + total + ", servicioEnvio=" + servicioEnvio
				+ ", pesoEnvio=" + pesoEnvio + "]";
	}

}
package com.castellana.ecommerce.dto.pago;

import java.math.BigDecimal;

public class VentaLDto {
	
	private int idVenta;
	private String fechaCreacion;
	private String metodoPago;
	private BigDecimal subtotal;
	private BigDecimal costoEnvio;
	private BigDecimal total;
	private String estatusPortal;
	private String calleDomicilio;
	public int getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
	public BigDecimal getCostoEnvio() {
		return costoEnvio;
	}
	public void setCostoEnvio(BigDecimal costoEnvio) {
		this.costoEnvio = costoEnvio;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getEstatusPortal() {
		return estatusPortal;
	}
	public void setEstatusPortal(String estatusPortal) {
		this.estatusPortal = estatusPortal;
	}
	public String getCalleDomicilio() {
		return calleDomicilio;
	}
	public void setCalleDomicilio(String calleDomicilio) {
		this.calleDomicilio = calleDomicilio;
	}
	@Override
	public String toString() {
		return "VentaLDto [idVenta=" + idVenta + ", fechaCreacion=" + fechaCreacion + ", metodoPago=" + metodoPago
				+ ", subtotal=" + subtotal + ", costoEnvio=" + costoEnvio + ", total=" + total + ", estatusPortal="
				+ estatusPortal + ", calleDomicilio=" + calleDomicilio + "]";
	}

}

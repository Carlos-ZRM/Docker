package com.castellana.ecommerce.dto;

import java.math.BigDecimal;

public class DetalleVentaDto {

	private String imagen1;
	private String descripcionArticulo;
	private int idVenta;
	private int idArticulo;
	private int cantidad;
	private BigDecimal precioConImpuesto;
	private BigDecimal descuentoPorcentual;
	private BigDecimal precioConDescuento;
	private BigDecimal precioFinal;
	private BigDecimal precioEnvio;
	private BigDecimal totalVenta;
	private int idDireccion;

	public String getImagen1() {
		return imagen1;
	}

	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}

	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}

	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioConImpuesto() {
		return precioConImpuesto;
	}

	public void setPrecioConImpuesto(BigDecimal precioConImpuesto) {
		this.precioConImpuesto = precioConImpuesto;
	}

	public BigDecimal getDescuentoPorcentual() {
		return descuentoPorcentual;
	}

	public void setDescuentoPorcentual(BigDecimal descuentoPorcentual) {
		this.descuentoPorcentual = descuentoPorcentual;
	}

	public BigDecimal getPrecioConDescuento() {
		return precioConDescuento;
	}

	public void setPrecioConDescuento(BigDecimal precioConDescuento) {
		this.precioConDescuento = precioConDescuento;
	}

	public BigDecimal getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(BigDecimal precioFinal) {
		this.precioFinal = precioFinal;
	}

	public BigDecimal getPrecioEnvio() {
		return precioEnvio;
	}

	public void setPrecioEnvio(BigDecimal precioEnvio) {
		this.precioEnvio = precioEnvio;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}

	public int getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(int idDireccion) {
		this.idDireccion = idDireccion;
	}

	@Override
	public String toString() {
		return "DetalleVentaDto [imagen1=" + imagen1 + ", descripcionArticulo=" + descripcionArticulo + ", idVenta="
				+ idVenta + ", idArticulo=" + idArticulo + ", cantidad=" + cantidad + ", precioConImpuesto="
				+ precioConImpuesto + ", descuentoPorcentual=" + descuentoPorcentual + ", precioConDescuento="
				+ precioConDescuento + ", precioFinal=" + precioFinal + ", precioEnvio=" + precioEnvio + ", totalVenta="
				+ totalVenta + ", idDireccion=" + idDireccion + "]";
	}

}

package com.castellana.ecommerce.dto.pago;

import java.math.BigDecimal;

public class CuerpoPagoTarjetaAlmacenadaDto {

	private String tokenCarrito;
	private String token;
	private String idDireccion;
	private String tipoEnvio;
	private String costoEnvio;
	private String costoTotal;
	private String idCard;
	private String clienteId;
	private BigDecimal totalCompra;
	private String installments;
	private String calleTarjeta;
	private String cpTarjeta;
	private String numCalleTarjera;

	public String getTokenCarrito() {
		return tokenCarrito;
	}

	public void setTokenCarrito(String tokenCarrito) {
		this.tokenCarrito = tokenCarrito;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public String getCostoEnvio() {
		return costoEnvio;
	}

	public void setCostoEnvio(String costoEnvio) {
		this.costoEnvio = costoEnvio;
	}

	public String getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(String costoTotal) {
		this.costoTotal = costoTotal;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}

	public String getInstallments() {
		return installments;
	}

	public void setInstallments(String installments) {
		this.installments = installments;
	}
	
	
	public String getCalleTarjeta() {
		return calleTarjeta;
	}

	public void setCalleTarjeta(String calleTarjeta) {
		this.calleTarjeta = calleTarjeta;
	}

	public String getCpTarjeta() {
		return cpTarjeta;
	}

	public void setCpTarjeta(String cpTarjeta) {
		this.cpTarjeta = cpTarjeta;
	}

	public String getNumCalleTarjera() {
		return numCalleTarjera;
	}

	public void setNumCalleTarjera(String numCalleTarjera) {
		this.numCalleTarjera = numCalleTarjera;
	}

	@Override
	public String toString() {
		return "CuerpoPagoTarjetaAlmacenadaDto [tokenCarrito=" + tokenCarrito + ", token=" + token + ", idDireccion="
				+ idDireccion + ", tipoEnvio=" + tipoEnvio + ", costoEnvio=" + costoEnvio + ", costoTotal=" + costoTotal
				+ ", idCard=" + idCard + ", clienteId=" + clienteId + ", totalCompra=" + totalCompra + ", installments="
				+ installments + ", calleTarjeta=" + calleTarjeta + ", cpTarjeta=" + cpTarjeta + ", numCalleTarjera="
				+ numCalleTarjera + "]";
	}

}

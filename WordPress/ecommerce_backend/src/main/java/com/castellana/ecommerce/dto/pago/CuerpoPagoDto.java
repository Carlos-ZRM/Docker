package com.castellana.ecommerce.dto.pago;

import java.util.ArrayList;

public class CuerpoPagoDto {

	private String token;
	private String tokenCarrito;
	private String idDireccion;
	private String tipoEnvio;
	private String costoEnvio;
	private String costoTotal;
	private String tipoTarjeta;
	private ArrayList<ItemDto> items;
	private String issuerID;
	private String installments;
	private String calleTarjeta;
	private String cpTarjeta;
	private String numCalleTarjera;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenCarrito() {
		return tokenCarrito;
	}

	public void setTokenCarrito(String tokenCarrito) {
		this.tokenCarrito = tokenCarrito;
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

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public ArrayList<ItemDto> getItems() {
		return items;
	}

	public void ArrayList(ArrayList<ItemDto> items) {
		this.items = items;
	}

	public String getIssuerID() {
		return issuerID;
	}

	public void setIssuerID(String issuerID) {
		this.issuerID = issuerID;
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
		return "CuerpoPagoDto [token=" + token + ", tokenCarrito=" + tokenCarrito + ", idDireccion=" + idDireccion
				+ ", tipoEnvio=" + tipoEnvio + ", costoEnvio=" + costoEnvio + ", costoTotal=" + costoTotal
				+ ", tipoTarjeta=" + tipoTarjeta + ", items=" + items + ", issuerID=" + issuerID + ", installments="
				+ installments + ", calleTarjeta=" + calleTarjeta + ", cpTarjeta=" + cpTarjeta + ", numCalleTarjera="
				+ numCalleTarjera + "]";
	}

}

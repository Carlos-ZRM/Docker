package com.castellana.ecommerce.dto;

import java.util.List;

public class ParametrosValCompProdDTO {
	
	private int idUsuario;
	private int idToken;
	private List<Integer> idproductos;
	private String fecha;
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdToken() {
		return idToken;
	}
	public void setIdToken(int idToken) {
		this.idToken = idToken;
	}
	public List<Integer> getIdproductos() {
		return idproductos;
	}
	public void setIdproductos(List<Integer> idproductos) {
		this.idproductos = idproductos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		return "ParametrosValCompProdDTO [idUsuario=" + idUsuario + ", idToken=" + idToken + ", idproductos="
				+ idproductos + ", fecha=" + fecha + "]";
	}

}

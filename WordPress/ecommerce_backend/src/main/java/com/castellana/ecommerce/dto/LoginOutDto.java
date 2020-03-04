package com.castellana.ecommerce.dto;

import java.io.Serializable;

import com.castellana.ecommerce.models.UsuarioModel;

public class LoginOutDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String token;
	private UsuarioModel usuario;
	
	public LoginOutDto(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UsuarioModel getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

}

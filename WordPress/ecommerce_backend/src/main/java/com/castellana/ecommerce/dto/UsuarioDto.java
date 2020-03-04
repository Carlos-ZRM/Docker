package com.castellana.ecommerce.dto;

public class UsuarioDto {

	private String usuario;
	private int idUsuario;
	private String nombre;
	private String appaterno;
	private String apmaterno;
	private String email;
	private String password;

	private String fechaRegistro;
	private int idVenta;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAppaterno() {
		return appaterno;
	}

	public void setAppaterno(String appaterno) {
		this.appaterno = appaterno;
	}

	public String getApmaterno() {
		return apmaterno;
	}

	public void setApmaterno(String apmaterno) {
		this.apmaterno = apmaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	@Override
	public String toString() {
		return "UsuarioDto [usuario=" + usuario + ", idUsuario=" + idUsuario + ", nombre=" + nombre + ", appaterno="
				+ appaterno + ", apmaterno=" + apmaterno + ", email=" + email + ", password=" + password
				+ ", fechaRegistro=" + fechaRegistro + ", idVenta=" + idVenta + "]";
	}

}
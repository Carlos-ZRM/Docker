package com.castellana.ecommerce.dto;

public class MensajeContactanos {

	private String nombre;
	private String mail;
	private String mensaje;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "mensajeContactanos [nombre=" + nombre + ", mail=" + mail + ", mensaje=" + mensaje + "]";
	}

}

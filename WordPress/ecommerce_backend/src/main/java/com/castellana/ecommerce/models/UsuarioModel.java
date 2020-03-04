package com.castellana.ecommerce.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author Augusto GG 
 */
@Entity
@Table(name = "EcommerceUsuario")
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idUsuario")
	private int idUsuario;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "nombre")
    private String nombre;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "appaterno")
    private String appaterno;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "apmaterno")
    private String apmaterno;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "email")
    private String email;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "password")
    private String password;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "fechaRegistro")
	private String fechaRegistro;
	
	@Transient
	private String browserMajorVersion;
	@Transient
	private String platformVersion;
    
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
	public String getBrowserMajorVersion() {
		return browserMajorVersion;
	}
	public void setBrowserMajorVersion(String browserMajorVersion) {
		this.browserMajorVersion = browserMajorVersion;
	}
	public String getPlatformVersion() {
		return platformVersion;
	}
	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apmaterno == null) ? 0 : apmaterno.hashCode());
		result = prime * result + ((appaterno == null) ? 0 : appaterno.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaRegistro == null) ? 0 : fechaRegistro.hashCode());
		result = prime * result + idUsuario;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioModel other = (UsuarioModel) obj;
		if (apmaterno == null) {
			if (other.apmaterno != null)
				return false;
		} else if (!apmaterno.equals(other.apmaterno))
			return false;
		if (appaterno == null) {
			if (other.appaterno != null)
				return false;
		} else if (!appaterno.equals(other.appaterno))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaRegistro == null) {
			if (other.fechaRegistro != null)
				return false;
		} else if (!fechaRegistro.equals(other.fechaRegistro))
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", appaterno=" + appaterno + ", apmaterno="
				+ apmaterno + ", email=" + email + ", password=" + password + ", fechaRegistro=" + fechaRegistro + "]";
	}
}
